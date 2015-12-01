package com.pam.codenamehippie.modele;

import com.google.gson.annotations.SerializedName;

/**
 * Créé par Carl St-Louis le 2015-12-01.
 */
public class AdresseModele extends BaseModele {

    @SerializedName("no_civique")
    private int no_civique;
    @SerializedName("type_rue")
    private String type_rue;
    @SerializedName("nom")
    private String nom;
    @SerializedName("app")
    private String app;
    @SerializedName("ville")
    private String ville;
    @SerializedName("province")
    private String province;
    @SerializedName("code_postal")
    private String code_postal;
    @SerializedName("pays")
    private String pays;


    // ------------------------------ Début Constructeur
    public AdresseModele(BaseModeleDepot depot, int id) {
        super(depot, id);
    }

    // todo: Vérifier les champs obligatoires pour nos constructeurs

    // ------------------------------ Fin Constructeur

    // ------------------------------ Début Accesseur et Mutateur
    public int getNo_civique() {
        return no_civique;
    }

    public void setNo_civique(int no_civique) {
        this.no_civique = no_civique;
    }

    public String getType_rue() {
        return type_rue;
    }

    public void setType_rue(String type_rue) {
        this.type_rue = type_rue;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCode_postal() {
        return code_postal;
    }

    public void setCode_postal(String code_postal) {
        this.code_postal = code_postal;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }
    // ------------------------------ Fin Accesseur et Mutateur
}
