# Contrato API (Struts actions)

Base en desarrollo: `http://localhost:3000/api` → `.../transtemare-web`.

Content-Type habitual: `application/x-www-form-urlencoded`.  
Auth: cookie de sesión.

## Auth

| Método | Path | Notas |
|--------|------|-------|
| POST | `/entrar` | `usuario`, `contrasena` |
| GET | `/logout` | Limpia sesión |
| GET | `/jsonEmpresas?rows=1&page=1` | Probe de sesión |

## Grids (jqGrid)

Query: `page`, `rows`, `sidx`, `sord`, `searchField`, `searchString`, `searchOper`  
Body respuesta: `gridModel`, `page`, `total`, `records`

| Dominio | Listado | ABM |
|---------|---------|-----|
| Empresas | `/jsonEmpresas` | `/ABMEmpresas` |
| Transportadoras | `/jsonTableTransportadoras` | `/ABMTransportadora` |
| Camiones | `/jsonTableCamiones` | `/ABMCamiones` |
| Terminales | `/jsonTableTerminales` | `/ABMTerminales` |
| Carpetas | `/jsonCarpetas` | ver abajo |

ABM: `oper=add|edit|del`, `id=_empty|<id>`.

## Carpetas

| Acción | Path | Uso |
|--------|------|-----|
| Crear | `POST /NuevaCarpeta` | Alta + MICs |
| Guardar | `POST /CrearCarpeta` | Edición (puede devolver HTML) |
| Histórico | `GET /pasarCarpetaHistorico` | Mover a histórico |

## Autocomplete / listas

Patrón: `/jsonLista{Entidad}?term=` → `{ lista: string[] }` o array de `id--label`.

Ejemplos: `jsonListaCamiones`, `jsonListaChoferes`, `jsonListaTransportadoras`, `jsonListaTerminales`, `jsonListaRutas`.

Localidades en React: `/jsonTableLocalidades` (`LocalidadAutocomplete`).

## Reportes PDF

| Documento | URL |
|-----------|-----|
| CRT | `/CRT.action?id={idCarpeta}` |
| MICDTA | `/MICDTA.action?id={idCarpeta}` |
| MICDTA camión sust. | `/MICDTA.action?id={id}&camionSust=true` |
| Sabana / Carátula | `/SABANA.action`, `/Caratula.action` (y variantes histórico) |

Abrir con `window.open`, no con axios.

## Módulos React espejo

`transtemare-react/src/api/{auth,empresas,transportadoras,camiones,terminales,carpetas}.ts`
