/*
 * ObservateurDeDepot.java
 * CodeNameHippie
 *
 * Copyright (c) 2016. Philippe Lafontaine
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

package com.pam.codenamehippie.modele.depot;

import com.pam.codenamehippie.modele.BaseModele;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Interface de callback pour recevoir des notifications de changement dans les dépots.
 *
 * @param <T>
 *         Type du modèle à recevoir dans les callback
 */
public interface ObservateurDeDepot<T extends BaseModele<T>> {

    /**
     * Callback pour savoir quand un dépôt débute une requête.
     */
    void surDebutDeRequete();

    /**
     * Callback pour recevoir les données du dépôt.
     *
     * @param modeles
     *         le contenu du dépôt au moment du callback.
     */
    void surChangementDeDonnees(ArrayList<T> modeles);

    /**
     * Callback pour savoir quand un dépôt finit une requête.
     */
    void surFinDeRequete();

    /**
     * Callback pour savoir quand un dépôt a une erreur lors d'une requête.
     */
    void surErreur(IOException e);
}
