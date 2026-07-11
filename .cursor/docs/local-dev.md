# Desarrollo local

## Requisitos

- JDK 6+ (proyecto compilado a 1.6; conviene JDK 8 para tooling moderno si el build lo permite)
- Maven 3.x
- MySQL con base `skuncadb`
- Node.js 20+ (para React)
- Contenedor servlet (Tomcat/JBoss) en `http://localhost:8080`

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
# Desplegar target/transtemare-web.war en el servidor
```

Contexto: `/transtemare-web`  
Smoke: `http://localhost:8080/transtemare-web/` → `index.action`

El plugin Cargo del POM apunta a JBoss 4.2.3 (paths Linux); en Windows suele desplegarse a mano.

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
