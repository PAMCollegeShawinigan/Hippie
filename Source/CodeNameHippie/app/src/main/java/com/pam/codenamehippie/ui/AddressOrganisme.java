package com.pam.codenamehippie.ui;


/**
 * Created by BEG-163 on 2016-01-12.
 */
public class AddressOrganisme {

    private Integer noCivique;

    private String typeRue;

    private String nom;

    private String app;

    private String ville;

    private String province;

    private String codePostal;

    private String pays;

    public Integer getNoCivique() {
        return this.noCivique;
    }

    public AddressOrganisme setNoCivique(Integer noCivique) {
        this.noCivique = noCivique;
        return this;
    }

    public String getTypeRue() {
        return this.typeRue;
    }

    public AddressOrganisme setTypeRue(String typeRue) {
        this.typeRue = typeRue;
        return this;
    }

    public String getNom() {
        return this.nom;
    }

    public AddressOrganisme setNom(String nom) {
        this.nom = nom;
        return this;
    }

    public String getApp() {
        return this.app;
    }

    public AddressOrganisme setApp(String app) {
        this.app = app;
        return this;
    }

    public String getVille() {
        return this.ville;
    }

    public AddressOrganisme setVille(String ville) {
        this.ville = ville;
        return this;
    }

    public String getProvince() {
        return this.province;
    }

    public AddressOrganisme setProvince(String province) {
        this.province = province;
        return this;
    }

    public String getCodePostal() {
        return this.codePostal;
    }

    public AddressOrganisme setCodePostal(String codePostal) {
        this.codePostal = codePostal;
        return this;
    }

    public String getPays() {
        return this.pays;
    }

    public AddressOrganisme setPays(String pays) {
        this.pays = pays;
        return this;
    }


}
