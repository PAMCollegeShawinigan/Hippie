package com.pam.codenamehippie.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.LayoutRes;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.http.Authentificateur;

import okhttp3.OkHttpClient;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Classe de base pour toutes les {@link android.app.Activity} du projet. Sert principalement à
 * propager le menu principal dans l'action bar.
 */
public class HippieActivity extends AppCompatActivity implements ConnectionCallbacks,
                                                                 OnConnectionFailedListener {

    protected Authentificateur authentificateur;
    protected OkHttpClient httpClient;
    protected SharedPreferences sharedPreferences;
    protected ViewSwitcher viewSwitcher;
    protected TextView progressBarTextView;
    protected ProgressBar progressBar;
    protected GoogleApiClient googleApiClient;
    private Boolean progressBarEstAffichee = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.httpClient = ((HippieApplication) this.getApplication()).getHttpClient();
        this.authentificateur = ((Authentificateur) this.httpClient.authenticator());
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

    }

    @Override
    protected void onStart() {
        if (this.googleApiClient != null) {
            this.googleApiClient.connect();
        }
        super.onStart();
        ConnectivityManager connectivityManager =
                ((ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE));
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            if (((activeNetwork != null) && !activeNetwork.isConnected()) ||
                (activeNetwork == null)) {
                WifiManager wifiManager = ((WifiManager) this.getSystemService(WIFI_SERVICE));
                if (((wifiManager != null) && !wifiManager.isWifiEnabled())) {
                    wifiManager.setWifiEnabled(true);
                }
            }
        }
    }

    @Override
    protected void onStop() {
        if (this.googleApiClient != null) {
            this.googleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO: Gérer le back stack comme du monde
        // http://developer.android.com/training/implementing-navigation/temporal.html
        switch (item.getItemId()) {
            case android.R.id.home: {
                Intent intent = this.getSupportParentActivityIntent();
                if ((intent != null) && (NavUtils.shouldUpRecreateTask(this, intent))) {
                    TaskStackBuilder.create(this)
                                    .addNextIntentWithParentStack(intent)
                                    .startActivities();
                } else {
                    NavUtils.navigateUpFromSameTask(this);
                }
            }
            return true;
            // FIXME: Utilisation temporaire pour afficher ListeMesDonsActivity
            case R.id.menu_profil:
                if (!this.getClass().equals(ProfilActivity.class)) {
                    this.startActivity(new Intent(this, ProfilActivity.class));
                }
                return true;
            case R.id.menu_un:
                // Invoque le menu si on est pas déjà dedans
                if (!this.getClass().equals(MenuActivity.class)) {
                    this.startActivity(new Intent(this, MenuActivity.class));
                }
                return true;
            case R.id.info:
                if (!this.getClass().equals(MainActivity.class)) {
                    this.startActivity(new Intent(this, MainActivity.class));
                }
                return true;
            case R.id.menu_deconnexion:
                this.authentificateur.deconnecter();
                this.startActivity(new Intent(this, LoginActivity.class));
                this.finish();
                return true;
            case R.id.menu_aide:
                if (!this.getClass().equals(AideActivity.class)) {
                    this.startActivity(new Intent(this,
                                                  AideActivity.class
                    ));
                }
                return true;
            // FIXME: Utilisation temporaire pour afficher ListeStatistiquesActivity
            case R.id.menu_statistique:
                if (!this.getClass().equals(ListeStatistiquesActivity.class)) {
                    this.startActivity(new Intent(this, ListeStatistiquesActivity.class));
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Fait la même chose que {@link AppCompatActivity#setContentView(int)} à la différence que
     * qu'on appelle {@link AppCompatActivity#setSupportActionBar(Toolbar)} si on trouve une
     * toolbar avec l'id toolbar.
     *
     * @param layoutResID
     *         Ressource de layout à gonfler.
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        // Configuration de l'action bar
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        if (toolbar != null) {
            this.setSupportActionBar(toolbar);
            ActionBar actionBar = this.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setLogo(R.drawable.logo);
                actionBar.setDisplayUseLogoEnabled(true);
                actionBar.setDisplayShowTitleEnabled(false);
                actionBar.setDisplayHomeAsUpEnabled(this.getSupportParentActivityIntent() != null);
            }
        }
        // Configuration du viewSwitcher
        this.viewSwitcher = ((ViewSwitcher) this.findViewById(R.id.main_view_switcher));
        if (this.viewSwitcher != null) {
            this.viewSwitcher.setInAnimation(this, android.R.anim.fade_in);
            this.viewSwitcher.setOutAnimation(this, android.R.anim.fade_out);
        }
        this.progressBar = ((ProgressBar) this.findViewById(R.id.main_progress));
        this.progressBarTextView = ((TextView) this.findViewById(R.id.tv_main_progress));
        if ((this.viewSwitcher != null) && (this.progressBar != null)) {
            this.progressBarEstAffichee = false;
        }
    }

    /**
     * Méthode de comodité pour afficher un layout de progress bar à l'aide d'un view switcher.
     */
    public void afficherLaProgressBar() {
        if (this.progressBarEstAffichee == null) {
            throw new IllegalStateException("Le layout de l'activité ne gère pas l'affichage de " +
                                            "progressbar");
        }
        if (!this.progressBarEstAffichee) {
            this.viewSwitcher.showPrevious();
            this.progressBarEstAffichee = !this.progressBarEstAffichee;
        }
    }

    /**
     * Méthode de comodité pour cacher un layout de progress bar à l'aide d'un view switcher.
     */
    public void cacherLaProgressbar() {
        if (this.progressBarEstAffichee == null) {
            throw new IllegalStateException("Le layout de l'activité ne gère pas l'affichage de " +
                                            "progressbar");
        }
        if (this.progressBarEstAffichee) {
            this.viewSwitcher.showNext();
            this.progressBarEstAffichee = !this.progressBarEstAffichee;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("Connection", "Google client failed to connect" + connectionResult.getErrorMessage());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
