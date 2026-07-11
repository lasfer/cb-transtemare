import { useEffect, useMemo } from 'react'
import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Alert,
  Box,
  Button,
  Chip,
  CircularProgress,
  FormControlLabel,
  InputLabel,
  MenuItem,
  Select,
  Stack,
  Switch,
  TextField,
  Typography,
} from '@mui/material'
import Grid from '@mui/material/GridLegacy'
import ExpandMoreIcon from '@mui/icons-material/ExpandMore'
import SaveIcon from '@mui/icons-material/Save'
import ArrowBackIcon from '@mui/icons-material/ArrowBack'
import { Controller, useForm } from 'react-hook-form'
import { useMutation, useQuery } from '@tanstack/react-query'
import { useNavigate, useParams } from 'react-router-dom'
import LegacyAutocompleteField from '../components/LegacyAutocompleteField'
import {
  fetchLegacyStaticOptions,
  fetchTerminalesOptions,
  guardarCarpeta,
} from '../api/carpetas'
import { DEFAULT_CARPETA_FORM, type CarpetaFormData } from '../types/carpeta'

function extractContainerCode(form: CarpetaFormData): string {
  return `${form.numeroContenedorParte1}${form.numeroContenedorParte2}${form.numeroContenedorParte3}`.toUpperCase()
}

function isValidContainerNumber(code: string): boolean {
  if (!code || code.length !== 11) return false
  const map = [10, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 34, 35, 36, 37, 38]
  const weights = [1, 2, 4, 8, 16, 32, 64, 128, 256, 512]

  const getNumber = (char: string) => map[char.charCodeAt(0) - 'A'.charCodeAt(0)]
  const checkDigit = Number(code[10])
  if (Number.isNaN(checkDigit)) return false

  const digits = new Array<number>(10).fill(0)
  for (let i = 0; i < 4; i += 1) {
    const c = code[i]
    if (!/[A-Z]/.test(c)) return false
    digits[i] = getNumber(c)
  }
  for (let i = 4; i < 10; i += 1) {
    const c = Number(code[i])
    if (Number.isNaN(c)) return false
    digits[i] = c
  }

  const total = digits.reduce((acc, digit, idx) => acc + digit * weights[idx], 0)
  return total % 11 === checkDigit
}

export default function CarpetasFormPage() {
  const navigate = useNavigate()
  const { idCarpeta: idCarpetaParam } = useParams<{ idCarpeta: string }>()
  const {
    control,
    handleSubmit,
    watch,
    setValue,
    formState: { errors },
  } = useForm<CarpetaFormData>({
    defaultValues: DEFAULT_CARPETA_FORM,
  })

  useEffect(() => {
    if (idCarpetaParam) {
      setValue('idCarpeta', idCarpetaParam)
    }
  }, [idCarpetaParam, setValue])

  const garantiaEnabled = watch('cargarInformacionGarantia')
  const contenedorCode = extractContainerCode(watch())
  const showContenedorValidation = contenedorCode.length === 11

  const originalTransportadora = watch('transportadoraCamionOriginal')
  const sustitutoTransportadora = watch('transportadoraCamionSustituto')

  const { data: terminales = [] } = useQuery({
    queryKey: ['terminales-options'],
    queryFn: fetchTerminalesOptions,
    staleTime: 5 * 60_000,
  })

  const { data: monedas = [] } = useQuery({
    queryKey: ['monedas-options'],
    queryFn: () => fetchLegacyStaticOptions('jsonListaMonedas'),
    staleTime: 5 * 60_000,
  })

  const { data: tiposContenedor = [] } = useQuery({
    queryKey: ['tipo-contenedor-options'],
    queryFn: () => fetchLegacyStaticOptions('jsonTipoContenedores'),
    staleTime: 5 * 60_000,
  })

  const saveMutation = useMutation({
    mutationFn: guardarCarpeta,
  })

  const saveResult = useMemo(() => {
    const html = saveMutation.data ?? ''
    if (!html) return null
    if (/ok\.png/i.test(html)) return { type: 'success' as const, text: 'Carpeta guardada correctamente.' }
    if (/warn\.png/i.test(html)) return { type: 'warning' as const, text: 'Carpeta guardada con advertencias.' }
    return { type: 'error' as const, text: 'No se pudo confirmar el guardado. Revisá el resultado del servidor.' }
  }, [saveMutation.data])

  return (
    <Box>
      <Stack direction={{ xs: 'column', md: 'row' }} justifyContent="space-between" spacing={2} mb={2}>
        <Box>
          <Typography variant="h5" color="primary">
            Carpeta - Alta / Edición (MVP React)
          </Typography>
          <Typography variant="body2" color="text.secondary">
            Primera versión con secciones y dependencias para mejorar usabilidad frente al JSP clásico.
          </Typography>
        </Box>
        <Button
          variant="contained"
          startIcon={saveMutation.isPending ? <CircularProgress size={16} color="inherit" /> : <SaveIcon />}
          disabled={saveMutation.isPending}
          onClick={handleSubmit((form) => saveMutation.mutate(form))}
        >
          Guardar carpeta
        </Button>
      </Stack>

      <Button
        variant="text"
        startIcon={<ArrowBackIcon />}
        onClick={() => navigate('/carpetas')}
        sx={{ mb: 2 }}
      >
        Volver al listado
      </Button>

      <Alert severity="info" sx={{ mb: 2 }}>
        Mejora UX aplicada: formulario dividido por bloques, validación visual de número de contenedor y campos
        dependientes habilitados según contexto.
      </Alert>

      {saveMutation.isError && (
        <Alert severity="error" sx={{ mb: 2 }}>
          Error al guardar la carpeta. Verificá datos y conexión con el backend.
        </Alert>
      )}

      {saveResult && (
        <Alert severity={saveResult.type} sx={{ mb: 2 }}>
          {saveResult.text}
        </Alert>
      )}

      <Accordion defaultExpanded>
        <AccordionSummary expandIcon={<ExpandMoreIcon />}>
          <Typography variant="subtitle1">1) Identificación y control</Typography>
        </AccordionSummary>
        <AccordionDetails>
          <Grid container spacing={2}>
            <Grid item xs={12} md={3}>
              <Controller
                name="idCarpeta"
                control={control}
                rules={{ required: 'El número de carpeta es obligatorio' }}
                render={({ field }) => (
                  <TextField
                    {...field}
                    label="Número de carpeta *"
                    size="small"
                    fullWidth
                    error={!!errors.idCarpeta}
                    helperText={errors.idCarpeta?.message}
                  />
                )}
              />
            </Grid>
            <Grid item xs={12} md={4}>
              <Controller
                name="nroTransmision"
                control={control}
                render={({ field }) => <TextField {...field} label="Nro. transmisión" size="small" fullWidth />}
              />
            </Grid>
            <Grid item xs={12} md={5}>
              <Controller
                name="referenciaDestino"
                control={control}
                render={({ field }) => <TextField {...field} label="Referencia destino" size="small" fullWidth />}
              />
            </Grid>
            <Grid item xs={12} md={3}>
              <Controller
                name="numeroContenedorParte1"
                control={control}
                render={({ field }) => (
                  <TextField
                    {...field}
                    label="Contenedor (4)"
                    inputProps={{ maxLength: 4 }}
                    onChange={(e) => field.onChange(e.target.value.toUpperCase())}
                    size="small"
                    fullWidth
                  />
                )}
              />
            </Grid>
            <Grid item xs={12} md={3}>
              <Controller
                name="numeroContenedorParte2"
                control={control}
                render={({ field }) => (
                  <TextField
                    {...field}
                    label="Contenedor (6)"
                    inputProps={{ maxLength: 6 }}
                    onChange={(e) => field.onChange(e.target.value.toUpperCase())}
                    size="small"
                    fullWidth
                  />
                )}
              />
            </Grid>
            <Grid item xs={12} md={2}>
              <Controller
                name="numeroContenedorParte3"
                control={control}
                render={({ field }) => (
                  <TextField
                    {...field}
                    label="DV"
                    inputProps={{ maxLength: 1 }}
                    onChange={(e) => field.onChange(e.target.value.toUpperCase())}
                    size="small"
                    fullWidth
                  />
                )}
              />
            </Grid>
            <Grid item xs={12} md={4} display="flex" alignItems="center">
              {showContenedorValidation ? (
                <Chip
                  color={isValidContainerNumber(contenedorCode) ? 'success' : 'warning'}
                  label={
                    isValidContainerNumber(contenedorCode)
                      ? `Contenedor válido: ${contenedorCode}`
                      : `Contenedor inválido: ${contenedorCode}`
                  }
                  variant="outlined"
                />
              ) : (
                <Typography variant="body2" color="text.secondary">
                  Completá los 11 caracteres del contenedor para validar.
                </Typography>
              )}
            </Grid>
            <Grid item xs={12} md={4}>
              <Controller
                name="terminalId"
                control={control}
                render={({ field }) => (
                  <>
                    <InputLabel sx={{ mb: 0.5 }}>Terminal</InputLabel>
                    <Select {...field} size="small" fullWidth displayEmpty>
                      <MenuItem value="">
                        <em>Seleccionar...</em>
                      </MenuItem>
                      {terminales.map((t) => (
                        <MenuItem key={t.id} value={String(t.id)}>
                          {t.nombre}
                        </MenuItem>
                      ))}
                    </Select>
                  </>
                )}
              />
            </Grid>
            <Grid item xs={12} md={4}>
              <Controller
                name="transitoAduanero"
                control={control}
                render={({ field }) => (
                  <FormControlLabel
                    control={<Switch checked={!!field.value} onChange={(_, checked) => field.onChange(checked)} />}
                    label="Tránsito aduanero"
                  />
                )}
              />
            </Grid>
          </Grid>
        </AccordionDetails>
      </Accordion>

      <Accordion defaultExpanded>
        <AccordionSummary expandIcon={<ExpandMoreIcon />}>
          <Typography variant="subtitle1">2) Localización, ruta y aduanas</Typography>
        </AccordionSummary>
        <AccordionDetails>
          <Grid container spacing={2}>
            <Grid item xs={12} md={6}>
              <Controller
                name="ciudadDeOrigen"
                control={control}
                render={({ field }) => (
                  <LegacyAutocompleteField endpoint="jsonListaCiudades" label="Ciudad de origen" value={field.value} onChange={field.onChange} />
                )}
              />
            </Grid>
            <Grid item xs={12} md={6}>
              <Controller
                name="aduanaOrigen"
                control={control}
                render={({ field }) => (
                  <LegacyAutocompleteField endpoint="jsonListaAduanas" label="Aduana origen" value={field.value} onChange={field.onChange} />
                )}
              />
            </Grid>
            <Grid item xs={12} md={6}>
              <Controller
                name="ciudadDeDestino"
                control={control}
                render={({ field }) => (
                  <LegacyAutocompleteField endpoint="jsonListaCiudades" label="Ciudad de destino" value={field.value} onChange={field.onChange} />
                )}
              />
            </Grid>
            <Grid item xs={12} md={6}>
              <Controller
                name="aduanaDestino"
                control={control}
                render={({ field }) => (
                  <LegacyAutocompleteField endpoint="jsonListaAduanas" label="Aduana destino" value={field.value} onChange={field.onChange} />
                )}
              />
            </Grid>
            <Grid item xs={12} md={6}>
              <Controller
                name="ciudadDeEmision"
                control={control}
                render={({ field }) => (
                  <LegacyAutocompleteField endpoint="jsonListaCiudades" label="Ciudad de emisión" value={field.value} onChange={field.onChange} />
                )}
              />
            </Grid>
            <Grid item xs={12} md={6}>
              <Controller
                name="ciudadEntrega"
                control={control}
                render={({ field }) => (
                  <LegacyAutocompleteField endpoint="jsonListaCiudades" label="Ciudad de entrega" value={field.value} onChange={field.onChange} />
                )}
              />
            </Grid>
            <Grid item xs={12} md={6}>
              <Controller
                name="paisOrigenMercaderias"
                control={control}
                render={({ field }) => (
                  <LegacyAutocompleteField endpoint="jsonListaPaises" label="País origen mercaderías" value={field.value} onChange={field.onChange} />
                )}
              />
            </Grid>
            <Grid item xs={12} md={6}>
              <Controller
                name="ruta"
                control={control}
                render={({ field }) => (
                  <LegacyAutocompleteField endpoint="jsonListaRutas" label="Ruta principal" value={field.value} onChange={field.onChange} />
                )}
              />
            </Grid>
          </Grid>
        </AccordionDetails>
      </Accordion>

      <Accordion defaultExpanded>
        <AccordionSummary expandIcon={<ExpandMoreIcon />}>
          <Typography variant="subtitle1">3) Flota (original y sustituto)</Typography>
        </AccordionSummary>
        <AccordionDetails>
          <Typography variant="subtitle2" color="text.secondary" mb={1}>
            Original
          </Typography>
          <Grid container spacing={2} mb={2}>
            <Grid item xs={12} md={6}>
              <Controller
                name="transportadoraCamionOriginal"
                control={control}
                render={({ field }) => (
                  <LegacyAutocompleteField endpoint="jsonListaTransportadoras" label="Transportadora camión original" value={field.value} onChange={field.onChange} />
                )}
              />
            </Grid>
            <Grid item xs={12} md={6}>
              <Controller
                name="choferOriginal"
                control={control}
                render={({ field }) => (
                  <LegacyAutocompleteField
                    endpoint="jsonListaChoferes"
                    label="Chofer original"
                    value={field.value}
                    onChange={field.onChange}
                    disabled={!originalTransportadora}
                  />
                )}
              />
            </Grid>
            <Grid item xs={12} md={6}>
              <Controller
                name="camionOriginal"
                control={control}
                render={({ field }) => (
                  <LegacyAutocompleteField
                    endpoint="jsonListaCamiones"
                    label="Camión original"
                    value={field.value}
                    onChange={field.onChange}
                    disabled={!originalTransportadora}
                  />
                )}
              />
            </Grid>
            <Grid item xs={12} md={6}>
              <Controller
                name="remolqueOriginal"
                control={control}
                render={({ field }) => (
                  <LegacyAutocompleteField
                    endpoint="jsonListaRemolques"
                    label="Remolque original"
                    value={field.value}
                    onChange={field.onChange}
                    disabled={!originalTransportadora}
                  />
                )}
              />
            </Grid>
          </Grid>

          <Typography variant="subtitle2" color="text.secondary" mb={1}>
            Sustituto
          </Typography>
          <Grid container spacing={2}>
            <Grid item xs={12} md={6}>
              <Controller
                name="transportadoraCamionSustituto"
                control={control}
                render={({ field }) => (
                  <LegacyAutocompleteField endpoint="jsonListaTransportadoras" label="Transportadora camión sustituto" value={field.value} onChange={field.onChange} />
                )}
              />
            </Grid>
            <Grid item xs={12} md={6}>
              <Controller
                name="choferSubstituto"
                control={control}
                render={({ field }) => (
                  <LegacyAutocompleteField
                    endpoint="jsonListaChoferes"
                    label="Chofer sustituto"
                    value={field.value}
                    onChange={field.onChange}
                    disabled={!sustitutoTransportadora}
                  />
                )}
              />
            </Grid>
            <Grid item xs={12} md={6}>
              <Controller
                name="camionSubstituto"
                control={control}
                render={({ field }) => (
                  <LegacyAutocompleteField
                    endpoint="jsonListaCamiones"
                    label="Camión sustituto"
                    value={field.value}
                    onChange={field.onChange}
                    disabled={!sustitutoTransportadora}
                  />
                )}
              />
            </Grid>
            <Grid item xs={12} md={6}>
              <Controller
                name="remolqueSubstituto"
                control={control}
                render={({ field }) => (
                  <LegacyAutocompleteField
                    endpoint="jsonListaRemolques"
                    label="Remolque sustituto"
                    value={field.value}
                    onChange={field.onChange}
                    disabled={!sustitutoTransportadora}
                  />
                )}
              />
            </Grid>
          </Grid>
        </AccordionDetails>
      </Accordion>

      <Accordion defaultExpanded>
        <AccordionSummary expandIcon={<ExpandMoreIcon />}>
          <Typography variant="subtitle1">4) Empresas y costos</Typography>
        </AccordionSummary>
        <AccordionDetails>
          <Grid container spacing={2}>
            <Grid item xs={12} md={4}>
              <Controller
                name="cliente"
                control={control}
                render={({ field }) => (
                  <LegacyAutocompleteField endpoint="jsonListaClientes" label="Cliente" value={field.value} onChange={field.onChange} />
                )}
              />
            </Grid>
            <Grid item xs={12} md={4}>
              <Controller
                name="empresaRemitente"
                control={control}
                render={({ field }) => (
                  <LegacyAutocompleteField endpoint="jsonListaEmpresas" label="Remitente" value={field.value} onChange={field.onChange} />
                )}
              />
            </Grid>
            <Grid item xs={12} md={4}>
              <Controller
                name="empresaDestinataria"
                control={control}
                render={({ field }) => (
                  <LegacyAutocompleteField endpoint="jsonListaEmpresas" label="Destinataria" value={field.value} onChange={field.onChange} />
                )}
              />
            </Grid>
            <Grid item xs={12} md={4}>
              <Controller
                name="empresaConsignataria"
                control={control}
                render={({ field }) => (
                  <LegacyAutocompleteField endpoint="jsonListaEmpresas" label="Consignataria" value={field.value} onChange={field.onChange} />
                )}
              />
            </Grid>
            <Grid item xs={12} md={4}>
              <Controller
                name="notificarA"
                control={control}
                render={({ field }) => (
                  <LegacyAutocompleteField endpoint="jsonListaEmpresas" label="Notificar a" value={field.value} onChange={field.onChange} />
                )}
              />
            </Grid>
            <Grid item xs={12} md={4}>
              <Controller
                name="despachante"
                control={control}
                render={({ field }) => (
                  <LegacyAutocompleteField endpoint="jsonListaDespachantes" label="Despachante" value={field.value} onChange={field.onChange} />
                )}
              />
            </Grid>
            <Grid item xs={12} md={4}>
              <Controller
                name="agenciaMaritima"
                control={control}
                render={({ field }) => (
                  <LegacyAutocompleteField endpoint="jsonListaAgenciasMaritimas" label="Agencia marítima" value={field.value} onChange={field.onChange} />
                )}
              />
            </Grid>
            <Grid item xs={12} md={4}>
              <Controller
                name="tipoBulto"
                control={control}
                render={({ field }) => (
                  <LegacyAutocompleteField endpoint="jsonListaBultos" label="Tipo de bulto" value={field.value} onChange={field.onChange} />
                )}
              />
            </Grid>
            <Grid item xs={12} md={4}>
              <Controller
                name="tipoContenedor"
                control={control}
                render={({ field }) => (
                  <>
                    <InputLabel sx={{ mb: 0.5 }}>Tipo de contenedor</InputLabel>
                    <Select {...field} size="small" fullWidth displayEmpty>
                      <MenuItem value="">
                        <em>Seleccionar...</em>
                      </MenuItem>
                      {tiposContenedor.map((tipo) => (
                        <MenuItem key={tipo} value={tipo}>
                          {tipo}
                        </MenuItem>
                      ))}
                    </Select>
                  </>
                )}
              />
            </Grid>
            <Grid item xs={12} md={2}>
              <Controller
                name="cantidadBultos"
                control={control}
                render={({ field }) => <TextField {...field} label="Cant. bultos" size="small" fullWidth />}
              />
            </Grid>
            <Grid item xs={12} md={2}>
              <Controller
                name="pesoBruto"
                control={control}
                render={({ field }) => <TextField {...field} label="Peso bruto" size="small" fullWidth />}
              />
            </Grid>
            <Grid item xs={12} md={2}>
              <Controller
                name="volumenMC"
                control={control}
                render={({ field }) => <TextField {...field} label="Volumen MC" size="small" fullWidth />}
              />
            </Grid>
            <Grid item xs={12} md={2}>
              <Controller
                name="moneda"
                control={control}
                render={({ field }) => (
                  <>
                    <InputLabel sx={{ mb: 0.5 }}>Moneda</InputLabel>
                    <Select {...field} size="small" fullWidth displayEmpty>
                      {monedas.map((m) => (
                        <MenuItem key={m} value={m}>
                          {m}
                        </MenuItem>
                      ))}
                    </Select>
                  </>
                )}
              />
            </Grid>
            <Grid item xs={12} md={2}>
              <Controller
                name="valorFOT"
                control={control}
                render={({ field }) => <TextField {...field} label="Valor FOT" size="small" fullWidth />}
              />
            </Grid>
            <Grid item xs={12} md={2}>
              <Controller
                name="costoFlete"
                control={control}
                render={({ field }) => <TextField {...field} label="Costo flete" size="small" fullWidth />}
              />
            </Grid>
            <Grid item xs={12} md={2}>
              <Controller
                name="seguro"
                control={control}
                render={({ field }) => <TextField {...field} label="Seguro" size="small" fullWidth />}
              />
            </Grid>
          </Grid>
        </AccordionDetails>
      </Accordion>

      <Accordion>
        <AccordionSummary expandIcon={<ExpandMoreIcon />}>
          <Typography variant="subtitle1">5) Textos y garantía</Typography>
        </AccordionSummary>
        <AccordionDetails>
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <Controller
                name="documentosAnexos"
                control={control}
                render={({ field }) => <TextField {...field} label="Documentos anexos" size="small" fullWidth multiline minRows={2} />}
              />
            </Grid>
            <Grid item xs={12}>
              <Controller
                name="formalidadesAduana"
                control={control}
                render={({ field }) => <TextField {...field} label="Formalidades aduana" size="small" fullWidth multiline minRows={2} />}
              />
            </Grid>
            <Grid item xs={12} md={4}>
              <Controller
                name="rutasCorto"
                control={control}
                render={({ field }) => <TextField {...field} label="Rutas corto" size="small" fullWidth />}
              />
            </Grid>
            <Grid item xs={12} md={4}>
              <Controller
                name="validoHasta"
                control={control}
                render={({ field }) => <TextField {...field} label="Válido hasta" size="small" fullWidth />}
              />
            </Grid>
            <Grid item xs={12} md={4}>
              <Controller
                name="firmante"
                control={control}
                render={({ field }) => <TextField {...field} label="Firmante" size="small" fullWidth />}
              />
            </Grid>
            <Grid item xs={12}>
              <Controller
                name="rutasLargo"
                control={control}
                render={({ field }) => <TextField {...field} label="Rutas largo / observaciones" size="small" fullWidth multiline minRows={3} />}
              />
            </Grid>
            <Grid item xs={12}>
              <Controller
                name="cargarInformacionGarantia"
                control={control}
                render={({ field }) => (
                  <FormControlLabel
                    control={<Switch checked={!!field.value} onChange={(_, checked) => field.onChange(checked)} />}
                    label="Cargar información de garantía"
                  />
                )}
              />
            </Grid>
            <Grid item xs={12} md={3}>
              <Controller
                name="tipoGarantia"
                control={control}
                render={({ field }) => <TextField {...field} disabled={!garantiaEnabled} label="Tipo garantía" size="small" fullWidth />}
              />
            </Grid>
            <Grid item xs={12} md={3}>
              <Controller
                name="importeGarantia"
                control={control}
                render={({ field }) => <TextField {...field} disabled={!garantiaEnabled} label="Importe garantía" size="small" fullWidth />}
              />
            </Grid>
            <Grid item xs={12} md={3}>
              <Controller
                name="bancoGarantia"
                control={control}
                render={({ field }) => <TextField {...field} disabled={!garantiaEnabled} label="Banco garantía" size="small" fullWidth />}
              />
            </Grid>
            <Grid item xs={12} md={3}>
              <Controller
                name="nroChequeGarantia"
                control={control}
                render={({ field }) => <TextField {...field} disabled={!garantiaEnabled} label="Nro cheque garantía" size="small" fullWidth />}
              />
            </Grid>
          </Grid>
        </AccordionDetails>
      </Accordion>
    </Box>
  )
}

