/*
 * Authenficateur.java
 * CodeNameHippie
 *
 * Copyright (c) 2015. Philippe Lafontaine
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

package com.pam.codenamehippie.http;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

import com.pam.codenamehippie.R;

import java.io.IOException;
import java.net.HttpRetryException;

import okhttp3.Authenticator;
import okhttp3.Challenge;
import okhttp3.Credentials;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Classe servant de délégué au client HTTP pour les authentification de type Basic.
 */
public final class Authentificateur implements Authenticator {

    private static final String TAG = Authentificateur.class.getSimpleName();
    private final Context context;
    private final SharedPreferences preferences;
    private volatile String motDePasse;

    /**
     * Constructeur de l'authentificateur
     */
    private Authentificateur(Context context) {
        this.context = context;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Méthode usine statique pour créer une nouvelle instance.
     **/
    public static Authentificateur newInstance(Context context) {
        return new Authentificateur(context);
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        String email =
                this.preferences.getString(this.context.getString(R.string.pref_email_key), null);
        Log.d(TAG, "Authenticating for resp: " + response.toString());
        for (Challenge challenge : response.challenges()) {
            Log.d(TAG, "Challenge: " + challenge.toString());
        }
        if ((email != null) && (this.motDePasse != null)) {
            String credentials = Credentials.basic(email, this.motDePasse);
            return response.request().newBuilder().header("Authorization", credentials).build();
        } else {
            // On lance une exeception si le combo mot de passe/email est pas bon.
            throw new HttpRetryException(response.message(),
                                         response.code(),
                                         response.request().url().toString()
            );
        }
    }

    /**
     * Fonction pour vérifié si on est authentifié. On se considère authentifié si on a des
     * cookies ou que l'authentificateur à un mot de passe en mémoire.
     *
     * @return vrai si on est authentifié faux sinon.
     */
    public boolean estAuthentifie() {
        // On check seulement si on a un user id en mémoire.
        //TODO: Un vrai syst
        return ((this.motDePasse != null) ||
                (this.preferences.contains(this.context.getString(R.string.pref_user_id_key))));
    }

    @SuppressLint("CommitPrefEdits")
    public synchronized void deconnecte() {
        // TODO: Mieux gérer l'authentification.
        String userIdKey = this.context.getString(R.string.pref_user_id_key);
        String orgIdKey = this.context.getString(R.string.pref_org_id_key);
        Editor editor = this.preferences.edit();
        // On supprime l'ID d'organisme.
        if (this.preferences.contains(orgIdKey)) {
            editor.remove(orgIdKey);
        }
        // On supprime l'ID de l'utilisateur.
        if (this.preferences.contains(userIdKey)) {
            editor.remove(userIdKey);
        }
        editor.commit();
        this.motDePasse = null;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}
