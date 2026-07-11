Compilar y desplegar el backend en Tomcat local.

Seguí la skill del repo `.cursor/skills/deploy-tomcat/SKILL.md`:

1. Verificar servicio Windows `Tomcat9` (Running/Stopped).
2. Si está parado, `Start-Service Tomcat9`; si falla por permisos, pedirme que lo levante yo.
3. No uses `catalina.bat` / `startup.bat` / `shutdown.bat`.
4. `mvn package` de `transtemare-web` (e `install` de core si hace falta).
5. Copiar el WAR a `webapps` (parar/arrancar el servicio solo si hace falta).
6. Smoke en `http://localhost:8080/transtemare-web/`.
