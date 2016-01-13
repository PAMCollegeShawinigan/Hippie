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
-- Table structure for table `alimentaire`
--

DROP TABLE IF EXISTS `alimentaire`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alimentaire` (
  `alimentaire_id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'Numéro unique pour chaque type de marchandise disponible.',
  `nom` varchar(50) DEFAULT NULL COMMENT 'Représente le nom de l''aliment.',
  `description_alimentaire` varchar(100) DEFAULT NULL COMMENT 'Décris ce qu''est l''aliment.',
  `quantite` double(10,2) DEFAULT NULL COMMENT 'Indique la quantité de l''aliment.',
  `marchandise_etat` int(10) DEFAULT NULL COMMENT 'Nombre référant à la clé étrangère de la table marchandise_etat.',
  `marchandise_unite` int(10) DEFAULT NULL COMMENT 'Nombre référant à la clé étrangère de la table marchandise_unite.',
  `valeur` int(10) DEFAULT '0' COMMENT 'Nombre référant au prix estimé sans décimales.',
  `marchandise_statut` int(10) DEFAULT NULL COMMENT 'Nombre référant à la clé étrangère de la table marchandise_statut.',
  `type_alimentaire` int(10) DEFAULT NULL COMMENT 'Nombre référant à la clé étrangère de la table type_aliment.',
  `date_peremption` datetime DEFAULT NULL COMMENT 'Date(timestamp) pour la durée de conservation de l''aliment.',
  PRIMARY KEY (`alimentaire_id`),
  UNIQUE KEY `alimentaire_id_UNIQUE` (`alimentaire_id`),
  KEY `fk_marchandise_etat$alimentaire` (`marchandise_etat`),
  KEY `fk_marchandise_unite$alimentaire` (`marchandise_unite`),
  KEY `fk_marchandise_statut$alimentaire` (`marchandise_statut`),
  KEY `fk_type_aliment$alimentaire` (`type_alimentaire`),
  CONSTRAINT `fk_marchandise_etat$alimentaire` FOREIGN KEY (`marchandise_etat`) REFERENCES `marchandise_etat` (`etat_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_marchandise_statut$alimentaire` FOREIGN KEY (`marchandise_statut`) REFERENCES `marchandise_statut` (`statut_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_marchandise_unite$alimentaire` FOREIGN KEY (`marchandise_unite`) REFERENCES `marchandise_unite` (`unite_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_type_aliment$alimentaire` FOREIGN KEY (`type_alimentaire`) REFERENCES `type_aliment` (`aliment_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 COMMENT='Table permettant de lister les aliments disponibles pour les organismes.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alimentaire`
--

LOCK TABLES `alimentaire` WRITE;
/*!40000 ALTER TABLE `alimentaire` DISABLE KEYS */;
INSERT INTO `alimentaire` (`alimentaire_id`, `nom`, `description_alimentaire`, `quantite`, `marchandise_etat`, `marchandise_unite`, `valeur`, `marchandise_statut`, `type_alimentaire`, `date_peremption`) VALUES (1,'Raisins','3 caisses raisins rouges sans pépins',3.00,1,3,15,3,1,'2015-12-31 05:00:00'),(2,'Pains','Pains sandwich POM',12.00,1,4,45,4,6,'2015-11-30 05:00:00'),(3,'Lait','45 X 2L Nutrilait 3.25%',90.00,1,5,175,2,2,'2015-11-30 05:00:00'),(4,'Steak Haché','Mi-maigre, congelé',30.00,1,1,150,2,3,'2016-02-28 05:00:00'),(5,'Soupe Tomates','Selection merite',75.00,1,3,70,3,5,NULL),(6,'Légumes variés','Selection Merite, sacs congelés',9.00,1,3,40,3,4,NULL),(7,'Carottes','Carottes tres biologiques',6.00,3,1,35,2,1,'2015-12-08 00:00:00'),(8,'Postman Test','test',253.00,3,3,33,3,1,'0000-00-00 00:00:00'),(9,'LOL','BOB',25.50,3,3,56,3,3,'0000-00-00 00:00:00'),(26,'Ouch P-A!','Test',253.00,3,3,33,3,1,'0000-00-00 00:00:00'),(27,'TETE','EPAULE',25.00,3,3,32,3,3,'0000-00-00 00:00:00'),(28,'TETE2','ÉPAULE',25.00,3,3,32,3,3,'0000-00-00 00:00:00'),(29,'KLP','KOP',6.00,3,1,3,3,1,'0000-00-00 00:00:00'),(30,'JIO','HHJJK',2.00,3,1,5,3,4,'0000-00-00 00:00:00'),(31,'TEST','TEST',25.00,3,2,58,3,3,'2016-01-28 11:13:38'),(32,'LKPO','POLK',2.00,3,2,5,3,2,'2016-01-22 11:27:49'),(33,'TEST','TEST',5.00,3,1,2,3,5,'2016-01-22 11:29:23'),(34,'PAIN','PAINS À HAMBURBER',20.00,3,3,25,3,6,'0000-00-00 00:00:00'),(35,'PIEUVRE','PATTES DE PIEUVRE',96.00,3,3,150,3,3,'2016-01-29 12:57:14'),(36,'PATATES','PATATES FRITES',50.00,3,2,50,3,4,'2016-01-29 12:59:19'),(43,'PATEST','TEST',1234.00,3,4,5,3,4,NULL),(44,'TESTPA PERISSABLE','TESTPA',5.00,3,3,5,3,1,'2016-01-29 13:59:34'),(45,'RENE','RENE',10.43,3,3,25,3,2,'2016-01-14 14:16:32'),(46,'GHJ','CARL',1.00,3,1,5,3,4,NULL);
/*!40000 ALTER TABLE `alimentaire` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-13 11:28:24
