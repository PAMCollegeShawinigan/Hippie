package com.pam.codenamehippie.ui.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

import static android.view.View.VISIBLE;

/**
 * Classe d'utilité pour manipuler des views.
 */
public final class ViewUtils {

    /**
     * Constructeur privé pour éviter d'instancier cette classe
     */
    private ViewUtils() {}

    /**
     * Méthode qui affiche le texte d'une TextView ou mets la vue invisible si le texte est absent.
     *
     * @param view
     *         TextView à manipuler
     * @param texte
     *         texte à afficher dans la vue
     */
    public static void afficherTexteOuMettreLaVueInvisible(@NonNull TextView view,
                                                      @Nullable CharSequence texte) {
        if (!TextUtils.isEmpty(texte)) {
            view.setVisibility(VISIBLE);
            view.setText(texte);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Méthode qui affiche le texte d'une TextView ou mets la vue gone si le texte est absent.
     *
     * @param view
     *         TextView à manipuler
     * @param texte
     *         texte à afficher dans la vue
     */
    public static void afficherTexteOuMettreLaVueGone(@NonNull TextView view,
                                                 @Nullable CharSequence texte) {
        if (!TextUtils.isEmpty(texte)) {
            view.setVisibility(VISIBLE);
            view.setText(texte);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * Méthode qui affiche la date d'une TextView ou mets la vue invisible  si la date est null.
     *
     * @param view
     *         text view à manipuler
     * @param date
     *         Date à afficher
     * @param format
     *         format de la date
     */
    public static void afficherDateOuMetterLaVueGone(@NonNull TextView view,
                                                     @NonNull DateFormat format,
                                                     Date date) {
        afficherTexteOuMettreLaVueGone(view, (date != null) ? format.format(date) : null);
    }

    /**
     * Méthode qui affiche la date d'une TextView ou mets la vue gone si la date est null.
     *
     * @param view
     *         text view à manipuler
     * @param date
     *         Date à afficher
     * @param format
     *         format de la date
     */
    public static void afficherDateOuMettreLaVueInvisble(@NonNull TextView view,
                                                         @NonNull DateFormat format,
                                                         Date date) {
        afficherTexteOuMettreLaVueInvisible(view, (date != null) ? format.format(date) : null);
    }
}
