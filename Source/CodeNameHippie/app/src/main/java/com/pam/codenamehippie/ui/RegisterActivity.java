package com.pam.codenamehippie.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
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
import com.pam.codenamehippie.controleur.validation.ValidateurDeChampTexte;
import com.pam.codenamehippie.controleur.validation.ValidateurMotDePasse;
import com.pam.codenamehippie.controleur.validation.ValidateurObserver;
import com.pam.codenamehippie.http.Authentificateur;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class RegisterActivity extends HippieActivity
        implements ValidateurObserver, EditText.OnEditorActionListener {

    private ValidateurDeChampTexte validateurNom;
    private boolean nomEstValide;
    private ValidateurDeChampTexte validateurPrenom;
    private boolean prenomEstValide;
    private ValidateurMotDePasse validateurMotDePasse;
    private boolean motPasseEstValide;
    private ValidateurMotDePasse validateurConfirmMotDePasse;
    private boolean confirmMotdePasseEstValide;
    private ValidateurCourriel validateurCourriel;
    private boolean courrielEstValide;
    private Button loginButton;
    private Authentificateur authentificateur;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_register);
        this.httpClient = ((HippieApplication) this.getApplication()).getHttpClient();
        this.authentificateur = ((Authentificateur) this.httpClient.getAuthenticator());
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        if (toolbar != null) {
            this.setSupportActionBar(toolbar);
        }
        EditText etInscriptionNom = ((EditText) this.findViewById(R.id.etInscriptionNom));
        this.validateurNom =
                ValidateurDeChampTexte.newInstance(this,
                                                   etInscriptionNom,
                                                   true,
                                                   ValidateurDeChampTexte.NOM_LONGUEUR_MAX
                                                  );
        this.validateurNom.registerObserver(this);
        EditText etInscriptionPrenom = ((EditText) this.findViewById(R.id.etInscriptionPrenom));
        this.validateurPrenom =
                ValidateurDeChampTexte.newInstance(this,
                                                   etInscriptionPrenom,
                                                   true,
                                                   ValidateurDeChampTexte.PRENOM_LONGUEUR_MAX
                                                  );
        this.validateurPrenom.registerObserver(this);
        EditText etCourriel = ((EditText) this.findViewById(R.id.etCourriel));
        this.validateurCourriel = ValidateurCourriel.newInstance(this, etCourriel, true);
        this.validateurCourriel.registerObserver(this);
        EditText etPassword = ((EditText) this.findViewById(R.id.etPassword));
        this.validateurMotDePasse = ValidateurMotDePasse.newInstance(this, etPassword);
        this.validateurMotDePasse.registerObserver(this);
        EditText etConfirmPassword = ((EditText) this.findViewById(R.id.etConfirmPassword));
        etConfirmPassword.setOnEditorActionListener(this);
        this.validateurConfirmMotDePasse =
                ValidateurMotDePasse.newInstance(this, etConfirmPassword);
        this.validateurConfirmMotDePasse.registerObserver(this);
        this.loginButton = (Button) this.findViewById(R.id.bLogin);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.validateurPrenom.onPause();
        this.validateurNom.onPause();
        this.validateurCourriel.onPause();
        this.validateurMotDePasse.onPause();
        this.validateurConfirmMotDePasse.onPause();
    }

    protected void onResume() {
        super.onResume();
        this.validateurPrenom.onResume();
        this.validateurNom.onResume();
        this.validateurCourriel.onResume();
        this.validateurMotDePasse.onResume();
        this.validateurConfirmMotDePasse.onResume();
    }

    @Override
    public void enValidatant(Validateur validateur, boolean estValide) {
        if (validateur.equals(this.validateurPrenom)) {
            this.prenomEstValide = estValide;
        } else if (validateur.equals(this.validateurNom)) {
            this.nomEstValide = estValide;
        } else if (validateur.equals(this.validateurCourriel)) {
            this.courrielEstValide = estValide;
        } else if (validateur.equals(this.validateurMotDePasse)) {
            this.motPasseEstValide = estValide;
            if ((this.motPasseEstValide && this.confirmMotdePasseEstValide)) {
                int cmp = this.validateurMotDePasse.getTextString()
                                                   .compareTo(this.validateurConfirmMotDePasse
                                                                      .getTextString());
                if (cmp == 0) {
                    String error = this.getString(R.string.error_confirm_password);
                    this.validateurConfirmMotDePasse.getEditText()
                                                    .setError(null);
                }
            }
        } else if (validateur.equals(this.validateurConfirmMotDePasse)) {
            this.confirmMotdePasseEstValide = estValide;
            if ((this.motPasseEstValide && this.confirmMotdePasseEstValide)) {
                int cmp = this.validateurMotDePasse.getTextString()
                                                   .compareTo(this.validateurConfirmMotDePasse
                                                                      .getTextString());
                if (cmp != 0) {
                    String error = this.getString(R.string.error_confirm_password);
                    this.validateurConfirmMotDePasse.getEditText()
                                                    .setError(error);
                }
            }
        }
        this.loginButton.setEnabled(this.motPasseEstValide &&
                                    this.confirmMotdePasseEstValide &&
                                    this.prenomEstValide &&
                                    this.nomEstValide &&
                                    this.courrielEstValide);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean handled = false;
        switch (actionId) {
            case EditorInfo.IME_ACTION_DONE:
                if (this.loginButton.isEnabled()) {
                    this.soummettreLaConnexion(v);
                }
                handled = true;
                break;
            default:
                break;
        }
        return handled;
    }

    /**
     * Methode déclechant la connection.
     *
     * @param v
     *         un objet view qui est en lien avec l'interaction de connection.
     */
    public void soummettreLaConnexion(final View v) {
        // TODO: RAJOUTER LES ORGANISME
        //FIXME: Utiliser depot
        RequestBody body =
                new FormEncodingBuilder().add("nom", this.validateurNom.getTextString())
                                         .add("prenom", this.validateurPrenom.getTextString())
                                         .add("mot_de_passe",
                                              this.validateurMotDePasse.getTextString()
                                             )
                                         .add("courriel", this.validateurCourriel.getTextString())
                                         .build();
        HttpUrl url = HippieApplication.baseUrl.newBuilder().addPathSegment("utilisateur").build();
        Request request = new Request.Builder().url(url).post(body).build();
        this.httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                RegisterActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Snackbar.make(v, R.string.error_connection, Snackbar.LENGTH_SHORT).show();
                    }
                });
                // On oublie le mot de passe. Parce qu'on a échoué.
                Authentificateur authentificateur =
                        ((Authentificateur) RegisterActivity.this.httpClient.getAuthenticator());
                authentificateur.setMotDePasse(null);

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {

                } else {
                    //FIXME: Gérer le retour du serveur
                    RegisterActivity.this.sauvegarderFormulaire();
                    RegisterActivity.this.navigueAMainActivity();
                }
            }
        });

    }

    private void sauvegarderFormulaire() {
        this.authentificateur.setMotDePasse(this.validateurMotDePasse.getText().toString());
        this.sharedPreferences.edit()
                              .putString(this.getString(R.string.pref_email_key),
                                         this.validateurCourriel.getText().toString()
                                        )
                              .commit();
    }

    /**
     * Methode d'aide pour démarrer sur le main thread la main activity et appeler finish.
     */
    private void navigueAMainActivity() {
        RegisterActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                RegisterActivity.this.startActivity(intent);
                RegisterActivity.this.finish();
            }
        });
    }
}
