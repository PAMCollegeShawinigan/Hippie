/* Table data export for table marchandise_unite */

/* Preserve session variables */
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS;
SET FOREIGN_KEY_CHECKS=0;

/* Export data */
INSERT INTO `marchandise_unite` (`unite_id`,`description_marchandise_unite`) VALUES (1,"Lbs");
INSERT INTO `marchandise_unite` (`unite_id`,`description_marchandise_unite`) VALUES (2,"Kg");
INSERT INTO `marchandise_unite` (`unite_id`,`description_marchandise_unite`) VALUES (3,"Unité(s)");
INSERT INTO `marchandise_unite` (`unite_id`,`description_marchandise_unite`) VALUES (4,"Ml");
INSERT INTO `marchandise_unite` (`unite_id`,`description_marchandise_unite`) VALUES (5,"L");
INSERT INTO `marchandise_unite` (`unite_id`,`description_marchandise_unite`) VALUES (6,"Oz");

/* Restore session variables to original values */
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
