import { useMemo } from 'react'
import { Autocomplete, CircularProgress, TextField } from '@mui/material'
import { useQuery } from '@tanstack/react-query'
import { fetchLegacyAutocompleteOptions } from '../api/carpetas'

interface Props {
  endpoint: string
  label: string
  value: string
  onChange: (value: string) => void
  disabled?: boolean
  required?: boolean
  minChars?: number
  helperText?: string
  error?: boolean
}

function cleanLabel(option: string): string {
  return option.replace(/^\s*\d+\s*--\s*/, '')
}

export default function LegacyAutocompleteField({
  endpoint,
  label,
  value,
  onChange,
  disabled,
  required,
  minChars = 2,
  helperText,
  error,
}: Props) {
  const term = (value ?? '').trim()
  const enabled = !disabled && term.length >= minChars

  const { data = [], isFetching } = useQuery({
    queryKey: ['legacy-autocomplete', endpoint, term],
    queryFn: () => fetchLegacyAutocompleteOptions(endpoint, term),
    enabled,
    staleTime: 60_000,
  })

  const options = useMemo(() => data.slice(0, 30), [data])

  return (
    <Autocomplete
      freeSolo
      disabled={disabled}
      options={options}
      value={value}
      inputValue={value}
      onInputChange={(_, newValue) => onChange(newValue)}
      onChange={(_, newValue) => onChange(newValue ?? '')}
      getOptionLabel={(option) => (typeof option === 'string' ? cleanLabel(option) : '')}
      renderOption={(props, option) => (
        <li {...props} key={option}>
          {cleanLabel(option)}
        </li>
      )}
      noOptionsText={
        disabled
          ? 'Seleccioná el campo anterior'
          : term.length < minChars
            ? `Escribí al menos ${minChars} caracteres`
            : 'Sin resultados'
      }
      renderInput={(params) => (
        <TextField
          {...params}
          label={required ? `${label} *` : label}
          size="small"
          error={error}
          helperText={helperText}
          InputProps={{
            ...params.InputProps,
            endAdornment: (
              <>
                {isFetching ? <CircularProgress color="inherit" size={16} /> : null}
                {params.InputProps.endAdornment}
              </>
            ),
          }}
        />
      )}
    />
  )
}

