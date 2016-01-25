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
			// recupere les informations de la derniere transaction fait pour le compte
			$req1 = $bdd -> prepare('SELECT donneur_id, date_disponible, MAX(date_transaction) FROM transaction WHERE marchandise_id = :marchndise_id');
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
				'marchandise_statut' => '2' ));  // TO_DO modifier le hard-coding ( 2 = reserve)
				
				return response('La reservation a ete fait',200);
	
	
	}
	
	public function annulerreservation($marchandise_id){
		include('Connection/bdlogin.php'); //inclu le fichier de connection a la basse de donné hip_dev
		
					$req1 = $bdd -> prepare('SELECT donneur_id, receveur_id, date_disponible, MAX(date_transaction) FROM transaction WHERE marchandise_id = :marchandise_id');
						$req1->execute(array(
						'marchandise_id' => $marchandise_id
						));
						
						$transaction = $req1->fetch();
						
						
					$req2 = $bdd ->prepare('INSERT INTO transaction (donneur_id, receveur_id, marchandise_id, date_disponible, date_reservation)
											VALUES(:donneur_id, :receveur_id, :marchandise_id, :date_disponible, :date_reservation)');
						$req2->execute(array(
							'donneur_id'=>  $transaction['donneur_id'],
							'receveur_id' => $transaction['receveur_id'],
							'marchandise_id' => $marchandise_id,
							'date_disponible' => $transaction['date_disponible'] ,
							'date_reservation' => '0000-00-00 00:00:00'
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
		
		$req = $bdd -> prepare('SELECT	typali.description_type_aliment, 
											ali.nom,
											ali.description_alimentaire,
											ali.quantite,
											marunit.description_marchandise_unite,
											trx.date_reservation,
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
											util.telephone
											
		
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
					
			if($resultat[6] != null )
			{
			$date = date_create($resultat[6]);
			
			$date_peremption = date_format($date, DATE_ATOM);
			}
			else
			{
				$date_peremption = null;
			}	
					
			if($resultat[5] != null )
			{
			$dater = date_create($resultat[5]);
			
			$date_reservation = date_format($dater, DATE_ATOM);
			}
			else
			{
				$date_reservation = null;
			}	
					
					
						$adresse = array('no_civique' => $resultat[8], 'type_rue' => $resultat[9], 'nom' => $resultat[10], 'ville' => $resultat[11], 'province' => $resultat[12], 'code_postal' => $resultat[13], 'pays' =>$resultat[14]);
						
						$contact = array('nom'=> $resultat[18], 'prenom' => $resultat[19], 'courriel' => $resultat[17], 'telephone' => $resultat[20] );
						
						$organisme = array('nom' => $resultat[7], 'telephone' => $resultat[15], 'poste' => $resultat[16], 'adresse' => $adresse, 'contact' => $contact );
						
						$arr = array('nom' => $resultat[0], 'description' => $resultat[1], 'quantite' => $resultat[2], 'unite' => $resultat[3], 'date_peremption' => $date_peremption, 'date_reservation' => $date_reservation, 'organisme' => $organisme  );
						
						array_push($array, $arr);
				}
				
				return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE);
	
		
	}
	
}
	