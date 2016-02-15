package com.pam.codenamehippie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.controleur.validation.Validateur;
import com.pam.codenamehippie.controleur.validation.ValidateurCourriel;
import com.pam.codenamehippie.controleur.validation.ValidateurDeChampTexte;
import com.pam.codenamehippie.controleur.validation.ValidateurDeSpinner;
import com.pam.codenamehippie.controleur.validation.ValidateurMotDePasse;
import com.pam.codenamehippie.controleur.validation.ValidateurObserver;
import com.pam.codenamehippie.modele.UtilisateurModele;
import com.pam.codenamehippie.modele.depot.ObservateurDeDepot;
import com.pam.codenamehippie.modele.depot.UtilisateurModeleDepot;
import com.pam.codenamehippie.ui.adapter.HippieSpinnerAdapter;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends HippieActivity
        implements ValidateurObserver, EditText.OnEditorActionListener, ObservateurDeDepot<UtilisateurModele> {
    // Variable static final pour le spinner
    private static final String SELECTED_SPINNER_TYPE_RUE_POSITION = "position_type_rue";

    // Section des informations d'utilisateur
    private ValidateurDeChampTexte validateurNom;
    private boolean nomEstValide;
    private ValidateurDeChampTexte validateurPrenom;
    private boolean prenomEstValide;
    private ValidateurDeChampTexte validateurUsername;
    private boolean usernameEstValide;
    private ValidateurMotDePasse validateurMotDePasse;
    private boolean motPasseEstValide;
    private ValidateurMotDePasse validateurConfirmMotDePasse;
    private boolean confirmMotdePasseEstValide;

    // Section des informations sur les moyens de contact
    private ValidateurCourriel validateurCourriel;
    private boolean courrielEstValide;
    private ValidateurDeChampTexte validateurTelephone;
    private boolean telephoneEstValide;

    // TODO : Vérifier comment fonctionne les radios button/radio group

    // Section des informations sur l'adresse de l'organisme
    private ValidateurDeChampTexte validateurNoCivique;
    private boolean noCiviqueEstValide;
    private ValidateurDeSpinner validateurSpinnerTypeRue;
    private boolean spinnerTypeRueEstValide;
    private ValidateurDeChampTexte validateurNomRue;
    private boolean nomRueEstValide;
    private ValidateurDeChampTexte validateurVille;
    private boolean villeEstValide;
    private ValidateurDeChampTexte validateurProvince;
    private boolean provinceEstValide;
    private ValidateurDeChampTexte validateurCodePostal;
    private boolean codePostalEstValide;
    private ValidateurDeChampTexte validateurPays;
    private boolean paysEstValide;

    // Section information sur l'entreprise (no entreprise et/ou osbl)
    private ValidateurDeChampTexte validateurNoEntreprise;
    private boolean noEntrepriseEstValide;
    private ValidateurDeChampTexte validateurNoOsbl;
    private boolean noOsblEstValide;

    private Button loginButton;
    private UtilisateurModele modele;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_register);
        this.httpClient = ((HippieApplication) this.getApplication()).getHttpClient();

        /**
         * SECTION INFORMATIONS D'UTILISATEUR
         */
        // Validateur pour le nom
        EditText etInscriptionNom = ((EditText) this.findViewById(R.id.etInscriptionNom));
        this.validateurNom =
                ValidateurDeChampTexte.newInstance(this,
                                                   etInscriptionNom,
                                                   true,
                                                   ValidateurDeChampTexte.NOM_LONGUEUR_MAX
                                                  );
        this.validateurNom.registerObserver(this);

        // Validateur pour le prénom
        EditText etInscriptionPrenom = ((EditText) this.findViewById(R.id.etInscriptionPrenom));
        this.validateurPrenom =
                ValidateurDeChampTexte.newInstance(this,
                                                   etInscriptionPrenom,
                                                   true,
                                                   ValidateurDeChampTexte.PRENOM_LONGUEUR_MAX
                                                  );
        this.validateurPrenom.registerObserver(this);

        // Validateur pour le nom d'utilisateur
        EditText etUsername = (EditText) this.findViewById(R.id.etUsername);
        this.validateurUsername =
                ValidateurDeChampTexte.newInstance(this,
                                                   etUsername,
                                                   true,
                                                   ValidateurDeChampTexte.NOM_LONGUEUR_MAX);

        // Validateur pour le mot de passe et confirmer le mot de passe
        EditText etPassword = ((EditText) this.findViewById(R.id.etPassword));
        this.validateurMotDePasse = ValidateurMotDePasse.newInstance(this, etPassword);
        this.validateurMotDePasse.registerObserver(this);
        EditText etConfirmPassword = ((EditText) this.findViewById(R.id.etConfirmPassword));
        etConfirmPassword.setOnEditorActionListener(this);
        this.validateurConfirmMotDePasse =
                ValidateurMotDePasse.newInstance(this, etConfirmPassword);
        this.validateurConfirmMotDePasse.registerObserver(this);

        // SECTION INFORMATION SUR LES MOYENS DE CONTACT
        // Validateur pour le courriel
        EditText etCourriel = ((EditText) this.findViewById(R.id.etCourriel));
        this.validateurCourriel = ValidateurCourriel.newInstance(this, etCourriel, true);
        this.validateurCourriel.registerObserver(this);

        // Validateur pour le téléphone
        EditText etTelephone = (EditText) this.findViewById(R.id.etTelephone);
        this.validateurTelephone =
                ValidateurDeChampTexte.newInstance(this,
                                                   etTelephone,
                                                   true,
                                                   ValidateurDeChampTexte.NO_TELEPHONE_LONGUEUR_MAX);

        // Validateur pour le moyen de contact
        // TODO: Ajouter le validateur pour le radiogroup des moyens de contact
        // Radio group ID : rgMoyenContact
        // Radio button ID : rbCourriel, rbTelephone et rbBoth


        /**
         * SECTION ADRESSE
         */

        // Validateur pour le numéro civique
        EditText etNoCivique = (EditText) this.findViewById(R.id.etNoCivique);
        this.validateurNoCivique =
                ValidateurDeChampTexte.newInstance(this,
                        etNoCivique,
                        true,
                        ValidateurDeChampTexte.NO_TELEPHONE_LONGUEUR_MAX);

        // Validateur pour le spinner du type de rue
        Spinner spinnerTypeRue = (Spinner) this.findViewById(R.id.spinnerTypeRue);
        this.validateurSpinnerTypeRue = ValidateurDeSpinner.newInstance(spinnerTypeRue);
        this.validateurSpinnerTypeRue.registerObserver(this);

        // Binder les type de rues au spinnerTypeRue
        // TODO : Vérifier si tout est correct
        UtilisateurModeleDepot utilisateurModeleDepot =
                ((HippieApplication) this.getApplication()).getUtilisateurModeleDepot();
        HippieSpinnerAdapter typeRueAdapter = new HippieSpinnerAdapter(this);
        spinnerTypeRue.setAdapter(typeRueAdapter);

        // Validateur pour le nom de rue
        EditText etNomRue = (EditText) this.findViewById(R.id.etNomRue);
        this.validateurNomRue =
                ValidateurDeChampTexte.newInstance(this,
                        etNomRue,
                        true,
                        ValidateurDeChampTexte.NOM_LONGUEUR_MAX);

        // Validateur pour le nom de la ville
        EditText etVille = (EditText) this.findViewById(R.id.etVille);
        this.validateurVille =
                ValidateurDeChampTexte.newInstance(this,
                        etVille,
                        true,
                        ValidateurDeChampTexte.NOM_LONGUEUR_MAX);

        // Validateur pour la province
        EditText etProvince = (EditText) this.findViewById(R.id.etProvince);
        this.validateurProvince =
                ValidateurDeChampTexte.newInstance(this,
                        etProvince,
                        true,
                        ValidateurDeChampTexte.NOM_LONGUEUR_MAX);

        // Validateur pour le code postal
        EditText etCodePostal = (EditText) this.findViewById(R.id.etCodePostal);
        this.validateurCodePostal =
                ValidateurDeChampTexte.newInstance(this,
                        etCodePostal,
                        true,
                        ValidateurDeChampTexte.CODE_POSTAL_LONGUEUR_MAX);

        // Validateur pour le pays
        EditText etPays = (EditText) this.findViewById(R.id.etPays);
        this.validateurPays =
                ValidateurDeChampTexte.newInstance(this,
                        etPays,
                        true,
                        ValidateurDeChampTexte.NOM_LONGUEUR_MAX);

        /**
         * SECTION ENTREPRISE / ORGANISME
         */
        // TODO : Radio button/group, regarder ça.
        // RadioGroup ID : rgEtesVous
        // RadioButton ID : rbEntreprise et rbOrganisme
        RadioButton rbEntreprise = (RadioButton) this.findViewById(R.id.rbEntreprise);
        RadioButton rbOrganisme = (RadioButton) this.findViewById(R.id.rbOrganisme);

        // Validateur pour le numéro d'entreprise
        EditText etNoEntreprise = (EditText) this.findViewById(R.id.etNoEntreprise);
        this.validateurNoEntreprise =
                ValidateurDeChampTexte.newInstance(this,
                        etNoEntreprise,
                        true,
                        ValidateurDeChampTexte.NEQ_LONGUEUR_MAX);

        // Validateur pour le numéro d'OSBL
        EditText etNoOsbl = (EditText) this.findViewById(R.id.etNoOsbl);
        this.validateurNoOsbl =
                ValidateurDeChampTexte.newInstance(this,
                        etNoOsbl,
                        true,
                        ValidateurDeChampTexte.OSBL_LONGUEUR_MAX);

        // Bouton pour s'inscrire
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
        this.validateurUsername.onPause();
        this.validateurNomRue.onPause();
        this.validateurVille.onPause();
        this.validateurProvince.onPause();
        this.validateurCodePostal.onPause();
        this.validateurNoCivique.onPause();
        this.validateurNoEntreprise.onPause();
        this.validateurNoOsbl.onPause();
        this.validateurTelephone.onPause();
        this.validateurSpinnerTypeRue.onPause();
        this.validateurPays.onPause();
        UtilisateurModeleDepot depot =
                ((HippieApplication) this.getApplication()).getUtilisateurModeleDepot();
        depot.setFiltreDeListe(null);
        depot.supprimerTousLesObservateurs();
    }

    protected void onResume() {
        super.onResume();
        this.validateurPrenom.onResume();
        this.validateurNom.onResume();
        this.validateurCourriel.onResume();
        this.validateurMotDePasse.onResume();
        this.validateurConfirmMotDePasse.onResume();
        this.validateurUsername.onResume();
        this.validateurNomRue.onResume();
        this.validateurVille.onResume();
        this.validateurProvince.onResume();
        this.validateurCodePostal.onResume();
        this.validateurNoCivique.onResume();
        this.validateurNoEntreprise.onResume();
        this.validateurNoOsbl.onResume();
        this.validateurTelephone.onResume();
        this.validateurSpinnerTypeRue.onResume();
        this.validateurPays.onResume();
        UtilisateurModeleDepot depot =
                ((HippieApplication) this.getApplication()).getUtilisateurModeleDepot();
        // FIXME : Peupler les listes de spinners pour le type de rue?
    }

    @Override
    public void enValidant(Validateur validateur, boolean estValide) {
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
        } else if (validateur.equals(this.validateurCodePostal)) {
            this.codePostalEstValide = estValide;
        } else if (validateur.equals(this.validateurNoCivique)) {
            this.noCiviqueEstValide = estValide;
        } else if (validateur.equals(this.validateurNoEntreprise)) {
            this.noEntrepriseEstValide = estValide;
        } else if (validateur.equals(this.validateurNomRue)) {
            this.nomRueEstValide = estValide;
        } else if (validateur.equals(this.validateurNoOsbl)) {
            this.noOsblEstValide = estValide;
        } else if (validateur.equals(this.validateurPays)) {
            this.paysEstValide = estValide;
        } else if (validateur.equals(this.validateurSpinnerTypeRue)) {
            this.spinnerTypeRueEstValide = estValide;
        } else if (validateur.equals(this.validateurVille)) {
            this.villeEstValide = estValide;
        } else if (validateur.equals(this.validateurTelephone)) {
            this.telephoneEstValide = estValide;
        } else if (validateur.equals(this.validateurUsername)) {
            this.usernameEstValide = estValide;
        } else if (validateur.equals(this.validateurProvince)) {
            this.provinceEstValide = estValide;
        }

        this.loginButton.setEnabled(this.motPasseEstValide &&
                this.confirmMotdePasseEstValide &&
                this.prenomEstValide &&
                this.nomEstValide &&
                this.courrielEstValide &&
                this.codePostalEstValide &&
                this.noCiviqueEstValide &&
                this.noEntrepriseEstValide &&
                this.nomRueEstValide &&
                this.noOsblEstValide &&
                this.paysEstValide &&
                this.spinnerTypeRueEstValide &&
                this.villeEstValide &&
                this.telephoneEstValide &&
                this.usernameEstValide &&
                this.provinceEstValide
        );
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
        // TODO: RAJOUTER LES ORGANISMES
        //FIXME: Utiliser depot
        RequestBody body =
                new FormBody.Builder().add("nom", this.validateurNom.getTextString())
                                      .add("prenom", this.validateurPrenom.getTextString())
                                      .add("mot_de_passe",
                                           this.validateurMotDePasse.getTextString()
                                          )
                                      .add("courriel", this.validateurCourriel.getTextString())
                                      .build();
        HttpUrl url = HippieApplication.BASE_URL.newBuilder().addPathSegment("utilisateur").build();
        Request request = new Request.Builder().url(url).post(body).build();
        this.httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                RegisterActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Snackbar.make(v, R.string.error_connection, Snackbar.LENGTH_SHORT).show();
                    }
                });
                // On "déconnecte": on a échoué.
                RegisterActivity.this.authentificateur.deconnecter();

            }

            @Override
            public void onResponse(Call call, Response response) {
                if (!response.isSuccessful()) {
                    switch (response.code()) {
                        case 409:
                            RegisterActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Snackbar.make(v,
                                                  R.string.error_invalid_email,
                                                  Snackbar.LENGTH_SHORT
                                                 )
                                            .show();
                                    // L'adresse est invalide on la supprime
                                    RegisterActivity.this.validateurCourriel.setText(null);
                                }
                            });
                            break;
                        default:
                            RegisterActivity.this.runOnUiThread(new Runnable() {
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
                    RegisterActivity.this.authentificateur.deconnecter();
                } else {
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
                Intent intent = new Intent(RegisterActivity.this, InfoActivity.class);
                RegisterActivity.this.startActivity(intent);
                RegisterActivity.this.finish();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_SPINNER_TYPE_RUE_POSITION,
                this.validateurSpinnerTypeRue.getSelectedItemPosition()
        );
    }

    @Override
    public void surDebutDeRequete() {

    }

    @Override
    public void surChangementDeDonnees(ArrayList<UtilisateurModele> modeles) {
        if ((modeles != null) && (modeles.size() != 0)) {
            this.modele = modeles.get(0);
        }
    }

    @Override
    public void surFinDeRequete() {

    }

    @Override
    public void surErreur(IOException e) {

    }
}
