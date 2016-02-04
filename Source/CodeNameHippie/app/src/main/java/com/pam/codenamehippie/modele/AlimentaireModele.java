package com.pam.codenamehippie.modele;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;

/**
 * Créé par Carl St-Louis le 23-11-2015.
 */
public class AlimentaireModele extends MarchandiseModele<AlimentaireModele> {

    @SerializedName("type_alimentaire")
    private String typeAlimentaire;
    @SerializedName("date_peremption")
    private Date datePeremption;
    @SerializedName("date_reservation")
    private Date dateReservation;

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
}
