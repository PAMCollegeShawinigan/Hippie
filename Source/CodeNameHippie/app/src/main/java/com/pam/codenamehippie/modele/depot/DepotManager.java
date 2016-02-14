package com.pam.codenamehippie.modele.depot;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import com.pam.codenamehippie.modele.BaseModele;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import okhttp3.OkHttpClient;

/**
 * Classe servant à gérer les depots. Cette classe enregistre automatiquement les activitées qui
 * implémente l'interface {@link ObservateurDeDepot} au bon dépot. Cette classe est un singleton.
 */
public final class DepotManager implements ActivityLifecycleCallbacks {

    public static final String DEPOT_MANAGER = DepotManager.class.getSimpleName();

    private static final AtomicReference<DepotManager> instanceRef = new AtomicReference<>(null);
    private final Application application;
    /**
     * Instance du client http pour l'application
     */
    private final OkHttpClient httpClient;

    private final Map<Class, Class> registreActivite = new ArrayMap<>();

    private final Map<Class, BaseModeleDepot> registreDeDepot = new ArrayMap<>();

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

    private DepotManager(Application application, OkHttpClient httpClient) {
        this.application = application;
        this.httpClient = httpClient;
    }

    /**
     * Initialise le manager de dépot. Doit être appelée avant {@link DepotManager#getInstance()}
     * et au maximum une fois.
     * <p>
     * Cette méthode est en théorie thread-safe.
     * </p>
     *
     * @param application
     *         Application courante
     * @param httpClient
     *         Le client http a utiliser pour tout les depots
     *
     * @throws IllegalStateException
     *         Si une instance existe déjà.
     */
    public static void init(@NonNull Application application, @NonNull OkHttpClient httpClient) {
        DepotManager instance = new DepotManager(application, httpClient);
        if (!instanceRef.compareAndSet(null, instance)) {
            throw new IllegalStateException("Already initialzed");
        } else {
            application.registerActivityLifecycleCallbacks(instance);
        }
    }

    /**
     * Retourne le manager de dépot.  {@link DepotManager#init(Application, OkHttpClient)} doit
     * être appelée avant cette méthode.
     * <p>
     * Cette méthode est en théorie thread-safe.
     * </p>
     *
     * @throws IllegalStateException
     *         Si une instance n'existe pas déjà.
     */
    public static DepotManager getInstance() {
        DepotManager instance = instanceRef.get();
        if (instance == null) {
            throw new IllegalStateException("Not initialzed");
        }
        return instance;
    }

    public synchronized OkHttpClient getHttpClient() {
        return this.httpClient;
    }

    @Deprecated
    public synchronized UtilisateurModeleDepot getUtilisateurModeleDepot() {
        if (this.utilisateurModeleDepot == null) {
            this.utilisateurModeleDepot =
                    new UtilisateurModeleDepot(this.application, this.httpClient);
            this.registreDeDepot.put(this.utilisateurModeleDepot.classeDeT,
                                     this.utilisateurModeleDepot
                                    );
        }
        return this.utilisateurModeleDepot;
    }

    @Deprecated
    public synchronized OrganismeModeleDepot getOrganismeModeleDepot() {
        if (this.organismeModeleDepot == null) {
            this.organismeModeleDepot = new OrganismeModeleDepot(this.application, this.httpClient);
            this.registreDeDepot.put(this.organismeModeleDepot.classeDeT,
                                     this.organismeModeleDepot
                                    );
        }
        return this.organismeModeleDepot;
    }

    @Deprecated
    public synchronized TransactionModeleDepot getTransactionModeleDepot() {
        if (this.transactionModeleDepot == null) {
            this.transactionModeleDepot =
                    new TransactionModeleDepot(this.application, this.httpClient);
            this.registreDeDepot.put(this.transactionModeleDepot.classeDeT,
                                     this.transactionModeleDepot
                                    );
        }
        return this.transactionModeleDepot;
    }

    @Deprecated
    public synchronized AlimentaireModeleDepot getAlimentaireModeleDepot() {
        if (this.alimentaireModeleDepot == null) {
            this.alimentaireModeleDepot =
                    new AlimentaireModeleDepot(this.application, this.httpClient);
            this.registreDeDepot.put(this.alimentaireModeleDepot.classeDeT,
                                     this.alimentaireModeleDepot
                                    );
        }
        return this.alimentaireModeleDepot;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Class clazz = activity.getClass();
        if (ObservateurDeDepot.class.isAssignableFrom(clazz)) {
            Type[] interfaces = clazz.getGenericInterfaces();
            for (Type iface : interfaces) {
                ParameterizedType gIface =
                        ((iface instanceof ParameterizedType) ? ((ParameterizedType) iface) : null);
                if (gIface != null) {
                    for (Type arg : gIface.getActualTypeArguments()) {
                        if (BaseModele.class.isAssignableFrom((Class) arg)) {
                            if (!this.registreActivite.containsKey(clazz)) {
                                this.registreActivite.put(clazz, (Class) arg);
                            }
                        }
                    }
                }
            }
        }

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    @SuppressWarnings("unchecked")
    public void onActivityResumed(Activity activity) {
        Class modelClass = this.registreActivite.get(activity.getClass());
        if (modelClass != null) {
            BaseModeleDepot depot = this.registreDeDepot.get(modelClass);
            if (depot != null) {
                depot.ajouterUnObservateur((ObservateurDeDepot) activity);
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onActivityPaused(Activity activity) {
        Class modelClass = this.registreActivite.get(activity.getClass());
        if (modelClass != null) {
            BaseModeleDepot depot = this.registreDeDepot.get(modelClass);
            if (depot != null) {
                depot.setFiltreDeListe(null);
                depot.supprimerTousLesObservateurs();
            }
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
