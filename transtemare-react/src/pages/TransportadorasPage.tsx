import { useState, useCallback, useRef, useEffect } from 'react'
import {
  Alert,
  Box,
  Button,
  CircularProgress,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Divider,
  IconButton,
  Paper,
  Stack,
  TextField,
  Tooltip,
  Typography,
  Chip,
} from '@mui/material'
import AddIcon from '@mui/icons-material/Add'
import EditIcon from '@mui/icons-material/Edit'
import DeleteIcon from '@mui/icons-material/Delete'
import CloudUploadIcon from '@mui/icons-material/CloudUpload'
import ImageIcon from '@mui/icons-material/Image'
import { DataGrid } from '@mui/x-data-grid'
import type { GridColDef, GridPaginationModel, GridSortModel } from '@mui/x-data-grid'
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { useForm, Controller } from 'react-hook-form'
import {
  fetchTransportadoras,
  crearTransportadora,
  editarTransportadora,
  eliminarTransportadora,
  subirLogoTransportadora,
  getLogoUrl,
} from '../api/transportadoras'
import type { Transportadora, TransportadoraFormData } from '../types/transportadora'
import ConfirmDialog from '../components/ConfirmDialog'
import LocalidadAutocomplete from '../components/LocalidadAutocomplete'

const DEFAULT_VALUES: TransportadoraFormData = {
  nombre: '',
  domicilio: '',
  rol: '',
  localidad: '',
  prefijo: '',
  numerador: '',
  imagen: '',
}

export default function TransportadorasPage() {
  const queryClient = useQueryClient()

  // Pagination & sort
  const [paginationModel, setPaginationModel] = useState<GridPaginationModel>({
    page: 0,
    pageSize: 15,
  })
  const [sortModel, setSortModel] = useState<GridSortModel>([])
  const [search, setSearch] = useState('')
  const [searchInput, setSearchInput] = useState('')

  // Selected row — tracked as state, updated via onRowClick
  const [selectedRow, setSelectedRow] = useState<Transportadora | null>(null)

  // Modal
  const [modalOpen, setModalOpen] = useState(false)
  const [editingRow, setEditingRow] = useState<Transportadora | null>(null)

  // Delete
  const [deleteTarget, setDeleteTarget] = useState<Transportadora | null>(null)

  // Logo upload
  const fileInputRef = useRef<HTMLInputElement>(null)
  const [logoFile, setLogoFile] = useState<File | null>(null)
  const [logoPreviewUrl, setLogoPreviewUrl] = useState<string | null>(null)
  const [logoCacheBust, setLogoCacheBust] = useState(Date.now())
  const [logoUploadMsg, setLogoUploadMsg] = useState<{
    type: 'success' | 'error'
    text: string
  } | null>(null)

  // Form
  const {
    control,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<TransportadoraFormData>({ defaultValues: DEFAULT_VALUES })

  // Query
  const { data, isLoading, isError } = useQuery({
    queryKey: [
      'transportadoras',
      paginationModel.page,
      paginationModel.pageSize,
      sortModel,
      search,
    ],
    queryFn: () =>
      fetchTransportadoras({
        page: paginationModel.page + 1,
        rows: paginationModel.pageSize,
        sidx: sortModel[0]?.field,
        sord: sortModel[0]?.sort as 'asc' | 'desc' | undefined,
        searchField: search ? 'nombre' : undefined,
        searchString: search || undefined,
        searchOper: search ? 'cn' : undefined,
      }),
  })

  const invalidate = useCallback(() => {
    queryClient.invalidateQueries({ queryKey: ['transportadoras'] })
  }, [queryClient])

  const createMutation = useMutation({
    mutationFn: crearTransportadora,
    onSuccess: () => {
      invalidate()
      setModalOpen(false)
    },
  })

  const editMutation = useMutation({
    mutationFn: ({ id, form }: { id: number; form: TransportadoraFormData }) =>
      editarTransportadora(id, form),
    onSuccess: () => {
      invalidate()
      setModalOpen(false)
    },
  })

  const deleteMutation = useMutation({
    mutationFn: eliminarTransportadora,
    onSuccess: () => {
      invalidate()
      setDeleteTarget(null)
      setSelectedRow(null)
    },
  })

  const logoMutation = useMutation({
    mutationFn: ({ id, file }: { id: number; file: File }) =>
      subirLogoTransportadora(id, file),
    onSuccess: () => {
      setLogoUploadMsg({ type: 'success', text: 'Logo subido correctamente.' })
      setLogoFile(null)
      setLogoPreviewUrl(null)
      setLogoCacheBust(Date.now())
    },
    onError: () => {
      setLogoUploadMsg({ type: 'error', text: 'Error al subir el logo.' })
    },
  })

  // Handlers
  const openCreate = () => {
    setEditingRow(null)
    reset(DEFAULT_VALUES)
    setModalOpen(true)
  }

  const openEdit = (row: Transportadora) => {
    setEditingRow(row)
    reset({
      nombre: row.nombre,
      domicilio: row.domicilio,
      rol: row.rol,
      localidad: row.localidad,
      prefijo: row.prefijo,
      numerador: row.numerador,
      imagen: row.imagen,
    })
    setModalOpen(true)
  }

  const onSubmit = (form: TransportadoraFormData) => {
    if (editingRow) {
      editMutation.mutate({ id: editingRow.id, form })
    } else {
      createMutation.mutate(form)
    }
  }

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0]
    if (!file) return
    setLogoFile(file)
    setLogoPreviewUrl(URL.createObjectURL(file))
    setLogoUploadMsg(null)
  }

  const handleUploadLogo = () => {
    if (!selectedRow || !logoFile) return
    logoMutation.mutate({ id: selectedRow.id, file: logoFile })
  }

  const handleRowClick = (row: Transportadora) => {
    if (selectedRow?.id === row.id) {
      setSelectedRow(null)
    } else {
      setSelectedRow(row)
      setLogoFile(null)
      setLogoPreviewUrl(null)
      setLogoUploadMsg(null)
      if (fileInputRef.current) fileInputRef.current.value = ''
    }
  }

  const isMutating = createMutation.isPending || editMutation.isPending

  // Columns
  const columns: GridColDef<Transportadora>[] = [
    { field: 'id', headerName: 'ID', width: 60, type: 'number' },
    { field: 'nombre', headerName: 'Nombre', flex: 1.5, minWidth: 160 },
    { field: 'domicilio', headerName: 'Domicilio', flex: 1.5, minWidth: 130 },
    { field: 'rol', headerName: 'Rol Contribuyente', flex: 1, minWidth: 130 },
    { field: 'localidad', headerName: 'Localidad', flex: 1, minWidth: 110 },
    { field: 'pais', headerName: 'País', width: 90 },
    { field: 'prefijo', headerName: 'Prefijo', width: 80 },
    { field: 'numerador', headerName: 'Numerador', width: 95 },
    {
      field: 'imagen',
      headerName: 'Imagen',
      width: 100,
      sortable: false,
      renderCell: ({ row }) =>
        row.imagen ? (
          <Chip label={row.imagen} size="small" variant="outlined" sx={{ fontSize: 10 }} />
        ) : (
          <Typography variant="caption" color="text.secondary">
            —
          </Typography>
        ),
    },
    {
      field: '_actions',
      headerName: 'Acciones',
      width: 100,
      sortable: false,
      filterable: false,
      renderCell: ({ row }) => (
        <Stack direction="row" spacing={0.5}>
          <Tooltip title="Editar">
            <IconButton
              size="small"
              color="primary"
              onClick={(e) => {
                e.stopPropagation()
                openEdit(row)
              }}
            >
              <EditIcon fontSize="small" />
            </IconButton>
          </Tooltip>
          <Tooltip title="Eliminar">
            <IconButton
              size="small"
              color="error"
              onClick={(e) => {
                e.stopPropagation()
                setDeleteTarget(row)
              }}
            >
              <DeleteIcon fontSize="small" />
            </IconButton>
          </Tooltip>
        </Stack>
      ),
    },
  ]

  return (
    <Box>
      {/* Header */}
      <Stack
        direction={{ xs: 'column', sm: 'row' }}
        alignItems={{ sm: 'center' }}
        justifyContent="space-between"
        spacing={2}
        mb={2}
      >
        <Typography variant="h5" color="primary">
          Transportadoras
        </Typography>
        <Stack direction="row" spacing={1}>
          <TextField
            size="small"
            placeholder="Buscar por nombre..."
            value={searchInput}
            onChange={(e) => setSearchInput(e.target.value)}
            onKeyDown={(e) => {
              if (e.key === 'Enter') {
                setSearch(searchInput)
                setPaginationModel((p) => ({ ...p, page: 0 }))
              }
            }}
            sx={{ width: 220 }}
          />
          <Button
            variant="outlined"
            size="small"
            onClick={() => {
              setSearch(searchInput)
              setPaginationModel((p) => ({ ...p, page: 0 }))
            }}
          >
            Buscar
          </Button>
          {search && (
            <Button
              variant="text"
              size="small"
              onClick={() => {
                setSearch('')
                setSearchInput('')
                setPaginationModel((p) => ({ ...p, page: 0 }))
              }}
            >
              Limpiar
            </Button>
          )}
          <Button variant="contained" startIcon={<AddIcon />} onClick={openCreate}>
            Agregar
          </Button>
        </Stack>
      </Stack>

      {isError && (
        <Alert severity="error" sx={{ mb: 2 }}>
          Error al cargar las transportadoras. Verificar que el servidor esté activo.
        </Alert>
      )}

      {/* DataGrid */}
      <Box
        sx={{
          height: 460,
          width: '100%',
          bgcolor: 'background.paper',
          borderRadius: 2,
          boxShadow: 1,
          '& .MuiDataGrid-columnHeaders': { bgcolor: '#e8eef7' },
          '& .MuiDataGrid-row': { cursor: 'pointer' },
          '& .row-selected': { bgcolor: '#dce8ff', '&:hover': { bgcolor: '#c8d9f5' } },
        }}
      >
        <DataGrid
          rows={data?.gridModel ?? []}
          columns={columns}
          rowCount={data?.records ?? 0}
          loading={isLoading}
          paginationMode="server"
          sortingMode="server"
          paginationModel={paginationModel}
          onPaginationModelChange={setPaginationModel}
          sortModel={sortModel}
          onSortModelChange={setSortModel}
          pageSizeOptions={[15, 30, 60]}
          onRowClick={({ row }) => handleRowClick(row as Transportadora)}
          getRowClassName={({ row }) =>
            (row as Transportadora).id === selectedRow?.id ? 'row-selected' : ''
          }
          density="compact"
          getRowId={(row) => row.id}
          disableRowSelectionOnClick
          sx={{ border: 0 }}
        />
      </Box>

      {/* Info hint */}
      {!selectedRow && (
        <Typography variant="caption" color="text.secondary" sx={{ mt: 1, display: 'block' }}>
          Haz clic en una fila para ver y gestionar el logo de la transportadora.
        </Typography>
      )}

      {/* Logo panel */}
      {selectedRow && (
        <Paper
          elevation={2}
          sx={{
            mt: 2,
            p: 2.5,
            borderRadius: 2,
            border: '1px solid',
            borderColor: 'primary.light',
          }}
        >
          <Stack
            direction={{ xs: 'column', md: 'row' }}
            spacing={3}
            alignItems={{ md: 'flex-start' }}
          >
            {/* Current logo */}
            <Box>
              <Typography variant="subtitle2" color="text.secondary" gutterBottom>
                Logo actual — <strong>{selectedRow.nombre}</strong>
              </Typography>
              <LogoPreview
                key={`${selectedRow.id}-${logoCacheBust}`}
                id={selectedRow.id}
                cacheBust={logoCacheBust}
              />
            </Box>

            <Divider
              orientation="vertical"
              flexItem
              sx={{ display: { xs: 'none', md: 'block' } }}
            />

            {/* Upload new logo */}
            <Box sx={{ flex: 1 }}>
              <Typography variant="subtitle2" color="text.secondary" gutterBottom>
                Subir nuevo logo
              </Typography>
              <Stack spacing={1.5}>
                <input
                  ref={fileInputRef}
                  type="file"
                  accept="image/*"
                  style={{ display: 'none' }}
                  onChange={handleFileChange}
                  id="logo-file-input"
                />
                <Stack direction="row" spacing={1} alignItems="center">
                  <Button
                    variant="outlined"
                    size="small"
                    startIcon={<ImageIcon />}
                    component="label"
                    htmlFor="logo-file-input"
                  >
                    Seleccionar imagen
                  </Button>
                  {logoFile && (
                    <Typography
                      variant="body2"
                      color="text.secondary"
                      noWrap
                      sx={{ maxWidth: 200 }}
                    >
                      {logoFile.name}
                    </Typography>
                  )}
                </Stack>

                {logoPreviewUrl && (
                  <Box
                    sx={{
                      width: 180,
                      height: 100,
                      border: '1px solid',
                      borderColor: 'primary.light',
                      borderRadius: 1,
                      overflow: 'hidden',
                      bgcolor: '#f8f9fa',
                      display: 'flex',
                      alignItems: 'center',
                      justifyContent: 'center',
                    }}
                  >
                    <Box
                      component="img"
                      src={logoPreviewUrl}
                      alt="Preview"
                      sx={{ maxWidth: '100%', maxHeight: '100%', objectFit: 'contain' }}
                    />
                  </Box>
                )}

                <Button
                  variant="contained"
                  size="small"
                  startIcon={
                    logoMutation.isPending ? (
                      <CircularProgress size={16} color="inherit" />
                    ) : (
                      <CloudUploadIcon />
                    )
                  }
                  onClick={handleUploadLogo}
                  disabled={!logoFile || logoMutation.isPending}
                  sx={{ alignSelf: 'flex-start' }}
                >
                  Subir logo
                </Button>

                {logoUploadMsg && (
                  <Alert severity={logoUploadMsg.type} sx={{ py: 0.5 }}>
                    {logoUploadMsg.text}
                  </Alert>
                )}
              </Stack>
            </Box>
          </Stack>
        </Paper>
      )}

      {/* Create / Edit Dialog */}
      <Dialog
        open={modalOpen}
        onClose={() => !isMutating && setModalOpen(false)}
        maxWidth="sm"
        fullWidth
      >
        <DialogTitle>
          {editingRow
            ? `Editar transportadora: ${editingRow.nombre}`
            : 'Nueva transportadora'}
        </DialogTitle>
        <DialogContent dividers>
          <Stack spacing={2} mt={0.5}>
            {(createMutation.isError || editMutation.isError) && (
              <Alert severity="error">Error al guardar. Intentar nuevamente.</Alert>
            )}

            <Controller
              name="nombre"
              control={control}
              rules={{ required: 'El nombre es requerido' }}
              render={({ field }) => (
                <TextField
                  {...field}
                  label="Nombre *"
                  fullWidth
                  size="small"
                  error={!!errors.nombre}
                  helperText={errors.nombre?.message}
                />
              )}
            />
            <Controller
              name="domicilio"
              control={control}
              render={({ field }) => (
                <TextField {...field} label="Domicilio" fullWidth size="small" />
              )}
            />
            <Controller
              name="rol"
              control={control}
              render={({ field }) => (
                <TextField
                  {...field}
                  label="Rol del Contribuyente"
                  fullWidth
                  size="small"
                />
              )}
            />
            <Controller
              name="localidad"
              control={control}
              render={({ field }) => (
                <LocalidadAutocomplete
                  value={field.value}
                  onChange={field.onChange}
                  label="Localidad"
                  size="small"
                />
              )}
            />
            <Stack direction="row" spacing={2}>
              <Controller
                name="prefijo"
                control={control}
                render={({ field }) => (
                  <TextField {...field} label="Prefijo" size="small" sx={{ flex: 1 }} />
                )}
              />
              <Controller
                name="numerador"
                control={control}
                rules={{ required: 'El numerador es requerido' }}
                render={({ field }) => (
                  <TextField
                    {...field}
                    label="Numerador *"
                    size="small"
                    sx={{ flex: 1 }}
                    error={!!errors.numerador}
                    helperText={errors.numerador?.message}
                  />
                )}
              />
            </Stack>
            <Controller
              name="imagen"
              control={control}
              render={({ field }) => (
                <TextField
                  {...field}
                  label="Nombre de imagen (legacy)"
                  fullWidth
                  size="small"
                  placeholder="archivo.png"
                  helperText="Nombre de archivo en classpath (opcional)"
                />
              )}
            />
          </Stack>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setModalOpen(false)} disabled={isMutating}>
            Cancelar
          </Button>
          <Button
            variant="contained"
            onClick={handleSubmit(onSubmit)}
            disabled={isMutating}
            startIcon={
              isMutating ? <CircularProgress size={16} color="inherit" /> : undefined
            }
          >
            {editingRow ? 'Guardar cambios' : 'Crear'}
          </Button>
        </DialogActions>
      </Dialog>

      {/* Delete confirm */}
      <ConfirmDialog
        open={!!deleteTarget}
        title="Eliminar transportadora"
        message={`¿Confirma eliminar "${deleteTarget?.nombre}"? Esta acción no se puede deshacer.`}
        confirmLabel="Eliminar"
        onConfirm={() => deleteTarget && deleteMutation.mutate(deleteTarget.id)}
        onCancel={() => setDeleteTarget(null)}
        loading={deleteMutation.isPending}
      />
    </Box>
  )
}

// ── Logo preview sub-component ────────────────────────────────────────────────
function LogoPreview({ id, cacheBust }: { id: number; cacheBust: number }) {
  const [hasError, setHasError] = useState(false)

  // Reset error state when the transportadora or cacheBust changes so a
  // freshly uploaded logo (or switching rows) always retries the request.
  useEffect(() => {
    setHasError(false)
  }, [id, cacheBust])

  const src = getLogoUrl(id, cacheBust)

  return (
    <Box
      sx={{
        width: 180,
        height: 100,
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        border: '1px dashed',
        borderColor: 'divider',
        borderRadius: 1,
        overflow: 'hidden',
        bgcolor: '#f8f9fa',
      }}
    >
      {hasError ? (
        <Typography variant="caption" color="text.secondary">
          Sin logo cargado
        </Typography>
      ) : (
        <Box
          component="img"
          key={src}
          src={src}
          alt="Logo"
          sx={{ maxWidth: '100%', maxHeight: '100%', objectFit: 'contain' }}
          onError={() => setHasError(true)}
          onLoad={() => setHasError(false)}
        />
      )}
    </Box>
  )
}
