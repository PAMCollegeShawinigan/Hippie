/* Table data export for table utilisateur */

/* Preserve session variables */
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS;
SET FOREIGN_KEY_CHECKS=0;

/* Export data */
INSERT INTO `utilisateur` (`utilisateur_id`,`mot_de_passe`,`nom`,`prenom`,`courriel`,`telephone`,`moyen_contact`,`organisme_id`,`derniere_connexion`) VALUES (4,"patate","Lachance","Pier-Alain","laluckalien@gmail.com","819-852-2704",1,NULL,"2015-11-25 21:01:43");
INSERT INTO `utilisateur` (`utilisateur_id`,`mot_de_passe`,`nom`,`prenom`,`courriel`,`telephone`,`moyen_contact`,`organisme_id`,`derniere_connexion`) VALUES (5,"test","test","test","test@test.com","123-456-7890",1,4,"2015-12-08 08:58:10");

/* Restore session variables to original values */
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
