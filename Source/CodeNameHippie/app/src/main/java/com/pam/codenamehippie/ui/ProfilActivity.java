package com.pam.codenamehippie.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AdresseModele;
import com.pam.codenamehippie.modele.OrganismeModele;
import com.pam.codenamehippie.modele.UtilisateurModele;
import com.pam.codenamehippie.modele.depot.DepotManager;
import com.pam.codenamehippie.modele.depot.ObservateurDeDepot;
import com.pam.codenamehippie.modele.depot.UtilisateurModeleDepot;

import java.io.IOException;
import java.util.List;

public class ProfilActivity extends HippieActivity
        implements ObservateurDeDepot<UtilisateurModele> {

    private static final String TAG = ProfilActivity.class.getSimpleName();
    private UtilisateurModeleDepot utilisateurModeleDepot;
    private UtilisateurModele utilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_profil);
        this.utilisateurModeleDepot = DepotManager.getInstance().getUtilisateurModeleDepot();
        UtilisateurModele uc = this.authentificateur.getUtilisateur();
        if (uc != null) {
            this.setUtilisateur(uc);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.utilisateur != null) {
            this.utilisateurModeleDepot.rechercherParId(this.utilisateur.getId());
        }
    }

    @Override
    public void surDebutDeRequete() {
        this.afficherLaProgressBar();
    }

    @Override
    public void surChangementDeDonnees(List<UtilisateurModele> modeles) {
        Log.e("--------------------", "SIZE=" + modeles.size());
        this.setUtilisateur(modeles.get(0));
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

    public void surEditProfilClick(View v) {
        Snackbar.make(v, this.getString(R.string.message_welcome, this.utilisateur.getNomComplet()),
                      Snackbar.LENGTH_SHORT).show();

    }

    public void setUtilisateur(@NonNull UtilisateurModele utilisateur) {
        this.utilisateur = utilisateur;
        Log.d("bob", this.utilisateur.toString());
        ((TextView) this.findViewById(R.id.tv_profil)).setText(R.string.tv_profil_name);
        ((TextView) this.findViewById(R.id.tv_profil_contact_name_placeholder)).setText(
                this.utilisateur.getNom());
        ((TextView) this.findViewById(R.id.tv_profil_email_placeholder)).setText(
                this.utilisateur.getCourriel());
        ((TextView) this.findViewById(R.id.tv_profil_phone_placeholder)).setText(
                this.utilisateur.getFormattedTelephone());
        ((TextView) this.findViewById(R.id.tv_profil_moyen_contact_placeholder)).setText(
                "telephone ou courriel");
        ((TextView) this.findViewById(R.id.tv_enterprise_section)).setText(
                R.string.tv_entreprise_section);
        OrganismeModele organisme = this.utilisateur.getOrganisme();
        if (organisme != null) {
            ((TextView) this.findViewById(R.id.tv_profil_nom_entreprise_placeholder)).setText(
                    organisme.getNom());
            ((TextView) this.findViewById(R.id.tv_profil_phone2_placeholder)).setText(
                    organisme.getFormattedTelephone());
            ((TextView) this.findViewById(R.id.tv_profil_osbl_placeholder)).setText(
                    organisme.getNoOsbl());
            ((TextView) this.findViewById(R.id.tv_profil_neq_placeholder)).setText(
                    organisme.getNoEntreprise());
            AdresseModele addr = organisme.getAdresse();
            if (addr != null) {
                ((TextView) this.findViewById(R.id.tv_profil_adresse_placeholder)).setText(
                        addr.toFormattedString());
            }
        }

    }
}
