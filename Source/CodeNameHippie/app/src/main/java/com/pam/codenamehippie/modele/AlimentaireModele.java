package com.pam.codenamehippie.modele;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Créé par Carl St-Louis le 23-11-2015.
 */
public class AlimentaireModele extends MarchandiseModele {

    @SerializedName("nom")
    private String nom;
    @SerializedName("description")
    private String description;
    @SerializedName("quantite")
    private double quantite;
    @SerializedName("etat")
    private String etat;
    @SerializedName("qtee_unite")
    private int qteeUnite;
    @SerializedName("valeur")
    private int valeur;
    @SerializedName("statut")
    private String statut;
    @SerializedName("type_alimentaire")
    private String typeAlimentaire;
    @SerializedName("date_peremption")
    private Date datePeremption;


    // ------------------ Début Accesseurs et mutateurs

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

    public int getQteeUnite() {
        return this.qteeUnite;
    }

    public void setQteeUnite(int qteeUnite) {
        this.qteeUnite = qteeUnite;
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

    public String getTypeAlimentaire() {
        return this.typeAlimentaire;
    }

    public void setTypeAlimentaire(String typeAlimentaire) {
        this.typeAlimentaire = typeAlimentaire;
    }

    public Date getDatePeremption() {
        return this.datePeremption;
    }

    public void setDatePeremption(Date datePeremption) {
        this.datePeremption = datePeremption;
    }
    // ------------------ Fin Accesseurs et mutateurs
}
