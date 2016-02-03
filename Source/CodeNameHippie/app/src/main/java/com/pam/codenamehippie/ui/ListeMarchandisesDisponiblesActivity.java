package com.pam.codenamehippie.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;

import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.depot.AlimentaireModeleDepot;
import com.pam.codenamehippie.modele.depot.FiltreDeListe;
import com.pam.codenamehippie.modele.depot.ObservateurDeDepot;
import com.pam.codenamehippie.ui.adapter.ListeMarchandisesDisponiblesAdapter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Catherine on 2016-01-21.
 * <p>
 * Activité pour faire afficher la liste des marchandises disponibles (pas google maps)
 */
public class ListeMarchandisesDisponiblesActivity extends HippieActivity implements
                                                                         ObservateurDeDepot<AlimentaireModele> {

    private static final String TAG = ListeMesReservationsActivity.class.getSimpleName();
    private ExpandableListView maListeMarchandisesDisponibles;
    private ListeMarchandisesDisponiblesAdapter listeMarchandisesDisponiblesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_marchandise_dispo);

        AlimentaireModeleDepot alimentaireModeleDepot =
                ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();

        // Filtre pour récupérer les données pour les marchandises dans le parent
        // Parent = liste_marchandise_dispo_group (le layout)
        ArrayList<AlimentaireModele> modelesGroup = new ArrayList<>();

        // Filtre pour récupérer les données des entreprises dans l'enfant
        // Enfant = liste_marchandise_dispo_details
        ArrayList<AlimentaireModele> modelesDetails = new ArrayList<>();

        // On va chercher l'expendable listView
        maListeMarchandisesDisponibles = (ExpandableListView) findViewById(R.id.marchandise_dispo);
        listeMarchandisesDisponiblesAdapter =
                new ListeMarchandisesDisponiblesAdapter(this, alimentaireModeleDepot);
        // On set l'adapter pour la liste.
        maListeMarchandisesDisponibles.setAdapter(listeMarchandisesDisponiblesAdapter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        AlimentaireModeleDepot alimentaireModeleDepot =
                ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();
        alimentaireModeleDepot.setFiltreDeListe(null);
        alimentaireModeleDepot.supprimerTousLesObservateurs();

    }

    @Override
    protected void onResume() {
        super.onResume();
        AlimentaireModeleDepot alimentaireModeleDepot =
                ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();
        alimentaireModeleDepot.ajouterUnObservateur(this);
        // Filtre pour récupérer les items dont le statut est Disponible
        alimentaireModeleDepot.setFiltreDeListe(new FiltreDeListe<AlimentaireModele>() {
            @Override
            public boolean appliquer(AlimentaireModele item) {
                String statut = item.getStatut();
                return (statut.equalsIgnoreCase("Disponible"));
            }
        });
        alimentaireModeleDepot.peuplerListeDonDispo();
    }

    @Override
    public void surDebutDeRequete() {
        this.afficherLaProgressBar();
    }

    @Override
    public void surChangementDeDonnees(ArrayList<AlimentaireModele> modeles) {
        this.listeMarchandisesDisponiblesAdapter.setGroupItems(modeles);
    }

    @Override
    public void surFinDeRequete() {
        this.cacherLaProgressbar();
    }

    @Override
    public void surErreur(IOException e) {
        // TODO: Faire un toast.
        Log.e(TAG, "Requête échouée", e);
    }
}
