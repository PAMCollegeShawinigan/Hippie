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

    public String getNom() {
        return this.nom;
    }

    public MarchandiseModele setNom(String nom) {
        this.nom = nom;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public MarchandiseModele setDescription(String description) {
        this.description = description;
        return this;
    }

    public Double getQuantite() {
        return this.quantite;
    }

    public MarchandiseModele setQuantite(Double quantite) {
        this.quantite = quantite;
        return this;
    }

    public String getEtat() {
        return this.etat;
    }

    public MarchandiseModele setEtat(String etat) {
        this.etat = etat;
        return this;
    }

    public Integer getValeur() {
        return this.valeur;
    }

    public MarchandiseModele setValeur(Integer valeur) {
        this.valeur = valeur;
        return this;
    }

    public String getStatut() {
        return this.statut;
    }

    public MarchandiseModele setStatut(String statut) {
        this.statut = statut;
        return this;
    }

}

