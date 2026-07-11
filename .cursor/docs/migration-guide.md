# Guía de migración JSP → React

Objetivo: reemplazar pantallas JSP/jqGrid por React sin reescribir el backend.

## Estrategia

1. Identificar action(s) Struts que ya alimentan la pantalla.
2. Tipar respuesta en `src/types/<entidad>.ts`.
3. Envolver llamadas en `src/api/<entidad>.ts` con el `client` axios.
4. Implementar página siguiendo un patrón existente.
5. Registrar ruta + ítem de navegación.
6. Dejar el JSP intacto hasta validar paridad funcional.

## Patrones de UI

### A — CRUD lista + modal

Referencia: `CamionesPage.tsx`, `EmpresasPage.tsx`

- DataGrid server pagination/sort
- Dialog + react-hook-form
- ConfirmDialog para delete
- `useQuery` / `useMutation` + invalidate

### B — Lista + panel de acciones

Referencia: `CarpetasPage.tsx`

- Click en fila → Card de acciones (editar, PDFs, histórico)
- Navegación a formulario full-page

### C — Formulario legacy complejo

Referencia: `CarpetasFormPage.tsx`

- Accordion por sección
- `LegacyAutocompleteField` para `jsonLista*`
- Params con nombres Struts anidados (`carpeta.foo.bar`)
- Respuestas HTML: parsear éxito/error

## Checklist nueva entidad

- [ ] `types/<entidad>.ts` (entity, form, defaults)
- [ ] `api/<entidad>.ts` (`GridResponse`, `PaginationParams`)
- [ ] `pages/<Entidad>Page.tsx`
- [ ] Ruta en `App.tsx`
- [ ] `NAV_ITEMS` en `Layout.tsx`
- [ ] Verificar nombres de campos vs action Java
- [ ] Fechas en formato que espere Struts
- [ ] Probar con sesión real (cookie)

## Qué no hacer

- No crear controllers Spring MVC / REST paralelos “por limpieza” sin acuerdo.
- No poner `fetch`/`axios` fuera de `src/api/`.
- No asumir JSON limpio: muchas actions legacy devuelven HTML o redirects.
- No romper el contrato jqGrid (`gridModel`, `oper`, etc.) si otras pantallas lo usan.

## Estado actual

| Área | Estado |
|------|--------|
| Empresas / Transportadoras / Camiones / Terminales | CRUD React maduro |
| Carpetas | Lista + form parcial (frontera de migración) |
| Reportes | Siguen en Jasper vía `.action` |
| Resto JSP | Pendiente |
