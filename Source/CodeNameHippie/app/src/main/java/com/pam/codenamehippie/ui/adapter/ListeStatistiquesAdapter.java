package com.pam.codenamehippie.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.OrganismeModele;
import com.pam.codenamehippie.modele.TransactionModele;
import com.pam.codenamehippie.modele.depot.TransactionModeleDepot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Cette classe permet de faire le lien entre les composantes de l'interface utilisateur et
 * la source de données afin de relier les données aux vues correctement.
 */
public class ListeStatistiquesAdapter extends BaseExpandableListAdapter{

    private final Context context;
    private TransactionModeleDepot depot;


    public void setItems(List<TransactionModele> items) {
        this.items.clear();
        if (items != null && !items.isEmpty()) {
            for (TransactionModele modele : items) {
               // OrganismeModele k = modele.getDonneur();
                OrganismeModele k = modele.getReceveur();
                if (this.items.containsKey(k)) {
                    this.items.get(k).add(modele);
                } else {
                    List v = new ArrayList();
                    v.add(modele);
                    this.items.put(k, v);
                }
            }
        }
        this.notifyDataSetChanged();
    }

    // Liste pour statistiques produits collecté en tant que donneur
    private Map<OrganismeModele ,List<TransactionModele>> items = new HashMap<>();
    // Liste pour statistiques produits collecté en tant que receveur
   // private volatile ArrayList<AlimentaireModele> itemsReceveur = new ArrayList<>();


    public ListeStatistiquesAdapter(Context context) {
        this.context = context;
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
            Double total = 0.00;
            for (TransactionModele e : this.items.get(modele)) {
                AlimentaireModele ea = e.getAlimentaire();
                total += ((ea != null) && (ea.getValeur() != null))? ea.getValeur() : 0.00;
            }

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView =
                    infalInflater.inflate(R.layout.liste_statistiques_group, parent, false);
        }

            // Affiche le nom de l'organisme ou entreprise selon le cas
            ((TextView) convertView.findViewById(R.id.tv_statistiques_nom_organisme_group))
                    .setText(modele.getNom());

            // Affiche la valeur des dons de l'entreprise ou la valeur des dons reçus par un
            // organisme selon le cas
            ((TextView) convertView.findViewById(R.id.tv_statistiques_valeur_total_group))
                    .setText("$ " + String.format("%.2f", total));

            return convertView;
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
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {

        // Fait afficher le layout modèle Details, afin de voir les infos des transactions
        // Lorsque l'on clique sur l'organisme pour voir plus d'informations.
        // C'est le "child" modèle.
        TransactionModele modele = this.getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.liste_statistiques_detail, parent, false);
        }

        // Fait afficher le nom du produit
        ((TextView) convertView.findViewById(R.id.tv_statistiques_nom_marchandise))
                .setText(modele.getAlimentaire().getNom());

        // Fait afficher la quantité du produit
        ((TextView) convertView.findViewById(R.id.tv_statistiques_qtee_marchandise))
                .setText(modele.getAlimentaire().getQuantiteString());

        // Fait afficher la date de collecte du produit
        Calendar cal = modele.getCalendarDateCollecte();
        Date date = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateCollecte = format.format(date);
        ((TextView) convertView.findViewById(R.id.tv_statistiques_date_collecte))
                .setText(dateCollecte);

        // Fait afficher la valeur total du produit
        ((TextView) convertView.findViewById(R.id.tv_statistiques_valeur_produit))
                .setText("$ " + String.format("%.2f", modele.getAlimentaire().getValeur()));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


}
