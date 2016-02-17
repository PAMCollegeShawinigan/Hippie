<?php

use Illuminate\Database\Connection;

	$bdd = DB:: connection()->getPdo();
	


function execution($requete, $array){
	
	try
	{
		$bdd = DB:: connection()->getPdo();

		
		$req = $bdd ->prepare($requete);
	
	
		return ($req->execute($array));
		
		
		
	}
	catch(PDOException $e){
		throw $e;
	}
}
	
	

?>