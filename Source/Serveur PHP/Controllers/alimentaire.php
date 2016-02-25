<?php

namespace App\Http\Controllers;

use App\User;
use App\Http\Controllers\alimentaire;


class alimentaire extends Controller
{

	public function alimentaireid($id) { // retourne l'objet aliment selon son id
		require('Connection/bdlogin.php');
		require('fonction.php');
		
			$req = 'SELECT * FROM alimentaire

									INNER JOIN marchandise_etat ON alimentaire.marchandise_etat = marchandise_etat.etat_id
									INNER JOIN marchandise_unite ON alimentaire.marchandise_unite = marchandise_unite.unite_id
									INNER JOIN marchandise_statut ON alimentaire.marchandise_statut = marchandise_statut.statut_id
									INNER JOIN type_aliment ON alimentaire.type_alimentaire = type_aliment.aliment_id

									WHERE alimentaire_id = :id';


			$arr = array('id' => $id);

				$resultat = execution($req,$arr)->fetch();

				//convertis la date de peremption en DATE_ATOM
			$date_peremption = convertirdate($resultat['date_peremption']);
				

			$array = array(	'nom' => $resultat['nom'], 
							'description' => $resultat['description_alimentaire'],
							'quantite' => $resultat['quantite'],
							'marchandise_etat'=> $resultat['description_marchandise_etat'],
							'unite' => $resultat['description_marchandise_unite'],
							'valeur' => $resultat['valeur'],
							'marchandise_statut' =>$resultat['description_marchandise_statut'],
							'type_alimentaire' => $resultat['description_type_aliment'],
							'date_peremption' => $date_peremption );

				// retourne 200 si le programme ne rencontre pas de probleme sinon execution() lance une exeption		
				return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE);
	}

	
	public function ajoutalimentaire(){ // permet d'ajouter un aliment et de créer une transaction

		require('Connection/bdlogin.php'); //inclu le fichier de connection a la basse de donné hip_dev
	
			$req = 'SELECT perissable FROM type_aliment WHERE aliment_id = :type_alimentaire ';

			$arr = array('type_alimentaire' => $_POST['type_alimentaire']);

				$resultat = execution($req,$arr)->fetch();

			// si le type alimentaire est perissable defini $date_peremption sinon le met NULL
			$date_peremption = siperissable($resultat['perissable'], $_POST['date_peremption']);
		

			$req = 'INSERT INTO alimentaire (nom, description_alimentaire, quantite, marchandise_etat, marchandise_unite, valeur, marchandise_statut, type_alimentaire, date_peremption)
										VALUES(:nom, :description_alimentaire, :quantite, :marchandise_etat, :marchandise_unite, :valeur, :marchandise_statut, :type_alimentaire, :date_peremption)';
		
		
			$arr = array(
					'nom' => $_POST['nom'],
					'description_alimentaire' => $_POST['description'],
					'quantite'=> $_POST['quantite'],
					'marchandise_etat' => $_POST['marchandise_etat'],
					'marchandise_unite'=> $_POST['unite'],
					'valeur' => $_POST['valeur'],
					'marchandise_statut' => '3', // TO_DO modifier le hard-coding ( 3 = disponible)
					'type_alimentaire'=> $_POST['type_alimentaire'],
					'date_peremption' => $date_peremption);										
													
				execution($req, $array);

			$req = 'INSERT INTO transaction (donneur_id, marchandise_id, date_disponible, date_transaction)
											VALUES(:donneur_id, :marchandise_id,  NOW(), NOW())';
					
			$arr = array(
						'donneur_id'=>  $_POST['donneur_id'],
						'marchandise_id' => $bdd->lastInsertId());

				execution($req, $array);
							// retourne 200 si le programme ne rencontre pas de probleme sinon execution lance une exeption
					return response('lajout a ete fait',200);

	}

	public function modifieralimentaire(){ // permet de modifier un aliment, necessite toutes les informations

		require('Connection/bdlogin.php');
		require('fonction.php');
		
			// si l'aliment n'est plus disponible envoi un 409
		if(!sidisponible($id_alimentaire))
		{
			return response('La marchandise n\'est plus disponible', 409);
		}

			$req = 'SELECT perissable FROM type_aliment WHERE aliment_id = :type_alimentaire ';

			$array = array('type_alimentaire'=>$_POST['type_alimentaire']);

				$resultat = execution($req, $array)->fetch();

				
			// si le type alimentaire est perissable defini $date_peremption sinon le met NULL
			$date_peremption = siperissable($resultat['perissable'], $_POST['date_peremption']);


			$req = 'UPDATE alimentaire SET 
							nom = :nom,
							description_alimentaire = :description_alimentaire,
							quantite = :quantite,
							marchandise_etat = :marchandise_etat,
							marchandise_unite = :marchandise_unite,
							valeur = :valeur,
							type_alimentaire = :type_alimentaire,
							date_peremption = :date_peremption

							WHERE alimentaire_id = :aliment_id';
							
			$array = array(
							'nom' => $_POST['nom'],
							'description_alimentaire' => $_POST['description'],
							'quantite'=> $_POST['quantite'],
							'marchandise_etat' => $_POST['marchandise_etat'],
							'marchandise_unite'=> $_POST['unite'],
							'valeur' =>$_POST['valeur'],
							'type_alimentaire'=> $_POST['type_alimentaire'],
							'date_peremption' =>$date_peremption,
							'aliment_id' => $_POST['id']);

				$execution($req, $array);
				
				// retourne 200 si le programme ne rencontre pas d'erreur sinon la fonction execution lance une exeption
			return response('La modification a ete fait avec succes',200);

	}

	public function cancelleraliment($id_alimentaire) // met un aliment au status cancellé et cré une transaction
	{
		require('Connection/bdlogin.php'); // inclus le fichier de connection à la bd
		
			// si l'aliment n'est plus disponible envoi un 409
		if(!sidisponible($id_alimentaire)){
			return response('La marchandise n\'est plus disponible', 409);
		}
		
			$req = 'SELECT donneur_id, MAX(date_transaction) FROM transaction WHERE marchandise_id = :marchandise_id'; // selectionne l'id du donneur dans transaction
			
			$array = array('marchandise_id' => $id_alimentaire);

				$resultat = execution($req, $array) -> fetch();

			$req ='INSERT INTO transaction (donneur_id, marchandise_id, date_transaction) VALUES (:donneur_id, :marchandise_id, NOW())';
			
			$array = array(
				'donneur_id' => $resultat['donneur_id'],
				'marchandise_id' => $id_alimentaire );

				execution($req, $array);

			$req = 'UPDATE alimentaire SET marchandise_statut = 7 WHERE alimentaire_id = :id_alimentaire';
		
			$array = array('id_alimentaire' => $id_alimentaire);
			
				execution($req, $array);
			// retourne 200 si le programme ne rencontre pas d'erreur sinon la fonction execution lance une exeption
			return response('La cancellation a ete fait',200);
	}

	public function collecteralimentaire($id_alimentaire){ // permet de collecter un aliment, met le status a collecté et cré une transaction
		require('Connection/bdlogin.php');
		require('fonction.php');
 
			// si l'aliment n'est plus disponible envoi un 409
		if(!sidisponible($id_alimentaire)){
			return response('La marchandise n\'est plus disponible', 409);
		}
		
			$req ='SELECT 
						transaction_id,
						receveur_id,
						donneur_id,
						date_reservation,
						date_disponible

						FROM transaction 
						WHERE marchandise_id = :marchandise_id 
						ORDER BY transaction_id DESC LIMIT 1'; // selectionne les informations de la derniere transaction relié a l'aliment
							
			$array = array('marchandise_id' => $id_alimentaire);

				$resultat = execution($req, $array) -> fetch();
	
			$req = 'INSERT INTO transaction (receveur_id, donneur_id, marchandise_id, date_reservation, date_disponible, date_transaction, date_collecte)
									VALUES (:receveur_id, :donneur_id, :marchandise_id, :date_reservation, :date_disponible, NOW(), NOW())';
			$array = array(
							'receveur_id'=>$resultat['receveur_id'],
							'donneur_id' => $resultat['donneur_id'],
							'marchandise_id' => $id_alimentaire,
							'date_reservation' => $resultat['date_reservation'],
							'date_disponible' => $resultat['date_disponible']);

				execution($req, $array);
			$req = 'UPDATE alimentaire 
									SET marchandise_statut = 4 
									WHERE alimentaire_id = :id_alimentaire'; // TODO enlever le hard-coding
			
			$array = array('id_alimentaire' => $id_alimentaire);
			
				execution($req, $array);
			
			// retourne 200 si le programme ne rencontre pas d'erreur sinon la fonction execution lance une exeption
			return response('La collecte a ete fait',200);

	}

}