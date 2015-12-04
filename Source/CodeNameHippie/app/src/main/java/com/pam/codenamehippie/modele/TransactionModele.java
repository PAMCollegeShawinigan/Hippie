package com.pam.codenamehippie.modele;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Créé par Carl St-Louis le 23-11-2015.
 */
public class TransactionModele extends BaseModele {

    @SerializedName("id_receveur")
    private OrganismeModele idReceveur;
    @SerializedName("idDonneur")
    private OrganismeModele idDonneur;
    @SerializedName("id_marchandise")
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

    public void setIdReceveur(OrganismeModele idReceveur) {
        this.idReceveur = idReceveur;
    }

    public OrganismeModele getIdDonneur() {
        return this.idDonneur;
    }

    public void setIdDonneur(OrganismeModele idDonneur) {
        this.idDonneur = idDonneur;
    }

    public MarchandiseModele getIdMarchandise() {
        return this.idMarchandise;
    }

    public void setIdMarchandise(MarchandiseModele idMarchandise) {
        this.idMarchandise = idMarchandise;
    }

    public Date getDateCollecte() {
        return this.dateCollecte;
    }

    public void setDateCollecte(Date dateCollecte) {
        this.dateCollecte = dateCollecte;
    }

    public Date getDateReservation() {
        return this.dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    public Date getDateDisponible() {
        return this.dateDisponible;
    }

    public void setDateDisponible(Date dateDisponible) {
        this.dateDisponible = dateDisponible;
    }
    // ------------------------------ Fin Accesseur et Mutateur
}
