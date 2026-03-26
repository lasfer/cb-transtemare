import client from './client'
import type { GridResponse, PaginationParams } from '../types/pagination'
import type { Transportadora, TransportadoraFormData, Localidad } from '../types/transportadora'

export async function fetchTransportadoras(
  params: PaginationParams
): Promise<GridResponse<Transportadora>> {
  const { data } = await client.get<GridResponse<Transportadora>>(
    '/jsonTableTransportadoras',
    { params }
  )
  return data
}

export async function fetchLocalidades(): Promise<Localidad[]> {
  const { data } = await client.get('/jsonTableLocalidades', {
    params: { rows: 9999, page: 1 },
  })
  return (data.gridModel ?? []).map(
    (l: { idLocalidad: number; descripcion: string; pais?: { descripcion?: string } }) => ({
      id: l.idLocalidad,
      descripcion: l.descripcion,
      pais: l.pais?.descripcion ?? '',
    })
  )
}


export async function crearTransportadora(
  form: TransportadoraFormData
): Promise<void> {
  const params = new URLSearchParams({
    oper: 'add',
    id: '_empty',
    nombre: form.nombre,
    domicilio: form.domicilio,
    rol: form.rol,
    localidad: form.localidad,
    prefijo: form.prefijo,
    numerador: form.numerador,
    imagen: form.imagen,
  })
  await client.post('/ABMTransportadora', params)
}

export async function editarTransportadora(
  id: number,
  form: TransportadoraFormData
): Promise<void> {
  const params = new URLSearchParams({
    oper: 'edit',
    id: String(id),
    nombre: form.nombre,
    domicilio: form.domicilio,
    rol: form.rol,
    localidad: form.localidad,
    prefijo: form.prefijo,
    numerador: form.numerador,
    imagen: form.imagen,
  })
  await client.post('/ABMTransportadora', params)
}

export async function eliminarTransportadora(id: number): Promise<void> {
  const params = new URLSearchParams({
    oper: 'del',
    id: String(id),
  })
  await client.post('/ABMTransportadora', params)
}

export async function subirLogoTransportadora(
  id: number,
  file: File
): Promise<void> {
  const fd = new FormData()
  fd.append('id', String(id))
  fd.append('logoFile', file)
  await client.post('/subirLogoTransportadora', fd, {
    headers: { 'Content-Type': 'multipart/form-data' },
  })
}

export function getLogoUrl(id: number, cacheBust?: number): string {
  const t = cacheBust ?? 0
  return `/api/verImagenTransportadora?id=${id}&t=${t}`
}
