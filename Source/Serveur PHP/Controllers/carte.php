<?php

namespace App\Http\Controllers;

use App\User;
use App\Http\Controllers\carte;

class carte extends Controller
{

	public function entreprisedon() // fait afficher la liste des entreprises qui ont des aliments a donner
	{
		$header = array ('Content-Type' => 'application/json; charset=UTF-8', 'charset' => 'utf-8' );
		include('Connection/bdlogin.php'); //inclu le fichier de connection a la basse de donné hip_dev
		
			
			$req = $bdd->query('SELECT DISTINCT org.organisme_id, 
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
											
											WHERE ali.marchandise_statut = 3 AND (ali.date_peremption > current_date OR ali.date_peremption IS NULL)
												');
			
			$array = array();
			
			
			
			while($resultat = $req->fetch()) // s'execute dans que la requete retourne des resultats
						{
							//création d'un array d'objet adresse
							$adresse = array('no_civique' => $resultat[2], 
									'nom' => $resultat[3], 'type_rue' => $resultat[4], 'ville' => $resultat[5],
										'province' => $resultat[6], 'code_postal' => $resultat[7], 'pays' => $resultat[8], 'id' => $resultat[9]);
						//création d'un array d'objet aliment contenant adresse
						$arr = array('id' =>$resultat[0], 'nom' => $resultat[1], 'telephone' => $resultat[10], 'poste' => $resultat[11], 'adresse' => $adresse );
						
						array_push($array, $arr);
						}
			
		return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE);
			
			
	}
	
	
	public function donid($id_donneur){ // affichage des aliments disponible par id_entreprise
		$header = array ('Content-Type' => 'application/json; charset=UTF-8', 'charset' => 'utf-8' );
		
		include('Connection/bdlogin.php'); //inclu le fichier de connection a la basse de donné hip_dev		
		
		$req = $bdd -> prepare('SELECT DISTINCT ali.nom,
										ali.alimentaire_id,
										ali.description_alimentaire, 
										ali.quantite, 
										marunit.description_marchandise_unite,
										ali.date_peremption,
										ali.valeur
		
								FROM transaction trx
							
							INNER JOIN organisme org ON org.organisme_id = trx.donneur_id
							INNER JOIN alimentaire ali ON ali.alimentaire_id = trx.marchandise_id
							INNER JOIN type_aliment typali ON typali.aliment_id = ali.type_alimentaire
							INNER JOIN marchandise_unite marunit ON marunit.unite_id = ali.marchandise_unite

							WHERE (ali.marchandise_statut = 3 )AND trx.donneur_id = :id_donneur AND (ali.date_peremption > current_date OR ali.date_peremption IS NULL) ORDER BY aliment_id DESC');
		
					$req->execute(array(
						'id_donneur' => $id_donneur //passe le parametre a la requete MYSQL
						));
		
		$array = array(); // array vide remplis dans la boucle
		
		while($resultat = $req->fetch()){ // s'execute dans que la requete retourne des resultats
			
			if($resultat['date_peremption'] != null ) //gere la date
			{
			$date = date_create($resultat['date_peremption']);
			
			$date_peremption = date_format($date, DATE_ATOM); // transforme la date en format ATOM géré par l'api
			}
			else
			{
				$date_peremption = null;
			}	
			
			$arr = array( 'id' => $resultat['alimentaire_id'], 'quantite' => $resultat['quantite'], 'unite' => $resultat['description_marchandise_unite'], 
			'nom' => $resultat['nom'], 'description' => $resultat['description_alimentaire'], 
			 'date_peremption' => $date_peremption, 'valeur' => $resultat['valeur']);
			
			array_push($array, $arr);
			
		}
		return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE); // transforme l'array en JSon
	}
}