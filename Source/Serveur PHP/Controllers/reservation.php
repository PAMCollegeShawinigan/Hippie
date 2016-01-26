<?php

namespace App\Http\Controllers;

use App\User;
use App\Http\Controllers\reservation;

class reservation extends Controller
{

	public function reservationajout()
	{
	 /// je recoit id marchandise
		include('Connection/bdlogin.php'); //inclu le fichier de connection a la basse de donné hip_dev
			$req = $bdd -> prepare('SELECT marchandise_statut FROM alimentaire WHERE alimentaire_id = :alimentaire_id');
					$req-> execute(array(
						'alimentaire_id' => $_POST['marchandise_id']
					));
					$resultat = $req->fetch();
			if($resultat['marchandise_statut'] != 3 ) // si le status n'est plus a disponible
			{
				return response('La requete n\'a pas pu être exécuté', 409);
			}		
			else // fait la reservation et la transaction
			{
			
			
			// recupere les informations de la derniere transaction fait pour le compte
			$req1 = $bdd -> prepare('SELECT transaction_id, donneur_id, date_disponible FROM transaction WHERE marchandise_id = :marchandise_id ORDER BY transaction_id DESC LIMIT 1 ');
						$req1->execute(array(
						'marchandise_id' => $_POST['marchandise_id'] 
						));
						
						$transaction = $req1->fetch();
			
			
			$req2 = $bdd ->prepare('INSERT INTO transaction (donneur_id, receveur_id, marchandise_id, date_disponible, date_reservation, date_transaction)
									VALUES(:donneur_id, :receveur_id, :marchandise_id, :date_disponible,  NOW(), NOW() )');
				$req2->execute(array(
				'donneur_id'=>  $transaction['donneur_id'],
				'receveur_id' => $_POST['receveur_id'],
				'marchandise_id' => $_POST['marchandise_id'],
				'date_disponible' => $transaction['date_disponible']
				));
				
			$req3 = $bdd -> prepare('UPDATE alimentaire SET marchandise_statut = :marchandise_statut WHERE alimentaire_id = :id'); 
				$req3->execute(array(
				'id' => $_POST['marchandise_id'],
				'marchandise_statut' => '2' ));  // TODO modifier le hard-coding ( 2 = reserve)
				
				return response('La reservation a ete fait',200);
	
			}
	}
	
	public function annulerreservation($marchandise_id){
		include('Connection/bdlogin.php'); //inclu le fichier de connection a la basse de donné hip_dev
		
					$req1 = $bdd -> prepare('SELECT transaction_id, donneur_id, receveur_id, date_disponible FROM transaction WHERE marchandise_id = :marchandise_id ORDER BY transaction_id DESC LIMIT 1');
						$req1->execute(array(
						'marchandise_id' => $marchandise_id
						));
						
						$transaction = $req1->fetch();
						
						
					$req2 = $bdd ->prepare('INSERT INTO transaction (donneur_id, receveur_id, marchandise_id, date_disponible, date_transaction)
											VALUES(:donneur_id, :receveur_id, :marchandise_id, NOW(), NOW())');
						$req2->execute(array(
							'donneur_id'=>  $transaction['donneur_id'],
							'receveur_id' => $transaction['receveur_id'],
							'marchandise_id' => $marchandise_id
							));
						
					$req3 = $bdd ->prepare('UPDATE alimentaire SET marchandise_statut = :marchandise_statut WHERE alimentaire_id = :id');
						$req3->execute(array(
							'marchandise_statut' => '3',
							'id' => $marchandise_id
							));
		return response('La reservation a ete annulé',200);
		
	}
	public function listereservation($id_organisme){
		include('Connection/bdlogin.php'); //inclu le fichier de connection a la basse de donné hip_dev
		$header = array ('Content-Type' => 'application/json; charset=UTF-8', 'charset' => 'utf-8' );
		
		$req = $bdd -> prepare('SELECT DISTINCT	typali.description_type_aliment, 
											ali.nom,
											ali.description_alimentaire,
											ali.quantite,
											marunit.description_marchandise_unite,
											ali.date_peremption,
											org.nom,
											adr.no_civique, 
											typrue.description_type_rue, 
											adr.nom, 
											adr.ville, 
											adr.province, 
											adr.code_postal, 
											adr.pays,
											org.telephone,
											org.poste,
											util.courriel,
											util.nom,
											util.prenom,
											util.telephone,
											ali.alimentaire_id
											
		
											FROM type_aliment typali
											INNER JOIN alimentaire ali ON ali.type_alimentaire = typali.aliment_id
											INNER JOIN marchandise_unite marunit ON marunit.unite_id = ali.marchandise_unite
											INNER JOIN transaction trx ON trx.marchandise_id = ali.alimentaire_id
											INNER JOIN organisme org ON org.organisme_id = trx.receveur_id
											INNER JOIN adresse adr ON adr.adresse_id = org.adresse
											INNER JOIN type_rue typrue ON typrue.type_rue_id = adr.type_rue
											INNER JOIN utilisateur util ON util.utilisateur_id = org.utilisateur_contact
											WHERE 	ali.marchandise_statut = 2
											AND trx.receveur_id = :id_organisme
											ORDER BY typali.aliment_id DESC');	

											
								$req->execute(array(
						'id_organisme' => $id_organisme
						));
						
						$array = array();
				while($resultat = $req->fetch()){
					
			if($resultat[5] != null )
			{
			$date = date_create($resultat[5]);
			
			$date_peremption = date_format($date, DATE_ATOM);
			}
			else
			{
				$date_peremption = null;
			}	
						
					
						$adresse = array('no_civique' => $resultat[7], 'type_rue' => $resultat[8], 'nom' => $resultat[9], 'ville' => $resultat[10], 'province' => $resultat[11], 'code_postal' => $resultat[12], 'pays' =>$resultat[13]);
						
						$contact = array('nom'=> $resultat[17], 'prenom' => $resultat[18], 'courriel' => $resultat[16], 'telephone' => $resultat[19] );
						
						$organisme = array('nom' => $resultat[6], 'telephone' => $resultat[14], 'poste' => $resultat[15], 'adresse' => $adresse, 'contact' => $contact );
						
						$arr = array('id' => $resultat[20], 'type_alimentaire' => $resultat[0], 'nom' => $resultat[1], 'description' => $resultat[2], 'quantite' => $resultat[3], 'unite' => $resultat[4], 'date_peremption' => $date_peremption, 'organisme' => $organisme  );
						
						array_push($array, $arr);
				}
				
				return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE);
	
		
	}
	
}
	