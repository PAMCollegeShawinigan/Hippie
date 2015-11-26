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
-- Table structure for table `Transaction`
--

DROP TABLE IF EXISTS `Transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Transaction` (
  `id_transaction` int(10) NOT NULL AUTO_INCREMENT,
  `id_receveur` int(10) DEFAULT NULL,
  `id_donneur` int(10) DEFAULT NULL,
  `id_marchandise` int(10) DEFAULT NULL,
  `date_collecte` timestamp NULL DEFAULT NULL,
  `date_reservation` timestamp NULL DEFAULT NULL,
  `date_disponible` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id_transaction`),
  KEY `donneur_fk_idx` (`id_donneur`),
  KEY `receveur_fk_idx` (`id_receveur`),
  KEY `Alimentaire_FK_idx` (`id_marchandise`),
  CONSTRAINT `Alimentaire_FK` FOREIGN KEY (`id_marchandise`) REFERENCES `Alimentaire` (`id_alimentaire`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Transaction`
--

LOCK TABLES `Transaction` WRITE;
/*!40000 ALTER TABLE `Transaction` DISABLE KEYS */;
INSERT INTO `Transaction` (`id_transaction`, `id_receveur`, `id_donneur`, `id_marchandise`, `date_collecte`, `date_reservation`, `date_disponible`) VALUES (21,19,23,5,NULL,NULL,'0000-00-00 00:00:00'),(22,19,26,10,NULL,NULL,'0000-00-00 00:00:00'),(24,19,23,5,NULL,NULL,'0000-00-00 00:00:00'),(25,19,26,10,NULL,NULL,'0000-00-00 00:00:00'),(26,20,23,11,NULL,NULL,'0000-00-00 00:00:00'),(27,20,24,12,NULL,NULL,'0000-00-00 00:00:00'),(28,21,23,13,NULL,NULL,'0000-00-00 00:00:00'),(29,22,23,14,NULL,NULL,'0000-00-00 00:00:00'),(30,22,24,5,NULL,NULL,'0000-00-00 00:00:00'),(31,22,25,10,NULL,NULL,'0000-00-00 00:00:00'),(32,22,23,11,NULL,NULL,'0000-00-00 00:00:00'),(33,19,23,5,NULL,NULL,'0000-00-00 00:00:00'),(34,19,26,10,NULL,NULL,'0000-00-00 00:00:00'),(35,20,23,11,NULL,NULL,'0000-00-00 00:00:00'),(36,20,24,12,NULL,NULL,'0000-00-00 00:00:00'),(37,21,23,13,NULL,NULL,'0000-00-00 00:00:00'),(38,22,23,14,NULL,NULL,'0000-00-00 00:00:00'),(39,22,24,5,NULL,NULL,'0000-00-00 00:00:00'),(40,22,25,10,NULL,NULL,'0000-00-00 00:00:00'),(41,22,23,11,NULL,NULL,'0000-00-00 00:00:00');
/*!40000 ALTER TABLE `Transaction` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-11-26  8:44:31
