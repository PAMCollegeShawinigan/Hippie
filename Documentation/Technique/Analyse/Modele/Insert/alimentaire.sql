/* Table data export for table alimentaire */

/* Preserve session variables */
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS;
SET FOREIGN_KEY_CHECKS=0;

/* Export data */
INSERT INTO `alimentaire` (`alimentaire_id`,`nom`,`description_alimentaire`,`quantite`,`marchandise_etat`,`marchandise_unite`,`valeur`,`marchandise_statut`,`type_alimentaire`,`date_peremption`) VALUES (1,"Raisins","3 caisses raisins rouges sans pépins",3.00,1,3,15,3,1,"2015-12-12 05:00:00");
INSERT INTO `alimentaire` (`alimentaire_id`,`nom`,`description_alimentaire`,`quantite`,`marchandise_etat`,`marchandise_unite`,`valeur`,`marchandise_statut`,`type_alimentaire`,`date_peremption`) VALUES (2,"Pains","Pains sandwich POM",12.00,1,3,45,3,6,"2015-11-30 05:00:00");
INSERT INTO `alimentaire` (`alimentaire_id`,`nom`,`description_alimentaire`,`quantite`,`marchandise_etat`,`marchandise_unite`,`valeur`,`marchandise_statut`,`type_alimentaire`,`date_peremption`) VALUES (3,"Lait","45 X 2L Nutrilait 3.25%",90.00,1,5,175,2,2,"2015-11-30 05:00:00");
INSERT INTO `alimentaire` (`alimentaire_id`,`nom`,`description_alimentaire`,`quantite`,`marchandise_etat`,`marchandise_unite`,`valeur`,`marchandise_statut`,`type_alimentaire`,`date_peremption`) VALUES (4,"Steak Haché","Mi-maigre, congelé",30.00,1,1,150,2,3,"2016-02-28 05:00:00");
INSERT INTO `alimentaire` (`alimentaire_id`,`nom`,`description_alimentaire`,`quantite`,`marchandise_etat`,`marchandise_unite`,`valeur`,`marchandise_statut`,`type_alimentaire`,`date_peremption`) VALUES (5,"Soupe Tomates","Selection merite",75.00,1,3,70,3,5,NULL);
INSERT INTO `alimentaire` (`alimentaire_id`,`nom`,`description_alimentaire`,`quantite`,`marchandise_etat`,`marchandise_unite`,`valeur`,`marchandise_statut`,`type_alimentaire`,`date_peremption`) VALUES (6,"Légumes variés","Selection Merite, sacs congelés",9.00,1,3,40,3,4,NULL);
INSERT INTO `alimentaire` (`alimentaire_id`,`nom`,`description_alimentaire`,`quantite`,`marchandise_etat`,`marchandise_unite`,`valeur`,`marchandise_statut`,`type_alimentaire`,`date_peremption`) VALUES (7,"Carottes","Carottes tres biologiques",6.00,3,1,35,2,1,"2015-12-08 00:00:00");

/* Restore session variables to original values */
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
