export type EmpresaTipo = 'Empresa' | 'Cliente' | 'Despachante' | 'Agencia Maritima'

export const EMPRESA_TIPOS: EmpresaTipo[] = [
  'Empresa',
  'Cliente',
  'Despachante',
  'Agencia Maritima',
]

export interface Empresa {
  id: number
  nombre: string
  domicilio: string
  rol: string
  tipo: EmpresaTipo
  pais: string
  ciudad: string
  nombreCorto: string
  codigo: string
}

export interface EmpresaFormData {
  nombre: string
  domicilio: string
  rol: string
  tipo: EmpresaTipo
  ciudad: string
  nombreCorto: string
  codigo: string
}
