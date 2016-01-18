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
// TO_DO modifier le hard-coding ( 3 = disponible)

Route:: post('/', 'connection@connect');

Route:: get('liste/{type}', 'liste@liste');

Route:: get('adresse/{id}', 'adresse@adresse');

Route:: get('organisme/', 'organisme@nomid');

Route:: get('organisme/{id}', 'organisme@organismeid');

Route:: get('alimentaire/{id}', 'alimentaire@alimentaireid');

Route:: post('alimentaire/ajout','alimentaire@ajoutalimentaire');

Route:: post('alimentaire/modifier','alimentaire@modifieralimentaire');

Route:: get('alimentaire/canceller/{id}','alimentaire@cancelleraliment');

Route:: get('alimentaire/collecter/{id}','alimentaire@collecteralimentaire');

Route:: get('utilisateur/','utilisateur@listeutilisateur');

Route:: get('utilisateur/{id}','utilisateur@utilisateurid');

Route:: post('utilisateur/', 'utilisateur@enregistrement');

Route:: post('reservation/ajouter','reservation@reservationajout');

Route:: get('reservation/annuler/{marchandise_id}','reservation@annulerreservation');

Route:: get('don/{id}','don@listedonid');

Route:: get('carte/','carte@entreprisedon');

Route:: get('carte/{id}','carte@donid');



	
	
	
	



