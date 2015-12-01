package com.pam.codenamehippie.modele;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
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
    private int qtee_unite;
    @SerializedName("valeur")
    private int valeur;
    @SerializedName("statut")
    private String statut;
    @SerializedName("type_alimentaire")
    private String type_alimentaire;
    @SerializedName("date_peremption")
    private Date date_peremption;


    // ------------------ Début constructeurs
    public AlimentaireModele(BaseModeleDepot depot, int id) {
        super(depot, id);
    }

    // todo: Vérifier les champs obligatoires pour nos constructeurs

    // ------------------ Fin constructeurs

    // ------------------ Début Accesseurs et mutateurs

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public int getQtee_unite() {
        return qtee_unite;
    }

    public void setQtee_unite(int qtee_unite) {
        this.qtee_unite = qtee_unite;
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getType_alimentaire() {
        return type_alimentaire;
    }

    public void setType_alimentaire(String type_alimentaire) {
        this.type_alimentaire = type_alimentaire;
    }

    public Date getDate_peremption() {
        return date_peremption;
    }

    public void setDate_peremption(Date date_peremption) {
        this.date_peremption = date_peremption;
    }
    // ------------------ Fin Accesseurs et mutateurs
}
