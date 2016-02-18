<?php

namespace App\Http\Controllers;

	use App\User;
	use App\Http\Controllers\transaction;

		class Transaction extends Controller
			{
				
				public function transactions($id){
				require('function.php');
				

					include('Connection/bdlogin.php');
					
					$req = $bdd->prepare('SELECT orgd.organisme_id,
													  orgd.nom,
													  orgd.telephone,
													  orgd.poste,
													  orgd.no_entreprise,
													  orgd.no_osbl,
													  orgr.organisme_id,
													  orgr.nom,
													  orgr.telephone,
													  orgr.poste,
													  orgr.no_entreprise,
													  orgr.no_osbl,
													  trx.transaction_id,
													  trx.receveur_id,
													  trx.donneur_id,
													  trx.marchandise_id,
													  trx.date_collecte,
													  trx.date_reservation,
													  trx.date_disponible,
													  trx.date_transaction,
													  ali.valeur,
													  typali.description_type_aliment,
													  adrd.adresse_id,
													  adrd.no_civique,
													  typrued.description_type_rue,
													  adrd.nom,
													  adrd.app,
													  adrd.ville,
													  adrd.province,
													  adrd.code_postal,
													  adrd.pays,
													  adrr.adresse_id,
													  adrr.no_civique,
													  typruer.description_type_rue,													  
													  adrr.nom,
													  adrr.app,
													  adrr.ville,
													  adrr.province,
													  adrr.code_postal,
													  adrr.pays,
													  ali.nom,
													  ali.description_alimentaire,
													  ali.quantite,
													  maru.description_marchandise_unite
													  
													  
													  
													   
													FROM transaction trx
													INNER JOIN organisme orgr ON orgr.organisme_id = trx.receveur_id 
													INNER JOIN organisme orgd ON orgd.organisme_id = trx.donneur_id
													INNER JOIN alimentaire ali ON ali.alimentaire_id = trx.marchandise_id
													INNER JOIN type_aliment typali ON typali.aliment_id = ali.type_alimentaire
													INNER JOIN adresse adrr ON adrr.adresse_id = orgr.adresse
													INNER JOIN adresse adrd ON adrd.adresse_id = orgd.adresse
													INNER JOIN type_rue typruer ON typruer.type_rue_id = adrr.type_rue
													INNER JOIN type_rue typrued ON typrued.type_rue_id = adrd.type_rue
													INNER JOIN marchandise_unite maru ON maru.unite_id = ali.marchandise_unite
													

													WHERE trx.date_collecte IS NOT NULL

													AND  trx.receveur_id = :id1 OR trx.donneur_id = :id2
													AND  trx.date_collecte BETWEEN :date_debut AND :date_fin');
													
													//$date_du = date_create($_GET['date_du']);
													//$date_au = date_create($_GET['date_au']);
													
													
													$req->execute(array(
															'id1' => $id,
															'id2' => $id,
															'date_fin' => $_GET['date_au'],
															'date_debut' => $_GET['date_du']
															));
															
															
												$array = array();			
											while($resultat = $req->fetch()){
												$adresse_donneur = array('id' => $resultat[22], 'no_civique' => $resultat[23], 'type_rue' => $resultat[24], 'app' => $resultat[26], 'nom' => $resultat[25], 'ville' => $resultat[27], 'province' => $resultat[28], 'code_postal' => $resultat[29], 'pays' =>$resultat[30]);
												
												$adresse_receveur = array('id' => $resultat[31], 'no_civique' => $resultat[32], 'type_rue' => $resultat[33], 'app' => $resultat[35], 'nom' => $resultat[34], 'ville' => $resultat[36], 'province' => $resultat[37], 'code_postal' => $resultat[38], 'pays' =>$resultat[39]);
												
												$organisme_donneur = array('id' => $resultat[0], 'nom' => $resultat[1], 'telephone' => $resultat[2], 'poste' => $resultat[3],'no_entreprise' => $resultat[4], 'no_osbl' => $resultat[5], 'adresse'=> $adresse_donneur);
												
												$organisme_receveur = array('id' => $resultat[6], 'nom' => $resultat[7], 'telephone' => $resultat[8], 'poste' => $resultat[9],'no_entreprise' => $resultat[10], 'no_osbl' => $resultat[11], 'adresse'=> $adresse_receveur);
												
												$alimentaire = array('valeur' => $resultat[20], 'type_alimentaire' => $resultat[21], 'nom' => $resultat[40], 'description' => $resultat[41], 'quantite' => $resultat[42], 'unite' => $resultat[43]);
												
												$date_collecte = convertirdate($resultat[16]);
												
												$date_reservation = convertirdate($resultat[17]);
												
												$date_disponible = convertirdate($resultat[18]);
												
												$date_transaction = convertirdate($resultat[19]);
												
												$transaction = array('id' => $resultat[12], 'receveur' => $organisme_receveur, 'donneur' => $organisme_donneur, 'alimentaire' => $alimentaire, 'date_collecte' =>$date_collecte, 'date_reservation' => $date_reservation, 'date_disponible' => $date_disponible, 'date_transaction' => $date_transaction );
												
												array_push($array, $transaction);
											
											}		
										return response() -> json($array,200,$header,JSON_UNESCAPED_UNICODE);			
				
				}
				
			}

?>