package com.pam.codenamehippie.ui;

import android.os.Bundle;
import android.widget.ExpandableListView;

import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.AlimentaireModeleDepot;
import com.pam.codenamehippie.modele.OrganismeModele;
import com.pam.codenamehippie.modele.OrganismeModeleDepot;
import com.pam.codenamehippie.ui.adapter.ListeMarchandisesDisponiblesAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Catherine on 2016-01-21.
 *
 * Activité pour faire afficher la liste des marchandises disponibles (pas google maps)
 *
 */
public class ListeMarchandisesDisponiblesActivity extends HippieActivity {

    ExpandableListView maListeMarchandisesDisponibles;
    ListeMarchandisesDisponiblesAdapter listeMarchandisesDisponiblesAdapter;
    ArrayList<AlimentaireModele> groupItems;
    HashMap<OrganismeModele, ArrayList<AlimentaireModele>> detailsItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_marchandise_dispo);

        AlimentaireModeleDepot alimentaireModeleDepot = ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();
        OrganismeModeleDepot organismeModeleDepot = ((HippieApplication) this.getApplication()).getOrganismeModeleDepot();

        // Filtre pour récupérer les données pour les marchandises dans le parent
        // Parent = liste_marchandise_dispo_group (le layout)
        ArrayList<AlimentaireModele> modelesGroup = new ArrayList<>();

        // Filtre pour récupérer les données des entreprises dans l'enfant
        // Enfant = liste_marchandise_dispo_details
        ArrayList<OrganismeModele> modelesDetails = new ArrayList<>();

        // Recherche toutes les données demandés à faire afficher.
        // Toutes les données qui sont disponibles
        for(AlimentaireModele modele: alimentaireModeleDepot.getListeDon()) {
            String statut = modele.getStatut();
            if (statut.equalsIgnoreCase("Disponible")) {
                modelesGroup.add(modele);
            }
        }

        //Recherche toutes les données demandés à faire afficher.
        // Toutes les données étant les donneur (entreprise)
        for(OrganismeModele modele: organismeModeleDepot.getModeles()) {
            modelesDetails.add(modele);
        }


        // On va chercher l'expendable listView
        maListeMarchandisesDisponibles = (ExpandableListView) findViewById(R.id.marchandise_dispo);

        maListeMarchandisesDisponibles.setItemsCanFocus(false);
        listeMarchandisesDisponiblesAdapter = new ListeMarchandisesDisponiblesAdapter(this, groupItems, detailsItems);
        // On set l'adapter pour la liste.
        maListeMarchandisesDisponibles.setAdapter(listeMarchandisesDisponiblesAdapter);
    }

}
