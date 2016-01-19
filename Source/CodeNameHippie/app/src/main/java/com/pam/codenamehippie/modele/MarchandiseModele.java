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
    @SerializedName(value = "description")
    private String description;
    @SerializedName("quantite")
    private Double quantite;
    @SerializedName("marchandise_etat")
    private String etat;
    @SerializedName("valeur")
    private Integer valeur;
    @SerializedName("marchandise_statut")
    private String statut;

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

    public String getEtat() {
        return this.etat;
    }

    @SuppressWarnings(value = {"unchecked"})
    public T setEtat(String etat) {
        this.etat = etat;
        return (T) this;
    }

    public Integer getValeur() {
        return this.valeur;
    }

    @SuppressWarnings(value = {"unchecked"})
    public T setValeur(Integer valeur) {
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

