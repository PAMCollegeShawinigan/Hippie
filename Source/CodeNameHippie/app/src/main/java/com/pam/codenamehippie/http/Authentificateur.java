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
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.http.exception.HttpReponseException;
import com.pam.codenamehippie.modele.OrganismeModele;
import com.pam.codenamehippie.modele.UtilisateurModele;
import com.pam.codenamehippie.modele.depot.UtilisateurModeleDepot;

import java.io.IOException;
import java.io.Reader;
import java.net.HttpRetryException;

import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.Challenge;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Classe servant de délégué au client HTTP pour les authentification de type Basic.
 */
public final class Authentificateur
        implements Authenticator, SharedPreferences.OnSharedPreferenceChangeListener {

    public interface Callback {

        void surErreur(IOException e);

        void surSucces(UtilisateurModele utilisateur);
    }

    private static final String TAG = Authentificateur.class.getSimpleName();
    private static final HttpUrl CONNECTION_URL =
            HttpUrl.parse("http://yolainecourteau.com/hippie/laravel/public/");
    private final HippieApplication context;
    private final SharedPreferences preferences;
    private final String userIdKey;
    private final String orgIdKey;
    private final String emailKey;
    private final Object lock = new Object();
    private volatile String courriel;
    private volatile String motDePasse;
    private volatile UtilisateurModele utilisateur;

    /**
     * Constructeur de l'authentificateur
     */
    private Authentificateur(HippieApplication context) {
        this.context = context;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(this.context);
        this.preferences.registerOnSharedPreferenceChangeListener(this);
        this.userIdKey = this.context.getString(R.string.pref_user_id_key);
        this.orgIdKey = this.context.getString(R.string.pref_org_id_key);
        this.emailKey = this.context.getString(R.string.pref_email_key);
        this.courriel = this.preferences.getString(this.emailKey, null);
    }

    /**
     * Méthode usine statique pour créer une nouvelle instance.
     **/
    public static Authentificateur newInstance(HippieApplication context) {
        return new Authentificateur(context);
    }

    public String getMotDePasse() {
        synchronized (this.lock) {
            return this.motDePasse;
        }
    }

    public void setMotDePasse(String motDePasse) {
        synchronized (this.lock) {
            this.motDePasse = motDePasse;
        }
    }

    public UtilisateurModele getUtilisateur() {
        synchronized (this.lock) {
            return this.utilisateur;
        }
    }

    @SuppressLint("CommitPrefEdits")
    public void setUtilisateur(@NonNull UtilisateurModele utilisateur) {
        synchronized (this.lock) {
            this.utilisateur = utilisateur;
            Editor editor =
                    this.preferences.edit().putInt(this.userIdKey, this.utilisateur.getId());
            editor.putString(this.emailKey, this.utilisateur.getCourriel());
            OrganismeModele organisme = this.utilisateur.getOrganisme();
            if (organisme != null) {
                editor.putInt(this.orgIdKey, organisme.getId());
            }
            editor.commit();
        }
    }

    public String getCourriel() {
        synchronized (this.lock) {
            return this.courriel;
        }
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        Log.d(TAG, "Authenticating for resp: " + response.toString());
        for (Challenge challenge : response.challenges()) {
            Log.d(TAG, "Challenge: " + challenge.toString());
        }
        synchronized (this.lock) {
            if ((this.courriel != null) && (this.motDePasse != null)) {
                String credentials = Credentials.basic(this.courriel, this.motDePasse);
                return response.request().newBuilder().header("Authorization", credentials).build();
            } else {
                // On lance une exeception si le combo mot de passe/email est pas bon.
                throw new HttpRetryException(response.message(),
                                             response.code(),
                                             response.request().url().toString()
                );
            }
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
        //TODO: Token d'authenfication
        return ((this.motDePasse != null) ||
                (this.preferences.contains(this.context.getString(R.string.pref_user_id_key))));
    }

    /**
     * Connecte l'application au service web.
     * <p>
     * Cet méthode est équivalente à:
     * </p>
     * <pre class=”prettyprint”>
     * authenficateur.setMotDePasse(motDePasse);
     * authenficateur.connecter(callback);
     * </pre>
     * Cette méthode est asychrone et retourne immédiatement
     *
     * @param motDePasse
     *         mot de passe à utiliser
     * @param callback
     *         callback pour les résultats de l'opération
     *
     * @see Authentificateur#connecter(Callback)
     */
    public void connecter(@NonNull String motDePasse, @NonNull Callback callback) {
        this.setMotDePasse(motDePasse);
        this.connecter(callback);
    }

    /**
     * Connecte l'application au service web.
     *
     * @param callback
     *         callback pour les résultats de l'opération
     */
    public void connecter(@NonNull final Callback callback) {
        RequestBody requestBody =
                new FormBody.Builder().add("courriel", this.getCourriel())
                                      .add("mot_de_passe", this.getMotDePasse())
                                      .build();
        Request request =
                new Request.Builder().url(CONNECTION_URL).post(requestBody).build();
        this.context.getHttpClient().newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Authentificateur.this.context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.surErreur(e);
                    }
                });
                // On "déconnecte": on a échoué.
                Authentificateur.this.deconnecter();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Authentificateur.this.context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.surErreur(new HttpReponseException(response));
                        }
                    });
                    // On "déconnecte": on a échoué.
                    Authentificateur.this.deconnecter();
                } else {
                    UtilisateurModeleDepot depotUtilisateur =
                            Authentificateur.this.context.getUtilisateurModeleDepot();
                    Reader reader = response.body().charStream();
                    Authentificateur.this.setUtilisateur(depotUtilisateur.fromJson(reader));
                    Authentificateur.this.context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.surSucces(Authentificateur.this.getUtilisateur());

                        }
                    });
                }
            }
        });
    }

    @SuppressLint("CommitPrefEdits")
    public void deconnecter() {
        // TODO: Mieux gérer l'authentification.
        Editor editor = this.preferences.edit();
        // On supprime l'ID d'organisme.
        if (this.preferences.contains(this.orgIdKey)) {
            editor.remove(this.orgIdKey);
        }
        // On supprime l'ID de l'utilisateur.
        if (this.preferences.contains(this.userIdKey)) {
            editor.remove(this.userIdKey);
        }
        editor.commit();
        synchronized (this.lock) {
            this.motDePasse = null;
            this.utilisateur = null;
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        synchronized (this.lock) {
            if (key.equals(this.context.getString(R.string.pref_email_key))) {
                this.courriel = sharedPreferences.getString(key, null);
            }
        }
    }
}
