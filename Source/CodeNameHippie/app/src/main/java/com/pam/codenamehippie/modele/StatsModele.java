package com.pam.codenamehippie.modele;

/**
 * Created by Pier-Alain on 2016-02-11.
 */
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;

public class StatsModele extends BaseModele<StatsModele>{



    @SerializedName("montant_total")
    protected Long montantTotal = 0L;
    @SerializedName("date")
    protected Date date;


    public Long getMontantTotal() {
        return montantTotal;
    }

    public StatsModele setMontantTotal(Long montantTotal) {
        this.montantTotal = montantTotal;
        return this;
    }

    public Date getDate() {
        return date;
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
