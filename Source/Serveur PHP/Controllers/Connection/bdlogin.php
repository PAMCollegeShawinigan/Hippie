<?php

use Illuminate\Database\Connection;

	$bdd = DB:: connection()->getPdo();
	$header = array ('Content-Type' => 'application/json; charset=UTF-8','charset' => 'utf-8');


function execution($requete, $array){
	
	try
	{
		$bdd = DB:: connection()->getPdo();

		
		$req = $bdd ->prepare($requete);
	
	
		$req->execute($array);
		
		return $req;
		
		
	}
	catch(PDOException $e){
		throw $e;
	}
	

}
function requete($requete)
	{
		try
		{
			$bdd = DB:: connection()->getPdo();
			
			return $bdd->query($requete);
		}
		catch(PDOException $e)
		{
			throw $e;
		}
		
	}	
	
