package com.pam.codenamehippie.modele;

/**
 * Interface pour implémenter un foncteur pour filtrer des listes.
 *
 * @param <T>
 *         Type des item de la liste à filtrer.
 */
public interface FiltreDeListe<T extends BaseModele<T>> {

    /**
     * Cette méthode doit être appeler lors de la création d'une liste filtrée.
     *
     * @param item
     *         un item de liste à traiter
     *
     * @return true si item doit être ajouté à la liste.
     */
    boolean appliquer(T item);
}
