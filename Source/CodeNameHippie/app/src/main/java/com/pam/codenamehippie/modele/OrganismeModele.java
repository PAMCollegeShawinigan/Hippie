package com.pam.codenamehippie.modele;

import com.google.gson.annotations.SerializedName;

/**
 * Créé par Carl St-Louis le 23-11-2015.
 */
public class OrganismeModele extends BaseModele {

    @SerializedName("nom")
    private String nom;
    @SerializedName("adresse")
    private AdresseModele adresse;
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
    @SerializedName(value = "utilisateur", alternate = "utilisareur")
    private UtilisateurModele utilisateur;

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
        this.telephone = telephone;
        return this;
    }

    public Integer getPoste() {
        return this.poste;
    }

    public OrganismeModele setPoste(Integer poste) {
        this.poste = poste;
        return this;
    }

    public String getContact() {
        return this.contact;
    }

    public OrganismeModele setContact(String contact) {
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

    public UtilisateurModele getUtilisateur() {
        return this.utilisateur;
    }

    public void setUtilisateur(UtilisateurModele utilisateur) {
        this.utilisateur = utilisateur;
    }
}
