package com.pam.codenamehippie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.http.Authentificateur;
import com.pam.codenamehippie.parametre.ParametreActivity;
import com.squareup.okhttp.OkHttpClient;

/**
 * Classe de base pour toutes les {@link android.app.Activity} du projet. Sert principalement à
 * propager le menu principal dans l'action bar.
 */
public class HippieActivity extends AppCompatActivity {

    private Authentificateur authentificateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OkHttpClient httpClient = ((HippieApplication) this.getApplication()).getHttpClient();
        this.authentificateur = ((Authentificateur) httpClient.getAuthenticator());
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
            case R.id.menu_parametre:
                this.startActivity(new Intent(this, ParametreActivity.class));
                return true;
            case R.id.menu_deconnexion:
                this.authentificateur.deconnecte();
                this.startActivity(new Intent(this, LoginActivity.class));
                this.finish();
                return true;
            case R.id.menu_un:
                this.startActivity(new Intent(this, MenuActivity.class));
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
        }
    }
}
