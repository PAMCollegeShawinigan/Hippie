<?php

namespace App\Http\Controllers;

use App\User;
use App\Http\Controllers\adresse;

class adresse extends Controller
{

    public function adresse($id) //retourne l'adresse par l'id
    {
		require('Connection/bdlogin.php');
		
		$req = 'SELECT * 
						FROM adresse 
						INNER JOIN type_rue on adresse.type_rue = type_rue.type_rue_id 
						WHERE adresse_id = :id';
		
		$array = array('id' => $id);
					
			$adresse = execution($req, $array)->fetch();
							
				
		$array = array(	'no_civique' => $adresse['no_civique'],
						'nom' => $adresse['nom'], 
						'type_rue' => $adresse['description_type_rue'],
						'app' => $adresse['app'], 
						'ville' => $adresse['ville'],
						'province' => $adresse['province'],
						'code_postal' => $adresse['code_postal'],
						'pays' => $adresse['pays']);
					
				//retourne 200 si le programme ne rencontre pas d'erreur sinon execution lance une exeption
				return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE);
	
	}
}	