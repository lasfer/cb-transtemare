# Desarrollo local

## Requisitos

- **JDK 8** (bytecode target 1.8)
- Maven 3.x
- MySQL con base `skuncadb`
- Node.js 20+ (para React)
- **Tomcat 8.5** en `http://localhost:8080` (JBoss 4.2 ya no es el runtime soportado)

## Base de datos

Config por defecto en `applicationContext.xml`:

- URL: `jdbc:mysql://localhost:3306/skuncadb?autoReconnect=true`
- Usuario/clave: `root` / `root` (ajustar localmente; no commitear credenciales reales)

Aplicar scripts de `transtemare-core/sqlscripts/` según necesidad.

## Backend

```bash
cd transtemare-core
mvn clean install

cd ../transtemare-web
mvn clean package
# Desplegar target/transtemare-web.war en Tomcat 8.5
```

Contexto: `/transtemare-web`  
Smoke: `http://localhost:8080/transtemare-web/` → `index.action`

### Deploy en Tomcat (Windows)

En esta máquina el runtime es el servicio **`Tomcat9`** (Tomcat 9.0 bajo JDK 8).  
`CATALINA_HOME`: `C:\Program Files\Apache Software Foundation\Tomcat 9.0`

```powershell
Get-Service Tomcat9
# Si está Stopped (puede requerir PowerShell admin):
Start-Service Tomcat9

Copy-Item -Force transtemare-web\target\transtemare-web.war `
  "C:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps\transtemare-web.war"
```

Verificar `http://localhost:8080/transtemare-web/`.

**No** arrancar/parar con `catalina.bat`: usar siempre el servicio.  
Agente: skill `.cursor/skills/deploy-tomcat/` y comando `/deploy-backend`.

Cargo en el POM está como `tomcat85x` (opcional). En Windows preferir servicio + copy del WAR.

## Frontend

```bash
cd transtemare-react
npm install
npm run dev    # http://localhost:3000
```

El proxy Vite reescribe:

- `/api/*` → `http://localhost:8080/transtemare-web/*`
- `Set-Cookie` Path para que la sesión funcione en `/api`
- `Location` de redirects 302

## Scripts útiles React

| Script | Uso |
|--------|-----|
| `npm run dev` / `start` | Desarrollo |
| `npm run build` | `tsc -b` + Vite build |
| `npm run lint` | ESLint |
| `npm run preview` | Preview del build |

## Login

Usar un usuario existente en DB. Tras login, React valida sesión con `GET /api/jsonEmpresas?rows=1&page=1`.

## Smoke checklist (post Java 8)

1. Login Struts / sesión
2. Grids JSON + ABM (empresas/camiones)
3. Carpetas + PDF CRT/MICDTA
4. React `npm run dev` contra `/api`
