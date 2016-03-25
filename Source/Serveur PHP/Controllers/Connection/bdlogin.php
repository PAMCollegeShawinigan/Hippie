<?php

use Illuminate\Database\Connection;

	$bdd = DB:: connection()->getPdo();
	$header = array ('Content-Type' => 'application/json; charset=UTF-8','charset' => 'utf-8');


function execution($requete, $array) //execute une requete avec des variables
{
	try
	{
		$bdd = DB:: connection()->getPdo();

		
		$req = $bdd ->prepare($requete);
	
	
		$req->execute($array);
		
		return $req;
		
	}
	
	catch(PDOException $e)
	{
		throw $e;
	}
	

}


function requete($requete) //execute une requete sans variables
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
	
