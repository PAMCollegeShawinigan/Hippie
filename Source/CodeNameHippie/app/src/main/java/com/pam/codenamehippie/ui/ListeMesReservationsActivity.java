package com.pam.codenamehippie.ui;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.ListView;

import com.pam.codenamehippie.R;
import com.pam.codenamehippie.http.exception.HttpReponseException;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.OrganismeModele;
import com.pam.codenamehippie.modele.UtilisateurModele;
import com.pam.codenamehippie.modele.depot.AlimentaireModeleDepot;
import com.pam.codenamehippie.modele.depot.DepotManager;
import com.pam.codenamehippie.modele.depot.ObservateurDeDepot;
import com.pam.codenamehippie.ui.adapter.MesReservationsAdapter;

import java.io.IOException;
import java.util.List;

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
                DepotManager.getInstance().getAlimentaireModeleDepot();

        this.mesReservationsAdapter = new MesReservationsAdapter(this, alimentaireModeleDepot);
        this.listeMesReservations = (ListView) this.findViewById(R.id.lv_reservation);
        this.listeMesReservations.setItemsCanFocus(false);
        this.listeMesReservations.setAdapter(this.mesReservationsAdapter);
        //this. mesReservationsAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AlimentaireModeleDepot alimentaireModeleDepot =
                DepotManager.getInstance().getAlimentaireModeleDepot();
        UtilisateurModele uc = this.authentificateur.getUtilisateur();
        OrganismeModele org = (uc != null) ? uc.getOrganisme() : null;
        int orgId = (org != null) ? org.getId() : -1;

        alimentaireModeleDepot.peuplerListeReservation(orgId);
    }

    @Override
    public void surDebutDeRequete() {
        this.afficherLaProgressBar();
    }

    @Override
    public void surChangementDeDonnees(List<AlimentaireModele> modeles) {
        Log.d("test", "Count= " + modeles.size());
        this.mesReservationsAdapter.setItems(modeles);
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
