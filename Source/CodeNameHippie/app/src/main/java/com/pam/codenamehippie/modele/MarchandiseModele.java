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
    private String nom;
    @SerializedName("description")
    private String description;
    @SerializedName("quantite")
    private Double quantite = 0.00d;
    @SerializedName("unite")
    private String uniteDeQuantite;
    @SerializedName("marchandise_etat")
    private String etat;
    @SerializedName("valeur")
    private Long valeur = 0L;
    @SerializedName("marchandise_statut")
    private String statut;
    @SerializedName("organisme")
    private OrganismeModele organisme;

    public OrganismeModele getOrganisme() {return this.organisme;}

    @SuppressWarnings(value = {"unchecked"})
    public T setOrganisme(OrganismeModele organisme) {
        this.organisme = organisme;
        return (T) this;
    }

    public String getNom() {
        return this.nom;
    }

    @SuppressWarnings(value = {"unchecked"})
    public T setNom(String nom) {
        this.nom = nom;
        return (T) this;
    }

    public String getDescription() {
        return this.description;
    }

    @SuppressWarnings(value = {"unchecked"})
    public T setDescription(String description) {
        this.description = description;
        return (T) this;
}

    public Double getQuantite() {
        return this.quantite;
    }

    @SuppressWarnings(value = {"unchecked"})
    public T setQuantite(Double quantite) {
        this.quantite = quantite;
        return (T) this;
    }

    public String getQuantiteString() {
        if (this.uniteDeQuantite != null) {
            return this.quantite.toString() + " " + this.uniteDeQuantite;
        } else {
            return this.quantite.toString();
        }
    }

    public String getUniteDeQuantite() {
        return this.uniteDeQuantite;
    }

    @SuppressWarnings(value = {"unchecked"})
    public T setUniteDeQuantite(String uniteDeQuantite) {
        this.uniteDeQuantite = uniteDeQuantite;
        return (T) this;
    }

    public String getEtat() {
        return this.etat;
    }

    @SuppressWarnings(value = {"unchecked"})
    public T setEtat(String etat) {
        this.etat = etat;
        return (T) this;
    }

    public Long getValeur() {
        return this.valeur;
    }

    @SuppressWarnings(value = {"unchecked"})
    public T setValeur(Long valeur) {
        this.valeur = valeur;
        return (T) this;
    }

    public String getStatut() {
        return this.statut;
    }

    @SuppressWarnings(value = {"unchecked"})
    public T setStatut(String statut) {
        this.statut = statut;
        return (T) this;
    }

}

