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
     * @param json
     *   de l'objet TransactionModele
     *
     * @return une nouvelle instance de TransactionModele vide ou null si la transaction existe déjà
     */
    @Override
    public TransactionModele ajouterModele(String json) {
        TransactionModele modele = this.gson.fromJson(json, TransactionModele.class);
        if (this.modeles.get(modele.getId()) == null) {
            this.modeles.put(modele.getId(), modele);
            // todo: requête au serveur pour ajouter une transaction
            return modele;
        } else {
            return null;
        }
    }

    /**
     * Modifier un TransactionModele présent dans le dépôt
     *
     * @param modele
     *   de l'objet TransactionModele
     *
     * @return un TransactionModele ou null si inexistant dans le dépôt
     */
    @Override
    public TransactionModele modifierModele(TransactionModele modele) {
        TransactionModele oldModele = this.modeles.get(modele.getId());

        if (oldModele != null) {
            // todo: requête au serveur pour modification sur la transaction
            return oldModele;
        } else {
            return null;
        }
    }

    /**
     * Supprimer un TransactionModele présent dans le dépôt
     *
     * @param modele
     *   de l'objet TransactionModele
     *
     * @return un TransactionModele ou null si inexistant dans le dépôt
     */
    @Override
    public TransactionModele supprimerModele(TransactionModele modele) {
        TransactionModele oldModele = this.modeles.put(modele.getId(), null);
        if (oldModele != null) {
            // todo: requête au serveur pour suppression d'une transaction
        }
        return oldModele;
    }
}
