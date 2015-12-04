package com.pam.codenamehippie.modele;

import com.google.gson.annotations.SerializedName;

/**
 * Créé par Carl St-Louis le 2015-12-01.
 */
public class AdresseModele extends BaseModele {

    @SerializedName("no_civique")
    private Integer noCivique;
    @SerializedName("type_rue")
    private String typeRue;
    @SerializedName("nom")
    private String nom;
    @SerializedName("app")
    private String app;
    @SerializedName("ville")
    private String ville;
    @SerializedName("province")
    private String province;
    @SerializedName("code_postal")
    private String codePostal;
    @SerializedName("pays")
    private String pays;

    // ------------------------------ Début Accesseur et Mutateur
    public Integer getNoCivique() {
        return this.noCivique;
    }

    public void setNoCivique(Integer noCivique) {
        this.noCivique = noCivique;
    }

    public String getTypeRue() {
        return this.typeRue;
    }

    public void setTypeRue(String typeRue) {
        this.typeRue = typeRue;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getApp() {
        return this.app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getVille() {
        return this.ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCodePostal() {
        return this.codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getPays() {
        return this.pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }
    // ------------------------------ Fin Accesseur et Mutateur
}
