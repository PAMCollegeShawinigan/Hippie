/*
 * ValideurCourriel.java
 * CodeNameHippie
 *
 * Copyright (c) 2015. Philippe Lafontaine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.pam.codenamehippie.controleur.validation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Patterns;
import android.widget.EditText;

import com.pam.codenamehippie.R;

/**
 * Class qui sert à valider des champ de courriel
 */
public final class ValidateurCourriel extends ValidateurDeChampTexte {

    private ValidateurCourriel(@NonNull Context context,
                               @NonNull EditText editText,
                               boolean estRequis) {
        super(context, editText, estRequis, COURRIEL_LONGUEUR_MAX);
    }

    /**
     * Instancie un nouveau validateur de mot de passe
     *
     * @param context
     *         context pour retrouver les messages d'erreur
     * @param editText
     *         le champ de courriel à observer
     *
     * @return un nouveau {@link ValidateurMotDePasse}
     */
    public static ValidateurCourriel newInstance(@NonNull Context context,
                                                 @NonNull EditText editText,
                                                 boolean estRequis) {
        return new ValidateurCourriel(context, editText, estRequis);
    }

    @Override
    public boolean estValide() {
        boolean resultat = super.estValide();
        if (!Patterns.EMAIL_ADDRESS.matcher(this.getText()).matches()) {
            this.editText.setError(this.context.getString(R.string.error_invalid_email));
            return false;
        }
        return resultat;
    }
}

