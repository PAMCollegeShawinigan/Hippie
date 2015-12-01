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
    private int poste;
    @SerializedName("contact")
    private String contact;
    @SerializedName("no_entreprise")
    private String no_entreprise;
    @SerializedName("no_osbl")
    private String no_osbl;


    // ------------------ Début constructeurs
    public OrganismeModele(BaseModeleDepot depot, int id) {
        super(depot, id);
    }

    // Todo: Vérifier les champs obligatoires pour nos constructeurs

    // ------------------ Fin constructeurs


    // ------------------ Début Accesseurs et mutateurs
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getPoste() {
        return poste;
    }

    public void setPoste(int poste) {
        this.poste = poste;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getNo_entreprise() {
        return no_entreprise;
    }

    public void setNo_entreprise(String no_entreprise) {
        this.no_entreprise = no_entreprise;
    }

    public String getNo_osbl() {
        return no_osbl;
    }

    public void setNo_osbl(String no_osbl) {
        this.no_osbl = no_osbl;
    }
    // ------------------ Fin Accesseurs et mutateurs
}
