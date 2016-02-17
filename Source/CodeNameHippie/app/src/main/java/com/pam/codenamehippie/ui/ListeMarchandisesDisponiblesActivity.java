package com.pam.codenamehippie.ui;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.ExpandableListView;

import com.pam.codenamehippie.R;
import com.pam.codenamehippie.http.exception.HttpReponseException;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.OrganismeModele;
import com.pam.codenamehippie.modele.UtilisateurModele;
import com.pam.codenamehippie.modele.depot.AlimentaireModeleDepot;
import com.pam.codenamehippie.modele.depot.DepotManager;
import com.pam.codenamehippie.modele.depot.FiltreDeListe;
import com.pam.codenamehippie.modele.depot.ObservateurDeDepot;
import com.pam.codenamehippie.ui.adapter.ListeMarchandisesDisponiblesAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Catherine on 2016-01-21.
 * <p>
 * Activité pour faire afficher la liste des marchandises disponibles (pas google maps)
 * </p>
 */
public class ListeMarchandisesDisponiblesActivity extends HippieActivity
        implements ObservateurDeDepot<AlimentaireModele> {

    private static final String TAG = ListeMesReservationsActivity.class.getSimpleName();
    private ListeMarchandisesDisponiblesAdapter listeMarchandisesDisponiblesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.liste_marchandise_dispo);

        AlimentaireModeleDepot alimentaireModeleDepot = DepotManager.getInstance()
                                                                    .getAlimentaireModeleDepot();

        // Filtre pour récupérer les données pour les marchandises dans le parent
        // Parent = liste_marchandise_dispo_group (le layout)
        ArrayList<AlimentaireModele> modelesGroup = new ArrayList<>();

        // Filtre pour récupérer les données des entreprises dans l'enfant
        // Enfant = liste_marchandise_dispo_details
        ArrayList<AlimentaireModele> modelesDetails = new ArrayList<>();
        UtilisateurModele uc = this.authentificateur.getUtilisateur();
        OrganismeModele organisme = (uc != null) ? uc.getOrganisme() : null;

        // On va chercher l'expendable listView
        ExpandableListView listView =
                (ExpandableListView) this.findViewById(R.id.marchandise_dispo);
        this.listeMarchandisesDisponiblesAdapter =
                new ListeMarchandisesDisponiblesAdapter(this, alimentaireModeleDepot, organisme);
        // On set l'adapter pour la liste.
        listView.setAdapter(this.listeMarchandisesDisponiblesAdapter);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AlimentaireModeleDepot alimentaireModeleDepot = DepotManager.getInstance()
                                                                    .getAlimentaireModeleDepot();
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
    public void surChangementDeDonnees(List<AlimentaireModele> modeles) {
        this.listeMarchandisesDisponiblesAdapter.setGroupItems(modeles);
    }

    @Override
    public void surFinDeRequete() {
        this.cacherLaProgressbar();
    }

    @Override
    public void surErreur(IOException e) {
        Snackbar snackbar;
        if (e instanceof HttpReponseException) {
            Integer code = ((HttpReponseException) e).getCode();
            switch (code) {
                case 404:
                    snackbar = Snackbar.make(this.viewSwitcher, R.string.error_http_404,
                                             Snackbar.LENGTH_SHORT);
                    break;
                case 409:
                    snackbar = Snackbar.make(this.viewSwitcher, "Conflit: déjà reservé",
                                             Snackbar.LENGTH_SHORT);
                    break;
                case 500:
                    snackbar = Snackbar.make(this.viewSwitcher, R.string.error_http_500,
                                             Snackbar.LENGTH_SHORT);
                    break;
                default:
                    snackbar = Snackbar.make(this.viewSwitcher, R.string.error_connection,
                                             Snackbar.LENGTH_SHORT);
                    break;
            }
        } else {
            snackbar = Snackbar.make(this.viewSwitcher, R.string.error_connection,
                                     Snackbar.LENGTH_SHORT);
        }
        snackbar.show();
        Log.e(TAG, "Requête échouée", e);
    }
}
