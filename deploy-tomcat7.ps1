# Copia el WAR de transtemare-web a la carpeta webapps de una instalacion Tomcat 7.
# Uso:
#   .\deploy-tomcat7.ps1
#   .\deploy-tomcat7.ps1 -TomcatHome "C:\apache-tomcat-7.0.109"
#   .\deploy-tomcat7.ps1 -TomcatHome "C:\apache-tomcat-7.0.109" -ContextPath "transtemare"

param(
    [Parameter(Mandatory=$false)]
    [string]$TomcatHome = $env:CATALINA_HOME,
    [string]$ContextPath = "transtemare-web"
)

$ErrorActionPreference = "Stop"
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$warPath = Join-Path $scriptDir "transtemare-web\target\transtemare-web.war"

if (-not (Test-Path $warPath)) {
    Write-Host "No se encuentra el WAR. Ejecuta antes: mvn clean package -DskipTests -pl transtemare-web -am" -ForegroundColor Red
    exit 1
}

if (-not $TomcatHome -or -not (Test-Path $TomcatHome)) {
    Write-Host "Indica la ruta de Tomcat 7 (CATALINA_HOME o parametro -TomcatHome)." -ForegroundColor Red
    Write-Host "Ejemplo: .\deploy-tomcat7.ps1 -TomcatHome `"C:\apache-tomcat-7.0.109`"" -ForegroundColor Yellow
    exit 1
}

$webapps = Join-Path $TomcatHome "webapps"
if (-not (Test-Path $webapps)) {
    Write-Host "No existe la carpeta webapps en: $TomcatHome" -ForegroundColor Red
    exit 1
}

# Nombre del archivo WAR en webapps define el context path
# transtemare-web.war -> http://localhost:8080/transtemare-web/
# transtemare.war     -> http://localhost:8080/transtemare/
$destWar = Join-Path $webapps "$ContextPath.war"
Copy-Item -Path $warPath -Destination $destWar -Force
Write-Host "WAR copiado a: $destWar" -ForegroundColor Green
Write-Host "Context path: /$ContextPath" -ForegroundColor Cyan
Write-Host "Arranca Tomcat 7 y abre: http://localhost:8080/$ContextPath/" -ForegroundColor Cyan
