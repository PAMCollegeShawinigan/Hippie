package com.pam.codenamehippie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pam.codenamehippie.R;
import com.pam.codenamehippie.controleur.validation.Validateur;
import com.pam.codenamehippie.controleur.validation.ValidateurCourriel;
import com.pam.codenamehippie.controleur.validation.ValidateurMotDePasse;
import com.pam.codenamehippie.controleur.validation.ValidateurObserver;
import com.pam.codenamehippie.http.Authentificateur.Callback;
import com.pam.codenamehippie.http.exception.HttpReponseException;
import com.pam.codenamehippie.modele.UtilisateurModele;

import java.io.IOException;

public class LoginActivity extends HippieActivity
        implements EditText.OnEditorActionListener, ValidateurObserver {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private ValidateurMotDePasse validateurMotDePasse;
    private boolean motDePassEstValide;
    private ValidateurCourriel validateurCourriel;
    private boolean courrielEstValide;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);
        this.validateurCourriel = ValidateurCourriel.newInstance(this,
                                                                 ((EditText) this.findViewById(
                                                                         R.id.etCourriel)), true);
        UtilisateurModele uc = this.authentificateur.getUtilisateur();
        String rememberedEmail = (uc != null) ? uc.getCourriel() : "";
        if ((rememberedEmail != null)) {
            this.validateurCourriel.setText(rememberedEmail);
        }
        this.validateurMotDePasse = ValidateurMotDePasse.newInstance(this,
                                                                     ((EditText) this.findViewById(
                                                                             R.id.etPassword)));
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
        this.authentificateur.connecter(this.validateurCourriel.getTextString(),
                                        this.validateurMotDePasse.getTextString(), new Callback() {
                    @Override
                    public void surErreur(IOException e) {
                        Snackbar snackbar;
                        if (e instanceof HttpReponseException) {
                            switch (((HttpReponseException) e).getCode()) {
                                case 403:
                                    snackbar = Snackbar.make(v,
                                                             R.string.error_bad_credentials,
                                                             Snackbar.LENGTH_SHORT);
                                    break;
                                default:
                                    snackbar = Snackbar.make(v, R.string.error_connection,
                                                             Snackbar.LENGTH_SHORT);
                                    break;
                            }
                        } else {
                            snackbar = Snackbar.make(v, R.string.error_connection,
                                                     Snackbar.LENGTH_SHORT);
                        }
                        snackbar.show();
                    }

                    @Override
                    public void surSucces(UtilisateurModele utilisateur) {
                        Snackbar.make(v, LoginActivity.this.getString(R.string.message_welcome,
                                                                      utilisateur.getNomComplet()),
                                      Snackbar.LENGTH_SHORT).setCallback(new Snackbar.Callback() {
                            @Override
                            public void onDismissed(Snackbar snackbar, int event) {
                                super.onDismissed(snackbar, event);
                                LoginActivity.this.navigueAMainActivity();
                            }
                        }).show();

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
                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                LoginActivity.this.startActivity(intent);
                LoginActivity.this.finish();
            }
        });
    }

}
