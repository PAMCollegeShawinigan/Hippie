package com.pam.codenamehippie.modele;

import android.support.v4.util.SimpleArrayMap;

/**
 * Créé par Carl St-Louis le 23-11-2015.
 */
public class MarchandiseModeleDepot extends BaseModeleDepot {

    private SimpleArrayMap<Integer, MarchandiseModele> modeles = null;

    /**
     * Contruction du dépot pour modèle Marchandise
     */
    public MarchandiseModeleDepot() {
        this.modeles = new SimpleArrayMap<>();
    }


    /**
     * Rechercher un MarchandiseModele par ID dans le dépôt
     *
     * @param id de l'objet MarchandiseModele
     * @return un MarchandiseModele ou null si inexistant dans le dépôt
     */
    @Override
    public MarchandiseModele rechercherParId(int id) {
        MarchandiseModele modele = this.modeles.get(id);
        if (modele != null) {
            return this.modeles.get(id);
        } else {
            return null;
        }
    }


    /**
     * Ajouter un nouveau MarchandiseModele dans le dépôt
     *
     * @param id de l'objet MarchandiseModele
     * @return une nouvelle instance de MarchandiseModele vide ou null si la marchansise existe déjà
     */
    @Override
    public MarchandiseModele ajouterModele(int id) {
        MarchandiseModele modele = new MarchandiseModele(this, id);

        if (this.modeles.get(id) == null){
            this.modeles.put(id,modele);
            // todo: requête au serveur pour ajouter une marchandise
            return modele;
        } else {
            return  null;
        }
    }


    /**
     * Modifier un MarchandiseModele présent dans le dépôt
     *
     * @param id de l'objet MarchandiseModele
     * @return un MarchandiseModele ou null si inexistant dans le dépôt
     */
    @Override
    public MarchandiseModele modifierModele(int id) {
        MarchandiseModele modele = this.modeles.get(id);

        if (modele != null) {
            // todo: requête au serveur pour modification sur la marchandise
            return modele;
        } else {
            return null;
        }
    }

    /**
     * Supprimer un MarchandiseModele présent dans le dépôt
     *
     * @param id de l'objet MarchandiseModele
     * @return un MarchandiseModele ou null si inexistant dans le dépôt
     */
    @Override
    public MarchandiseModele supprimerModele(int id) {
        MarchandiseModele modele = this.modeles.put(id,null);
        if (modele != null){
            // todo: requête au serveur pour suppression de la marchandise
        }
        return modele;
    }
}
