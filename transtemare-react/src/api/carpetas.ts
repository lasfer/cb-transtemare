import client from './client'
import type { CarpetaFormData } from '../types/carpeta'
import type { GridResponse, PaginationParams } from '../types/pagination'

export interface CarpetaRow {
  numeroCarpeta: string
  referenciaDestino?: string
  numeroContenedor?: string
  numeroContenedorFormateado?: string
  numeroDocumento?: number
  transportadora?: string
  terminal?: string
  agenciaMaritima?: string
  fechaLlegadaBuque?: string
}

export interface TerminalOption {
  id: number
  nombre: string
}

export interface NuevaCarpetaResponse {
  ok: boolean
  idCarpeta?: number
  mics?: string[]
  mensaje?: string
}

export async function fetchCarpetas(params: PaginationParams): Promise<GridResponse<CarpetaRow>> {
  const safeParams = {
    ...params,
    rows: Math.max(1, params.rows ?? 15),
    page: Math.max(1, params.page ?? 1),
  }
  const { data } = await client.get<GridResponse<CarpetaRow>>('/jsonCarpetas', { params: safeParams })
  return data
}

function normalizeStringListResponse(data: unknown): string[] {
  if (Array.isArray(data)) return data.filter((v): v is string => typeof v === 'string')
  if (data && typeof data === 'object' && Array.isArray((data as { lista?: unknown[] }).lista)) {
    return (data as { lista: unknown[] }).lista.filter((v): v is string => typeof v === 'string')
  }
  return []
}

export async function fetchLegacyAutocompleteOptions(
  endpoint: string,
  term: string
): Promise<string[]> {
  const { data } = await client.get(`/` + endpoint, { params: { term } })
  return normalizeStringListResponse(data)
}

export async function fetchLegacyStaticOptions(endpoint: string): Promise<string[]> {
  const { data } = await client.get(`/` + endpoint)
  return normalizeStringListResponse(data)
}

export async function fetchTerminalesOptions(): Promise<TerminalOption[]> {
  const { data } = await client.get('/jsonListaTerminales')
  const raw = data && typeof data === 'object' ? (data as { lista?: unknown[] }).lista : undefined
  if (!Array.isArray(raw)) return []
  return raw
    .map((item) => {
      const obj = item as { id?: number; nombre?: string }
      if (typeof obj?.id !== 'number' || typeof obj?.nombre !== 'string') return null
      return { id: obj.id, nombre: obj.nombre }
    })
    .filter((v): v is TerminalOption => v !== null)
}

function boolToString(value: boolean): string {
  return value ? 'true' : 'false'
}

export async function guardarCarpeta(form: CarpetaFormData): Promise<string> {
  const params = new URLSearchParams({
    idCarpeta: form.idCarpeta,
    referenciaDestino: form.referenciaDestino,
    nroTransmision: form.nroTransmision,
    ciudadDeOrigen: form.ciudadDeOrigen,
    aduanaOrigen: form.aduanaOrigen,
    ciudadDeDestino: form.ciudadDeDestino,
    aduanaDestino: form.aduanaDestino,
    ciudadDeEmision: form.ciudadDeEmision,
    ciudadEntrega: form.ciudadEntrega,
    paisOrigenMercaderias: form.paisOrigenMercaderias,
    ruta: form.ruta,
    cliente: form.cliente,
    empresaRemitente: form.empresaRemitente,
    empresaDestinataria: form.empresaDestinataria,
    empresaConsignataria: form.empresaConsignataria,
    notificarA: form.notificarA,
    despachante: form.despachante,
    agenciaMaritima: form.agenciaMaritima,
    tipoBulto: form.tipoBulto,
    transportadoraCamionOriginal: form.transportadoraCamionOriginal,
    choferOriginal: form.choferOriginal,
    camionOriginal: form.camionOriginal,
    remolqueOriginal: form.remolqueOriginal,
    transportadoraCamionSustituto: form.transportadoraCamionSustituto,
    choferSubstituto: form.choferSubstituto,
    camionSubstituto: form.camionSubstituto,
    remolqueSubstituto: form.remolqueSubstituto,
    'carpeta.numeroContenedorParte1': form.numeroContenedorParte1,
    'carpeta.numeroContenedorParte2': form.numeroContenedorParte2,
    'carpeta.numeroContenedorParte3': form.numeroContenedorParte3,
    'carpeta.transitoAduanero': boolToString(form.transitoAduanero),
    'carpeta.terminal.id': form.terminalId,
    'carpeta.referenciaDestino': form.referenciaDestino,
    'carpeta.nroTransmision': form.nroTransmision,
    'carpeta.tipoContenedor': form.tipoContenedor,
    'carpeta.cantidadBultos': form.cantidadBultos,
    'carpeta.pesoBruto': form.pesoBruto,
    'carpeta.volumenMC': form.volumenMC,
    'carpeta.moneda': form.moneda,
    'carpeta.valorFOT': form.valorFOT,
    'carpeta.costoFlete': form.costoFlete,
    'carpeta.seguro': form.seguro,
    'carpeta.documentosAnexos': form.documentosAnexos,
    'carpeta.formalidadesAduana': form.formalidadesAduana,
    'carpeta.rutasLargo': form.rutasLargo,
    'carpeta.rutasCorto': form.rutasCorto,
    'carpeta.validoHasta': form.validoHasta,
    'carpeta.firmante': form.firmante,
    'carpeta.cargarInformacionGarantia': boolToString(form.cargarInformacionGarantia),
    'carpeta.tipoGarantia': form.tipoGarantia,
    'carpeta.importeGarantia': form.importeGarantia,
    'carpeta.bancoGarantia': form.bancoGarantia,
    'carpeta.nroChequeGarantia': form.nroChequeGarantia,
  })

  const { data } = await client.post('/CrearCarpeta', params, {
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    responseType: 'text',
  })
  return typeof data === 'string' ? data : ''
}

export async function crearNuevaCarpeta(transportadora: string, cantidadMIC: number): Promise<NuevaCarpetaResponse> {
  const params = new URLSearchParams({
    transportadora,
    cantidadMIC: String(Math.max(1, cantidadMIC || 1)),
  })
  const response = await client.post('/NuevaCarpeta', params, {
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    responseType: 'text',
  })

  // NuevaCarpeta legacy redirige a index con destino=editarCarpeta?id=XXX
  // Intentamos extraer el id desde la URL final de la redirección.
  const responseURL = (response.request as { responseURL?: string } | undefined)?.responseURL ?? ''
  try {
    const url = new URL(responseURL)
    const destino = url.searchParams.get('destino') ?? ''
    const match = destino.match(/id=(\d+)/)
    if (match && match[1]) {
      return { ok: true, idCarpeta: Number(match[1]) }
    }
  } catch {
    // Ignorado: se reporta error controlado abajo.
  }
  return { ok: false, mensaje: 'No se pudo determinar la carpeta creada desde la respuesta legacy.' }
}

export async function pasarCarpetaAHistorico(idCarpeta: string): Promise<void> {
  await client.get('/pasarCarpetaHistorico', {
    params: { id: idCarpeta },
    responseType: 'text',
  })
}

