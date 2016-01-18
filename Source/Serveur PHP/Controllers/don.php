<?php

namespace App\Http\Controllers;

use App\User;
use App\Http\Controllers\don;

class don extends Controller
{

	public function listedonid($id_donneur)
	{
	
	$header = array ('Content-Type' => 'application/json; charset=UTF-8', 'charset' => 'utf-8' );
		
		include('Connection/bdlogin.php'); //inclu le fichier de connection a la basse de donné hip_dev		
		
		$req = $bdd -> prepare('SELECT ali.nom, ali.alimentaire_id,
		ali.description_alimentaire, ali.quantite, marunit.description_marchandise_unite,ali.date_peremption, ali.valeur, marstat.description
		
		FROM transaction trx
		
		
		INNER JOIN organisme org ON org.organisme_id = trx.donneur_id
		INNER JOIN alimentaire ali ON ali.alimentaire_id = trx.marchandise_id
		INNER JOIN type_aliment typali ON typali.aliment_id = ali.type_alimentaire
		INNER JOIN marchandise_statut marstat ON marstat.statut_id = ali.marchandise_statut
		INNER JOIN marchandise_etat maretat ON maretat.etat_id = ali.marchandise_etat
		INNER JOIN marchandise_unite marunit ON marunit.unite_id = ali.marchandise_unite

		WHERE trx.donneur_id = :id_donneur ');
		
					$req->execute(array(
						'id_donneur' => $id_donneur
						));
		
		$array = array();
		
		while($resultat = $req->fetch()){
			
			$arr = array( 'id' => $resultat['alimentaire_id'], 'quantite' => $resultat['quantite'], 'unite' => $resultat['description_marchandise_unite'], 
			'nom' => $resultat['nom'], 'description' => $resultat['description_alimentaire'], 
			 'date_peremption' => $resultat['date_peremption'], 'valeur' => $resultat['valeur']);
			
			array_push($array, $arr);
			
		}
		return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE);
	}
	
}	