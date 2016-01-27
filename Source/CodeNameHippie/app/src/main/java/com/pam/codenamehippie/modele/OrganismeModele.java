package com.pam.codenamehippie.modele;

import com.google.gson.annotations.SerializedName;

/**
 * Créé par Carl St-Louis le 23-11-2015.
 */
public class OrganismeModele extends BaseModele<OrganismeModele> {

    @SerializedName("nom")
    private String nom;
    @SerializedName("adresse")
    private AdresseModele adresse;
    @SerializedName("telephone")
    private String telephone;
    @SerializedName("poste")
    private Integer poste;
    @SerializedName("contact")
    private UtilisateurModele contact;
    @SerializedName("no_entreprise")
    private String noEntreprise;
    @SerializedName("no_osbl")
    private String noOsbl;

    public String getNom() {
        return this.nom;
    }

    public OrganismeModele setNom(String nom) {
        this.nom = nom;
        return this;
    }

    public AdresseModele getAdresse() {
        return this.adresse;
    }

    public OrganismeModele setAdresse(AdresseModele adresse) {
        this.adresse = adresse;
        return this;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public OrganismeModele setTelephone(String telephone) {
        if (telephone == null) {
            this.telephone = null;
        } else {
            this.telephone = telephone.replaceAll("[\\s()-\\.]+", "");
        }
        return this;
    }

    public String getFormattedTelephone() {
        if (this.telephone == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder(this.telephone);
        stringBuilder.insert(6, "-").insert(0, "(").insert(4, ") ");
        return stringBuilder.toString();
    }

    public Integer getPoste() {
        return this.poste;
    }

    public OrganismeModele setPoste(Integer poste) {
        this.poste = poste;
        return this;
    }

    public UtilisateurModele getContact() {
        return this.contact;
    }

    public OrganismeModele setContact(UtilisateurModele contact) {
        this.contact = contact;
        return this;
    }

    public String getNoEntreprise() {
        return this.noEntreprise;
    }

    public OrganismeModele setNoEntreprise(String noEntreprise) {
        this.noEntreprise = noEntreprise;
        return this;
    }

    public String getNoOsbl() {
        return this.noOsbl;
    }

    public OrganismeModele setNoOsbl(String noOsbl) {
        this.noOsbl = noOsbl;
        return this;
    }
}
