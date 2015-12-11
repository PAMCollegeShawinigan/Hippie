package com.pam.codenamehippie.controleur.validation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.EditText;

/**
 * Classe qui sert à valider des champs de mots de passe.
 * <p/>
 * Les champs de mot de passe sont toujours requis
 */
public final class ValidateurMotDePasse extends ValidateurDeChampTexte {

    private ValidateurMotDePasse(@NonNull Context context,
                                 @NonNull EditText editText) {
        super(context, editText, true, MOT_PASSE_LONGUEUR_MAX);
    }

    /**
     * Instancie un nouveau validateur de mot de passe
     *
     * @param context
     *         context pour retrouver les messages d'erreurs
     * @param editText
     *         le champ de mot passe à observer
     *
     * @return un nouveau {@link ValidateurMotDePasse}
     */
    public static ValidateurMotDePasse newInstance(@NonNull Context context,
                                                   @NonNull EditText editText) {
        return new ValidateurMotDePasse(context, editText);
    }

    @Override
    public boolean estValide() {
        //TODO Ajouter des contraintes aux mots de passe
        return super.estValide();
    }

}
