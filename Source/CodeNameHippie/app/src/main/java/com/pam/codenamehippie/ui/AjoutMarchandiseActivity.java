package com.pam.codenamehippie.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.controleur.validation.Validateur;
import com.pam.codenamehippie.controleur.validation.ValidateurDeChampTexte;
import com.pam.codenamehippie.controleur.validation.ValidateurDeSpinner;
import com.pam.codenamehippie.controleur.validation.ValidateurObserver;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.AlimentaireModeleDepot;
import com.pam.codenamehippie.ui.adapter.HippieSpinnerAdapter;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

public class AjoutMarchandiseActivity extends HippieActivity
        implements ValidateurObserver {

    private ValidateurDeChampTexte validateurNom;
    private boolean nomEstValide;
    private ValidateurDeChampTexte validateurDescription;
    private boolean descriptionEstValide;
    private ValidateurDeChampTexte validateurQuantite;
    private boolean quantiteEstValide;
    private ValidateurDeChampTexte validateurValeur;
    private boolean valeurEstValide;
    private ValidateurDeSpinner validateurSpinnerUniteMarchandise;
    private ValidateurDeSpinner validateurSpinnerTypeMarchandise;
    private DatePicker datePeremption;
    private Button bAjoutMarchandise;
    private boolean spinnerUniteMarchandiseEstValide;
    private boolean spinnerTypeMarchandiseEstValide;
    private boolean datePeremptionEstValide;

    // Id de l'organisme dont l'utilisateur est membre.
    private Integer organismeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.ajout_marchandise);
        this.httpClient = ((HippieApplication) this.getApplication()).getHttpClient();
        EditText etNomMarchandise = (EditText) this.findViewById(R.id.etNomMarchandise);
        this.validateurNom =
                ValidateurDeChampTexte.newInstance(this,
                                                   etNomMarchandise,
                                                   true,
                                                   ValidateurDeChampTexte
                                                           .NOM_ALIMENTAIRE_LONGUEUR_MAX
                                                  );
        this.validateurNom.registerObserver(this);
        EditText etDescMarchandise = (EditText) this.findViewById(R.id.etDescMarchandise);
        this.validateurDescription =
                ValidateurDeChampTexte.newInstance(this,
                                                   etDescMarchandise,
                                                   true,
                                                   ValidateurDeChampTexte
                                                           .DESCRIPTION_ALIMENTAIRE_LONGUEUR_MAX
                                                  );
        this.validateurDescription.registerObserver(this);
        EditText etQteeMarchandise = (EditText) this.findViewById(R.id.etQteeMarchandise);
        this.validateurQuantite =
                ValidateurDeChampTexte.newInstance(this,
                                                   etQteeMarchandise,
                                                   true,
                                                   ValidateurDeChampTexte
                                                           .QUANTITE_ALIMENTAIRE_LONGUEUR_MAX
                                                  );
        this.validateurQuantite.registerObserver(this);

        Spinner spinnerUniteMarchandise = (Spinner) this.findViewById(R.id.spinnerUniteMarchandise);
        this.validateurSpinnerUniteMarchandise =
                ValidateurDeSpinner.newInstance(spinnerUniteMarchandise);
        this.validateurSpinnerUniteMarchandise.registerObserver(this);
        AlimentaireModeleDepot alimentaireModeleDepot =
                ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();
        HippieSpinnerAdapter uniteAdapter =
                new HippieSpinnerAdapter(this, alimentaireModeleDepot.getListeUnitee());
        spinnerUniteMarchandise.setAdapter(uniteAdapter);

        EditText etValeurMarchandise = (EditText) this.findViewById(R.id.etValeurMarchandise);
        this.validateurValeur =
                ValidateurDeChampTexte.newInstance(this,
                                                   etValeurMarchandise,
                                                   true,
                                                   ValidateurDeChampTexte
                                                           .VALEUR_ALIMENTAIRE_LONGUEUR_MAX
                                                  );
        this.validateurValeur.registerObserver(this);

        Spinner spinnerTypeMarchandise = (Spinner) this.findViewById(R.id.spinnerTypeMarchandise);
        this.validateurSpinnerTypeMarchandise =
                ValidateurDeSpinner.newInstance(spinnerTypeMarchandise);
        this.validateurSpinnerTypeMarchandise.registerObserver(this);
        HippieSpinnerAdapter typeAdapter =
                new HippieSpinnerAdapter(this, alimentaireModeleDepot.getListeTypeAlimentaire());
        spinnerTypeMarchandise.setAdapter(typeAdapter);

        this.datePeremption = (DatePicker) this.findViewById(R.id.datePicker);
        // Set la date minimale du date picker au moment présent.
        this.bAjoutMarchandise = (Button) this.findViewById(R.id.bAjoutMarchandise);
        // Retrouve l'organisme id de shared pref. -1 signifie qu'il n'y a pas d'organisme.
        this.organismeId = this.sharedPreferences.getInt(this.getString(R.string.pref_org_id_key),
                                                         -1
                                                        );

    }

    @Override
    protected void onPause() {
        super.onPause();
        this.validateurNom.onPause();
        this.validateurDescription.onPause();
        this.validateurQuantite.onPause();
        this.validateurValeur.onPause();
        this.validateurSpinnerUniteMarchandise.onPause();
        this.validateurSpinnerTypeMarchandise.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.validateurNom.onResume();
        this.validateurDescription.onResume();
        this.validateurQuantite.onResume();
        this.validateurValeur.onResume();
        this.validateurSpinnerUniteMarchandise.onResume();
        this.validateurSpinnerTypeMarchandise.onResume();
    }

    @Override
    public void enValidatant(Validateur validateur, boolean estValide) {
        if (validateur.equals(this.validateurNom)) {
            this.nomEstValide = estValide;
        } else if (validateur.equals(this.validateurDescription)) {
            this.descriptionEstValide = estValide;
        } else if (validateur.equals(this.validateurQuantite)) {
            this.quantiteEstValide = estValide;
        } else if (validateur.equals(this.validateurValeur)) {
            this.valeurEstValide = estValide;
        } else if (validateur.equals(this.validateurSpinnerUniteMarchandise)) {
            this.spinnerUniteMarchandiseEstValide = estValide;
        } else if (validateur.equals(this.validateurSpinnerTypeMarchandise)) {
            this.spinnerTypeMarchandiseEstValide = estValide;
        }
        // Check si on fait parti d'un organisme
        boolean hasOrganismeid = (this.organismeId != -1);
        //TODO: Valider datePeremption si egal date du jour mettre datePeremption à null sinon
        // convertir au bon format et mettre datePeremptionEstValide = estValide
        this.bAjoutMarchandise.setEnabled(this.nomEstValide &&
                                          this.descriptionEstValide &&
                                          this.quantiteEstValide &&
                                          this.valeurEstValide &&
                                          this.spinnerUniteMarchandiseEstValide &&
                                          this.spinnerTypeMarchandiseEstValide &&
                                          hasOrganismeid);

    }

    public void soumettreMarchandise(final View v) {
        //TODO: soumettre la marchandise au serveur selon les paramètres TransactionModele
        final String receveurId = "";
        AlimentaireModele modele = new AlimentaireModele();
        AlimentaireModeleDepot depot =
                ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();
        depot.ajouterModele(modele, true);
        RequestBody body =
                new FormEncodingBuilder().add("receveur_id", receveurId)
                                         .add("donneur_id", this.organismeId.toString())
                                         .build();
    }
}
