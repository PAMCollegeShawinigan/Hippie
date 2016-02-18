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
-- Dumping events for database 'yolaine_hipdev'
--
/*!50106 SET @save_time_zone= @@TIME_ZONE */ ;
/*!50106 DROP EVENT IF EXISTS `event_donneur_mois` */;
DELIMITER ;;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;;
/*!50003 SET character_set_client  = latin1 */ ;;
/*!50003 SET character_set_results = latin1 */ ;;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;;
/*!50003 SET sql_mode              = '' */ ;;
/*!50003 SET @saved_time_zone      = @@time_zone */ ;;
/*!50003 SET time_zone             = 'SYSTEM' */ ;;
/*!50106 CREATE*/ /*!50117 DEFINER=`yolaine`@`localhost`*/ /*!50106 EVENT `event_donneur_mois` ON SCHEDULE EVERY 1 MONTH STARTS '2016-02-01 00:00:00' ON COMPLETION PRESERVE ENABLE DO CALL proc_donneur_mois() */ ;;
/*!50003 SET time_zone             = @saved_time_zone */ ;;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;;
/*!50003 SET character_set_client  = @saved_cs_client */ ;;
/*!50003 SET character_set_results = @saved_cs_results */ ;;
/*!50003 SET collation_connection  = @saved_col_connection */ ;;
DELIMITER ;
/*!50106 SET TIME_ZONE= @save_time_zone */ ;

--
-- Dumping routines for database 'yolaine_hipdev'
--
/*!50003 DROP PROCEDURE IF EXISTS `proc_donneur_mois` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`yolaine_yolainec`@`localhost` PROCEDURE `proc_donneur_mois`()
BEGIN

	DECLARE done INT DEFAULT 0;
    
    DECLARE don_id INT;
    DECLARE max_don DECIMAL(10,2);
    DECLARE date_don DATE;
    
    DECLARE cur CURSOR FOR
		/*
			maximum des sommation des transactions par donneur par période
		*/
		SELECT
			trx2.donneur_id,
			MAX(sum_donneur) AS max_donneur,
			str_to_date(CONCAT(EXTRACT(YEAR_MONTH FROM trx2.date_collecte),'01'), '%Y%m%d') AS date_coll
			-- trx2.date_collecte,
			-- EXTRACT(YEAR_MONTH FROM SYSDATE()) -1
		FROM yolaine_hipdev.transaction trx2
		RIGHT JOIN yolaine_hipdev.organisme org2 ON org2.organisme_id = trx2.donneur_id
		RIGHT JOIN yolaine_hipdev.alimentaire ali2 ON ali2.alimentaire_id = trx2.marchandise_id
		LEFT JOIN 
		(SELECT
				trx.donneur_id AS don_id,
				SUM(ali.valeur) AS sum_donneur
				
				FROM yolaine_hipdev.transaction trx
				JOIN yolaine_hipdev.organisme org ON org.organisme_id = trx.donneur_id
				JOIN yolaine_hipdev.alimentaire ali ON ali.alimentaire_id = trx.marchandise_id
				WHERE EXTRACT(YEAR_MONTH FROM trx.date_collecte) BETWEEN (EXTRACT(YEAR_MONTH FROM SYSDATE())) -1  AND (EXTRACT(YEAR_MONTH FROM SYSDATE())) - 1
				GROUP BY trx.donneur_id
			)somme_donneur
					ON trx2.donneur_id = don_id
		WHERE EXTRACT(YEAR_MONTH FROM trx2.date_collecte) BETWEEN (EXTRACT(YEAR_MONTH FROM SYSDATE())) - 1 AND (EXTRACT(YEAR_MONTH FROM SYSDATE())) - 1
		;
		
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
    -- TODO a partir d'ici
    OPEN cur;
	
    START TRANSACTION; 
	read_loop: LOOP
  
	FETCH cur INTO don_id, max_don, date_don;
    
    IF done THEN
      LEAVE read_loop;
    END IF;
    
	
  
  END LOOP;
  COMMIT;
  CLOSE cur;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-18  9:30:05
