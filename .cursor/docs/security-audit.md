# Transtemare — Security Audit

**Fecha:** 2026-07-10  
**Alcance:** `transtemare-core`, `transtemare-web`, `transtemare-react`  
**Método:** Revisión estática (sin explotación dinámica)  
**Riesgo global:** **Critical**

También disponible como canvas interactivo en el IDE (Security Audit).

---

## Resumen ejecutivo

El backend legacy concentra riesgo crítico: **Struts 2.3.3** y **XStream 1.3.1** (superficie RCE), **passwords en plaintext**, y **credenciales de DB en el repositorio**. No hay RBAC: cualquier usuario autenticado puede operar todo.

React está en mejor forma (sin `dangerouslySetInnerHTML`, cookies de sesión vía proxy). El **diff de la rama `react`** no introdujo hallazgos medium+ nuevos; el riesgo viene del stack Struts compartido.

---

## Hallazgos

| Sev | Categoría | Ubicación | Hallazgo | Recomendación |
|-----|-----------|-----------|----------|---------------|
| Critical | Dependencies | `transtemare-web/pom.xml` | Struts **2.3.3** — CVEs RCE (S2-045, OGNL). `FilterDispatcher` en `web.xml`. | Salir de Struts (plan Spring Boot) o upgrade mínimo a línea soportada. Quitar `struts2-config-browser-plugin`. |
| Critical | Deserialization | `CRTHistoricoV1.java`, `MICDTAHistoricoV1.java`, `CaratulaHistoricoV1.java` | **XStream 1.3.1** `new XStream()` + `fromXML` sin allowlist. | XStream ≥1.4.20 con allowlist, o Jackson/JSON. |
| Critical | Auth | `SQLComunes.java`, `daoResponsable.java`, `Entrar.java` | Login `user=? and password=?` → almacenamiento/comparación en claro. | bcrypt/argon2 + migración de usuarios. |
| Critical | Secrets | `applicationContext.xml`, `Configuraciones.java` | DB `root`/`root` y `root`/`geocom` en código. | Env/JNDI/secrets manager; rotar; secret scanning. |
| High | Auth | `JsonListaUsuarios.java` | JSON de usuarios incluye **password**. | DTO sin password; auditar todos los `@Result(type=json)`. |
| High | Dependencies | POMs core/web | Log4j **1.2.17**, Spring **3.1.x**, MySQL Connector **5.1.21** — EOL. | Logback/Log4j2; Spring 5.3+; `mysql-connector-j` 8.x. |
| High | AuthZ | `AuthenticationInterceptor.java` | Solo flag de sesión `"logueado"`; sin roles. | Spring Security + RBAC. |
| High | Disclosure | `struts.xml`, `pom.xml`, `echo.jsp` | `devMode=true`, config-browser, stack traces al cliente. | `devMode=false` en no-dev; errores genéricos. |
| Medium | CSRF | ABM / `CrearCarpeta` / `auth.ts` | POSTs con cookie sin CSRF; logout por **GET**. | Tokens CSRF / SameSite; logout POST. |
| Medium | Session | `Entrar.java`, `Salir.java` | Sin regeneración de sesión en login; logout no invalida sesión. | `invalidate` + nueva sesión; cookies Secure/HttpOnly/SameSite. |
| Medium | Path traversal | `CRT.java`, `MICDTA.java`, ABM transportadora | `imagen` concatenada a path de logos. | Allowlist de filename + `Path.normalize` bajo directorio base. |
| Medium | XSS | `commons.js`, `mics.jsp`, JSPs de reportes | `.html(datos)` y valores en `<script>` sin escape. | Escape server-side / DOMPurify; migrar a React. |
| Medium | Logging | `daoResponsable.java` | Password en log DEBUG. | Nunca loguear credenciales. |
| Medium | Proxy | `vite.config.ts` | Rewrite de `Path` de cookie (solo dev). | No exponer Vite en red; prod same-origin. |
| Low | SQL | `daoLocalidades.java` | `IN (...)` por concat de IDs (hoy internos). | `NamedParameterJdbcTemplate`. |
| Low | Auth | Interceptor / React | UI gate solo cosmético; falta result `login` global claro. | 401 JSON en APIs; axios interceptor. |

---

## Diff rama `react` (Security Review)

Sin hallazgos medium/high/critical **introducidos por el diff**. La SPA delega auth al interceptor Struts. Sugerencias no bloqueantes: allowlist de endpoints en autocomplete, confirmar “pasar a histórico”, cargar carpeta antes de guardar.

---

## Quick wins

1. `struts.devMode=false`; quitar config-browser en prod  
2. Quitar password de `JsonListaUsuarios`  
3. Dejar de loguear contraseñas  
4. Externalizar y rotar credenciales DB  
5. Upgrade XStream + allowlist (o reemplazo)  
6. CSRF + logout POST  
7. Sanitizar filenames de logos  

## Largo plazo

1. Plan Spring Boot REST rewrite (salir de Struts)  
2. Hash de passwords + RBAC  
3. Completar migración JSP → React  
4. CI dependency scan (OWASP Dependency-Check / Snyk)  
5. Headers de seguridad + cookies endurecidas  
6. Auditoría de historial git por secretos  

---

## Aspectos positivos

- `struts.enable.DynamicMethodInvocation=false`  
- Interceptor de auth en package `default`  
- React sin `dangerouslySetInnerHTML`  
- Mayoría de SQL vía `JdbcTemplate` parametrizado  
- React no guarda credenciales en `localStorage`  

---

*Este informe no sustituye un pentest ni un scan automatizado de CVEs en CI.*
