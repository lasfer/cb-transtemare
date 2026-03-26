# Crea la base skuncadb e importa respaldo.sql en MySQL (contenedor Docker).
# Uso:
#   .\importar-respaldo.ps1
#   .\importar-respaldo.ps1 -RespaldoSql "C:\ruta\respaldo.sql"
# Requiere: Docker con el contenedor transtemare-mysql (docker compose -f docker-compose-mysql.yml up -d)

param(
    [string]$RespaldoSql = (Join-Path (Split-Path -Parent $MyInvocation.MyCommand.Path) "respaldo.sql"),
    [string]$ContainerName = "transtemare-mysql"
)

$ErrorActionPreference = "Stop"

if (-not (Test-Path $RespaldoSql)) {
    Write-Host "No se encuentra el archivo de respaldo: $RespaldoSql" -ForegroundColor Red
    Write-Host "Copia respaldo.sql en la carpeta del proyecto o indica la ruta: .\importar-respaldo.ps1 -RespaldoSql `"C:\ruta\respaldo.sql`"" -ForegroundColor Yellow
    exit 1
}

$running = docker inspect -f '{{.State.Running}}' $ContainerName 2>$null
if ($running -ne "true") {
    Write-Host "El contenedor $ContainerName no está en ejecución." -ForegroundColor Red
    Write-Host "Inicia MySQL con: docker compose -f docker-compose-mysql.yml up -d" -ForegroundColor Yellow
    exit 1
}

Write-Host "Creando base de datos skuncadb (si no existe) e importando respaldo..." -ForegroundColor Cyan
$respaldoFullPath = (Resolve-Path $RespaldoSql).Path

# Copiamos el archivo al contenedor y ejecutamos mysql con redirección dentro del contenedor
$destInContainer = "/tmp/respaldo_import.sql"
docker cp $respaldoFullPath "${ContainerName}:${destInContainer}"
if ($LASTEXITCODE -ne 0) {
    Write-Host "Error al copiar el archivo al contenedor." -ForegroundColor Red
    exit 1
}

docker exec $ContainerName mysql -uroot -proot -e "CREATE DATABASE IF NOT EXISTS skuncadb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
docker exec $ContainerName sh -c "mysql -uroot -proot skuncadb < $destInContainer"
docker exec $ContainerName rm -f $destInContainer

Write-Host "Importación completada. Base de datos: skuncadb" -ForegroundColor Green
