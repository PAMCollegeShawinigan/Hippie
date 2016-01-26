package com.pam.codenamehippie.controleur.validation;

import android.database.Observable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

/**
 * Classe de base pour valider les Spinner
 */
public class ValidateurDeSpinner extends Observable<ValidateurObserver>
        implements AdapterView.OnItemSelectedListener, Validateur {

    private final Spinner spinner;
    private long id = 0;

    protected ValidateurDeSpinner(Spinner spinner) {
        this.spinner = spinner;
    }

    public static ValidateurDeSpinner newInstance(Spinner spinner) {
        return new ValidateurDeSpinner(spinner);
    }

    public Spinner getSpinner() {
        return this.spinner;
    }

    public long getSelectedItemId() {
        return this.spinner.getSelectedItemId();
    }

    public void setSelectedItemId(long id) {
        this.id = id;
        this.spinner.setSelection((int) id, true);
        this.notifierLesVoyeurs(this.estValide());
    }

    public int getSelectedItemPosition() {
        return this.spinner.getSelectedItemPosition();
    }

    public Object getSelectedItem() {
        return this.spinner.getSelectedItem();
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
                    observer.enValidant(this, estValide);
                }
            }
        }
    }

}
