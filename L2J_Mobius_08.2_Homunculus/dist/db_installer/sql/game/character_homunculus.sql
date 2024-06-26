CREATE TABLE IF NOT EXISTS `character_homunculus` (
	`ownerId` INT NOT NULL,
	`slot` TINYINT UNSIGNED NOT NULL DEFAULT '0',
	`id` TINYINT UNSIGNED NOT NULL,
	`level` TINYINT UNSIGNED NOT NULL DEFAULT '1',
	`exp` INT UNSIGNED NOT NULL DEFAULT '0',
	`skillLevel1` TINYINT UNSIGNED NOT NULL DEFAULT '0',
	`skillLevel2` TINYINT UNSIGNED NOT NULL DEFAULT '0',
	`skillLevel3` TINYINT UNSIGNED NOT NULL DEFAULT '0',
	`skillLevel4` TINYINT UNSIGNED NOT NULL DEFAULT '0',
	`skillLevel5` TINYINT UNSIGNED NOT NULL DEFAULT '0',
	`active` TINYINT UNSIGNED NOT NULL DEFAULT '0'
) DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;