package com.pam.codenamehippie.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.LayoutRes;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.http.Authentificateur;
import com.squareup.okhttp.OkHttpClient;

/**
 * Classe de base pour toutes les {@link android.app.Activity} du projet. Sert principalement à
 * propager le menu principal dans l'action bar.
 */
public class HippieActivity extends  AppCompatActivity
{

    protected Authentificateur authentificateur;
    protected OkHttpClient httpClient;
    protected SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.httpClient = ((HippieApplication) this.getApplication()).getHttpClient();
        this.authentificateur = ((Authentificateur) this.httpClient.getAuthenticator());
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
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

            case R.id.menu_profil:
                if (!this.getClass().equals(ListeMesDonsActivity.class)) {
                    this.startActivity(new Intent(this, ListeMesDonsActivity.class));
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
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        if (toolbar != null) {
            this.setSupportActionBar(toolbar);
            this.getSupportActionBar().setLogo(R.drawable.logo);
            this.getSupportActionBar().setDisplayUseLogoEnabled(true);
            this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }
}
