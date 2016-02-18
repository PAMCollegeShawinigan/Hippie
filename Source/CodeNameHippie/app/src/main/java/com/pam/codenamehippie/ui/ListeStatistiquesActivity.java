package com.pam.codenamehippie.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.OrganismeModele;
import com.pam.codenamehippie.modele.TransactionModele;
import com.pam.codenamehippie.modele.UtilisateurModele;
import com.pam.codenamehippie.modele.depot.DepotManager;
import com.pam.codenamehippie.modele.depot.FiltreDeListe;
import com.pam.codenamehippie.modele.depot.ObservateurDeDepot;
import com.pam.codenamehippie.modele.depot.TransactionModeleDepot;
import com.pam.codenamehippie.ui.adapter.ListeStatistiquesAdapter;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Cette activité sert à afficher les statistiques des produits collectés
 * selon l'id de l'organisme/entreprise
 */
public class ListeStatistiquesActivity extends HippieActivity
        implements ObservateurDeDepot<TransactionModele> {

    private static final String TAG = ListeStatistiquesActivity.class.getSimpleName();
    private ExpandableListView listeStatistiques;
    private ListeStatistiquesAdapter statistiquesAdapter;
    // Id de l'organisme/entreprise
    private Integer orgId;
    private Double totalFinal = 0.00d;

    private TextView tv_totalFinal;
    public void setTv_totalFinal(TextView tv_totalFinal) {
        this.tv_totalFinal = tv_totalFinal;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.liste_statistiques);

        // TODO: trouver une façon d'afficher le total final (somme des valeurs de chaque groupe)
        tv_totalFinal = (TextView)findViewById(R.id.tv_statistiques_valeur_total);
        // On va chercher l'expendable listView
        this.listeStatistiques = (ExpandableListView) findViewById(R.id.list_statistiques_group);
        this.statistiquesAdapter = new ListeStatistiquesAdapter(this);
        this.listeStatistiques.setAdapter(this.statistiquesAdapter);
        //On va chercher l'id organisme
        UtilisateurModele uc = this.authentificateur.getUtilisateur();
        OrganismeModele org = (uc != null) ? uc.getOrganisme() : null;
        this.orgId = (org != null) ? org.getId() : -1;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        TransactionModeleDepot transactionModeleDepot =
                DepotManager.getInstance().getTransactionModeleDepot();
        if (this.orgId != null && this.orgId != -1) {
            // TODO: Ajouter 2 DatePicker dans le layout list_statistique
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, 2014);
            Date dateDebut = calendar.getTime();
            calendar = Calendar.getInstance();
            Date dateFin = calendar.getTime();
            // TODO: inserer filtre de liste
//            transactionModeleDepot.setFiltreDeListe(new FiltreDeListe<TransactionModele>() {
//                @Override
//                public boolean appliquer(TransactionModele item) {
//                    OrganismeModele k = item.getReceveur();
//                    return (orgId.toString().equalsIgnoreCase(k.toString()));
//                }
//            });
            transactionModeleDepot.peuplerTransactions(this.orgId, dateDebut, dateFin);
        }
    }

    @Override
    public void surDebutDeRequete() {
        this.afficherLaProgressBar();
    }

    @Override
    public void surChangementDeDonnees(List<TransactionModele> modeles) {
        totalFinal = 0.00d;
        for (TransactionModele modele : modeles) {
            totalFinal += modele.getAlimentaire().getValeur();
        }
        tv_totalFinal.setText(String.format("$ " + "%.2f", totalFinal));
        Log.d("totalFinal", totalFinal.toString());
        this.statistiquesAdapter.setItems(modeles);
    }

    @Override
    public void surFinDeRequete() {
        this.cacherLaProgressbar();
    }

    @Override
    public void surErreur(IOException e) {
        //todo: snackbar
        Log.e(this.getClass().getSimpleName(), e.getMessage(), e);

    }
}
