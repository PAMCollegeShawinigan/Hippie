/* Table data export for table organisme */

/* Preserve session variables */
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS;
SET FOREIGN_KEY_CHECKS=0;

/* Export data */
INSERT INTO `organisme` (`organisme_id`,`nom`,`adresse`,`telephone`,`poste`,`utilisateur_contact`,`no_entreprise`,`no_osbl`) VALUES (1,"Organisme_1",19,"",NULL,4,NULL,NULL);
INSERT INTO `organisme` (`organisme_id`,`nom`,`adresse`,`telephone`,`poste`,`utilisateur_contact`,`no_entreprise`,`no_osbl`) VALUES (2,"Organisme_2",20,"",NULL,4,NULL,NULL);
INSERT INTO `organisme` (`organisme_id`,`nom`,`adresse`,`telephone`,`poste`,`utilisateur_contact`,`no_entreprise`,`no_osbl`) VALUES (3,"Organisme_3",21,"",NULL,4,NULL,NULL);
INSERT INTO `organisme` (`organisme_id`,`nom`,`adresse`,`telephone`,`poste`,`utilisateur_contact`,`no_entreprise`,`no_osbl`) VALUES (4,"Organisme_4",22,"",NULL,4,NULL,NULL);
INSERT INTO `organisme` (`organisme_id`,`nom`,`adresse`,`telephone`,`poste`,`utilisateur_contact`,`no_entreprise`,`no_osbl`) VALUES (5,"Entreprise_5",23,"",NULL,4,NULL,NULL);
INSERT INTO `organisme` (`organisme_id`,`nom`,`adresse`,`telephone`,`poste`,`utilisateur_contact`,`no_entreprise`,`no_osbl`) VALUES (6,"Entreprise_6",24,"",NULL,4,NULL,NULL);
INSERT INTO `organisme` (`organisme_id`,`nom`,`adresse`,`telephone`,`poste`,`utilisateur_contact`,`no_entreprise`,`no_osbl`) VALUES (7,"Entreprise_7",25,"",NULL,4,NULL,NULL);
INSERT INTO `organisme` (`organisme_id`,`nom`,`adresse`,`telephone`,`poste`,`utilisateur_contact`,`no_entreprise`,`no_osbl`) VALUES (8,"Entreprise_5",26,"",NULL,4,NULL,NULL);

/* Restore session variables to original values */
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
