# Documentación Cursor — Transtemare

Documentación y reglas para agentes/desarrolladores que trabajan en este monorepo.

## Reglas (`.cursor/rules/`)

| Archivo | Alcance |
|---------|---------|
| `project-overview.mdc` | Siempre — visión del monorepo |
| `react-frontend.mdc` | `transtemare-react/**` |
| `java-backend.mdc` | `transtemare-core/**`, `transtemare-web/**` |
| `struts-api-contract.mdc` | API React ↔ acciones Struts |
| `domain-carpetas.mdc` | Dominio carpetas / CRT / MICDTA |

## Docs

| Archivo | Contenido |
|---------|-----------|
| [architecture.md](./architecture.md) | Capas, auth, persistencia, reportes |
| [local-dev.md](./local-dev.md) | Cómo levantar DB, WAR y SPA |
| [api-contract.md](./api-contract.md) | Endpoints Struts usados por React |
| [migration-guide.md](./migration-guide.md) | Cómo migrar pantallas JSP → React |
| [presentacion-sistema.html](./presentacion-sistema.html) | Presentación HTML (funcional + técnico) |
| [security-audit.md](./security-audit.md) | Auditoría de seguridad (estática) |
| [code-quality-audit.md](./code-quality-audit.md) | Calidad de código / FindBugs-style |

## Inicio rápido

1. Leer `project-overview` (regla always-on).
2. Según el archivo que edites, se activan las reglas por glob.
3. Para contexto profundo, abrir la doc correspondiente en esta carpeta.
