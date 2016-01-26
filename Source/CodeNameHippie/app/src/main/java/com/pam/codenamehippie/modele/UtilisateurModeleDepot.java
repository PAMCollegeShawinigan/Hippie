package com.pam.codenamehippie.modele;

import android.content.Context;

import okhttp3.OkHttpClient;

/**
 * Créé par Carl St-Louis le 23-11-2015.
 */

public class UtilisateurModeleDepot extends BaseModeleDepot<UtilisateurModele> {

    public UtilisateurModeleDepot(Context context, OkHttpClient httpClient) {
        super(context, httpClient);
        this.url = this.url.newBuilder().addPathSegment("utilisateur").build();
    }
}
