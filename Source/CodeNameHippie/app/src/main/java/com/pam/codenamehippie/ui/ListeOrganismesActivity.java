package com.pam.codenamehippie.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.ObservateurDeDepot;
import com.pam.codenamehippie.modele.OrganismeModele;
import com.pam.codenamehippie.modele.OrganismeModeleDepot;
import com.pam.codenamehippie.ui.adapter.CarteListeOrganismeAdapter;
import com.pam.codenamehippie.ui.adapter.MesReservationsAdapter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Catherine on 2016-01-13.
 */
public class ListeOrganismesActivity extends HippieActivity
        implements ObservateurDeDepot<OrganismeModele> {

    // TODO: Mes réservations
    // Informations sur les listes et les listAdapter : http://www.vogella
    // .com/tutorials/AndroidListView/article.html

    private ListView listeMesReservations;
    private MesReservationsAdapter mesReservationsAdapter;
    private CarteListeOrganismeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.liste_organisme);
        this.adapter = new CarteListeOrganismeAdapter(this);
        ((ExpandableListView) findViewById(R.id.liste_organisme)).setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        OrganismeModeleDepot depot =
                ((HippieApplication) this.getApplication()).getOrganismeModeleDepot();
        depot.setFiltreDeListe(null);
        depot.supprimerTousLesObservateurs();

    }

    @Override
    protected void onResume() {
        super.onResume();
        OrganismeModeleDepot depot =
                ((HippieApplication) this.getApplication()).getOrganismeModeleDepot();
        depot.ajouterUnObservateur(this);
        depot.peuplerListeOrganisme();

    }

    @Override
    public void surDebutDeRequete() {
        this.afficherLaProgressBar();
    }

    @Override
    public void surChangementDeDonnees(ArrayList<OrganismeModele> modeles) {
        this.adapter.setItems(modeles);
    }

    @Override
    public void surFinDeRequete() {
        this.cacherLaProgressbar();
    }

    @Override
    public void surErreur(IOException e) {
        Log.e(this.getClass().getSimpleName(), "Error", e);
    }
}
