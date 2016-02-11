package com.pam.codenamehippie.ui;

import android.os.Bundle;
import android.widget.ExpandableListView;

import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.depot.AlimentaireModeleDepot;
import com.pam.codenamehippie.modele.depot.ObservateurDeDepot;
import com.pam.codenamehippie.modele.depot.TransactionModeleDepot;
import com.pam.codenamehippie.ui.adapter.ListeStatistiquesAdapter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Carl St-Louis le 2016-02-08.
 */
public class ListeStatistiquesActivity extends HippieActivity
    implements ObservateurDeDepot<AlimentaireModele>{

    private static final String TAG = ListeStatistiquesActivity.class.getSimpleName();
    private ExpandableListView listeStatistiques;
    private ListeStatistiquesAdapter statistiquesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.liste_statistiques);

        //TODO: Vérifier si AlimentaireModeleDepot ou TransactionModeleDepot
        TransactionModeleDepot transactionModeleDepot =
                ((HippieApplication) this.getApplication()).getTransactionModeleDepot();

        // On va chercher l'expendable listView
        listeStatistiques = (ExpandableListView)findViewById(R.id.list_statistiques_group);
        statistiquesAdapter = new ListeStatistiquesAdapter(this,transactionModeleDepot);
        listeStatistiques.setAdapter(statistiquesAdapter);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void surDebutDeRequete() {

    }

    @Override
    public void surChangementDeDonnees(ArrayList<AlimentaireModele> modeles) {

    }

    @Override
    public void surFinDeRequete() {

    }

    @Override
    public void surErreur(IOException e) {

    }
}
