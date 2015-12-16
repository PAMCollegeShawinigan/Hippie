package com.pam.codenamehippie.controleur.validation;

import android.database.Observable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

/**
 * Created by Carl St-Louis le 2015-12-16.
 */
public class ValidateurDeSpinner extends Observable<ValidateurObserver>
        implements AdapterView.OnItemSelectedListener, Validateur {

    private Spinner spinner;
    private long id = 0;

    public static ValidateurDeSpinner newInstance(Spinner spinner) {
        return new ValidateurDeSpinner(spinner);
    }


    public Spinner getSpinner() {
        return this.spinner;
    }

    private ValidateurDeSpinner(Spinner spinner){
        this.spinner = spinner;

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.id = id;
        this.notifierLesVoyeurs(this.estValide());

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean estValide() {
        return (this.id != 0);
    }

    @Override
    public void onPause() {
        this.spinner.setOnItemSelectedListener(null);
    }

    @Override
    public void onResume() {
        this.spinner.setOnItemSelectedListener(this);
        this.notifierLesVoyeurs(this.estValide());
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
