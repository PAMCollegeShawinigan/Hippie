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
    private String motDePasse;
    @SerializedName("nom")
    private String nom;
    @SerializedName("prenom")
    private String prenom;
    @SerializedName("telephone")
    private String telephone;
    // FIXME: Le serveur retourne moyenContact au lieu de moyen_contact
    @SerializedName(value = "moyen_contact", alternate = "moyenContact")
    private Integer moyenContact;
    @SerializedName("id_organisme")
    private Integer idOrganisme;
    @SerializedName("dern_connexion")
    private Date dernConnexion;

    // ------------------ Début Accesseurs et mutateurs
    public String getCourriel() {
        return this.courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public String getMotDePasse() {
        return this.motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getMoyenContact() {
        return this.moyenContact;
    }

    public void setMoyenContact(Integer moyenContact) {
        this.moyenContact = moyenContact;
    }

    public Integer getIdOrganisme() {
        return this.idOrganisme;
    }

    public void setIdOrganisme(Integer idOrganisme) {
        this.idOrganisme = idOrganisme;
    }

    public Date getDernConnexion() {
        return this.dernConnexion;
    }

    public void setDernConnexion(Date dernConnexion) {
        this.dernConnexion = dernConnexion;
    }
    // ------------------ Fin Accesseurs et mutateurs
}
