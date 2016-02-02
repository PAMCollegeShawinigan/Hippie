package com.pam.codenamehippie.modele;

import android.content.Context;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Créé par Carl St-Louis le 23-11-2015.
 */

public class OrganismeModeleDepot extends BaseModeleDepot<OrganismeModele> {

    private static final String TAG = OrganismeModeleDepot.class.getSimpleName();

    private final HttpUrl listeOrganismeDonneurUrl;

    /**
     * Construction du dépot pour modèle Organisme
     */
    public OrganismeModeleDepot(Context context, OkHttpClient httpClient) {
        super(context, httpClient);
        this.listeOrganismeDonneurUrl = this.url.newBuilder().addPathSegment("carte").build();
        this.url = this.url.newBuilder().addPathSegment("organisme").build();
    }

    public void peuplerListeDonneur() {
        this.peuplerLeDepot(this.listeOrganismeDonneurUrl);

    }
    public void peuplerListeOrganisme() {
        this.peuplerLeDepot(this.url);
    }
}
