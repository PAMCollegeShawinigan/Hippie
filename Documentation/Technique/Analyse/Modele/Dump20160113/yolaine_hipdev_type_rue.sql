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
-- Table structure for table `type_rue`
--

DROP TABLE IF EXISTS `type_rue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `type_rue` (
  `type_rue_id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'Nombre unique pour chaque type de localisation.',
  `description_type_rue` varchar(15) DEFAULT NULL COMMENT 'Indique le nom pour chaque nombre unique de type_rue_id.',
  PRIMARY KEY (`type_rue_id`),
  UNIQUE KEY `type_rue_id_UNIQUE` (`type_rue_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='Table permettant de donner un nom précis pour une localisation.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type_rue`
--

LOCK TABLES `type_rue` WRITE;
/*!40000 ALTER TABLE `type_rue` DISABLE KEYS */;
INSERT INTO `type_rue` (`type_rue_id`, `description_type_rue`) VALUES (1,'Avenue'),(2,'Boulevard'),(3,'Carré'),(4,'Cercle'),(5,'Chemin'),(6,'Concession'),(7,'Côte'),(8,'Croissant'),(9,'Montée'),(10,'Place'),(11,'Promenade'),(12,'Rang'),(13,'Route'),(14,'Rue'),(15,'Ruelle'),(16,'Terrasse');
/*!40000 ALTER TABLE `type_rue` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-13 11:28:26
