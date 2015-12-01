package com.pam.codenamehippie.modele;

import android.support.v4.util.SimpleArrayMap;

public abstract class BaseModeleDepot {

    /**
     * Conteneur de modèles.
     */

    protected String url = null;

    /**
     * Méthode qui recherche selon l'id de l'objet reçu en paramètre.
     *
     * @param id de l'objet
     * @return une instance du modèle correspondant au id reçu en paramètre.
     */
    public BaseModele rechercherParId(int id) {
        // Todo: requête au serveur
        throw new AbstractMethodError("non implémenté");
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

    public BaseModele supprimerModele(int id) {
        throw new AbstractMethodError("non implémenté");
    }


}
