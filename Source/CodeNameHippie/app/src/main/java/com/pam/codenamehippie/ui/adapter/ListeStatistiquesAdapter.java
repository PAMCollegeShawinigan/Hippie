package com.pam.codenamehippie.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.OrganismeModele;
import com.pam.codenamehippie.modele.TransactionModele;
import com.pam.codenamehippie.modele.depot.ObservateurDeDepot;
import com.pam.codenamehippie.modele.depot.TransactionModeleDepot;
import com.pam.codenamehippie.ui.HippieActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Cette classe permet de faire le lien entre les composantes de l'interface utilisateur et
 * la source de données afin de relier les données aux vues correctement.
 */
public class ListeStatistiquesAdapter extends BaseExpandableListAdapter
        implements ObservateurDeDepot<TransactionModele> {

    private final HippieActivity context;
    private TransactionModeleDepot depot;
    // Liste pour statistiques produits collecté en tant que donneur
    private Map<OrganismeModele ,List<TransactionModele>> items = new HashMap<>();
    // Liste pour statistiques produits collecté en tant que receveur
   // private volatile ArrayList<AlimentaireModele> itemsReceveur = new ArrayList<>();


    public ListeStatistiquesAdapter(HippieActivity context, TransactionModeleDepot depot) {
        this.context = context;
        this.depot = depot;
    }

    @Override
    public OrganismeModele getGroup(int groupPosition) {
        return items.keySet().toArray(new OrganismeModele[items.size()])[groupPosition];
    }

    @Override
    public int getGroupCount() {
        return this.items.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return this.getGroup(groupPosition).getId();
    }

        @Override
        public View getGroupView(final int groupPosition,
                                 final boolean isExpanded,
                                 View convertView,
                                 ViewGroup parent) {

            final OrganismeModele modele = this.getGroup(groupPosition);
            long total = 0L;
            for (TransactionModele e : this.items.get(modele)) {
                AlimentaireModele ea = e.getAlimentaire();
                total += ((ea != null) && (ea.getValeur() != null))? ea.getValeur() : 0L;
            }

            if (convertView == null) {
                LayoutInflater infalInflater =
                        (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView =
                    infalInflater.inflate(R.layout.liste_statistiques_group, parent, false);
        }

        // Affiche le nom de l'organisme ou entreprise selon le cas
        ((TextView) convertView.findViewById(R.id.tv_statistiques_nom_organisme_group)).setText(modele.getNom());
        // Affiche la valeur des dons de l'entreprise ou la valeur des dons reçus par un organisme
        // selon le cas

                ((TextView) convertView.findViewById(R.id.tv_statistiques_valeur_total_group)).setText("Ajouter Somme");
        return null;
    }

    @Override
    public TransactionModele getChild(int groupPosition, int childPosition) {
        return this.items.get(this.getGroup(groupPosition)).get(childPosition);
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.items.get(this.getGroup(groupPosition)).size();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return this.getChild(groupPosition, childPosition).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public void surDebutDeRequete() {
        this.context.afficherLaProgressBar();
    }

    @Override
    public void surChangementDeDonnees(ArrayList<TransactionModele> modeles) {
        if (modeles != null && !modeles.isEmpty()){
          for (TransactionModele modele: modeles) {
              OrganismeModele k = modele.getDonneur();
              if (this.items.containsKey(k)) {
                  this.items.get(k).add(modele);
              } else {
                  List v = new ArrayList();
                  v.add(modele);
                  this.items.put(k,v);
              }
          }
        }
    }

    @Override
    public void surFinDeRequete() {
        this.context.cacherLaProgressbar();
    }

    @Override
    public void surErreur(IOException e) {
    //todo: snackbar
        Log.e(this.getClass().getSimpleName(),e.getMessage(),e);

    }
}
