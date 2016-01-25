package com.pam.codenamehippie.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;

import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.AlimentaireModeleDepot;
import com.pam.codenamehippie.modele.FiltreDeListe;
import com.pam.codenamehippie.modele.MarchandiseModele;
import com.pam.codenamehippie.modele.ObservateurDeDepot;
import com.pam.codenamehippie.modele.OrganismeModele;
import com.pam.codenamehippie.modele.OrganismeModeleDepot;
import com.pam.codenamehippie.ui.adapter.ListeMarchandisesDisponiblesAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Catherine on 2016-01-21.
 *
 * Activité pour faire afficher la liste des marchandises disponibles (pas google maps)
 *
 */
public class ListeMarchandisesDisponiblesActivity extends HippieActivity implements
                                                                         ObservateurDeDepot<AlimentaireModele> {

    private ExpandableListView maListeMarchandisesDisponibles;
    private ListeMarchandisesDisponiblesAdapter listeMarchandisesDisponiblesAdapter;
    private static final String TAG = ListeMesReservationsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_marchandise_dispo);

        AlimentaireModeleDepot alimentaireModeleDepot = ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();

        // Filtre pour récupérer les données pour les marchandises dans le parent
        // Parent = liste_marchandise_dispo_group (le layout)
        ArrayList<AlimentaireModele> modelesGroup = new ArrayList<>();

        // Filtre pour récupérer les données des entreprises dans l'enfant
        // Enfant = liste_marchandise_dispo_details
        ArrayList<AlimentaireModele> modelesDetails = new ArrayList<>();


        // On va chercher l'expendable listView
        maListeMarchandisesDisponibles = (ExpandableListView) findViewById(R.id.marchandise_dispo);
        listeMarchandisesDisponiblesAdapter = new ListeMarchandisesDisponiblesAdapter(this, alimentaireModeleDepot);
        // On set l'adapter pour la liste.
        maListeMarchandisesDisponibles.setAdapter(listeMarchandisesDisponiblesAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AlimentaireModeleDepot alimentaireModeleDepot =
                ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();
        alimentaireModeleDepot.setFiltreDeListe(null);
        alimentaireModeleDepot.supprimerToutLesObservateurs();

    }

    @Override
    protected void onResume() {
        super.onResume();
        AlimentaireModeleDepot alimentaireModeleDepot =
                ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();
        // TODO: Déplacer les deux ligne qui suivent dans l'activité de liste
        int orgId = this.sharedPreferences.getInt(this.getString(R.string.pref_org_id_key),
                -1
        );
        alimentaireModeleDepot.ajouterUnObservateur(this);
        // Filtre pour récupérer les items dont le statut est Disponible ou Réservé
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
