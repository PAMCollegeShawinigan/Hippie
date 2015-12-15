/* Table data export for table adresse */

/* Preserve session variables */
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS;
SET FOREIGN_KEY_CHECKS=0;

/* Export data */
INSERT INTO `adresse` (`adresse_id`,`no_civique`,`type_rue`,`nom`,`app`,`ville`,`province`,`code_postal`,`pays`) VALUES (19,"1",1,"Rue 1",NULL,"Shawinigan","Qc","XXX XXX","Canada");
INSERT INTO `adresse` (`adresse_id`,`no_civique`,`type_rue`,`nom`,`app`,`ville`,`province`,`code_postal`,`pays`) VALUES (20,"2",2,"Promenade_2",NULL,"Shawinigan","Qc","XXX XXX","Canada");
INSERT INTO `adresse` (`adresse_id`,`no_civique`,`type_rue`,`nom`,`app`,`ville`,`province`,`code_postal`,`pays`) VALUES (21,"3",3,"Montee 9",NULL,"Shawinigan","Qc","XXX XXX","Canada");
INSERT INTO `adresse` (`adresse_id`,`no_civique`,`type_rue`,`nom`,`app`,`ville`,`province`,`code_postal`,`pays`) VALUES (22,"4",4,"Place 4",NULL,"Shawinigan","Qc","XXX XXX","Canada");
INSERT INTO `adresse` (`adresse_id`,`no_civique`,`type_rue`,`nom`,`app`,`ville`,`province`,`code_postal`,`pays`) VALUES (23,"5",5,"Rang 5",NULL,"Shawinigan","Qc","XXX XXX","Canada");
INSERT INTO `adresse` (`adresse_id`,`no_civique`,`type_rue`,`nom`,`app`,`ville`,`province`,`code_postal`,`pays`) VALUES (24,"6",6,"Route 6",NULL,"Shawinigan","Qc","XXX XXX","Canada");
INSERT INTO `adresse` (`adresse_id`,`no_civique`,`type_rue`,`nom`,`app`,`ville`,`province`,`code_postal`,`pays`) VALUES (25,"7",7,"Avenue 7",NULL,"Shawinigan","Qc","XXX XXX","Canada");
INSERT INTO `adresse` (`adresse_id`,`no_civique`,`type_rue`,`nom`,`app`,`ville`,`province`,`code_postal`,`pays`) VALUES (26,"8",2,"Boulevard 8",NULL,"Shawinigan","Qc","XXX XXX",NULL);

/* Restore session variables to original values */
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
