package com.pam.codenamehippie.ui;

import android.os.Bundle;
import android.widget.ListView;

import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.AlimentaireModeleDepot;
import com.pam.codenamehippie.ui.adapter.MesReservationsAdapter;

import java.util.ArrayList;

/**
 * Created by Catherine on 2016-01-13.
 */
public class ListeMesReservationsActivity extends HippieActivity {

    // TODO: Mes réservations
    // Informations sur les listes et les listAdapter : http://www.vogella.com/tutorials/AndroidListView/article.html

    ListView mesReservations;
    MesReservationsAdapter mesReservationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.liste_reservations);

        AlimentaireModeleDepot alimentaireModeleDepot = ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();

        // Filtre pour récupérer les items dont le statut est réservé par le receveur (AKA organisme)
        ArrayList<AlimentaireModele> modeles = new ArrayList<>();

        // TODO: Vérifier ce qui est disponible dans cette dite de ListeDon()
        for (AlimentaireModele modele : alimentaireModeleDepot.getListeDon()) {
            String statut = modele.getStatut();
            int idReceveur = modele.getId();

            if (statut.equalsIgnoreCase("Reservé"))

        }

    }

}
