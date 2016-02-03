<?php

namespace App\Http\Controllers;

use App\User;
use App\Http\Controllers\adresse;

class adresse extends Controller
{

    public function adresse($id) //retourne l'adresse par l'id
    {
		include('Connection/bdlogin.php');
		$header = array ('Content-Type' => 'application/json; charset=UTF-8','charset' => 'utf-8');
		
		$req = $bdd->prepare('SELECT * 
								FROM adresse 
								INNER JOIN type_rue on adresse.type_rue = type_rue.type_rue_id 
								WHERE adresse_id = :id');
		
				$req->execute(array(
					'id' => $id));
					
					$adresse = $req->fetch();
				
				
				
				$array = array('no_civique' => $adresse['no_civique'], 'nom' => $adresse['nom'], 
								'type_rue' => $adresse['description_type_rue'], 'app' => $adresse['app'], 'ville' => $adresse['ville'],
								'province' => $adresse['province'], 'code_postal' => $adresse['code_postal'], 'pays' => $adresse['pays']);
				
				
				
				
				return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE);
				
	
	
	}
}	