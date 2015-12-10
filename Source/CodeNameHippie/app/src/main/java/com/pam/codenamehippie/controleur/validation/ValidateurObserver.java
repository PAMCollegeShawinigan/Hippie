package com.pam.codenamehippie.controleur.validation;

/**
 * Interface pour les objet qui veulent observer des Validateurs
 */
public interface ValidateurObserver {

    void enValidatant(Validateur validateur, boolean estValide);
}
