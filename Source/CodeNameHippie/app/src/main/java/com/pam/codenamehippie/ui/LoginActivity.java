package com.pam.codenamehippie.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
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

public class LoginActivity extends AppCompatActivity implements EditText.OnEditorActionListener {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private OkHttpClient httpClient;
    private EditText courrielEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private SharedPreferences sharedPreferences;
    private Authentificateur authentificateur;
    private final TextWatcher formulaireTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            LoginActivity.this.validerFormulaire();
        }
    };

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
        this.courrielEditText = ((EditText) this.findViewById(R.id.etCourriel));
        String rememberedEmail =
                this.sharedPreferences.getString(this.getString(R.string.pref_email_key), null);
        if ((rememberedEmail != null) && (this.courrielEditText != null)) {
            this.courrielEditText.setText(rememberedEmail);
        }
        this.passwordEditText = ((EditText) this.findViewById(R.id.etPassword));

        this.loginButton = (Button) this.findViewById(R.id.bLogin);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (this.courrielEditText != null) {
            this.courrielEditText.removeTextChangedListener(this.formulaireTextWatcher);
        }
        if (this.passwordEditText != null) {
            this.passwordEditText.removeTextChangedListener(this.formulaireTextWatcher);
            this.passwordEditText.setOnEditorActionListener(null);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.courrielEditText != null) {
            this.courrielEditText.addTextChangedListener(this.formulaireTextWatcher);
        }
        if (this.passwordEditText != null) {
            this.passwordEditText.addTextChangedListener(this.formulaireTextWatcher);
            this.passwordEditText.setOnEditorActionListener(this);
        }
        this.validerFormulaire();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (this.authentificateur.estAuthentifie()) {
            this.navigueAMainActivity();
        }
    }

    /**
     * Methode pour vérifier si le champ courriel du formulaire est valide et update la vue en
     * conséquence.
     *
     * @return True si le champ est valide
     */
    private boolean courrielEstIlValide() {
        if ((this.courrielEditText != null)) {
            Editable text = this.courrielEditText.getText();
            String errorMessage = null;
            if (TextUtils.isEmpty(text)) {
                errorMessage = this.getString(R.string.error_field_required);
            } else if (!Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                errorMessage = this.getString(R.string.error_invalid_email);
            }
            this.courrielEditText.setError(errorMessage);
            if (errorMessage == null) {
                this.sharedPreferences.edit()
                                      .putString(this.getString(R.string.pref_email_key),
                                                 text.toString()
                                                )
                                      .commit();
            }
            return this.courrielEditText.getError() == null;
        }
        return false;
    }

    /**
     * Methode pour vérifier si le champ mot the passse du formulaire est valide et update la vue
     * en conséquence.
     *
     * @return True si le champ est valide
     */
    private boolean motDePasseEstIlValide() {
        if ((this.passwordEditText != null)) {
            Editable text = this.passwordEditText.getText();
            String errorMessage = null;
            if (TextUtils.isEmpty(text)) {
                errorMessage = this.getString(R.string.error_field_required);
            }
            // TODO: Checker les contraintes de mots de passe.
            if (errorMessage == null) {
                this.authentificateur.setMotDePasse(text.toString());
            }
            this.passwordEditText.setError(errorMessage);
            return this.passwordEditText.getError() == null;
        }
        return false;
    }

    private void validerFormulaire() {
        this.loginButton.setEnabled(LoginActivity.this.courrielEstIlValide() &&
                                    LoginActivity.this.motDePasseEstIlValide());
    }

    /**
     * Methode pour ouvrir le formulaire d'inscription d'un  nouvel utilisateur
     */
    public void onClickInscription(View v) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        LoginActivity.this.startActivity(intent);
        //LoginActivity.this.finish();
        // TODO: ajouter le parent dans le manifest pour le retour au parent
    }

    /**
     * Methode pour vérifier si les champs mot the passse et courriel du formulaire est valide sont
     * update la vue en conséquence.
     */

    public void onClickLogin(final View v) {
        RequestBody requestBody =
                new FormEncodingBuilder().add("courriel",
                                              this.courrielEditText.getText().toString()
                                             )
                                         .add("mot_de_passe",
                                              this.passwordEditText.getText().toString()
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
}
