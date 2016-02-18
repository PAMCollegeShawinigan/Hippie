/*-------------------------------------------------------------------------------------------------*/ 
/* Fichier créé par : Jean-François Noël                                                           */
/* Cours: Projet d'intégration                                                                     */
/* Titre du projet : denrée ô suivant                                                              */
/* Date de création : 2016-02-13                                                                   */
/*                                                                                                 */
/* Dans la section suivante vous aurez les différents SELECT utilisés                              */
/*                                                                                                 */
/*-------------------------------------------------------------------------------------------------*/


/*                                                                                                 
 * Requête : Liste des organismes qui ont des produits disponibles. 
 * Apparaît sur la carte dans mes denrées disponibles, et dans la liste des marchandises disponbiles                                
 */                                                                                                 

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

/*                                                                                                
 * Requête : Liste des marchandises disponibles pour id_donneur précis. Quand on clique sur un marqueur de la carte.
 * REMPLACER la variable :id_donneur                                                                        
 */

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

/*
 * Requête : Liste des marchandises disponibles à partir de Google Map, la 3e icône
 */
   
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

/*
 * Requête : Liste des organismes communautaires à partir du menu.                                               
 */	
	
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

/*
 * Requête : Liste des marchandises réservées: Mes réservations à partir du menu.
 * REMPLACER la variable :receveur_id 
 */  

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

/*
 * Requête : Liste des informations pour le profil de l'utilisateur                                                
 * REMPLACER les variables: :courriel et :mdp (mot de passe) 
 */
 
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

/*
 * Requête : Liste des marchandises données et reçues par un même organisme (statistiques)                                            
 * REMPLACER les variables: :id (organisme), :date_debut, :date_fin 
 */
   
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

/*
 * Requête : Identifier le donneur du mois (statistiques) 
 * REMPLACER les dates par date_debut '2015-12-14' et date_fin '2016-02-01' pour couvrir la période.
 */

SELECT	don.organisme_id,
		org.nom,
		don.montant_total,
		don.date_donneur_mois
		
FROM	donneur_mois don
INNER JOIN organisme org ON org.organisme_id = don.organisme_id
WHERE EXTRACT(YEAR_MONTH FROM date_donneur_mois) BETWEEN (EXTRACT(YEAR_MONTH FROM '2015-12-14')) AND (EXTRACT(YEAR_MONTH FROM '2016-02-01'));

/*
 * Requête : Marqueurs de mes réservations sur ma carte.
 * REMPLACER le chiffre : trx.receveur_id = 4, par le id de l'organisme choisi.
 */

SELECT
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
     
FROM transaction trx
INNER JOIN organisme org ON org.organisme_id = trx.donneur_id
INNER JOIN adresse adr ON adr.adresse_id = org.adresse
INNER JOIN type_rue typrue ON typrue.type_rue_id = adr.type_rue
INNER JOIN utilisateur util ON util.utilisateur_id = org.utilisateur_contact
WHERE trx.date_reservation IS NOT NULL
AND trx.date_collecte IS NULL
AND trx.receveur_id = 4
GROUP BY trx.receveur_id, trx.donneur_id;