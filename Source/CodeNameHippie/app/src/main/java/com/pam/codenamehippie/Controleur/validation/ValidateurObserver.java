package com.pam.codenamehippie.controleur.validation;

/**
 * Interface pour les objet qui veulent observer des Validateurs. Sert, entre autre à remplir le
 * parametre T de {@link android.database.Observable}
 */
public interface ValidateurObserver {

    void enValidant(Validateur validateur, boolean estValide);
}
