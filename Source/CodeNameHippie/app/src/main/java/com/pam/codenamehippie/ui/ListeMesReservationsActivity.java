package com.pam.codenamehippie.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.AlimentaireModeleDepot;
import com.pam.codenamehippie.modele.FiltreDeListe;
import com.pam.codenamehippie.modele.ObservateurDeDepot;
import com.pam.codenamehippie.ui.adapter.MesReservationsAdapter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Catherine on 2016-01-13.
 *
 * Activité pour faire affiché la liste des réservations des receveurs (organismes communautaires)
 *
 */
public class ListeMesReservationsActivity extends HippieActivity implements ObservateurDeDepot<AlimentaireModele> {

    private ListView listeMesReservations;
    private MesReservationsAdapter mesReservationsAdapter;
    private static final String TAG = ListeMesReservationsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.liste_reservations);

        AlimentaireModeleDepot alimentaireModeleDepot = ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();

        // Filtre pour récupérer les items dont le statut est réservé par le receveur (AKA organisme)
        ArrayList<AlimentaireModele> modeles = new ArrayList<>();

        this.mesReservationsAdapter = new MesReservationsAdapter(this, alimentaireModeleDepot);
        listeMesReservations = (ListView) findViewById(R.id.lv_reservation);
        listeMesReservations.setItemsCanFocus(false);
        listeMesReservations.setAdapter(mesReservationsAdapter);

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
                return (statut.equalsIgnoreCase("Réservé"));
            }
        });

        // FIXME: Devrais appeler peupler réservation
        alimentaireModeleDepot.peuplerListeDon(orgId);
    }

    @Override
    public void surDebutDeRequete() {
        this.afficherLaProgressBar();
    }

    @Override
    public void surChangementDeDonnees(ArrayList<AlimentaireModele> modeles) {
        this.mesReservationsAdapter.setItems(modeles);
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
