import client from './client'
import type { GridResponse, PaginationParams } from '../types/pagination'
import type { Empresa, EmpresaFormData } from '../types/empresa'

export async function fetchEmpresas(
  params: PaginationParams
): Promise<GridResponse<Empresa>> {
  const { data } = await client.get<GridResponse<Empresa>>('/jsonEmpresas', {
    params,
  })
  return data
}

export async function fetchTodasLasLocalidades(): Promise<
  { id: number; descripcion: string }[]
> {
  const { data } = await client.get('/jsonLocalidades', {
    params: { rows: 9999, page: 1 },
  })
  return (data.gridModel ?? []).map((l: { idLocalidad: number; descripcion: string }) => ({
    id: l.idLocalidad,
    descripcion: l.descripcion,
  }))
}

export async function crearEmpresa(form: EmpresaFormData): Promise<void> {
  const params = new URLSearchParams({
    oper: 'add',
    id: '_empty',
    nombre: form.nombre,
    domicilio: form.domicilio,
    rol: form.rol,
    tipo: form.tipo,
    ciudad: form.ciudad,
    nombreCorto: form.nombreCorto,
    codigo: form.codigo,
  })
  await client.post('/ABMEmpresas', params)
}

export async function editarEmpresa(
  id: number,
  form: EmpresaFormData
): Promise<void> {
  const params = new URLSearchParams({
    oper: 'edit',
    id: String(id),
    nombre: form.nombre,
    domicilio: form.domicilio,
    rol: form.rol,
    tipo: form.tipo,
    ciudad: form.ciudad,
    nombreCorto: form.nombreCorto,
    codigo: form.codigo,
  })
  await client.post('/ABMEmpresas', params)
}

export async function eliminarEmpresa(id: number): Promise<void> {
  const params = new URLSearchParams({
    oper: 'del',
    id: String(id),
  })
  await client.post('/ABMEmpresas', params)
}
