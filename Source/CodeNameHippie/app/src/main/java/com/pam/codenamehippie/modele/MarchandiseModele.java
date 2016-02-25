package com.pam.codenamehippie.modele;

import com.google.gson.annotations.SerializedName;

/**
 * Classe de base pour toutes les types de marchandises.
 *
 * @param <T>
 *         La classe qui hérite de marchandise modèle.
 *
 * @see BaseModele
 */
public abstract class MarchandiseModele<T extends MarchandiseModele<T>> extends BaseModele<T> {

    @SerializedName("nom")
    protected String nom;
    @SerializedName("description")
    protected String description;
    @SerializedName("quantite")
    protected Double quantite = 0.00d;
    @SerializedName("unite")
    protected String uniteDeQuantite;
    @SerializedName("marchandise_etat")
    protected String etat;
    @SerializedName("valeur")
    protected Double valeur = 0.00d;
    @SerializedName("marchandise_statut")
    protected String statut;
    @SerializedName("organisme")
    protected OrganismeModele organisme;

    protected transient volatile String cacheQuantiteString;

    public OrganismeModele getOrganisme() {return this.organisme;}

    @SuppressWarnings("unchecked")
    public T setOrganisme(OrganismeModele organisme) {
        this.organisme = organisme;
        return (T) this;
    }

    public String getNom() {
        return this.nom;
    }

    @SuppressWarnings("unchecked")
    public T setNom(String nom) {
        this.nom = nom;
        return (T) this;
    }

    public String getDescription() {
        return this.description;
    }

    @SuppressWarnings("unchecked")
    public T setDescription(String description) {
        this.description = description;
        return (T) this;
    }

    public Double getQuantite() {
        return this.quantite;
    }

    @SuppressWarnings("unchecked")
    public T setQuantite(Double quantite) {
        this.quantite = quantite;
        this.cacheQuantiteString = null;
        return (T) this;
    }

    public String getQuantiteString() {
        if (this.cacheQuantiteString == null) {
            if (this.uniteDeQuantite != null) {
                this.cacheQuantiteString = this.quantite.toString() + " " + this.uniteDeQuantite;
            } else {
                this.cacheQuantiteString = this.quantite.toString();
            }
        }
        return this.cacheQuantiteString;
    }

    public String getUniteDeQuantite() {
        return this.uniteDeQuantite;
    }

    @SuppressWarnings("unchecked")
    public T setUniteDeQuantite(String uniteDeQuantite) {
        this.uniteDeQuantite = uniteDeQuantite;
        this.cacheQuantiteString = null;
        return (T) this;
    }

    public String getEtat() {
        return this.etat;
    }

    @SuppressWarnings("unchecked")
    public T setEtat(String etat) {
        this.etat = etat;
        return (T) this;
    }

    public Double getValeur() {
        return this.valeur;
    }

    @SuppressWarnings("unchecked")
    public T setValeur(Double valeur) {
        this.valeur = valeur;
        return (T) this;
    }

    public String getStatut() {
        return this.statut;
    }

    @SuppressWarnings("unchecked")
    public T setStatut(String statut) {
        this.statut = statut;
        return (T) this;
    }

}

