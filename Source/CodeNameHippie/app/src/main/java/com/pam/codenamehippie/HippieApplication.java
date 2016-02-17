package com.pam.codenamehippie;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.pam.codenamehippie.http.Authentificateur;
import com.pam.codenamehippie.http.intercepteur.AcceptJsonInterceptor;
import com.pam.codenamehippie.http.intercepteur.AcceptLanguageInterceptor;
import com.pam.codenamehippie.modele.depot.AlimentaireModeleDepot;
import com.pam.codenamehippie.modele.depot.DepotManager;
import com.pam.codenamehippie.modele.depot.OrganismeModeleDepot;
import com.pam.codenamehippie.modele.depot.TransactionModeleDepot;
import com.pam.codenamehippie.modele.depot.UtilisateurModeleDepot;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Sous classe de {@link Application} qui sert à initialiser l'application et à stocker les
 * variables globales
 */
public class HippieApplication extends Application {

    public static final HttpUrl BASE_URL =
            HttpUrl.parse("http://yolainecourteau.com/hippie/laravel/public/");
    protected final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    /**
     * Instance du client http pour l'application
     */
    private volatile OkHttpClient httpClient;
    /**
     * Instance de {@link UtilisateurModeleDepot} pour l'application
     */
    private volatile UtilisateurModeleDepot utilisateurModeleDepot;

    /**
     * Instance d'{@link OrganismeModeleDepot} pour l'application
     */
    private volatile OrganismeModeleDepot organismeModeleDepot;
    /**
     * Instance de {@link TransactionModeleDepot} pour l'application
     */
    private volatile TransactionModeleDepot transactionModeleDepot;
    /**
     * Instance de {@link AlimentaireModeleDepot} pour l'application
     */
    private volatile AlimentaireModeleDepot alimentaireModeleDepot;

    public synchronized OkHttpClient getHttpClient() {
        return this.httpClient;
    }

    /**
     * Accesseur de depot
     *
     * @return Le depot correspondant au nom de la méthode
     *
     * @deprecated Utiliser {@link DepotManager#getAlimentaireModeleDepot()}
     */
    @Deprecated
    public synchronized AlimentaireModeleDepot getAlimentaireModeleDepot() {
        if (this.alimentaireModeleDepot == null) {
            this.alimentaireModeleDepot = new AlimentaireModeleDepot(this, this.httpClient);
        }
        return this.alimentaireModeleDepot;
    }

    /**
     * Accesseur de depot
     *
     * @return Le depot correspondant au nom de la méthode
     *
     * @deprecated Utiliser {@link DepotManager#getOrganismeModeleDepot()}
     */
    @Deprecated
    public synchronized OrganismeModeleDepot getOrganismeModeleDepot() {
        if (this.organismeModeleDepot == null) {
            this.organismeModeleDepot = new OrganismeModeleDepot(this, this.httpClient);
        }
        return this.organismeModeleDepot;
    }

    /**
     * Accesseur de depot
     *
     * @return Le depot correspondant au nom de la méthode
     *
     * @deprecated Utiliser {@link DepotManager#getTransactionModeleDepot()}
     */
    @Deprecated
    public synchronized TransactionModeleDepot getTransactionModeleDepot() {
        if (this.transactionModeleDepot == null) {
            this.transactionModeleDepot = new TransactionModeleDepot(this, this.httpClient);
        }
        return this.transactionModeleDepot;
    }

    /**
     * Accesseur de depot
     *
     * @return Le depot correspondant au nom de la méthode
     *
     * @deprecated Utiliser {@link DepotManager#getUtilisateurModeleDepot()}
     */
    @Deprecated
    public synchronized UtilisateurModeleDepot getUtilisateurModeleDepot() {
        if (this.utilisateurModeleDepot == null) {
            this.utilisateurModeleDepot = new UtilisateurModeleDepot(this, this.httpClient);
        }
        return this.utilisateurModeleDepot;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Configuration du logger http
        Level level = (BuildConfig.DEBUG) ? Level.BODY : Level.HEADERS;
        // Configuration du client Http.
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.authenticator(Authentificateur.newInstance(this))
                         .addNetworkInterceptor(AcceptJsonInterceptor.newInstance())
                         .addNetworkInterceptor(AcceptLanguageInterceptor.newInstance(this))
                         .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(level));
        this.httpClient = httpClientBuilder.build();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                                              .setDefaultFontPath("fonts/opensans_light.ttf")
                                              .setFontAttrId(R.attr.fontPath)
                                              .build()
        );
        DepotManager.init(this, this.httpClient);
    }

    @Override
    public String getSystemServiceName(Class<?> serviceClass) {
        if (serviceClass.equals(DepotManager.class)) {
            return DepotManager.DEPOT_SERVICE;
        }
        return super.getSystemServiceName(serviceClass);
    }

    @Override
    public Object getSystemService(String name) {
        if (name.equals(DepotManager.DEPOT_SERVICE)) {
            return DepotManager.getInstance();
        }
        return super.getSystemService(name);
    }

    /**
     * Réimplémentation de {@link android.app.Activity#runOnUiThread(Runnable)}
     *
     * @param action
     *         truc à rouler sur le main thread
     *
     * @see android.app.Activity#runOnUiThread(Runnable)
     */
    public void runOnUiThread(Runnable action) {
        if (Thread.currentThread() != this.mainThreadHandler.getLooper().getThread()) {
            this.mainThreadHandler.post(action);
        } else {
            action.run();
        }
    }
}
