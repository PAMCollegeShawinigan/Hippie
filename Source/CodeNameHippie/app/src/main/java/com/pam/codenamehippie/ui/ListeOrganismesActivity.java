package com.pam.codenamehippie.ui;

import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.pam.codenamehippie.R;
import com.pam.codenamehippie.ui.adapter.CarteListeOrganismeAdapter;
import com.pam.codenamehippie.ui.adapter.MesReservationsAdapter;
import com.pam.codenamehippie.ui.view.trianglemenu.TestDonneeCentre;

import java.util.ArrayList;

/**
 * Created by Catherine on 2016-01-13.
 */
public class ListeOrganismesActivity extends HippieActivity {

    // TODO: Mes réservations
    // Informations sur les listes et les listAdapter : http://www.vogella.com/tutorials/AndroidListView/article.html

    ListView listeMesReservations;
    MesReservationsAdapter mesReservationsAdapter;
    ArrayList<TestDonneeCentre.Organisme> listOrganisme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.liste_organisme);
        listOrganisme= TestDonneeCentre.prepareDonnees_organismes();
                ((ExpandableListView) findViewById(R.id.liste_organisme)).setAdapter(new CarteListeOrganismeAdapter(this, listOrganisme));
    }

}
