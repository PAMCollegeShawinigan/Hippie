<?php
function convertirdate($datebd) // retourne la date en format DATE_ATOM si la date n'est pas null
{
	if($datebd != null )
	{

		$date = date_create($datebd);

		$date_peremption = date_format($date, DATE_ATOM);

	}
	else
	{
		$date_peremption = null;
	}

	return $date_peremption;
}

function siperissable($resultat, $post) // si l'aliment est perissable retourne une date sinon retourne null
{
	$date_peremption = null;

	if($resultat == 0)
	{
		$dare_peremtion = $post;
	}
	return $date_peremption;
}

function sidisponible($id_alimentaire)// retourne vrai si l'aliment est toujours disponble sinon retourne faux
{
	require('Connection/bdlogin.php');

	$req = 'SELECT marchandise_statut
								FROM alimentaire
								WHERE alimentaire_id = :alimentaire_id';

	$array = array('alimentaire_id' => $id_alimentaire);

		$resultat = execution($req, $array)->fetch();

	return($resultat['marchandise_status'] == 3);

}
