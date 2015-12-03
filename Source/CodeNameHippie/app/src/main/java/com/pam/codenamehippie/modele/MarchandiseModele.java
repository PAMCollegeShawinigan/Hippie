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
    private double quantite;
    @SerializedName("etat")
    private String etat;
    @SerializedName("valeur")
    private int valeur;
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

    public double getQuantite() {
        return this.quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public String getEtat() {
        return this.etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public int getValeur() {
        return this.valeur;
    }

    public void setValeur(int valeur) {
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

