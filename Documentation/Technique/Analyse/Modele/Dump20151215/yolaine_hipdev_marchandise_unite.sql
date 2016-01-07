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
-- Table structure for table `marchandise_unite`
--

DROP TABLE IF EXISTS `marchandise_unite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `marchandise_unite` (
  `unite_id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'Numéro unique pour chaque type d''unité.',
  `description_marchandise_unite` varchar(25) DEFAULT NULL COMMENT 'Indique le type d''unité pour le produit pour chaque unite_id.',
  PRIMARY KEY (`unite_id`),
  UNIQUE KEY `unite_id_UNIQUE` (`unite_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='Table permettant de mettre un type de mesure sur la marchandise.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `marchandise_unite`
--

LOCK TABLES `marchandise_unite` WRITE;
/*!40000 ALTER TABLE `marchandise_unite` DISABLE KEYS */;
INSERT INTO `marchandise_unite` (`unite_id`, `description_marchandise_unite`) VALUES (1,'Lbs'),(2,'Kg'),(3,'Unité(s)'),(4,'Ml'),(5,'L'),(6,'Oz');
/*!40000 ALTER TABLE `marchandise_unite` ENABLE KEYS */;
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
