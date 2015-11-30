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
-- Table structure for table `Alimentaire`
--

DROP TABLE IF EXISTS `Alimentaire`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Alimentaire` (
  `id_alimentaire` int(10) NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) NOT NULL,
  `description` varchar(100) NOT NULL,
  `quantite` double(10,2) NOT NULL,
  `marchandise_etat` int(10) NOT NULL,
  `marchandise_qtee_unite` int(10) NOT NULL,
  `valeur` int(10) DEFAULT '0',
  `marchandise_statut` int(10) NOT NULL,
  `type_alimentaire` int(10) NOT NULL,
  `date_peremption` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id_alimentaire`),
  KEY `Marchandise_Etat_FK_idx` (`marchandise_etat`),
  KEY `Marchandise_Qtee_Unite_FK_idx` (`marchandise_qtee_unite`),
  KEY `Marchandise_Statut_FK_idx` (`marchandise_statut`),
  KEY `Type_Aliment_FK_idx` (`type_alimentaire`),
  CONSTRAINT `Marchandise_Etat_FK` FOREIGN KEY (`marchandise_etat`) REFERENCES `Marchandise_Etat` (`id_etat`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Marchandise_Qtee_Unite_FK` FOREIGN KEY (`marchandise_qtee_unite`) REFERENCES `Marchandise_Qtee_Unite` (`id_qtee_unite`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Marchandise_Statut_FK` FOREIGN KEY (`marchandise_statut`) REFERENCES `Marchandise_Statut` (`id_statut`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Type_Aliment_FK` FOREIGN KEY (`type_alimentaire`) REFERENCES `Type_Aliment` (`id_aliment`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Alimentaire`
--

LOCK TABLES `Alimentaire` WRITE;
/*!40000 ALTER TABLE `Alimentaire` DISABLE KEYS */;
/*!40000 ALTER TABLE `Alimentaire` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-11-27 13:32:29
