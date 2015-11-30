package com.pam.codenamehippie.modele;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Créé par Carl St-Louis le 23-11-2015.
 */
public class UtilisateurModele extends BaseModele {

    @SerializedName("courriel")
    private String courriel;
    @SerializedName("mot_de_passe")
    private String mot_de_passe;
    @SerializedName("nom")
    private String nom;
    @SerializedName("prenom")
    private String prenom;
    @SerializedName("telephone")
    private String telephone;
    @SerializedName("moyen_contact")
    private int moyen_contact;
    @SerializedName("id_organisme")
    private int id_organisme;
    @SerializedName("dern_connexion")
    private Date dern_connexion;

    // ------------------ Début constructeurs

    public UtilisateurModele(BaseModeleDepot depot, int id) {
        super(depot, id);
    }

    // Todo: Vérifier les champs obligatoires pour nos constructeurs

    // ------------------ Fin constructeurs

    // ------------------ Début Accesseurs et mutateurs
    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getMoyen_contact() {
        return moyen_contact;
    }

    public void setMoyen_contact(int moyen_contact) {
        this.moyen_contact = moyen_contact;
    }

    public int getId_organisme() {
        return id_organisme;
    }

    public void setId_organisme(int id_organisme) {
        this.id_organisme = id_organisme;
    }

    public Date getDern_connexion() {
        return dern_connexion;
    }

    public void setDern_connexion(Date dern_connexion) {
        this.dern_connexion = dern_connexion;
    }
    // ------------------ Fin Accesseurs et mutateurs
}
