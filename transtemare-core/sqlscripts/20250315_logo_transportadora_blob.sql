-- Logo de transportadora como BLOB (upload) en lugar de solo nombre de archivo en classpath.
USE `skuncadb`;
ALTER TABLE `transportadora` ADD COLUMN `imagen_logo` LONGBLOB NULL AFTER `imagen`;
ALTER TABLE `transportadora` ADD COLUMN `imagen_content_type` VARCHAR(100) NULL AFTER `imagen_logo`;
