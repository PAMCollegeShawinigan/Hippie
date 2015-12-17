package com.pam.codenamehippie.controleur.validation;

import android.content.Context;
import android.database.Observable;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.pam.codenamehippie.R;

/**
 * Classe de base pour les classes de validation de champ texte
 */
public class ValidateurDeChampTexte extends Observable<ValidateurObserver>
        implements TextWatcher, Validateur {

    /**
     * Longueur maximale d'un champ courriel
     */
    public static final int COURRIEL_LONGUEUR_MAX = 255;
    /**
     * Longueur maximale du champ de mot de passe
     */
    public static final int MOT_PASSE_LONGUEUR_MAX = 255;
    /**
     * Longueur maximale du champ nom pour organime/utilisateur
     */
    public static final int NOM_LONGUEUR_MAX = 30;

    /**
     * Longueur maximale du champ nom pour alimentaire
     */
    public static final int NOM_ALIMENTAIRE_LONGUEUR_MAX = 50;

    /**
     * Longueur maximale du champ description pour alimentaire
     */
    public static final int DESCRIPTION_ALIMENTAIRE_LONGUEUR_MAX = 100;

    /**
     * Longueur maximale du champ quantite pour alimentaire
     */
    public static final int QUANTITE_ALIMENTAIRE_LONGUEUR_MAX = 13;

    /**
     * Longueur maximale du champ valeur pour alimentaire
     */
    public static final int VALEUR_ALIMENTAIRE_LONGUEUR_MAX = 10;

    /**
     * Longueur maximale du champ prenom
     */
    public static final int PRENOM_LONGUEUR_MAX = 30;

    /**
     * Longueur maximale du champ no telephone
     */
    public static final int NO_TELEPHONE_LONGUEUR_MAX = 10;

    /**
     * {@link Context} pour aller chercher les ressources.
     */
    protected final Context context;
    /**
     * Défini la longueure maximale du champ
     */
    protected final int longueurMaximale;
    /**
     * {@link EditText} à valider.
     */
    protected final EditText editText;
    /**
     * Defini si le champ validé est requis.
     */
    protected final boolean estRequis;

    protected ValidateurDeChampTexte(@NonNull Context context,
                                     @NonNull EditText champText,
                                     boolean requis,
                                     int longueurMaximale) {
        this.context = context;
        this.editText = champText;
        this.estRequis = requis;
        this.longueurMaximale = longueurMaximale;
    }

    public static ValidateurDeChampTexte newInstance(@NonNull Context context,
                                                     @NonNull EditText champText,
                                                     boolean requis,
                                                     int longueurMaximale) {
        return new ValidateurDeChampTexte(context, champText, requis, longueurMaximale);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        this.notifierLesVoyeurs(this.estValide());
    }

    @Override
    public boolean estValide() {
        Editable text = this.getText();
        if (TextUtils.isEmpty(text) && (this.estRequis)) {
            this.editText.setError(this.context.getString(R.string.error_field_required));
            return false;
        } else if (text.length() > this.longueurMaximale) {
            this.editText.setError(this.context.getString(R.string.error_field_too_long));
            return false;
        }
        return true;
    }

    @Override
    public void onPause() {
        this.editText.removeTextChangedListener(this);

    }

    @Override
    public void onResume() {
        this.editText.addTextChangedListener(this);
        this.notifierLesVoyeurs(this.estValide());
    }

    /**
     * Accesseur du champ observé
     *
     * @return le champ observé
     */
    public EditText getEditText() {
        return this.editText;
    }

    /**
     * Accesseur de comodité pour la valeur du champ observé
     *
     * @return la valeur du champ observé.
     */
    public Editable getText() {
        return this.editText.getText();
    }

    /**
     * Mutateur de comodité pour la valeur du champ observé
     *
     * @see EditText#setText(CharSequence)
     */
    public final void setText(CharSequence text) {
        this.editText.setText(text);
        this.afterTextChanged(this.editText.getText());
    }

    /**
     * Mutateur de comodité pour la valeur du champ observé
     *
     * @see EditText#setText(int)
     */
    public final void setText(@StringRes int resId) {
        this.editText.setText(resId);
        this.afterTextChanged(this.editText.getText());
    }

    /**
     * Accesseur de comodité pour la valeur du champ observé
     *
     * @return la valeur du champ observé.
     */
    public String getTextString() {
        return this.getText().toString();
    }

    /**
     * Accesseur de la longueur maximale du champ
     *
     * @return la longueur maximale du champ
     */
    public int getLongueurMaximale() {
        return this.longueurMaximale;
    }

    /**
     * Accesseur du champ est requis
     *
     * @return vrai si le champ est requis
     */
    public boolean estRequis() {
        return this.estRequis;
    }

    /**
     * Notifie les observateur.
     *
     * @param estValide
     *         valeur à envoyer à ceux-ci
     */
    protected void notifierLesVoyeurs(boolean estValide) {
        synchronized (this.mObservers) {
            if (!this.mObservers.isEmpty()) {
                for (ValidateurObserver observer : this.mObservers) {
                    observer.enValidatant(this, estValide);
                }
            }
        }
    }

}
