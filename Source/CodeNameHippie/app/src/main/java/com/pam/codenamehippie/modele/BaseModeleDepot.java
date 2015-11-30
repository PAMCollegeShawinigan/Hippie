package com.pam.codenamehippie.modele;

import android.support.v4.util.SimpleArrayMap;

import java.util.ArrayList;

public abstract class BaseModeleDepot {

    /**
     * Conteneur de modèles.
     */
    protected SimpleArrayMap<Integer, ? super BaseModele> modeles = null;
    protected String url = null;

    /**
     * Méthode qui recherche selon l'id de l'objet reçu en paramètre.
     *
     * @param id de l'objet
     * @return une instance du modèle correspondant au id reçu en paramètre.
     */
    public BaseModele rechercheParId(int id){
        // Todo: requête au serveur
        return (BaseModele) this.modeles.get(id);
    }

    /**
     * Méthode qui modifie selon l'id de l'objet reçu en paramètre.
     *
     * @param id de l'objet
     * @return une instance du modèle correspondant au id reçu en paramètre.
     */
    public BaseModele modifierModele(int id) {
        throw new AbstractMethodError("non implémenté");
    }

    public BaseModele ajouterModele(int id) {
        throw new AbstractMethodError("non implémenté");
    }

    public BaseModele supprimerModèle(int id) {
        throw new AbstractMethodError("non implémenté");
    }


}
