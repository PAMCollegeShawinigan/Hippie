package com.pam.codenamehippie.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

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
 * Cette classe permet de récupérer la liste des réservations personnalisée selon l'id du receveur
 * et de l'afficher dans l'interface utilisateur.
 */
public class ListeMesReservationsActivity extends HippieActivity
        implements ObservateurDeDepot<AlimentaireModele> {

    private static final String TAG = ListeMesReservationsActivity.class.getSimpleName();
    private ListView listeMesReservations;
    private MesReservationsAdapter mesReservationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.liste_reservations);

        AlimentaireModeleDepot alimentaireModeleDepot =
                ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();

        this.mesReservationsAdapter = new MesReservationsAdapter(this, alimentaireModeleDepot);
        listeMesReservations = (ListView) findViewById(R.id.lv_reservation);
        listeMesReservations.setItemsCanFocus(false);
        listeMesReservations.setAdapter(mesReservationsAdapter);
        //this. mesReservationsAdapter.notifyDataSetChanged();

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
        int orgId = this.sharedPreferences.getInt(this.getString(R.string.pref_org_id_key),
                                                  -1
                                                 );
        alimentaireModeleDepot.ajouterUnObservateur(this);
        // Filtre pour récupérer les items dont le statut est Réservé
        alimentaireModeleDepot.setFiltreDeListe(new FiltreDeListe<AlimentaireModele>() {

            @Override
            public boolean appliquer(AlimentaireModele item) {
                String statut = item.getStatut();
                return (statut.equalsIgnoreCase("Réservé"));
            }
        });

        alimentaireModeleDepot.peuplerListeReservation(orgId);
    }

    @Override
    public void surDebutDeRequete() {
        this.afficherLaProgressBar();
    }

    @Override
    public void surChangementDeDonnees(ArrayList<AlimentaireModele> modeles) {
        Log.d("test", "Count= " + modeles.size());
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
