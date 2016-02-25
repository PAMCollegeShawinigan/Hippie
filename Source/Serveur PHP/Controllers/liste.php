<?php

namespace App\Http\Controllers;

use App\User;
use App\Http\Controllers\liste;

class liste extends Controller
{

	public function liste($type){ //retourne la liste d'element contenu dans la bd selon le type demandé
	
	include('Connection/bdlogin.php');

		switch($type){

			case 'alimentaire': //retourne tous les champs de la table type_aliment

				$req = requete('SELECT * FROM type_aliment');

				break;
			case 'etat': //retourne tous les champs de la table marcandise_etat

				$req = requete('SELECT * FROM marchandise_etat');

				break;
			case 'statut':  //retourne tous les champs de la table marchandise_statut

				$req = requete('SELECT * FROM marchandise_statut');

				break;
			case 'unite':  //retourne tous les champs de la table marchandise_unite

				$req = requete('SELECT * FROM marchandise_unite');

				break;

			case 'rue':   //retourne tous les champs de la table type_rue

				$req = requete('SELECT * FROM type_rue');
		
				break;

			default: // reponse si l'url n'est pas bonne
				return response('Aucune liste disponible pour '.$type.'',404);
				break;
		}
		
			$array = array();
				while($resultat = $req->fetch())
				{
					$arr = array('id' => $resultat[0], 'description' => $resultat[1] );

					if(isset($resultat[2]))
					{
						$arr['perissable'] = ( $resultat[2] != 0);
						
					}
					
					array_push($array, $arr);
				}

				// retourne 200 si le programme ne rencontre pas d'erreur sinon la fonction execution lance une exeption
				return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE);


	}
}