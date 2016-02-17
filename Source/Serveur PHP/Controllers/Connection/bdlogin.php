<?php

date_default_timezone_set('America/Montreal');

try
{
	$bdd = new PDO('mysql:host=localhost;dbname=yolaine_hipdev; charset=utf8', 'yolaine_hippie', 'hippie', array(PDO::MYSQL_ATTR_INIT_COMMAND => "SET NAMES utf8"));
	
}
catch (Exception $e)
{
        die('Erreur : ' . $e->getMessage());
}

function preparation($requete){
	
	try
	{
	return($bdd->prepare($requete);
	}
	catch(PDOException $e)
	{
		return response('Echec de la preparation de la requete :'.$requete.'('.$e ->getCode().')'. $e ->getMessage(),400);
	}
}
function executer($req,$array){
	
	try
	{
	return($req ->execute($array));
	}
	catch(PDOException $e)
	{
		return response('Echec de la preparation de la requete :'.$requete.'('.$e ->getCode().')'. $e ->getMessage(),400);
	}
}

?>