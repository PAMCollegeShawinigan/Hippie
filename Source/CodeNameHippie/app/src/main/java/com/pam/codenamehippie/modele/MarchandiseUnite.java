package com.pam.codenamehippie.modele;

/**
 * Créé par Carl St-Louis le 23-11-2015.
 */
public enum MarchandiseUnite {

    kilogramme("Kg"),
    livres("Lbs"),
    once("oz"),
    mililitre("ml"),
    litre("L"),
    unitaire("Unité");

    private String unite = "";

    // Constructeur
    MarchandiseUnite(String unite){
        this.unite = unite;
    }

    public String toString(){
        return unite;
    }
}
