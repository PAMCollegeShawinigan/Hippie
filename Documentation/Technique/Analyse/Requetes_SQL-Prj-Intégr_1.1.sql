/*	
	Par J.F.Noël et PA
	À noter que dans les fichiers .php les requêtes SQL peuvent avoir ou non le point-virgule.
	Pour fin de lisibilité, chaque champ
*/

-- Tester les requêtes pour les marchandises disponibles sur la carte
-- Fichier: carte.php, fonction : entreprisedon() 
-- D'abord l'affichage des entreprises qui ont des marchandises disponibles avec leurs coordonnées
-- Testé le 19 janvier 2016 (avec des commentaires pour PA)
-- Corrections à faire:
-- Dans le WHERE remplacer le ANd par AND
-- Mettre le ANd et le reste de la commande sur une autre ligne
-- Mettre en majuscule CURRENT_DATE
-- Le champ adr.adresse_id dans la commande du SELECT (pas nécessaire, et nous le gardons pour avoir l'objet complet)
SELECT DISTINCT org.organisme_id, 
				org.nom, 
				adr.no_civique, 
				adr.nom , 
				typrue.description_type_rue , 
				adr.ville , adr.province, 
				adr.code_postal, adr.pays, adr.adresse_id 

FROM alimentaire ali
INNER JOIN marchandise_statut marstat ON marstat.statut_id = ali.marchandise_statut
INNER JOIN transaction trx ON trx.marchandise_id = ali.alimentaire_id 
INNER JOIN organisme org ON org.organisme_id = trx.donneur_id
INNER JOIN adresse adr ON adr.adresse_id = org.adresse
INNER JOIN type_rue typrue ON adr.type_rue = typrue.type_rue_id 
WHERE ali.marchandise_statut = 3 ANd ali.date_peremption > current_date;

-- Fonction : donid, REMPLACER :id_donneur mettre une valeur ex: 4
-- Affiche les informations marchandises disponibles pour une entreprise en particulier
-- Dans le SELECT pas nécessaire (les garder pour les objets) : ali.alimentaire_id, ali.valeur
-- Mettre en majuscule : CURRENT_DATE
-- Ajouter pour l'ORDER BY l'alias du champ : ORDER BY typali.aliment_id DESC
-- À enlever (non-nécessaire) - le résultat reste le même
-- INNER JOIN marchandise_statut marstat ON marstat.statut_id = ali.marchandise_statut
-- INNER JOIN marchandise_etat maretat ON maretat.etat_id = ali.marchandise_etat
-- 
-- Temps pris 1.90 ms, 15 record(s)

-- Requête avant
SELECT ali.nom, ali.alimentaire_id,
		ali.description_alimentaire, ali.quantite, marunit.description_marchandise_unite,ali.date_peremption, ali.valeur
		
		FROM transaction trx
		
		INNER JOIN organisme org ON org.organisme_id = trx.donneur_id
		INNER JOIN alimentaire ali ON ali.alimentaire_id = trx.marchandise_id
		INNER JOIN type_aliment typali ON typali.aliment_id = ali.type_alimentaire
		INNER JOIN marchandise_statut marstat ON marstat.statut_id = ali.marchandise_statut
		INNER JOIN marchandise_etat maretat ON maretat.etat_id = ali.marchandise_etat
		INNER JOIN marchandise_unite marunit ON marunit.unite_id = ali.marchandise_unite

		WHERE (ali.marchandise_statut = 3 OR ali.marchandise_statut = 2 )AND trx.donneur_id = :id_donneur AND (ali.date_peremption > current_date OR ali.date_peremption IS NULL) ORDER BY aliment_id DESC;

-- Requête après, pour tester REMPLACER :id_donneur par 4 (ajout ; à la fin)
SELECT 	ali.nom
		,ali.description_alimentaire
		, ali.quantite
		, marunit.description_marchandise_unite
		,ali.date_peremption
FROM transaction trx
INNER JOIN organisme org ON org.organisme_id = trx.donneur_id
INNER JOIN alimentaire ali ON ali.alimentaire_id = trx.marchandise_id
INNER JOIN type_aliment typali ON typali.aliment_id = ali.type_alimentaire
INNER JOIN marchandise_unite marunit ON marunit.unite_id = ali.marchandise_unite
WHERE (ali.marchandise_statut = 3 OR ali.marchandise_statut = 2 )
AND trx.donneur_id = :id_donneur
AND (ali.date_peremption > CURRENT_DATE OR ali.date_peremption IS NULL) 
ORDER BY typali.aliment_id DESC;
		
-- Requête Marchandises_disponibles liste dans des dons (avant que Catherine fasse une carte Trello)
-- Fonction : listedondispo, fichier : don.php
-- À ajouter : l'alias du champ dans ORDER BY :  ORDER BY ali.alimentaire_id DESC
-- À enlever : 	INNER JOIN type_aliment typali ON typali.aliment_id = ali.type_alimentaire 
--				INNER JOIN marchandise_etat maretat ON maretat.etat_id = ali.marchandise_etat
SELECT ali.nom, 
	ali.alimentaire_id,
	ali.description_alimentaire, 
	ali.quantite, 
	marunit.description_marchandise_unite, 
	ali.date_peremption,
	ali.valeur, 
	marstat.description_marchandise_statut, 
	org.organisme_id, 
	org.nom, 
	org.telephone, 
	org.poste, 
	adr.adresse_id, 
	adr.no_civique, 
	typrue.description_type_rue, 
	adr.nom, 
	adr.ville, 
	adr.province, 
	adr.code_postal, 
	adr.pays
  
  FROM transaction trx
  
  INNER JOIN organisme org ON org.organisme_id = trx.donneur_id
  INNER JOIN adresse adr ON adr.adresse_id = org.adresse
  INNER JOIN type_rue typrue ON typrue.type_rue_id = adr.type_rue
  INNER JOIN alimentaire ali ON ali.alimentaire_id = trx.marchandise_id
  INNER JOIN type_aliment typali ON typali.aliment_id = ali.type_alimentaire
  INNER JOIN marchandise_statut marstat ON marstat.statut_id = ali.marchandise_statut
  INNER JOIN marchandise_etat maretat ON maretat.etat_id = ali.marchandise_etat
  INNER JOIN marchandise_unite marunit ON marunit.unite_id = ali.marchandise_unite 
  
  WHERE ali.marchandise_statut = 3 
  ORDER BY alimentaire_id DESC;
  
-- Liste des marchandises disponibles par Catherine
/*	
    Type Catégorie
    Nom du produit
    Description du produit
    Quantité
    Unité
    Date de péremption
    Nom de l'entreprise donneur
    Adresse de l'entreprise donneur
    Numéro de téléphone de l'entreprise donneur et le numéro de poste
    Prénom et Nom de la personne contact de l'entreprise donneur (s'il y a)
    Courriel de l'entreprise donneur (s'il y a)
*/  


-- Changement dans la clause WHERE,2016-02-01
-- Enlever du WHERE : AND (ali.date_peremption > CURRENT_DATE OR ali.date_peremption IS NULL)
-- Pour avoir les marchandises périmées, car elles peuvent être données et encore bonnes.
-- L'ordre dans le GROUP BY est le même que dans l'énoncé de Catherine plus haut.

SELECT MAX(trx.date_disponible) as date_disponible,
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
    org.telephone,
    org.poste,
    util.prenom,
    util.nom,
    util.courriel

FROM type_aliment typali
INNER JOIN alimentaire ali ON ali.type_alimentaire = typali.aliment_id
INNER JOIN marchandise_unite marunit ON marunit.unite_id = ali.marchandise_unite
INNER JOIN transaction trx ON trx.marchandise_id = ali.alimentaire_id
INNER JOIN organisme org ON org.organisme_id = trx.donneur_id
INNER JOIN adresse adr ON adr.adresse_id = org.adresse
INNER JOIN type_rue typrue ON typrue.type_rue_id = adr.type_rue
INNER JOIN utilisateur util ON util.utilisateur_id = org.utilisateur_contact  
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
    util.courriel; 

-- Fonction Liste des oranismes communautaires
/*
- Nom du receveur (organisme qui reçoit)
- Adresse du receveur
- Nom de la personne contact (prénom, nom)
- Numéro de téléphone du receveur
- Courriel de l'organisme communautaire
*/
-- PA vérifie si les champs que je mets conviennent à l'idée d'objet que tu souhaites utilisé.
-- Avec les ajouts de PA (2016-01-25)
SELECT	org.nom, 
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

-- Fonctionnaliés - requêtes
-- Ajout marchandise (déjà créée par PA), fichier alimentaire.php, fonction: ajoutalimentaire()
-- Chaque valeur correspondant au champ de la table a besoin d'être mis dans les parentèses (VALUES) avec les apostrophes
-- si c'est un champ d'un ou plusieurs caractères ou de date
-- Requête qui fonctionne bien, rien à redire.

INSERT INTO alimentaire (nom, description_alimentaire, quantite, marchandise_etat, marchandise_unite, valeur, marchandise_statut, type_alimentaire, date_peremption) 
												VALUES(:nom, :description_alimentaire, :quantite, :marchandise_etat, :marchandise_unite, :valeur, :marchandise_statut, :type_alimentaire, :date_peremption);
 
 -- La fonction ajout marchandise implique l'ajout d'une transaction, fichier alimentaire.php, fonction: ajoutalimentaire()
 -- REMPLACER les champs : donneur_id, :marchandise_id par des valeurs.
 -- Requête qui fonctionne bien, rien à redire.-- 
 
INSERT INTO transaction (donneur_id, marchandise_id, date_disponible, date_transaction)
			VALUES(:donneur_id, :marchandise_id,  NOW(), NOW());
 
-- Fonctionnaliés - requêtes
-- Modification marchandise (déjà créée par PA), fichier alimentaire.php, fonction: modifieralimentaire()
-- REMPLACER les variables PHP par des valeurs valides.-
-- Chaque valeur correspondant au champ de la table a besoin d'être mis avec les apostrophes
-- si c'est un champ d'un ou plusieurs caractères ou de date
-- La requête ne fonctionne pas bien (manque une virgule après :valeur, et le nom de la colonne description serait 
-- à remplacer par description_alimentaire
-- alignement du WHERE vis à vis le UPDATE.

UPDATE alimentaire SET nom = :nom, description = :description_alimentaire, quantite = :quantite,
								marchandise_etat = :marchandise_etat, marchandise_unite = :marchandise_unite, valeur = :valeur
								type_alimentaire = :type_alimentaire, date_peremption = :date_peremption
								WHERE alimentaire_id = :alimentaire_id;

-- Fonctionnaliés - requêtes
-- Suppression marchandise (déjà créée par PA), fichier alimentaire.php, fonction: cancelleraliment($id_alimentaire)
-- REMPLACER :id_alimentaire par un ID valide.
-- Requête parfaite.

UPDATE alimentaire SET marchandise_statut = 7 WHERE alimentaire_id = :id_alimentaire;

-- Fonctionnaliés - requêtes
-- Collecte marchandise (déjà créée par PA), fichier alimentaire.php, fonction: collecteralimentaire($id_alimentaire)
-- REMPLACER :id_alimentaire par un ID valide et la marchandise_statut = 4 (collecté). J'aurais cru que la valeur
-- soit mise comme dans le cas précédent. Elle est gérée par le code PHP par la suite.
-- Requête fonctionne avec les bonnes valeurs.

UPDATE alimentaire SET marchandise_statut = :marchandise_statut WHERE alimentaire_id = :id_alimentaire;	

-- Fonctionnaliés - requêtes
-- Collecte transaction
-- REMPLACER : transaction_id par le numéro de transaction que l'on veut modifier le champ date_collecte
-- Nouvelle requête pour PA
-- À ajouter sans doute dans le fichier alimentaire.php où il y a déjà le marchandise_statut qui change avec la collecte.

UPDATE transaction SET 	date_collecte =  NOW()
WHERE transaction_id = :transaction_id;

-- Fonctionnaliés - requêtes
-- Ajout reservation (déjà créée par PA), fichier reservation.php, fonction: reservationajout()
-- REMPLACER les valeurs : :donneur_id, :receveur_id, :marchandise_id, :date_disponible
-- Requête fonctionne seulement j'ai un avertissement, date_transaction :  Field 'date_transaction' doesn't have a default value
-- Lors de l'insertion la valeur est : 0000-00-00 00:00:00. Je ne sais pas si le PHP ajoute la valeur courante?
-- Une façon de régler l'avertissement c'est d'ajouter à l'insertion le champ : date_transaction et la valeur CURRENT_TIMESTAMP

INSERT INTO transaction (donneur_id, receveur_id, marchandise_id, date_disponible, date_reservation)
									VALUES(:donneur_id, :receveur_id, :marchandise_id, :date_disponible,  CURRENT_TIMESTAMP);
	
-- Fonctionnaliés - requêtes
-- Ajout reservation (déjà créée par PA), fichier reservation.php, fonction: reservationajout()	
-- Suite pour que la marchandise passe à un état de disponible à réservé, une mise à jour sur la table
-- alimentaire a lieu.
-- REMPLACER: :marchandise_statut par 2 (réserve) et choisir un :id valide de la table alimenaire
-- Déplacer le WHERE sur une autre ligne
-- Bonne requête.

UPDATE alimentaire SET marchandise_statut = :marchandise_statut WHERE alimentaire_id = :id;

-- Fonctionnaliés - requêtes
-- Annulation reservation (déjà créée par PA), fichier reservation.php, fonction: annulerreservation($marchandise_id)	
-- REMPLACER chaque variable par des valeurs valides dans la seconde ligne VALUES(). Requête similaire à l'ajout,
-- même commentaire. L'item choisi prendra aura une nouvelle date et heure de disponibilité.

INSERT INTO transaction (donneur_id, receveur_id, marchandise_id, date_disponible, date_reservation)
											VALUES(:donneur_id, :receveur_id, :marchandise_id, :date_disponible, :date_reservation);

-- Du même coup, la marchandise sera à nouveau disponible.
-- REMPLACER: :marchandise_statut par 3 (disponible) et choisir un :id valide de la table alimenaire
-- Même requête que plus haut

UPDATE alimentaire SET marchandise_statut = :marchandise_statut WHERE alimentaire_id = :id;

-- Liste des marchandises réservées, ou liste de réservations (Catherine)
-- 
/*  Liste à Catherine - Carte Trello
    Type alimentaire
    Nom
    Description
    Quantitée
    Unité
    Date de réservation
    Date de péremption
    Nom du donneur (entreprise qui donne)
    Adresse du donneur
    Numéro de téléphone du donneur (s'il y a)
    Courriel du donneur (s'il y a)
*/
-- Avec les ajouts de PA (2016-01-25)
-- J'ai ajouté (2016-01-30) dans la condition du WHERE : AND date_reservation IS NOT NULL
-- Une autre condition a besoin d'être ajoutée qui réfère marchandise_id et date_transaction
-- Pour enlever la possibilité d'avoir la même marchandise_id pour 2 usagers différents, une sous-requête
-- est nécessaire.

SELECT typali.description_type_aliment,
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
    util.courriel
   
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

-- Profil - requêtes - information utilisateur
-- Avant de faire la modification sur le profil, il faut envoyer toute l'information sur le profil de l'utilisateur
-- Pour le utilisateur_id, REMPLACER utilisateur_id par une valeur valide.

SELECT 	utilisateur_id,
		mot_de_passe,
		nom,
		prenom,
		courriel,
		telephone,
		moyen_contact,
		organisme_id,
		derniere_connexion
FROM utilisateur
WHERE utilisateur_id = :utilisateur_id;

-- Modification profil à créer, fichier utilisateur.php, fonction: modifierutilisateur()
-- REMPLACER les variables PHP par des valeurs valides.
-- Je n'ai pas mis organisme_id, derniere_connexion, utilisateur_id car l'usager n'a pas à le faire.
-- Il manque le champ 'poste' pour rejoindre l'usager. Quelque chose qui aura lieu d'ajouter une fois mis.

UPDATE utilisateur SET 	mot_de_passe = :mot_de_passe, 
						nom = :nom,
						prenom = :prenom,
						courriel = :courriel, 
						telephone = :telephone, 
						moyen_contact = :moyen_contact					
WHERE utilisateur_id = :utilisateur_id;


-- Profil - requêtes - information organisme
-- Avant de faire la modification sur le profil, il faut envoyer toute l'information sur le profil de l'organisme
-- Pour le organisme_id, REMPLACER organisme_id par une valeur valide.
-- Fonction existante par PA. Fichier : organisme.php, fonction: organismeid($id)
-- qui inclut trois requêtes.

SELECT * FROM organisme WHERE organisme_id = :id;
SELECT * FROM utilisateur WHERE utilisateur_id = :id;
SELECT * FROM adresse INNER JOIN type_rue on adresse.type_rue = type_rue.type_rue_id WHERE adresse_id = :id;

-- Une autre requête pourrait être proposée qui toucherait seulement l'organisme et l'adresse.

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
		org.organisme_id,
		org.nom,
		org.adresse,
		org.telephone,
		org.poste,
		org.utilisateur_contact,
		org.no_entreprise,
		org.no_osbl
FROM organisme org
INNER JOIN adresse adr ON adr.adresse_id = org.adresse
INNER JOIN type_rue typrue ON typrue.type_rue_id = adr.type_rue
WHERE org.organisme_id = :org.organisme_id;

-- REMPLACER :org.organisme_id par une valeur.

SELECT   adr.adresse_id,
    adr.no_civique,
    typrue.description_type_rue,
	adr.type_rue,
    adr.nom,
    adr.app,
    adr.ville,
    adr.province,
    adr.code_postal,
    adr.pays,
    org.organisme_id,
    org.nom,
	org.adresse,
    org.telephone,
    org.poste,
    org.utilisateur_contact,
    org.no_entreprise,
    org.no_osbl
FROM organisme org
INNER JOIN adresse adr ON adr.adresse_id = org.adresse
INNER JOIN type_rue typrue ON typrue.type_rue_id = adr.type_rue
WHERE org.organisme_id = 4;

-- Modification profil à créer, 
-- REMPLACER les variables PHP par des valeurs valides.

UPDATE adresse SET 	no_civique = :no_civique,
					type_rue = :type_rue,
					nom = :nom,
					app = :app,
					ville = :ville,
					province = :province,
					code_postal = :code_postal,
					pays = :pays
						
WHERE adresse_id = :adresse_id; 

UPDATE organisme SET 	nom = :nom,
						telephone = :telephone,
						poste = :telephone,
						no_entreprise = :no_entreprise,
						no_osbl = :no_osbl
						
WHERE organisme_id = :organisme_id;

-- Demande à PA
-- Avec sa correction sur la jointure touchant
-- INNER JOIN utilisateur util ON util.utilisateur_id = org.utilisateur_contact
-- remplacée par :
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

--					--
-- REQUÊTE DE STATS --
--					--

/* 	Requête permettant d'avoir les informations sur les statistiques Mes collectes et Mes dons
	pour un même ID, jouant le rôle de donneur et de receveur.
	REMPLACER :id, :date_debut, :date_fin 
*/	dans le second SELECT le ID joue le rôle de donneur)

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

	
--	Requêtes pour le donneur du mois
	
/*	Création d'une table qui va avoir les données du donneur du mois : donneur_mois.
	Une clé secondaire est ajoutée sur le champ organisme_id venant de la table organisme.
*/ 

CREATE TABLE `donneur_mois` (
  `organisme_id` int(10) NOT NULL COMMENT 'Numéro unique pour l''organisme.',
  `montant_total` int(10) NOT NULL COMMENT 'Représente la somme totale offerte par l''organisme du donneur du mois pour un mois précis.',
  `date` datetime NOT NULL COMMENT 'Représente date (année, mois) pour le donneur du mois qui a fait son exploit.',
  PRIMARY KEY (`date`),
  UNIQUE KEY `pk_donneur_mois` (`date`),
  KEY `fk_organisme$donneur_mois` (`organisme_id`),
  CONSTRAINT `fk_organisme$donneur_mois` FOREIGN KEY (`organisme_id`) REFERENCES `organisme` (`organisme_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Table permettant d''enregistrer le meilleur donneur pour un mois et une année en particulier.';

-- 4 insertions dans la table donneur_mois

INSERT INTO `yolaine_hipdev`.`donneur_mois`
(`organisme_id`,
`montant_total`,
`date`)
VALUES
(5,
3000,
'2015-10-25 23:00:00');

INSERT INTO `yolaine_hipdev`.`donneur_mois`
(`organisme_id`,
`montant_total`,
`date`)
VALUES
(6,
2500,
'2015-11-25 23:00:00');

INSERT INTO `yolaine_hipdev`.`donneur_mois`
(`organisme_id`,
`montant_total`,
`date`)
VALUES
(7,
2450,
'2015-12-25 23:00:00');

INSERT INTO `yolaine_hipdev`.`donneur_mois`
(`organisme_id`,
`montant_total`,
`date`)
VALUES
(8,
3560,
'2016-01-25 23:00:00');

/*	SELECT pour la route donneur_mois 
	Remplacer: :annee et :mois pour avoir le donneur du mois d'une année précise.
*/

SELECT	don.organisme_id,
		org.nom,
		don.montant_total,
		don.date
		
FROM	donneur_mois don
INNER JOIN organisme org ON org.organisme_id = don.organisme_id
WHERE	don.date BETWEEN :date_debut AND date_fin;


