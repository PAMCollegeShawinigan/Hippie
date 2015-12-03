package com.pam.codenamehippie.modele;

import android.support.v4.util.SimpleArrayMap;

/**
 * Créé par Carl St-Louis le 23-11-2015.
 */
public class TransactionModeleDepot extends BaseModeleDepot<TransactionModele> {

    /**
     * Contruction du dépot pour modèle Transaction
     */
    public TransactionModeleDepot() {
        this.modeles = new SimpleArrayMap<>();
    }

    /**
     * Rechercher un TransactionModele par ID dans le dépôt
     *
     * @param id
     *   de l'objet TransactionModele
     *
     * @return un TransactionModele ou null si inexistant dans le dépôt
     */
    @Override
    public TransactionModele rechercherParId(int id) {
        TransactionModele modele = this.modeles.get(id);
        if (modele != null) {
            return this.modeles.get(id);
        } else {
            return null;
        }
    }

    /**
     * Ajouter un nouveau TransactionModele dans le dépôt
     *
     * @param id
     *   de l'objet TransactionModele
     *
     * @return une nouvelle instance de TransactionModele vide ou null si la transaction existe déjà
     */
    @Override
    public TransactionModele ajouterModele(int id) {
        TransactionModele modele = new TransactionModele(this, id);

        if (this.modeles.get(id) == null) {
            this.modeles.put(id, modele);
            // todo: requête au serveur pour ajouter une transaction
            return modele;
        } else {
            return null;
        }
    }

    /**
     * Modifier un TransactionModele présent dans le dépôt
     *
     * @param id
     *   de l'objet TransactionModele
     *
     * @return un TransactionModele ou null si inexistant dans le dépôt
     */
    @Override
    public TransactionModele modifierModele(int id) {
        TransactionModele modele = this.modeles.get(id);

        if (modele != null) {
            // todo: requête au serveur pour modification sur la transaction
            return modele;
        } else {
            return null;
        }
    }

    /**
     * Supprimer un TransactionModele présent dans le dépôt
     *
     * @param id
     *   de l'objet TransactionModele
     *
     * @return un TransactionModele ou null si inexistant dans le dépôt
     */
    @Override
    public TransactionModele supprimerModele(int id) {
        TransactionModele modele = this.modeles.put(id, null);
        if (modele != null) {
            // todo: requête au serveur pour suppression d'une transaction
        }
        return modele;
    }
}
