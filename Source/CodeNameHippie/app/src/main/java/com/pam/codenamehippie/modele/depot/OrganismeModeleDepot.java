/*
 * OrganismeModeleDepot.java
 * CodeNameHippie
 *
 * Copyright (c) 2016. Philippe Lafontaine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.pam.codenamehippie.modele.depot;

import android.content.Context;

import com.pam.codenamehippie.modele.OrganismeModele;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

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
