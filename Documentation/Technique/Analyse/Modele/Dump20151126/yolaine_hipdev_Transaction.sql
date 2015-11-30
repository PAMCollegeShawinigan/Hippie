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
  KEY `Organisme_Donneur_FK_idx` (`id_donneur`),
  KEY `Alimentaire_TRX_FK_idx` (`id_marchandise`),
  KEY `Organisme_Receveur_FK` (`id_receveur`),
  CONSTRAINT `Organisme_Donneur_FK` FOREIGN KEY (`id_donneur`) REFERENCES `Organisme` (`id_organisme`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Organisme_Receveur_FK` FOREIGN KEY (`id_receveur`) REFERENCES `Organisme` (`id_organisme`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Alimentaire_TRX_FK` FOREIGN KEY (`id_marchandise`) REFERENCES `Alimentaire` (`id_alimentaire`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Transaction`
--

LOCK TABLES `Transaction` WRITE;
/*!40000 ALTER TABLE `Transaction` DISABLE KEYS */;
INSERT INTO `Transaction` (`id_transaction`, `id_receveur`, `id_donneur`, `id_marchandise`, `date_collecte`, `date_reservation`, `date_disponible`) VALUES (23,12,16,5,NULL,NULL,'0000-00-00 00:00:00'),(24,12,17,10,NULL,NULL,'0000-00-00 00:00:00'),(25,13,17,11,NULL,NULL,'0000-00-00 00:00:00'),(26,13,18,12,NULL,NULL,'0000-00-00 00:00:00'),(27,14,17,13,NULL,NULL,'0000-00-00 00:00:00'),(28,15,17,14,NULL,NULL,'0000-00-00 00:00:00'),(29,15,18,5,NULL,NULL,'0000-00-00 00:00:00'),(30,15,19,10,NULL,NULL,'0000-00-00 00:00:00'),(31,15,17,11,NULL,NULL,'0000-00-00 00:00:00');
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

-- Dump completed on 2015-11-27 13:32:28
