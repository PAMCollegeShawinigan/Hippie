<?php

namespace App\Http\Controllers;

use App\User;
use App\Http\Controllers\connection;

class Connection extends Controller
{

    public function connect()
    {
        include('Connection/bdlogin.php'); //inclu le fichier de connection a la basse de donné hip_dev
	
	$req = $bdd->prepare('SELECT * FROM utilisateur WHERE courriel = :courriel AND mot_de_passe = :mdp');
			$req->execute(array(
				'courriel' => $_POST['courriel'],
					'mdp' => $_POST['mot_de_passe'])); 

				$resultat = $req->fetch();
	
	if($resultat){ //si il y a un resultat c'est que le nom_utilisateur et mot_de_passe sont correct
		//session_start();
		//$_SESION['id'] = $resultat['utilisateur_id'];
		
		$organisme = array('id' => $resultat['organisme_id']);
		
		$arr = array('id' => $resultat['utilisateur_id'],'nom' => $resultat['nom'], 'prenom' => $resultat['prenom'], 'telephone' => $resultat['telephone'], 'moyen_contact' => $resultat['moyen_contact'],
					'courriel' => $resultat['courriel'], 'organisme' => $organisme);
	
	return response(json_encode($arr),200);
	}
	
	else{ //si pas de resultat un des 2 informations fournis ne sont pas bon
		
		return response('Mauvais identifiant ou mot de passe',403);
	}
    }
}