<?php

namespace App\Http\Controllers; //ne pas modifier

use App\User; // ne pas modifier
use App\Http\Controllers\(nom du document); // insérer le nom du document sans extensions ex: vierge, don, organisme... 

class (Nom du document) extends Controller // nsérer le nom du document sans extensions ex: vierge, don, organisme...
{

    public function adresse($id)
    {
		include('Connection/bdlogin.php'); // inclus le fichier qui s'occupe de la connection à la base de donné, necessaire pour faire des requêtes
		  $header = array ('Content-Type' => 'application/json; charset=UTF-8', 'charset' => 'utf-8');// pour faire afficher les accents quand on fait des tests via navigateur web
		  
		$req = $bdd->prepare('Requete MYSQL'); // on prépare la requete sans l'exécuter
		$req->execute(array(
					'variable requete' => $variable php));  // si la requete contient des variables ex: WHERE id_alimentaire = :id nous devons définir les variable ici
					
					$array = array(); // cré un array vide qu,on va remplir dans la boucle
				while($resultat = $req->fetch()){ // permet de récupérer les resultats de la requete par nom de champ ex: $resultat['alimentaire_id']
												// la boucle s'execute tant et aussi longtemps que la requete retourne une collone
												
					$arr = array('nom du champ Json' => $resultat['nom du champ de la table'],'nom du champ Json' => $resultat['nom du champ de la table'] );							
						// cré un array temporaire ou l'ont insère le nom du champ ainsi que le resultat de la bd 
						//ex: $arr = array('id' => $resultat['alimentaire_id'], 'nom' => $resultat['nom']);
						
						array_push($array, $arr); // on pousse l'array temporaire dans l'array complete (faire attention a l'ordre)
				}//fermeture de la boucle
			return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE); // transforme le $arra en JSON et retourne l'objet	
	}// fermeture de la fonction
	 // on peux insérer une nouvelle fonction ici
} // fermeture de la classe
?>	