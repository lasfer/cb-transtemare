import {
  Autocomplete,
  CircularProgress,
  TextField,
  type TextFieldProps,
} from '@mui/material'
import { useQuery } from '@tanstack/react-query'
import client from '../api/client'

interface LocalidadOption {
  descripcion: string
  pais: string
}

async function fetchTodasLasLocalidades(): Promise<LocalidadOption[]> {
  const { data } = await client.get('/jsonTableLocalidades', {
    params: { rows: 9999, page: 1 },
  })
  return (data.gridModel ?? []).map(
    (l: { descripcion: string; pais?: { descripcion?: string } }) => ({
      descripcion: l.descripcion ?? '',
      pais: l.pais?.descripcion ?? '',
    })
  )
}

interface Props {
  value: string
  onChange: (value: string) => void
  error?: boolean
  helperText?: string
  label?: string
  size?: TextFieldProps['size']
  required?: boolean
}

export default function LocalidadAutocomplete({
  value,
  onChange,
  error,
  helperText,
  label = 'Localidad',
  size = 'small',
  required,
}: Props) {
  const { data: localidades = [], isLoading } = useQuery({
    queryKey: ['localidades-all'],
    queryFn: fetchTodasLasLocalidades,
    staleTime: 5 * 60 * 1000, // 5 min cache — the list rarely changes
  })

  return (
    <Autocomplete
      freeSolo
      options={localidades.map((l) => l.descripcion)}
      loading={isLoading}
      value={value}
      inputValue={value}
      onInputChange={(_, newInput) => onChange(newInput)}
      onChange={(_, newValue) => onChange(newValue ?? '')}
      filterOptions={(options, { inputValue }) => {
        const term = inputValue.trim().toLowerCase()
        if (!term) return []
        return options.filter((o) => o.toLowerCase().includes(term)).slice(0, 20)
      }}
      loadingText="Cargando localidades..."
      noOptionsText={value.trim().length < 1 ? 'Escribí para buscar' : 'Sin resultados'}
      renderOption={(props, option) => {
        const loc = localidades.find((l) => l.descripcion === option)
        return (
          <li {...props} key={option}>
            {option}
            {loc?.pais && (
              <span style={{ marginLeft: 8, fontSize: 11, color: '#999' }}>
                {loc.pais}
              </span>
            )}
          </li>
        )
      }}
      renderInput={(params) => (
        <TextField
          {...params}
          label={required ? `${label} *` : label}
          size={size}
          error={error}
          helperText={helperText}
          InputProps={{
            ...params.InputProps,
            endAdornment: (
              <>
                {isLoading ? <CircularProgress color="inherit" size={16} /> : null}
                {params.InputProps.endAdornment}
              </>
            ),
          }}
        />
      )}
    />
  )
}
