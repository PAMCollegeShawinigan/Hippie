/* Table data export for table marchandise_etat */

/* Preserve session variables */
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS;
SET FOREIGN_KEY_CHECKS=0;

/* Export data */
INSERT INTO `marchandise_etat` (`etat_id`,`description_marchandise_etat`) VALUES (1,"Incomplet");
INSERT INTO `marchandise_etat` (`etat_id`,`description_marchandise_etat`) VALUES (2,"Mauvais État");
INSERT INTO `marchandise_etat` (`etat_id`,`description_marchandise_etat`) VALUES (3,"Neuf");
INSERT INTO `marchandise_etat` (`etat_id`,`description_marchandise_etat`) VALUES (4,"Usagé");

/* Restore session variables to original values */
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
