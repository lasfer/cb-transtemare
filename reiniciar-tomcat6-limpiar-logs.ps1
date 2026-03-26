# Ejecutar como Administrador: clic derecho -> "Ejecutar con PowerShell como administrador"
# Detiene Tomcat 6, limpia la carpeta log y reinicia el servicio.

$ErrorActionPreference = "Stop"
$tomcatLog = "C:\Program Files\Apache Software Foundation\Tomcat 6.0\log"
$serviceName = "Tomcat6"

Write-Host "Deteniendo $serviceName..." -ForegroundColor Cyan
Stop-Service -Name $serviceName -Force -ErrorAction Stop
Start-Sleep -Seconds 3

Write-Host "Limpiando carpeta log..." -ForegroundColor Cyan
if (Test-Path $tomcatLog) {
    Get-ChildItem $tomcatLog -File | Remove-Item -Force
    Write-Host "Archivos en log eliminados." -ForegroundColor Green
} else {
    Write-Host "Carpeta log no encontrada: $tomcatLog" -ForegroundColor Yellow
}

Write-Host "Iniciando $serviceName..." -ForegroundColor Cyan
Start-Service -Name $serviceName -ErrorAction Stop
Write-Host "Listo. Tomcat 6 reiniciado y logs limpiados." -ForegroundColor Green
