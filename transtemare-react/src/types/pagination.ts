export interface GridResponse<T> {
  page: number
  total: number
  records: number
  gridModel: T[]
}

export interface PaginationParams {
  page: number
  rows: number
  sidx?: string
  sord?: 'asc' | 'desc'
  searchField?: string
  searchString?: string
  searchOper?: string
}
