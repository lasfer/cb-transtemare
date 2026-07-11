# Transtemare — Code Quality / FindBugs-style Audit

**Fecha:** 2026-07-10  
**Alcance:** `transtemare-core`, `transtemare-web`, `transtemare-react`  
**Método:** Revisión estática tipo FindBugs/SpotBugs + `npm run lint`  
**Nota:** No hay SpotBugs/PMD configurado en Maven aún; este informe es el baseline.

---

## Resumen

| Área | Veredicto |
|------|-----------|
| React ABM maduro | Bueno (Camiones/Empresas/Terminales) |
| Carpetas React | **Critical** — edit sin load |
| Paginación Java grids | **High** — `LIMIT` mal usado |
| Reportes PDF | **High** — NPE / SUCCESS tras error |
| ESLint | 3 errors, 1 warning |

---

## Hallazgos prioritarios

| Sev | Área | Ubicación | Hallazgo | Recomendación |
|-----|------|-----------|----------|---------------|
| Critical | React | `CarpetasFormPage.tsx` | Edición solo setea `idCarpeta`; form vacío; save puede pisar carpeta. | `GET` carpeta + `reset(data)` antes de editar; bloquear save hasta load. |
| High | Java | `JSONCarpetas.java:44-45` (+ grids) | `to = rows * page` pasado a `LIMIT ?,?` (offset, **count**). Página 2 pide el doble de filas. | Pasar `(from, rows)`; validar `page >= 1`. |
| High | Java | `CRT.java` / `MICDTA.java` | `c` null tras fallo de carga → NPE en `cargarParametros`; `SUCCESS` igual. | Early `ERROR` si `c == null`; null-safe nested getters. |
| High | Java | `CrearCarpeta.java:185-216` | Save fallido (`check == false`) retorna `SUCCESS`. | Retornar `ERROR` / `INPUT`. |
| High | Java | `Utils.java` check-digit | ISO 6346: `total % 11 == 10` debe equivaler a dígito `0`. | Mapear resto 10 → 0 antes de comparar. |
| High | React | `CarpetasPage.tsx` | Histórico sin confirmación; sin `invalidateQueries`. | `ConfirmDialog` + invalidar `['carpetas']`. |
| Medium | Java | CRT/MICDTA `static jasperReport` | Mutación de estilos en report estático → race. | Clonar report por request o solo parámetros. |
| Medium | Java | `Utils.obtenerId` | Fallo de parse → `0` silencioso. | Fallar explícito / Optional; rechazar 0 en FKs requeridos. |
| Medium | Java | CRT/MICDTA `split(Utils.ID)[1]` | Sin check de length → AIOOBE. | Parser seguro. |
| Medium | Java | `SQLComunes` | `INERT INTO` typo (código muerto). | Corregir o eliminar. |
| Medium | React | `carpetas.ts` `crearNuevaCarpeta` | ID desde `responseURL` post-redirect. | Respuesta JSON con `idCarpeta`. |
| Medium | React | `TransportadorasPage.tsx` | `Date.now()` en render; blob URL leak; setState en effect. | Inicializar cache bust lazy; `revokeObjectURL`; key en img. |
| Medium | React | `CarpetasFormPage` | Select sin `FormControl`/`labelId`; options sin error UI. | Alinear a CamionesPage. |
| Low | Java | Fachadas | `FachadaException(msg)` pierde cause. | Constructor con cause. |
| Low | Quality | God classes | `Carpeta` ~740L, `CrearCarpeta`/`CRT`/`MICDTA` ~400L. | Extraer mappers/VOs. |

---

## ESLint (`npm run lint`)

| Nivel | Archivo | Regla |
|-------|---------|-------|
| error | `AuthContext.tsx:61` | `react-refresh/only-export-components` |
| error | `TransportadorasPage.tsx:77` | `react-hooks/purity` — `Date.now()` en render |
| error | `TransportadorasPage.tsx:643` | `react-hooks/set-state-in-effect` |
| warning | `CarpetasFormPage.tsx:83` | `react-hooks/incompatible-library` — `watch()` |

---

## No encontrado (positivo)

- Catch vacíos en Java  
- Comparación de Strings con `==` en auth/search  
- Leaks JDBC manuales (JdbcTemplate)  
- `any` / `eslint-disable` en React  
- Loops infinitos / `useEffect` problemáticos graves (salvo Transportadoras logo)  
- Patrón ABM Camiones/Empresas/Terminales consistente  

---

## Prioridad de remediación

1. Cargar carpeta en edit (`CarpetasFormPage`)  
2. Fix `LIMIT` en todos los JSON grids  
3. Guards NPE + fail-fast en CRT/MICDTA  
4. `CrearCarpeta` → ERROR si falla save  
5. Histórico: confirm + invalidate  
6. Check-digit contenedor  
7. Limpiar ESLint Transportadoras  

## Siguiente paso tooling

Agregar a Maven (cuando haya Java 8+/11+):

- SpotBugs / Error Prone  
- Checkstyle o PMD (opcional)  
- CI: `mvn verify` + `npm run lint`  

---

*Informe companion del security-audit.md. Canvas: code-quality-audit.*
