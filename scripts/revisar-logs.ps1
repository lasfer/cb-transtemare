# Revisar logs de Tomcat 6 y transtemare-web
# Uso: .\revisar-logs.ps1 [-Tail 50] [-SoloErrores]

param(
    [int]$Tail = 30,
    [switch]$SoloErrores
)

$tomcatLogs = "C:\Program Files\Apache Software Foundation\Tomcat 6.0\logs"
$appLog = "C:\Program Files\Apache Software Foundation\Tomcat 6.0\log\transtemare-web-error.log"

Write-Host "=== Tomcat 6 - Logs (ultimas $Tail lineas) ===" -ForegroundColor Cyan

if (-not $SoloErrores) {
    $catalina = Get-ChildItem $tomcatLogs -Filter "catalina.*.log" | Sort-Object LastWriteTime -Descending | Select-Object -First 1
    if ($catalina) {
        Write-Host "`n--- $($catalina.Name) ---" -ForegroundColor Yellow
        Get-Content $catalina.FullName -Tail $Tail -Encoding Default
    }
}

if (Test-Path $appLog) {
    Write-Host "`n--- transtemare-web-error.log ---" -ForegroundColor Yellow
    Get-Content $appLog -Tail $Tail -Encoding Default
}
