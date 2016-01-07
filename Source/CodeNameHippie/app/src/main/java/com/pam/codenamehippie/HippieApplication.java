package com.pam.codenamehippie;

import android.app.Application;

import com.pam.codenamehippie.http.Authentificateur;
import com.pam.codenamehippie.http.PersistentCookieStore;
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
    /**
     * Instance de {@link PersistentCookieStore} pour l'application
     */
    private volatile PersistentCookieStore boiteAbiscuit;

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

    public MarchandiseModeleDepot getMarchandiseModeleDepot() {
        return this.marchandiseModeleDepot;
    }

    public PersistentCookieStore getBoiteAbiscuit() {
        return this.boiteAbiscuit;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Manager de cookie.
        // Les cookies sont sauvegardé dans shared preference.
        this.boiteAbiscuit = new PersistentCookieStore(this);
        CookieManager cookieManager = new CookieManager(this.boiteAbiscuit,
                                                        CookiePolicy.ACCEPT_ALL
        );
        // Configuration du client Http.
        this.httpClient.setAuthenticator(Authentificateur.newInstance(this, this.boiteAbiscuit))
                       .setCookieHandler(cookieManager);
        this.httpClient.networkInterceptors().add(AcceptJsonInterceptor.newInstance());
        if (BuildConfig.DEBUG) {
            // Rapport de debug pour les requêtes.
            this.httpClient.networkInterceptors().add(new HttpDebugInterceptor());
        }
    }
}
