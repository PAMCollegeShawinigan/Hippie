package com.pam.codenamehippie.ui;

import android.content.Intent;
import android.os.Bundle;


import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;

import com.pam.codenamehippie.modele.AdresseModele;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.OrganismeModele;
import com.pam.codenamehippie.modele.UtilisateurModele;
import com.pam.codenamehippie.modele.depot.AlimentaireModeleDepot;
import com.pam.codenamehippie.modele.depot.ObservateurDeDepot;
import com.pam.codenamehippie.modele.depot.UtilisateurModeleDepot;

import java.io.IOException;
import java.util.ArrayList;

public class ProfilActivity extends HippieActivity implements ObservateurDeDepot<UtilisateurModele> {
    private static final String TAG = ProfilActivity.class.getSimpleName();
    private UtilisateurModeleDepot utilisateurModeleDepot;
    private UtilisateurModele utilisateur;
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        utilisateurModeleDepot =
                ((HippieApplication) this.getApplication()).getUtilisateurModeleDepot();
        userID = this.sharedPreferences.getInt(this.getString(R.string.pref_user_id_key), -1);


    }

    @Override
    protected void onPause() {
        super.onPause();
        utilisateurModeleDepot.setFiltreDeListe(null);
        utilisateurModeleDepot.supprimerTousLesObservateurs();
    }

    @Override
    protected void onResume() {
        super.onResume();
        utilisateurModeleDepot.ajouterUnObservateur(this);
        if (userID != -1) {
            utilisateurModeleDepot.rechercherParId(userID);
        }
    }


    @Override
    public void surDebutDeRequete() {
        this.afficherLaProgressBar();
    }

    @Override
    public void surChangementDeDonnees(ArrayList<UtilisateurModele> modeles) {
        this.utilisateur = modeles.get(0);
        Log.e("--------------------", "SIZE=" + modeles.size());
        Log.d("bob", this.utilisateur.toString());
        ((TextView) findViewById(R.id.tv_profil)).setText(R.string.tv_profil_name);
        ((TextView) findViewById(R.id.tv_profil_contact_name_placeholder)).setText(utilisateur.getNom());
        ((TextView) findViewById(R.id.tv_profil_email_placeholder)).setText(utilisateur.getCourriel());
        ((TextView) findViewById(R.id.tv_profil_phone_placeholder)).setText(utilisateur.getFormattedTelephone());
        ((TextView) findViewById(R.id.tv_profil_moyen_contact_placeholder)).setText("telephone ou courriel");
        ((TextView) findViewById(R.id.tv_enterprise_section)).setText(R.string.tv_entreprise_section);
        OrganismeModele organisme = this.utilisateur.getOrganisme();
        if (organisme != null) {
            ((TextView) findViewById(R.id.tv_profil_nom_entreprise_placeholder)).setText(organisme.getNom());
            ((TextView) findViewById(R.id.tv_profil_phone2_placeholder)).setText(organisme.getFormattedTelephone());
            ((TextView) findViewById(R.id.tv_profil_osbl_placeholder)).setText(organisme.getNoOsbl());
            ((TextView) findViewById(R.id.tv_profil_neq_placeholder)).setText(organisme.getNoEntreprise());
            AdresseModele addr = organisme.getAdresse();
            if ( addr != null) {
                ((TextView) findViewById(R.id.tv_profil_adresse_placeholder)).setText(addr.toFormattedString());
            }

        }

    }


    @Override
    public void surFinDeRequete() {
        this.cacherLaProgressbar();
    }

    @Override
    public void surErreur(IOException e) {
        // TODO: Faire un toast.
        Log.e(TAG, "Requête échouée", e);

    }

    public void surEditProfilClick(View v){
        Snackbar.make(v,
                this.getString(R.string.message_welcome, this.utilisateur.getNomComplet()),
                Snackbar.LENGTH_SHORT
        ).show();

    }
}
