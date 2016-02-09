package com.pam.codenamehippie.ui;

import android.os.Bundle;

import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.depot.ObservateurDeDepot;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Carl St-Louis le 2016-02-08.
 */
public class ListeStatistiquesActivity extends HippieActivity
    implements ObservateurDeDepot<AlimentaireModele>{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.liste_statistiques);
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
