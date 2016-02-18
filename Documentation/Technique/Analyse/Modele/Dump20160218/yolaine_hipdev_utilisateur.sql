-- MySQL dump 10.13  Distrib 5.7.9, for osx10.9 (x86_64)
--
-- Host: localhost    Database: yolaine_hipdev
-- ------------------------------------------------------
-- Server version	5.5.44-MariaDB-cll-lve

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `utilisateur` (
  `utilisateur_id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'Numéro unique pour chaque utilisateur.',
  `mot_de_passe` varchar(255) NOT NULL COMMENT 'Représente le mot de passe de l''utilisateur.',
  `nom` varchar(30) NOT NULL COMMENT 'Représente le nom de l''utilisateur.',
  `prenom` varchar(30) NOT NULL COMMENT 'Représente le prénom de l''utilisateur.',
  `courriel` varchar(255) DEFAULT NULL COMMENT 'Représente l''adresse courriel de l''utilisateur.',
  `telephone` varchar(10) DEFAULT NULL COMMENT 'Représente le numéro de téléphone de l''utilisateur.',
  `moyen_contact` int(1) NOT NULL COMMENT 'Moyen de rejoindre l''utilisateur ; téléphone seulement = 0, courriel seulement = 1, les deux = 2.',
  `organisme_id` int(10) DEFAULT NULL COMMENT 'Numéro de l''organisme relié à la table organisme.',
  `derniere_connexion` datetime NOT NULL COMMENT 'Date et heure(DATETIME) lors de la dernière connexion.',
  PRIMARY KEY (`utilisateur_id`),
  UNIQUE KEY `id_utilisateur_UNIQUE` (`utilisateur_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='Table permettant de prendre les données sur l''utilisateur.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utilisateur`
--

LOCK TABLES `utilisateur` WRITE;
/*!40000 ALTER TABLE `utilisateur` DISABLE KEYS */;
INSERT INTO `utilisateur` (`utilisateur_id`, `mot_de_passe`, `nom`, `prenom`, `courriel`, `telephone`, `moyen_contact`, `organisme_id`, `derniere_connexion`) VALUES (4,'patate','Lachance','Pier-Alain','laluckalien@gmail.com','8198522704',1,NULL,'2015-11-25 21:01:43'),(5,'test','Calille','Sylvie','test@test.com','8195378851',1,4,'2016-01-13 20:37:10'),(6,'123','Gratton','Bob','lafontaine.philippe@gmail.com',NULL,0,NULL,'2015-12-14 10:41:44'),(7,'123','Béchard','Fabien','org1@1.ca','8195374884',0,1,'2016-01-13 11:18:09'),(8,'123','NADEAU','PHILIPPE','org2@2.ca','8195373477',0,2,'2016-01-13 14:28:40'),(9,'123','Leloup','Sam','org3@3.ca','8195379813',2,3,'2016-01-13 14:30:50'),(10,'123','Baril','Séléna','ent1@1.ca','8195364141',1,5,'2016-01-16 16:18:00'),(11,'123','Dussault','Alain','ent2@2.ca','8195397498',1,6,'2016-01-16 16:34:00'),(12,'123','Gauthier','Claud','ent3@3.ca','8195334553',1,7,'2016-01-16 20:27:00'),(13,'123','Ayotte','Philippe','ent4@4.ca','8195373724',1,8,'2016-01-16 20:34:00');
/*!40000 ALTER TABLE `utilisateur` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-18  9:29:58
