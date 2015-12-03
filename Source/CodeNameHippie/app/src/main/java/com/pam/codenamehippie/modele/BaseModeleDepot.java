package com.pam.codenamehippie.modele;

import android.support.annotation.Nullable;
import android.support.v4.util.SimpleArrayMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pam.codenamehippie.HippieApplication;
import com.squareup.okhttp.HttpUrl;

/**
 * Classe patron représentant un dépôt d'objet de type {@link BaseModele}.
 * <p/>
 * Cette classe est définie comme abstraite pour 2 raisons:
 * <ol>
 * <li>Elle à été conçue avec l'intention d'être la classe mère de tous les autres dépôt.</li>
 * <li>En Java, les méthodes dans les interfaces ne peuvent contenir un corps. Autrement dit,
 * cette classe fourni une implémentation des fonctionnalité communes de ses sous-classes</li>
 * </ol>
 *
 * @param <T>
 *   Classe que le dépot contient.
 */
public abstract class BaseModeleDepot<T extends BaseModele> {

    protected Gson gson = new GsonBuilder().serializeNulls().create();

    /**
     * Méthode de sérialisation du modèle en JSON.
     *
     * @return le modèle en format JSON.
     */
    public String toJson() {
       // return this.gson.toJson(); FIXME: passer le type comme du monde
        return  null;
    }

    /**
     * Méthode de désérialisation du modèle en JSON
     *
     * @param json
     *   une string formatté en JSON. représentant le modèle
     *
     * @return une instance du modèle.
     */
    public T fromJson(String json) {
        //return gson.fromJson(json,new TClass<T>()); // FIXME: passer le type comme du monde
        return null;
    }

    /**
     * Contenant qui renferme les objets entretenus par le dépôt.
     */

    protected SimpleArrayMap<Integer, T> modeles = new SimpleArrayMap<>();

    /**
     * Addresse url qui représente
     */
    protected HttpUrl url = HippieApplication.baseUrl;

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
    public T rechercherParId(int id) {
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
