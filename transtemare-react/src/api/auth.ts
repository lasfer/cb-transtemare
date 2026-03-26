import client from './client'

export async function login(usuario: string, contrasena: string): Promise<boolean> {
  const params = new URLSearchParams({ usuario, contrasena })
  await client.post('/entrar', params)
  return checkAuth()
}

export async function checkAuth(): Promise<boolean> {
  try {
    const { data } = await client.get('/jsonEmpresas', {
      params: { rows: 1, page: 1 },
    })
    return Array.isArray(data?.gridModel)
  } catch {
    return false
  }
}

export async function logout(): Promise<void> {
  await client.get('/logout').catch(() => {})
}
