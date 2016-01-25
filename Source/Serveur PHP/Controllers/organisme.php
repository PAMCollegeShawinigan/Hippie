<?php

namespace App\Http\Controllers;

use App\User;
use App\Http\Controllers\organisme;

	class organisme extends Controller
	{
		
		public function nomid(){
			$header = array (
                'Content-Type' => 'application/json; charset=UTF-8',
                'charset' => 'utf-8'
            );
			
			include('Connection/bdlogin.php');
			
			
			$req = $bdd->query('SELECT organisme_id, nom FROM organisme'); // Requête de l'id_organisme et du nom de l'organisme

					$array = array();
					while($reponse = $req->fetch()){
						
						$arr = array('id' => $reponse['organisme_id'], 'nom' => $reponse['nom']);
						
						array_push($array, $arr);

					}
					
					return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE);
			
		}
		
		public function organismeid($id){
					
			$header = array (
                'Content-Type' => 'application/json; charset=UTF-8',
                'charset' => 'utf-8'
            );
			
			include('Connection/bdlogin.php');
			
			
			// Requête pour obtenir les informations du contact de l'organisme
			$req1 = $bdd->prepare('Select * From organisme WHERE organisme_id = :id'); // Requête de tout les champs d'organisme
				$req1->execute(array(
					'id' => $id));
					
					$organisme = $req1->fetch();
					
				$req2 = $bdd->prepare('SELECT * FROM utilisateur WHERE utilisateur_id = :id'); // requetes de tout les champs relié a l'utilisateur dans organisme
				$req2->execute(array(
					'id' => $organisme['utilisateur_contact']));
					
					$utilisateur = $req2->fetch();

			
				$req3 = $bdd->prepare('SELECT * FROM adresse INNER JOIN type_rue on adresse.type_rue = type_rue.type_rue_id WHERE adresse_id = :id'); // Requête de tout les champs d'adresse avec jointure a type_rue relié a l'organisme
				$req3->execute(array(
					'id' => $organisme['adresse']));
					
					$adresse = $req3->fetch();
					
					
					$arrAdresse = array('no_civique' => $adresse['no_civique'], 'nom' => $adresse['nom'], 
										'type_rue' => $adresse['description_type_rue'], 'app' => $adresse['app'], 'ville' => $adresse['ville'],
										'province' => $adresse['province'], 'code_postal' => $adresse['code_postal'], 'pays' => $adresse['pays']);
					$arrUtilisateur = array('utilisateur_id' => $utilisateur['utilisateur_id'],'nom' => $utilisateur['nom'], 
											'prenom' => $utilisateur['prenom'], 'telephone' => $utilisateur['telephone'], 'moyen_contact' => $utilisateur['moyen_contact'],
											'courriel' => $utilisateur['courriel'], 'organisme_id' => $utilisateur['organisme_id']);
					$arrOrganisme = array('nom' => $organisme['nom'], 'telephone' => $organisme['telephone'], 'poste' => $organisme['poste'],
											'no_entreprise' => $organisme['no_entreprise'], 'no_osbl' => $organisme['no_osbl'], 'adresse'=> $arrAdresse, 'utilisateur' => $arrUtilisateur);
											
											//array_push($arrOrganisme, $arrAdresse);
											//array_push($arrOrganisme, $arrUtilisateur);
											
											return response() -> json($arrOrganisme,200,$header,JSON_UNESCAPED_UNICODE);
					
					
					
		}
		public function listeorganisme(){
			$header = array (
                'Content-Type' => 'application/json; charset=UTF-8',
                'charset' => 'utf-8'
            );
			
			include('Connection/bdlogin.php');
			
			
			$req = $bdd->query('SELECT  org.nom, 
										  adr.no_civique, 
										  typrue.description_type_rue, 
										  adr.nom, 
										  adr.ville, 
										  adr.province, 
										  adr.code_postal, 
										  adr.pays,
										  util.prenom,
										  util.nom,
										  org.telephone,
										  org.poste,
										  util.courriel
										  
										FROM organisme org
										INNER JOIN adresse adr ON adr.adresse_id = org.adresse
										INNER JOIN type_rue typrue ON typrue.type_rue_id = adr.type_rue
										INNER JOIN utilisateur util ON util.utilisateur_id = org.utilisateur_contact
										WHERE org.no_osbl IS NOT NULL;'); 

					$array = array();
					while($reponse = $req->fetch()){
						
						$arr = array('id' => $reponse['organisme_id'], 'nom' => $reponse['nom']);
						
						array_push($array, $arr);

					}
					
					return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE);
			
		}
		}
 
	}