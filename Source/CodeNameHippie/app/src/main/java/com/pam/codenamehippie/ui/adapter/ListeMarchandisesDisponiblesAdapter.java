package com.pam.codenamehippie.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.AlimentaireModeleDepot;
import com.pam.codenamehippie.modele.OrganismeModele;
import com.pam.codenamehippie.modele.OrganismeModeleDepot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Catherine on 2016-01-21.
 *
 * L'adapter pour faire afficher la liste des marchandises disponibles par date
 * (et non avec google maps)
 *
 */

public class ListeMarchandisesDisponiblesAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<AlimentaireModele> groupItems = new ArrayList<>();
    private ArrayList<AlimentaireModele> detailsItems = new ArrayList<>();
    private AlimentaireModeleDepot alimentaireDepot;

    /**
     * On ajoute les paramètres.
     * @param context
     * @param alimentaireDepot
     */

    public ListeMarchandisesDisponiblesAdapter(Context context,
                                               AlimentaireModeleDepot alimentaireDepot) {
        this.context = context;
        this.alimentaireDepot = alimentaireDepot;
    }

    /**
     * Ici, on ajoute les élements pour faire afficher la liste expendable
     * Donc, on affiche le layout : liste_marchandises_dispo_details, afin de faire afficher l'adresse
     * et le nom de l'entreprise (du donneur en fait) qui donne la marchandise ciblée.
     *
     *
     * On ajoute les paramètres.
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groupItems.get(groupPosition);
    }

    /**
     * On ajoute les paramètres.
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * On ajoute les paramètres.
     *
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return
     */

    @Override
    public View getChildView(int groupPosition,
                             int childPosition,
                             boolean isLastChild,
                             View convertView,
                             ViewGroup parent) {

        // Fait afficher le layout modèle Details, afin de voir les infos de l'entreprise
        // Lorsque l'on clique sur la marchandise pour voir plus d'informations.
        // C'est le "child" modèle.
        AlimentaireModele modele = (AlimentaireModele) this.getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.liste_marchandise_dispo_details, parent, false);
        }

        // Fait afficher le nom de l'entreprise
        ((TextView) convertView.findViewById(R.id.tv_md_nom_entreprise)).setText(modele.getOrganisme().getNom());

        // Fait afficher l'adresse de l'entreprise
        // TODO: Arranger l'erreur du CharSequence, pour le moment, on le laisse commenté.
        ((TextView) convertView.findViewById(R.id.tv_md_adresse_entreprise)).setText(modele.getOrganisme().getAdresse().toString());
        return convertView;
    }

    /**
     * On ajoute les paramètres.
     *
     * @param groupPosition
     * @return
     */

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    /**
     *
     * Ici, on ajoute les élements pour faire afficher les informations de la marchandise.
     * On affiche donc le layout liste_marchandises_dispo_group
     *
     *
     * On ajoute les paramètres.
     *
     * @param groupPosition
     * @return
     */

    @Override
    public Object getGroup(int groupPosition) {
        return groupItems.get(groupPosition);
    }

    /**
     * On ajoute les paramètres.
     *
     * @return
     */

    @Override
    public int getGroupCount() {
        return this.groupItems.size();
    }

    /**
     * On ajoute les paramètres.
     *
     * @param groupPosition
     * @return
     */

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * On ajoute les paramètres.
     *
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */

    @Override
    public View getGroupView(final int groupPosition,
                             final boolean isExpanded,
                             View convertView,
                             ViewGroup parent) {
        final AlimentaireModele modele = (AlimentaireModele) this.getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.liste_marchandise_dispo_group, parent, false);
        }

        // Fait afficher l'icône correspondant au bon type alimentaire à côté du texte
        String image = modele.getTypeAlimentaire();
        ImageView ivResCategorie = (ImageView) convertView.findViewById(R.id.iv_md_categorie);
        switch (image) {
            case "Surgelés":
                ivResCategorie.setImageResource(R.drawable.map_surgele);
                break;
            case "Fruits et Légumes":
                ivResCategorie.setImageResource(R.drawable.map_fruit_legume);
                break;
            case "Boulangerie":
                ivResCategorie.setImageResource(R.drawable.map_boulangerie);
                break;
            case "Produits laitiers":
                ivResCategorie.setImageResource(R.drawable.map_laitier);
                break;
            case "Viandes":
                ivResCategorie.setImageResource(R.drawable.map_viande);
                break;
            case "Non Périssable":
                ivResCategorie.setImageResource(R.drawable.map_non_perissable);
                break;
            default:
                ivResCategorie.setImageResource(R.drawable.map_non_comestible);
                break;
        }

        // Affiche le nom de la marchandise
        ((TextView) convertView.findViewById(R.id.tv_md_nom_marchandise)).setText(modele.getNom());
        // Affiche la description de la marchandise
        ((TextView) convertView.findViewById(R.id.tv_md_description)).setText(modele.getDescription());

        // On affiche la quantité + l'unité de la marchandise
        String quantiteString = modele.getQuantite().toString() + " " + modele.getUnite();
        ((TextView) convertView.findViewById(R.id.tv_md_qtee_marchandise)).setText(quantiteString);

        // Affiche la date de péremption, quand c'est valable.
        if (modele.getDatePeremption() != null) {
            DateFormat format = android.text.format.DateFormat.getLongDateFormat(this.context);
            String date = format.format(modele.getDatePeremption());
            ((TextView) convertView.findViewById(R.id.tv_md_date_marchandise)).setText(date);
        } else {
            ((TextView) convertView.findViewById(R.id.tv_md_date_marchandise)).setVisibility(View.INVISIBLE);
        }

        // Réserver la marchandise (pour les organismes seulement)
        ImageButton ibSupprimer = (ImageButton) convertView.findViewById(R.id.ib_md_ajouter);
        ibSupprimer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO: faire ce qui faut pour reserver une marchandise.
                Log.i("Boutton ajouté cliqué", "**********" + groupPosition);

                Toast.makeText(context, "Marchandise réservée",
                        Toast.LENGTH_LONG).show();
            }
        });



        return convertView;
    }


    /**
     * On ajoute les paramètres.
     *
     * @return
     */

    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * On ajoute les paramètres.
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    public void setGroupItems(ArrayList<AlimentaireModele> groupItems) {
        this.groupItems = groupItems;
    }

    public void setDetailsItems(ArrayList<AlimentaireModele> detailsItems) {
        this.detailsItems = detailsItems;
    }
}
