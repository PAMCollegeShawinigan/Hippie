CREATE DATABASE  IF NOT EXISTS `yolaine_hipdev` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `yolaine_hipdev`;
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
-- Table structure for table `Adresse`
--

DROP TABLE IF EXISTS `Adresse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Adresse` (
  `id_adresse` int(10) NOT NULL AUTO_INCREMENT,
  `no_civique` varchar(10) DEFAULT NULL,
  `type_rue` int(10) DEFAULT NULL,
  `nom` varchar(30) DEFAULT NULL,
  `app` varchar(10) DEFAULT NULL,
  `ville` varchar(50) DEFAULT NULL,
  `province` varchar(25) DEFAULT NULL,
  `code_postal` varchar(10) DEFAULT NULL,
  `pays` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id_adresse`),
  UNIQUE KEY `Type_rue_fk` (`type_rue`),
  CONSTRAINT `Adresse_ibfk_1` FOREIGN KEY (`type_rue`) REFERENCES `Type_Rue` (`id_type_rue`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Adresse`
--

LOCK TABLES `Adresse` WRITE;
/*!40000 ALTER TABLE `Adresse` DISABLE KEYS */;
INSERT INTO `Adresse` (`id_adresse`, `no_civique`, `type_rue`, `nom`, `app`, `ville`, `province`, `code_postal`, `pays`) VALUES (19,'1',14,'Rue 1',NULL,'Shawinigan','Qc','XXX XXX','Canada'),(20,'2',11,'Promenade_2',NULL,'Shawinigan','Qc','XXX XXX','Canada'),(21,'3',8,'Montee 9',NULL,'Shawinigan','Qc','XXX XXX','Canada'),(22,'4',10,'Place 4',NULL,'Shawinigan','Qc','XXX XXX','Canada'),(23,'5',12,'Rang 5',NULL,'Shawinigan','Qc','XXX XXX','Canada'),(24,'6',13,'Route 6',NULL,'Shawinigan','Qc','XXX XXX','Canada'),(25,'7',1,'Avenue 7',NULL,'Shawinigan','Qc','XXX XXX','Canada'),(26,'8',2,'Boulevard 8',NULL,'Shawinigan','Qc','XXX XXX',NULL);
/*!40000 ALTER TABLE `Adresse` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-11-26  8:44:41
