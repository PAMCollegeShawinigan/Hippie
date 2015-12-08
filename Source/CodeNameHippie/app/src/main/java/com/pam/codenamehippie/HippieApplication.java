package com.pam.codenamehippie;

import android.app.Application;

import com.pam.codenamehippie.http.Authentificateur;
import com.pam.codenamehippie.http.intercepteur.AcceptJsonInterceptor;
import com.pam.codenamehippie.http.intercepteur.HttpDebugInterceptor;
import com.pam.codenamehippie.modele.MarchandiseModeleDepot;
import com.pam.codenamehippie.modele.OrganismeModeleDepot;
import com.pam.codenamehippie.modele.TransactionModeleDepot;
import com.pam.codenamehippie.modele.UtilisateurModeleDepot;
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
    /**
     * Instance du client http pour l'application
     */
    private final OkHttpClient httpClient = new OkHttpClient();

    /**
     * Instance de {@link UtilisateurModeleDepot} pour l'application
     */
    private final UtilisateurModeleDepot utilisateurModeleDepot =
            new UtilisateurModeleDepot(this.httpClient);

    /**
     * Instance d'{@link OrganismeModeleDepot} pour l'application
     */
    private final OrganismeModeleDepot organismeModeleDepot =
            new OrganismeModeleDepot(this.httpClient);

    /**
     * Instance de {@link TransactionModeleDepot} pour l'application
     */
    private final TransactionModeleDepot transactionModeleDepot =
            new TransactionModeleDepot(this.httpClient);

    /**
     * Instance de {@link MarchandiseModeleDepot} pour l'application
     */
    private final MarchandiseModeleDepot marchandiseModeleDepot =
            new MarchandiseModeleDepot(this.httpClient);

    public OkHttpClient getHttpClient() {
        return this.httpClient;
    }

    public synchronized UtilisateurModeleDepot getUtilisateurModeleDepot() {
        return this.utilisateurModeleDepot;
    }

    public synchronized OrganismeModeleDepot getOrganismeModeleDepot() {
        return this.organismeModeleDepot;
    }

    public synchronized TransactionModeleDepot getTransactionModeleDepot() {
        return this.transactionModeleDepot;
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
