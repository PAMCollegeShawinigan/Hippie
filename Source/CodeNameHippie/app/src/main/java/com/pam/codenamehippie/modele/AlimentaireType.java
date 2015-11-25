package com.pam.codenamehippie.modele;

/**
 * Créé par Carl St-Louis le 23-11-2015.
 */
public enum AlimentaireType {

    surgele("Aliments surgelés"),
    boisson("Boisson - Eau - Jus"),
    boulangeriePatisserie("Boulangerie - Pâtisserie"),
    cafeTheTisane("Café - Thé - Tisanes"),
    charcuterie("Charcuterie"),
    collationsFriandises("Collations - Friandises"),
    condimentsHuilesSauces("Condiments - Huiles - Sauces"),
    confituresTartinadesSirop("Confitures - Tartinades - Sirop"),
    fruitsDeMerPoissons("Fruits de mer - Poissons"),
    fruitsLegumes("Fruits - Légumes"),
    patesFarinesOeufs("Pates - Farines - Oeufs"),
    pretAMangerConserves("Prêts à manger - Conserves"),
    produitsLaitiersGlaces("Produits laitiers - Glaces"),
    dejeunerCereales("Petit déjeuner - Céréales"),
    viandes("Viandes"),
    volailles("Volailles");

    private String typeAliment = "";

    // Constructeur
    AlimentaireType(String typeAliment) {
        this.typeAliment = typeAliment;
    }

    public String toString() {
        return typeAliment;
    }

}
