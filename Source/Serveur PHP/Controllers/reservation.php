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
			
			
			$req2 = $bdd ->prepare('INSERT INTO transaction (donneur_id, receveur_id, marchandise_id, date_disponible, date_reservation)
									VALUES(:donneur_id, :receveur_id, :marchandise_id, :date_disponible,  CURRENT_TIMESTAMP)');
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
}
	