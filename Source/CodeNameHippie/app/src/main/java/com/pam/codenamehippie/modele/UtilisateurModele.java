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

    protected transient volatile String formattedTelephone;
    protected transient volatile String nomComplet;

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
        this.nomComplet = null;
        return this;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public UtilisateurModele setPrenom(String prenom) {
        this.prenom = prenom;
        this.nomComplet = null;
        return this;
    }

    public String getNomComplet() {
        if (this.nomComplet == null) {
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
            this.nomComplet = (resultat.length() != 0) ? resultat.toString() : null;
        }
        return this.nomComplet;
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
        this.formattedTelephone = null;
        return this;
    }

    public String getFormattedTelephone() {
        if (this.telephone == null) {
            return null;
        }
        if (this.formattedTelephone == null) {
            StringBuilder stringBuilder = new StringBuilder(this.telephone);
            stringBuilder.insert(6, "-").insert(0, "(").insert(4, ") ");
            this.formattedTelephone = stringBuilder.toString();
        }
        return this.formattedTelephone;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UtilisateurModele)) {
            return false;
        }
        UtilisateurModele rhs = ((UtilisateurModele) o);
        return (((this.id == null) ? (rhs.id == null) : this.id.equals(rhs.id)) &&
                ((this.courriel == null)
                 ? (rhs.courriel == null)
                 : this.courriel.equals(rhs.courriel)) &&
                ((this.motDePasse == null)
                 ? (rhs.motDePasse == null)
                 : this.motDePasse.equals(rhs.motDePasse)) &&
                ((this.getNomComplet() == null)
                 ? (rhs.getNomComplet() == null)
                 : this.getNomComplet().equals(rhs.getNomComplet())) &&
                ((this.telephone == null)
                 ? (rhs.telephone == null)
                 : this.telephone.equals(rhs.telephone)) &&
                ((this.moyenContact == null)
                 ? (rhs.moyenContact == null)
                 : this.moyenContact.equals(rhs.moyenContact)) &&
                ((this.organisme == null)
                 ? (rhs.organisme == null)
                 : this.organisme.equals(rhs.organisme)) &&
                ((this.dernConnexion == null)
                 ? (rhs.dernConnexion == null)
                 : this.dernConnexion.equals(rhs.dernConnexion)));
    }

    @Override
    public int hashCode() {
        int hash = 99;
        hash = (this.id != null) ? 33 * hash + this.id.hashCode() : hash;
        hash = (this.courriel != null) ? 33 * hash + this.courriel.hashCode() : hash;
        hash = (this.motDePasse != null) ? 33 * hash + this.motDePasse.hashCode() : hash;
        hash = (this.getNomComplet() != null) ? 33 * hash + this.nomComplet.hashCode() : hash;
        hash = (this.telephone != null) ? 33 * hash + this.telephone.hashCode() : hash;
        hash = (this.moyenContact != null) ? 33 * hash + this.moyenContact.hashCode() : hash;
        hash = (this.organisme != null) ? 33 * hash + this.organisme.hashCode() : hash;
        hash = (this.dernConnexion != null) ? 33 * hash + this.dernConnexion.hashCode() : hash;
        return hash;
    }
}
