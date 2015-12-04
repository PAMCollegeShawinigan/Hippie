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
    private Double quantite;
    @SerializedName("etat")
    private String etat;
    @SerializedName("qtee_unite")
    private Integer qteeUnite;
    @SerializedName("valeur")
    private Integer valeur;
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

    public Integer getQteeUnite() {
        return this.qteeUnite;
    }

    public void setQteeUnite(Integer qteeUnite) {
        this.qteeUnite = qteeUnite;
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
