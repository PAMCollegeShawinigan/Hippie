<?php

namespace App\Http\Controllers;

use App\User;
use App\Http\Controllers\alimentaire;

class alimentaire extends Controller
{

	public function alimentaireid($id)
	{
	
    $header = array (
                'Content-Type' => 'application/json; charset=UTF-8',
                'charset' => 'utf-8'
            );
			
		include('Connection/bdlogin.php'); //inclu le fichier de connection a la basse de donné hip_dev
		
		$req = $bdd->prepare('Select * From alimentaire 
		INNER JOIN marchandise_etat ON alimentaire.marchandise_etat = marchandise_etat.etat_id
		INNER JOIN marchandise_unite ON alimentaire.marchandise_unite = marchandise_unite.unite_id
		INNER JOIN marchandise_statut ON alimentaire.marchandise_statut = marchandise_statut.statut_id
		INNER JOIN type_aliment ON alimentaire.type_alimentaire = type_aliment.aliment_id
		WHERE alimentaire_id = :id AND marchandise_statut = 3'); // Requête de tout les champs d'organisme
				
				
				$req->execute(array(
					'id' => $id));
		$resultat = $req->fetch();
		
		if($resultat['date_peremption'] != null ){
		
		$date = date_create($resultat['date_peremption']);
			
		$date_peremption = date_format($date, DATE_ATOM);
		}
		else
		{
			$date_peremption = 'null';
		}
		
		$arr = array('nom' => $resultat['nom'], 'description' => $resultat['description_alimentaire'], 'quantite' => $resultat['quantite'],
					'marchandise_etat'=> $resultat['description_marchandise_etat'], 'marchandise_unite' => $resultat['description_marchandise_unite'],
					'valeur' => $resultat['valeur'], 'marchandise_statut' =>$resultat['description_marchandise_statut'], 'type_alimentaire' => $resultat['description_type_aliment'], 	
					'date_peremption' => $date_peremption);
						
						return response() -> json($arr,200,$header,JSON_UNESCAPED_UNICODE);
	}
	
	public function ajoutalimentaire(){
		
		include('Connection/bdlogin.php'); //inclu le fichier de connection a la basse de donné hip_dev
		
		$perissable = $bdd->prepare('SELECT perissable FROM type_aliment WHERE aliment_id = :type_alimentaire ');
		
		$perissable->execute(array(
		'type_alimentaire'=>$_POST['type_alimentaire']
		));
		
		$reponse = $perissable->fetch();
		
		if($reponse['perissable'] == 0){
			$date_peremption = NULL;
		}
		else
		{
			$date_peremption = $_POST['date_peremption'];
		}
		
		
		$req = $bdd->prepare('INSERT INTO alimentaire (nom, description_alimentaire, quantite, marchandise_etat, marchandise_unite, valeur, marchandise_statut, type_alimentaire, date_peremption) 
												VALUES(:nom, :description_alimentaire, :quantite, :marchandise_etat, :marchandise_unite, :valeur, :marchandise_statut, :type_alimentaire, :date_peremption)');
			$req->execute(array(
				'nom' => $_POST['nom'], 
				'description_alimentaire' => $_POST['description_alimentaire'],
				'quantite'=> $_POST['quantite'],
				'marchandise_etat' => $_POST['marchandise_etat'], 
				'marchandise_unite'=> $_POST['marchandise_unite'],
				'valeur' =>$_POST['valeur'], 
				'marchandise_statut' => '3', // TO_DO modifier le hard-coding ( 3 = disponible)
				'type_alimentaire'=> $_POST['type_alimentaire'], 
				'date_peremption' =>$date_peremption));
				
			$last_index = $bdd->lastInsertId();	
			
			
			$req2 = $bdd ->prepare('INSERT INTO transaction (donneur_id, marchandise_id, date_disponible, date_transaction)
			VALUES(:donneur_id, :marchandise_id,  NOW(), NOW())');
				$req2->execute(array(
				'donneur_id'=>  $_POST['donneur_id'],
				'marchandise_id' => $last_index
				));
				
				return response('lajout a ete fait',200);
				
	}
	
	public function modifieralimentaire(){
		
		include('Connection/bdlogin.php');
		
		$perissable = $bdd->prepare('SELECT perissable FROM type_aliment WHERE aliment_id = :type_alimentaire ');
		
		$perissable->execute(array(
		'type_alimentaire'=>$_POST['type_alimentaire']
		));
		
		$reponse = $perissable->fetch();
		
		if($reponse['perissable'] == 0){
			$date_peremption = NULL;
		}
		else
		{
			$date_peremption = $_POST['date_peremption'];
		}
		
		
		$req = $bdd->prepare('UPDATE alimentaire SET nom = :nom, description = :description_alimentaire, quantite = :quantite,
								marchandise_etat = :marchandise_etat, marchandise_unite = :marchandise_unite, valeur = :valeur
								type_alimentaire = :type_alimentaire, date_peremption = :date_peremption
								WHERE alimentaire_id = :alimentaire_id');
			$req->execute(array(
				'nom' => $_POST['nom'], 
				'description_alimentaire' => $_POST['description_alimentaire'],
				'quantite'=> $_POST['quantite'],
				'marchandise_etat' => $_POST['marchandise_etat'], 
				'marchandise_unite'=> $_POST['marchandise_unite'],
				'valeur' =>$_POST['valeur'], 
				'type_alimentaire'=> $_POST['type_alimentaire'], 
				'date_peremption' =>$date_peremption,
				'aliment_id' => $_POST['alimentaire_id']));	
			
			
			
		
		return response('La modification a ete fait avec succes',200);
		
	}
	
	public function cancelleraliment($id_alimentaire){ // TO-DO, FAIRE UNE TRANSACTION
		include('Connection/bdlogin.php');
		
		$req = $bdd -> prepare('UPDATE alimentaire SET marchandise_statut = :marchandise_statut WHERE alimentaire_id = :id_alimentaire');
			$req->execute(array(
			'marchandise_statut' => '7', //enlever le hard-coding
			'id_alimentaire' => $id_alimentaire
			));
			
			
			//select la bd transaction pour avoir les infos
			
				
			//$req2 = $bdd ->prepare('INSERT INTO transaction (donneur_id, marchandise_id, date_disponible, date_transaction)
			//VALUES(:donneur_id, :marchandise_id,  NOW(), NOW())');
			//	$req2->execute(array(
			//	'donneur_id'=>  $_POST['donneur_id'],
			//	'marchandise_id' => $last_index
			//	));
			
			
			return response('La cancellation a ete fait',200);
	}
	
	public function collecteralimentaire($id_alimentaire){ //TO-DO FAIRE UNE TRANSACTION
		include('Connection/bdlogin.php');
		
				$req = $bdd -> prepare('UPDATE alimentaire SET marchandise_statut = :marchandise_statut WHERE alimentaire_id = :id_alimentaire');
			$req->execute(array(
			'marchandise_statut' => '4',
			'id_alimentaire' => $id_alimentaire
			));
		
	}

	
	
}	