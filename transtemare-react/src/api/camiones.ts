import client from './client'
import type { GridResponse, PaginationParams } from '../types/pagination'
import type { Camion, CamionFormData } from '../types/camion'

export async function fetchCamiones(
  params: PaginationParams
): Promise<GridResponse<Camion>> {
  const safeParams = {
    ...params,
    rows: Math.max(1, params.rows ?? 15),
    page: Math.max(1, params.page ?? 1),
  }
  const { data } = await client.get<GridResponse<Camion>>('/jsonTableCamiones', {
    params: safeParams,
  })
  return data
}

export async function crearCamion(form: CamionFormData): Promise<void> {
  const params = new URLSearchParams({
    oper: 'add',
    id: '_empty',
    matricula: form.matricula,
    capacidad: form.capacidad,
    marca: form.marca,
    anio: form.anio,
    tipo: form.tipo,
    numeroPoliza: form.numeroPoliza ?? '',
    vencimientoPoliza: form.vencimientoPoliza ?? '',
  })
  await client.post('/ABMCamiones', params)
}

export async function editarCamion(id: number, form: CamionFormData): Promise<void> {
  const params = new URLSearchParams({
    oper: 'edit',
    id: String(id),
    matricula: form.matricula,
    capacidad: form.capacidad,
    marca: form.marca,
    anio: form.anio,
    tipo: form.tipo,
    numeroPoliza: form.numeroPoliza ?? '',
    vencimientoPoliza: form.vencimientoPoliza ?? '',
  })
  await client.post('/ABMCamiones', params)
}

export async function eliminarCamion(id: number): Promise<void> {
  const params = new URLSearchParams({ oper: 'del', id: String(id) })
  await client.post('/ABMCamiones', params)
}
