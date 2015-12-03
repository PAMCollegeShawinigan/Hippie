package com.pam.codenamehippie.modele;

import android.support.annotation.Nullable;
import android.support.v4.util.SimpleArrayMap;

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
     * @param id
     *   de l'objet
     *
     * @return une instance du modèle correspondant au id reçu en paramètre.
     */
    public abstract T modifierModele(int id);

    public abstract T ajouterModele(int id);

    public abstract T supprimerModele(int id);

}
