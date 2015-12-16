package com.pam.codenamehippie.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.controleur.validation.Validateur;
import com.pam.codenamehippie.controleur.validation.ValidateurDeChampTexte;
import com.pam.codenamehippie.controleur.validation.ValidateurObserver;
import com.pam.codenamehippie.ui.adapter.HippieSpinnerAdapter;
import com.pam.codenamehippie.ui.view.trianglemenu.TriangleLayout;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.Arrays;

public class AjoutMarchandiseActivity extends AppCompatActivity implements ValidateurObserver, AdapterView.OnItemSelectedListener {

    private OkHttpClient httpClient;
    private ValidateurDeChampTexte validateurNom;
    private Boolean nomEstValide;
    private ValidateurDeChampTexte validateurDescription;
    private Boolean descriptionEstValide;
    private ValidateurDeChampTexte validateurQuantite;
    private Boolean quantiteEstValide;
    private ValidateurDeChampTexte validateurValeur;
    private Boolean valeurEstValide;
    private Spinner spinnerUniteMarchandise;
    private Spinner spinnerTypeMarchandise;
    private DatePicker datePeremption;
    private Button bAjoutMarchandise;
    private Boolean spinnerUniteMarchandiseEstValide;
    private Boolean spinnerTypeMarchandiseEstValide;
    private Boolean datePeremptionEstValide;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.ajout_marchandise);
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        if (toolbar != null) {
            this.setSupportActionBar(toolbar);
        }
        this.httpClient = ((HippieApplication) this.getApplication()).getHttpClient();
        EditText etNomMarchandise = (EditText) this.findViewById(R.id.etNomMarchandise);
        this.validateurNom =
                ValidateurDeChampTexte.newInstance(this,
                        etNomMarchandise,true,ValidateurDeChampTexte.NOM_ALIMENTAIRE_LONGUEUR_MAX);
        this.validateurNom.registerObserver(this);
        EditText etDescMarchandise = (EditText) this.findViewById(R.id.etDescMarchandise);
        this.validateurDescription =
                ValidateurDeChampTexte.newInstance(this,
                        etDescMarchandise,true,ValidateurDeChampTexte.DESCRIPTION_ALIMENTAIRE_LONGUEUR_MAX);
        this.validateurDescription.registerObserver(this);
        EditText etQteeMarchandise = (EditText) this.findViewById(R.id.etQteeMarchandise);
        this.validateurQuantite =
                ValidateurDeChampTexte.newInstance(this,
                        etQteeMarchandise,true,ValidateurDeChampTexte.QUANTITE_ALIMENTAIRE_LONGUEUR_MAX);
        this.validateurQuantite.registerObserver(this);
        spinnerUniteMarchandise = (Spinner) findViewById(R.id.spinnerUniteMarchandise);
        //TODO: Plugger les données du service WEB
//        String[] test = {"patate", "pomme", "tarte"};
//        this.spinnerUniteMarchandise.setAdapter(new HippieSpinnerAdapter(this,new ArrayList<String>(Arrays.asList(test))));


        EditText etValeurMarchandise = (EditText) findViewById(R.id.etValeurMarchandise);
        this.validateurValeur =
                ValidateurDeChampTexte.newInstance(this,
                        etValeurMarchandise,true,ValidateurDeChampTexte.VALEUR_ALIMENTAIRE_LONGUEUR_MAX);
        this.validateurValeur.registerObserver(this);
        spinnerTypeMarchandise = (Spinner) findViewById(R.id.spinnerTypeMarchandise);
        //TODO: Plugger les données du WEB

        datePeremption = (DatePicker) findViewById(R.id.datePicker);
        bAjoutMarchandise = (Button)findViewById(R.id.bAjoutMarchandise);

    }

    @Override
    protected void onPause() {
        super.onPause();
        this.validateurNom.onPause();
        this.validateurDescription.onPause();
        this.validateurQuantite.onPause();
        this.validateurValeur.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.validateurNom.onResume();
        this.validateurDescription.onResume();
        this.validateurQuantite.onResume();
        this.validateurValeur.onResume();
    }

    @Override
    public void enValidatant(Validateur validateur, boolean estValide) {
        if (validateur.equals(this.validateurNom)) {
            this.nomEstValide = estValide;
        } else if (validateur.equals(this.validateurDescription)){
            this.descriptionEstValide = estValide;
        } else if (validateur.equals(this.validateurQuantite)) {
            this.quantiteEstValide = estValide;
        } else if (validateur.equals(this.validateurValeur)) {
            this.valeurEstValide = estValide;
        } else if (spinnerUniteMarchandise.getSelectedItem() != 0){
            this.spinnerUniteMarchandiseEstValide = estValide;
        } else if (spinnerTypeMarchandise.getSelectedItem() != 0) {
            this.spinnerTypeMarchandiseEstValide = estValide;
        }
        //TODO: Valider datePeremption si egal date du jour mettre datePeremption à null sinon convertir au bon format et mettre datePeremptionEstValide = estValide
        this.bAjoutMarchandise.setEnabled(this.nomEstValide &&
                                          this.descriptionEstValide &&
                                          this.quantiteEstValide &&
                                          this.valeurEstValide &&
                                          this.spinnerUniteMarchandiseEstValide &&
                                          this.spinnerTypeMarchandiseEstValide);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
