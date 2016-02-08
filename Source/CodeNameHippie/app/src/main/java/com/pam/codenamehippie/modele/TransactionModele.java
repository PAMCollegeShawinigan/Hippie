package com.pam.codenamehippie.modele;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;

/**
 * Créé par Carl St-Louis le 23-11-2015.
 */
public class TransactionModele extends BaseModele<TransactionModele> {

    @SerializedName("receveur_id")
    protected OrganismeModele receveur;
    @SerializedName("donneur_id")
    protected OrganismeModele donneur;
    @SerializedName("alimentaire")
    protected AlimentaireModele alimentaire;
    @SerializedName("date_collecte")
    protected Date dateCollecte;
    @SerializedName("date_reservation")
    protected Date dateReservation;
    @SerializedName("date_disponible")
    protected Date dateDisponible;

    public OrganismeModele getReceveur() {
        return this.receveur;

    public TransactionModele setReceveur(OrganismeModele receveur) {
        this.receveur = receveur;
        return this;
    }

    public OrganismeModele getDonneur() {
        return this.donneur;
    }

    public TransactionModele setDonneur(OrganismeModele donneur) {
        this.donneur = donneur;
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
}
