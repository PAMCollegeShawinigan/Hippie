package com.pam.codenamehippie.controleur;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;

import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.OrganismeModele;
import com.pam.codenamehippie.modele.depot.AlimentaireModeleDepot;
import com.pam.codenamehippie.modele.depot.DepotManager;

import static android.content.Context.WIFI_SERVICE;

/**
 * Classe servant à encapsuler les fonctions de button d'item de liste view de marchandise
 * disponible.
 */
public final class ActionMarchandiseDispo implements OnClickListener,
                                                     DialogInterface.OnClickListener {

    private static final int ACTION_AUCUNE_CONNEXION = 1;
    private static final int ACTION_NORMAL = 2;
    private int actionBouton = ACTION_NORMAL;

    /**
     * Button collecter
     */
    private final View reserverButton;

    /**
     * Context pour accéder au strings.
     */
    private final Context context;

    /**
     * Objet à modifier.
     */
    private final AlimentaireModele modele;

    /**
     * Organisme faisant la réservation
     */
    private final OrganismeModele receveur;

    /**
     * Dépot pour gérer les requête.
     */
    private final AlimentaireModeleDepot depot;

    private final Runnable reserverCallback = new Runnable() {
        @Override
        public void run() {
            Snackbar.make(ActionMarchandiseDispo.this.reserverButton,
                          R.string.msg_reservation_ajoute,
                          Snackbar.LENGTH_SHORT)
                    .show();
        }
    };

    private ActionMarchandiseDispo(View reserverButton,
                                   OrganismeModele receveur,
                                   AlimentaireModele modele) {
        this.reserverButton = reserverButton;
        this.reserverButton.setOnClickListener(this);
        this.receveur = receveur;
        this.context = reserverButton.getContext();
        this.modele = modele;
        this.depot = DepotManager.getInstance().getAlimentaireModeleDepot();
    }

    public static ActionMarchandiseDispo instancier(@NonNull View reserverButton,
                                                    @NonNull OrganismeModele receveur,
                                                    @NonNull AlimentaireModele modele) {
        return new ActionMarchandiseDispo(reserverButton, receveur, modele);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                if (this.actionBouton == ACTION_NORMAL) {
                    this.depot.ajouterReservation(this.modele, this.receveur, this.reserverCallback);
                }
                dialog.dismiss();
                break;
            default:
                dialog.dismiss();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        ConnectivityManager connectivityManager =
                ((ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE));
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            if (((activeNetwork != null) && !activeNetwork.isConnected()) ||
                    (activeNetwork == null)) {
                WifiManager wifiManager = ((WifiManager) this.context.getSystemService(WIFI_SERVICE));
                if (((wifiManager != null) && !wifiManager.isWifiEnabled())) {
                    wifiManager.setWifiEnabled(true);
                }
                if (((activeNetwork != null) && !activeNetwork.isConnected()) ||
                        (activeNetwork == null)) {
                    // TODO: Internet check 2.0
                    this.afficherOkDialog(R.string.error_no_internet);
                    this.actionBouton = ACTION_AUCUNE_CONNEXION;
                }
            } else if (v.equals(this.reserverButton)) {
                this.afficherDialog(R.string.msg_reservation_confirme_ajout);
                this.actionBouton = ACTION_NORMAL;
            }
        }
    }

    private void afficherDialog(@StringRes int message) {
        new AlertDialog.Builder(this.context).setMessage(message)
                                             .setPositiveButton(R.string.bouton_confirme_oui, this)
                                             .setNegativeButton(R.string.bouton_confirme_non, this)
                                             .create()
                                             .show();
    }

    private void afficherOkDialog(@StringRes int message) {
        new AlertDialog.Builder(this.context).setMessage(message)
                                             .setPositiveButton("OK", this)
                                             .create()
                                             .show();
    }
}
