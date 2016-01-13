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
-- Table structure for table `marchandise_etat`
--

DROP TABLE IF EXISTS `marchandise_etat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `marchandise_etat` (
  `etat_id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'Numéro unique pour chaque état d''une marchandise.',
  `description_marchandise_etat` varchar(25) DEFAULT NULL COMMENT 'Indique l''état de la marchandise pour chaque id unique.',
  PRIMARY KEY (`etat_id`),
  UNIQUE KEY `etat_id_UNIQUE` (`etat_id`),
  KEY `ix_marchandise_etat` (`description_marchandise_etat`) COMMENT 'Requête de la liste des marchandises réservé par un organisme (par receveur_id)'
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='Table permettant de mettre un type d''état pour une marchandise.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `marchandise_etat`
--

LOCK TABLES `marchandise_etat` WRITE;
/*!40000 ALTER TABLE `marchandise_etat` DISABLE KEYS */;
INSERT INTO `marchandise_etat` (`etat_id`, `description_marchandise_etat`) VALUES (1,'Incomplet'),(2,'Mauvais État'),(3,'Neuf'),(4,'Usagé');
/*!40000 ALTER TABLE `marchandise_etat` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-12-15 10:54:52
