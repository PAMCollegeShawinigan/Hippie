package com.pam.codenamehippie.modele;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Interface de callback pour recevoir des notifications de changement dans les dépots.
 *
 * @param <T>
 *         Type du modèle à recevoir dans les callback
 */
public interface ObservateurDeDepot<T extends BaseModele<T>> {

    /**
     * Callback pour savoir quand un dépôt débute une requête.
     */
    void surDebutDeRequete();

    /**
     * Callback pour recevoir les données du dépôt.
     *
     * @param modeles
     *         le contenu du dépôt au moment du callback.
     */
    void surChangementDeDonnees(ArrayList<T> modeles);

    /**
     * Callback pour savoir quand un dépôt finit une requête.
     */
    void surFinDeRequete();

    /**
     * Callback pour savoir quand un dépôt a une erreur lors d'une requête.
     */
    void surErreur(IOException e);
}
