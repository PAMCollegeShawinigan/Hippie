package com.pam.codenamehippie.modele;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

/**
 * Classe de base des modèles. Cette classe est générique afin de pouvoir utiliser un API fluent
 * et éviter de dupliquer du code inutilement, car autrement les classes qui hérite devrait
 * faire des override de mutateurs en changeant le type de retour.
 * <p/>
 * Par API fluent nous entendons quelque chose comme ceci:
 * <p/>
 * <code>
 * UtilisateurModele modele = new UtilisateurModele();<br/>
 * modele.setNom("Lafontaine").setPrenom("Philippe").setTelephone("819 555 8963");
 * </code>
 * <p/>
 * Par opposition à:
 * <p/>
 * <code>
 * UtilisateurModele modele = new UtilisateurModele();<br/>
 * modele.setNom("Lafontaine");<br/>
 * modele.setPrenom("Philippe");<br/>
 * modele.setTelephone("819 555 8963");
 * </code>
 *
 * @param <T>
 *         La classe qui hérite de BaseModele
 *
 * @see <a href="http://goo.gl/67YLAR">Using Generics To Build Fluent API's In Java</a>
 */
public abstract class BaseModele<T extends BaseModele<T>> {

    // TODO : Vérifier la convention de nom avec la BD
    @SerializedName(value = "id")
    private Integer id = 0;

    /**
     * Accesseur de l'id du modèle
     *
     * @return l'id de l'objet
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Mutateur pour l'id
     *
     * @param id
     *         le nouvelle id du modele
     *
     * @return l'instance du modèle.
     */
    @SuppressWarnings(value = {"unchecked"})
    public T setId(Integer id) {
        this.id = id;
        return (T) this;
    }

    /**
     * Surcharge de la méthode {@link Object#toString()}. Appelle la méthode {@link
     * com.google.gson.Gson#toJson(Object)}.
     * <p/>
     * Cette méthode est disponible à des fins de développement, car l'objet JSON
     * résultant est adapté pour un affichage à la console.
     *
     * @return l'objet en format JSON.
     *
     * @see com.google.gson.Gson#toJson(Object)
     * @see GsonBuilder#setPrettyPrinting()
     */
    @Override
    public String toString() {
        return new GsonBuilder().serializeNulls()
                                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                                .setPrettyPrinting()
                                .enableComplexMapKeySerialization()
                                .create()
                                .toJson(this);
    }
}
