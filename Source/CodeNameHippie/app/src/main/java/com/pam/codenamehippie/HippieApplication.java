package com.pam.codenamehippie;

import android.app.Application;

import com.pam.codenamehippie.http.Authentificateur;
import com.pam.codenamehippie.http.intercepteur.AcceptJsonInterceptor;
import com.pam.codenamehippie.http.intercepteur.HttpDebugInterceptor;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;

import java.net.CookieManager;
import java.net.CookiePolicy;

/**
 * Sous classe de {@link Application} qui sert à initialiser l'application et à stocker les
 * variables globales
 */
public class HippieApplication extends Application {

    public static final HttpUrl baseUrl =
            HttpUrl.parse("http://yolainecourteau.com/hippie/laravel/public/");
    private final OkHttpClient httpClient = new OkHttpClient();

    public OkHttpClient getHttpClient() {
        return this.httpClient;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Configuration du client Http.
        this.httpClient.setAuthenticator(Authentificateur.newInstance(this));
        // Manager de cookie. Par défaut les cookies ne sont pas persistent.
        CookieManager cookieManager = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
        // Configuration du client Http.
        this.httpClient.setAuthenticator(Authentificateur.newInstance(this))
                       .setCookieHandler(cookieManager);
        this.httpClient.networkInterceptors().add(AcceptJsonInterceptor.newInstance());
        if (BuildConfig.DEBUG) {
            // Rapport de debug pour les requêtes.
            this.httpClient.networkInterceptors().add(new HttpDebugInterceptor());
        }

    }
}
