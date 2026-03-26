import { useState } from 'react'
import { useNavigate, useLocation } from 'react-router-dom'
import {
  Alert,
  Box,
  Button,
  CircularProgress,
  IconButton,
  InputAdornment,
  Paper,
  Stack,
  TextField,
  Typography,
} from '@mui/material'
import Visibility from '@mui/icons-material/Visibility'
import VisibilityOff from '@mui/icons-material/VisibilityOff'
import LocalShippingIcon from '@mui/icons-material/LocalShipping'
import { useForm } from 'react-hook-form'
import { useAuth } from '../contexts/AuthContext'

interface LoginForm {
  usuario: string
  contrasena: string
}

export default function LoginPage() {
  const { login } = useAuth()
  const navigate = useNavigate()
  const location = useLocation()
  const from = (location.state as { from?: Location })?.from?.pathname ?? '/empresas'

  const [showPassword, setShowPassword] = useState(false)
  const [loading, setLoading] = useState(false)
  const [errorMsg, setErrorMsg] = useState<string | null>(null)

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<LoginForm>()

  const onSubmit = async ({ usuario, contrasena }: LoginForm) => {
    setLoading(true)
    setErrorMsg(null)
    try {
      const ok = await login(usuario, contrasena)
      if (ok) {
        navigate(from, { replace: true })
      } else {
        setErrorMsg('Usuario o contraseña incorrectos.')
      }
    } catch {
      setErrorMsg('No se pudo conectar con el servidor.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <Box
      sx={{
        minHeight: '100vh',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        background: 'linear-gradient(135deg, #1565c0 0%, #0288d1 100%)',
      }}
    >
      <Paper
        elevation={8}
        sx={{
          p: { xs: 3, sm: 5 },
          width: '100%',
          maxWidth: 380,
          borderRadius: 3,
        }}
      >
        {/* Logo / title */}
        <Stack alignItems="center" spacing={1} mb={4}>
          <Box
            sx={{
              width: 64,
              height: 64,
              borderRadius: '50%',
              bgcolor: 'primary.main',
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'center',
            }}
          >
            <LocalShippingIcon sx={{ color: 'white', fontSize: 36 }} />
          </Box>
          <Typography variant="h5" fontWeight={700} color="primary.main">
            Transtemare
          </Typography>
          <Typography variant="body2" color="text.secondary">
            Ingresá tus credenciales para continuar
          </Typography>
        </Stack>

        <form onSubmit={handleSubmit(onSubmit)} noValidate>
          <Stack spacing={2.5}>
            {errorMsg && (
              <Alert severity="error" sx={{ py: 0.5 }}>
                {errorMsg}
              </Alert>
            )}

            <TextField
              label="Usuario"
              fullWidth
              autoFocus
              autoComplete="username"
              error={!!errors.usuario}
              helperText={errors.usuario?.message}
              {...register('usuario', { required: 'El usuario es requerido' })}
            />

            <TextField
              label="Contraseña"
              fullWidth
              type={showPassword ? 'text' : 'password'}
              autoComplete="current-password"
              error={!!errors.contrasena}
              helperText={errors.contrasena?.message}
              InputProps={{
                endAdornment: (
                  <InputAdornment position="end">
                    <IconButton
                      onClick={() => setShowPassword((v) => !v)}
                      edge="end"
                      tabIndex={-1}
                    >
                      {showPassword ? <VisibilityOff /> : <Visibility />}
                    </IconButton>
                  </InputAdornment>
                ),
              }}
              {...register('contrasena', { required: 'La contraseña es requerida' })}
            />

            <Button
              type="submit"
              variant="contained"
              fullWidth
              size="large"
              disabled={loading}
              startIcon={loading ? <CircularProgress size={18} color="inherit" /> : undefined}
              sx={{ mt: 1, py: 1.2, fontWeight: 600, fontSize: '1rem' }}
            >
              {loading ? 'Ingresando...' : 'Ingresar'}
            </Button>
          </Stack>
        </form>
      </Paper>
    </Box>
  )
}
