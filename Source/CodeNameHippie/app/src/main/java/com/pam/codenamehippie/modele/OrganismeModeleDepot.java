package com.pam.codenamehippie.modele;

import android.content.Context;
import android.util.Log;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Créé par Carl St-Louis le 23-11-2015.
 */

public class OrganismeModeleDepot extends BaseModeleDepot<OrganismeModele> {

    private static final String TAG = OrganismeModeleDepot.class.getSimpleName();

    private final HttpUrl listeOrganismeDonneur;

    /**
     * Construction du dépot pour modèle Organisme
     */
    public OrganismeModeleDepot(Context context, OkHttpClient httpClient) {
        super(context, httpClient);
        this.listeOrganismeDonneur = this.url.newBuilder().addPathSegment("carte").build();
        this.url = this.url.newBuilder().addPathSegment("organisme").build();
    }

    public void peuplerListeDonneur() {
        this.peuplerLeDepot(this.listeOrganismeDonneur);

    }
    public void peuplerListeOrganisme() {
        this.peuplerLeDepot(this.url);
    }
}
