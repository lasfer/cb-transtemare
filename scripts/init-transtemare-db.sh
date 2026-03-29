#!/usr/bin/env bash
# Uso: desde la raíz del repo: bash scripts/init-transtemare-db.sh [--recreate]
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
COMPOSE="${ROOT}/docker-compose.yml"
CONTAINER="transtemare-mysql"
RECREATE=false

for arg in "$@"; do
  if [[ "$arg" == "--recreate" ]]; then RECREATE=true; fi
done

cd "$ROOT"
if [[ "$RECREATE" == true ]]; then
  echo "Recreando volumen..."
  docker compose -f "$COMPOSE" down -v
fi

echo "Iniciando MySQL..."
docker compose -f "$COMPOSE" up -d

_start=$(date +%s)
until docker exec "$CONTAINER" mysqladmin ping -h localhost -uroot -proot --silent 2>/dev/null; do
  if (( $(date +%s) - _start > 180 )); then
    echo "Timeout esperando MySQL"
    exit 1
  fi
  sleep 3
done

echo "Importando respaldo.sql..."
docker exec -i "$CONTAINER" mysql -uroot -proot skuncadb < "${ROOT}/respaldo.sql"

if [[ -d "${ROOT}/transtemare-core/sqlscripts" ]]; then
  for f in $(ls "${ROOT}/transtemare-core/sqlscripts"/*.sql 2>/dev/null | sort); do
    echo "Ejecutando: $(basename "$f")"
    docker exec -i "$CONTAINER" mysql -uroot -proot skuncadb < "$f"
  done
fi

echo "Listo. jdbc:mysql://localhost:3306/skuncadb"
