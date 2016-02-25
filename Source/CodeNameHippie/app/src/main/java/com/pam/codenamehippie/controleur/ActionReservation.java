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
import com.pam.codenamehippie.modele.depot.AlimentaireModeleDepot;
import com.pam.codenamehippie.modele.depot.DepotManager;

import static android.content.Context.WIFI_SERVICE;

/**
 * Classe servant à encapsuler les fonctions de button d'item de liste view de réservation.
 */
public final class ActionReservation implements OnClickListener,
                                                DialogInterface.OnClickListener {

    private static final int ACTION_COLLECTER = 1;
    private static final int ACTION_SUPPRIMER = 2;
    private static final int ACTION_AUCUNE_CONNEXION = 3;
    /**
     * Button supprimer
     */
    private final View supprimerButton;
    /**
     * Button collecter
     */
    private final View collecterButton;
    /**
     * Context pour accéder au strings.
     */
    private final Context context;
    /**
     * Objet à modifier.
     */
    private final AlimentaireModele modele;
    /**
     * Dépot pour gérer les requête.
     */
    private final AlimentaireModeleDepot depot;

    private final Runnable collecterCallback = new Runnable() {
        @Override
        public void run() {
            Snackbar.make(ActionReservation.this.collecterButton,
                          R.string.msg_reservation_collecte,
                          Snackbar.LENGTH_SHORT)
                    .show();
        }
    };
    private final Runnable supprimerCallback = new Runnable() {
        @Override
        public void run() {
            Snackbar.make(ActionReservation.this.supprimerButton,
                          R.string.msg_reservation_supprime,
                          Snackbar.LENGTH_SHORT)
                    .show();
        }
    };

    /**
     * identifiant du button cliqué.
     */
    private int actionButton;

    private ActionReservation(View supprimerButton,
                              View collecterButton,
                              AlimentaireModele modele) {
        this.collecterButton = collecterButton;
        this.collecterButton.setOnClickListener(this);
        this.supprimerButton = supprimerButton;
        this.supprimerButton.setOnClickListener(this);
        this.context = supprimerButton.getContext();
        this.modele = modele;
        this.depot = DepotManager.getInstance().getAlimentaireModeleDepot();
    }

    public static ActionReservation instancier(@NonNull View supprimerButton,
                                               @NonNull View collecterButton,
                                               @NonNull AlimentaireModele modele) {
        return new ActionReservation(supprimerButton, collecterButton, modele);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                switch (this.actionButton) {
                    case ACTION_COLLECTER:
                        this.depot.collecter(this.modele, this.collecterCallback);
                        break;
                    case ACTION_SUPPRIMER:
                        this.depot.annulerReservation(this.modele, this.supprimerCallback);
                        break;
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
                    this.actionButton = ACTION_AUCUNE_CONNEXION;
                }
            } else if (v.equals(this.collecterButton)) {
                this.afficherDialog(R.string.msg_reservation_confirme_collecte);
                this.actionButton = ACTION_COLLECTER;
            } else {
                this.afficherDialog(R.string.msg_reservation_confirme_suppression);
                this.actionButton = ACTION_SUPPRIMER;
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
