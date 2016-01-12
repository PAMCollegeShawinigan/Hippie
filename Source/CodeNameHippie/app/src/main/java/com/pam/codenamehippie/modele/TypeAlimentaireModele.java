package com.pam.codenamehippie.modele;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Carl St-Louis le 2016-01-12.
 */
public class TypeAlimentaireModele extends DescriptionModel {

    @SerializedName("perissable")
    private Integer estPerissable = 0;

    /**
     *
     * @return true si le produit est périssable
     */
    public Boolean getEstPerissable() {
        return !this.estPerissable.equals(0);
    }
}
