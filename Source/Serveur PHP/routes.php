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

Route:: get('organisme/', 'organisme@listeorganisme');

Route:: get('organisme/{id}', 'organisme@organismeid');

Route:: get('alimentaire/{id}', 'alimentaire@alimentaireid'); //retourne l'objet aliment selon l'id voulu

Route:: post('alimentaire/ajout','alimentaire@ajoutalimentaire'); // permet l'ajout d'aliment

Route:: post('alimentaire/modifier','alimentaire@modifieralimentaire'); // permet la modification d'un aliment

Route:: get('alimentaire/canceller/{id}','alimentaire@cancelleraliment'); // permet de canceller un aliment ( ne le supprime pas )

Route:: get('alimentaire/collecter/{id}','alimentaire@collecteralimentaire'); // permet de collecter un aliment

Route:: get('utilisateur/','utilisateur@listeutilisateur');

Route:: get('utilisateur/{id}','utilisateur@utilisateurid');

Route:: post('utilisateur/', 'utilisateur@enregistrement');

Route:: post('utilisateur/modifier','utilisateur@modifierutilisateur');

Route:: post('reservation/ajouter','reservation@reservationajout');

Route:: get('reservation/annuler/{marchandise_id}','reservation@annulerreservation');

Route:: get('reservation/liste/{id_organisme}','reservation@listereservation');

Route:: get('don/listedon/{id}','don@listedonid');

Route:: get('don/listedondispo','don@listedondispo');

Route:: get('carte/','carte@entreprisedon');

Route:: get('carte/{id}','carte@donid');

Route:: get('carte/reservation/{id_organisme}','carte@organismereservation');

Route:: get('transaction/{id}', 'transaction@transactions');

Route:: get('donneur_mois', 'donneurmois@donneurdumois');



	
	
	
	



