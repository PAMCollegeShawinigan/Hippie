package com.pam.codenamehippie.modele;

import com.google.gson.annotations.SerializedName;

/**
 * Classe servant à modeler des élement provenant des tables de lookup de la base de données.
 */
public class DescriptionModel extends BaseModele<DescriptionModel> {

    @SerializedName("description")
    protected String description = "Faites votre choix...";

    /**
     * Accesseur la description
     *
     * @return retourne la valeur du champ description
     */
    public String getDescription() {
        return this.description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DescriptionModel)) {
            return false;
        }
        DescriptionModel rhs = ((DescriptionModel) o);
        return (((this.id == null) ? rhs.id == null : this.id.equals(rhs.id)) &&
                ((this.description == null)
                 ? rhs.description == null
                 : this.description.equals(rhs.description)));
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + this.id.hashCode();
        hash = 31 * hash + this.description.hashCode();
        return hash;
    }
}
