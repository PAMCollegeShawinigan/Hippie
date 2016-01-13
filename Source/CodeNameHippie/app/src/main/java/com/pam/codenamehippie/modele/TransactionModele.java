package com.pam.codenamehippie.modele;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Créé par Carl St-Louis le 23-11-2015.
 */
public class TransactionModele extends BaseModele<TransactionModele> {

    @SerializedName("receveur_id")
    private OrganismeModele idReceveur;
    @SerializedName("donneur_id")
    private OrganismeModele idDonneur;
    @SerializedName("marchandise_id")
    private MarchandiseModele idMarchandise;
    @SerializedName("date_collecte")
    private Date dateCollecte;
    @SerializedName("date_reservation")
    private Date dateReservation;
    @SerializedName("date_disponible")
    private Date dateDisponible;

    // ------------------------------ Début Accesseur et Mutateur
    public OrganismeModele getIdReceveur() {
        return this.idReceveur;
    }

    public TransactionModele setIdReceveur(OrganismeModele idReceveur) {
        this.idReceveur = idReceveur;
        return this;
    }

    public OrganismeModele getIdDonneur() {
        return this.idDonneur;
    }

    public TransactionModele setIdDonneur(OrganismeModele idDonneur) {
        this.idDonneur = idDonneur;
        return this;
    }

    public MarchandiseModele getIdMarchandise() {
        return this.idMarchandise;
    }

    public TransactionModele setIdMarchandise(MarchandiseModele idMarchandise) {
        this.idMarchandise = idMarchandise;
        return this;
    }

    public Date getDateCollecte() {
        return this.dateCollecte;
    }

    public TransactionModele setDateCollecte(Date dateCollecte) {
        this.dateCollecte = dateCollecte;
        return this;
    }

    public Date getDateReservation() {
        return this.dateReservation;
    }

    public TransactionModele setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
        return this;
    }

    public Date getDateDisponible() {
        return this.dateDisponible;
    }

    public TransactionModele setDateDisponible(Date dateDisponible) {
        this.dateDisponible = dateDisponible;
        return this;
    }
    // ------------------------------ Fin Accesseur et Mutateur
}
