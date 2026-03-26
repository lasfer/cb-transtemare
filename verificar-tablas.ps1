# Comprueba que la base skuncadb existe y lista las tablas (contenedor Docker).
# Uso: .\verificar-tablas.ps1
# O:   powershell -ExecutionPolicy Bypass -File .\verificar-tablas.ps1

$ContainerName = "transtemare-mysql"

$running = docker inspect -f '{{.State.Running}}' $ContainerName 2>$null
if ($running -ne "true") {
    Write-Host "El contenedor $ContainerName no esta en ejecucion. Levanta MySQL con:" -ForegroundColor Red
    Write-Host "  docker compose -f docker-compose-mysql.yml up -d" -ForegroundColor Yellow
    exit 1
}

Write-Host "Base de datos: skuncadb" -ForegroundColor Cyan
Write-Host "Tablas:" -ForegroundColor Cyan
Write-Host "-------"
docker exec $ContainerName mysql -uroot -proot -N -e "USE skuncadb; SHOW TABLES;"
if ($LASTEXITCODE -eq 0) {
    $count = (docker exec $ContainerName mysql -uroot -proot -N -e "USE skuncadb; SHOW TABLES;" 2>$null | Measure-Object -Line).Lines
    Write-Host "-------"
    Write-Host "Total: $count tablas" -ForegroundColor Green
} else {
    Write-Host "Error al conectar o la base no existe." -ForegroundColor Red
    exit 1
}
