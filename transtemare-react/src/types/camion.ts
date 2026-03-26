export type CamionTipo = 'Camion' | 'Remolque'

export const CAMION_TIPOS: CamionTipo[] = ['Camion', 'Remolque']

export interface Camion {
  id: number
  matricula: string
  capacidad: string
  marca: string
  anio: string
  tipo: CamionTipo
  numeroPoliza?: string
  vencimientoPoliza?: string
}

export interface CamionFormData {
  matricula: string
  capacidad: string
  marca: string
  anio: string
  tipo: CamionTipo
  numeroPoliza: string
  vencimientoPoliza: string
}
