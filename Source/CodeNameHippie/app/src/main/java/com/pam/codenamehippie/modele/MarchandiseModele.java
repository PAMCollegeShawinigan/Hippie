package com.pam.codenamehippie.modele;

import com.google.gson.annotations.SerializedName;

/**
 * Créé par Carl St-Louis le 23-11-2015.
 */
public class MarchandiseModele extends BaseModele {

    @SerializedName("nom")
    private String nom;
    @SerializedName("description")
    private String description;
    @SerializedName("quantite")
    private Double quantite;
    @SerializedName("etat")
    private String etat;
    @SerializedName("valeur")
    private Integer valeur;
    @SerializedName("statut")
    private String statut;

    // ------------------------------ Début Accesseur et Mutateur
    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getQuantite() {
        return this.quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public String getEtat() {
        return this.etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Integer getValeur() {
        return this.valeur;
    }

    public void setValeur(Integer valeur) {
        this.valeur = valeur;
    }

    public String getStatut() {
        return this.statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
    // ------------------------------ Fin Accesseur et Mutateur

}

