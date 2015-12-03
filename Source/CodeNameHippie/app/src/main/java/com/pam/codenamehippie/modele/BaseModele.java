package com.pam.codenamehippie.modele;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public abstract class BaseModele {

    // TODO : vérifier convention avec la BD
    @SerializedName(value = "id",
                    alternate = {"id_utilisateur", "id_organisme", "id_transaction", "id"})
    private int id;

    // Accesseur
    public int getId() {
        return id;
    }

    // FIXME : arrange toi avec ça
    /**
     * Surcharge de la méthode {@link Object#toString()} appelle la méthode
     * {@link BaseModele#toJSon()}.
     *
     * @return l'objet en format JSON.
     *
     * @see BaseModele#toJSon()
     */
//    @Override
//    public String toString() {
//        return this.toJSon();
//    }
}
