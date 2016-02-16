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
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.http.exception.HttpReponseException;
import com.pam.codenamehippie.modele.UtilisateurModele;
import com.pam.codenamehippie.modele.depot.DepotManager;
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
public final class Authentificateur implements Authenticator {

    public interface Callback {

        void surErreur(IOException e);

        void surSucces(UtilisateurModele utilisateur);
    }

    private static final String TAG = Authentificateur.class.getSimpleName();
    private static final HttpUrl CONNECTION_URL =
            HttpUrl.parse("http://yolainecourteau.com/hippie/laravel/public/");
    private final HippieApplication context;
    private final SharedPreferences preferences;
    private final String userKey;
    private final Object lock = new Object();
    private volatile String motDePasse;
    private volatile UtilisateurModele utilisateur;

    /**
     * Constructeur de l'authentificateur
     */
    private Authentificateur(HippieApplication context) {
        this.context = context;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(this.context);
        this.userKey = this.context.getString(R.string.pref_user_key);
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
            if (this.utilisateur == null) {
                String json = this.preferences.getString(this.userKey, null);
                if (json == null) {
                    return null;
                }
                // Charge l'objet utilisateur des pref
                UtilisateurModeleDepot depot =
                        DepotManager.getInstance().getUtilisateurModeleDepot();
                this.utilisateur = depot.fromJson(json);

            }
            return this.utilisateur;
        }
    }

    public void setUtilisateur(@NonNull UtilisateurModele utilisateur) {
        synchronized (this.lock) {
            this.utilisateur = utilisateur;
            UtilisateurModeleDepot depot = DepotManager.getInstance().getUtilisateurModeleDepot();
            this.preferences.edit().putString(this.userKey, depot.toJson(this.utilisateur)).apply();
        }
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        Log.d(TAG, "Authenticating for resp: " + response.toString());
        for (Challenge challenge : response.challenges()) {
            Log.d(TAG, "Challenge: " + challenge.toString());
        }
        synchronized (this.lock) {
            String courriel = this.utilisateur.getCourriel();
            if ((courriel != null) && (this.motDePasse != null)) {
                String credentials = Credentials.basic(courriel, this.motDePasse);
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
     * Fonction pour vérifié si on est authentifié. On se considère authentifié si on a un objet
     * utilisateur ou que l'authentificateur à un mot de passe en mémoire.
     *
     * @return Vrai si on est authentifié faux sinon.
     */
    public boolean estAuthentifie() {
        //TODO: Token d'authenfication
        return ((this.motDePasse != null) || (this.getUtilisateur() != null));
    }

    /**
     * Connecte l'application au service web avec l'utilisateur existant.
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
     */
    public void connecter(@NonNull String motDePasse, @NonNull Callback callback) {
        this.setMotDePasse(motDePasse);
        RequestBody requestBody =
                new FormBody.Builder().add("courriel", this.utilisateur.getCourriel())
                                      .add("mot_de_passe", this.getMotDePasse())
                                      .build();
        this.connecter(requestBody, callback);
    }

    /**
     * Connecte l'application au service web avec le mot de passe et courriel passé en paramètre.
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
     */
    public void connecter(@NonNull String courriel,
                          @NonNull String motDePasse,
                          @NonNull Callback callback) {
        this.setMotDePasse(motDePasse);
        RequestBody requestBody = new FormBody.Builder().add("courriel", courriel)
                                                        .add("mot_de_passe", this.getMotDePasse())
                                                        .build();
        this.connecter(requestBody, callback);
    }

    /**
     * Connecte l'application au service web.
     *
     * @param requestBody trucs à envoyer en paramètres au serveur.
     *
     * @param callback
     *         callback pour les résultats de l'opération
     */
    private void connecter(@NonNull RequestBody requestBody, @NonNull final Callback callback) {
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
                    UtilisateurModeleDepot depot =
                            DepotManager.getInstance().getUtilisateurModeleDepot();
                    Reader reader = response.body().charStream();
                    Authentificateur.this.setUtilisateur(depot.fromJson(reader));
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
        this.preferences.edit().remove(this.userKey).commit();
        synchronized (this.lock) {
            this.motDePasse = null;
            this.utilisateur = null;
        }
    }
}
