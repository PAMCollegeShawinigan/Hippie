package com.pam.codenamehippie.modele;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;

/**
 * Créé par Carl St-Louis le 23-11-2015.
 */
public class TransactionModele extends BaseModele<TransactionModele> {

    @SerializedName("receveur")
    protected OrganismeModele receveur;
    @SerializedName("donneur")
    protected OrganismeModele donneur;
    @SerializedName("alimentaire")
    protected AlimentaireModele alimentaire;
    @SerializedName("date_collecte")
    protected Date dateCollecte;
    @SerializedName("date_reservation")
    protected Date dateReservation;
    @SerializedName("date_disponible")
    protected Date dateDisponible;
    @SerializedName("date_transaction")
    protected Date dateTransaction;

    public Date getDateTransaction() {
        return dateTransaction;
    }

    public TransactionModele setDateTransaction(Date dateTransaction) {
        this.dateTransaction = dateTransaction;
        return this;
    }



    public OrganismeModele getReceveur() {
        return this.receveur;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionModele)) {
            return false;
        }
        TransactionModele rhs = (TransactionModele) o;
        return (((this.id == null) ? (rhs.id == null) : this.id.equals(rhs.id)) &&
                ((this.receveur == null) ? (rhs.receveur == null) : this.receveur.equals(rhs.receveur)) &&
                ((this.donneur == null)
                        ? (rhs.donneur == null)
                        : this.donneur.equals(rhs.donneur)) &&
                ((this.alimentaire == null) ? (rhs.alimentaire == null) : this.alimentaire.equals(rhs.alimentaire)) &&
                ((this.dateCollecte == null)
                        ? (rhs.dateCollecte == null)
                        : this.dateCollecte.equals(rhs.dateCollecte)) &&
                ((this.dateReservation == null)
                        ? (rhs.dateReservation == null)
                        : this.dateReservation.equals(rhs.dateReservation)) &&
                ((this.dateDisponible == null)
                        ? (rhs.dateDisponible == null)
                        : this.dateDisponible.equals(rhs.dateDisponible)) &&
                ((this.dateTransaction == null)
                        ? (rhs.dateTransaction == null)
                        : this.dateTransaction.equals(rhs.dateTransaction)));
    }

    @Override
    public int hashCode() {
        int hash = 142;
        hash = (this.id != null) ? 71 * hash + this.id.hashCode() : hash;
        hash = (this.receveur != null) ? 71 * hash + this.receveur.hashCode() : hash;
        hash = (this.donneur != null) ? 71 * hash + this.donneur.hashCode() : hash;
        hash = (this.alimentaire != null) ? 71 * hash + this.alimentaire.hashCode() : hash;
        hash = (this.dateCollecte != null) ? 71 * hash + this.dateCollecte.hashCode() : hash;
        hash = (this.dateReservation != null) ? 71 * hash + this.dateReservation.hashCode() : hash;
        hash = (this.dateDisponible != null) ? 71 * hash + this.dateDisponible.hashCode() : hash;
        hash = (this.dateTransaction != null) ? 71 * hash + this.dateTransaction.hashCode() : hash;
        return hash;
    }

}
