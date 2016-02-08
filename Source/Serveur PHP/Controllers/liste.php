<?php

namespace App\Http\Controllers;

use App\User;
use App\Http\Controllers\liste;

class liste extends Controller
{

	public function liste($type){
	session_start();
	include('Connection/bdlogin.php');
	//if(isset($_SESSION['id'])){

		$header = array (
                'Content-Type' => 'application/json; charset=UTF-8',
                'charset' => 'utf-8'
            );

		switch($type){

			case 'alimentaire': //retourne tous les champs de la table type_aliment


		$req = $bdd->query('SELECT * FROM type_aliment');


				$array = array();
				while($resultat = $req->fetch())
				{
					$arr = array('id' => $resultat['aliment_id'], 'description' => $resultat['description_type_aliment'], 'perissable' => ($resultat['perissable'] != 0) );


					array_push($array, $arr);
				}

				return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE);



			break;
			case 'etat': //retourne tous les champs de la table marcandise_etat

			$req = $bdd->query('SELECT * FROM marchandise_etat');


				$array = array();
				while($resultat = $req->fetch())
				{
					$arr = array('id' => $resultat['etat_id'], 'description' => $resultat['description_marchandise_etat'] );


					array_push($array, $arr);
				}


				return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE);


			break;
			case 'statut':  //retourne tous les champs de la table marchandise_statut

			$req = $bdd->query('SELECT * FROM marchandise_statut');


				$array = array();
				while($resultat = $req->fetch())
				{
					$arr = array('id' => $resultat['statut_id'], 'description' => $resultat['description_marchandise_statut'] );


					array_push($array, $arr);
				}


				return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE);


			break;
			case 'unite':  //retourne tous les champs de la table marchandise_unite

			$req = $bdd->query('SELECT * FROM marchandise_unite');


				$array = array();
				while($resultat = $req->fetch())
				{
					$arr = array('id' => $resultat['unite_id'], 'description' => $resultat['description_marchandise_unite'] );


					array_push($array, $arr);
				}


				return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE);


			break;

			case 'rue':   //retourne tous les champs de la table type_rue

			$req = $bdd->query('SELECT * FROM type_rue');


				$array = array();
				while($resultat = $req->fetch())
				{
					$arr = array('id' => $resultat['type_rue_id'], 'description' => $resultat['description_type_rue'] );


					array_push($array, $arr);
				}

				return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE);


			break;

			default:
				return response('Aucune liste disponible pour '.$type.'',404);
			break;
		}
	//}
	//else{
		//response('Erreur d\'authentification', 403);

	//}


}
}