<?php

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It's a breeze. Simply tell Laravel the URIs it should respond to
| and give it the controller to call when that URI is requested.
|
*/


Route:: post('/', 'connection@connect'); // permet la connection et retourne les informations de l'utilisateur et l'organisme auquel il est relié

Route:: get('liste/{type}', 'liste@liste'); // retourne la liste de certaine caractéristiques selon le type (alimentaire, rue, etat, etc..)

Route:: get('adresse/{id}', 'adresse@adresse'); // Retourne l'adresse selon l'id (NON-UTILISE)

Route:: get('organisme/', 'organisme@listeorganisme'); // retourne la liste des organismes

Route:: get('organisme/{id}', 'organisme@organismeid'); // retourne les informations d'un organisme selon l'id

Route:: get('alimentaire/{id}', 'alimentaire@alimentaireid'); //retourne l'objet aliment selon l'id voulu

Route:: post('alimentaire/ajout','alimentaire@ajoutalimentaire'); // permet l'ajout d'aliment

Route:: post('alimentaire/modifier','alimentaire@modifieralimentaire'); // permet la modification d'un aliment

Route:: get('alimentaire/canceller/{id}','alimentaire@cancelleraliment'); // permet de canceller un aliment ( ne le supprime pas )

Route:: get('alimentaire/collecter/{id}','alimentaire@collecteralimentaire'); // permet de collecter un aliment

Route:: get('utilisateur/','utilisateur@listeutilisateur'); // retourne la liste des utilisateurs

Route:: get('utilisateur/{id}','utilisateur@utilisateurid'); // retourne un utilisateur selon son id

Route:: post('utilisateur/', 'utilisateur@enregistrement'); // permet l'enregistrement d'un utilisateur

Route:: post('utilisateur/modifier','utilisateur@modifierutilisateur'); // route en place mais vide

Route:: post('reservation/ajouter','reservation@reservationajout'); // permet de faire une reservation

Route:: get('reservation/annuler/{marchandise_id}','reservation@annulerreservation'); // permet d'annuler une reservation

Route:: get('reservation/liste/{id_organisme}','reservation@listereservation'); // retourne la liste des reservation incluant l'aliment et l'adresse

Route:: get('don/listedon/{id}','don@listedonid'); // retourne les aliment qu'un donneur donne

Route:: get('don/listedondispo','don@listedondispo'); // retourne la liste de tout les don disponible tout donneur confondu

Route:: get('carte/','carte@entreprisedon'); // fait afficher la liste des entreprises qui ont des aliments a donner

Route:: get('carte/{id}','carte@donid'); // affichage des aliments disponible par id_entreprise

Route:: get('carte/reservation/{id_organisme}','carte@organismereservation'); // retourne les organismes pour lesquels l'utilisateur a des reservations

Route:: get('transaction/{id}', 'transaction@transactions'); // retourne toutes les transactions concernant l'utilisateur autant en tant que donneur que receveur

Route:: get('donneur_mois', 'donneurmois@donneurdumois'); // retourne le ou les donneurs du mois selon les date debut et date fin



	
	
	
	



