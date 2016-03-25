<?php

namespace App\Http\Controllers;

use App\User;
use App\Http\Controllers\don;

class don extends Controller
{

	public function listedonid($id_donneur) // retourne les aliment qu'un donneur donne
	{
	
		require('Connection/bdlogin.php'); //inclu le fichier de connection a la basse de donné hip_dev		
		require('fonction.php');
		
			$req ='SELECT DISTINCT 	ali.nom,
									ali.alimentaire_id,
									typali.description_type_aliment,
									ali.description_alimentaire,
									ali.quantite,
									marunit.description_marchandise_unite,
									ali.date_peremption,
									ali.valeur,
									marstat.description_marchandise_statut
									
									FROM transaction trx
			
									INNER JOIN organisme org ON org.organisme_id = trx.donneur_id
									INNER JOIN alimentaire ali ON ali.alimentaire_id = trx.marchandise_id
									INNER JOIN type_aliment typali ON typali.aliment_id = ali.type_alimentaire
									INNER JOIN marchandise_statut marstat ON marstat.statut_id = ali.marchandise_statut
									INNER JOIN marchandise_etat maretat ON maretat.etat_id = ali.marchandise_etat
									INNER JOIN marchandise_unite marunit ON marunit.unite_id = ali.marchandise_unite 

									WHERE trx.donneur_id = :id_donneur ORDER BY ali.alimentaire_id DESC';
			
			$variable = array('id_donneur' => $id_donneur);
		
			$array = array();
			$req = execution($req, $variable);
			
		while($resultat = $req->fetch())
		{
				
			$date_peremption = convertirdate($resultat['date_peremption']); // convertis la date en DATE_ATOM
				
			$aliment = array( 	'id' => $resultat['alimentaire_id'],
								'quantite' => $resultat['quantite'],
								'unite' => $resultat['description_marchandise_unite'], 
								'nom' => $resultat['nom'],
								'description' => $resultat['description_alimentaire'],
								'type_alimentaire' => $resultat['description_type_aliment'],
								'date_peremption' => $date_peremption,
								'valeur' => $resultat['valeur'],
								'marchandise_statut' => $resultat['description_marchandise_statut']);
				
			array_push($array, $aliment);
				
		}
		
			// retourne 200 si le programme ne rencontre pas d'erreur sinon execution() lance une exception
		return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE);
		
	}
	
	public function listedondispo() // retourne la liste de tout les don disponible tout donneur confondu
	{
			
		require('Connection/bdlogin.php'); //inclu le fichier de connection a la basse de donné hip_dev	
		require('fonction.php');	
		
			$req = 'SELECT typali.description_type_aliment,
							ali.alimentaire_id,		
							ali.nom,
							ali.description_alimentaire,
							ali.quantite,
							marunit.description_marchandise_unite,
							ali.date_peremption,
							marstat.description_marchandise_statut,
							org.organisme_id,			
							org.nom,
							org.telephone,
							org.poste,
							adr.adresse_id,
							adr.no_civique,
							typrue.description_type_rue,
							adr.app,	
							adr.nom,
							adr.ville,
							adr.province,
							adr.code_postal,
							adr.pays,
							util.nom,
							util.prenom,
							util.courriel,
							util.telephone,
							MAX(trx.date_disponible) as date_disponible		
								
							FROM type_aliment typali
							INNER JOIN alimentaire ali ON ali.type_alimentaire = typali.aliment_id
							INNER JOIN marchandise_unite marunit ON marunit.unite_id = ali.marchandise_unite
							INNER JOIN transaction trx ON trx.marchandise_id = ali.alimentaire_id
							INNER JOIN organisme org ON org.organisme_id = trx.donneur_id
							INNER JOIN adresse adr ON adr.adresse_id = org.adresse
							INNER JOIN type_rue typrue ON typrue.type_rue_id = adr.type_rue
							INNER JOIN utilisateur util ON util.utilisateur_id = org.utilisateur_contact
							INNER JOIN marchandise_statut marstat ON marstat.statut_id = ali.marchandise_statut 		
							WHERE ali.marchandise_statut = 3
							AND  trx.marchandise_id in (SELECT DISTINCT marchandise_id FROM transaction)
							AND   trx.date_reservation IS NULL  
							GROUP BY typali.description_type_aliment,
								ali.nom,
								ali.description_alimentaire,
								ali.quantite,
								marunit.description_marchandise_unite,
								ali.date_peremption,
								org.nom,
								adr.adresse_id,
								adr.no_civique,
								typrue.description_type_rue,
								adr.nom,
								adr.ville,
								adr.province,
								adr.code_postal,
								adr.pays,
								org.telephone,
								org.poste,
								util.prenom,
								util.nom,
								util.courriel,
								ali.alimentaire_id,
								marstat.description_marchandise_statut,
								org.organisme_id,
								adr.app';
		
			$req = requete($req); // execute la requete
		
				$array = array();
				while($resultat = $req->fetch())
				{
						$adresse = array(	'id' => $resultat[12],
											'no_civique' => $resultat[13],
											'type_rue' => $resultat[14],
											'app' => $resultat[15],
											'nom' => $resultat[16], 
											'ville' => $resultat[17],
											'province' => $resultat[18],
											'code_postal' => $resultat[19], 
											'pays' =>$resultat[20]);
						
						$contact = array(	'nom'=> $resultat[21],
											'prenom' => $resultat[22],
											'courriel' => $resultat[23],
											'telephone' => $resultat[24] );
						
						$organisme = array(	'id' => $resultat[8],
											'nom' => $resultat[9],
											'telephone' => $resultat[10],
											'poste' => $resultat[11],
											'adresse' => $adresse,
											'contact' => $contact );
						
						$aliment = array(	'id' => $resultat[1],
											'nom' => $resultat[2],
											'description' => $resultat[3],
											'quantite' => $resultat[4],
											'unite' => $resultat[5],
											'date_peremption' => convertirdate($resultat[6]),
											'marchandise_statut' => $resultat[7],
											'type_alimentaire' => $resultat[0], 
											'organisme' => $organisme);
						
						array_push($array, $aliment);
						
				}
				// retourne 200 si le programme ne rencontre pas d'erreur sinon execution() lance une exception
			return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE);
		
	}
	
}	