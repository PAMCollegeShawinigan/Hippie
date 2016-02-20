package com.pam.codenamehippie.controleur;

import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;

import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.depot.AlimentaireModeleDepot;
import com.pam.codenamehippie.modele.depot.DepotManager;

/**
 * Classe servant à encapsuler les fonctions de button d'item de liste view de marchandise.
 */
public final class ActionMarchandise implements OnClickListener,
                                                DialogInterface.OnClickListener {

    /**
     * Button supprimer
     */
    private final View supprimerButton;
    /**
     * Button collecter
     */
    private final View reserverButton;

    /**
     * Objet à modifier.
     */
    private final AlimentaireModele modele;

    private final AlimentaireModeleDepot depot;

    private ActionMarchandise(View supprimerButton,
                              View reserverButton,
                              AlimentaireModele modele) {
        this.reserverButton = reserverButton;
        this.reserverButton.setOnClickListener(this);
        this.supprimerButton = supprimerButton;
        this.supprimerButton.setOnClickListener(this);
        this.modele = modele;
        this.depot = DepotManager.getInstance().getAlimentaireModeleDepot();

    }

    private static ActionMarchandise newActionMarchandise(View supprimerButton,
                                                          View reserverButton,
                                                          AlimentaireModele modele) {
        return new ActionMarchandise(supprimerButton, reserverButton, modele);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

    @Override
    public void onClick(View v) {

    }
}
