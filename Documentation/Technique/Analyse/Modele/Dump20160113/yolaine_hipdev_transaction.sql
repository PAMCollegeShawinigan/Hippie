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
  `date_collecte` datetime DEFAULT NULL,
  `date_reservation` datetime DEFAULT NULL,
  `date_disponible` datetime DEFAULT NULL,
  `date_transaction` datetime NOT NULL,
  PRIMARY KEY (`transaction_id`),
  UNIQUE KEY `id_transaction_UNIQUE` (`transaction_id`),
  KEY `fk_organisme_donneur$transaction` (`donneur_id`),
  KEY `fk_organisme_receveur$transaction` (`receveur_id`),
  KEY `fk_alimentaire$transaction` (`marchandise_id`),
  KEY `ix_transaction` (`date_disponible`,`date_collecte`) COMMENT 'Requête pour voir la liste des marchandises disponible, champs dans le where''; 2',
  CONSTRAINT `fk_alimentaire$transaction` FOREIGN KEY (`marchandise_id`) REFERENCES `alimentaire` (`alimentaire_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_organisme_donneur$transaction` FOREIGN KEY (`donneur_id`) REFERENCES `organisme` (`organisme_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_organisme_receveur$transaction` FOREIGN KEY (`receveur_id`) REFERENCES `organisme` (`organisme_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='Table permettant de prendre les informations sur la transaction pour une marchandise donnée.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` (`transaction_id`, `receveur_id`, `donneur_id`, `marchandise_id`, `date_collecte`, `date_reservation`, `date_disponible`, `date_transaction`) VALUES (1,NULL,4,35,NULL,NULL,'2016-01-12 12:57:14','2016-01-12 12:57:14'),(2,NULL,4,36,NULL,NULL,'2016-01-12 12:59:19','2016-01-12 12:59:19'),(9,NULL,4,43,NULL,NULL,'2016-01-12 13:58:29','2016-01-12 13:58:29'),(10,NULL,4,44,NULL,NULL,'2016-01-12 13:59:34','2016-01-12 13:59:34'),(11,NULL,4,45,NULL,NULL,'2016-01-12 14:16:32','2016-01-12 14:16:32'),(12,NULL,4,46,NULL,NULL,'2016-01-12 14:19:48','2016-01-12 14:19:48');
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

-- Dump completed on 2016-01-13 11:28:23
