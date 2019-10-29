-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema MyMusic
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema MyMusic
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `MyMusic` DEFAULT CHARACTER SET utf8 ;
USE `MyMusic` ;

-- -----------------------------------------------------
-- Table `MyMusic`.`Album`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyMusic`.`Album` (
	`album_id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(45) NOT NULL,
	`imagePath` VARCHAR(100) NULL,
	`genre` VARCHAR(45) NULL,
	`year` INT NULL,
	`rating` FLOAT NULL,
	PRIMARY KEY (`album_id`),
	UNIQUE INDEX `album_id_UNIQUE` (`album_id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MyMusic`.`Track`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyMusic`.`Track` (
	`track_id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(45) NOT NULL,
	`genre` VARCHAR(45) NULL,
	`plays` INT NULL DEFAULT 0,
	`time` TIME NULL,
	`album_id` INT NULL,
	INDEX `fk_Track_Album1_idx` (`album_id` ASC),
	UNIQUE INDEX `track_id_UNIQUE` (`track_id` ASC),
	PRIMARY KEY (`track_id`),
	CONSTRAINT `fk_Track_Album1`
		FOREIGN KEY (`album_id`)
		REFERENCES `MyMusic`.`Album` (`album_id`)
		ON DELETE SET NULL
		ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MyMusic`.`Artist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyMusic`.`Artist` (
	`artist_id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(45) NOT NULL,
	`imagePath` VARCHAR(100) NULL,
	`rating` FLOAT NULL,
	PRIMARY KEY (`artist_id`),
	UNIQUE INDEX `artist_id_UNIQUE` (`artist_id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MyMusic`.`Album_has_Artist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyMusic`.`Album_has_Artist` (
	`album_id` INT NOT NULL,
	`artist_id` INT NOT NULL,
	PRIMARY KEY (`album_id`, `artist_id`),
	INDEX `fk_Album_has_Artist_Artist1_idx` (`artist_id` ASC),
	CONSTRAINT `fk_Album_has_Artist_Album1`
		FOREIGN KEY (`album_id`)
		REFERENCES `MyMusic`.`Album` (`album_id`)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	CONSTRAINT `fk_Album_has_Artist_Artist1`
		FOREIGN KEY (`artist_id`)
		REFERENCES `MyMusic`.`Artist` (`artist_id`)
		ON DELETE CASCADE
		ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MyMusic`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyMusic`.`User` (
	`user_id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(45) NOT NULL,
	`username` VARCHAR(45) NOT NULL,
	`password` VARCHAR(45) NOT NULL,
	`admin` TINYINT(1) NOT NULL,
	PRIMARY KEY (`user_id`),
	UNIQUE INDEX `username_UNIQUE` (`username` ASC),
	UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MyMusic`.`Playlist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyMusic`.`Playlist` (
	`playlist_id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(45) NOT NULL,
	`user_id` INT NOT NULL,
	PRIMARY KEY (`playlist_id`),
	INDEX `fk_Playlist_User1_idx` (`user_id` ASC),
	UNIQUE INDEX `playlist_id_UNIQUE` (`playlist_id` ASC),
	CONSTRAINT `fk_Playlist_User1`
		FOREIGN KEY (`user_id`)
		REFERENCES `MyMusic`.`User` (`user_id`)
		ON DELETE CASCADE
		ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MyMusic`.`Playlist_has_Track`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyMusic`.`Playlist_has_Track` (
	`playlist_id` INT NOT NULL,
	`track_id` INT NOT NULL,
	PRIMARY KEY (`playlist_id`, `track_id`),
	INDEX `fk_Playlist_has_Track_Track1_idx` (`track_id` ASC),
	CONSTRAINT `fk_Playlist_has_Track_Playlist1`
		FOREIGN KEY (`playlist_id`)
		REFERENCES `MyMusic`.`Playlist` (`playlist_id`)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	CONSTRAINT `fk_Playlist_has_Track_Track1`
		FOREIGN KEY (`track_id`)
		REFERENCES `MyMusic`.`Track` (`track_id`)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MyMusic`.`Track_has_Artist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyMusic`.`Track_has_Artist` (
	`track_id` INT NOT NULL,
	`artist_id` INT NOT NULL,
	PRIMARY KEY (`track_id`, `artist_id`),
	INDEX `fk_Track_has_Artist_Artist1_idx` (`artist_id` ASC),
	CONSTRAINT `fk_Track_has_Artist_Track1`
		FOREIGN KEY (`track_id`)
		REFERENCES `MyMusic`.`Track` (`track_id`)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	CONSTRAINT `fk_Track_has_Artist_Artist1`
		FOREIGN KEY (`artist_id`)
		REFERENCES `MyMusic`.`Artist` (`artist_id`)
		ON DELETE CASCADE
		ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
