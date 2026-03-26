# Base de datos MySQL para Transtemare

**Importante:** El respaldo (`respaldo.sql`) fue generado con **MySQL 5.0.77** y la tabla `carpeta` usa `ROW_FORMAT=FIXED`, que **no está soportado en MySQL 5.7+**. Por eso el compose usa **MySQL 5.5**.

La aplicación está configurada para conectarse a MySQL con:

| Parámetro | Valor (por defecto en applicationContext.xml) |
|-----------|-----------------------------------------------|
| Host      | localhost                                    |
| Puerto    | 3306                                         |
| Base de datos | skuncadb                                |
| Usuario   | root                                         |
| Contraseña| root                                         |

---

## Opción 1: Docker (recomendada para desarrollo)

Si tienes [Docker Desktop](https://www.docker.com/products/docker-desktop/) instalado:

```powershell
cd c:\Users\ferna\OneDrive\Documents\transtemare\cb-transtemare
docker compose -f docker-compose-mysql.yml up -d
```

- MySQL 5.7 queda corriendo en **localhost:3306**.
- La base de datos **skuncadb** se crea automáticamente al arrancar.
- Para **importar el respaldo**: copia `respaldo.sql` en la carpeta del proyecto (`cb-transtemare`) y ejecuta:
  ```powershell
  .\importar-respaldo.ps1
  ```
  O indica la ruta del archivo: `.\importar-respaldo.ps1 -RespaldoSql "C:\ruta\respaldo.sql"`
- Los datos se guardan en un volumen (persisten al reiniciar el contenedor).

Para parar:

```powershell
docker compose -f docker-compose-mysql.yml down
```

Para ver logs:

```powershell
docker compose -f docker-compose-mysql.yml logs -f mysql
```

---

## Opción 2: MySQL instalado en Windows

1. **Descargar e instalar**
   - [MySQL Community Server](https://dev.mysql.com/downloads/mysql/) (por ejemplo 5.7 u 8.0), o
   - [XAMPP](https://www.apachefriends.org/) (incluye MySQL y phpMyAdmin).

2. **Crear la base de datos y usuario**
   - Con XAMPP: arranca MySQL desde el panel de control y usa phpMyAdmin en `http://localhost/phpmyadmin`.
   - Con MySQL instalado solo, en consola:

   ```bash
   mysql -u root -p
   ```

   Luego en MySQL:

   ```sql
   CREATE DATABASE skuncadb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   -- Si quieres mantener user root y contraseña root, configúrala con:
   ALTER USER 'root'@'localhost' IDENTIFIED BY 'root';
   ```

3. **Scripts SQL del proyecto**  
   En el proyecto hay scripts en `transtemare-core/sqlscripts/` (parches/migraciones). Si tienes un script de creación inicial de tablas, ejecútalo sobre `skuncadb`. Si no, tendrás que crearlas según el modelo de la aplicación o usar un backup/dump existente.

---

## Opción 3: MySQL en la nube

Para un entorno más parecido a producción puedes usar:

- **Oracle MySQL Cloud** o **Azure Database for MySQL**
- Crear una base de datos, anotar host, puerto, usuario y contraseña, y actualizar `transtemare-web/src/main/webapp/WEB-INF/applicationContext.xml` (o un archivo de propiedades que use la app) con la URL JDBC y credenciales correctas.

---

## Comprobar la conexión

Desde PowerShell (con MySQL en localhost):

```powershell
# Con Docker
docker exec -it transtemare-mysql mysql -uroot -proot -e "SELECT 1; USE skuncadb; SHOW TABLES;"

# Con MySQL instalado localmente
mysql -h localhost -P 3306 -u root -proot -e "SELECT 1; USE skuncadb; SHOW TABLES;"
```

Si la base está vacía, `SHOW TABLES` no mostrará tablas hasta que ejecutes los scripts de creación o importes un dump.
