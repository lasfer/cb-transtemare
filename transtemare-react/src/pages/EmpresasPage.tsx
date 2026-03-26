import { useState, useCallback } from 'react'
import {
  Box,
  Button,
  Chip,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  FormControl,
  FormHelperText,
  IconButton,
  InputLabel,
  MenuItem,
  Select,
  Stack,
  TextField,
  Tooltip,
  Typography,
  Alert,
  CircularProgress,
} from '@mui/material'
import AddIcon from '@mui/icons-material/Add'
import EditIcon from '@mui/icons-material/Edit'
import DeleteIcon from '@mui/icons-material/Delete'
import { DataGrid } from '@mui/x-data-grid'
import type { GridColDef, GridPaginationModel, GridSortModel } from '@mui/x-data-grid'
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { useForm, Controller } from 'react-hook-form'
import { fetchEmpresas, crearEmpresa, editarEmpresa, eliminarEmpresa } from '../api/empresas'
import type { Empresa, EmpresaFormData } from '../types/empresa'
import { EMPRESA_TIPOS } from '../types/empresa'
import ConfirmDialog from '../components/ConfirmDialog'

const TIPO_COLORS: Record<string, 'default' | 'primary' | 'secondary' | 'success' | 'warning'> = {
  Empresa: 'primary',
  Cliente: 'success',
  Despachante: 'secondary',
  'Agencia Maritima': 'warning',
}

const DEFAULT_VALUES: EmpresaFormData = {
  nombre: '',
  domicilio: '',
  rol: '',
  tipo: 'Empresa',
  ciudad: '',
  nombreCorto: '',
  codigo: '',
}

export default function EmpresasPage() {
  const queryClient = useQueryClient()

  // Pagination & sort state
  const [paginationModel, setPaginationModel] = useState<GridPaginationModel>({
    page: 0,
    pageSize: 15,
  })
  const [sortModel, setSortModel] = useState<GridSortModel>([])
  const [search, setSearch] = useState('')
  const [searchInput, setSearchInput] = useState('')

  // Modal state
  const [modalOpen, setModalOpen] = useState(false)
  const [editingRow, setEditingRow] = useState<Empresa | null>(null)

  // Delete confirm state
  const [deleteTarget, setDeleteTarget] = useState<Empresa | null>(null)

  // Form
  const {
    control,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<EmpresaFormData>({ defaultValues: DEFAULT_VALUES })

  // Query
  const { data, isLoading, isError } = useQuery({
    queryKey: [
      'empresas',
      paginationModel.page,
      paginationModel.pageSize,
      sortModel,
      search,
    ],
    queryFn: () =>
      fetchEmpresas({
        page: paginationModel.page + 1,
        rows: paginationModel.pageSize,
        sidx: sortModel[0]?.field,
        sord: sortModel[0]?.sort as 'asc' | 'desc' | undefined,
        searchField: search ? 'nombre' : undefined,
        searchString: search || undefined,
        searchOper: search ? 'cn' : undefined,
      }),
  })

  // Mutations
  const invalidate = useCallback(() => {
    queryClient.invalidateQueries({ queryKey: ['empresas'] })
  }, [queryClient])

  const createMutation = useMutation({
    mutationFn: crearEmpresa,
    onSuccess: () => { invalidate(); setModalOpen(false) },
  })

  const editMutation = useMutation({
    mutationFn: ({ id, form }: { id: number; form: EmpresaFormData }) =>
      editarEmpresa(id, form),
    onSuccess: () => { invalidate(); setModalOpen(false) },
  })

  const deleteMutation = useMutation({
    mutationFn: eliminarEmpresa,
    onSuccess: () => { invalidate(); setDeleteTarget(null) },
  })

  // Handlers
  const openCreate = () => {
    setEditingRow(null)
    reset(DEFAULT_VALUES)
    setModalOpen(true)
  }

  const openEdit = (row: Empresa) => {
    setEditingRow(row)
    reset({
      nombre: row.nombre,
      domicilio: row.domicilio,
      rol: row.rol,
      tipo: row.tipo,
      ciudad: row.ciudad,
      nombreCorto: row.nombreCorto,
      codigo: row.codigo,
    })
    setModalOpen(true)
  }

  const onSubmit = (form: EmpresaFormData) => {
    if (editingRow) {
      editMutation.mutate({ id: editingRow.id, form })
    } else {
      createMutation.mutate(form)
    }
  }

  const isMutating = createMutation.isPending || editMutation.isPending

  // Columns
  const columns: GridColDef<Empresa>[] = [
    { field: 'id', headerName: 'ID', width: 60, type: 'number' },
    { field: 'nombre', headerName: 'Nombre', flex: 1.5, minWidth: 160 },
    { field: 'domicilio', headerName: 'Domicilio', flex: 1.5, minWidth: 140 },
    { field: 'rol', headerName: 'Rol Contribuyente', flex: 1, minWidth: 130 },
    {
      field: 'tipo',
      headerName: 'Tipo',
      width: 150,
      renderCell: ({ value }) => (
        <Chip
          label={value}
          size="small"
          color={TIPO_COLORS[value] ?? 'default'}
          variant="outlined"
        />
      ),
    },
    { field: 'pais', headerName: 'País', width: 100 },
    { field: 'ciudad', headerName: 'Ciudad', flex: 1, minWidth: 110 },
    { field: 'nombreCorto', headerName: 'Cod. Corto', width: 100 },
    { field: 'codigo', headerName: 'Código', width: 90 },
    {
      field: '_actions',
      headerName: 'Acciones',
      width: 100,
      sortable: false,
      filterable: false,
      renderCell: ({ row }) => (
        <Stack direction="row" spacing={0.5}>
          <Tooltip title="Editar">
            <IconButton size="small" color="primary" onClick={() => openEdit(row)}>
              <EditIcon fontSize="small" />
            </IconButton>
          </Tooltip>
          <Tooltip title="Eliminar">
            <IconButton
              size="small"
              color="error"
              onClick={() => setDeleteTarget(row)}
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
          Empresas
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
          <Button
            variant="contained"
            startIcon={<AddIcon />}
            onClick={openCreate}
          >
            Agregar
          </Button>
        </Stack>
      </Stack>

      {isError && (
        <Alert severity="error" sx={{ mb: 2 }}>
          Error al cargar las empresas. Verificar que el servidor esté activo.
        </Alert>
      )}

      {/* DataGrid */}
      <Box sx={{ height: 520, width: '100%', bgcolor: 'background.paper', borderRadius: 2, boxShadow: 1 }}>
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
          disableRowSelectionOnClick
          density="compact"
          getRowId={(row) => row.id}
          sx={{ border: 0 }}
        />
      </Box>

      {/* Create / Edit Dialog */}
      <Dialog
        open={modalOpen}
        onClose={() => !isMutating && setModalOpen(false)}
        maxWidth="sm"
        fullWidth
      >
        <DialogTitle>
          {editingRow ? `Editar empresa: ${editingRow.nombre}` : 'Nueva empresa'}
        </DialogTitle>
        <DialogContent dividers>
          <Stack spacing={2} mt={0.5}>
            {(createMutation.isError || editMutation.isError) && (
              <Alert severity="error">Error al guardar. Intentar nuevamente.</Alert>
            )}

            <Controller
              name="nombre"
              control={control}
              rules={{ required: 'El nombre es requerido', maxLength: { value: 150, message: 'Máximo 150 caracteres' } }}
              render={({ field }) => (
                <TextField
                  {...field}
                  label="Nombre *"
                  fullWidth
                  size="small"
                  error={!!errors.nombre}
                  helperText={errors.nombre?.message}
                  inputProps={{ maxLength: 150 }}
                />
              )}
            />

            <Controller
              name="domicilio"
              control={control}
              rules={{ required: 'El domicilio es requerido', maxLength: { value: 250, message: 'Máximo 250 caracteres' } }}
              render={({ field }) => (
                <TextField
                  {...field}
                  label="Domicilio *"
                  fullWidth
                  size="small"
                  error={!!errors.domicilio}
                  helperText={errors.domicilio?.message}
                  inputProps={{ maxLength: 250 }}
                />
              )}
            />

            <Controller
              name="rol"
              control={control}
              rules={{ required: 'El rol es requerido', maxLength: { value: 100, message: 'Máximo 100 caracteres' } }}
              render={({ field }) => (
                <TextField
                  {...field}
                  label="Rol del Contribuyente *"
                  fullWidth
                  size="small"
                  error={!!errors.rol}
                  helperText={errors.rol?.message}
                  inputProps={{ maxLength: 100 }}
                />
              )}
            />

            <Controller
              name="tipo"
              control={control}
              rules={{ required: 'El tipo es requerido' }}
              render={({ field }) => (
                <FormControl fullWidth size="small" error={!!errors.tipo}>
                  <InputLabel>Tipo *</InputLabel>
                  <Select {...field} label="Tipo *">
                    {EMPRESA_TIPOS.map((t) => (
                      <MenuItem key={t} value={t}>{t}</MenuItem>
                    ))}
                  </Select>
                  {errors.tipo && (
                    <FormHelperText>{errors.tipo.message}</FormHelperText>
                  )}
                </FormControl>
              )}
            />

            <Controller
              name="ciudad"
              control={control}
              render={({ field }) => (
                <TextField
                  {...field}
                  label="Ciudad / Localidad"
                  fullWidth
                  size="small"
                  placeholder="Nombre exacto de la localidad"
                />
              )}
            />

            <Stack direction="row" spacing={2}>
              <Controller
                name="nombreCorto"
                control={control}
                rules={{ maxLength: { value: 6, message: 'Máximo 6 caracteres' } }}
                render={({ field }) => (
                  <TextField
                    {...field}
                    label="Nombre Corto"
                    size="small"
                    error={!!errors.nombreCorto}
                    helperText={errors.nombreCorto?.message}
                    inputProps={{ maxLength: 6 }}
                    sx={{ flex: 1 }}
                  />
                )}
              />
              <Controller
                name="codigo"
                control={control}
                rules={{ maxLength: { value: 8, message: 'Máximo 8 caracteres' } }}
                render={({ field }) => (
                  <TextField
                    {...field}
                    label="Código"
                    size="small"
                    error={!!errors.codigo}
                    helperText={errors.codigo?.message}
                    inputProps={{ maxLength: 8 }}
                    sx={{ flex: 1 }}
                  />
                )}
              />
            </Stack>
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
            startIcon={isMutating ? <CircularProgress size={16} color="inherit" /> : undefined}
          >
            {editingRow ? 'Guardar cambios' : 'Crear'}
          </Button>
        </DialogActions>
      </Dialog>

      {/* Delete confirm */}
      <ConfirmDialog
        open={!!deleteTarget}
        title="Eliminar empresa"
        message={`¿Confirma eliminar "${deleteTarget?.nombre}"? Esta acción no se puede deshacer.`}
        confirmLabel="Eliminar"
        onConfirm={() => deleteTarget && deleteMutation.mutate(deleteTarget.id)}
        onCancel={() => setDeleteTarget(null)}
        loading={deleteMutation.isPending}
      />
    </Box>
  )
}
