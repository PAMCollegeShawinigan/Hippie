package com.pam.codenamehippie.modele;

import com.google.gson.annotations.SerializedName;
import com.pam.codenamehippie.modele.depot.AlimentaireModeleDepot;

import java.util.Calendar;
import java.util.Date;

/**
 * Créé par Carl St-Louis le 23-11-2015.
 */
public class TransactionModele extends BaseModele<TransactionModele> {

    @SerializedName("receveur_id")
    private OrganismeModele idReceveur;
    @SerializedName("donneur_id")
    private OrganismeModele idDonneur;
    @SerializedName("alimentaire")
    private AlimentaireModele alimentaire;
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

    public AlimentaireModele getAlimentaire() {
        return this.alimentaire;
    }

    public TransactionModele setAlimentaire(AlimentaireModele alimentaire) {
        this.alimentaire = alimentaire;
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

    public Calendar getCalendarDateDisponible() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.dateDisponible);
        return calendar;
    }

    public Calendar getCalendarDateReservation() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.dateReservation);
        return calendar;
    }

    public Calendar getCalendarDateCollecte() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.dateCollecte);
        return calendar;
    }
    // ------------------------------ Fin Accesseur et Mutateur
}
