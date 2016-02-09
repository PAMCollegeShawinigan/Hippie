package com.pam.codenamehippie.modele;

import com.google.gson.annotations.SerializedName;

import java.util.regex.Pattern;

/**
 * Créé par Carl St-Louis le 2015-12-01.
 */
public class AdresseModele extends BaseModele<AdresseModele> {

    @SerializedName("no_civique")
    protected Integer noCivique;
    @SerializedName("type_rue")
    protected String typeRue;
    @SerializedName("nom")
    protected String nom;
    @SerializedName("app")
    protected String app;
    @SerializedName("ville")
    protected String ville;
    @SerializedName("province")
    protected String province;
    @SerializedName("code_postal")
    protected String codePostal;
    @SerializedName("pays")
    protected String pays;

    protected transient volatile String prettyPrintString;
    protected transient volatile String cacheString;
    protected transient volatile String formattedCodePostal;

    public Integer getNoCivique() {
        return this.noCivique;
    }

    public AdresseModele setNoCivique(Integer noCivique) {
        this.noCivique = noCivique;
        this.prettyPrintString = null;
        this.cacheString = null;
        return this;
    }

    public String getTypeRue() {
        return this.typeRue;
    }

    public AdresseModele setTypeRue(String typeRue) {
        this.typeRue = typeRue;
        this.prettyPrintString = null;
        this.cacheString = null;
        return this;
    }

    public String getNom() {
        return this.nom;
    }

    public AdresseModele setNom(String nom) {
        this.nom = nom;
        this.prettyPrintString = null;
        this.cacheString = null;
        return this;
    }

    public String getApp() {
        return this.app;
    }

    public AdresseModele setApp(String app) {
        this.app = app;
        this.prettyPrintString = null;
        this.cacheString = null;
        return this;
    }

    public String getVille() {
        return this.ville;
    }

    public AdresseModele setVille(String ville) {
        this.ville = ville;
        this.prettyPrintString = null;
        this.cacheString = null;
        return this;
    }

    public String getProvince() {
        return this.province;
    }

    public AdresseModele setProvince(String province) {
        this.province = province;
        this.prettyPrintString = null;
        this.cacheString = null;
        return this;
    }

    public String getCodePostal() {
        return this.codePostal;
    }

    public AdresseModele setCodePostal(String codePostal) {
        if (codePostal == null) {
            this.codePostal = null;
        } else {
            this.codePostal = codePostal.replaceAll("\\s+", "").toUpperCase();
            this.formattedCodePostal = null;
            this.prettyPrintString = null;
            this.cacheString = null;
        }
        return this;
    }

    public String getFormattedCodePostal() {
        if (this.codePostal == null) {
            return null;
        }
        if (this.formattedCodePostal == null) {
            StringBuilder stringBuilder = new StringBuilder(this.codePostal);
            stringBuilder.insert(3, " ");
            this.formattedCodePostal = stringBuilder.toString();
        }
        return this.formattedCodePostal;
    }

    public String getPays() {
        return this.pays;
    }

    public AdresseModele setPays(String pays) {
        this.pays = pays;
        this.prettyPrintString = null;
        this.cacheString = null;
        return this;
    }

    protected String toString(boolean estMultiligne) {
        StringBuilder stringBuilder = new StringBuilder(200);
        if (this.noCivique != null) {
            stringBuilder.append(this.noCivique).append(" ");
        }
        if ((this.nom != null) && (this.typeRue != null)) {
            String first =
                    (Pattern.matches("^([0-9+]).+$", this.nom)) ? this.nom : this.typeRue;
            String second = (first.equals(this.typeRue)) ? this.nom : this.typeRue;
            stringBuilder.append(first).append(" ").append(second);
        }
        stringBuilder.append(", ");
        if (this.app != null) {
            stringBuilder.append("Apt. ").append(this.app);
        }
        if (estMultiligne) {
            stringBuilder.append("\n");
        }
        if (this.ville != null) {
            stringBuilder.append(this.ville).append(", ");
        }
        if (this.province != null) {
            stringBuilder.append(this.province).append(" ");
        }
        if (this.codePostal != null) {
            stringBuilder.append(this.getFormattedCodePostal());

        }
        if (estMultiligne) {
            stringBuilder.append("\n");
        } else {
            stringBuilder.append(" ");
        }
        if (this.pays != null) {
            stringBuilder.append(this.pays).append(" ");
        }
        return (stringBuilder.length() == 0) ? null : stringBuilder.toString();
    }

    public String toFormattedString() {
        if (this.prettyPrintString == null) {
            this.prettyPrintString = this.toString(true);
        }
        return this.prettyPrintString;
    }

    public String string() {
        if (this.cacheString == null) {
            this.cacheString = this.toString(false);
        }
        return this.cacheString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdresseModele)) {
            return false;
        }
        AdresseModele rhs = (AdresseModele) o;
        return (((this.id == null) ? (rhs.id == null) : this.id.equals(rhs.id)) &&
                ((this.string() == null)
                 ? rhs.string() == null
                 : this.cacheString.equals(rhs.string())));
    }

    @Override
    public int hashCode() {
        int hash = 62;
        hash = (this.id != null) ? 31 * hash + this.id.hashCode() : hash;
        hash = (this.string() != null)
               ? 31 * hash + this.cacheString.hashCode()
               : hash;
        return hash;
    }
}
