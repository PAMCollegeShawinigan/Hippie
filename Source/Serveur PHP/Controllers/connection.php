<?php

namespace App\Http\Controllers;

use App\User;
use App\Http\Controllers\connection;

class Connection extends Controller
{

    public function connect() // permet la connection, retoure les informations de la personne qui se connecte ainsi que l'organisme rattache
    {
        require('Connection/bdlogin.php'); //inclu le fichier de connection a la basse de donné hip_dev
	
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
						
						WHERE util.courriel = :courriel 
						AND util.mot_de_passe = :mdp';
						
			$variable = array(	'courriel' => $_POST['courriel'],
								'mdp' => $_POST['mot_de_passe'])); 

				$resultat = execution($req, $variable)->fetch();
	
			if($resultat) //si il y a un resultat c'est que le nom_utilisateur et mot_de_passe sont correct
			{ 
				
		
				$adresse = array(	'id' => $resultat[0],
									'no_civique' => $resultat[1],
									'type_rue' => $resultat[2],
									'nom' => $resultat[4],
									'app' => $resultat[5],
									'ville' => $resultat[6],
									'province' => $resultat[7],
									'code_postal' => $resultat[8],
									'pays' =>$resultat[9]);

		
				$organisme = array(	'id' => $resultat[16],
									'nom' => $resultat[17],
									'telephone' => $resultat[19],
									'poste' => $resultat[20],
									'no_entreprise' => $resultat[22],
									'no_osbl' => $resultat[23],
									'adresse' => $adresse );
		
				$contact = array(	'id' => $resultat[10],
									'nom' => $resultat[11],
									'prenom' => $resultat[12],
									'telephone' => $resultat[14],
									'moyen_contact' => $resultat[15],
									'courriel' => $resultat[13],
									'organisme' => $organisme);
						//retourne 200 si le programme ne rencontre pas de probleme sinon execution() lance une exception
					return response(json_encode($contact),200);
			}
	
			else
			{ //si pas de resultat un des 2 informations fournis ne sont pas bon
		
				return response('Mauvais identifiant ou mot de passe',403);
				
			}
    }
}