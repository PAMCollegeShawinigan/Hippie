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
    @SerializedName("organisme_id")
    private Integer idOrganisme;
    @SerializedName("dern_connexion")
    private Date dernConnexion;

    public String getCourriel() {
        return this.courriel;
    }

    public UtilisateurModele setCourriel(String courriel) {
        this.courriel = courriel;
        return this;
    }

    public String getMotDePasse() {
        return this.motDePasse;
    }

    public UtilisateurModele setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
        return this;
    }

    public String getNom() {
        return this.nom;
    }

    public UtilisateurModele setNom(String nom) {
        this.nom = nom;
        return this;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public UtilisateurModele setPrenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public UtilisateurModele setTelephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public Integer getMoyenContact() {
        return this.moyenContact;
    }

    public UtilisateurModele setMoyenContact(Integer moyenContact) {
        this.moyenContact = moyenContact;
        return this;
    }

    public Integer getIdOrganisme() {
        return this.idOrganisme;
    }

    public UtilisateurModele setIdOrganisme(Integer idOrganisme) {
        this.idOrganisme = idOrganisme;
        return this;
    }

    public Date getDernConnexion() {
        return this.dernConnexion;
    }

    public UtilisateurModele setDernConnexion(Date dernConnexion) {
        this.dernConnexion = dernConnexion;
        return this;
    }
}
