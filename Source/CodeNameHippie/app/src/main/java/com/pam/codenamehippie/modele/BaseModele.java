package com.pam.codenamehippie.modele;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public abstract class BaseModele {

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
