import { useState } from 'react'
import { Outlet, useNavigate, useLocation } from 'react-router-dom'
import {
  AppBar,
  Box,
  CssBaseline,
  Drawer,
  IconButton,
  List,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  Toolbar,
  Typography,
  useTheme,
  useMediaQuery,
  Divider,
  Tooltip,
} from '@mui/material'
import MenuIcon from '@mui/icons-material/Menu'
import BusinessIcon from '@mui/icons-material/Business'
import LocalShippingIcon from '@mui/icons-material/LocalShipping'
import DirectionsBusIcon from '@mui/icons-material/DirectionsBus'
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft'
import WarehouseIcon from '@mui/icons-material/Warehouse'
import LogoutIcon from '@mui/icons-material/Logout'
import { useAuth } from '../contexts/AuthContext'

const DRAWER_WIDTH = 220
const DRAWER_COLLAPSED = 64

const NAV_ITEMS = [
  { label: 'Empresas', path: '/empresas', icon: <BusinessIcon /> },
  { label: 'Transportadoras', path: '/transportadoras', icon: <LocalShippingIcon /> },
  { label: 'Camiones', path: '/camiones', icon: <DirectionsBusIcon /> },
  { label: 'Terminales', path: '/terminales', icon: <WarehouseIcon /> },
]

export default function Layout() {
  const theme = useTheme()
  const isMobile = useMediaQuery(theme.breakpoints.down('sm'))
  const [open, setOpen] = useState(!isMobile)
  const navigate = useNavigate()
  const location = useLocation()
  const { logout, username } = useAuth()

  const drawerWidth = open ? DRAWER_WIDTH : DRAWER_COLLAPSED

  return (
    <Box sx={{ display: 'flex', minHeight: '100vh' }}>
      <CssBaseline />

      {/* AppBar */}
      <AppBar
        position="fixed"
        sx={{
          zIndex: theme.zIndex.drawer + 1,
          width: '100%',
        }}
      >
        <Toolbar>
          <IconButton
            color="inherit"
            edge="start"
            onClick={() => setOpen((v) => !v)}
            sx={{ mr: 2 }}
          >
            {open ? <ChevronLeftIcon /> : <MenuIcon />}
          </IconButton>
          <Box
            component="img"
            src="/logo-placeholder.png"
            alt=""
            sx={{ height: 32, mr: 1, display: { xs: 'none', sm: 'block' } }}
            onError={(e) => {
              ;(e.target as HTMLImageElement).style.display = 'none'
            }}
          />
          <Typography variant="h6" noWrap sx={{ fontWeight: 700, letterSpacing: 1, flexGrow: 1 }}>
            Transtemare
          </Typography>
          {username && (
            <Typography variant="body2" sx={{ mr: 1, opacity: 0.85 }}>
              {username}
            </Typography>
          )}
          <Tooltip title="Cerrar sesión">
            <IconButton
              color="inherit"
              onClick={async () => {
                await logout()
                navigate('/login')
              }}
            >
              <LogoutIcon />
            </IconButton>
          </Tooltip>
        </Toolbar>
      </AppBar>

      {/* Drawer */}
      <Drawer
        variant={isMobile ? 'temporary' : 'permanent'}
        open={isMobile ? open : true}
        onClose={() => setOpen(false)}
        sx={{
          width: drawerWidth,
          flexShrink: 0,
          whiteSpace: 'nowrap',
          '& .MuiDrawer-paper': {
            width: drawerWidth,
            overflowX: 'hidden',
            transition: theme.transitions.create('width', {
              easing: theme.transitions.easing.sharp,
              duration: open
                ? theme.transitions.duration.enteringScreen
                : theme.transitions.duration.leavingScreen,
            }),
            boxSizing: 'border-box',
          },
        }}
      >
        <Toolbar />
        <Divider />
        <List dense>
          {NAV_ITEMS.map((item) => {
            const selected = location.pathname === item.path
            return (
              <Tooltip
                key={item.path}
                title={open ? '' : item.label}
                placement="right"
              >
                <span>
                  <ListItemButton
                    selected={selected}
                    disabled={item.disabled}
                    onClick={() => {
                      navigate(item.path)
                      if (isMobile) setOpen(false)
                    }}
                    sx={{
                      minHeight: 48,
                      px: 2.5,
                      borderRadius: 1,
                      mx: 0.5,
                      my: 0.25,
                      '&.Mui-selected': {
                        backgroundColor: 'primary.main',
                        color: 'white',
                        '& .MuiListItemIcon-root': { color: 'white' },
                        '&:hover': { backgroundColor: 'primary.dark' },
                      },
                    }}
                  >
                    <ListItemIcon
                      sx={{
                        minWidth: 0,
                        mr: open ? 2 : 'auto',
                        justifyContent: 'center',
                        color: selected ? 'white' : 'inherit',
                      }}
                    >
                      {item.icon}
                    </ListItemIcon>
                    {open && <ListItemText primary={item.label} />}
                  </ListItemButton>
                </span>
              </Tooltip>
            )
          })}
        </List>
      </Drawer>

      {/* Main content */}
      <Box
        component="main"
        sx={{
          flexGrow: 1,
          p: { xs: 2, sm: 3 },
          width: { sm: `calc(100% - ${drawerWidth}px)` },
          transition: theme.transitions.create(['width', 'margin'], {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.leavingScreen,
          }),
        }}
      >
        <Toolbar />
        <Outlet />
      </Box>
    </Box>
  )
}
