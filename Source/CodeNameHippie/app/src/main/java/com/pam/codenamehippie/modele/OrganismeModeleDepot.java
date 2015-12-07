package com.pam.codenamehippie.modele;

import com.squareup.okhttp.OkHttpClient;

/**
 * Créé par Carl St-Louis le 23-11-2015.
 */

public class OrganismeModeleDepot extends BaseModeleDepot<OrganismeModele> {

    /**
     * Contruction du dépot pour modèle Organisme
     */
    public OrganismeModeleDepot(OkHttpClient httpClient) {
        super(httpClient);
        this.url = this.url.newBuilder().addPathSegment("organisme").build();
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
    public OrganismeModele rechercherParId(Integer id) {
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
     * @param json
     *   de l'objet OrganismeModele
     *
     * @return une nouvelle instance de OrganismeModele vide ou null si l'organisme existe déjà
     */
    @Override
    public OrganismeModele ajouterModele(String json) {
        OrganismeModele modele = this.gson.fromJson(json, OrganismeModele.class);
        if (this.modeles.get(modele.getId()) == null) {
            this.modeles.put(modele.getId(), modele);
            // todo: requête au serveur pour ajouter un organisme
            return modele;
        } else {
            return null;
        }
    }

    /**
     * Modifier un OrganismeModele présent dans le dépôt
     *
     * @param modele
     *   de l'objet OrganismeModele
     *
     * @return un OrganismeModele ou null si inexistant dans le dépôt
     */
    @Override
    public OrganismeModele modifierModele(OrganismeModele modele) {
        OrganismeModele oldModele = this.modeles.get(modele.getId());

        if (oldModele != null) {
            // todo: requête au serveur pour modification sur l'organisme
            return oldModele;
        } else {
            return null;
        }
    }

    /**
     * Supprimer un OrganismeModele présent dans le dépôt
     *
     * @param modele
     *   de l'objet OrganismeModele
     *
     * @return un OrganismeModele ou null si inexistant dans le dépôt
     */
    @Override
    public OrganismeModele supprimerModele(OrganismeModele modele) {
        OrganismeModele oldModele = this.modeles.put(modele.getId(), null);
        if (oldModele != null) {
            // todo: requête au serveur pour suppression d'un organisme
        }
        return oldModele;
    }
}
