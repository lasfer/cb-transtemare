import { useState, useCallback } from 'react'
import {
  Alert,
  Box,
  Button,
  CircularProgress,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  IconButton,
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
  fetchTerminales,
  crearTerminal,
  editarTerminal,
  eliminarTerminal,
} from '../api/terminales'
import type { Terminal, TerminalFormData } from '../types/terminal'
import ConfirmDialog from '../components/ConfirmDialog'

const DEFAULT_VALUES: TerminalFormData = {
  nombre: '',
}

export default function TerminalesPage() {
  const queryClient = useQueryClient()
  const [paginationModel, setPaginationModel] = useState<GridPaginationModel>({
    page: 0,
    pageSize: 15,
  })
  const [sortModel, setSortModel] = useState<GridSortModel>([])
  const [modalOpen, setModalOpen] = useState(false)
  const [editingRow, setEditingRow] = useState<Terminal | null>(null)
  const [deleteTarget, setDeleteTarget] = useState<Terminal | null>(null)

  const {
    control,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<TerminalFormData>({ defaultValues: DEFAULT_VALUES })

  const { data, isLoading, isError } = useQuery({
    queryKey: ['terminales', paginationModel.page, paginationModel.pageSize, sortModel],
    queryFn: () =>
      fetchTerminales({
        page: paginationModel.page + 1,
        rows: paginationModel.pageSize,
        sidx: sortModel[0]?.field,
        sord: sortModel[0]?.sort as 'asc' | 'desc' | undefined,
      }),
  })

  const invalidate = useCallback(() => {
    queryClient.invalidateQueries({ queryKey: ['terminales'] })
  }, [queryClient])

  const createMutation = useMutation({
    mutationFn: crearTerminal,
    onSuccess: () => {
      invalidate()
      setModalOpen(false)
    },
  })

  const editMutation = useMutation({
    mutationFn: ({ id, form }: { id: number; form: TerminalFormData }) =>
      editarTerminal(id, form),
    onSuccess: () => {
      invalidate()
      setModalOpen(false)
    },
  })

  const deleteMutation = useMutation({
    mutationFn: eliminarTerminal,
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

  const openEdit = (row: Terminal) => {
    setEditingRow(row)
    reset({ nombre: row.nombre })
    setModalOpen(true)
  }

  const onSubmit = (form: TerminalFormData) => {
    if (editingRow) {
      editMutation.mutate({ id: editingRow.id, form })
    } else {
      createMutation.mutate(form)
    }
  }

  const isMutating = createMutation.isPending || editMutation.isPending

  const rows = (data?.gridModel ?? []).map((r) => ({
    ...r,
    id: typeof r.id === 'string' ? parseInt(r.id, 10) : r.id,
  }))

  const columns: GridColDef<Terminal>[] = [
    { field: 'id', headerName: 'ID', width: 60, type: 'number' },
    { field: 'nombre', headerName: 'Nombre', flex: 1, minWidth: 200 },
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
          Terminales
        </Typography>
        <Button variant="contained" startIcon={<AddIcon />} onClick={openCreate}>
          Agregar
        </Button>
      </Stack>

      {isError && (
        <Alert severity="error" sx={{ mb: 2 }}>
          Error al cargar los terminales. Verificar que el servidor esté activo.
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

      <Dialog
        open={modalOpen}
        onClose={() => !isMutating && setModalOpen(false)}
        maxWidth="sm"
        fullWidth
      >
        <DialogTitle>
          {editingRow ? `Editar terminal: ${editingRow.nombre}` : 'Nuevo terminal'}
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

      <ConfirmDialog
        open={!!deleteTarget}
        title="Eliminar terminal"
        message={`¿Confirma eliminar "${deleteTarget?.nombre}"? Esta acción no se puede deshacer.`}
        confirmLabel="Eliminar"
        onConfirm={() => deleteTarget && deleteMutation.mutate(deleteTarget.id)}
        onCancel={() => setDeleteTarget(null)}
        loading={deleteMutation.isPending}
      />
    </Box>
  )
}
