---
-- #%L
-- Naonedbus-server
-- %%
-- Copyright (C) 2010 - 2013 Naonedbus
-- %%
-- This program is free software: you can redistribute it and/or modify
-- it under the terms of the GNU General Public License as
-- published by the Free Software Foundation, either version 3 of the 
-- License, or (at your option) any later version.
-- 
-- This program is distributed in the hope that it will be useful,
-- but WITHOUT ANY WARRANTY; without even the implied warranty of
-- MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
-- GNU General Public License for more details.
-- 
-- You should have received a copy of the GNU General Public 
-- License along with this program.  If not, see
-- <http://www.gnu.org/licenses/gpl-3.0.html>.
-- #L%
---
CREATE TABLE `horaire` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`terminus` VARCHAR(255),
	`timestamp` BIGINT(20) NOT NULL,
	`id_arret` INT(11) NOT NULL,
	PRIMARY KEY (`id`),
	INDEX `FK41662726B4C47347` (`id_arret`),
	INDEX `idx_horaire_arret` (`id_arret`),
	CONSTRAINT `FK41662726B4C47347` FOREIGN KEY (`id_arret`) REFERENCES `arret` (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

ALTER TABLE `commentaire`  ADD COLUMN `source` VARCHAR(50) NOT NULL AFTER `datePublication`;