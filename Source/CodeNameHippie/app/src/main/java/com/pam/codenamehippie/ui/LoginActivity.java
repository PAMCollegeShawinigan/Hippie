package com.pam.codenamehippie.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
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
import com.pam.codenamehippie.modele.OrganismeModele;
import com.pam.codenamehippie.modele.UtilisateurModele;
import com.pam.codenamehippie.modele.depot.UtilisateurModeleDepot;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends HippieActivity
        implements EditText.OnEditorActionListener, ValidateurObserver {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private ValidateurMotDePasse validateurMotDePasse;
    private boolean motDePassEstValide;
    private ValidateurCourriel validateurCourriel;
    private boolean courrielEstValide;
    private Button loginButton;
    private UtilisateurModele utilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);
        this.validateurCourriel =
                ValidateurCourriel.newInstance(this,
                                               ((EditText) this.findViewById(R.id.etCourriel)),
                                               true
                                              );
        String rememberedEmail =
                this.sharedPreferences.getString(this.getString(R.string.pref_email_key), null);
        if ((rememberedEmail != null)) {
            this.validateurCourriel.setText(rememberedEmail);
        }
        this.validateurMotDePasse =
                ValidateurMotDePasse.newInstance(this,
                                                 ((EditText) this.findViewById(R.id.etPassword))
                                                );
        this.validateurMotDePasse.getEditText().setOnEditorActionListener(this);
        this.loginButton = ((Button) this.findViewById(R.id.bLogin));
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.validateurCourriel.onPause();
        this.validateurMotDePasse.onPause();
        this.validateurCourriel.unregisterObserver(this);
        this.validateurMotDePasse.unregisterObserver(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.validateurCourriel.registerObserver(this);
        this.validateurMotDePasse.registerObserver(this);
        this.validateurCourriel.onResume();
        this.validateurMotDePasse.onResume();
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
                if (this.loginButton.isEnabled()) {
                    this.onClickLogin(v);
                }
                handled = true;
                break;
            default:
                break;
        }
        return handled;
    }

    @Override
    public void enValidant(Validateur validateur, boolean estValide) {
        if (validateur.equals(this.validateurCourriel)) {
            this.courrielEstValide = estValide;
        } else if (validateur.equals(this.validateurMotDePasse)) {
            this.motDePassEstValide = estValide;
        }
        this.loginButton.setEnabled(this.motDePassEstValide && this.courrielEstValide);
    }

    /**
     * Methode pour ouvrir le formulaire d'inscription d'un  nouvel utilisateur
     */
    public void onClickInscription(View v) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        LoginActivity.this.startActivity(intent);
    }

    /**
     * Methode déclechant la connection.
     *
     * @param v
     *         un objet view qui est en lien avec l'interaction de connection.
     */
    public void onClickLogin(final View v) {
        RequestBody requestBody =
                new FormBody.Builder().add("courriel", this.validateurCourriel.getText().toString())
                                      .add("mot_de_passe",
                                           this.validateurMotDePasse.getText().toString()
                                          )
                                      .build();
        Request request =
                new Request.Builder().url(HippieApplication.baseUrl).post(requestBody).build();
        this.httpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Snackbar.make(v, R.string.error_connection, Snackbar.LENGTH_SHORT).show();
                    }
                });
                // On "déconnecte": on a échoué.
                LoginActivity.this.authentificateur.deconnecte();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    switch (response.code()) {
                        case 403:
                            LoginActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Snackbar.make(v,
                                                  R.string.error_bad_credentials,
                                                  Snackbar.LENGTH_SHORT
                                                 )
                                            .show();
                                }
                            });
                            break;
                        default:
                            LoginActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Snackbar.make(v,
                                                  R.string.error_connection,
                                                  Snackbar.LENGTH_SHORT
                                                 )
                                            .show();
                                }
                            });
                            break;
                    }
                    // On "déconnecte": on a échoué.
                    LoginActivity.this.authentificateur.deconnecte();
                } else {
                    HippieApplication application =
                            ((HippieApplication) LoginActivity.this.getApplication());
                    UtilisateurModeleDepot depotUtilisateur =
                            application.getUtilisateurModeleDepot();
                    LoginActivity.this.utilisateur =
                            depotUtilisateur.fromJson(response.body().charStream());
                    String nom = LoginActivity.this.utilisateur.getPrenom() +
                                 " " +
                                 LoginActivity.this.utilisateur.getNom();
                    Log.d(TAG, LoginActivity.this.utilisateur.toString());
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

    @SuppressLint("CommitPrefEdits")
    private void sauvegarderFormulaire() {
        this.authentificateur.setMotDePasse(this.validateurMotDePasse.getText().toString());
        SharedPreferences.Editor editor =
                this.sharedPreferences.edit()
                                      .putString(this.getString(R.string.pref_email_key),
                                                 this.validateurCourriel.getText().toString()
                                                );
        if (this.utilisateur != null) {
            OrganismeModele organisme = this.utilisateur.getOrganisme();
            editor.putInt(this.getString(R.string.pref_user_id_key), this.utilisateur.getId());
            if (organisme != null) {
                editor.putInt(this.getString(R.string.pref_org_id_key), organisme.getId());
            }
        }
        editor.commit();
    }

}
