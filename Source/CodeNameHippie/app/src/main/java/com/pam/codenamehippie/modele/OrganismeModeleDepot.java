package com.pam.codenamehippie.modele;

import android.support.v4.util.SimpleArrayMap;

/**
 * Créé par Carl St-Louis le 23-11-2015.
 */
public class OrganismeModeleDepot extends BaseModeleDepot<OrganismeModele> {

    /**
     * Contruction du dépot pour modèle Organisme
     */
    public OrganismeModeleDepot() {
        this.modeles = new SimpleArrayMap<>();
    }

    /**
     * Rechercher un OrganismeModele par ID dans le dépôt
     *
     * @param id
     *   de l'objet OrganismeModele
     *
     * @return un OrganismeModele ou null si inexistant dans le dépôt
     */
    @Override
    public OrganismeModele rechercherParId(int id) {
        OrganismeModele modele = this.modeles.get(id);
        if (modele != null) {
            return this.modeles.get(id);
        } else {
            return null;
        }
    }

    /**
     * Ajouter un nouveau OrganismeModele dans le dépôt
     *
     * @param id
     *   de l'objet OrganismeModele
     *
     * @return une nouvelle instance de OrganismeModele vide ou null si l'organisme existe déjà
     */
    @Override
    public OrganismeModele ajouterModele(int id) {
        OrganismeModele modele = new OrganismeModele(this, id);

        if (this.modeles.get(id) == null) {
            this.modeles.put(id, modele);
            // todo: requête au serveur pour ajouter un organisme
            return modele;
        } else {
            return null;
        }
    }

    /**
     * Modifier un OrganismeModele présent dans le dépôt
     *
     * @param id
     *   de l'objet OrganismeModele
     *
     * @return un OrganismeModele ou null si inexistant dans le dépôt
     */
    @Override
    public OrganismeModele modifierModele(int id) {
        OrganismeModele modele = this.modeles.get(id);

        if (modele != null) {
            // todo: requête au serveur pour modification sur l'organisme
            return modele;
        } else {
            return null;
        }
    }

    /**
     * Supprimer un OrganismeModele présent dans le dépôt
     *
     * @param id
     *   de l'objet OrganismeModele
     *
     * @return un OrganismeModele ou null si inexistant dans le dépôt
     */
    @Override
    public OrganismeModele supprimerModele(int id) {
        OrganismeModele modele = this.modeles.put(id, null);
        if (modele != null) {
            // todo: requête au serveur pour suppression d'un organisme
        }
        return modele;
    }
}
