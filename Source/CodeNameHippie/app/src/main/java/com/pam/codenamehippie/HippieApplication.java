package com.pam.codenamehippie;

import android.app.Application;

import com.pam.codenamehippie.auth.Authentificateur;
import com.squareup.okhttp.OkHttpClient;

/**
 * Sous classe de {@link Application} qui sert à initialiser l'application et à stocker les
 * variables globales
 */
public class HippieApplication extends Application {

    private final OkHttpClient httpClient = new OkHttpClient();

    @Override
    public void onCreate() {
        super.onCreate();
        this.httpClient.setAuthenticator(Authentificateur.newInstance(this));
    }
}
