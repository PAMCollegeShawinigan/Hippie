package com.pam.codenamehippie.modele;

import android.content.Context;
import android.database.DataSetObservable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.http.exception.HttpReponseException;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Classe patron représentant un dépôt d'objet de type {@link BaseModele}.
 * <p>
 * Cette classe est définie comme abstraite pour 2 raisons:
 * <ol>
 * <li>
 * Elle à été conçue avec l'Intention d'être la classe mère de tous les autres dépôt.
 * </li>
 * <li>
 * En Java, les méthodes dans les interfaces ne peuvent contenir un corps et nous désirons
 * avoir éviter de la duplication de code inutile. Autrement dit, cette classe tente de
 * fournir des une implémentation par défaut quand c'est possible.
 * </li>
 * </ol>
 * <p>
 * L'initialisation d'un dépôt requiert une inspection de sa hiearchie de classe en utilisant
 * le mécanisme de réflection de Java. Ceci est une opération relativement dispendieuse, par
 * conséquent nous recommandons de limiter le nombre d'allocation d'instances d'objet de type
 * dépôt.
 *
 * @param <T>
 *         Type de modèle que le dépot contient.
 */
public abstract class BaseModeleDepot<T extends BaseModele<T>> {

    /**
     * Instance globale de la classe servant à la conversion des objets du dépôt en format JSON.
     * Ce membre est publique afin de réduire le nombre d'allocation.
     */
    protected final static Gson gson =
            new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create();
    private static final String TAG = BaseModeleDepot.class.getSimpleName();
    /**
     * Contenant qui renferme les objets entretenus par le dépôt.
     */
    protected final ArrayList<T> modeles = new ArrayList<>();
    /**
     * Client http.
     */
    protected final OkHttpClient httpClient;
    /**
     * Context pour accèder au ressources string.
     */
    protected final Context context;
    /**
     * Verrou de synchronisation.
     */
    protected final Object lock = new Object();
    /**
     * La valeur du paramètre de type T.
     */
    protected Class classeDeT;

    /**
     * Url du des objets du dépôt.
     */
    protected HttpUrl url = HippieApplication.baseUrl;

    /**
     * Url de la dernière requête de peuplement effectuée
     */
    protected HttpUrl urlDeRepeuplement = null;

    /**
     * Url pour modifications des objets du dépot.
     */
    protected HttpUrl modifierUrl = null;

    /**
     * Url pour les ajouts des objets du dépot
     */
    protected HttpUrl ajoutUrl = null;

    /**
     * Url pour les suppressions des objets du dépot
     */
    protected HttpUrl supprimerUrl = null;

    /**
     * Liste contenant les objets qui observe le dépôt.
     */
    protected volatile ArrayList<ObservateurDeDepot<T>> observateurs = new ArrayList<>();

    /**
     * Foncteur pour les listes résultantes des requêtes
     */
    protected FiltreDeListe<T> filtreDeListe = null;

    /**
     * Main thread handler
     */
    protected Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    /**
     * Initialise les variables commune à tous les dépôts.
     *
     * @param context
     *         le context pour aller chercher des ressources string.
     * @param httpClient
     *         le client http pour utiliser par les dépots pour faire des requêtes au
     *         serveur
     */

    public BaseModeleDepot(Context context, OkHttpClient httpClient) {
        Class clazz = this.getClass();
        ParameterizedType genericType;
        // Recherche la première classe générique dans l'heritage.
        do {
            genericType = ((ParameterizedType) clazz.getGenericSuperclass());
            clazz = super.getClass();
        } while (genericType == null);
        // Recherche le premier paramètre de type qui hérite de BaseModele.
        for (Type type : genericType.getActualTypeArguments()) {
            if (BaseModele.class.isAssignableFrom((Class) type)) {
                synchronized (this.lock) {
                    this.classeDeT = (Class) type;
                    break;
                }
            }
        }
        this.context = context;
        this.httpClient = httpClient;
    }

    /**
     * Accesseur de l'url des objet du dépôt
     *
     * @return Url du des objets du dépôt.
     */
    public HttpUrl getUrl() {
        return this.url;
    }

    /**
     * Accesseur du contenu du dépôt. Le contenu du dépôt est toujours le résultat de de la
     * dernière requête de peuplement.
     *
     * @return Le contenu du dépôt
     */
    public ArrayList<T> getModeles() {
        synchronized (this.lock) {
            return this.modeles;
        }
    }

    

    public FiltreDeListe<T> getFiltreDeListe() {
        synchronized (this.lock) {
            return this.filtreDeListe;
        }
    }

    /**
     * Assigne un filtre pour toutes les nouvelles requêtes de peuplement du dépôt. Mettre à null
     * pour supprimer le filtre
     *
     * @param filtreDeListe
     *         Le filtre à mettre pour la requête.
     */
    public void setFiltreDeListe(@Nullable FiltreDeListe<T> filtreDeListe) {
        synchronized (this.lock) {
            this.filtreDeListe = filtreDeListe;
        }
    }

    /**
     * Méthode de sérialisation du modèle en JSON.
     *
     * @return le modèle en format JSON.
     */
    public String toJson(T modele) {
        synchronized (this.lock) {
            return gson.toJson(modele);
        }
    }

    /**
     * Méthode de désérialisation du modèle en JSON
     *
     * @param json
     *         un string formatté en JSON. représentant le modèle
     *
     * @return une instance du modèle.
     */
    @SuppressWarnings("unchecked")
    public T fromJson(String json) {
        synchronized (this.lock) {
            return (T) gson.fromJson(json, this.classeDeT);
        }
    }

    /**
     * Méthode de désérialisation du modèle en JSON
     *
     * @param reader
     *         un reader de string formatté en JSON. représentant le modèle
     *
     * @return une instance du modèle.
     */
    @SuppressWarnings("unchecked")
    public T fromJson(JsonReader reader) {
        synchronized (this.lock) {
            return (T) gson.fromJson(reader, this.classeDeT);
        }
    }

    /**
     * Méthode de désérialisation du modèle en JSON
     *
     * @param reader
     *         un reader de string formatté en JSON. représentant le modèle
     *
     * @return une instance du modèle.
     */
    @SuppressWarnings("unchecked")
    public T fromJson(Reader reader) {
        synchronized (this.lock) {
            return (T) gson.fromJson(reader, this.classeDeT);
        }
    }

    /**
     * Permet de peupler le dépot.
     * <p>
     * Cette methode est asynchrone et retourne immédiatement.
     *
     * @param url
     *         url de la requête.
     */
    protected void peuplerLeDepot(@NonNull HttpUrl url) {
        synchronized (this.lock) {
            if ((this.urlDeRepeuplement == null) || (!this.urlDeRepeuplement.equals(url))) {
                this.urlDeRepeuplement = url;
            }
        }
        Request request = new Request.Builder().url(url).get().build();
        // FIXME: surDebutDeRequête devrait être caller quand le dispatcher traite la requête.
        // Il faudrait soummettre manuellement les calls aux dispatcher… Ça demanderait quand
        // même assez de travail… Pour les besoins de la cause on va tenter de pas soumettre
        // plusieurs requêtes en même temps au même dépot.
        this.surDebutDeRequete();
        this.httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Request failed: " + call.request().toString(), e);
                BaseModeleDepot.this.surErreur(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "Request failed: " + response.toString());
                    BaseModeleDepot.this.surErreur(new HttpReponseException(response));
                } else {
                    synchronized (BaseModeleDepot.this.lock) {
                        // On vide le dépôt pour faire place au nouveau stock.
                        BaseModeleDepot.this.modeles.clear();
                        // Le serveur retourne un array. Donc pour supporter un énorme array on
                        // utilise des streams.
                        JsonReader reader = new JsonReader(response.body().charStream());
                        reader.beginArray();
                        while (reader.hasNext()) {
                            T modele = BaseModeleDepot.this.fromJson(reader);
                            if (BaseModeleDepot.this.filtreDeListe != null) {
                                if (BaseModeleDepot.this.filtreDeListe.appliquer(modele)) {
                                    BaseModeleDepot.this.modeles.add(modele);
                                }
                            } else {
                                BaseModeleDepot.this.modeles.add(modele);
                            }
                        }
                        reader.endArray();
                        reader.close();
                    }
                    BaseModeleDepot.this.surChangementDeDonnees();
                }
                BaseModeleDepot.this.surFinDeRequete();
            }
        });
    }

    /**
     * Repeuple le dépôt avec la dernière url utilisée par le dépôt.
     */
    public void repeuplerLedepot() {
        synchronized (this.lock) {
            if (this.urlDeRepeuplement != null) {
                this.peuplerLeDepot(this.urlDeRepeuplement);
            }
        }
    }

    /**
     * Méthode qui recherche un modèle selon l'id de l'objet reçu en paramètre.
     * <p>
     * Cette methode est asynchrone et retourne immédiatement.
     *
     * @param id
     *         de l'objet
     */
    public void rechercherParId(@NonNull Integer id) {
        HttpUrl url = this.url.newBuilder().addPathSegment(id.toString()).build();
        Request request = new Request.Builder().url(url).build();
        //TODO: Implémenter la requête + callback?
    }

    /**
     * Ajouter un nouveau modèle dans le dépôt.
     *
     * @param modele
     *         le modele à ajouter
     * @param devraitPoster
     *         determine si le dépôt doit envoyer le paramètre modèle au serveur.
     */
    public void ajouterModele(T modele, boolean devraitPoster) {
        if (this.ajoutUrl == null) {
            if (this.modifierUrl == null) {
                throw new UnsupportedOperationException("Ce dépôt ne supporte pas l'ajout");
            }
        }
        synchronized (this.lock) {
            int index = this.modeles.indexOf(modele);
            if (index != -1) {
                this.modeles.add(index, modele);
            } else {
                this.modeles.add(modele);
            }
        }
        if (devraitPoster) {
            // Ceci est du code expérimental/prototype.
            // L'idee ici c'est d'utiliser la réflection java pour créer une form http.
            // Il serait plus facile de soumettre du json, mais en ce moment, le serveur ne le
            // prend pas en ce moment
            Class clazz = modele.getClass();
            do {
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    SerializedName serializedName = field.getAnnotation(SerializedName.class);
                    boolean old = field.isAccessible();
                    field.setAccessible(true);
                    try {
                        Log.d(TAG,
                              field.getName() +
                              ": " +
                              field.get(modele) +
                              " serializedName: " +
                              serializedName
                                      .value()
                             );
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    field.setAccessible(old);

                }
                clazz = clazz.getSuperclass();
            } while (clazz != null);

        }
    }

    /**
     * Modifie un é présent dans le dépôt correspondant selon l'id de l'objet reçu en
     * paramètre.
     *
     * @param modele
     *         Le modèle à modifier.
     */
    public void modifierModele(T modele) {
        if (this.modifierUrl == null) {
            throw new UnsupportedOperationException("Ce dépôt ne supporte pas la modification");
        }
        //TODO: Code de modification de modèle générique.
    }

    /**
     * Envoi une commande de suppression de données au serveur.
     * <p>
     * Cette méthode est asynchrone et retourne immédiatement.<br/>
     * Cette méthode est équivalente à {@code supprimerModele(modele, null)}.
     *
     * @param modele
     *         l'objet à supprimer.
     *
     * @see BaseModeleDepot#supprimerModele(BaseModele, Runnable)
     */
    public void supprimerModele(T modele) {
        this.supprimerModele(modele, null);
    }

    /**
     * Envoi une commande de suppression de données au serveur.
     * <p>
     * Cette méthode est asynchrone et retourne immédiatement.
     *
     * @param modele
     *         l'objet à supprimer
     * @param action
     *         une action à executer en cas de succès. Cette action est exécutée sur le main
     *         thread.
     */
    public void supprimerModele(T modele, @Nullable final Runnable action) {
        if (this.supprimerUrl == null) {
            throw new UnsupportedOperationException("Ce dépot ne supporte pas la suppression");
        }
        HttpUrl url = this.supprimerUrl.newBuilder()
                                       .addPathSegment(modele.getId().toString())
                                       .build();
        Request request = new Request.Builder().url(url).get().build();
        this.httpClient.newCall(request)
                       .enqueue(new Callback() {
                           @Override
                           public void onFailure(Call call, IOException e) {
                               BaseModeleDepot.this.surErreur(e);
                           }

                           @Override
                           public void onResponse(Call call, Response response) {
                               if (!response.isSuccessful()) {
                                   HttpReponseException e = new HttpReponseException(response);
                                   BaseModeleDepot.this.surErreur(e);
                               } else {
                                   BaseModeleDepot.this.repeuplerLedepot();
                                   if (action != null) {
                                       BaseModeleDepot.this.runOnUiThread(action);
                                   }
                               }

                           }
                       });
    }

    /**
     * Ajoute un objet implémentant l'interface {@link ObservateurDeDepot} dans la listes des
     * observateurs du dépôt.  L'objet ajouté reçoit des notifications du dépôt à l'aide des
     * méthodes de l'interface {@link ObservateurDeDepot}.
     *
     * @param observateur
     *         L'objet qui va recevoir les callbacks.
     *
     * @see {@link android.database.DataSetObservable#registerObserver(Object)}
     */
    public void ajouterUnObservateur(@NonNull ObservateurDeDepot<T> observateur) {
        synchronized (this.lock) {
            if (this.observateurs.contains(observateur)) {
                throw new IllegalStateException("L'observateur " + observateur + "est déjà ajouté");
            }
            this.observateurs.add(observateur);
        }

    }

    /**
     * Supprime un objet implémentant l'interface {@link ObservateurDeDepot} de la liste des
     * observateurs du dépôt. L'objet supprimé cesse de recevoir des notifications du dépôt.
     *
     * @param observateur
     *         L'objet à enlever de la liste des observateur.
     *
     * @see {@link android.database.DataSetObservable#unregisterObserver(Object)}
     */
    public void supprimerUnObservateur(@NonNull ObservateurDeDepot<T> observateur) {
        synchronized (this.lock) {
            int index = this.observateurs.indexOf(observateur);
            if (index == -1) {
                throw new IllegalStateException("L'observateur " + observateur + "n'est pas déjà " +
                                                "ajouté");
            }
            this.observateurs.remove(index);
        }
    }

    /**
     * Vide la liste des observateurs.
     *
     * @see {@link DataSetObservable#unregisterAll()}
     */
    public void supprimerTousLesObservateurs() {
        synchronized (this.lock) {
            this.observateurs.clear();
        }
    }

    /**
     * Notifie tous les observateurs du dépôt qu'une requête a démarré.
     *
     * @see {@link ObservateurDeDepot#surDebutDeRequete()}
     */
    public void surDebutDeRequete() {
        Runnable action = new Runnable() {
            public void run() {
                synchronized (BaseModeleDepot.this.lock) {
                    if (!BaseModeleDepot.this.observateurs.isEmpty()) {
                        for (ObservateurDeDepot<T> obs : BaseModeleDepot.this.observateurs) {
                            obs.surDebutDeRequete();
                        }
                    }
                }
            }
        };
        this.runOnUiThread(action);
    }

    /**
     * Notifie tous les observateurs du dépôt qu'il y a eu un changement dans les données du dépôt.
     */
    public void surChangementDeDonnees() {
        Runnable action = new Runnable() {
            public void run() {
                synchronized (BaseModeleDepot.this.lock) {
                    if (!BaseModeleDepot.this.observateurs.isEmpty()) {
                        for (ObservateurDeDepot<T> obs : BaseModeleDepot.this.observateurs) {
                            obs.surChangementDeDonnees(BaseModeleDepot.this.modeles);
                        }
                    }
                }
            }
        };
        this.runOnUiThread(action);
    }

    /**
     * Notifie tous les observateurs du dépôt qu'il y a eu une erreur lors d'une requête.
     */
    public void surErreur(final IOException e) {
        Runnable action = new Runnable() {
            public void run() {
                synchronized (BaseModeleDepot.this.lock) {
                    if (!BaseModeleDepot.this.observateurs.isEmpty()) {
                        for (ObservateurDeDepot<T> obs : BaseModeleDepot.this.observateurs) {
                            obs.surErreur(e);
                        }
                    }
                }
            }
        };
        this.runOnUiThread(action);
    }

    /**
     * Notifie tous les observateurs du dépôt qu'une requête a terminée.
     *
     * @see {@link ObservateurDeDepot#surDebutDeRequete()}
     */
    public void surFinDeRequete() {
        Runnable action = new Runnable() {
            public void run() {
                synchronized (BaseModeleDepot.this.lock) {
                    if (!BaseModeleDepot.this.observateurs.isEmpty()) {
                        for (ObservateurDeDepot<T> obs : BaseModeleDepot.this.observateurs) {
                            obs.surFinDeRequete();
                        }
                    }
                }
            }
        };
        this.runOnUiThread(action);
    }

    /**
     * Réimplémentation de {@link android.app.Activity#runOnUiThread(Runnable)}
     *
     * @param action
     *         truc à rouler sur le main thread
     *
     * @see android.app.Activity#runOnUiThread(Runnable)
     */
    protected void runOnUiThread(Runnable action) {
        if (Thread.currentThread() != this.mainThreadHandler.getLooper().getThread()) {
            this.mainThreadHandler.post(action);
        } else {
            action.run();
        }
    }
}
