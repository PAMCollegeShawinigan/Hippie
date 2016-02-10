/*
 * TransactionModeleDepot.java
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

import android.annotation.SuppressLint;
import android.content.Context;

import com.pam.codenamehippie.modele.TransactionModele;

import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.OkHttpClient;

/**
 * Créé par Carl St-Louis le 23-11-2015.
 */
public class TransactionModeleDepot extends BaseModeleDepot<TransactionModele> {

    /**
     * Construction du dépot pour modèle Transaction
     */
    public TransactionModeleDepot(Context context, OkHttpClient httpClient) {
        super(context, httpClient);
        this.url = this.url.newBuilder().addPathSegment("transaction").build();
    }

    /**
     *
     * @param id du donneur et du receveur des transactions
     *           retourne la liste des transaction en tant que donneur et receveur
     */
    @SuppressLint("SimpleDateFormat")
    public void peuplerTransactions(Integer id, Date dateDebut, Date dateFin){

        String dateDebutString =
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(dateDebut);

        String dateFinString =
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(dateFin);
        this.peuplerLeDepot(this.url.newBuilder().addPathSegment(id.toString())
                                                 .addQueryParameter("date_du",dateDebutString)
                                                 .addQueryParameter("date_au", dateFinString)
                                                 .build()
                            );
    }

//    /**
//     * Rechercher un TransactionModele par ID dans le dépôt
//     *
//     * @param id
//     *   de l'objet TransactionModele
//     *
//     * @return un TransactionModele ou null si inexistant dans le dépôt
//     */
//    @Override
//    public TransactionModele rechercherParId(Integer id) {
//        TransactionModele modele = this.modeles.get(id);
//        if (modele != null) {
//            return this.modeles.get(id);
//        } else {
//            return null;
//        }
//    }

//    /**
//     * Ajouter un nouveau TransactionModele dans le dépôt
//     *
//     * @param json
//     *   de l'objet TransactionModele
//     *
//     * @return une nouvelle instance de TransactionModele vide ou null si la transaction existe
// déjà
//     */
//    @Override
//    public TransactionModele ajouterModele(String json) {
//        TransactionModele modele = this.fromJson(json);
//        if (this.modeles.get(modele.getId()) == null) {
//            this.modeles.put(modele.getId(), modele);
//            // todo: requête au serveur pour ajouter une transaction
//            return modele;
//        } else {
//            return null;
//        }
//    }

//    /**
//     * Modifier un TransactionModele présent dans le dépôt
//     *
//     * @param modele
//     *   de l'objet TransactionModele
//     *
//     * @return un TransactionModele ou null si inexistant dans le dépôt
//     */
//    @Override
//    public TransactionModele modifierModele(TransactionModele modele) {
//        TransactionModele oldModele = this.modeles.get(modele.getId());
//
//        if (oldModele != null) {
//            // todo: requête au serveur pour modification sur la transaction
//            return oldModele;
//        } else {
//            return null;
//        }
//    }

//    /**
//     * Supprimer un TransactionModele présent dans le dépôt
//     *
//     * @param modele
//     *   de l'objet TransactionModele
//     *
//     * @return un TransactionModele ou null si inexistant dans le dépôt
//     */
//    @Override
//    public TransactionModele supprimerModele(TransactionModele modele) {
//        TransactionModele oldModele = this.modeles.put(modele.getId(), null);
//        if (oldModele != null) {
//            // todo: requête au serveur pour suppression d'une transaction
//        }
//        return oldModele;
//    }
}
