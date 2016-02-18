-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema yolaine_hipdev
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema yolaine_hipdev
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `yolaine_hipdev` DEFAULT CHARACTER SET utf8 ;
USE `yolaine_hipdev` ;

-- -----------------------------------------------------
-- Table `yolaine_hipdev`.`type_rue`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yolaine_hipdev`.`type_rue` ;

CREATE TABLE IF NOT EXISTS `yolaine_hipdev`.`type_rue` (
  `type_rue_id` INT(10) NOT NULL AUTO_INCREMENT COMMENT 'Nombre unique pour chaque type de localisation.',
  `description_type_rue` VARCHAR(15) NULL DEFAULT NULL COMMENT 'Indique le nom pour chaque nombre unique de type_rue_id.',
  PRIMARY KEY (`type_rue_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 17
DEFAULT CHARACTER SET = utf8
COMMENT = 'Table permettant de donner un nom précis pour une localisation.';

CREATE UNIQUE INDEX `type_rue_id_UNIQUE` ON `yolaine_hipdev`.`type_rue` (`type_rue_id` ASC);


-- -----------------------------------------------------
-- Table `yolaine_hipdev`.`adresse`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yolaine_hipdev`.`adresse` ;

CREATE TABLE IF NOT EXISTS `yolaine_hipdev`.`adresse` (
  `adresse_id` INT(10) NOT NULL AUTO_INCREMENT COMMENT 'Numéro unique pour chaque adresse.',
  `no_civique` VARCHAR(10) NULL DEFAULT NULL COMMENT 'Numéro civique pour chaque adresse.',
  `type_rue` INT(10) NULL DEFAULT NULL COMMENT 'Nombre correspondant au type_rue de la table type_rue.',
  `nom` VARCHAR(30) NULL DEFAULT NULL COMMENT 'Nom de la localisation complétant type_rue.',
  `app` VARCHAR(10) NULL DEFAULT NULL COMMENT 'Numéro d\'appartiement pour la localisation.',
  `ville` VARCHAR(50) NULL DEFAULT NULL COMMENT 'Nom de la ville.',
  `province` VARCHAR(25) NULL DEFAULT NULL COMMENT 'Nom de la province.',
  `code_postal` VARCHAR(6) NULL DEFAULT NULL COMMENT 'Code postal.',
  `pays` VARCHAR(30) NULL DEFAULT NULL COMMENT 'Pays où se situe l\'adresse.',
  PRIMARY KEY (`adresse_id`),
  CONSTRAINT `fk_type_rue$adresse`
    FOREIGN KEY (`type_rue`)
    REFERENCES `yolaine_hipdev`.`type_rue` (`type_rue_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 27
DEFAULT CHARACTER SET = utf8
COMMENT = 'Table permettant d\'identifier l\'adresse du donneur ou de l\'organisme.';

CREATE UNIQUE INDEX `adresse_id_UNIQUE` ON `yolaine_hipdev`.`adresse` (`adresse_id` ASC);

CREATE INDEX `fk_type_rue$adresse` ON `yolaine_hipdev`.`adresse` (`type_rue` ASC);


-- -----------------------------------------------------
-- Table `yolaine_hipdev`.`marchandise_etat`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yolaine_hipdev`.`marchandise_etat` ;

CREATE TABLE IF NOT EXISTS `yolaine_hipdev`.`marchandise_etat` (
  `etat_id` INT(10) NOT NULL AUTO_INCREMENT COMMENT 'Numéro unique pour chaque état d\'une marchandise.',
  `description_marchandise_etat` VARCHAR(25) NULL DEFAULT NULL COMMENT 'Indique l\'état de la marchandise pour chaque id unique.',
  PRIMARY KEY (`etat_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8
COMMENT = 'Table permettant de mettre un type d\'état pour une marchandise.';

CREATE UNIQUE INDEX `etat_id_UNIQUE` ON `yolaine_hipdev`.`marchandise_etat` (`etat_id` ASC);

CREATE INDEX `ix_marchandise_etat` ON `yolaine_hipdev`.`marchandise_etat` (`description_marchandise_etat` ASC)  COMMENT 'Requête de la liste des marchandises réservé par un organisme (par receveur_id)';


-- -----------------------------------------------------
-- Table `yolaine_hipdev`.`marchandise_statut`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yolaine_hipdev`.`marchandise_statut` ;

CREATE TABLE IF NOT EXISTS `yolaine_hipdev`.`marchandise_statut` (
  `statut_id` INT(10) NOT NULL AUTO_INCREMENT COMMENT 'Numéro unique pour chaque type de statut pour une marchandise.',
  `description_marchandise_statut` VARCHAR(25) NULL DEFAULT NULL COMMENT 'Indique le type de statut pour la marchandise pour chaque statut_id.',
  PRIMARY KEY (`statut_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8
COMMENT = 'Table permettant d\'identifier le type de statut pour la marchandise.';

CREATE UNIQUE INDEX `statut_id_UNIQUE` ON `yolaine_hipdev`.`marchandise_statut` (`statut_id` ASC);


-- -----------------------------------------------------
-- Table `yolaine_hipdev`.`marchandise_unite`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yolaine_hipdev`.`marchandise_unite` ;

CREATE TABLE IF NOT EXISTS `yolaine_hipdev`.`marchandise_unite` (
  `unite_id` INT(10) NOT NULL AUTO_INCREMENT COMMENT 'Numéro unique pour chaque type d\'unité.',
  `description_marchandise_unite` VARCHAR(25) NULL DEFAULT NULL COMMENT 'Indique le type d\'unité pour le produit pour chaque unite_id.',
  PRIMARY KEY (`unite_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8
COMMENT = 'Table permettant de mettre un type de mesure sur la marchandise.';

CREATE UNIQUE INDEX `unite_id_UNIQUE` ON `yolaine_hipdev`.`marchandise_unite` (`unite_id` ASC);


-- -----------------------------------------------------
-- Table `yolaine_hipdev`.`type_aliment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yolaine_hipdev`.`type_aliment` ;

CREATE TABLE IF NOT EXISTS `yolaine_hipdev`.`type_aliment` (
  `aliment_id` INT(10) NOT NULL AUTO_INCREMENT COMMENT 'Numéro unique pour chaque aliment.',
  `description_type_aliment` VARCHAR(25) CHARACTER SET 'utf8' NULL DEFAULT NULL COMMENT 'Indique le nom de l\'aliment pour chaque nombre unique.',
  `perissable` INT(1) NULL DEFAULT NULL COMMENT 'La ressource est périssable = 1, non-périssable = 0',
  PRIMARY KEY (`aliment_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8
COMMENT = 'Table permettant d\'identifier les types d\'aliment offert.';

CREATE UNIQUE INDEX `aliment_id_UNIQUE` ON `yolaine_hipdev`.`type_aliment` (`aliment_id` ASC);


-- -----------------------------------------------------
-- Table `yolaine_hipdev`.`alimentaire`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yolaine_hipdev`.`alimentaire` ;

CREATE TABLE IF NOT EXISTS `yolaine_hipdev`.`alimentaire` (
  `alimentaire_id` INT(10) NOT NULL AUTO_INCREMENT COMMENT 'Numéro unique pour chaque type de marchandise disponible.',
  `nom` VARCHAR(50) NULL DEFAULT NULL COMMENT 'Représente le nom de l\'aliment.',
  `description_alimentaire` VARCHAR(100) NULL DEFAULT NULL COMMENT 'Décris ce qu\'est l\'aliment.',
  `quantite` DOUBLE(10,2) NULL DEFAULT NULL COMMENT 'Indique la quantité de l\'aliment.',
  `marchandise_etat` INT(10) NULL DEFAULT NULL COMMENT 'Nombre référant à la clé étrangère de la table marchandise_etat.',
  `marchandise_unite` INT(10) NULL DEFAULT NULL COMMENT 'Nombre référant à la clé étrangère de la table marchandise_unite.',
  `valeur` DECIMAL(10,2) NULL DEFAULT '0' COMMENT 'Nombre référant au prix estimé sans décimales.',
  `marchandise_statut` INT(10) NULL DEFAULT NULL COMMENT 'Nombre référant à la clé étrangère de la table marchandise_statut.',
  `type_alimentaire` INT(10) NULL DEFAULT NULL COMMENT 'Nombre référant à la clé étrangère de la table type_aliment.',
  `date_peremption` DATETIME NULL DEFAULT NULL COMMENT 'Date(DATETIME) pour la durée de conservation de l\'aliment.',
  PRIMARY KEY (`alimentaire_id`),
  CONSTRAINT `fk_marchandise_etat$alimentaire`
    FOREIGN KEY (`marchandise_etat`)
    REFERENCES `yolaine_hipdev`.`marchandise_etat` (`etat_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_marchandise_statut$alimentaire`
    FOREIGN KEY (`marchandise_statut`)
    REFERENCES `yolaine_hipdev`.`marchandise_statut` (`statut_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_marchandise_unite$alimentaire`
    FOREIGN KEY (`marchandise_unite`)
    REFERENCES `yolaine_hipdev`.`marchandise_unite` (`unite_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_type_aliment$alimentaire`
    FOREIGN KEY (`type_alimentaire`)
    REFERENCES `yolaine_hipdev`.`type_aliment` (`aliment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8
COMMENT = 'Table permettant de lister les aliments disponibles pour les organismes.';

CREATE UNIQUE INDEX `alimentaire_id_UNIQUE` ON `yolaine_hipdev`.`alimentaire` (`alimentaire_id` ASC);

CREATE INDEX `fk_marchandise_etat$alimentaire` ON `yolaine_hipdev`.`alimentaire` (`marchandise_etat` ASC);

CREATE INDEX `fk_marchandise_unite$alimentaire` ON `yolaine_hipdev`.`alimentaire` (`marchandise_unite` ASC);

CREATE INDEX `fk_marchandise_statut$alimentaire` ON `yolaine_hipdev`.`alimentaire` (`marchandise_statut` ASC);

CREATE INDEX `fk_type_aliment$alimentaire` ON `yolaine_hipdev`.`alimentaire` (`type_alimentaire` ASC);


-- -----------------------------------------------------
-- Table `yolaine_hipdev`.`utilisateur`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yolaine_hipdev`.`utilisateur` ;

CREATE TABLE IF NOT EXISTS `yolaine_hipdev`.`utilisateur` (
  `utilisateur_id` INT(10) NOT NULL AUTO_INCREMENT COMMENT 'Numéro unique pour chaque utilisateur.',
  `mot_de_passe` VARCHAR(255) NOT NULL COMMENT 'Représente le mot de passe de l\'utilisateur.',
  `nom` VARCHAR(30) NOT NULL COMMENT 'Représente le nom de l\'utilisateur.',
  `prenom` VARCHAR(30) NOT NULL COMMENT 'Représente le prénom de l\'utilisateur.',
  `courriel` VARCHAR(255) NULL DEFAULT NULL COMMENT 'Représente l\'adresse courriel de l\'utilisateur.',
  `telephone` VARCHAR(10) NULL DEFAULT NULL COMMENT 'Représente le numéro de téléphone de l\'utilisateur.',
  `moyen_contact` INT(1) NOT NULL COMMENT 'Moyen de rejoindre l\'utilisateur ; téléphone seulement = 0, courriel seulement = 1, les deux = 2.',
  `organisme_id` INT(10) NULL DEFAULT NULL COMMENT 'Numéro de l\'organisme relié à la table organisme.',
  `derniere_connexion` DATETIME NOT NULL COMMENT 'Date et heure(DATETIME) lors de la dernière connexion.',
  PRIMARY KEY (`utilisateur_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8
COMMENT = 'Table permettant de prendre les données sur l\'utilisateur.';

CREATE UNIQUE INDEX `id_utilisateur_UNIQUE` ON `yolaine_hipdev`.`utilisateur` (`utilisateur_id` ASC);


-- -----------------------------------------------------
-- Table `yolaine_hipdev`.`organisme`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yolaine_hipdev`.`organisme` ;

CREATE TABLE IF NOT EXISTS `yolaine_hipdev`.`organisme` (
  `organisme_id` INT(10) NOT NULL AUTO_INCREMENT COMMENT 'Numéro unique pour chaque organisme.',
  `nom` VARCHAR(255) NOT NULL COMMENT 'Représente le nom de l\'organisme.',
  `adresse` INT(10) NOT NULL COMMENT 'Adressse de l\'organisme.',
  `telephone` VARCHAR(10) NOT NULL COMMENT 'Numéro de téléphone de l\'organisme.',
  `poste` INT(5) NULL DEFAULT NULL COMMENT 'Poste téléphonique pour l\'organisme.',
  `utilisateur_contact` INT(11) NOT NULL COMMENT 'Personne contact pour rejoindre l\'organisme.',
  `no_entreprise` VARCHAR(50) NULL DEFAULT NULL COMMENT 'Numéro de l\'entreprise pour fin d\'identification.',
  `no_osbl` VARCHAR(50) NULL DEFAULT NULL COMMENT 'Numéro de l\'organisme sans but lucratif.',
  PRIMARY KEY (`organisme_id`),
  CONSTRAINT `fk_adresse$organisme`
    FOREIGN KEY (`adresse`)
    REFERENCES `yolaine_hipdev`.`adresse` (`adresse_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_utilisateur$organisme`
    FOREIGN KEY (`utilisateur_contact`)
    REFERENCES `yolaine_hipdev`.`utilisateur` (`utilisateur_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8
COMMENT = 'Table permettant de prendre les données sur l\'organisme.';

CREATE UNIQUE INDEX `id_organisme_UNIQUE` ON `yolaine_hipdev`.`organisme` (`organisme_id` ASC);

CREATE INDEX `fk_adresse$organisme` ON `yolaine_hipdev`.`organisme` (`adresse` ASC);

CREATE INDEX `fk_utilisateur$organisme` ON `yolaine_hipdev`.`organisme` (`utilisateur_contact` ASC);


-- -----------------------------------------------------
-- Table `yolaine_hipdev`.`donneur_mois`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yolaine_hipdev`.`donneur_mois` ;

CREATE TABLE IF NOT EXISTS `yolaine_hipdev`.`donneur_mois` (
  `organisme_id` INT(10) NOT NULL COMMENT 'Numéro unique pour l\'organisme.',
  `montant_total` DECIMAL(10,2) NOT NULL COMMENT 'Représente la somme totale offerte par l\'organisme du donneur du mois pour un mois précis.',
  `date_donneur_mois` DATE NOT NULL COMMENT 'Représente date (année, mois) pour le donneur du mois qui a fait son exploit.',
  PRIMARY KEY (`date_donneur_mois`),
  CONSTRAINT `fk_organisme$donneur_mois`
    FOREIGN KEY (`organisme_id`)
    REFERENCES `yolaine_hipdev`.`organisme` (`organisme_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Table permettant d\'enregistrer le meilleur donneur pour un mois et une année en particulier.';

CREATE UNIQUE INDEX `pk_donneur_mois` ON `yolaine_hipdev`.`donneur_mois` (`date_donneur_mois` ASC);

CREATE INDEX `fk_organisme$donneur_mois` ON `yolaine_hipdev`.`donneur_mois` (`organisme_id` ASC);


-- -----------------------------------------------------
-- Table `yolaine_hipdev`.`transaction`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yolaine_hipdev`.`transaction` ;

CREATE TABLE IF NOT EXISTS `yolaine_hipdev`.`transaction` (
  `transaction_id` INT(10) NOT NULL AUTO_INCREMENT COMMENT 'Numéro unique pour chaque transaction.',
  `receveur_id` INT(10) NULL DEFAULT NULL COMMENT 'Numéro unique pour chaque organisme receveur, à partir de la table organisme.',
  `donneur_id` INT(10) NULL DEFAULT NULL COMMENT 'Numéro unique pour chaque donneur, à partir de la table organisme.',
  `marchandise_id` INT(10) NULL DEFAULT NULL COMMENT 'Numéro unique de la marchandise, à partir de la table alimentaire.',
  `date_collecte` DATETIME NULL DEFAULT NULL COMMENT 'Date (DATETIME) de la collecte par l\'oranisme receveur.',
  `date_reservation` DATETIME NULL DEFAULT NULL COMMENT 'Date (DATETIME) de la réservation faite par l\'organisme receveur.',
  `date_disponible` DATETIME NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Date (DATETIME) où la marchandise est disponible.',
  `date_transaction` DATETIME NOT NULL COMMENT 'Date (DATETIME) de la transaction.',
  PRIMARY KEY (`transaction_id`),
  CONSTRAINT `fk_alimentaire$transaction`
    FOREIGN KEY (`marchandise_id`)
    REFERENCES `yolaine_hipdev`.`alimentaire` (`alimentaire_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_organisme_donneur$transaction`
    FOREIGN KEY (`donneur_id`)
    REFERENCES `yolaine_hipdev`.`organisme` (`organisme_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_organisme_receveur$transaction`
    FOREIGN KEY (`receveur_id`)
    REFERENCES `yolaine_hipdev`.`organisme` (`organisme_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 22
DEFAULT CHARACTER SET = utf8
COMMENT = 'Table permettant de prendre les informations sur la transaction pour une marchandise donnée.'
DELAY_KEY_WRITE = 1;

CREATE UNIQUE INDEX `id_transaction_UNIQUE` ON `yolaine_hipdev`.`transaction` (`transaction_id` ASC);

CREATE INDEX `fk_organisme_donneur$transaction` ON `yolaine_hipdev`.`transaction` (`donneur_id` ASC);

CREATE INDEX `fk_organisme_receveur$transaction` ON `yolaine_hipdev`.`transaction` (`receveur_id` ASC);

CREATE INDEX `fk_alimentaire$transaction` ON `yolaine_hipdev`.`transaction` (`marchandise_id` ASC);

CREATE INDEX `ix_transaction` ON `yolaine_hipdev`.`transaction` (`date_disponible` ASC, `date_collecte` ASC);

USE `yolaine_hipdev` ;

-- -----------------------------------------------------
-- procedure proc_donneur_mois
-- -----------------------------------------------------

USE `yolaine_hipdev`;
DROP procedure IF EXISTS `yolaine_hipdev`.`proc_donneur_mois`;

DELIMITER $$
USE `yolaine_hipdev`$$
CREATE DEFINER=`yolaine_yolainec`@`localhost` PROCEDURE `proc_donneur_mois`()
BEGIN

	DECLARE done INT DEFAULT 0;
    
    DECLARE don_id INT;
    DECLARE max_don DECIMAL(10,2);
    DECLARE date_don DATE;
    
    DECLARE cur CURSOR FOR
		/*
			maximum des sommation des transactions par donneur par période
		*/
		SELECT
			trx2.donneur_id,
			MAX(sum_donneur) AS max_donneur,
			str_to_date(CONCAT(EXTRACT(YEAR_MONTH FROM trx2.date_collecte),'01'), '%Y%m%d') AS date_coll
			-- trx2.date_collecte,
			-- EXTRACT(YEAR_MONTH FROM SYSDATE()) -1
		FROM yolaine_hipdev.transaction trx2
		RIGHT JOIN yolaine_hipdev.organisme org2 ON org2.organisme_id = trx2.donneur_id
		RIGHT JOIN yolaine_hipdev.alimentaire ali2 ON ali2.alimentaire_id = trx2.marchandise_id
		LEFT JOIN 
		(SELECT
				trx.donneur_id AS don_id,
				SUM(ali.valeur) AS sum_donneur
				
				FROM yolaine_hipdev.transaction trx
				JOIN yolaine_hipdev.organisme org ON org.organisme_id = trx.donneur_id
				JOIN yolaine_hipdev.alimentaire ali ON ali.alimentaire_id = trx.marchandise_id
				WHERE EXTRACT(YEAR_MONTH FROM trx.date_collecte) BETWEEN (EXTRACT(YEAR_MONTH FROM SYSDATE())) -1  AND (EXTRACT(YEAR_MONTH FROM SYSDATE())) - 1
				GROUP BY trx.donneur_id
			)somme_donneur
					ON trx2.donneur_id = don_id
		WHERE EXTRACT(YEAR_MONTH FROM trx2.date_collecte) BETWEEN (EXTRACT(YEAR_MONTH FROM SYSDATE())) - 1 AND (EXTRACT(YEAR_MONTH FROM SYSDATE())) - 1
		;
		
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
    -- TODO a partir d'ici
    OPEN cur;
	
    START TRANSACTION; 
	read_loop: LOOP
  
	FETCH cur INTO don_id, max_don, date_don;
    
    IF done THEN
      LEAVE read_loop;
    END IF;
    
	
  
  END LOOP;
  COMMIT;
  CLOSE cur;
END$$

DELIMITER ;
SET SQL_MODE = '';
GRANT USAGE ON *.* TO hippie;
 DROP USER hippie;
SET SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';
CREATE USER 'hippie' IDENTIFIED BY 'hippie';

GRANT SELECT ON TABLE `yolaine_hipdev`.* TO 'hippie';
GRANT SELECT, INSERT, TRIGGER ON TABLE `yolaine_hipdev`.* TO 'hippie';
GRANT EXECUTE ON ROUTINE `yolaine_hipdev`.* TO 'hippie';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
