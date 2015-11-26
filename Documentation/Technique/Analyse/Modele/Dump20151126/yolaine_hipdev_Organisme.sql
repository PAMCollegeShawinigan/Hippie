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
-- Table structure for table `Organisme`
--

DROP TABLE IF EXISTS `Organisme`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Organisme` (
  `id_organisme` int(10) NOT NULL AUTO_INCREMENT,
  `nom` varchar(30) CHARACTER SET latin1 NOT NULL,
  `adresse` int(10) NOT NULL,
  `telephone` varchar(10) CHARACTER SET latin1 NOT NULL,
  `poste` int(5) DEFAULT NULL,
  `contact` int(11) NOT NULL,
  `no_entreprise` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  `no_osbl` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (`id_organisme`),
  KEY `Adresse_FK_idx` (`adresse`),
  KEY `Utilisateur_FK_idx` (`contact`),
  CONSTRAINT `Adresse_FK` FOREIGN KEY (`adresse`) REFERENCES `Adresse` (`id_adresse`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Utilisateur_FK` FOREIGN KEY (`contact`) REFERENCES `Utilisateur` (`id_utilisateur`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Organisme`
--

LOCK TABLES `Organisme` WRITE;
/*!40000 ALTER TABLE `Organisme` DISABLE KEYS */;
INSERT INTO `Organisme` (`id_organisme`, `nom`, `adresse`, `telephone`, `poste`, `contact`, `no_entreprise`, `no_osbl`) VALUES (12,'Organisme_1',19,'',NULL,4,NULL,NULL),(13,'Organisme_2',20,'',NULL,4,NULL,NULL),(14,'Organisme_3',21,'',NULL,4,NULL,NULL),(15,'Organisme_4',22,'',NULL,4,NULL,NULL),(16,'Entreprise_5',23,'',NULL,4,NULL,NULL),(17,'Entreprise_6',24,'',NULL,4,NULL,NULL),(18,'Entreprise_7',25,'',NULL,4,NULL,NULL),(19,'Entreprise_5',26,'',NULL,4,NULL,NULL);
/*!40000 ALTER TABLE `Organisme` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-11-26  8:44:43
