package com.pam.codenamehippie.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import com.pam.codenamehippie.R;
import com.pam.codenamehippie.controleur.validation.Validateur;
import com.pam.codenamehippie.controleur.validation.ValidateurCourriel;
import com.pam.codenamehippie.controleur.validation.ValidateurDeChampTexte;
import com.pam.codenamehippie.controleur.validation.ValidateurMotDePasse;
import com.pam.codenamehippie.controleur.validation.ValidateurObserver;
import com.pam.codenamehippie.http.Authentificateur;
import com.squareup.okhttp.OkHttpClient;

public class RegisterActivity extends AppCompatActivity implements ValidateurObserver {

    private OkHttpClient httpClient;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_register);
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
}
