package com.pam.codenamehippie.modele;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Créé par Carl St-Louis le 23-11-2015.
 */
public class AlimentaireModele extends MarchandiseModele<AlimentaireModele> {

    @SerializedName("unite")
    private String unite;
    @SerializedName("type_alimentaire")
    private String typeAlimentaire;
    @SerializedName("date_peremption")
    private Date datePeremption;

    public String getUnite() {
        return this.unite;
    }

    public AlimentaireModele setUnite(String unite) {
        this.unite = unite;
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
}
