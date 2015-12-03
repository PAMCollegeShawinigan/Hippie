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

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.pam.codenamehippie.R;
import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Challenge;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.Proxy;

/**
 * Classe servant de délégué au client HTTP pour les authentification de type Basic.
 */
public final class Authentificateur implements Authenticator {

    private static final String TAG = Authentificateur.class.getSimpleName();
    private final Context context;
    private final SharedPreferences preferences;

    // Constructeur de l'authentification
    private Authentificateur(Context context) {
        this.context = context;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(this.context);
    }

<<<<<<< HEAD:Source/CodeNameHippie/app/src/main/java/com/pam/codenamehippie/auth/Authentificateur.java
    // Méthode usine static pour créer une nouvelle instance de Authentificateur
=======
    /**
     * Méthode usine statique pour créer une nouvelle instance.
     **/
>>>>>>> refs/heads/pr/4:Source/CodeNameHippie/app/src/main/java/com/pam/codenamehippie/http/Authentificateur.java
    public static Authentificateur newInstance(Context context) {
        return new Authentificateur(context);
    }

    @Override
    public Request authenticate(Proxy proxy, Response response) throws IOException {
        String email =
          this.preferences.getString(this.context.getString(R.string.pref_email_key), null);
        String password =
          this.preferences.getString(this.context.getString(R.string.pref_password_key), null);
        Log.d(TAG, "Authenticating for resp: " + response.toString());
        for (Challenge challenge : response.challenges()) {
            Log.d(TAG, "Challenge: " + challenge.toString());
        }
        if ((email != null) && (password != null)) {
            String credentials = Credentials.basic(email, password);
            return response.request().newBuilder().header("Authorization", credentials).build();
        } else {
            return null;
        }
    }

    @Override
    public Request authenticateProxy(Proxy proxy, Response response) throws IOException {
        return null;
    }
}
