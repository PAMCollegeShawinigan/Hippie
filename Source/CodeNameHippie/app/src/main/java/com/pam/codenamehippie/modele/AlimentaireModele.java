package com.pam.codenamehippie.modele;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;

/**
 * Créé par Carl St-Louis le 23-11-2015.
 */
public class AlimentaireModele extends MarchandiseModele<AlimentaireModele> {

    @SerializedName("type_alimentaire")
    protected String typeAlimentaire;
    @SerializedName("date_peremption")
    protected Date datePeremption;
    @SerializedName("date_reservation")
    protected Date dateReservation;

    public Date getDateReservation() {return this.dateReservation;}

    public AlimentaireModele setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
        return this;
    }

    public String getTypeAlimentaire() {
        return this.typeAlimentaire;
    }

    public AlimentaireModele setTypeAlimentaire(String typeAlimentaire) {
        this.typeAlimentaire = typeAlimentaire;
        return this;
    }

    public Date getDatePeremption() {
        return this.datePeremption;
    }

    public AlimentaireModele setDatePeremption(Date datePeremption) {
        this.datePeremption = datePeremption;
        return this;
    }

    public Calendar getCalendarDatePeremption() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.datePeremption);
        return calendar;
    }

    public Calendar getCalendarDateReservation() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.dateReservation);
        return calendar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AlimentaireModele)) {
            return false;
        }
        AlimentaireModele rhs = ((AlimentaireModele) o);
        return (((this.id == null) ? (rhs.id == null) : this.id.equals(rhs.id)) &&
                ((this.nom == null) ? (rhs.nom == null) : this.nom.equals(rhs.nom)) &&
                ((this.description == null)
                 ? (rhs.description == null)
                 : this.description.equals(rhs.description)) &&
                ((this.quantite == null)
                 ? (rhs.quantite == null)
                 : this.quantite.equals(rhs.quantite)));
    }

    @Override
    public int hashCode() {
        int hash = 64;
        hash = 32 * hash + this.id.hashCode();
        hash = 32 * hash + this.nom.hashCode();
        hash = 32 * hash + this.description.hashCode();
        return hash;
    }
}
