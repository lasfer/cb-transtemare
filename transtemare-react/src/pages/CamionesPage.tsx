import { useState, useCallback } from 'react'
import {
  Alert,
  Box,
  Button,
  Chip,
  CircularProgress,
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
} from '@mui/material'
import AddIcon from '@mui/icons-material/Add'
import EditIcon from '@mui/icons-material/Edit'
import DeleteIcon from '@mui/icons-material/Delete'
import { DataGrid } from '@mui/x-data-grid'
import type { GridColDef, GridPaginationModel, GridSortModel } from '@mui/x-data-grid'
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { useForm, Controller } from 'react-hook-form'
import {
  fetchCamiones,
  crearCamion,
  editarCamion,
  eliminarCamion,
} from '../api/camiones'
import type { Camion, CamionFormData } from '../types/camion'
import { CAMION_TIPOS } from '../types/camion'
import ConfirmDialog from '../components/ConfirmDialog'

const DEFAULT_VALUES: CamionFormData = {
  matricula: '',
  capacidad: '',
  marca: '',
  anio: '',
  tipo: 'Camion',
  numeroPoliza: '',
  vencimientoPoliza: '',
}

function formatDateForBackend(value: string): string {
  if (!value?.trim()) return ''
  const d = new Date(value)
  if (isNaN(d.getTime())) return value
  const day = String(d.getDate()).padStart(2, '0')
  const month = String(d.getMonth() + 1).padStart(2, '0')
  const year = d.getFullYear()
  return `${day}/${month}/${year}`
}

export default function CamionesPage() {
  const queryClient = useQueryClient()
  const [paginationModel, setPaginationModel] = useState<GridPaginationModel>({
    page: 0,
    pageSize: 15,
  })
  const [sortModel, setSortModel] = useState<GridSortModel>([])
  const [search, setSearch] = useState('')
  const [searchInput, setSearchInput] = useState('')
  const [modalOpen, setModalOpen] = useState(false)
  const [editingRow, setEditingRow] = useState<Camion | null>(null)
  const [deleteTarget, setDeleteTarget] = useState<Camion | null>(null)

  const {
    control,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<CamionFormData>({ defaultValues: DEFAULT_VALUES })

  const { data, isLoading, isError } = useQuery({
    queryKey: [
      'camiones',
      paginationModel.page,
      paginationModel.pageSize,
      sortModel,
      search,
    ],
    queryFn: () =>
      fetchCamiones({
        page: paginationModel.page + 1,
        rows: paginationModel.pageSize,
        sidx: sortModel[0]?.field,
        sord: sortModel[0]?.sort as 'asc' | 'desc' | undefined,
        searchField: search ? 'matricula' : undefined,
        searchString: search || undefined,
        searchOper: search ? 'cn' : undefined,
      }),
  })

  const invalidate = useCallback(() => {
    queryClient.invalidateQueries({ queryKey: ['camiones'] })
  }, [queryClient])

  const createMutation = useMutation({
    mutationFn: crearCamion,
    onSuccess: () => {
      invalidate()
      setModalOpen(false)
    },
  })

  const editMutation = useMutation({
    mutationFn: ({ id, form }: { id: number; form: CamionFormData }) =>
      editarCamion(id, form),
    onSuccess: () => {
      invalidate()
      setModalOpen(false)
    },
  })

  const deleteMutation = useMutation({
    mutationFn: eliminarCamion,
    onSuccess: () => {
      invalidate()
      setDeleteTarget(null)
    },
  })

  const openCreate = () => {
    setEditingRow(null)
    reset(DEFAULT_VALUES)
    setModalOpen(true)
  }

  const openEdit = (row: Camion & { id?: string | number }) => {
    setEditingRow(row as Camion)
    reset({
      matricula: row.matricula,
      capacidad: row.capacidad,
      marca: row.marca,
      anio: row.anio,
      tipo: row.tipo,
      numeroPoliza: row.numeroPoliza ?? '',
      vencimientoPoliza: row.vencimientoPoliza ?? '',
    })
    setModalOpen(true)
  }

  const onSubmit = (form: CamionFormData) => {
    if (editingRow) {
      const id = typeof editingRow.id === 'string' ? parseInt(editingRow.id, 10) : editingRow.id
      editMutation.mutate({ id, form })
    } else {
      createMutation.mutate(form)
    }
  }

  const isMutating = createMutation.isPending || editMutation.isPending

  const rows = data?.gridModel ?? []

  const columns: GridColDef[] = [
    { field: 'id', headerName: 'ID', width: 60 },
    { field: 'matricula', headerName: 'Matrícula', flex: 1, minWidth: 100 },
    { field: 'capacidad', headerName: 'Capacidad', width: 100 },
    { field: 'marca', headerName: 'Marca', flex: 1, minWidth: 120 },
    { field: 'anio', headerName: 'Año', width: 70 },
    {
      field: 'tipo',
      headerName: 'Tipo',
      width: 100,
      renderCell: ({ value }) => (
        <Chip
          label={value ?? '—'}
          size="small"
          color={value === 'Camion' ? 'primary' : 'secondary'}
          variant="outlined"
        />
      ),
    },
    { field: 'numeroPoliza', headerName: 'Nro. Póliza', width: 110 },
    {
      field: 'vencimientoPoliza',
      headerName: 'Venc. Póliza',
      width: 110,
      valueFormatter: (params) => {
        const v = params?.value
        if (v == null || v === '') return '—'
        const d = new Date(v as string | number)
        return isNaN(d.getTime()) ? String(v) : d.toLocaleDateString('es-AR')
      },
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
      <Stack
        direction={{ xs: 'column', sm: 'row' }}
        alignItems={{ sm: 'center' }}
        justifyContent="space-between"
        spacing={2}
        mb={2}
      >
        <Typography variant="h5" color="primary">
          Camiones
        </Typography>
        <Stack direction="row" spacing={1}>
          <TextField
            size="small"
            placeholder="Buscar por matrícula..."
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
          Error al cargar los camiones. Verificar que el servidor esté activo.
        </Alert>
      )}

      <Box
        sx={{
          height: 520,
          width: '100%',
          bgcolor: 'background.paper',
          borderRadius: 2,
          boxShadow: 1,
        }}
      >
        <DataGrid
          rows={rows}
          columns={columns}
          rowCount={Number(data?.records) || 0}
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

      <Dialog
        open={modalOpen}
        onClose={() => !isMutating && setModalOpen(false)}
        maxWidth="sm"
        fullWidth
      >
        <DialogTitle>
          {editingRow ? `Editar camión: ${editingRow.matricula}` : 'Nuevo camión'}
        </DialogTitle>
        <DialogContent dividers>
          <Stack spacing={2} mt={0.5}>
            {(createMutation.isError || editMutation.isError) && (
              <Alert severity="error">Error al guardar. Intentar nuevamente.</Alert>
            )}

            <Controller
              name="matricula"
              control={control}
              rules={{ required: 'La matrícula es requerida' }}
              render={({ field }) => (
                <TextField
                  {...field}
                  label="Matrícula *"
                  fullWidth
                  size="small"
                  error={!!errors.matricula}
                  helperText={errors.matricula?.message}
                />
              )}
            />
            <Controller
              name="capacidad"
              control={control}
              rules={{ required: 'La capacidad es requerida' }}
              render={({ field }) => (
                <TextField
                  {...field}
                  label="Capacidad *"
                  fullWidth
                  size="small"
                  error={!!errors.capacidad}
                  helperText={errors.capacidad?.message}
                />
              )}
            />
            <Controller
              name="marca"
              control={control}
              rules={{ required: 'La marca es requerida', maxLength: { value: 50, message: 'Máximo 50 caracteres' } }}
              render={({ field }) => (
                <TextField
                  {...field}
                  label="Marca *"
                  fullWidth
                  size="small"
                  error={!!errors.marca}
                  helperText={errors.marca?.message}
                  inputProps={{ maxLength: 50 }}
                />
              )}
            />
            <Stack direction="row" spacing={2}>
              <Controller
                name="anio"
                control={control}
                rules={{
                  required: 'El año es requerido',
                  min: { value: 1, message: 'Mínimo 1' },
                  max: { value: 9999, message: 'Máximo 9999' },
                }}
                render={({ field }) => (
                  <TextField
                    {...field}
                    label="Año *"
                    type="number"
                    size="small"
                    sx={{ flex: 1 }}
                    error={!!errors.anio}
                    helperText={errors.anio?.message}
                    inputProps={{ min: 1, max: 9999 }}
                  />
                )}
              />
              <Controller
                name="tipo"
                control={control}
                rules={{ required: 'El tipo es requerido' }}
                render={({ field }) => (
                  <FormControl fullWidth size="small" error={!!errors.tipo} sx={{ flex: 1 }}>
                    <InputLabel>Tipo *</InputLabel>
                    <Select {...field} label="Tipo *">
                      {CAMION_TIPOS.map((t) => (
                        <MenuItem key={t} value={t}>
                          {t}
                        </MenuItem>
                      ))}
                    </Select>
                    {errors.tipo && (
                      <FormHelperText>{errors.tipo.message}</FormHelperText>
                    )}
                  </FormControl>
                )}
              />
            </Stack>
            <Controller
              name="numeroPoliza"
              control={control}
              render={({ field }) => (
                <TextField
                  {...field}
                  label="Nro. Póliza"
                  fullWidth
                  size="small"
                  inputProps={{ maxLength: 16 }}
                />
              )}
            />
            <Controller
              name="vencimientoPoliza"
              control={control}
              render={({ field }) => (
                <TextField
                  {...field}
                  label="Venc. Póliza (dd/mm/aaaa)"
                  fullWidth
                  size="small"
                  placeholder="dd/mm/aaaa"
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
            onClick={handleSubmit((form) => {
              const payload = {
                ...form,
                vencimientoPoliza: formatDateForBackend(form.vencimientoPoliza),
              }
              if (editingRow) {
                editMutation.mutate({ id: editingRow.id, form: payload })
              } else {
                createMutation.mutate(payload)
              }
            })}
            disabled={isMutating}
            startIcon={isMutating ? <CircularProgress size={16} color="inherit" /> : undefined}
          >
            {editingRow ? 'Guardar cambios' : 'Crear'}
          </Button>
        </DialogActions>
      </Dialog>

      <ConfirmDialog
        open={!!deleteTarget}
        title="Eliminar camión"
        message={`¿Confirma eliminar el camión "${deleteTarget?.matricula}"? Esta acción no se puede deshacer.`}
        confirmLabel="Eliminar"
        onConfirm={() => {
          if (!deleteTarget) return
          const id = typeof deleteTarget.id === 'string' ? parseInt(deleteTarget.id, 10) : deleteTarget.id
          deleteMutation.mutate(id)
        }}
        onCancel={() => setDeleteTarget(null)}
        loading={deleteMutation.isPending}
      />
    </Box>
  )
}
