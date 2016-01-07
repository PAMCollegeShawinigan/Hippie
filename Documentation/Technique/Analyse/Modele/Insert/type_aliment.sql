/* Table data export for table type_aliment */

/* Preserve session variables */
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS;
SET FOREIGN_KEY_CHECKS=0;

/* Export data */
INSERT INTO `type_aliment` (`aliment_id`,`description_type_aliment`) VALUES (1,"Fruits et Légumes");
INSERT INTO `type_aliment` (`aliment_id`,`description_type_aliment`) VALUES (2,"Produits laitiers");
INSERT INTO `type_aliment` (`aliment_id`,`description_type_aliment`) VALUES (3,"Viandes");
INSERT INTO `type_aliment` (`aliment_id`,`description_type_aliment`) VALUES (4,"Surgelés");
INSERT INTO `type_aliment` (`aliment_id`,`description_type_aliment`) VALUES (5,"Non Périssable");
INSERT INTO `type_aliment` (`aliment_id`,`description_type_aliment`) VALUES (6,"Boulangerie");

/* Restore session variables to original values */
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
