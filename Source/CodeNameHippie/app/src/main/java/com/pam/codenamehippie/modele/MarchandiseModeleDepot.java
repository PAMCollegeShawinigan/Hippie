package com.pam.codenamehippie.modele;

import com.squareup.okhttp.OkHttpClient;

/**
 * Créé par Carl St-Louis le 23-11-2015.
 */

public class MarchandiseModeleDepot extends BaseModeleDepot<MarchandiseModele> {

    public MarchandiseModeleDepot(OkHttpClient httpClient) {
        super(httpClient);
        this.url = this.url.newBuilder().addPathSegment("marchandise").build();
    }

//    /**
//     * Rechercher un MarchandiseModele par ID dans le dépôt
//     *
//     * @param id
//     *   de l'objet MarchandiseModele
//     *
//     * @return un MarchandiseModele ou null si inexistant dans le dépôt
//     */
//    @Override
//    public MarchandiseModele rechercherParId(Integer id) {
//        MarchandiseModele modele = this.modeles.get(id);
//        if (modele != null) {
//            return this.modeles.get(id);
//        } else {
//            return null;
//        }
//    }

//    /**
//     * Ajouter un nouveau MarchandiseModele dans le dépôt
//     *
//     * @param json
//     *   de l'objet MarchandiseModele
//     *
//     * @return une nouvelle instance de MarchandiseModele vide ou null si la marchansise existe
// déjà
//     */
//    @Override
//    public MarchandiseModele ajouterModele(String json) {
//        MarchandiseModele modele = gson.fromJson(json, MarchandiseModele.class);
//
//        if (this.modeles.get(modele.getId()) == null) {
//            this.modeles.put(modele.getId(), modele);
//            // todo: requête au serveur pour ajouter une marchandise
//            return modele;
//        } else {
//            return null;
//        }
//    }

//    /**
//     * Modifier un MarchandiseModele présent dans le dépôt
//     *
//     * @param modele
//     *   de l'objet MarchandiseModele
//     *
//     * @return un MarchandiseModele ou null si inexistant dans le dépôt
//     */
//    @Override
//    public MarchandiseModele modifierModele(MarchandiseModele modele) {
//        MarchandiseModele oldModele = this.modeles.get(modele.getId());
//
//        if (oldModele != null) {
//            // todo: requête au serveur pour modification sur la marchandise
//            return oldModele;
//        } else {
//            return null;
//        }
//    }

//    /**
//     * Supprimer un MarchandiseModele présent dans le dépôt
//     *
//     * @param modele
//     *   de l'objet MarchandiseModele
//     *
//     * @return un MarchandiseModele ou null si inexistant dans le dépôt
//     */
//    @Override
//    public MarchandiseModele supprimerModele(MarchandiseModele modele) {
//        MarchandiseModele oldModele = this.modeles.put(modele.getId(), null);
//        if (oldModele != null) {
//            // todo: requête au serveur pour suppression de la marchandise
//        }
//        return oldModele;
//    }
}
