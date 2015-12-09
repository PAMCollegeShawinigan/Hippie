package com.pam.codenamehippie.Controleur;

import com.pam.codenamehippie.modele.MarchandiseModele;
import com.pam.codenamehippie.modele.OrganismeModele;
import com.pam.codenamehippie.modele.TransactionModele;

public class ActionTransaction {

    /**
     * Méthode pour ajouter un nouveau produit à la base de données. La date du jour
     * sera considérée comme date de disponibilité lors de la requête au serveur.
     *
     * @param transaction
     *         instance de transaction
     * @param donneur
     *         instance de organisme donneur
     * @param marchandise
     *         instance de marchandise
     *
     * @return objet transaction avec les informations à envoyer par une requête SQL
     */
    public TransactionModele ajouterMarchandise(TransactionModele transaction,
                                                OrganismeModele donneur,
                                                MarchandiseModele marchandise) {

        transaction.setIdDonneur(donneur)
                   .setIdMarchandise(marchandise);

        return transaction;
    }

    public TransactionModele modifierMarchandise() {
        //TODO: Réflexion sur comment modifier une transaction sur marchandise. Ex: modifier
        // quantité en cas d'erreur.
        return null;
    }

    /**
     * Méthode pour réserver une marchandise en modifiant une transaction existante. La date du
     * jour
     * sera considérée comme date de réservation lors de la requête au serveur.
     *
     * @param transaction
     *         objet transaction à modifier
     * @param receveur
     *         organisme receveur à insérer dans la transaction
     *
     * @return transaction avec les informations à envoyer par une requête SQL
     */
    public TransactionModele reserverMarchandise(TransactionModele transaction,
                                                 OrganismeModele receveur) {

        transaction.setIdReceveur(receveur);

        return transaction;
    }

    /**
     * Méthode pour annuler une réservation. La date de réservation et l'objet organisme receveur
     * seront remis à null lors de la requête au serveur.
     *
     * @param transaction
     *         objet transaction à canceller
     *
     * @return transaction à envoyer par une requête SQL
     */
    public TransactionModele annulerReservationMarchandise(TransactionModele transaction) {

        return transaction;
    }

    /**
     * Méthode pour collecter la marchandise. La date du jour
     * sera considérée comme date de collecte lors de la requête au serveur.
     *
     * @param transaction
     *         objet transaction à collecter
     *
     * @return transaction à envoyer par une requête SQL
     */
    public TransactionModele collecterMarchandise(TransactionModele transaction) {

        return transaction;
    }
}
