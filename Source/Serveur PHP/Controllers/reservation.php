<?php

namespace App\Http\Controllers;

use App\User;
use App\Http\Controllers\reservation;

class reservation extends Controller
{

	public function reservationajout() // permet de faire une reservation
	{
		require('Connection/bdlogin.php'); //inclu le fichier de connection a la basse de donné hip_dev
		require('fonction.php');	
			
			if(!sidisponible($_POST['marchandise_id'])) // retourne si l'aliment est encore disponible
			{
				return response('La marchandise n\'est plus disponible', 409); // si non-disponible retourne une erreur
			}
				
			// recupere les informations de la derniere transaction fait pour le compte
			$req = 'SELECT 	transaction_id,
							donneur_id,
							date_disponible
							
							FROM transaction
							WHERE marchandise_id = :marchandise_id 
							ORDER BY transaction_id DESC LIMIT 1 ';
			
			$variable = array('marchandise_id' => $_POST['marchandise_id']);
						
				$transaction = execution($req, $array)->fetch();
			
			// cré la transaction
			$req = 'INSERT INTO transaction (donneur_id, receveur_id, marchandise_id, date_disponible, date_reservation, date_transaction)
									VALUES(:donneur_id, :receveur_id, :marchandise_id, :date_disponible,  NOW(), NOW() )';
									
			$variable = array(	'donneur_id'=>  $transaction['donneur_id'],
								'receveur_id' => $_POST['receveur_id'],
								'marchandise_id' => $_POST['marchandise_id'],
								'date_disponible' => $transaction['date_disponible']);
								
				execution($req, variable);					
				
			//mise a jour de l'aliment
			$req = 'UPDATE alimentaire 
								SET marchandise_statut = 2 
								
								WHERE alimentaire_id = :id'; 
								
			$variable = array('id' => $_POST['marchandise_id']);
			
				execution($req, $variable);
				
			// retourne 200 si le programme ne rencontre pas de probleme sinon execution lance une exeption	
		return response('La reservation a ete fait',200);
	
	}
	
	public function annulerreservation($marchandise_id) // permet d'annuler une reservation et rends l'aliment disponible
	{
		require('Connection/bdlogin.php'); //inclu le fichier de connection a la basse de donné hip_dev
		
			// recupere les informations de la derniere transaction
			$req = 'SELECT transaction_id,
							donneur_id,
							receveur_id,
							date_disponible
							
							FROM transaction 
							
							WHERE marchandise_id = :marchandise_id 
							ORDER BY transaction_id DESC LIMIT 1';
							
			$variable = array('marchandise_id' => $marchandise_id);
						
				$transaction = execution($req, $variable)->fetch();
						
			//création de la transaction			
			$req = 'INSERT INTO transaction	 (donneur_id, marchandise_id, date_disponible, date_transaction)
										VALUES(:donneur_id, :marchandise_id, NOW(), NOW())';
										
			$varable = array(	'donneur_id'=>  $transaction['donneur_id'],
								'marchandise_id' => $marchandise_id);
								
				execution($req, $variable);				
			// mise a jour de l'aliment			
			$req = 'UPDATE alimentaire
					SET marchandise_statut = 3
					WHERE alimentaire_id = :id';
					
			$variable = array('id' => $marchandise_id);
				
				execution($req, $variable);
				
			// retourne 200 si le programme ne rencontre pas de probleme sinon execution lance une exeption
		return response('La reservation a ete annulé',200);
		
	}
	
	
	public function listereservation($id_organisme){ // retourne la liste des reservation incluant l'aliment et l'adresse
		require('Connection/bdlogin.php'); //inclu le fichier de connection a la basse de donné hip_dev
	
		
		$req = 'SELECT 	ali.alimentaire_id,
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
							 ORDER BY typali.description_type_aliment, ali.nom, ali.description_alimentaire, ali.quantite';	

											
		$variable = array('receveur_id' => $id_organisme);
						
			$array = array();
		while($resultat = execution($req, $variable)->fetch())
		{		
					
			$adresse = array(	'id' => $resultat[8],
								'no_civique' => $resultat[9],
								'type_rue' => $resultat[10],
								'nom' => $resultat[11],
								'app' => $resultat[16],
								'ville' => $resultat[12],
								'province' => $resultat[13],
								'code_postal' => $resultat[14],
								'pays' =>$resultat[15]);
						
			$contact = array(	'nom'=> $resultat[20],
								'prenom' => $resultat[19],
								'courriel' => $resultat[21],
								'telephone' => $resultat[22] );
						
			$organisme = array(	'nom' => $resultat[7],
								'telephone' => $resultat[17],
								'poste' => $resultat[18],
								'adresse' => $adresse,
								'contact' => $contact );
						
			$arr = array(		'id' => $resultat[0],
								'type_alimentaire' => $resultat[1],
								'nom' => $resultat[2],
								'description' => $resultat[3],
								'quantite' => $resultat[4],
								'unite' => $resultat[5],
								'date_peremption' => convertirdate($resultat[6]),
								'organisme' => $organisme  );
						
			array_push($array, $arr);
		}
	// retourne 200 si le programme ne rencontre pas de probleme sinon execution lance une exeption		
	return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE);
		
	}
	
}
	