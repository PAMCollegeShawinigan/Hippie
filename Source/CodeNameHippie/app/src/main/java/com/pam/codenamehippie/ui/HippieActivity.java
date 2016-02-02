package com.pam.codenamehippie.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.LayoutRes;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.ViewSwitcher;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.http.Authentificateur;

import okhttp3.OkHttpClient;

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
    protected ProgressBar progressBar;
    protected GoogleApiClient googleApiClient;

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
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
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
                this.authentificateur.deconnecte();
                this.startActivity(new Intent(this, LoginActivity.class));
                this.finish();
                return true;
            // TODO: À effacer plus tard, lorsque les tests avec cette activité sera terminé.
            case R.id.menu_marchandise_disponible:
                if (!this.getClass().equals(ListeMarchandisesDisponiblesActivity.class)) {
                    this.startActivity(new Intent(this,
                                                  ListeMarchandisesDisponiblesActivity.class
                    ));
                }
                return true;
            case R.id.menu_aide:
                if (!this.getClass().equals(AideActivity.class)) {
                    this.startActivity(new Intent(this,
                            AideActivity.class
                    ));
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
            }
        }
        // Configuration du viewSwitcher
        this.viewSwitcher = ((ViewSwitcher) this.findViewById(R.id.main_view_switcher));
        if (this.viewSwitcher != null) {
            this.viewSwitcher.setInAnimation(this, android.R.anim.fade_in);
            this.viewSwitcher.setOutAnimation(this, android.R.anim.fade_out);
        }

        this.progressBar = ((ProgressBar) this.findViewById(R.id.main_progress));
    }

    /**
     * Méthode de comodité pour afficher un layout de progress bar à l'aide d'un view switcher.
     */
    public void afficherLaProgressBar() {
        if (this.progressBar == null) {
            throw new IllegalStateException("Le layout de l'activité ne contient pas de " +
                                            "progressbar.");
        }
        if (this.viewSwitcher == null) {
            throw new IllegalStateException("Le layout de l'activité ne contient pas de " +
                                            "ViewSwitcher pour gérer l'affichage de la " +
                                            "progressbar");
        }
        this.viewSwitcher.showNext();
    }

    /**
     * Méthode de comodité pour cacher un layout de progress bar à l'aide d'un view switcher.
     */
    public void cacherLaProgressbar() {
        if (this.progressBar == null) {
            throw new IllegalStateException("Le layout de l'activité ne contient pas de " +
                                            "progressbar.");
        }
        if (this.viewSwitcher == null) {
            throw new IllegalStateException("Le layout de l'activité ne contient pas de " +
                                            "ViewSwitcher pour gérer l'affichage de la " +
                                            "progressbar");
        }
        this.viewSwitcher.showPrevious();
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
