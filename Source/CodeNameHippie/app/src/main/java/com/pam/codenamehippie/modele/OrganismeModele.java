package com.pam.codenamehippie.modele;

import com.google.gson.annotations.SerializedName;

/**
 * Créé par Carl St-Louis le 23-11-2015.
 */
public class OrganismeModele extends BaseModele {

    @SerializedName("nom")
    private String nom;
    @SerializedName("adresse")
    private String adresse;
    @SerializedName("telephone")
    private String telephone;
    @SerializedName("poste")
    private Integer poste;
    @SerializedName("contact")
    private String contact;
    @SerializedName("no_entreprise")
    private String noEntreprise;
    @SerializedName("no_osbl")
    private String noOsbl;

    // ------------------ Début Accesseurs et mutateurs
    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getPoste() {
        return this.poste;
    }

    public void setPoste(Integer poste) {
        this.poste = poste;
    }

    public String getContact() {
        return this.contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getNoEntreprise() {
        return this.noEntreprise;
    }

    public void setNoEntreprise(String noEntreprise) {
        this.noEntreprise = noEntreprise;
    }

    public String getNoOsbl() {
        return this.noOsbl;
    }

    public void setNoOsbl(String noOsbl) {
        this.noOsbl = noOsbl;
    }
    // ------------------ Fin Accesseurs et mutateurs
}
