<?php

namespace App\Http\Controllers;

use App\User;
use App\Http\Controllers\don;

class don extends Controller
{

	public function listedonid($id_donneur)
	{
	
	$header = array ('Content-Type' => 'application/json; charset=UTF-8', 'charset' => 'utf-8' );
		
		include('Connection/bdlogin.php'); //inclu le fichier de connection a la basse de donné hip_dev		
		
		$req = $bdd -> prepare('SELECT ali.nom, ali.alimentaire_id,typali.description_type_aliment,
		ali.description_alimentaire, ali.quantite, marunit.description_marchandise_unite,ali.date_peremption, ali.valeur, marstat.description_marchandise_statut
		FROM transaction trx
		
		
		INNER JOIN organisme org ON org.organisme_id = trx.donneur_id
		INNER JOIN alimentaire ali ON ali.alimentaire_id = trx.marchandise_id
		INNER JOIN type_aliment typali ON typali.aliment_id = ali.type_alimentaire
		INNER JOIN marchandise_statut marstat ON marstat.statut_id = ali.marchandise_statut
		INNER JOIN marchandise_etat maretat ON maretat.etat_id = ali.marchandise_etat
		INNER JOIN marchandise_unite marunit ON marunit.unite_id = ali.marchandise_unite 

		WHERE trx.donneur_id = :id_donneur ORDER BY alimentaire_id DESC');
		
					$req->execute(array(
						'id_donneur' => $id_donneur
						));
		
		$array = array();
		
		while($resultat = $req->fetch()){
			
			if($resultat['date_peremption'] != null )
			{
			$date = date_create($resultat['date_peremption']);
			
			$date_peremption = date_format($date, DATE_ATOM);
			}
			else
			{
				$date_peremption = null;
			}	
			$arr = array( 'id' => $resultat['alimentaire_id'], 'quantite' => $resultat['quantite'], 'unite' => $resultat['description_marchandise_unite'], 
			'nom' => $resultat['nom'], 'description' => $resultat['description_alimentaire'], 'type_alimentaire' => $resultat['description_type_aliment'],
			 'date_peremption' => $date_peremption, 'valeur' => $resultat['valeur'], 'marchandise_statut' => $resultat['description_marchandise_statut']);
			
			array_push($array, $arr);
			
		}
		return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE);
	}
	
	public function listedondispo(){
			$header = array ('Content-Type' => 'application/json; charset=UTF-8', 'charset' => 'utf-8' );
		
		include('Connection/bdlogin.php'); //inclu le fichier de connection a la basse de donné hip_dev		
		
		$req = $bdd -> query('SELECT ali.nom, ali.alimentaire_id,
		ali.description_alimentaire, ali.quantite, marunit.description_marchandise_unite, ali.date_peremption, 
		ali.valeur, marstat.description_marchandise_statut, org.organisme_id, org.nom, org.telephone, org.poste, 
		adr.adresse_id, adr.no_civique, typrue.description_type_rue, adr.nom, adr.ville, adr.province, adr.code_postal, adr.pays,
		typali.description_type_aliment
		
		FROM transaction trx
		
		INNER JOIN organisme org ON org.organisme_id = trx.donneur_id
		INNER JOIN adresse adr ON adr.adresse_id = org.adresse
		INNER JOIN type_rue typrue ON typrue.type_rue_id = adr.type_rue
		INNER JOIN alimentaire ali ON ali.alimentaire_id = trx.marchandise_id
		INNER JOIN type_aliment typali ON typali.aliment_id = ali.type_alimentaire
		INNER JOIN marchandise_statut marstat ON marstat.statut_id = ali.marchandise_statut
		INNER JOIN marchandise_etat maretat ON maretat.etat_id = ali.marchandise_etat
		INNER JOIN marchandise_unite marunit ON marunit.unite_id = ali.marchandise_unite 
		
		WHERE ali.marchandise_statut = 3 
	
		ORDER BY alimentaire_id DESC');
		
		
			$array = array();
					while($resultat = $req->fetch()){
						
			if($resultat['date_peremption'] != null )
			{
			$date = date_create($resultat['date_peremption']);
			
			$date_peremption = date_format($date, DATE_ATOM);
			}
			else
			{
				$date_peremption = null;
			}	
						if($resultat['date_peremption'] != null )
			{
			$date = date_create($resultat[5]);
			
			$date_peremption = date_format($date, DATE_ATOM);
			}
			else
			{
				$date_peremption = null;
			}	
						
						$adresse = array('id' => $resultat[12], 'no_civique' => $resultat[13], 'type_rue' => $resultat[14], 'nom' => $resultat[15], 'ville' => $resultat[16], 'province' => $resultat[17], 'code_postal' => $resultat[18], 'pays' =>$resultat[19]);
						
						$organisme = array('id' => $resultat[8], 'nom' => $resultat[9], 'telephone' => $resultat[10], 'poste' => $resultat[11], 'adresse' => $adresse );
						
						$arr = array('id' => $resultat[1], 'nom' => $resultat[0], 'description' => $resultat[2], 'quantite' => $resultat[3],
										'unite' => $resultat['4'], 'date_peremption' => $date_peremption, 'marchandise_statut' => $resultat[7], 'type_alimentaire' => $resultat[20], 
											'organisme' => $organisme);
						
						array_push($array, $arr);
					}
					return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE);
		
	}
	
}	