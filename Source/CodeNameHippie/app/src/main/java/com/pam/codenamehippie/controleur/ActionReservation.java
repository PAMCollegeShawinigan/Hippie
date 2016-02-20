package com.pam.codenamehippie.controleur;

import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;

import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.depot.AlimentaireModeleDepot;
import com.pam.codenamehippie.modele.depot.DepotManager;

/**
 * Classe servant à encapsuler les fonctions de button d'item de liste view de réservation.
 */
public final class ActionReservation implements OnClickListener,
                                                DialogInterface.OnClickListener {

    /**
     * Button supprimer
     */
    private final View supprimerButton;
    /**
     * Button collecter
     */
    private final View collecterButton;

    /**
     * Objet à modifier.
     */
    private final AlimentaireModele modele;

    private final AlimentaireModeleDepot depot;

    private ActionReservation(View supprimerButton,
                              View collecterButton,
                              AlimentaireModele modele) {
        this.collecterButton = collecterButton;
        this.collecterButton.setOnClickListener(this);
        this.supprimerButton = supprimerButton;
        this.supprimerButton.setOnClickListener(this);
        this.modele = modele;
        this.depot = DepotManager.getInstance().getAlimentaireModeleDepot();

    }

    private static ActionReservation newActionReservation(View supprimerButton,
                                                          View collecterButton,
                                                          AlimentaireModele modele) {
        return new ActionReservation(supprimerButton, collecterButton, modele);
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

    }
}
