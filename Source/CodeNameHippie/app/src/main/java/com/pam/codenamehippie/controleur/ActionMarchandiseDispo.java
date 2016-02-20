package com.pam.codenamehippie.controleur;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;

import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.OrganismeModele;
import com.pam.codenamehippie.modele.depot.AlimentaireModeleDepot;
import com.pam.codenamehippie.modele.depot.DepotManager;

/**
 * Classe servant à encapsuler les fonctions de button d'item de liste view de marchandise
 * disponible.
 */
public final class ActionMarchandiseDispo implements OnClickListener,
                                                     DialogInterface.OnClickListener {

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

    private static ActionMarchandiseDispo instancier(@NonNull View reserverButton,
                                                     @NonNull OrganismeModele receveur,
                                                     @NonNull AlimentaireModele modele) {
        return new ActionMarchandiseDispo(reserverButton, receveur, modele);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                dialog.dismiss();
                break;
            default:
                dialog.dismiss();
                break;
        }

    }

    @Override
    public void onClick(View v) {
        if (v.equals(this.reserverButton)) {
            this.afficherDialog(R.string.marchandise_reservees);
        }

    }

    private void afficherDialog(@StringRes int message) {
        new AlertDialog.Builder(this.context).setMessage(message)
                                             .setPositiveButton(R.string.bouton_confirme_oui, this)
                                             .setNegativeButton(R.string.bouton_confirme_non, this)
                                             .create()
                                             .show();
    }
}
