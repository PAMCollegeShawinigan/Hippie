package com.pam.codenamehippie.modele;

import android.util.SparseArray;

public interface DepotObserver<T extends BaseModele<T>> {

    void surDebutDeRequete();

    void surSucces(SparseArray<T> modeles);

    // FIXME: Passer des exceptions?
    void surErreur();
}
