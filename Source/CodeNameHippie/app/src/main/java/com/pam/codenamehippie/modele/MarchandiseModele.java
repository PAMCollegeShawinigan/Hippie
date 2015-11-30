package com.pam.codenamehippie.modele;

/**
 * Créé par Carl St-Louis le 23-11-2015.
 */
public class MarchandiseModele extends BaseModele {

    protected String nom;
    protected String description;
    protected MarchandiseStatut statut;


    public MarchandiseModele(BaseModeleDepot depot, int id) {
        super(depot, id);
    }
}

