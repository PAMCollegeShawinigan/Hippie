package com.pam.codenamehippie.modele;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.http.exception.HttpReponseException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AlimentaireModeleDepot extends BaseModeleDepot<AlimentaireModele> {

    public interface PeuplerListesDeSpinnerListener {

        void surDebut();

        void surErreur(IOException e);

        void surListeUnite(ArrayList<DescriptionModel> items);

        void surListeType(ArrayList<TypeAlimentaireModele> items);

        void surFin();

    }

    private static final String TAG = AlimentaireModeleDepot.class.getSimpleName();

    private final HttpUrl listeUniteUrl;
    private final HttpUrl listeTypeAlimentaireUrl;
    private final HttpUrl listeDonUrl;
    private final HttpUrl listeDonDispoUrl;
    private final HttpUrl reservationUrl;
    private final HttpUrl listeReservationUrl;
    private final HttpUrl collecterUrl;

    private volatile ArrayList<DescriptionModel> listeUnitee;
    private volatile ArrayList<TypeAlimentaireModele> listeTypeAlimentaire;

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
        this.reservationUrl = this.url.newBuilder().addPathSegment("reservation").build();
        this.listeReservationUrl =
                this.reservationUrl.newBuilder().addPathSegment("liste").build();
        this.url = this.url.newBuilder().addPathSegment("alimentaire").build();
        this.ajoutUrl = this.url.newBuilder().addPathSegment("ajout").build();
        this.modifierUrl = this.url.newBuilder().addPathSegment("modifier").build();
        this.supprimerUrl = this.url.newBuilder().addPathSegment("canceller").build();
        this.collecterUrl = this.url.newBuilder().addPathSegment("collecter").build();

    }

    public synchronized ArrayList<DescriptionModel> getListeUnitee() {
        return this.listeUnitee;
    }

    public synchronized ArrayList<TypeAlimentaireModele> getListeTypeAlimentaire() {
        return this.listeTypeAlimentaire;
    }

    /**
     * Permet de peupler les items pour les spinner.
     * <p/>
     * Cette methode est asynchrone et retourne immédiatement
     */
    public void peuplerLesListesDeSpinners(final PeuplerListesDeSpinnerListener listener) {
        Request listeUniteRequete = new Request.Builder().url(this.listeUniteUrl).get().build();
        Request listeTypeAlimentaireRequete =
                new Request.Builder().url(this.listeTypeAlimentaireUrl).get().build();
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listener.surDebut();
            }
        });
        this.httpClient.newCall(listeUniteRequete).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                // TODO: Mettre un toast ou whatever
                Log.e(TAG, "Request failed: " + call.request().toString(), e);
                AlimentaireModeleDepot.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.surErreur(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "Request failed: " + response.toString());
                    AlimentaireModeleDepot.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listener.surErreur(new HttpReponseException(response));
                            listener.surFin();
                        }
                    });
                } else {
                    synchronized (AlimentaireModeleDepot.this.lock) {
                        Type type = new TypeToken<ArrayList<DescriptionModel>>() { }.getType();
                        // Ajouter un String "Faites votre choix..." à l'indice 0
                        ArrayList<DescriptionModel> temp = new ArrayList<>();
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
                    AlimentaireModeleDepot.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            synchronized (AlimentaireModeleDepot.this.lock) {
                                listener.surListeUnite(AlimentaireModeleDepot.this.listeUnitee);
                            }
                        }
                    });
                }
            }
        });
        this.httpClient.newCall(listeTypeAlimentaireRequete).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                // TODO: Mettre un toast ou whatever
                Log.e(TAG, "Request failed: " + call.request().toString(), e);
                AlimentaireModeleDepot.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.surErreur(e);
                        listener.surFin();
                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "Request failed: " + response.toString());
                    AlimentaireModeleDepot.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listener.surErreur(new HttpReponseException(response));
                            listener.surFin();
                        }
                    });
                } else {
                    synchronized (AlimentaireModeleDepot.this.lock) {
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
                    AlimentaireModeleDepot.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            synchronized (AlimentaireModeleDepot.this.lock) {
                                listener.surListeType(AlimentaireModeleDepot.this
                                                              .listeTypeAlimentaire);
                                listener.surFin();
                            }
                        }
                    });
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
    }

    public void peuplerListeDonDispo() {
        this.peuplerLeDepot(this.listeDonDispoUrl);
    }

    public void peuplerListeReservation(Integer idOrganisme) {
        HttpUrl url = this.listeReservationUrl.newBuilder()
                                              .addPathSegment(idOrganisme.toString())
                                              .build();
        this.peuplerLeDepot(url);
    }

    public void collecter(AlimentaireModele modele, final Runnable action) {
        HttpUrl url = this.collecterUrl.newBuilder().addPathSegment(modele.getId().toString())
                                       .build();
        Request request = new Request.Builder().url(url).get().build();
        this.httpClient.newCall(request)
                       .enqueue(new Callback() {
                           @Override
                           public void onFailure(Call call, IOException e) {
                               AlimentaireModeleDepot.this.surErreur(e);
                           }

                           @Override
                           public void onResponse(Call call, Response response) {
                               if (!response.isSuccessful()) {
                                   HttpReponseException e = new HttpReponseException(response);
                                   AlimentaireModeleDepot.this.surErreur(e);
                               } else {
                                   AlimentaireModeleDepot.this.repeuplerLedepot();
                                   if (action != null) {
                                       AlimentaireModeleDepot.this.runOnUiThread(action);
                                   }
                               }

                           }
                       });
    }

    public void ajouterReservation(AlimentaireModele modele, final Runnable action) {
        HttpUrl url = this.reservationUrl.newBuilder().addPathSegment("ajouter").build();
        Integer receveurId =
                PreferenceManager.getDefaultSharedPreferences(this.context)
                                 .getInt(this.context.getString(R.string.pref_org_id_key), -1);

        FormBody body = new FormBody.Builder().add("marchandise_id", modele.getId().toString())
                                              .add("receveur_id", receveurId.toString())
                                              .build();
        Request request = new Request.Builder().url(url).post(body).build();
        this.httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                AlimentaireModeleDepot.this.surErreur(e);
                AlimentaireModeleDepot.this.surFinDeRequete();
            }

            @Override
            public void onResponse(Call call, Response response) {
                if (!response.isSuccessful()) {
                    HttpReponseException e = new HttpReponseException(response);
                    AlimentaireModeleDepot.this.surErreur(e);
                    AlimentaireModeleDepot.this.surFinDeRequete();
                } else {
                    AlimentaireModeleDepot.this.repeuplerLedepot();
                    if (action != null) {
                        AlimentaireModeleDepot.this.runOnUiThread(action);
                    }
                }

            }
        });
    }

    public void annulerReservation(AlimentaireModele modele, final Runnable action) {
        HttpUrl url = this.reservationUrl.newBuilder()
                                         .addPathSegment("annuler")
                                         .addPathSegment(modele.getId().toString())
                                         .build();
        Request request = new Request.Builder().url(url).get().build();
        this.httpClient.newCall(request)
                       .enqueue(new Callback() {
                           @Override
                           public void onFailure(Call call, IOException e) {
                               AlimentaireModeleDepot.this.surErreur(e);
                               AlimentaireModeleDepot.this.surFinDeRequete();
                           }

                           @Override
                           public void onResponse(Call call, Response response) {
                               if (!response.isSuccessful()) {
                                   HttpReponseException e = new HttpReponseException(response);
                                   AlimentaireModeleDepot.this.surErreur(e);
                               } else {
                                   AlimentaireModeleDepot.this.repeuplerLedepot();
                                   if (action != null) {
                                       AlimentaireModeleDepot.this.runOnUiThread(action);
                                   }
                               }

                           }
                       });
    }
}
