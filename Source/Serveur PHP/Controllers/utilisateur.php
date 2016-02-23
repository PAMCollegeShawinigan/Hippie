<?php

namespace App\Http\Controllers;

use App\User;
use App\Http\Controllers\utilisateur;

class utilisateur extends Controller
{

	public function listeutilisateur() // retourne la liste des utilisateurs
	{
			require('Connection/bdlogin.php'); //inclu le fichier de connection a la basse de donné hip_dev
			
			$req = 'SELECT 	utilisateur_id,
							nom,
							prenom 
							
							FROM utilisateur';
							
			$req = requete($req);
			
			$array = array();
			while($resultat = $req->fetch()){
				
				$arr = array('id'=> $resultat['utilisateur_id'],
							 'nom' => $resultat['nom'],
							 'prenom' => $resultat['prenom']);
				
				array_push($array, $arr);
			}
			// retourne 200 si le programme ne rencontre pas d'erreur sinon la fonction execution lance une exeption
			return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE);
			
			
	}
	
	public function utilisateurid($id) // retourne un utilisateur selon son id
	{
			require('Connection/bdlogin.php'); //inclu le fichier de connection a la basse de donné hip_dev
			
			$req = 'SELECT nom, prenom, courriel, telephone, organisme_id FROM utilisateur WHERE utilisateur_id = :id';
			
			$variable = array('id' => $id);
						
						$resultat = execution($req, $variable)->fetch();
						
						
						if($resultat['organisme_id'] != null)
							{
								$req = 'SELECT nom 
										FROM organisme 
										WHERE organisme_id = :id';
								
								$variable = array('id' => $resultat['organisme_id']);
												
								$organisme = execution($req, $variable)->fetch();
												
								$org = array(	'id' => $resultat['organisme_id'],
												'nom' => $organisme['nom'])	;
							
							}
						else
							{
								$org = null;
							
							}
						$array = array(	'nom' => $resultat['nom'],
										'prenom' => $resultat['prenom'],
										'courriel' => $resultat['courriel'],
										'telephone' => $resultat['telephone'],
										'organisme' => $org );
						
						
			return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE);
		
	}
	
	public function enregistrement() // permet l'enregistrement d'un nouvel utilisateur
	{
		
		require('Connection/bdlogin.php'); //inclu le fichier de connection a la basse de donné hip_dev
		
		$req = 'SELECT utilisateur_id
					FROM utilisateur 
					WHERE courriel = :courriel';
					
		$variable = array('courriel' => $_POST['courriel']);
		

		if($resultat = execution($req, $variable)->fetch()) // verifi si le courriel est deja utilisé si oui retourne un erreur
		{

			return response('Le courriel est deja utilise', 409);
				
		}			
		
		$req = 'INSERT INTO utilisateur (nom, prenom, courriel, mot_de_passe, moyen_contact, organisme_id ) 
								VALUES (:nom, :prenom, :courriel, :mot_de_passe, :moyen_contact, :organisme_id)';
				
		$variable = array(	'nom' => $_POST['nom'],
							'prenom' => $_POST['prenom'],
							'courriel' => $_POST['courriel'],
							'mot_de_passe' => $_POST['mot_de_passe'],
							'moyen_contact' => '1', // par defaut composante a gérer plus tard
							'organisme_id' => $_POST['organisme_id']);
							
		execution($req, $variable);					
				
			return response() -> json($arr,200);
				
	}
	
	public function modifierutilisateur(){
		
		// mettre la requete et la fonction ici
		
	}
	
	
}	