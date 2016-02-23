<?php

namespace App\Http\Controllers;

use App\User;
use App\Http\Controllers\donneurmois;

class donneurmois extends Controller
{

	public function donneurdumois() // retourne le ou les donneurs du mois selon les date debut et fin 
	{
		
		require('fonction.php');
		require('Connection/bdlogin.php'); //inclu le fichier de connection a la basse de donné hip_dev	
		
		$req = 'SELECT 	don.organisme_id,
						org.nom,
						don.montant_total,
						don.date_donneur_mois
											  
						FROM donneur_mois don
						
						INNER JOIN organisme org ON org.organisme_id = don.organisme_id
						WHERE EXTRACT(YEAR_MONTH FROM date_donneur_mois) BETWEEN (EXTRACT(YEAR_MONTH FROM :date_debut)) 
						AND (EXTRACT(YEAR_MONTH FROM :date_fin))';	
		
		$variables = array(	'date_debut' => $_GET['date_debut'],
							'date_fin' => $_GET['date_fin']);
							
			$array = array();	
			While( $resultat = $req->fetch())	
			{
						$date = convertirdate($resultat['date_donneur_mois']); // retourne la date en DATE_ATOM
						
						$stats = array(	'montant_total' => $resultat['montant_total'],
										'date' => $date);
										
						$org = array(	'nom'=> $resultat['nom'],
										'organisme_id' => $resultat['organisme_id'],
										'statistique' => $stats);
						
						array_push($array, $org );
						
			}
					
		return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE);
	}	
	
}	