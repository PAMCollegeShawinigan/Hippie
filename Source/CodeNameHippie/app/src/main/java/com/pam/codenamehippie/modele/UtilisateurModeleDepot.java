package com.pam.codenamehippie.modele;

import android.support.v4.util.SimpleArrayMap;

/**
 * Créé par Carl St-Louis le 23-11-2015.
 */

public class UtilisateurModeleDepot extends BaseModeleDepot<UtilisateurModele> {

    /**
     * Contruction du dépot pour modèle Utilisateur
     */
    public UtilisateurModeleDepot() {
        this.modeles = new SimpleArrayMap<>();
    }

    /**
     * Rechercher un UtilisateurModele par ID dans le dépôt
     *
     * @param id
     *   de l'objet UtilisateurModele
     *
     * @return un UtilisateurModele ou null si inexistant dans le dépôt
     */
    @Override
    public UtilisateurModele rechercherParId(int id) {
        UtilisateurModele modele = this.modeles.get(id);
        if (modele != null) {
            return this.modeles.get(id);
        } else {
            return null;
        }
    }


    /**
     * Ajouter un nouvel UtilisateurModele dans le dépôt
     *
     * @param id
     *   de l'objet UtilisateurModele
     *
     * @return une nouvelle instance de UtilisateurModele vide ou null si l'utilisateur existe déjà
     */
    @Override
    public UtilisateurModele ajouterModele(int id) {
        UtilisateurModele modele = new UtilisateurModele(this, id);
        if (this.modeles.get(id) == null) {
            this.modeles.put(id, modele);
            // todo: requête au serveur pour ajouter un utilisateur
            return modele;
        } else {
            return null;
        }
    }

    /**
     * Modifier un UtilisateurModele présent dans le dépôt
     *
     * @param id
     *   de l'objet UtilisateurModele
     *
     * @return un UtilisateurModele ou null si inexistant dans le dépôt
     */
    @Override
    public UtilisateurModele modifierModele(int id) {
        UtilisateurModele modele = this.modeles.get(id);

        if (modele != null) {
            // todo: requête au serveur pour modification utilisateur
            return modele;
        } else {
            return null;
        }
    }

    /**
     * Supprimer un UtilisateurModele présent dans le dépôt
     *
     * @param id
     *   de l'objet UtilisateurModele
     *
     * @return un UtilisateurModele ou null si inexistant dans le dépôt
     */
    @Override
    public UtilisateurModele supprimerModele(int id) {
        UtilisateurModele modele = this.modeles.put(id, null);
        if (modele != null) {
            // todo: requête au serveur pour suppression utilisateur
        }
        return modele;
    }
}
