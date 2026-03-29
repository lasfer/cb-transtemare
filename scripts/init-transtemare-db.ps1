<#
.SYNOPSIS
  Levanta MySQL (Tomcat/Transtemare) con Docker Compose y carga respaldo.sql + scripts de transtemare-core/sqlscripts.

.PARAMETER Recreate
  docker compose down -v antes de subir el contenedor (volumen limpio). Recomendado la primera vez o para reset completo.

.PARAMETER ComposeFile
  Archivo compose (por defecto docker-compose.yml en la raíz del repo).

.PARAMETER SkipRespaldo
  No importa respaldo.sql (solo scripts extra).

.PARAMETER SkipScripts
  No ejecuta los .sql de transtemare-core/sqlscripts.
#>

param(
    [switch]$Recreate,
    [string]$ComposeFile = "",
    [switch]$SkipRespaldo,
    [switch]$SkipScripts
)

$ErrorActionPreference = "Stop"
$Root = Split-Path -Parent $PSScriptRoot
if (-not (Test-Path (Join-Path $Root "docker-compose.yml"))) {
    Write-Error "No se encuentra docker-compose.yml en $Root (ejecuta el script desde el repo cb-transtemare)."
}

if (-not $ComposeFile) {
    $ComposeFile = Join-Path $Root "docker-compose.yml"
}

if (-not (Test-Path $ComposeFile)) {
    Write-Error "No se encuentra docker-compose: $ComposeFile"
}

Push-Location $Root
try {
    if ($Recreate) {
        Write-Host "Recreando volumen (down -v)..." -ForegroundColor Yellow
        docker compose -f $ComposeFile down -v
    }

    Write-Host "Iniciando contenedor MySQL..." -ForegroundColor Cyan
    docker compose -f $ComposeFile up -d

    $container = "transtemare-mysql"
    $deadline = (Get-Date).AddMinutes(3)
    Write-Host "Esperando a MySQL ($container)..." -ForegroundColor Cyan
    do {
        $ok = $false
        docker exec $container mysqladmin ping -h localhost -uroot -proot 2>$null | Out-Null
        if ($LASTEXITCODE -eq 0) { $ok = $true; break }
        if ((Get-Date) -gt $deadline) {
            Write-Error "Timeout esperando MySQL. Revisa: docker logs $container"
        }
        Start-Sleep -Seconds 3
    } while (-not $ok)

    Write-Host "MySQL listo." -ForegroundColor Green

    if (-not $SkipRespaldo) {
        $respaldo = Join-Path $Root "respaldo.sql"
        if (-not (Test-Path $respaldo)) {
            Write-Error "Falta respaldo.sql en: $respaldo"
        }
        Write-Host "Importando respaldo.sql (puede tardar varios minutos)..." -ForegroundColor Cyan
        $r = [System.IO.Path]::GetFullPath($respaldo)
        $code = cmd /c "docker exec -i $container mysql -uroot -proot skuncadb < `"$r`""
        if ($code -ne 0) { Write-Error "Fallo importando respaldo.sql (codigo $code)" }
        Write-Host "respaldo.sql importado." -ForegroundColor Green
    }

    if (-not $SkipScripts) {
        $scriptsDir = Join-Path $Root "transtemare-core\sqlscripts"
        if (Test-Path $scriptsDir) {
            $files = Get-ChildItem $scriptsDir -Filter "*.sql" | Sort-Object Name
            foreach ($f in $files) {
                Write-Host "Ejecutando: $($f.Name) ..." -ForegroundColor Cyan
                $full = $f.FullName
                $code = cmd /c "docker exec -i $container mysql -uroot -proot skuncadb < `"$full`""
                if ($code -ne 0) { Write-Error "Fallo en $($f.Name) (codigo $code)" }
            }
            Write-Host "Scripts adicionales aplicados." -ForegroundColor Green
        } else {
            Write-Host "No existe $scriptsDir ; se omiten migraciones." -ForegroundColor Yellow
        }
    }

    Write-Host "`nListo. JDBC local: jdbc:mysql://localhost:3306/skuncadb (root/root)" -ForegroundColor Green
}
finally {
    Pop-Location
}
