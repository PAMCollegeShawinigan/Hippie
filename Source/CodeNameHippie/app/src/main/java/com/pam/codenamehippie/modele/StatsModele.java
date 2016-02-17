package com.pam.codenamehippie.modele;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Pier-Alain on 2016-02-11.
 */
public class StatsModele extends BaseModele<StatsModele> {

    @SerializedName("montant_total")
    protected Double montantTotal = 0.00d;
    @SerializedName("date")
    protected Date date;

    public Double getMontantTotal() {
        return this.montantTotal;
    }

    public StatsModele setMontantTotal(Double montantTotal) {
        this.montantTotal = montantTotal;
        return this;
    }

    public Date getDate() {
        return this.date;
    }

    public StatsModele setDate(Date date) {
        this.date = date;
        return this;
    }

    public Calendar getCalendarDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.date);
        return calendar;
    }
}
