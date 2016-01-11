/* Table data export for table marchandise_statut */

/* Preserve session variables */
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS;
SET FOREIGN_KEY_CHECKS=0;

/* Export data */
INSERT INTO `marchandise_statut` (`statut_id`,`description_marchandise_statut`) VALUES (1,"En Traitement");
INSERT INTO `marchandise_statut` (`statut_id`,`description_marchandise_statut`) VALUES (2,"Réservé");
INSERT INTO `marchandise_statut` (`statut_id`,`description_marchandise_statut`) VALUES (3,"Disponible");
INSERT INTO `marchandise_statut` (`statut_id`,`description_marchandise_statut`) VALUES (4,"Collecté");
INSERT INTO `marchandise_statut` (`statut_id`,`description_marchandise_statut`) VALUES (5,"Archivé");
INSERT INTO `marchandise_statut` (`statut_id`,`description_marchandise_statut`) VALUES (6,"Périmé");

/* Restore session variables to original values */
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
