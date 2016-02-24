<?php

namespace App\Http\Controllers;

use App\User;
use App\Http\Controllers\carte;

class carte extends Controller
{

	public function entreprisedon() // fait afficher la liste des entreprises qui ont des aliments a donner
	{
		
		require('Connection/bdlogin.php'); //inclu le fichier de connection a la basse de donné hip_dev
		
			
			$req = 'SELECT DISTINCT org.organisme_id, 
									org.nom,
									adr.no_civique,
									adr.nom,
									typrue.description_type_rue,
									adr.ville,
									adr.province,
									adr.code_postal,
									adr.pays,
									adr.adresse_id,
									org.telephone,
									org.poste
									
									FROM alimentaire ali
											
									INNER JOIN marchandise_statut marstat ON marstat.statut_id = ali.marchandise_statut
									INNER JOIN transaction trx ON trx.marchandise_id = ali.alimentaire_id 
									INNER JOIN organisme org ON org.organisme_id = trx.donneur_id
									INNER JOIN adresse adr ON adr.adresse_id = org.adresse
									INNER JOIN type_rue typrue ON adr.type_rue = typrue.type_rue_id
											
									WHERE ali.marchandise_statut = 3 AND (ali.date_peremption > CURRENT_DATE OR ali.date_peremption IS NULL)';
												
			
			$array = array();
			
			$requete = requete($req);
			while($resultat = $requete->fetch()) // s'execute dans que la requete retourne des resultats
			{ 
						
							//création d'un array d'objet adresse
						$adresse = array(	'no_civique' => $resultat[2], 
											'nom' => $resultat[3],
											'type_rue' => $resultat[4],
											'ville' => $resultat[5],
											'province' => $resultat[6],
											'code_postal' => $resultat[7],
											'pays' => $resultat[8],
											'id' => $resultat[9]);
											
						//création d'un array d'objet aliment contenant adresse
						$arr = array(	'id' =>$resultat[0],
										'nom' => $resultat[1],
										'telephone' => $resultat[10],
										'poste' => $resultat[11],
										'adresse' => $adresse );
						
						array_push($array, $arr);
						
			}
			
		//retourne 200 si le programme ne rencontre pas d'erreur sinon requete() lance une exception
		return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE);
			
			
	}
	
	
	public function donid($id_donneur){ // affichage des aliments disponible par id_entreprise
		
		require('Connection/bdlogin.php'); //inclu le fichier de connection a la basse de donné hip_dev		
		require('fonction.php');
			$req = 'SELECT DISTINCT ali.nom,
								ali.alimentaire_id,
								ali.description_alimentaire, 
								ali.quantite, 
								marunit.description_marchandise_unite,
								ali.date_peremption,
								ali.valeur,
								typali.description_type_aliment
		
								FROM transaction trx
							
								INNER JOIN organisme org ON org.organisme_id = trx.donneur_id
								INNER JOIN alimentaire ali ON ali.alimentaire_id = trx.marchandise_id
								INNER JOIN type_aliment typali ON typali.aliment_id = ali.type_alimentaire
								INNER JOIN marchandise_unite marunit ON marunit.unite_id = ali.marchandise_unite

								WHERE (ali.marchandise_statut = 3 )
								AND trx.donneur_id = :id_donneur 
								AND (ali.date_peremption > CURRENT_DATE OR ali.date_peremption IS NULL)
								ORDER BY ali.aliment_id DESC';
			
			$variable = array('id_donneur' => $id_donneur );
						
		
		
			$array = array(); // array vide remplis dans la boucle
		
			$req = execution($req, $variable);
			while($resultat = $req->fetch()){ 
			
					$date_peremption = convertirdate($resultat['date_peremption']);	
			
					$arr = array( 	'id' => $resultat['alimentaire_id'],
									'quantite' => $resultat['quantite'], 
									'unite' => $resultat['description_marchandise_unite'], 
									'nom' => $resultat['nom'],
									'description' => $resultat['description_alimentaire'], 
									'date_peremption' => $date_peremption,
									'valeur' => $resultat['valeur'],
									'type_alimentaire' => $resultat['description_type_aliment']);
			
					array_push($array, $arr);
			
			}
			//retourne 200 su le programme ne rencontre pas d'erreur sinon $execution lance une exception
		return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE); // transforme l'array en JSon
	}
	
	public function organismereservation($id_organisme){ // retourne les organismes pour lesquels l'utilisateur a des reservations
		require('Connection/bdlogin.php'); //inclu le fichier de connection a la basse de donné hip_dev
			 
				$req = 'SELECT
								org.nom,
								adr.adresse_id,
								adr.no_civique,
								typrue.description_type_rue,
								adr.nom,
								adr.ville,
								adr.province,
								adr.code_postal,
								adr.pays,
								adr.app,
								org.telephone,
								org.poste,
								util.prenom,
								util.nom,
								util.courriel,
								util.telephone
										 
								FROM transaction trx
								
								INNER JOIN organisme org ON org.organisme_id = trx.donneur_id
								INNER JOIN alimentaire ali ON ali.alimentaire_id = trx.marchandise_id
								INNER JOIN adresse adr ON adr.adresse_id = org.adresse
								INNER JOIN type_rue typrue ON typrue.type_rue_id = adr.type_rue
								INNER JOIN utilisateur util ON util.utilisateur_id = org.utilisateur_contact
								WHERE ali.marchandise_statut = 2
								AND trx.receveur_id = :receveur_id
								AND (trx.date_reservation, trx.marchandise_id) in
																(SELECT MAX(trx.date_reservation) as date_réservation,
																		trx.marchandise_id  
																 FROM transaction trx
																 WHERE trx.marchandise_id in (SELECT DISTINCT marchandise_id FROM transaction)
																 AND trx.date_reservation IS NOT NULL  
																 GROUP BY trx.marchandise_id)';	

											
					$variable = array('receveur_id' => $id_organisme);
						
				$array = array();
				$req = execution($req, $variable);
					while($resultat = $req->fetch()){

							$adresse = array(	'id' => $resultat[1],
												'no_civique' => $resultat[2],
												'type_rue' => $resultat[3],
												'nom' => $resultat[4],
												'app' => $resultat[9],
												'ville' => $resultat[5],
												'province' => $resultat[6],
												'code_postal' => $resultat[7],
												'pays' =>$resultat[8]);
							
							$contact = array(	'nom'=> $resultat[13],
												'prenom' => $resultat[12],
												'courriel' => $resultat[14],
												'telephone' => $resultat[15] );
							
							$organisme = array(	'nom' => $resultat[0],
												'telephone' => $resultat[10],
												'poste' => $resultat[11],
												'adresse' => $adresse,
												'contact' => $contact );
						
						array_push($array, $organisme);
						
				}
				//retourne 200 su le programme ne rencontre pas d'erreur sinon $execution lance une exception
			return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE);
		
	}
}