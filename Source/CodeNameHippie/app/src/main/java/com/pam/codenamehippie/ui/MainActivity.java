package com.pam.codenamehippie.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;


import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.http.Authentificateur;
import com.pam.codenamehippie.parametre.ParametreActivity;
import com.squareup.okhttp.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    private Authentificateur authentificateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        OkHttpClient httpClient = ((HippieApplication) this.getApplication()).getHttpClient();
        this.authentificateur = ((Authentificateur) httpClient.getAuthenticator());
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        if (toolbar != null) {
            this.setSupportActionBar(toolbar);
        }
    }

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
                Intent intent = new Intent(this, MenuActivity.class);
                intent.putExtra(MenuActivity.ARG_LAYOUT, R.layout.main_circle_layout);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}



