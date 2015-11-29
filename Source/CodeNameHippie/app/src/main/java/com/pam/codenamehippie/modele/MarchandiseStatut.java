package com.pam.codenamehippie.modele;

/**
 * Créé par Carl St-Louis le 23-11-2015.
 */
public enum MarchandiseStatut {
    disponible("Disponible"),
    reserve("Réservé"),
    collecte("Collecté"),
    enTraitement("En traitement");

    private String statut = "";

    // Constructeur
    MarchandiseStatut(String statut){
        this.statut = statut;
    }

    public String toString(){
        return statut;
    }
}
