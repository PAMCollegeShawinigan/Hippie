package com.pam.codenamehippie.modele;

import android.content.Context;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class AlimentaireModeleDepot extends BaseModeleDepot<AlimentaireModele> {

    private static final String TAG = AlimentaireModeleDepot.class.getSimpleName();

    private final HttpUrl listeUniteUrl;
    private final HttpUrl listeTypeAlimentaireUrl;

    private volatile ArrayList<DescriptionModel> listeUnitee;
    private volatile ArrayList<DescriptionModel> listeTypeAlimentaire;

    public AlimentaireModeleDepot(Context context, OkHttpClient httpClient) {
        super(context, httpClient);
        HttpUrl baseListeUrl = this.url.newBuilder().addPathSegment("liste").build();
        this.listeUniteUrl = baseListeUrl.newBuilder().addPathSegment("unite").build();
        this.listeTypeAlimentaireUrl =
                baseListeUrl.newBuilder().addPathSegment("alimentaire").build();
        this.url = this.url.newBuilder().addPathSegment("marchandise").build();
    }

    public synchronized ArrayList<DescriptionModel> getListeUnitee() {
        return this.listeUnitee;
    }

    public synchronized ArrayList<DescriptionModel> getListeTypeAlimentaire() {
        return this.listeTypeAlimentaire;
    }

    /**
     * Permet de peupler les items pour les spinner.
     * <p/>
     * Cette methode est asynchrone et retourne immédiatement
     */
    public void peuplerLesListes() {
        //TODO: Refactoriser la méthode pour passer un paramètre
        Request listeUniteRequete = new Request.Builder().url(this.listeUniteUrl).get().build();
        Request listeTypeAlimentaireRequete =
                new Request.Builder().url(this.listeTypeAlimentaireUrl).get().build();
        this.httpClient.newCall(listeUniteRequete).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                // TODO: Mettre un toast ou whatever
                Log.e(TAG, "Request failed: " + request.toString(), e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "Request failed: " + response.toString());
                } else {
                    Type type = new TypeToken<ArrayList<DescriptionModel>>() { }.getType();
                    // Ajouter un String "Faites votre choix..." à l'indice 0
                    ArrayList<DescriptionModel> temp = new ArrayList<DescriptionModel>();
                    temp.add(new DescriptionModel());
                    AlimentaireModeleDepot.this.listeUnitee =
                            gson.fromJson(response.body().charStream(), type);
                    temp.addAll(AlimentaireModeleDepot.this.listeUnitee);
                    AlimentaireModeleDepot.this.listeUnitee = temp;
                    Log.d(TAG,
                          "Liste type alimentaire: " +
                          AlimentaireModeleDepot.this.listeUnitee.toString()
                         );
                }
            }
        });
        this.httpClient.newCall(listeTypeAlimentaireRequete).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                // TODO: Mettre un toast ou whatever
                Log.e(TAG, "Request failed: " + request.toString(), e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "Request failed: " + response.toString());
                } else {
                    Type type = new TypeToken<ArrayList<DescriptionModel>>() { }.getType();
                    // Ajouter un String "Faites votre choix..." à l'indice 0
                    ArrayList<DescriptionModel> temp = new ArrayList<DescriptionModel>();
                    temp.add(new DescriptionModel());
                    AlimentaireModeleDepot.this.listeTypeAlimentaire =
                            gson.fromJson(response.body().charStream(), type);
                    temp.addAll(AlimentaireModeleDepot.this.listeTypeAlimentaire);
                    AlimentaireModeleDepot.this.listeTypeAlimentaire = temp;
                    Log.d(TAG,
                          "Liste type alimentaire: " +
                          AlimentaireModeleDepot.this.listeTypeAlimentaire.toString()
                         );
                }
            }
        });
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
