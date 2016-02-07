package com.pam.codenamehippie.modele;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Créé par Carl St-Louis le 23-11-2015.
 */
public class UtilisateurModele extends BaseModele<UtilisateurModele> {

    @SerializedName("courriel")
    protected String courriel;
    @SerializedName("mot_de_passe")
    protected String motDePasse;
    @SerializedName("nom")
    protected String nom;
    @SerializedName("prenom")
    protected String prenom;
    @SerializedName("telephone")
    protected String telephone;
    @SerializedName(value = "moyen_contact")
    protected Integer moyenContact;
    @SerializedName("organisme")
    protected OrganismeModele organisme;
    @SerializedName("dern_connexion")
    protected Date dernConnexion;

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

    public String getNomComplet() {
        StringBuilder resultat = new StringBuilder(50);
        if (this.prenom != null) {
            resultat.append(this.prenom);
        }
        if (this.nom != null) {
            if (this.prenom != null) {
                resultat.append(" ");
            }
            resultat.append(this.nom);
        }
        return resultat.toString();
    }

    public String getTelephone() {
        return this.telephone;
    }

    public UtilisateurModele setTelephone(String telephone) {
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

    public Integer getMoyenContact() {
        return this.moyenContact;
    }

    public UtilisateurModele setMoyenContact(Integer moyenContact) {
        this.moyenContact = moyenContact;
        return this;
    }

    public OrganismeModele getOrganisme() {
        return this.organisme;
    }

    public UtilisateurModele setOrganisme(OrganismeModele organisme) {
        this.organisme = organisme;
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
