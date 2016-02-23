<?php

namespace App\Http\Controllers;

use App\User;
use App\Http\Controllers\organisme;

class organisme extends Controller
{
		
	public function organismeid($id) // retourne les informations d'un organisme selon son id
	{ 
		
		require('Connection/bdlogin.php');
   
   
			   // Requête pour obtenir les informations du contact de l'organisme
				$req = 'SELECT adr.adresse_id, 
					 adr.no_civique, 
					 typrue.description_type_rue,
					 adr.type_rue,
					 adr.nom,
					 adr.app,
					 adr.ville,
					 adr.province,
					 adr.code_postal,
					 adr.pays,
					 util.utilisateur_id,
					 util.nom,
					 util.prenom,
					 util.courriel,
					 util.telephone,
					 util.moyen_contact,
					 org.organisme_id,
					 org.nom,
					 org.adresse,
					 org.telephone,
					 org.poste,
					 org.utilisateur_contact,
					 org.no_entreprise,
					 org.no_osbl
					
				  FROM organisme org
				  
				  INNER JOIN utilisateur util ON util.organisme_id = org.organisme_id
				  INNER JOIN adresse adr ON adr.adresse_id = org.adresse
				  INNER JOIN type_rue typrue ON typrue.type_rue_id = adr.type_rue
				  
				  WHERE org.organisme_id = :id';
				  
				$variable = array('id' => $id);
				 
				 $resultat = execution($req, $variable)->fetch();
				 
				 
				 $arrAdresse = array( 'no_civique' => $resultat[1], 
				   'nom' => $resultat[4], 
				   'type_rue' => $resultat[2],
				   'app' => $resultat[5], 
				   'ville' => $resultat[6],
				   'province' => $resultat[7], 
				   'code_postal' => $resultat[8],
				   'pays' => $resultat[9]);
				   
				 $arrUtilisateur = array( 'utilisateur_id' => $resultat[10],
					'nom' => $resultat[11], 
					'prenom' => $resultat[12],
					'courriel' => $resultat[13],
					'telephone' => $resultat[14],
					'moyen_contact' => $resultat[15],
					'organisme_id' => $resultat[16]);
					
				 $arrOrganisme = array( 'nom' => $resultat[17],
					'adresse'=> $arrAdresse,
				   'telephone' => $resultat[19],
				   'poste' => $resultat[20],
				   'utilisateur' => $arrUtilisateur,
				   'no_entreprise' => $resultat[22], 
				   'no_osbl' => $resultat[23]);

					   
			 return response() -> json($arrOrganisme,200,$header,JSON_UNESCAPED_UNICODE);
				 
     
     
	}
				
	
	
	public function listeorganisme() // retourne la liste des organismes
	{	
		require('Connection/bdlogin.php');
		
		
			$req = 'SELECT  org.nom, 
							adr.no_civique, 
							typrue.description_type_rue, 
							adr.nom, 
							adr.ville, 
							adr.province, 
							adr.code_postal, 
							adr.pays,
							util.prenom,
							util.nom,
							util.courriel,
							util.telephone,
							org.telephone,
							org.poste
									  
									  
							FROM organisme org
										
							INNER JOIN adresse adr ON adr.adresse_id = org.adresse
							INNER JOIN type_rue typrue ON typrue.type_rue_id = adr.type_rue
							INNER JOIN utilisateur util ON util.utilisateur_id = org.utilisateur_contact
										
							WHERE org.no_osbl IS NOT NULL';

			$req = requete($req);							

				$array = array();
				while($resultat = $req->fetch())
				{
					$adresse = array(	'no_civique' => $resultat[1],
										'type_rue' => $resultat[2],
										'nom' => $resultat[3],
										'ville' => $resultat[4],
										'province' => $resultat[5],
										'code_postal' => $resultat[6],
										'pays' =>$resultat[7]);
					
					$contact = array(	'prenom'=> $resultat[8],
										'nom' => $resultat[9],
										'courriel' => $resultat[10],
										'telephone' => $resultat[11]);
					
					
					$organisme = array(	'telephone' => $resultat[12],
										'nom' => $resultat[0],
										'poste' => $resultat[13],
										'adresse' => $adresse,
										'contact' => $contact);
					
					array_push($array, $organisme);

				}
				
		return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE);
		
	}
		
}