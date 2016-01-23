-- Par J.F.No�l
-- � noter que dans les fichiers .php les requ�tes SQL n'ont pas de point-virgule.

-- Comme correction g�n�rale, mettre chaque champ de la commande SELECT sur une ligne diff�rente

-- Tester les requ�tes pour les marchandises disponibles
-- Test� le 19 janvier 2016 (avec des commentaires pour PA)
-- Corrections � faire:
-- Dans le WHERE remplacer le ANd par AND
-- Mettre le ANd et le reste de la commande sur une autre ligne
-- Mettre en majuscule CURRENT_DATE
-- Le champ adr.adresse_id dans la commande du SELECT (pas n�cessaire, et nous le gardons pour avoir l'objet complet)
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

-- Fonction : donid
-- Dans le SELECT pas n�cessaire (les garder pour les objets) : ali.alimentaire_id, ali.valeur
-- Mettre en majuscule : CURRENT_DATE
-- Ajouter pour l'ORDER BY l'alias du champ : ORDER BY typali.aliment_id DESC
-- � enlever (non-n�cessaire) - le r�sultat reste le m�me
-- INNER JOIN marchandise_statut marstat ON marstat.statut_id = ali.marchandise_statut
-- INNER JOIN marchandise_etat maretat ON maretat.etat_id = ali.marchandise_etat
-- changer pour moi :id_donneur mettre une valeur
-- Temps pris 1.90 ms, 15 record(s)

-- Requ�te avant
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

-- Requ�te apr�s, pour tester remplacer :id_donneur par 4 (ajout ; � la fin)
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
		
-- Requ�te Marchandises_disponibles liste dans des dons
-- Fonction : listedondispo, fichier : don.php
-- � ajouter : l'alias du champ dans ORDER BY :  ORDER BY ali.alimentaire_id DESC
-- � enlever : 	INNER JOIN type_aliment typali ON typali.aliment_id = ali.type_alimentaire 
--				INNER JOIN marchandise_etat maretat ON maretat.etat_id = ali.marchandise_etat
SELECT ali.nom, ali.alimentaire_id,
  ali.description_alimentaire, ali.quantite, marunit.description_marchandise_unite, ali.date_peremption,
  ali.valeur, marstat.description_marchandise_statut, org.organisme_id, org.nom, org.telephone, org.poste, 
  adr.adresse_id, adr.no_civique, typrue.description_type_rue, adr.nom, adr.ville, adr.province, adr.code_postal, adr.pays
  
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
  
-- Fonction Liste des oranismes communautaires
/*
- Nom du receveur (organisme qui re�oit)
- Adresse du receveur
- Nom de la personne contact (pr�nom, nom)
- Num�ro de t�l�phone du receveur
- Courriel de l'organisme communautaire
*/
-- PA v�rifie si les champs que je mets conviennent � l'id�e d'objet que tu souhaites utilis�.
SELECT 	org.nom, 
		adr.no_civique, 
		typrue.description_type_rue, 
		adr.nom, 
		adr.ville, 
		adr.province, 
		adr.code_postal, 
		adr.pays,
		util.prenom,
		util.nom,
		org.telephone,
		org.poste,
		util.courriel
FROM organisme org
INNER JOIN adresse adr ON adr.adresse_id = org.adresse
INNER JOIN type_rue typrue ON typrue.type_rue_id = adr.type_rue
INNER JOIN utilisateur util ON util.utilisateur_id = org.utilisateur_contact
WHERE org.no_osbl IS NOT NULL;

-- Fonctionnali�s - requ�tes
-- Ajout marchandise (d�j� cr��e par PA), fichier alimentaire.php, fonction: ajoutalimentaire()
-- Chaque valeur correspondant au champ de la table a besoin d'�tre mis dans les parent�ses (VALUES) avec les apostrophes
-- si c'est un champ d'un ou plusieurs caract�res ou de date
-- Requ�te qui fonctionne bien, rien � redire.

INSERT INTO alimentaire (nom, description_alimentaire, quantite, marchandise_etat, marchandise_unite, valeur, marchandise_statut, type_alimentaire, date_peremption) 
												VALUES(:nom, :description_alimentaire, :quantite, :marchandise_etat, :marchandise_unite, :valeur, :marchandise_statut, :type_alimentaire, :date_peremption);
-- Test
INSERT INTO alimentaire (nom, description_alimentaire, quantite, marchandise_etat, marchandise_unite, valeur, marchandise_statut, type_alimentaire, date_peremption) 
				VALUES('Raisin rouge globe sans p�pins', 'Import� du P�rou', 3.00, 1, 2, 6, 3, 1, '2016-01-31 23:00:00' );
 
 -- La fonction ajout marchandise implique l'ajout d'une transaction, fichier alimentaire.php, fonction: ajoutalimentaire()
 -- Remplacer les champs : donneur_id, :marchandise_id par des valeurs.
 -- Requ�te qui fonctionne bien, rien � redire.-- 
 
INSERT INTO transaction (donneur_id, marchandise_id, date_disponible, date_transaction)
			VALUES(:donneur_id, :marchandise_id,  NOW(), NOW());
 
 -- Test
INSERT INTO transaction (donneur_id, marchandise_id, date_disponible, date_transaction)
			VALUES(6, 73,  NOW(), NOW());			
 
-- Fonctionnali�s - requ�tes
-- Modification marchandise (d�j� cr��e par PA), fichier alimentaire.php, fonction: modifieralimentaire()
-- Chaque valeur correspondant au champ de la table a besoin d'�tre mis avec les apostrophes
-- si c'est un champ d'un ou plusieurs caract�res ou de date
-- La requ�te ne fonctionne pas bien (manque une virgule apr�s :valeur, et le nom de la colonne description serait 
-- � remplacer par description_alimentaire
-- alignement du WHERE vis � vis le UPDATE.

UPDATE alimentaire SET nom = :nom, description = :description_alimentaire, quantite = :quantite,
								marchandise_etat = :marchandise_etat, marchandise_unite = :marchandise_unite, valeur = :valeur
								type_alimentaire = :type_alimentaire, date_peremption = :date_peremption
								WHERE alimentaire_id = :alimentaire_id;
-- Test
UPDATE alimentaire SET nom = 'Raisin rouge globe sans p�pins', description_alimentaire = 'Import� du P�rou', quantite = 4.00,
								marchandise_etat = 1, marchandise_unite = 2, valeur = 9,
								type_alimentaire = 1, date_peremption = '2016-01-31 23:00:00'
WHERE alimentaire_id = 73;		

-- Fonctionnali�s - requ�tes
-- Suppression marchandise (d�j� cr��e par PA), fichier alimentaire.php, fonction: cancelleraliment($id_alimentaire)
-- Remplacer :id_alimentaire par un ID valide.
-- Requ�te parfaite.

UPDATE alimentaire SET marchandise_statut = 7 WHERE alimentaire_id = :id_alimentaire;

-- Test
UPDATE alimentaire SET marchandise_statut = 7 WHERE alimentaire_id = 73;

-- Fonctionnali�s - requ�tes
-- Collecte marchandise (d�j� cr��e par PA), fichier alimentaire.php, fonction: collecteralimentaire($id_alimentaire)
-- Remplacer :id_alimentaire par un ID valide et la marchandise_statut = 4 (collect�). J'aurais cru que la valeur
-- soit mise comme dans le cas pr�c�dent. Est-ce c'est g�r� par l'application?
-- Requ�te fonctionne avec les bonnes valeurs.

UPDATE alimentaire SET marchandise_statut = :marchandise_statut WHERE alimentaire_id = :id_alimentaire;	

-- Test 
UPDATE alimentaire SET marchandise_statut = 4 WHERE alimentaire_id = 73;

-- Fonctionnali�s - requ�tes
-- Collecte transaction
-- Remplacer : transaction_id par le num�ro de transaction que l'on veut modifier le champ date_collecte
-- Nouvelle requ�te pour PA
-- � ajouter sans doute dans le fichier alimentaire.php o� il y a d�j� le marchandise_statut qui change avec la collecte.

UPDATE transaction SET 	date_collecte =  NOW()
WHERE transaction_id = :transaction_id;

-- Test
UPDATE transaction SET 	date_collecte =  NOW()
WHERE transaction_id = 39;

-- Fonctionnali�s - requ�tes
-- Ajout reservation (d�j� cr��e par PA), fichier reservation.php, fonction: reservationajout()
-- Remplacer les valeurs : :donneur_id, :receveur_id, :marchandise_id, :date_disponible
-- Requ�te fonctionne seulement j'ai un avertissement, date_transaction :  Field 'date_transaction' doesn't have a default value
-- Lors de l'insertion la valeur est : 0000-00-00 00:00:00. Je ne sais pas si le PHP ajoute la valeur courante?
-- Une fa�on de r�gler l'avertissement c'est d'ajouter � l'insertion le champ : date_transaction et la valeur CURRENT_TIMESTAMP
-- Voir le 2e test et je n'ai aucun avertissement.

INSERT INTO transaction (donneur_id, receveur_id, marchandise_id, date_disponible, date_reservation)
									VALUES(:donneur_id, :receveur_id, :marchandise_id, :date_disponible,  CURRENT_TIMESTAMP);
	
-- Test
INSERT INTO transaction (donneur_id, receveur_id, marchandise_id, date_disponible, date_reservation)
									VALUES(6, NULL, 73, '2016-01-23 17:37:40',  CURRENT_TIMESTAMP);

-- 2e test
INSERT INTO transaction (donneur_id, receveur_id, marchandise_id, date_disponible, date_reservation, date_transaction)
									VALUES(6, NULL, 73, '2016-01-23 17:37:40',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

	