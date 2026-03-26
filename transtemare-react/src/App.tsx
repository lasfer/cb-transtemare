import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import { AuthProvider } from './contexts/AuthContext'
import RequireAuth from './components/RequireAuth'
import Layout from './components/Layout'
import LoginPage from './pages/LoginPage'
import EmpresasPage from './pages/EmpresasPage'
import TransportadorasPage from './pages/TransportadorasPage'
import CamionesPage from './pages/CamionesPage'
import ErrorBoundary from './components/ErrorBoundary'
import TerminalesPage from './pages/TerminalesPage'

export default function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <Routes>
          <Route path="/login" element={<LoginPage />} />
          <Route
            path="/"
            element={
              <RequireAuth>
                <Layout />
              </RequireAuth>
            }
          >
            <Route index element={<Navigate to="/empresas" replace />} />
            <Route path="empresas" element={<EmpresasPage />} />
            <Route path="transportadoras" element={<TransportadorasPage />} />
            <Route path="camiones" element={<ErrorBoundary><CamionesPage /></ErrorBoundary>} />
            <Route path="terminales" element={<TerminalesPage />} />
          </Route>
          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </AuthProvider>
    </BrowserRouter>
  )
}
