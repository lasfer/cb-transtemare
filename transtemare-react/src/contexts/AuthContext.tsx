import {
  createContext,
  useCallback,
  useContext,
  useEffect,
  useState,
} from 'react'
import { checkAuth, login as apiLogin, logout as apiLogout } from '../api/auth'

interface AuthState {
  isAuthenticated: boolean
  checking: boolean
  username: string | null
}

interface AuthContextValue extends AuthState {
  login: (usuario: string, contrasena: string) => Promise<boolean>
  logout: () => Promise<void>
}

const AuthContext = createContext<AuthContextValue | null>(null)

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [state, setState] = useState<AuthState>({
    isAuthenticated: false,
    checking: true,
    username: null,
  })

  // On mount: check if already authenticated (existing session)
  useEffect(() => {
    checkAuth()
      .then((ok) => {
        setState({ isAuthenticated: ok, checking: false, username: null })
      })
      .catch(() => {
        setState({ isAuthenticated: false, checking: false, username: null })
      })
  }, [])

  const login = useCallback(async (usuario: string, contrasena: string) => {
    const ok = await apiLogin(usuario, contrasena)
    if (ok) {
      setState({ isAuthenticated: true, checking: false, username: usuario })
    }
    return ok
  }, [])

  const logout = useCallback(async () => {
    await apiLogout()
    setState({ isAuthenticated: false, checking: false, username: null })
  }, [])

  return (
    <AuthContext.Provider value={{ ...state, login, logout }}>
      {children}
    </AuthContext.Provider>
  )
}

export function useAuth(): AuthContextValue {
  const ctx = useContext(AuthContext)
  if (!ctx) throw new Error('useAuth must be used within AuthProvider')
  return ctx
}
