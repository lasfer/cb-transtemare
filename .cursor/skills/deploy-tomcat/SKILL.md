---
name: deploy-tomcat
description: >-
  Compila el WAR de transtemare-web y lo despliega en Tomcat vía el servicio
  Windows Tomcat9 (nunca catalina.bat). Usar cuando el usuario pida compilar,
  empaquetar, deployar, redeploy, levantar Tomcat, o publicar el backend local.
---

# Deploy Tomcat (Windows service)

## Defaults (esta máquina)

| Item | Valor |
|------|--------|
| Servicio | `Tomcat9` (`Apache Tomcat 9.0 Tomcat9`) |
| `CATALINA_HOME` | `C:\Program Files\Apache Software Foundation\Tomcat 9.0` |
| WAR | `transtemare-web/target/transtemare-web.war` → `%CATALINA_HOME%\webapps\transtemare-web.war` |
| URL | `http://localhost:8080/transtemare-web/` |
| JDK | 8 (`JAVA_HOME` / Adoptium JDK 8) |

## Prohibido

- **No** usar `catalina.bat` / `startup.bat` / `shutdown.bat`
- **No** matar procesos `java` a ciegas para “reiniciar” Tomcat
- Preferir siempre el **servicio Windows** `Tomcat9`

## Checklist

```
- [ ] 1. Estado del servicio Tomcat9
- [ ] 2. Arrancar servicio si hace falta (o pedir al usuario)
- [ ] 3. mvn package del WAR
- [ ] 4. Copiar WAR (parar servicio si el copy falla por archivo bloqueado)
- [ ] 5. Asegurar servicio Running + smoke HTTP
```

### 1. Verificar servicio

```powershell
Get-Service Tomcat9 | Format-List Name, Status, StartType
```

### 2. Si está Stopped

Intentar:

```powershell
Start-Service Tomcat9
```

Si falla por permisos (`Access is denied` / necesita elevación): **parar y pedirle al usuario** que inicie el servicio (Services / PowerShell admin: `Start-Service Tomcat9`). No inventar workarounds con `.bat`.

### 3. Compilar

```powershell
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-8.0.482.8-hotspot"  # ajustar si cambió
$env:Path = "$env:JAVA_HOME\bin;" + $env:Path
cd transtemare-core; mvn clean install -DskipTests
cd ../transtemare-web; mvn clean package -DskipTests
```

(Si core no cambió, alcanza `mvn package` en `transtemare-web`.)

### 4. Desplegar WAR

Si el servicio está Running y el copy falla (WAR/exploded bloqueado):

```powershell
Stop-Service Tomcat9
# opcional: quitar webapps\transtemare-web exploded si quedó viejo
Copy-Item -Force transtemare-web\target\transtemare-web.war "$env:CATALINA_HOME\webapps\transtemare-web.war"
Start-Service Tomcat9
```

Si ya estaba Stopped: copiar WAR y luego `Start-Service Tomcat9`.

### 5. Smoke

```powershell
Get-Service Tomcat9
Invoke-WebRequest -Uri "http://localhost:8080/transtemare-web/" -UseBasicParsing -TimeoutSec 15
```

Logs útiles: `%CATALINA_HOME%\logs\catalina*.log` y `%CATALINA_HOME%\log\transtemare-web.log` (si existe).

## Respuesta al usuario

Ser breve: servicio (Running/Stopped), build OK/fail, WAR copiado, URL smoke. Si hace falta admin para el servicio, decirlo explícitamente.
