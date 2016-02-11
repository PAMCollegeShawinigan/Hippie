package com.pam.codenamehippie.modele;

/**
 * Created by Pier-Alain on 2016-02-11.
 */
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class StatsModele extends BaseModele<StatsModele>{



    @SerializedName("montant_total")
    protected Long montantTotal = 0L;
    @SerializedName("date")
    protected Date dateCourant;


    public Long getMontantTotal() {
        return montantTotal;
    }

    public StatsModele setMontantTotal(Long montantTotal) {
        this.montantTotal = montantTotal;
        return this;
    }

    public Date getDateCourant() {
        return dateCourant;
    }

    public StatsModele setDateCourant(Date dateCourant) {
        this.dateCourant = dateCourant;
        return this;
    }
}
