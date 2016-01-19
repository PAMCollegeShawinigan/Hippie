--Quoi: Fichier des requêtes SQL pour le projet d'intégration
--Date de création: 2016-01-11
--Par: Jean-François Noël
--Dernière mise à jour : 2016-01-17

--IMPORTANT--
/*
Lors de changement des données (update) sur le site MyWebSQL, cliquer dans le bas 
(Update Record(s)) pour faire un commit
*/
--SQL pour lister les marchandises disponibles : Prendre les deux requêtes faites par PA dans le fichier liste.php

--Inserts/updates (insertions) faits au cours de la semaine du 2016-01-11 au 2016-01-17
--Comme il y avait déjà des insertions dans la plupart des tables, j'ai effectué des mises à jour afin de garder
--les occurences déjà faites.
--Les voici par tables:

/* data for Table adresse */
INSERT INTO `adresse` VALUES (19,"2193",1,"avenue Saint-Marc",NULL,"Shawinigan","Qc","G9N2J4","Canada");
INSERT INTO `adresse` VALUES (20,"599",14,"4e Rue",NULL,"Shawinigan","Qc","G9N1G9","Canada");
INSERT INTO `adresse` VALUES (21,"1560",1,"5e Avenue",NULL,"Shawinigan","Qc","G9N1M5","Canada");
INSERT INTO `adresse` VALUES (22,"2183",1,"avenue Saint-Marc",NULL,"Shawinigan","Qc","G9N2J4","Canada");
INSERT INTO `adresse` VALUES (23,"1500",14,"Trudel",NULL,"Shawinigan","Qc","G9N8K8","Canada");
INSERT INTO `adresse` VALUES (24,"3283",2,"Royal",NULL,"Shawinigan","Qc","G9N8K7","Canada");
INSERT INTO `adresse` VALUES (25,"850",7,"7e Av",NULL,"Grand-Mère","Qc","G9T2B8","Canada");
INSERT INTO `adresse` VALUES (26,"4175",7,"12e Ave",NULL,"Shawinigan","Qc","G9P4G3","Canada");

/* data for Table alimentaire */
INSERT INTO `alimentaire` VALUES (47,"Framboises","Framboises importées du Mexique",12.00,3,7,170,1,1,"2016-01-16 21:09:12");
INSERT INTO `alimentaire` VALUES (48,"Bleuets","Bleuets importés du Chili",15.00,3,7,170,2,1,"2016-01-16 21:12:00");
INSERT INTO `alimentaire` VALUES (49,"Mûres","Mûres importées du Mexique",50.00,3,7,170,3,1,"2016-01-16 21:15:00");
INSERT INTO `alimentaire` VALUES (50,"Cerises","Cerises importées du Chili",30.00,3,2,5,4,1,"2016-01-16 21:19:00");
INSERT INTO `alimentaire` VALUES (51,"Mini concombres","Mini concombres importés",40.00,3,7,397,5,1,"2016-01-16 21:25:00");
INSERT INTO `alimentaire` VALUES (52,"Nectarines","Nectarines importées du Chili,  Cat. No 1, GR 60-70",20.00,3,2,4,6,1,"2016-01-16 21:28:00");

/* data for Table marchandise_unite */
INSERT INTO `marchandise_unite` VALUES (7,"gr");

/* data for Table organisme */
INSERT INTO `organisme` VALUES (1,"La Tablée Élisabeth Bruyère",19,"8195374884",0,7,"1143475094","119009199RR0001 ");
INSERT INTO `organisme` VALUES (2,"La Tablée Mère D\'Youville",20,"8195373477",NULL,8,"1144206712	","119009199RR0001 ");
INSERT INTO `organisme` VALUES (3,"La Tablée Marcelle Mallet",21,"8195379813",NULL,9,"1144206712	","119009199RR0001 ");
INSERT INTO `organisme` VALUES (4,"Centre Roland-Bertrand",22,"8195378851",NULL,4,"1143475094","107860108RR0001");
INSERT INTO `organisme` VALUES (5,"Supermarché IGA extra Shawinigan",23,"8195364141",NULL,10,"1163343586",NULL);
INSERT INTO `organisme` VALUES (6,"Super C",24,"8195397498",NULL,11,"2243669118",NULL);
INSERT INTO `organisme` VALUES (7,"Alimentation Gauthier et Frères",25,"8195334553",NULL,12,"1143964634",NULL);
INSERT INTO `organisme` VALUES (8,"Métro Plus Shawinigan",26,"8195373724",NULL,4,"1165196495",NULL);

--Fait le 2016-01-11
INSERT INTO `type_aliment` SET `aliment_id`="7",`description_type_aliment`="Non comestible";

/* data for Table transaction */
INSERT INTO `transaction` SET `receveur_id`="1",`donneur_id`="5",`marchandise_id`="47",`date_disponible`="2016-01-17 11:05:00",`date_transaction`="2016-01-17 11:04:00"
INSERT INTO `transaction` SET `receveur_id`="2",`donneur_id`="6",`marchandise_id`="48",`date_collecte`="NULL",`date_reservation`="2016-01-17 11:10:00",`date_disponible`="2016-01-17 11:08:00",`date_transaction`="2016-01-17 11:07:00"
INSERT INTO `transaction` SET `receveur_id`="3",`donneur_id`="7",`marchandise_id`="49",`date_disponible`="2016-01-17 11:55:00",`date_transaction`="2016-01-17 11:54:00"
INSERT INTO `transaction` SET `receveur_id`="4",`donneur_id`="8",`marchandise_id`="50",`date_collecte`="2016-01-17 13:05:0",`date_reservation`="2016-01-17 12:05:0",`date_disponible`="2016-01-17 11:59:00",`date_transaction`="2016-01-17 11:59:0"
INSERT INTO `transaction` SET `receveur_id`="1",`donneur_id`="5",`marchandise_id`="51",`date_collecte`="2016-01-17 14:05:00",`date_reservation`="2016-01-17 13:15:00",`date_disponible`="2016-01-17 12:05:00",`date_transaction`="2016-01-17 12:00:00"
INSERT INTO `transaction` SET `donneur_id`="2",`marchandise_id`="52",`date_disponible`="2016-01-17 12:06:00",`date_transaction`="2016-01-17 12:05:00"

UPDATE `yolaine_hipdev`.`transaction` SET `date_collecte`=NULL WHERE `transaction_id`='14'; --que j'ai fait, remarquer le simple apostrophe au lieu du double.
--UPDATE `transaction` SET `date_collecte`="NULL" WHERE `transaction_id`="14"/ ne prend pas le null - avec MyWebSQL

/* data for Table utilisateur */
INSERT INTO `utilisateur` VALUES (5,"test","Calille","Sylvie","test@test.com","8195378851",1,4,"2016-01-13 20:37:10");
INSERT INTO `utilisateur` VALUES (7,"mot_de_passe","Béchard","Fabien","info@tableepopulaire.ca","8195374884",0,1,"2016-01-13 11:18:09");
INSERT INTO `utilisateur` VALUES (8,"mot_de_passe","NADEAU","PHILIPPE","info@tableepopulaire.ca","8195373477",0,2,"2016-01-13 14:28:40");
INSERT INTO `utilisateur` VALUES (9,"mot_de_passe","Leloup","Sam","info@tableepopulaire.ca","8195379813",2,3,"2016-01-13 14:30:50");
INSERT INTO `utilisateur` VALUES (10,"mot_de_passe","Baril","Séléna","NULL","8195364141",1,5,"2016-01-16 16:18:00");
INSERT INTO `utilisateur` VALUES (11,"mot_de_passe","Dussault","Alain","NULL","8195397498",1,6,"2016-01-16 16:34:00");
INSERT INTO `utilisateur` VALUES (12,"mot_de_passe","Gauthier","Claud","NULL","8195334553",1,7,"2016-01-16 20:27:00");
INSERT INTO `utilisateur` VALUES (13,"mot_de_passe","Ayotte","Philippe","NULL","8195373724",1,8,"2016-01-16 20:34:00");

--SECTION ALTER
--ALTER table
--Fait le 2016-01-12
ALTER TABLE `type_aliment`
ADD COLUMN `perissable` integer NOT NULL AFTER `description_type_aliment`;
-- ajout d'un commentaire à 
ALTER TABLE `type_aliment` CHANGE `perissable` `perissable` INT( 1 ) COMMENT 'La ressource est périssable = 1, non-périssable = 0';  

--ALTER pour permettre à PA la converison via PHP vers Json
--Fait le 2016-01-12
ALTER TABLE `utilisateur` CHANGE `derniere_connexion` `derniere_connexion` datetime NOT NULL COMMENT '';

--ALTER TABLE `alimentaire` CHANGE `date_peremption` `date_peremption` datetime NOT NULL;
ALTER TABLE `yolaine_hipdev`.`alimentaire` CHANGE COLUMN `date_peremption` `date_peremption` DATETIME NULL DEFAULT NULL COMMENT 'Date(timestamp) pour la durée de conservation de l''aliment.' ;

ALTER TABLE `yolaine_hipdev`.`transaction` 
CHANGE COLUMN `date_collecte` `date_collecte` DATETIME NULL DEFAULT NULL COMMENT 'Date (DATETIME) de la collecte par l''oranisme receveur.' ,
CHANGE COLUMN `date_reservation` `date_reservation` DATETIME NULL DEFAULT NULL COMMENT 'Date (DATETIME) de la réservation faite par l''organisme receveur.' ,
CHANGE COLUMN `date_disponible` `date_disponible` DATETIME NULL DEFAULT NULL COMMENT 'Date (DATETIME) où la marchandise est disponible.' ,
CHANGE COLUMN `date_transaction` `date_transaction` DATETIME NOT NULL COMMENT 'Date (DATETIME) de la transaction.';

--ALTER TABLE utilisateur
ALTER TABLE `yolaine_hipdev`.`utilisateur` 
CHANGE COLUMN `telephone` `telephone` varchar(10) DEFAULT NULL COMMENT 'Représente le numéro de téléphone de l''utilisateur.'

--ALTER pour réduire au minumum la longueur du champ code postal
--2016-01-13
ALTER TABLE `yolaine_hipdev`.`adresse` 
CHANGE COLUMN `code_postal` `code_postal` VARCHAR(6) NULL DEFAULT NULL COMMENT 'Code postal.' ;

--Pour indiquer le choix du moyen de communication
--2016-01-13
ALTER TABLE `yolaine_hipdev`.`utilisateur` 
CHANGE COLUMN `moyen_contact` `moyen_contact` INT(1) NOT NULL COMMENT 'Moyen de rejoindre l''utilisateur ; téléphone seulement = 0, courriel seulement = 1, les deux = 2.' ;

ALTER TABLE `yolaine_hipdev`.`alimentaire` 
CHANGE COLUMN `date_peremption` `date_peremption` DATETIME NULL DEFAULT NULL COMMENT 'Date(DATETIME) pour la durée de conservation de l''aliment.' ;

ALTER TABLE `yolaine_hipdev`.`utilisateur` 
CHANGE COLUMN `derniere_connexion` `derniere_connexion` DATETIME NOT NULL COMMENT 'Date et heure(DATETIME) lors de la dernière connexion.' ;

ALTER TABLE `yolaine_hipdev`.`transaction` 
CHANGE COLUMN `date_collecte` `date_collecte` DATETIME NULL DEFAULT NULL COMMENT 'Date (DATETIME) de la collecte par l''oranisme receveur.' ,
CHANGE COLUMN `date_reservation` `date_reservation` DATETIME NULL DEFAULT NULL COMMENT 'Date (DATETIME) de la réservation faite par l''organisme receveur.' ,
CHANGE COLUMN `date_disponible` `date_disponible` DATETIME NULL DEFAULT NULL COMMENT 'Date (DATETIME) où la marchandise est disponible.' ,
CHANGE COLUMN `date_transaction` `date_transaction` DATETIME NOT NULL COMMENT 'Date (DATETIME) de la transaction.';

--ALTER table oranisme
--2016-01-14
ALTER TABLE `yolaine_hipdev`.`organisme` 
CHANGE COLUMN `nom` `nom` varchar(255) NOT NULL COMMENT 'Représente le nom de l''organisme.';



