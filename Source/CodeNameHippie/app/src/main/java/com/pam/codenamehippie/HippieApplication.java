package com.pam.codenamehippie;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.pam.codenamehippie.http.Authentificateur;
import com.pam.codenamehippie.http.intercepteur.AcceptJsonInterceptor;
import com.pam.codenamehippie.http.intercepteur.AcceptLanguageInterceptor;
import com.pam.codenamehippie.modele.depot.DepotManager;

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

    public synchronized OkHttpClient getHttpClient() {
        return this.httpClient;
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
