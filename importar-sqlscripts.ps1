# Aplica los scripts SQL de transtemare-core/sqlscripts/ sobre la base skuncadb (orden cronológico).
# Requiere: contenedor transtemare-mysql en ejecución y base skuncadb existente (ej. tras importar-respaldo.ps1).
# Uso:
#   .\importar-sqlscripts.ps1
#   .\importar-sqlscripts.ps1 -IncluirRespaldo   # importa primero respaldo.sql y luego los scripts

param(
    [string]$ContainerName = "transtemare-mysql",
    [switch]$IncluirRespaldo,
    [string]$RespaldoSql = ""
)

$ErrorActionPreference = "Stop"
$repoRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
if ([string]::IsNullOrEmpty($RespaldoSql)) {
    $RespaldoSql = Join-Path $repoRoot "respaldo.sql"
    if (-not (Test-Path $RespaldoSql)) { $RespaldoSql = Join-Path (Split-Path -Parent $repoRoot) "respaldo.sql" }
}
$sqlscriptsDir = Join-Path $repoRoot "transtemare-core\sqlscripts"
if (-not (Test-Path $sqlscriptsDir)) {
    $sqlscriptsDir = Join-Path (Split-Path -Parent $repoRoot) "transtemare-core\sqlscripts"
}
if (-not (Test-Path $sqlscriptsDir)) {
    Write-Host "No se encuentra la carpeta sqlscripts (buscado en repo y carpeta padre)." -ForegroundColor Red
    exit 1
}

$running = docker inspect -f '{{.State.Running}}' $ContainerName 2>$null
if ($running -ne "true") {
    Write-Host "El contenedor $ContainerName no está en ejecución." -ForegroundColor Red
    Write-Host "Inicia MySQL con: docker compose up -d" -ForegroundColor Yellow
    exit 1
}

if ($IncluirRespaldo) {
    if (-not (Test-Path $RespaldoSql)) {
        Write-Host "No se encuentra respaldo: $RespaldoSql" -ForegroundColor Red
        exit 1
    }
    Write-Host "Importando respaldo.sql..." -ForegroundColor Cyan
    $dest = "/tmp/respaldo_import.sql"
    docker cp (Resolve-Path $RespaldoSql).Path "${ContainerName}:${dest}"
    docker exec $ContainerName mysql -uroot -proot -e "CREATE DATABASE IF NOT EXISTS skuncadb CHARACTER SET utf8 COLLATE utf8_unicode_ci;"
    docker exec $ContainerName sh -c "mysql -uroot -proot skuncadb < $dest"
    docker exec $ContainerName rm -f $dest
    Write-Host "Respaldo importado." -ForegroundColor Green
}

# Scripts en orden cronológico (prefijo YYYYMMDD)
$scripts = Get-ChildItem -Path $sqlscriptsDir -Filter "*.sql" | Sort-Object Name
if ($scripts.Count -eq 0) {
    Write-Host "No hay archivos .sql en $sqlscriptsDir" -ForegroundColor Yellow
    exit 0
}

Write-Host "Aplicando $($scripts.Count) script(s) sobre skuncadb..." -ForegroundColor Cyan
foreach ($f in $scripts) {
    Write-Host "  - $($f.Name)" -ForegroundColor Gray
    $destInContainer = "/tmp/sqlscript_$($f.Name)"
    docker cp $f.FullName "${ContainerName}:${destInContainer}"
    # --force sigue aunque haya errores (p. ej. columnas ya existentes si se re-ejecuta)
    $prevErr = $ErrorActionPreference
    $ErrorActionPreference = "Continue"
    docker exec $ContainerName sh -c "mysql -uroot -proot --force skuncadb < $destInContainer" 2>&1 | ForEach-Object { Write-Host $_ }
    $ErrorActionPreference = $prevErr
    docker exec $ContainerName rm -f $destInContainer
}
Write-Host "Scripts aplicados correctamente." -ForegroundColor Green
