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
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transaction` (
  `transaction_id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'Numéro unique pour chaque transaction.',
  `receveur_id` int(10) DEFAULT NULL COMMENT 'Numéro unique pour chaque organisme receveur, à partir de la table organisme.',
  `donneur_id` int(10) DEFAULT NULL COMMENT 'Numéro unique pour chaque donneur, à partir de la table organisme.',
  `marchandise_id` int(10) DEFAULT NULL COMMENT 'Numéro unique de la marchandise, à partir de la table alimentaire.',
  `date_collecte` timestamp NULL DEFAULT NULL COMMENT 'Date de la collecte par l''oranisme receveur.',
  `date_reservation` timestamp NULL DEFAULT NULL COMMENT 'Date de la réservation faite par l''organisme receveur.',
  `date_disponible` timestamp NULL DEFAULT NULL COMMENT 'Date de la transaction',
  `date_transaction` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`transaction_id`),
  UNIQUE KEY `id_transaction_UNIQUE` (`transaction_id`),
  KEY `fk_organisme_donneur$transaction` (`donneur_id`),
  KEY `fk_organisme_receveur$transaction` (`receveur_id`),
  KEY `fk_alimentaire$transaction` (`marchandise_id`),
  KEY `ix_transaction` (`date_disponible`,`date_collecte`) COMMENT 'Requête pour voir la liste des marchandises disponible, champs dans le where''; 2',
  CONSTRAINT `fk_alimentaire$transaction` FOREIGN KEY (`marchandise_id`) REFERENCES `alimentaire` (`alimentaire_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_organisme_donneur$transaction` FOREIGN KEY (`donneur_id`) REFERENCES `organisme` (`organisme_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_organisme_receveur$transaction` FOREIGN KEY (`receveur_id`) REFERENCES `organisme` (`organisme_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='Table permettant de prendre les informations sur la transaction pour une marchandise donnée.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` (`transaction_id`, `receveur_id`, `donneur_id`, `marchandise_id`, `date_collecte`, `date_reservation`, `date_disponible`, `date_transaction`) VALUES (1,1,6,1,'2015-12-02 05:00:00','2015-12-01 05:00:00','2015-12-15 05:00:00','2015-12-09 06:45:00'),(2,1,8,2,'2015-12-09 05:00:00',NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(3,1,5,1,'2015-12-10 05:00:00',NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(4,1,8,7,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(5,2,5,3,'2015-12-03 05:00:00','2015-12-23 05:00:00','0000-00-00 00:00:00','0000-00-00 00:00:00'),(6,2,6,4,NULL,'2015-12-22 05:00:00','0000-00-00 00:00:00','0000-00-00 00:00:00'),(7,3,5,5,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(8,4,5,6,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(9,4,6,1,'2015-12-06 05:00:00',NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(10,4,7,2,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(11,4,5,3,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(12,1,5,5,'2015-12-04 05:00:00',NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(13,1,8,2,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(14,2,5,3,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(15,2,6,4,'2015-12-24 05:00:00',NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(16,3,5,5,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(17,4,5,6,NULL,NULL,'2015-12-18 05:00:00','0000-00-00 00:00:00'),(18,4,6,1,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(19,4,7,2,NULL,NULL,'2015-12-21 05:00:00','0000-00-00 00:00:00'),(20,4,5,3,NULL,NULL,'2015-12-02 05:00:00','0000-00-00 00:00:00'),(21,NULL,6,4,NULL,NULL,'2015-11-29 05:08:30','0000-00-00 00:00:00'),(22,1,6,1,NULL,'2015-12-01 05:00:00','0000-00-00 00:00:00','2015-12-09 06:45:46'),(23,1,6,1,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00','2015-12-09 06:46:49'),(24,1,6,1,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00','2015-12-09 06:48:59');
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-12-15 10:54:53
