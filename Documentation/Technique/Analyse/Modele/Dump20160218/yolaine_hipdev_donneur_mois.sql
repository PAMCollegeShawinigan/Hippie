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
-- Table structure for table `donneur_mois`
--

DROP TABLE IF EXISTS `donneur_mois`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `donneur_mois` (
  `organisme_id` int(10) NOT NULL COMMENT 'Numéro unique pour l''organisme.',
  `montant_total` decimal(10,2) NOT NULL COMMENT 'Représente la somme totale offerte par l''organisme du donneur du mois pour un mois précis.',
  `date_donneur_mois` date NOT NULL COMMENT 'Représente date (année, mois) pour le donneur du mois qui a fait son exploit.',
  PRIMARY KEY (`date_donneur_mois`),
  UNIQUE KEY `pk_donneur_mois` (`date_donneur_mois`),
  KEY `fk_organisme$donneur_mois` (`organisme_id`),
  CONSTRAINT `fk_organisme$donneur_mois` FOREIGN KEY (`organisme_id`) REFERENCES `organisme` (`organisme_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Table permettant d''enregistrer le meilleur donneur pour un mois et une année en particulier.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donneur_mois`
--

LOCK TABLES `donneur_mois` WRITE;
/*!40000 ALTER TABLE `donneur_mois` DISABLE KEYS */;
INSERT INTO `donneur_mois` (`organisme_id`, `montant_total`, `date_donneur_mois`) VALUES (5,3000.00,'2015-10-25'),(6,2500.00,'2015-11-25'),(7,2450.00,'2015-12-25'),(5,3500.00,'2016-01-31');
/*!40000 ALTER TABLE `donneur_mois` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-18  9:30:04
