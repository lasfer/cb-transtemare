export interface Transportadora {
  id: number
  nombre: string
  domicilio: string
  rol: string
  localidad: string
  pais: string
  prefijo: string
  numerador: string
  imagen: string
}

export interface TransportadoraFormData {
  nombre: string
  domicilio: string
  rol: string
  localidad: string
  prefijo: string
  numerador: string
  imagen: string
}

export interface Localidad {
  id: number
  descripcion: string
  pais: string
}
