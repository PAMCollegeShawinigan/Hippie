<?php

namespace App\Http\Controllers;

use App\User;
use App\Http\Controllers\donneurmois;

class donneurmois extends Controller
{

	public function donneurdumois() // fait afficher la liste des entreprises qui ont des aliments a donner
	{
		$header = array ('Content-Type' => 'application/json; charset=UTF-8', 'charset' => 'utf-8' );
		require('function.php');
		include('Connection/bdlogin.php'); //inclu le fichier de connection a la basse de donné hip_dev	
		
		$req = $bdd->prepare('SELECT don.organisme_id,
									  org.nom,
									  don.montant_total,
									  don.date
									  
									FROM donneur_mois don
									INNER JOIN organisme org ON org.organisme_id = don.organisme_id
									WHERE don.date BETWEEN :date_debut AND :date_fin');
		
		
		
		
		
			$req->execute(array(
								'date_debut' => $_GET['date_debut'],
								'date_fin' => $_GET['date_fin']));
							
					$array = array();	
					While( $resultat = $req->fetch())	
					{
						$date = convertirdate($resultat['date']);
						$stats = array('montant_total' => $resultat['montant_total'], 'date' => $date);
						$org = array('nom'=> $resultat['nom'], 'organisme_id' => $resultat['organisme_id'], 'statistique' => $stats);
						
						array_push($array, $org );
						
					}
					
						return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE);
		}
		
		
		
	
	
	
}	