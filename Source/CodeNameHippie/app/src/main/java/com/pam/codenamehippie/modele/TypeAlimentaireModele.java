package com.pam.codenamehippie.modele;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Carl St-Louis le 2016-01-12.
 */
public class TypeAlimentaireModele extends DescriptionModel {

    @SerializedName("perissable")
    protected Boolean estPerissable = false;

    /**
     * @return true si le produit est périssable
     */
    public Boolean getEstPerissable() {
        return this.estPerissable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeAlimentaireModele)) {
            return false;
        }
        TypeAlimentaireModele rhs = (TypeAlimentaireModele) o;
        return (((this.id == null) ? (rhs.id == null) : this.id.equals(rhs.id)) &&
                ((this.description == null)
                 ? (rhs.description == null)
                 : this.description.equals(rhs.description)) &&
                ((this.estPerissable == null)
                 ? (rhs.estPerissable == null)
                 : this.estPerissable.equals(rhs.estPerissable)));
    }

    @Override
    public int hashCode() {
        int hash = 18;
        hash = (this.id != null) ? 31 * hash + this.id.hashCode() : hash;
        hash = (this.description != null) ? 31 * hash + this.description.hashCode() : hash;
        hash = (this.estPerissable != null) ? 31 * hash + this.estPerissable.hashCode() : hash;
        return hash;
    }
}
