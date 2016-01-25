package com.pam.codenamehippie.modele;

import android.content.Context;
import android.util.Log;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AlimentaireModeleDepot extends BaseModeleDepot<AlimentaireModele> {

    private static final String TAG = AlimentaireModeleDepot.class.getSimpleName();

    private final HttpUrl listeUniteUrl;
    private final HttpUrl listeTypeAlimentaireUrl;
    private final HttpUrl listeDonUrl;
    private final HttpUrl listeDonDispoUrl;

    private volatile ArrayList<DescriptionModel> listeUnitee;
    private volatile ArrayList<TypeAlimentaireModele> listeTypeAlimentaire;
    private volatile ArrayList<AlimentaireModele> listeDon;
    private volatile ArrayList<AlimentaireModele> listeDonDispo;

    public AlimentaireModeleDepot(Context context, OkHttpClient httpClient) {
        super(context, httpClient);
        HttpUrl baseListeUrl = this.url.newBuilder().addPathSegment("liste").build();
        this.listeUniteUrl = baseListeUrl.newBuilder().addPathSegment("unite").build();
        this.listeTypeAlimentaireUrl =
                baseListeUrl.newBuilder().addPathSegment("alimentaire").build();
        this.listeDonUrl =
                this.url.newBuilder().addPathSegment("don").addPathSegment("listedon").build();

        this.listeDonDispoUrl =
                this.url.newBuilder().addPathSegment("don").addPathSegment("listedondispo").build();

        this.url = this.url.newBuilder().addPathSegment("alimentaire").build();
        this.ajoutUrl = this.url.newBuilder().addPathSegment("ajout").build();
        this.modifierUrl = this.url.newBuilder().addPathSegment("modifier").build();
        this.supprimerUrl = this.url.newBuilder().addPathSegment("canceller").build();

    }

    public synchronized ArrayList<DescriptionModel> getListeUnitee() {
        return this.listeUnitee;
    }

    public synchronized ArrayList<TypeAlimentaireModele> getListeTypeAlimentaire() {
        return this.listeTypeAlimentaire;
    }

    /**
     * Accesseur pour la liste des dons reçu lors de l'appel de
     * {@link AlimentaireModeleDepot#peuplerListeDon(Integer)}
     *
     * @return La liste des dons
     *
     * @deprecated Veuillez utiliser un {@link ObservateurDeDepot} et
     * {@link AlimentaireModeleDepot#peuplerListeDon(Integer)} pour obtenir cette liste. Pour
     * plus de détail voir {@link com.pam.codenamehippie.ui.ListeMesDonsActivity}
     */
    @Deprecated
    public synchronized ArrayList<AlimentaireModele> getListeDon() {
        return this.listeDon;
    }

    /**
     * Permet de peupler les items pour les spinner.
     * <p>
     * Cette methode est asynchrone et retourne immédiatement
     */
    public void peuplerLesListes() {
        //TODO: Refactoriser la méthode pour passer en paramètre des callbacks
        Request listeUniteRequete = new Request.Builder().url(this.listeUniteUrl).get().build();
        Request listeTypeAlimentaireRequete =
                new Request.Builder().url(this.listeTypeAlimentaireUrl).get().build();
        this.httpClient.newCall(listeUniteRequete).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // TODO: Mettre un toast ou whatever
                Log.e(TAG, "Request failed: " + call.request().toString(), e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
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
            public void onFailure(Call call, IOException e) {
                // TODO: Mettre un toast ou whatever
                Log.e(TAG, "Request failed: " + call.request().toString(), e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "Request failed: " + response.toString());
                } else {
                    Type type = new TypeToken<ArrayList<TypeAlimentaireModele>>() { }.getType();
                    // Ajouter un String "Faites votre choix..." à l'indice 0
                    ArrayList<TypeAlimentaireModele> temp = new ArrayList<>();
                    temp.add(new TypeAlimentaireModele());
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

    /**
     * Peuple le dépot avec la liste de tous les dons de l'entreprise qui sont disponibles ou
     * reservé
     *
     * @param id
     *         id de l'organisme dont on veut obtenir la liste des dons.
     **/
    public void peuplerListeDon(Integer id) {
        HttpUrl url = this.listeDonUrl.newBuilder().addPathSegment(id.toString()).build();
        this.peuplerLeDepot(url);
        //TODO: Enlever le code en dessous et listeDon quand le nouvel api sera en place
        Request listeDonRequete = new Request.Builder().url(url).get().build();
        this.httpClient.newCall(listeDonRequete).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                //TODO: Toast ou whatever
                Log.e(TAG, "Request failed: " + call.request().toString(), e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (!response.isSuccessful()) {
                    Log.e(TAG, "Request failed: " + response.toString());
                } else {
                    Type type = new TypeToken<ArrayList<AlimentaireModele>>() {
                    }.getType();

                    AlimentaireModeleDepot.this.listeDon =
                            gson.fromJson(response.body().charStream(), type);

                    Log.d(TAG,
                          "Liste don: " +
                          AlimentaireModeleDepot.this.listeDon.toString()
                         );
                }

            }
        });

    }

    public void peuplerListeReserve() {

    }

    public void peuplerListeDonDispo() {
        this.peuplerLeDepot(this.listeDonDispoUrl);
        //TODO: Enlever le code en dessous et listeDonDispo quand le nouvel api sera en place
        Request listeDonDispoRequete =
                new Request.Builder().url(this.listeDonDispoUrl).get().build();

        this.httpClient.newCall(listeDonDispoRequete).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //TODO toast

                Log.e(TAG, "Request failed: " + call.request().toString(), e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "Request failed: " + response.toString());
                } else {
                    Type type = new TypeToken<ArrayList<AlimentaireModele>>() { }.getType();

                    AlimentaireModeleDepot.this.listeDonDispo =
                            gson.fromJson(response.body().charStream(), type);

                    Log.d(TAG,
                          "Liste don dispo: " +
                          AlimentaireModeleDepot.this.listeDonDispo.toString()
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
//     *         de l'objet MarchandiseModele
//     *
//     * @return un MarchandiseModele ou null si inexistant dans le dépôt
//     */
//    @Override
//    public void supprimerModele(@NonNull AlimentaireModele modele) {
//        // TODO: requête au serveur pour suppression de la marchandise
//
//    }
}
