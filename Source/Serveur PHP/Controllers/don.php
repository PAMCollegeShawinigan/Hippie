<?php

namespace App\Http\Controllers;

use App\User;
use App\Http\Controllers\don;

class don extends Controller
{

	public function listedonid()
	{
	
	$header = array ('Content-Type' => 'application/json; charset=UTF-8','charset' => 'utf-8');
			
			include('Connection/bdlogin.php'); //inclu le fichier de connection a la basse de donné hip_dev
			
			$req = $bdd -> prepare('SELECT * FROM transaction INNER JOIN marchandise_id on marchandise.marchandise_id WHERE donneur_id = :id AND ');
			
			$req->execute(array(
						'id' => $id
						));
						$array = array();
						while($resultat = $req->fetch())
						{
						
						$arr = array('' => );
						
						}
	
	}
	
}	