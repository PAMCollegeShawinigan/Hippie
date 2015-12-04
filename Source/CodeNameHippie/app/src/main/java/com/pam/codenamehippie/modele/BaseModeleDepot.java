package com.pam.codenamehippie.modele;

import android.support.annotation.Nullable;
import android.support.v4.util.SimpleArrayMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pam.codenamehippie.HippieApplication;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;

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
 *   Type de modèle que le dépot contient.
 */
public abstract class BaseModeleDepot<T extends BaseModele> {

    /**
     * Instance globale de la classe servant à la conversion des objets du dépôt en format JSON.
     * Ce membre est publique à fin de réduire du nombre d'allocation.
     */
    protected final static Gson gson = new GsonBuilder().serializeNulls().create();

    /**
     * La valeur du paramètre de type T.
     */
    protected Class classDeT;

    /**
     * Contenant qui renferme les objets entretenus par le dépôt.
     */
    protected SimpleArrayMap<Integer, T> modeles = new SimpleArrayMap<>();

    /**
     * Url du des objets du dépôt.
     */
    protected HttpUrl url = HippieApplication.baseUrl;

    /**
     * Client http.
     */
    protected OkHttpClient httpClient;

    /**
     * Initialise les variables variables commune à tous les dépôts.
     *
     * @param httpClient
     *   client http servant à faire des requêtes au serveur
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
     *   une string formatté en JSON. représentant le modèle
     *
     * @return une instance du modèle.
     */
    @SuppressWarnings("unchecked")
    public T fromJson(String json) {
        return (T) gson.fromJson(json, this.classDeT);
    }

    /**
     * Méthode qui recherche selon l'id de l'objet reçu en paramètre.
     *
     * @param id
     *   de l'objet
     *
     * @return une instance du modèle correspondant au id reçu en paramètre ou null si il
     * n'existe pas.
     */

    @Nullable
    public T rechercherParId(Integer id) {
        return this.modeles.get(id);
    }

    /**
     * Méthode qui modifie selon l'id de l'objet reçu en paramètre.
     *
     * @param modele
     *   de l'objet
     *
     * @return une instance du modèle correspondant au id reçu en paramètre.
     */
    public abstract T modifierModele(T modele);

    public abstract T ajouterModele(String json);

    public abstract T supprimerModele(T modele);

}
