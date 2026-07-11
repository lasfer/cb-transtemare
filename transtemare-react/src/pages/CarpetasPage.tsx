import { useState } from 'react'
import {
  Alert,
  Box,
  Button,
  Card,
  CardContent,
  CircularProgress,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  IconButton,
  Stack,
  TextField,
  Typography,
} from '@mui/material'
import AddIcon from '@mui/icons-material/Add'
import EditIcon from '@mui/icons-material/Edit'
import CloseIcon from '@mui/icons-material/Close'
import PictureAsPdfIcon from '@mui/icons-material/PictureAsPdf'
import HistoryIcon from '@mui/icons-material/History'
import { DataGrid } from '@mui/x-data-grid'
import type { GridColDef, GridPaginationModel, GridSortModel } from '@mui/x-data-grid'
import { useMutation, useQuery } from '@tanstack/react-query'
import { useNavigate } from 'react-router-dom'
import { Controller, useForm } from 'react-hook-form'
import {
  crearNuevaCarpeta,
  fetchCarpetas,
  pasarCarpetaAHistorico,
  type CarpetaRow,
} from '../api/carpetas'
import LegacyAutocompleteField from '../components/LegacyAutocompleteField'

type NuevaCarpetaForm = {
  transportadora: string
  cantidadMIC: number
}

const DEFAULT_NEW_FORM: NuevaCarpetaForm = {
  transportadora: '',
  cantidadMIC: 1,
}

export default function CarpetasPage() {
  const navigate = useNavigate()
  const [paginationModel, setPaginationModel] = useState<GridPaginationModel>({
    page: 0,
    pageSize: 30,
  })
  const [sortModel, setSortModel] = useState<GridSortModel>([])
  const [searchInput, setSearchInput] = useState('')
  const [search, setSearch] = useState('')
  const [newDialogOpen, setNewDialogOpen] = useState(false)
  const [selectedRow, setSelectedRow] = useState<CarpetaRow | null>(null)
  const [actionsOpen, setActionsOpen] = useState(false)

  const {
    control,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<NuevaCarpetaForm>({ defaultValues: DEFAULT_NEW_FORM })

  const { data, isLoading, isError } = useQuery({
    queryKey: ['carpetas', paginationModel.page, paginationModel.pageSize, sortModel, search],
    queryFn: () =>
      fetchCarpetas({
        page: paginationModel.page + 1,
        rows: paginationModel.pageSize,
        sidx: sortModel[0]?.field,
        sord: sortModel[0]?.sort as 'asc' | 'desc' | undefined,
        searchField: search ? 'numeroCarpeta' : undefined,
        searchString: search || undefined,
        searchOper: search ? 'eq' : undefined,
      }),
  })

  const createMutation = useMutation({
    mutationFn: ({ transportadora, cantidadMIC }: NuevaCarpetaForm) =>
      crearNuevaCarpeta(transportadora, cantidadMIC),
    onSuccess: (result) => {
      if (result?.ok && result?.idCarpeta) {
        setNewDialogOpen(false)
        reset(DEFAULT_NEW_FORM)
        navigate(`/carpetas/${result.idCarpeta}`)
      }
    },
  })

  const historicoMutation = useMutation({
    mutationFn: (idCarpeta: string) => pasarCarpetaAHistorico(idCarpeta),
  })

  const rows = data?.gridModel ?? []

  const columns: GridColDef<CarpetaRow>[] = [
    { field: 'numeroCarpeta', headerName: 'Número', width: 90 },
    { field: 'referenciaDestino', headerName: 'Nro. Paraguay', width: 150 },
    {
      field: 'numeroContenedorFormateado',
      headerName: 'Contenedor',
      width: 150,
      valueGetter: (_value, row) => row.numeroContenedorFormateado ?? row.numeroContenedor ?? '—',
    },
    { field: 'transportadora', headerName: 'Transportadora', flex: 1, minWidth: 160 },
    { field: 'terminal', headerName: 'Terminal', width: 120 },
    { field: 'agenciaMaritima', headerName: 'Agencia', width: 160 },
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
          Carpetas
        </Typography>
        <Stack direction="row" spacing={1}>
          <TextField
            size="small"
            placeholder="Buscar por número de carpeta..."
            value={searchInput}
            onChange={(e) => setSearchInput(e.target.value)}
            onKeyDown={(e) => {
              if (e.key === 'Enter') {
                setSearch(searchInput.trim())
                setPaginationModel((p) => ({ ...p, page: 0 }))
              }
            }}
            sx={{ width: 240 }}
          />
          <Button
            variant="outlined"
            size="small"
            onClick={() => {
              setSearch(searchInput.trim())
              setPaginationModel((p) => ({ ...p, page: 0 }))
            }}
          >
            Buscar
          </Button>
          <Button
            variant="contained"
            startIcon={<AddIcon />}
            onClick={() => setNewDialogOpen(true)}
          >
            Nueva carpeta
          </Button>
        </Stack>
      </Stack>

      <Stack direction={{ xs: 'column', lg: 'row' }} spacing={2} alignItems="stretch">
        <Box
          sx={{
            height: 560,
            flex: 1,
            bgcolor: 'background.paper',
            borderRadius: 2,
            boxShadow: 1,
            minWidth: 0,
          }}
        >
          <DataGrid
            rows={rows}
            columns={columns}
            getRowId={(row) => row.numeroCarpeta}
            rowCount={Number(data?.records) || 0}
            loading={isLoading}
            paginationMode="server"
            sortingMode="server"
            paginationModel={paginationModel}
            onPaginationModelChange={setPaginationModel}
            sortModel={sortModel}
            onSortModelChange={setSortModel}
            pageSizeOptions={[15, 30, 60]}
            onRowClick={(params) => {
              setSelectedRow(params.row)
              setActionsOpen(true)
            }}
            getRowClassName={(params) =>
              selectedRow?.numeroCarpeta === params.row.numeroCarpeta ? 'carpeta-row-selected' : ''
            }
            density="compact"
            sx={{
              border: 0,
              '& .carpeta-row-selected': {
                backgroundColor: 'primary.light',
              },
              '& .carpeta-row-selected:hover': {
                backgroundColor: 'primary.light',
              },
            }}
          />
        </Box>

        {actionsOpen && selectedRow && (
          <Card sx={{ width: { xs: '100%', lg: 320 }, flexShrink: 0, border: 1, borderColor: 'primary.main' }}>
          <CardContent>
            <Stack
              direction="row"
              alignItems="center"
              justifyContent="space-between"
              sx={{
                bgcolor: 'primary.main',
                color: 'primary.contrastText',
                borderRadius: 1,
                px: 1,
                py: 0.5,
                mb: 2,
              }}
            >
              <Typography variant="subtitle2">Acciones sobre carpeta</Typography>
              <IconButton
                size="small"
                onClick={() => setActionsOpen(false)}
                sx={{ color: 'primary.contrastText' }}
              >
                <CloseIcon fontSize="small" />
              </IconButton>
            </Stack>
                <Typography variant="body2" color="text.secondary" sx={{ mb: 2 }}>
                  Carpeta seleccionada: <b>{selectedRow.numeroCarpeta}</b>
                </Typography>
                <Stack spacing={1}>
                  <Button
                    fullWidth
                    variant="outlined"
                    startIcon={<EditIcon />}
                    onClick={() => navigate(`/carpetas/${selectedRow.numeroCarpeta}`)}
                  >
                    Editar carpeta
                  </Button>
                  <Button
                    fullWidth
                    variant="outlined"
                    startIcon={<PictureAsPdfIcon />}
                    onClick={() => window.open(`/api/CRT.action?id=${selectedRow.numeroCarpeta}`, '_blank')}
                  >
                    Imprimir CRT
                  </Button>
                  <Button
                    fullWidth
                    variant="outlined"
                    startIcon={<PictureAsPdfIcon />}
                    onClick={() => window.open(`/api/MICDTA.action?id=${selectedRow.numeroCarpeta}`, '_blank')}
                  >
                    Imprimir MICDTA
                  </Button>
                  <Button
                    fullWidth
                    variant="outlined"
                    startIcon={<PictureAsPdfIcon />}
                    onClick={() =>
                      window.open(`/api/MICDTA.action?id=${selectedRow.numeroCarpeta}&camionSust=true`, '_blank')
                    }
                  >
                    MICDTA Camión Sust.
                  </Button>
                  <Button
                    fullWidth
                    variant="outlined"
                    startIcon={<PictureAsPdfIcon />}
                    onClick={() => window.open(`/api/SABANA.action?id=${selectedRow.numeroCarpeta}`, '_blank')}
                  >
                    Imprimir SABANA
                  </Button>
                  <Button
                    fullWidth
                    color="warning"
                    variant="contained"
                    startIcon={historicoMutation.isPending ? <CircularProgress size={16} color="inherit" /> : <HistoryIcon />}
                    disabled={historicoMutation.isPending}
                    onClick={() => historicoMutation.mutate(selectedRow.numeroCarpeta)}
                  >
                    Pasar a histórico
                  </Button>
                </Stack>
            {historicoMutation.isSuccess && (
              <Alert severity="success" sx={{ mt: 2 }}>
                Carpeta enviada a histórico.
              </Alert>
            )}
            {historicoMutation.isError && (
              <Alert severity="error" sx={{ mt: 2 }}>
                Error al pasar carpeta a histórico.
              </Alert>
            )}
          </CardContent>
        </Card>
        )}
      </Stack>

      {isError && (
        <Alert severity="error" sx={{ mb: 2, mt: 2 }}>
          Error al cargar carpetas.
        </Alert>
      )}

      <Dialog
        open={newDialogOpen}
        onClose={() => !createMutation.isPending && setNewDialogOpen(false)}
        maxWidth="sm"
        fullWidth
      >
        <DialogTitle>Nueva carpeta</DialogTitle>
        <DialogContent dividers>
          <Stack spacing={2} mt={0.5}>
            {createMutation.isError && (
              <Alert severity="error">
                Error al crear carpeta.
              </Alert>
            )}
            {createMutation.data && !createMutation.data.ok && (
              <Alert severity="warning">
                {createMutation.data.mensaje || 'No se pudo crear la carpeta.'}
              </Alert>
            )}
            <Controller
              name="transportadora"
              control={control}
              rules={{ required: 'La transportadora es obligatoria' }}
              render={({ field }) => (
                <LegacyAutocompleteField
                  endpoint="jsonListaTransportadoras"
                  label="Transportadora"
                  value={field.value}
                  onChange={field.onChange}
                  required
                  error={!!errors.transportadora}
                  helperText={errors.transportadora?.message}
                />
              )}
            />
            <Controller
              name="cantidadMIC"
              control={control}
              rules={{
                required: 'La cantidad es obligatoria',
                min: { value: 1, message: 'Mínimo 1' },
              }}
              render={({ field }) => (
                <TextField
                  {...field}
                  label="Cantidad MIC/DTA"
                  type="number"
                  size="small"
                  fullWidth
                  inputProps={{ min: 1 }}
                  error={!!errors.cantidadMIC}
                  helperText={errors.cantidadMIC?.message}
                  onChange={(e) => field.onChange(Number(e.target.value))}
                />
              )}
            />
          </Stack>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setNewDialogOpen(false)} disabled={createMutation.isPending}>
            Cancelar
          </Button>
          <Button
            variant="contained"
            disabled={createMutation.isPending}
            onClick={handleSubmit((form) => createMutation.mutate(form))}
          >
            Crear carpeta
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  )
}

