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
  `valeur` decimal(10,2) DEFAULT '0.00' COMMENT 'Nombre référant au prix estimé sans décimales.',
  `marchandise_statut` int(10) DEFAULT NULL COMMENT 'Nombre référant à la clé étrangère de la table marchandise_statut.',
  `type_alimentaire` int(10) DEFAULT NULL COMMENT 'Nombre référant à la clé étrangère de la table type_aliment.',
  `date_peremption` datetime DEFAULT NULL COMMENT 'Date(DATETIME) pour la durée de conservation de l''aliment.',
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
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8 COMMENT='Table permettant de lister les aliments disponibles pour les organismes.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alimentaire`
--

LOCK TABLES `alimentaire` WRITE;
/*!40000 ALTER TABLE `alimentaire` DISABLE KEYS */;
INSERT INTO `alimentaire` (`alimentaire_id`, `nom`, `description_alimentaire`, `quantite`, `marchandise_etat`, `marchandise_unite`, `valeur`, `marchandise_statut`, `type_alimentaire`, `date_peremption`) VALUES (98,'Pains','Pains blanc Pom',12.00,3,3,5.00,2,6,'2016-07-14 18:52:33'),(99,'Porc','Longe de porc du Québec',20.00,3,1,30.00,2,3,'2016-05-28 18:53:30'),(100,'Fromage','Mozzarella 450g',30.00,3,3,20.00,3,2,'2016-07-07 18:54:32'),(101,'Yogourt','16\\emballage saveur varié',6.00,3,3,15.00,3,2,'2016-07-02 18:56:09'),(102,'Rosbif','Rosbif du roi bardé',10.00,3,2,25.00,4,3,'2016-09-29 18:58:21'),(103,'Céréales','Froots Loops boite de 755g',32.00,3,3,50.00,2,5,NULL),(104,'Farine','Blanche tout usage',30.00,3,2,20.00,2,6,'2016-08-31 19:01:12'),(105,'Pains','Hot-dog paquet de 12',6.00,3,3,10.00,2,6,'2016-06-28 19:02:22'),(106,'Saumon','De l\'Atlantique',5.25,3,1,30.00,2,3,'2016-05-29 19:03:44'),(107,'Patates','Sac de 10lbs',12.00,3,3,8.00,2,1,'2016-07-10 19:06:11'),(108,'Carottes','sac de 5lbs',14.00,3,3,14.00,4,1,'2016-04-29 19:06:59'),(109,'Brocolis','Doit être utilisées rapidement',20.00,3,1,10.00,4,1,'2016-01-27 19:08:14'),(110,'Fraises congelés','en tranche',32.00,3,3,18.00,4,4,NULL),(111,'Légumes du jardin','Congelés sac de 3kg',23.25,3,3,22.00,3,4,NULL),(117,'tccftctt','fsvdcxtsst',0.00,3,4,0.00,3,4,NULL),(118,'gross ','t\'as pas de ',25.00,3,3,26.00,3,5,NULL),(119,'je a','i',25.00,3,2,65.00,3,5,NULL),(120,'je a','i',25.00,3,2,65.00,3,5,NULL),(121,'je a','i',25.00,3,2,65.00,3,5,NULL),(122,'je aa','i',25.00,3,2,65.00,3,5,NULL);
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

-- Dump completed on 2016-02-18  9:30:00
