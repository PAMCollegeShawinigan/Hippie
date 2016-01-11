/* Table data export for table transaction */

/* Preserve session variables */
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS;
SET FOREIGN_KEY_CHECKS=0;

/* Export data */
INSERT INTO `transaction` (`transaction_id`,`receveur_id`,`donneur_id`,`marchandise_id`,`date_collecte`,`date_reservation`,`date_disponible`,`date_transaction`) VALUES (1,1,6,1,"2015-12-02 00:00:00","2015-12-01 00:00:00","2015-12-15 00:00:00","2015-12-09 01:45:00");
INSERT INTO `transaction` (`transaction_id`,`receveur_id`,`donneur_id`,`marchandise_id`,`date_collecte`,`date_reservation`,`date_disponible`,`date_transaction`) VALUES (2,1,8,2,NULL,NULL,"0000-00-00 00:00:00","0000-00-00 00:00:00");
INSERT INTO `transaction` (`transaction_id`,`receveur_id`,`donneur_id`,`marchandise_id`,`date_collecte`,`date_reservation`,`date_disponible`,`date_transaction`) VALUES (3,1,5,1,NULL,NULL,"0000-00-00 00:00:00","0000-00-00 00:00:00");
INSERT INTO `transaction` (`transaction_id`,`receveur_id`,`donneur_id`,`marchandise_id`,`date_collecte`,`date_reservation`,`date_disponible`,`date_transaction`) VALUES (4,1,8,7,NULL,NULL,"0000-00-00 00:00:00","0000-00-00 00:00:00");
INSERT INTO `transaction` (`transaction_id`,`receveur_id`,`donneur_id`,`marchandise_id`,`date_collecte`,`date_reservation`,`date_disponible`,`date_transaction`) VALUES (5,2,5,3,NULL,"2015-12-23 00:00:00","0000-00-00 00:00:00","0000-00-00 00:00:00");
INSERT INTO `transaction` (`transaction_id`,`receveur_id`,`donneur_id`,`marchandise_id`,`date_collecte`,`date_reservation`,`date_disponible`,`date_transaction`) VALUES (6,2,6,4,NULL,"2015-12-22 00:00:00","0000-00-00 00:00:00","0000-00-00 00:00:00");
INSERT INTO `transaction` (`transaction_id`,`receveur_id`,`donneur_id`,`marchandise_id`,`date_collecte`,`date_reservation`,`date_disponible`,`date_transaction`) VALUES (7,3,5,5,NULL,NULL,"0000-00-00 00:00:00","0000-00-00 00:00:00");
INSERT INTO `transaction` (`transaction_id`,`receveur_id`,`donneur_id`,`marchandise_id`,`date_collecte`,`date_reservation`,`date_disponible`,`date_transaction`) VALUES (8,4,5,6,NULL,NULL,"0000-00-00 00:00:00","0000-00-00 00:00:00");
INSERT INTO `transaction` (`transaction_id`,`receveur_id`,`donneur_id`,`marchandise_id`,`date_collecte`,`date_reservation`,`date_disponible`,`date_transaction`) VALUES (9,4,6,1,"2015-12-06 00:00:00",NULL,"0000-00-00 00:00:00","0000-00-00 00:00:00");
INSERT INTO `transaction` (`transaction_id`,`receveur_id`,`donneur_id`,`marchandise_id`,`date_collecte`,`date_reservation`,`date_disponible`,`date_transaction`) VALUES (10,4,7,2,NULL,NULL,"0000-00-00 00:00:00","0000-00-00 00:00:00");
INSERT INTO `transaction` (`transaction_id`,`receveur_id`,`donneur_id`,`marchandise_id`,`date_collecte`,`date_reservation`,`date_disponible`,`date_transaction`) VALUES (11,4,5,3,NULL,NULL,"0000-00-00 00:00:00","0000-00-00 00:00:00");
INSERT INTO `transaction` (`transaction_id`,`receveur_id`,`donneur_id`,`marchandise_id`,`date_collecte`,`date_reservation`,`date_disponible`,`date_transaction`) VALUES (12,1,5,5,NULL,NULL,"0000-00-00 00:00:00","0000-00-00 00:00:00");
INSERT INTO `transaction` (`transaction_id`,`receveur_id`,`donneur_id`,`marchandise_id`,`date_collecte`,`date_reservation`,`date_disponible`,`date_transaction`) VALUES (13,1,8,2,NULL,NULL,"0000-00-00 00:00:00","0000-00-00 00:00:00");
INSERT INTO `transaction` (`transaction_id`,`receveur_id`,`donneur_id`,`marchandise_id`,`date_collecte`,`date_reservation`,`date_disponible`,`date_transaction`) VALUES (14,2,5,3,NULL,NULL,"0000-00-00 00:00:00","0000-00-00 00:00:00");
INSERT INTO `transaction` (`transaction_id`,`receveur_id`,`donneur_id`,`marchandise_id`,`date_collecte`,`date_reservation`,`date_disponible`,`date_transaction`) VALUES (15,2,6,4,"2015-12-24 00:00:00",NULL,"0000-00-00 00:00:00","0000-00-00 00:00:00");
INSERT INTO `transaction` (`transaction_id`,`receveur_id`,`donneur_id`,`marchandise_id`,`date_collecte`,`date_reservation`,`date_disponible`,`date_transaction`) VALUES (16,3,5,5,NULL,NULL,"0000-00-00 00:00:00","0000-00-00 00:00:00");
INSERT INTO `transaction` (`transaction_id`,`receveur_id`,`donneur_id`,`marchandise_id`,`date_collecte`,`date_reservation`,`date_disponible`,`date_transaction`) VALUES (17,4,5,6,NULL,NULL,"2015-12-18 00:00:00","0000-00-00 00:00:00");
INSERT INTO `transaction` (`transaction_id`,`receveur_id`,`donneur_id`,`marchandise_id`,`date_collecte`,`date_reservation`,`date_disponible`,`date_transaction`) VALUES (18,4,6,1,NULL,NULL,"0000-00-00 00:00:00","0000-00-00 00:00:00");
INSERT INTO `transaction` (`transaction_id`,`receveur_id`,`donneur_id`,`marchandise_id`,`date_collecte`,`date_reservation`,`date_disponible`,`date_transaction`) VALUES (19,4,7,2,NULL,NULL,"2015-12-21 00:00:00","0000-00-00 00:00:00");
INSERT INTO `transaction` (`transaction_id`,`receveur_id`,`donneur_id`,`marchandise_id`,`date_collecte`,`date_reservation`,`date_disponible`,`date_transaction`) VALUES (20,4,5,3,NULL,NULL,"2015-12-02 00:00:00","0000-00-00 00:00:00");
INSERT INTO `transaction` (`transaction_id`,`receveur_id`,`donneur_id`,`marchandise_id`,`date_collecte`,`date_reservation`,`date_disponible`,`date_transaction`) VALUES (21,NULL,6,4,NULL,NULL,"2015-11-29 00:08:30","0000-00-00 00:00:00");
INSERT INTO `transaction` (`transaction_id`,`receveur_id`,`donneur_id`,`marchandise_id`,`date_collecte`,`date_reservation`,`date_disponible`,`date_transaction`) VALUES (22,1,6,1,NULL,"2015-12-01 00:00:00","0000-00-00 00:00:00","2015-12-09 01:45:46");
INSERT INTO `transaction` (`transaction_id`,`receveur_id`,`donneur_id`,`marchandise_id`,`date_collecte`,`date_reservation`,`date_disponible`,`date_transaction`) VALUES (23,1,6,1,NULL,"0000-00-00 00:00:00","0000-00-00 00:00:00","2015-12-09 01:46:49");
INSERT INTO `transaction` (`transaction_id`,`receveur_id`,`donneur_id`,`marchandise_id`,`date_collecte`,`date_reservation`,`date_disponible`,`date_transaction`) VALUES (24,1,6,1,NULL,"0000-00-00 00:00:00","0000-00-00 00:00:00","2015-12-09 01:48:59");

/* Restore session variables to original values */
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
