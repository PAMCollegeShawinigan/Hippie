<?php

namespace App\Http\Controllers;

use App\User;
use App\Http\Controllers\utilisateur;

class utilisateur extends Controller
{

	public function listeutilisateur()
	{
			$header = array ('Content-Type' => 'application/json; charset=UTF-8','charset' => 'utf-8');
			
			include('Connection/bdlogin.php'); //inclu le fichier de connection a la basse de donné hip_dev
			
			$req = $bdd ->query('SELECT utilisateur_id, nom, prenom FROM utilisateur');
			
			$array = array();
			while($resultat = $req->fetch()){
				$arr = array('id'=> $resultat['utilisateur_id'], 'nom' => $resultat['nom'], 'prenom' => $resultat['prenom']);
				
				array_push($array, $arr);
			}
			
			return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE);
			
			
	}
	
	public function utilisateurid($id)
	{
		$header = array ('Content-Type' => 'application/json; charset=UTF-8','charset' => 'utf-8');
			
			include('Connection/bdlogin.php'); //inclu le fichier de connection a la basse de donné hip_dev
			
			$req = $bdd ->prepare('SELECT nom, prenom, courriel, telephone, organisme_id FROM utilisateur WHERE utilisateur_id = :id');
			
			$req->execute(array(
						'id' => $id
						));
						
						$resultat = $req->fetch();
						
						
						if($resultat['organisme_id'] != null)
							{
								$req2 = $bdd -> prepare('SELECT nom FROM organisme WHERE organisme_id = :id');
								$req2->execute(array(
									'id' => $resultat['organisme_id']
												));
												
												$organisme = $req2->fetch();
												
											$org = array('id' => $resultat['organisme_id'], 'nom' => $organisme['nom'])	;
							
							}
						else
							{
								$org = null;
							
							}
						$array = array('nom' => $resultat['nom'], 'prenom' => $resultat['prenom'], 'courriel' => $resultat['courriel'], 'telephone' => $resultat['telephone'], 'organisme' => $org );
						
						
				return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE);
		
	}
	
	public function enregistrement(){
		
		include('Connection/bdlogin.php'); //inclu le fichier de connection a la basse de donné hip_dev
		
		$req = $bdd ->prepare('SELECT utilisateur_id FROM utilisateur WHERE courriel = :courriel');
		$req->execute(array(
						'courriel' => $_POST['courriel']
						));
				$resultat = $req->fetch();

					if(!$resultat)
						{
							$req2 = $bdd -> prepare('INSERT INTO utilisateur (nom, prenom, courriel, mot_de_passe, moyen_contact, organisme_id ) VALUES (:nom, :prenom,:courriel ,:mot_de_passe, :moyen_contact, :organisme_id)');
							
								$req2->execute(array(
								'nom' => $_POST['nom'],
								'prenom' => $_POST['prenom'],
								'courriel' => $_POST['courriel'],
								'mot_de_passe' => $_POST['mot_de_passe'],
								'moyen_contact' => '1', // par defaut composante a gérer plus tard
								'organisme_id' => $_POST['organisme_id']
								));
							
							$last_index = $bdd->exec('SELECT LAST_INSERT_ID() FROM utilisateur');
							
							$arr = array('id' => $last_index);
							
							return response() -> json($arr,200);
						}
					else
						{
						return response('Le courriel est deja utilise', 409);
						}
						
						
		
	}
	
}	