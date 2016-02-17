/*-------------------------------------------------------------------------------------------------*/ 
/* Fichier créé par : Jean-François Noël                                                           */
/* Cours: Projet d'intégration                                                                     */
/* Titre du projet : denrée ô suivant                                                              */
/* Date de création : 2016-02-13                                                                   */
/*                                                                                                 */
/* J'ai regroupé l'ensemble des requêtes faites par les sections suivantes:                        */
/*                                                                                                 */
/* 1- Les insertions (INSERT)                                                                      */
/* 2- Les mises à jour (UPDATE)                                                                    */
/* 3- Les altérations (ALTER)                                                                      */
/* 4- La création de la table: donneur_mois						                                   */
/* 5- Les sélections de données (SELECT)                                                           */
/*                                                                                                 */ 
/*-------------------------------------------------------------------------------------------------*/

/*-------------------------------------------------------------------------------------------------*/
/* 1- Les insertions (INSERT)                                                                      */
/*-------------------------------------------------------------------------------------------------*/

/* Pour la table :  adresse                                                                        */                                        
INSERT INTO `adresse` VALUES (19,"2193",1,"Saint-Marc",NULL,"Shawinigan","Qc","G9N2J4","Canada");
INSERT INTO `adresse` VALUES (20,"599",14,"4e",NULL,"Shawinigan","Qc","G9N1G9","Canada");
INSERT INTO `adresse` VALUES (21,"1560",1,"5e",NULL,"Shawinigan","Qc","G9N1M5","Canada");
INSERT INTO `adresse` VALUES (22,"2183",1,"Saint-Marc",NULL,"Shawinigan","Qc","G9N2J4","Canada");
INSERT INTO `adresse` VALUES (23,"1500",14,"Trudel",NULL,"Shawinigan","Qc","G9N8K8","Canada");
INSERT INTO `adresse` VALUES (24,"3283",2,"Royal",NULL,"Shawinigan","Qc","G9N8K7","Canada");
INSERT INTO `adresse` VALUES (25,"850",1,"7e",NULL,"Grand-Mère","Qc","G9T2B8","Canada");
INSERT INTO `adresse` VALUES (26,"4175",1,"12e",NULL,"Shawinigan","Qc","G9P4G3","Canada");

/* Pour la table :  alimentaire                                                                    */
INSERT INTO `alimentaire` VALUES (47,"Framboises","Framboises importées du Mexique",12.00,3,7,170,1,1,"2016-01-16 21:09:12");
INSERT INTO `alimentaire` VALUES (48,"Bleuets","Bleuets importés du Chili",15.00,3,7,170,2,1,"2016-01-16 21:12:00");
INSERT INTO `alimentaire` VALUES (49,"Mûres","Mûres importées du Mexique",50.00,3,7,170,3,1,"2016-01-16 21:15:00");
INSERT INTO `alimentaire` VALUES (50,"Cerises","Cerises importées du Chili",30.00,3,2,5,4,1,"2016-01-16 21:19:00");
INSERT INTO `alimentaire` VALUES (51,"Mini concombres","Mini concombres importés",40.00,3,7,397,5,1,"2016-01-16 21:25:00");
INSERT INTO `alimentaire` VALUES (52,"Nectarines","Nectarines importées du Chili,  Cat. No 1, GR 60-70",20.00,3,2,4,6,1,"2016-01-16 21:28:00");

/* Pour la table :  alimentaire via -PHP-                                                          */
/* Ajout marchandise, fichier alimentaire.php, fonction: ajoutalimentaire()
   REMPLACER chaque valeur correspondant au champ de la table à la ligne (VALUES) 
   dans les parenthèses                                                                            */
   
INSERT INTO alimentaire (nom, description_alimentaire, quantite, marchandise_etat, marchandise_unite, valeur, marchandise_statut, type_alimentaire, date_peremption) 
				VALUES(:nom, :description_alimentaire, :quantite, :marchandise_etat, :marchandise_unite, :valeur, :marchandise_statut, :type_alimentaire, :date_peremption);

/* Pour la table :  donneur_mois                                                                   */
INSERT INTO `yolaine_hipdev`.`donneur_mois`(`organisme_id`,`montant_total`,`date`)VALUES(5,3000,'2015-10-25 23:00:00');
INSERT INTO `yolaine_hipdev`.`donneur_mois`(`organisme_id`,`montant_total`,`date`)VALUES(6,2500,'2015-11-25 23:00:00');
INSERT INTO `yolaine_hipdev`.`donneur_mois`(`organisme_id`,`montant_total`,`date`)VALUES(7,2450,'2015-12-25 23:00:00');
INSERT INTO `yolaine_hipdev`.`donneur_mois`(`organisme_id`,`montant_total`,`date`)VALUES(8,3560,'2016-01-25 23:00:00');

/* Pour la table :  marchandise_unite                                                              */
INSERT INTO `marchandise_unite` VALUES (7,"gr");

/* Pour la table :  organisme                                                                      */
INSERT INTO `organisme` VALUES (1,"La Tablée Élisabeth Bruyère",19,"8195374884",0,7,"1143475094","119009199RR0001 ");
INSERT INTO `organisme` VALUES (2,"La Tablée Mère D\'Youville",20,"8195373477",NULL,8,"1144206712	","119009199RR0001 ");
INSERT INTO `organisme` VALUES (3,"La Tablée Marcelle Mallet",21,"8195379813",NULL,9,"1144206712	","119009199RR0001 ");
INSERT INTO `organisme` VALUES (4,"Centre Roland-Bertrand",22,"8195378851",NULL,4,"1143475094","107860108RR0001");
INSERT INTO `organisme` VALUES (5,"Supermarché IGA extra Shawinigan",23,"8195364141",NULL,10,"1163343586",NULL);
INSERT INTO `organisme` VALUES (6,"Super C",24,"8195397498",NULL,11,"2243669118",NULL);
INSERT INTO `organisme` VALUES (7,"Alimentation Gauthier et Frères",25,"8195334553",NULL,12,"1143964634",NULL);
INSERT INTO `organisme` VALUES (8,"Métro Plus Shawinigan",26,"8195373724",NULL,4,"1165196495",NULL);

/* Pour la table :  type_aliment                                                                   */
INSERT INTO `type_aliment` SET `aliment_id`="7",`description_type_aliment`="Non comestible";

/* Pour la table :  transaction                                                                    */
INSERT INTO `transaction` SET `receveur_id`="1",`donneur_id`="5",`marchandise_id`="47",`date_disponible`="2016-01-17 11:05:00",`date_transaction`="2016-01-17 11:04:00";
INSERT INTO `transaction` SET `receveur_id`="2",`donneur_id`="6",`marchandise_id`="48",`date_collecte`="NULL",`date_reservation`="2016-01-17 11:10:00",`date_disponible`="2016-01-17 11:08:00",`date_transaction`="2016-01-17 11:07:00";
INSERT INTO `transaction` SET `receveur_id`="3",`donneur_id`="7",`marchandise_id`="49",`date_disponible`="2016-01-17 11:55:00",`date_transaction`="2016-01-17 11:54:00";
INSERT INTO `transaction` SET `receveur_id`="4",`donneur_id`="8",`marchandise_id`="50",`date_collecte`="2016-01-17 13:05:0",`date_reservation`="2016-01-17 12:05:0",`date_disponible`="2016-01-17 11:59:00",`date_transaction`="2016-01-17 11:59:0";
INSERT INTO `transaction` SET `receveur_id`="1",`donneur_id`="5",`marchandise_id`="51",`date_collecte`="2016-01-17 14:05:00",`date_reservation`="2016-01-17 13:15:00",`date_disponible`="2016-01-17 12:05:00",`date_transaction`="2016-01-17 12:00:00";
INSERT INTO `transaction` SET `donneur_id`="2",`marchandise_id`="52",`date_disponible`="2016-01-17 12:06:00",`date_transaction`="2016-01-17 12:05:00";

/* Pour la table :  transaction via -PHP-                                                          */
/* Ajout d'une réservation, fichier reservation.php, fonction: reservationajout()	
   REMPLACER les valeurs : :donneur_id, :receveur_id, :marchandise_id, :date_disponible. 
   L'item choisi prendra aura une nouvelle date et heure de disponibilité.                         */
   
INSERT INTO transaction (donneur_id, receveur_id, marchandise_id, date_disponible, date_reservation, date_transaction)
									VALUES(:donneur_id, :receveur_id, :marchandise_id, :date_disponible,  NOW(), NOW();

/* Pour la table :  transaction via -PHP-                                                          */
/* Annulation d’une réservation, fichier reservation.php, fonction: annulerreservation($marchandise_id)	
   REMPLACER chaque variable (:nom_variable) par des valeurs valides dans la seconde ligne VALUES(). 
   L'item choisi prendra aura une nouvelle date et heure de disponibilité.                         */

INSERT INTO transaction (donneur_id, marchandise_id, date_disponible, date_transaction)
											VALUES(:donneur_id, :marchandise_id, NOW(), NOW());

/* Pour la table :  utilisateur                                                                    */
INSERT INTO `utilisateur` VALUES (5,"test","Calille","Sylvie","test@test.com","8195378851",1,4,"2016-01-13 20:37:10");
INSERT INTO `utilisateur` VALUES (7,"mot_de_passe","Béchard","Fabien","info@tableepopulaire.ca","8195374884",0,1,"2016-01-13 11:18:09");
INSERT INTO `utilisateur` VALUES (8,"mot_de_passe","NADEAU","PHILIPPE","info@tableepopulaire.ca","8195373477",0,2,"2016-01-13 14:28:40");
INSERT INTO `utilisateur` VALUES (9,"mot_de_passe","Leloup","Sam","info@tableepopulaire.ca","8195379813",2,3,"2016-01-13 14:30:50");
INSERT INTO `utilisateur` VALUES (10,"mot_de_passe","Baril","Séléna","NULL","8195364141",1,5,"2016-01-16 16:18:00");
INSERT INTO `utilisateur` VALUES (11,"mot_de_passe","Dussault","Alain","NULL","8195397498",1,6,"2016-01-16 16:34:00");
INSERT INTO `utilisateur` VALUES (12,"mot_de_passe","Gauthier","Claud","NULL","8195334553",1,7,"2016-01-16 20:27:00");
INSERT INTO `utilisateur` VALUES (13,"mot_de_passe","Ayotte","Philippe","NULL","8195373724",1,8,"2016-01-16 20:34:00");


/*-------------------------------------------------------------------------------------------------*/
/* 2- Les mises à jour (UPDATE)                                                                    */
/*-------------------------------------------------------------------------------------------------*/

/* Pour la table :  adresse via -PHP-                                                              */
/* Pour la fonction : modification de profil à compléter
   REMPLACER les variables PHP (:nom_variable) par des valeurs valides.                            */

UPDATE adresse SET 	no_civique = :no_civique,
					type_rue = :type_rue,
					nom = :nom,
					app = :app,
					ville = :ville,
					province = :province,
					code_postal = :code_postal,
					pays = :pays
						
WHERE adresse_id = :adresse_id; 

/* Pour la table :  alimentaire via -PHP-                                                          */
/* Ajout d’une réservation, fichier reservation.php, fonction: reservationajout()	
   Pour que la marchandise passe à un état de disponible à réservé, une mise à jour sur la table
   alimentaire a lieu.
   REMPLACER: :marchandise_statut par 2 (réservé) et choisir un :id valide de la table alimentaire */

UPDATE alimentaire SET marchandise_statut = :marchandise_statut WHERE alimentaire_id = :id;

/* Pour la table :  alimentaire via -PHP-                                                          */
/* Modification marchandise, fichier alimentaire.php, fonction: modifieralimentaire()
   REMPLACER les variables PHP (:nom_variable) par des valeurs valides.
   Chaque valeur correspondant au champ de la table                                                */

UPDATE alimentaire SET 	nom = :nom,
						description_alimentaire = :description_alimentaire,
						quantite = :quantite,
						marchandise_etat = :marchandise_etat,
						marchandise_unite = :marchandise_unite,
						valeur = :valeur,
						type_alimentaire = :type_alimentaire,
						date_peremption = :date_peremption
						
						WHERE alimentaire_id = :aliment_id;

/* Pour la table :  alimentaire via -PHP-                                                          */
/* Suppression marchandise, fichier alimentaire.php, fonction: cancelleraliment($id_alimentaire)
   REMPLACER :id_alimentaire par un ID valide.                                                     */

UPDATE alimentaire SET marchandise_statut = 7 WHERE alimentaire_id = :id_alimentaire;

/* Pour la table :  alimentaire via -PHP-                                                          */
/* Collecte marchandise, fichier alimentaire.php, fonction: collecteralimentaire($id_alimentaire)
   REMPLACER :id_alimentaire par un ID valide.                                                     */
   
UPDATE alimentaire SET marchandise_statut = 4 WHERE alimentaire_id = :id_alimentaire;

/* Pour la table :  organisme via -PHP-                                                            */
/* Pour la fonction : modification de profil à compléter
   REMPLACER les variables PHP (:nom_variable) par des valeurs valides.                            */
UPDATE organisme SET 	nom = :nom,
						telephone = :telephone,
						poste = :telephone,
						no_entreprise = :no_entreprise,
						no_osbl = :no_osbl
						
WHERE organisme_id = :organisme_id;

/* Pour la table :  transaction via -PHP-                                                          */
/* Collecte transaction
   REMPLACER : transaction_id par le numéro de transaction que l'on veut modifier 
   le champ date_collecte                                                                          */

UPDATE transaction SET 	date_collecte =  NOW()
WHERE transaction_id = :transaction_id;

/* Pour la table :  utilisateur via -PHP-                                                          */
/* Modification profil à créer, fichier utilisateur.php, fonction: modifierutilisateur()
   REMPLACER les variables PHP par des valeurs valides. 
   Il manque le champ 'poste' pour rejoindre l'usager. Quelque chose qui aura lieu d'ajouter.      */

UPDATE utilisateur SET 	mot_de_passe = :mot_de_passe, 
						nom = :nom,
						prenom = :prenom,
						courriel = :courriel, 
						telephone = :telephone, 
						moyen_contact = :moyen_contact					
WHERE utilisateur_id = :utilisateur_id;

						
/*-------------------------------------------------------------------------------------------------*/
/* 3- Les altérations (ALTER)                                                                      */
/*-------------------------------------------------------------------------------------------------*/

/* Pour la table :  adresse, modification du nombre de caractères du champ                         */
ALTER TABLE `yolaine_hipdev`.`adresse` CHANGE COLUMN `code_postal` `code_postal` VARCHAR(6) NULL DEFAULT NULL COMMENT 'Code postal.' ;

/* Pour la table :  alimentaire, modification du champ de timestamp à datetime                     */
ALTER TABLE `yolaine_hipdev`.`alimentaire` CHANGE COLUMN `date_peremption` `date_peremption` DATETIME NULL DEFAULT NULL COMMENT 'Date(DATETIME) pour la durée de conservation de l''aliment.' ;

/* Pour la table :  organisme, modification du nombre de caractères du champ                       */
ALTER TABLE `yolaine_hipdev`.`organisme` CHANGE COLUMN `nom` `nom` varchar(255) NOT NULL COMMENT 'Représente le nom de l''organisme.';

/* Pour la table :  transaction, modification de quatre champs de timestamp à datetime             */
ALTER TABLE `yolaine_hipdev`.`transaction` 
CHANGE COLUMN `date_collecte` `date_collecte` DATETIME NULL DEFAULT NULL COMMENT 'Date (DATETIME) de la collecte par l''oranisme receveur.' ,
CHANGE COLUMN `date_reservation` `date_reservation` DATETIME NULL DEFAULT NULL COMMENT 'Date (DATETIME) de la réservation faite par l''organisme receveur.' ,
CHANGE COLUMN `date_disponible` `date_disponible` DATETIME NULL DEFAULT NULL COMMENT 'Date (DATETIME) où la marchandise est disponible.' ,
CHANGE COLUMN `date_transaction` `date_transaction` DATETIME NOT NULL COMMENT 'Date (DATETIME) de la transaction.';

/* Pour la table :  type_aliment, ajout d'un champ `perissable`                                    */
ALTER TABLE `type_aliment` ADD COLUMN `perissable` integer NOT NULL AFTER `description_type_aliment`;
ALTER TABLE `type_aliment` CHANGE `perissable` `perissable` INT( 1 ) COMMENT 'La ressource est périssable = 1, non-périssable = 0';  

/* Pour la table :  utilisateur, ajout d'un commentaire                                            */
ALTER TABLE `yolaine_hipdev`.`utilisateur` CHANGE COLUMN `moyen_contact` `moyen_contact` INT(1) NOT NULL COMMENT 'Moyen de rejoindre l''utilisateur ; téléphone seulement = 0, courriel seulement = 1, les deux = 2.' ;

/* Pour la table :  utilisateur, modification du nombre de caractères du champ                     */
ALTER TABLE `yolaine_hipdev`.`utilisateur` CHANGE COLUMN `telephone` `telephone` varchar(10) DEFAULT NULL COMMENT 'Représente le numéro de téléphone de l''utilisateur.';

/* Pour la table :  utilisateur, modification du champ de timestamp à datetime                     */
ALTER TABLE `utilisateur` CHANGE `derniere_connexion` `derniere_connexion` datetime NOT NULL COMMENT 'Date et heure(DATETIME) lors de la dernière connexion.';


/*-------------------------------------------------------------------------------------------------*/
/* 4- La création de la table: donneur_mois                                                        */
/*    Afin de garder les statistiques du donneur du mois                                           */
/*-------------------------------------------------------------------------------------------------*/

CREATE TABLE `donneur_mois` (
  `organisme_id` int(10) NOT NULL COMMENT 'Numéro unique pour l''organisme.',
  `montant_total` int(10) NOT NULL COMMENT 'Représente la somme totale offerte par l''organisme du donneur du mois pour un mois précis.',
  `date` datetime NOT NULL COMMENT 'Représente date (année, mois) pour le donneur du mois qui a fait son exploit.',
  PRIMARY KEY (`date`),
  UNIQUE KEY `pk_donneur_mois` (`date`),
  KEY `fk_organisme$donneur_mois` (`organisme_id`),
  CONSTRAINT `fk_organisme$donneur_mois` FOREIGN KEY (`organisme_id`) REFERENCES `organisme` (`organisme_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Table permettant d''enregistrer le meilleur donneur pour un mois et une année en particulier.';


/*-------------------------------------------------------------------------------------------------*/
/* 5- Les sélections de données (SELECT)                                                           */
/*    À noter que les SELECT montrés ici diffèrent légèrement de ceux dans le code PHP par :       */
/*     - l'ajout de point-virgule (non nécessaire dans le code PHP pour l'exécution)               */
/*     - l'alignement des champs et des retours de ligne                                           */
/*-------------------------------------------------------------------------------------------------*/

/* Requête : Liste des organismes qui ont des marchandises à donner                                */
/* Fichier: carte.php, fonction : entreprisedon()                                                  */

SELECT DISTINCT org.organisme_id, 
				org.nom,
				adr.no_civique,
				adr.nom,
				typrue.description_type_rue,
				adr.ville,
				adr.province,
				adr.code_postal,
				adr.pays,
				adr.adresse_id,
				org.telephone,
				org.poste		
FROM alimentaire ali
			
INNER JOIN marchandise_statut marstat ON marstat.statut_id = ali.marchandise_statut
INNER JOIN transaction trx ON trx.marchandise_id = ali.alimentaire_id 
INNER JOIN organisme org ON org.organisme_id = trx.donneur_id
INNER JOIN adresse adr ON adr.adresse_id = org.adresse
INNER JOIN type_rue typrue ON adr.type_rue = typrue.type_rue_id

WHERE ali.marchandise_statut = 3 
AND (ali.date_peremption > current_date OR ali.date_peremption IS NULL);

/* Requête : Liste des marchandises disponibles par entreprise_id                                  */
/* Fichier: carte.php, fonction : entreprisedon()                                                  
   REMPLACER la variable :id_donneur par une valeur valide.                                        */

SELECT DISTINCT ali.nom,
				ali.alimentaire_id,
				ali.description_alimentaire, 
				ali.quantite, 
				marunit.description_marchandise_unite,
				ali.date_peremption,
				ali.valeur

FROM transaction trx

INNER JOIN organisme org ON org.organisme_id = trx.donneur_id
INNER JOIN alimentaire ali ON ali.alimentaire_id = trx.marchandise_id
INNER JOIN type_aliment typali ON typali.aliment_id = ali.type_alimentaire
INNER JOIN marchandise_unite marunit ON marunit.unite_id = ali.marchandise_unite

WHERE (ali.marchandise_statut = 3 )
AND trx.donneur_id = :id_donneur 
AND (ali.date_peremption > current_date OR ali.date_peremption IS NULL) 
ORDER BY typali.aliment_id DESC;

/* Requête : Liste des marchandises disponibles                                                    */
/* Fichier: don.php, fonction : listedondispo()                                                    */
   
 SELECT 
		typali.description_type_aliment,
		ali.alimentaire_id,
		ali.nom,
		ali.description_alimentaire,
		ali.quantite,
		marunit.description_marchandise_unite,
		ali.date_peremption,
		marstat.description_marchandise_statut,
		org.organisme_id,			
		org.nom,
		org.telephone,
		org.poste,
		adr.adresse_id,
		adr.no_civique,
		typrue.description_type_rue,
		adr.app,	
		adr.nom,
		adr.ville,
		adr.province,
		adr.code_postal,
		adr.pays,
		util.nom,
		util.prenom,
		util.courriel,
		util.telephone,
		MAX(trx.date_disponible) as date_disponible
		
FROM type_aliment typali
INNER JOIN alimentaire ali ON ali.type_alimentaire = typali.aliment_id
INNER JOIN marchandise_unite marunit ON marunit.unite_id = ali.marchandise_unite
INNER JOIN transaction trx ON trx.marchandise_id = ali.alimentaire_id
INNER JOIN organisme org ON org.organisme_id = trx.donneur_id
INNER JOIN adresse adr ON adr.adresse_id = org.adresse
INNER JOIN type_rue typrue ON typrue.type_rue_id = adr.type_rue
INNER JOIN utilisateur util ON util.utilisateur_id = org.utilisateur_contact
INNER JOIN marchandise_statut marstat ON marstat.statut_id = ali.marchandise_statut 		
WHERE ali.marchandise_statut = 3
AND  trx.marchandise_id in (SELECT DISTINCT marchandise_id FROM transaction)
AND   trx.date_reservation IS NULL  
GROUP BY typali.description_type_aliment,
	ali.nom,
	ali.description_alimentaire,
	ali.quantite,
	marunit.description_marchandise_unite,
	ali.date_peremption,
	org.nom,
	adr.adresse_id,
	adr.no_civique,
	typrue.description_type_rue,
	adr.nom,
	adr.ville,
	adr.province,
	adr.code_postal,
	adr.pays,
	org.telephone,
	org.poste,
	util.prenom,
	util.nom,
	util.courriel,
	ali.alimentaire_id,
	marstat.description_marchandise_statut,
	org.organisme_id,
	adr.app; 

/* Requête : Liste des organismes communautaires                                                   */
/* Fichier: organisme.php, fonction : listeorganisme()                                             */	
	
SELECT  org.nom, 
		adr.no_civique, 
		typrue.description_type_rue, 
		adr.nom, 
		adr.ville, 
		adr.province, 
		adr.code_postal, 
		adr.pays,
		util.prenom,
		util.nom,
		util.courriel,
		util.telephone,
		org.telephone,
		org.poste
	  	  
FROM organisme org
INNER JOIN adresse adr ON adr.adresse_id = org.adresse
INNER JOIN type_rue typrue ON typrue.type_rue_id = adr.type_rue
INNER JOIN utilisateur util ON util.utilisateur_id = org.utilisateur_contact
WHERE org.no_osbl IS NOT NULL;	

/* Requête : Liste des marchandises réservées                                                      */
/* Fichier: reservation.php, fonction : listereservation($id_organisme)                                             
   REMPLACER la variable :receveur_id par une valeur valide.                                       */

SELECT ali.alimentaire_id,
		typali.description_type_aliment,
		ali.nom,
		ali.description_alimentaire,
		ali.quantite,
		marunit.description_marchandise_unite,
		ali.date_peremption,
		org.nom,
		adr.adresse_id,
		adr.no_civique,
		typrue.description_type_rue,
		adr.nom,
		adr.ville,
		adr.province,
		adr.code_postal,
		adr.pays,
		adr.app,
		org.telephone,
		org.poste,
		util.prenom,
		util.nom,
		util.courriel,
		util.telephone
	   
FROM type_aliment typali
INNER JOIN alimentaire ali ON ali.type_alimentaire = typali.aliment_id
INNER JOIN marchandise_unite marunit ON marunit.unite_id = ali.marchandise_unite
INNER JOIN transaction trx ON trx.marchandise_id = ali.alimentaire_id
INNER JOIN organisme org ON org.organisme_id = trx.donneur_id
INNER JOIN adresse adr ON adr.adresse_id = org.adresse
INNER JOIN type_rue typrue ON typrue.type_rue_id = adr.type_rue
INNER JOIN utilisateur util ON util.utilisateur_id = org.utilisateur_contact
WHERE ali.marchandise_statut = 2
AND trx.receveur_id = :receveur_id 
AND (trx.date_reservation, trx.marchandise_id) in
									(SELECT MAX(trx.date_reservation) as date_réservation,
											trx.marchandise_id  
									 FROM transaction trx
									 WHERE trx.marchandise_id in (SELECT DISTINCT marchandise_id FROM transaction)
									 AND trx.date_reservation IS NOT NULL  
									 GROUP BY trx.marchandise_id)                                     
ORDER BY typali.description_type_aliment, ali.nom, ali.description_alimentaire, ali.quantite;

/* Requête : Liste des informations pour le profil                                                 */
/* Fichier: connection.php, fonction : connect()                                             
   REMPLACER les variables: :courriel et :mdp (mot de passe) par une valeur valide pour chaque.    */
 
SELECT 	adr.adresse_id, 
		adr.no_civique, 
		typrue.description_type_rue,
		adr.type_rue,
		adr.nom,
		adr.app,
		adr.ville,
		adr.province,
		adr.code_postal,
		adr.pays,
		util.utilisateur_id,
		util.mot_de_passe,
		util.nom,
		util.prenom,
		util.courriel,
		util.telephone,
		util.moyen_contact,
		util.organisme_id,
		util.derniere_connexion,
		org.organisme_id,
		org.nom,
		org.adresse,
		org.telephone,
		org.poste,
		org.utilisateur_contact,
		org.no_entreprise,
		org.no_osbl
FROM organisme org
INNER JOIN utilisateur util ON util.organisme_id = org.organisme_id
INNER JOIN adresse adr ON adr.adresse_id = org.adresse
INNER JOIN type_rue typrue ON typrue.type_rue_id = adr.type_rue
WHERE courriel = :courriel 
AND mot_de_passe = :mdp; 

/* Requête : Liste des marchandises données et reçues par un même organisme (statistiques)         */
/* Fichier: transaction.php, fonction : transactions($id)                                             
   REMPLACER les variables: :id (organisme), :date_debut, :date_fin                                */
   
SELECT orgd.organisme_id,
	   orgd.nom,
	   orgd.telephone,
	   orgd.poste,
	   orgd.no_entreprise,
	   orgd.no_osbl,
	   orgr.organisme_id,
	   orgr.nom,
	   orgr.telephone,
	   orgr.poste,
	   orgr.no_entreprise,
	   orgr.no_osbl,
	   trx.transaction_id,
	   trx.receveur_id,
	   trx.donneur_id,
	   trx.marchandise_id,
	   trx.date_collecte,
	   trx.date_reservation,
	   trx.date_disponible,
	   trx.date_transaction,
	   ali.valeur,
	   typali.description_type_aliment,
	   adrd.adresse_id,
	   adrd.no_civique,
	   typrued.description_type_rue,
	   adrd.nom,
	   adrd.app,
	   adrd.ville,
	   adrd.province,
	   adrd.code_postal,
	   adrd.pays,
	   adrr.adresse_id,
	   adrr.no_civique,
	   typruer.description_type_rue,               
	   adrr.nom,
	   adrr.app,
	   adrr.ville,
	   adrr.province,
	   adrr.code_postal,
	   adrr.pays,
	   ali.nom,
	   ali.description_alimentaire,
	   ali.quantite,
	   maru.description_marchandise_unite
		 
FROM transaction trx
INNER JOIN organisme orgd ON orgr.organisme_id = trx.receveur_id 
INNER JOIN organisme orgr ON orgd.organisme_id = trx.donneur_id
INNER JOIN alimentaire ali ON ali.alimentaire_id = trx.marchandise_id
INNER JOIN type_aliment typali ON typali.aliment_id = ali.type_alimentaire
INNER JOIN adresse adrr ON adrr.adresse_id = orgr.adresse
INNER JOIN adresse adrd ON adrd.adresse_id = orgd.adresse
INNER JOIN type_rue typruer ON typruer.type_rue_id = adrr.type_rue
INNER JOIN type_rue typrued ON typrued.type_rue_id = adrd.type_rue
INNER JOIN marchandise_unite maru ON maru.unite_id = ali.marchandise_unite

WHERE trx.date_collecte IS NOT NULL
AND  trx.receveur_id = :id 
OR trx.donneur_id = :id
AND  trx.date_collecte BETWEEN :date_debut AND :date_fin;   

/* Requête : Identifier le donneur du mois (statistiques)                                          */
/* Fichier: donneur_mois.php, fonction :                                                           */

SELECT	don.organisme_id,
		org.nom,
		don.montant_total,
		don.date_donneur_mois
		
FROM	donneur_mois don
INNER JOIN organisme org ON org.organisme_id = don.organisme_id
WHERE EXTRACT(YEAR_MONTH FROM date_donneur_mois) BETWEEN (EXTRACT(YEAR_MONTH FROM '2015-12-14')) AND (EXTRACT(YEAR_MONTH FROM '2016-02-01'));