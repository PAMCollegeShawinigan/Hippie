package com.pam.codenamehippie.controleur.validation;

/**
 * Interface pour les classes de validation d'entrée.
 */
public interface Validateur {

    /**
     * Methode pour vérifier si les champ du validateurs sont notifierLesVoyeurs et
     * update la vue
     * en conséquence.
     *
     * @return True si le champ est notifierLesVoyeurs
     */
    boolean estValide();

    /**
     * Methode à appeler dans {@link android.app.Fragment#onPause} ou dans
     * {@link android.app.Activity#onPause}
     */
    void onPause();

    /**
     * Methode à appeler dans {@link android.app.Fragment#onResume} ou dans
     * {@link android.app.Activity#onResume}
     */
    void onResume();
}
