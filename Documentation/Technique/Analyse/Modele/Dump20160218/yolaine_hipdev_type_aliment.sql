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
-- Table structure for table `type_aliment`
--

DROP TABLE IF EXISTS `type_aliment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `type_aliment` (
  `aliment_id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'Numéro unique pour chaque aliment.',
  `description_type_aliment` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Indique le nom de l''aliment pour chaque nombre unique.',
  `perissable` int(1) DEFAULT NULL COMMENT 'La ressource est périssable = 1, non-périssable = 0',
  PRIMARY KEY (`aliment_id`),
  UNIQUE KEY `aliment_id_UNIQUE` (`aliment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Table permettant d''identifier les types d''aliment offert.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type_aliment`
--

LOCK TABLES `type_aliment` WRITE;
/*!40000 ALTER TABLE `type_aliment` DISABLE KEYS */;
INSERT INTO `type_aliment` (`aliment_id`, `description_type_aliment`, `perissable`) VALUES (1,'Fruits et Légumes',1),(2,'Produits laitiers',1),(3,'Viandes',1),(4,'Surgelés',0),(5,'Non Périssable',0),(6,'Boulangerie',1),(7,'Non-comestible',0);
/*!40000 ALTER TABLE `type_aliment` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-18  9:29:59
