import client from './client'
import type { GridResponse, PaginationParams } from '../types/pagination'
import type { Terminal, TerminalFormData } from '../types/terminal'

export async function fetchTerminales(
  params: PaginationParams
): Promise<GridResponse<Terminal>> {
  const { data } = await client.get<GridResponse<Terminal>>('/jsonTerminales', {
    params,
  })
  return data
}

export async function crearTerminal(form: TerminalFormData): Promise<void> {
  const params = new URLSearchParams({
    oper: 'add',
    id: '_empty',
    nombre: form.nombre,
  })
  await client.post('/ABMTerminales', params)
}

export async function editarTerminal(id: number, form: TerminalFormData): Promise<void> {
  const params = new URLSearchParams({
    oper: 'edit',
    id: String(id),
    nombre: form.nombre,
  })
  await client.post('/ABMTerminales', params)
}

export async function eliminarTerminal(id: number): Promise<void> {
  const params = new URLSearchParams({ oper: 'del', id: String(id) })
  await client.post('/ABMTerminales', params)
}
