package com.pam.codenamehippie.modele;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Créé par Carl St-Louis le 23-11-2015.
 */
public class TransactionModele extends BaseModele {

    @SerializedName("id_receveur")
    private OrganismeModele id_receveur;
    @SerializedName("id_donneur")
    private OrganismeModele id_donneur;
    @SerializedName("id_marchandise")
    private MarchandiseModele id_marchandise;
    @SerializedName("date_collecte")
    private Date date_collecte;
    @SerializedName("date_reservation")
    private Date date_reservation;
    @SerializedName("date_disponible")
    private Date date_disponible;

    // ------------------------------ Début Constructeur
    public TransactionModele(BaseModeleDepot depot, int id) {
        super(depot, id);
    }

    // todo: Vérifier les champs obligatoires pour nos constructeurs

    // ------------------------------ Fin Constructeur

    // ------------------------------ Début Accesseur et Mutateur
    public OrganismeModele getId_receveur() {
        return id_receveur;
    }

    public void setId_receveur(OrganismeModele id_receveur) {
        this.id_receveur = id_receveur;
    }

    public OrganismeModele getId_donneur() {
        return id_donneur;
    }

    public void setId_donneur(OrganismeModele id_donneur) {
        this.id_donneur = id_donneur;
    }

    public MarchandiseModele getId_marchandise() {
        return id_marchandise;
    }

    public void setId_marchandise(MarchandiseModele id_marchandise) {
        this.id_marchandise = id_marchandise;
    }

    public Date getDate_collecte() {
        return date_collecte;
    }

    public void setDate_collecte(Date date_collecte) {
        this.date_collecte = date_collecte;
    }

    public Date getDate_reservation() {
        return date_reservation;
    }

    public void setDate_reservation(Date date_reservation) {
        this.date_reservation = date_reservation;
    }

    public Date getDate_disponible() {
        return date_disponible;
    }

    public void setDate_disponible(Date date_disponible) {
        this.date_disponible = date_disponible;
    }
    // ------------------------------ Fin Accesseur et Mutateur
}
