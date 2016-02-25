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
-- Table structure for table `organisme`
--

DROP TABLE IF EXISTS `organisme`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `organisme` (
  `organisme_id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'Numéro unique pour chaque organisme.',
  `nom` varchar(255) NOT NULL COMMENT 'Représente le nom de l''organisme.',
  `adresse` int(10) NOT NULL COMMENT 'Adressse de l''organisme.',
  `telephone` varchar(10) NOT NULL COMMENT 'Numéro de téléphone de l''organisme.',
  `poste` int(5) DEFAULT NULL COMMENT 'Poste téléphonique pour l''organisme.',
  `utilisateur_contact` int(11) NOT NULL COMMENT 'Personne contact pour rejoindre l''organisme.',
  `no_entreprise` varchar(50) DEFAULT NULL COMMENT 'Numéro de l''entreprise pour fin d''identification.',
  `no_osbl` varchar(50) DEFAULT NULL COMMENT 'Numéro de l''organisme sans but lucratif.',
  PRIMARY KEY (`organisme_id`),
  UNIQUE KEY `id_organisme_UNIQUE` (`organisme_id`),
  KEY `fk_adresse$organisme` (`adresse`),
  KEY `fk_utilisateur$organisme` (`utilisateur_contact`),
  CONSTRAINT `fk_adresse$organisme` FOREIGN KEY (`adresse`) REFERENCES `adresse` (`adresse_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_utilisateur$organisme` FOREIGN KEY (`utilisateur_contact`) REFERENCES `utilisateur` (`utilisateur_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='Table permettant de prendre les données sur l''organisme.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organisme`
--

LOCK TABLES `organisme` WRITE;
/*!40000 ALTER TABLE `organisme` DISABLE KEYS */;
INSERT INTO `organisme` (`organisme_id`, `nom`, `adresse`, `telephone`, `poste`, `utilisateur_contact`, `no_entreprise`, `no_osbl`) VALUES (1,'La Tablée Élisabeth Bruyère',19,'8195374884',0,7,'1143475094','119009199RR0001 '),(2,'La Tablée Mère D\'Youville',20,'8195373477',NULL,8,'1144206712	','119009199RR0001 '),(3,'La Tablée Marcelle Mallet',21,'8195379813',NULL,9,'1144206712	','119009199RR0001 '),(4,'Centre Roland-Bertrand',22,'8195378851',NULL,4,'1143475094','107860108RR0001'),(5,'Supermarché IGA extra Shawinigan',23,'8195364141',NULL,10,'1163343586',NULL),(6,'Super C',24,'8195397498',NULL,11,'2243669118',NULL),(7,'Alimentation Gauthier et Frères',25,'8195334553',NULL,12,'1143964634',NULL),(8,'Métro Plus Shawinigan',26,'8195373724',NULL,4,'1165196495',NULL);
/*!40000 ALTER TABLE `organisme` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-18  9:30:03
