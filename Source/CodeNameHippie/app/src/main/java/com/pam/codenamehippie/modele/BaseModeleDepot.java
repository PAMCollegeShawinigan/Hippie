package com.pam.codenamehippie.modele;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pam.codenamehippie.HippieApplication;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Classe patron représentant un dépôt d'objet de type {@link BaseModele}.
 * <p/>
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
 * <p/>
 * L'initialisation d'un dépôt requiert une inspection de sa hiearchie de classe en utilisant
 * le mécanisme de réflection de Java. Ceci est une opération dispendieuse, par conséquent nous
 * recommandons de limiter le nombre d'allocation d'instances d'objet de type dépôt.
 *
 * @param <T>
 *         Type de modèle que le dépot contient.
 */
public abstract class BaseModeleDepot<T extends BaseModele> {

    /**
     * Instance globale de la classe servant à la conversion des objets du dépôt en format JSON.
     * Ce membre est publique afin de réduire le nombre d'allocation.
     */
    protected final static Gson gson =
            new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create();
    private static final String TAG = BaseModele.class.getSimpleName();
    /**
     * Contenant qui renferme les objets entretenus par le dépôt.
     */
    protected final SparseArray<T> modeles = new SparseArray<>();
    /**
     * La valeur du paramètre de type T.
     */
    protected Class classDeT;
    /**
     * Url du des objets du dépôt.
     */
    protected HttpUrl url = HippieApplication.baseUrl;

    /**
     * Client http.
     */
    protected OkHttpClient httpClient;

    /**
     * Initialise les variables commune à tous les dépôts.
     *
     * @param httpClient
     *         client http servant à faire des requêtes au serveur
     */
    public BaseModeleDepot(OkHttpClient httpClient) {
        Class clazz = this.getClass();
        ParameterizedType genericType;
        // Recherche la première classe générique dans l'heritage.
        do {
            genericType = ((ParameterizedType) clazz.getGenericSuperclass());
            clazz = super.getClass();
        } while (genericType == null);
        // Recherche le premier paramètre de type qui hérite de BaseModèle.
        for (Type type : genericType.getActualTypeArguments()) {
            if (BaseModele.class.isAssignableFrom((Class) type)) {
                this.classDeT = (Class) type;
                break;
            }
        }
        this.httpClient = httpClient;
    }

    /**
     * Méthode de sérialisation du modèle en JSON.
     *
     * @return le modèle en format JSON.
     */
    public String toJson(T modele) {
        return gson.toJson(modele);
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
        return (T) gson.fromJson(json, this.classDeT);
    }

    /**
     * Méthode qui recherche un Modele selon l'id de l'objet reçu en paramètre.
     *
     * @param id
     *         de l'objet
     *
     * @return une instance du modèle correspondant au id reçu en paramètre ou null si il
     * n'existe pas.
     */

    @Nullable
    public synchronized T rechercherParId(@NonNull Integer id) {
        T modele = this.modeles.get(id);
        if (modele != null) {
            return this.modeles.get(id);
        } else {
            HttpUrl url = this.url.newBuilder().addPathSegment(id.toString()).build();
            Request request = new Request.Builder().url(url).build();
            Response response = null;
            try {
                // FIXME: Android aime pas les opérations réseaux sur le main thread... Callback?
                response = this.httpClient.newCall(request).execute();
                String body = response.body().string();
                if (response.isSuccessful()) {
                    return this.ajouterModele(body, false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if ((response != null) && (response.body() != null)) {
                    try {
                        response.body().close();
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage(), e);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Ajouter un nouveau modèle dans le dépôt correspondant
     *
     * @param json
     *         de l'objet Modele
     * @param devraitPoster
     *         determine si le dépôt doit envoyer le parmètre json au serveur.
     *
     * @return une nouvelle instance de Modele vide ou null s'il existe déjà
     */
    public synchronized T ajouterModele(String json, Boolean devraitPoster) {
        T modele = this.fromJson(json);
        if (this.modeles.get(modele.getId()) == null) {
            this.modeles.put(modele.getId(), modele);
            if (devraitPoster) {
                // todo: requête au serveur pour ajouter du stock
            }
            return modele;
        } else {
            return null;
        }
    }

    /**
     * Modifier un Modele présent dans le dépôt correspondant selon l'id de
     * l'objet reçu en paramètre.
     *
     * @param modele
     *         de l'objet
     *
     * @return Modele existant dans la dépôt ou null s'il n'existe pas dans le dépôt
     */
    public synchronized T modifierModele(T modele) {
        T oldModele = this.modeles.get(modele.getId());

        if (oldModele != null) {
            return oldModele;
        } else {
            return null;
        }
    }

    /**
     * Supprimer un Modele présent dans le dépôt
     *
     * @param modele
     *         de l'objet
     *
     * @return l'ancien Modele
     */
    public synchronized T supprimerModele(T modele) {
        T oldModele = this.modeles.get(modele.getId());

        if (oldModele != null) {
            this.modeles.remove(modele.getId());
            // todo: requête au serveur pour suppression de l'objet
        }
        return oldModele;
    }
}
