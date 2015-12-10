package com.pam.codenamehippie.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.controleur.validation.Validateur;
import com.pam.codenamehippie.controleur.validation.ValidateurCourriel;
import com.pam.codenamehippie.controleur.validation.ValidateurMotDePasse;
import com.pam.codenamehippie.controleur.validation.ValidateurObserver;
import com.pam.codenamehippie.http.Authentificateur;
import com.pam.codenamehippie.modele.UtilisateurModele;
import com.pam.codenamehippie.modele.UtilisateurModeleDepot;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity implements EditText.OnEditorActionListener,
                                                                ValidateurObserver {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private OkHttpClient httpClient;
    private ValidateurMotDePasse valideurMotDePasse;
    private boolean motDePassEstValide;
    private ValidateurCourriel valideurCourriel;
    private boolean courrielEstValide;
    private Button loginButton;
    private SharedPreferences sharedPreferences;
    private Authentificateur authentificateur;

    @Override
    public void enValidatant(Validateur validateur, boolean estValide) {
        if (validateur.equals(this.valideurCourriel)) {
            this.courrielEstValide = estValide;
        } else if (validateur.equals(this.valideurMotDePasse)) {
            this.motDePassEstValide = estValide;
        }
        this.loginButton.setEnabled(this.motDePassEstValide && this.courrielEstValide);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.httpClient = ((HippieApplication) this.getApplication()).getHttpClient();
        this.authentificateur = ((Authentificateur) this.httpClient.getAuthenticator());
        this.setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        if (toolbar != null) {
            this.setSupportActionBar(toolbar);
        }
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.valideurCourriel =
                ValidateurCourriel.newInstance(this,
                                               ((EditText) this.findViewById(R.id.etCourriel)),
                                               true
                                              );
        String rememberedEmail =
                this.sharedPreferences.getString(this.getString(R.string.pref_email_key), null);
        if ((rememberedEmail != null)) {
            this.valideurCourriel.setText(rememberedEmail);
        }
        this.valideurMotDePasse =
                ValidateurMotDePasse.newInstance(this,
                                                 ((EditText) this.findViewById(R.id.etPassword))
                                                );
        this.valideurMotDePasse.getEditText().setOnEditorActionListener(this);
        this.loginButton = ((Button) this.findViewById(R.id.bLogin));
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.valideurCourriel.onPause();
        this.valideurMotDePasse.onPause();
        this.valideurCourriel.unregisterObserver(this);
        this.valideurMotDePasse.unregisterObserver(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.valideurCourriel.onResume();
        this.valideurMotDePasse.onResume();
        this.valideurCourriel.registerObserver(this);
        this.valideurMotDePasse.registerObserver(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (this.authentificateur.estAuthentifie()) {
            this.navigueAMainActivity();
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean handled = false;
        switch (actionId) {
            case EditorInfo.IME_ACTION_DONE:
                this.onClickLogin(v);
                handled = true;
                break;
            default:
                break;
        }
        return handled;
    }

    /**
     * Methode pour ouvrir le formulaire d'inscription d'un  nouvel utilisateur
     */
    public void onClickInscription(View v) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        LoginActivity.this.startActivity(intent);
    }

    /**
     * Methode pour vérifier si les champs mot the passse et courriel du formulaire est
     * notifierLesVoyeurs sont
     * update la vue en conséquence.
     */
    public void onClickLogin(final View v) {
        RequestBody requestBody =
                new FormEncodingBuilder().add("courriel",
                                              this.valideurCourriel.getText().toString()
                                             )
                                         .add("mot_de_passe",
                                              this.valideurMotDePasse.getText().toString()
                                             )
                                         .build();
        Request request =
                new Request.Builder().url(HippieApplication.baseUrl)
                                     .post(requestBody)
                                     .build();
        this.httpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Snackbar.make(v, R.string.error_connection, Snackbar.LENGTH_SHORT).show();
                    }
                });
                // On oublie le mot de passe. Parce qu'on a échoué.
                Authentificateur authentificateur =
                        ((Authentificateur) LoginActivity.this.httpClient.getAuthenticator());
                authentificateur.setMotDePasse(null);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    LoginActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Snackbar.make(v, R.string.error_connection, Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                    });
                } else {
                    HippieApplication application =
                            ((HippieApplication) LoginActivity.this.getApplication());
                    UtilisateurModeleDepot depotUtilisateur =
                            application.getUtilisateurModeleDepot();
                    String json = response.body().string();
                    UtilisateurModele utilisateur = depotUtilisateur.fromJson(json);
                    String nom = utilisateur.getPrenom() + " " + utilisateur.getNom();
                    LoginActivity.this.sauvegarderFormulaire();
                    Snackbar.make(v,
                                  LoginActivity.this.getString(R.string.message_welcome, nom),
                                  Snackbar.LENGTH_SHORT
                                 )
                            .setCallback(new Snackbar.Callback() {
                                @Override
                                public void onDismissed(Snackbar snackbar, int event) {
                                    super.onDismissed(snackbar, event);
                                    LoginActivity.this.navigueAMainActivity();
                                }
                            })
                            .show();
                }
            }
        });
    }

    /**
     * Methode d'aide pour démarrer sur le main thread la main activity et appeler finish.
     */
    private void navigueAMainActivity() {
        LoginActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(intent);
                LoginActivity.this.finish();
            }
        });
    }

    private void sauvegarderFormulaire() {
        this.authentificateur.setMotDePasse(this.valideurCourriel.getText().toString());
        this.sharedPreferences.edit()
                              .putString(this.getString(R.string.pref_email_key),
                                         this.valideurMotDePasse.getText().toString()
                                        )
                              .commit();
    }

}
