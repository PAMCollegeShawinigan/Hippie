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
import com.pam.codenamehippie.modele.OrganismeModele;

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
    private ArrayList<AlimentaireModele> groupItems;
    private HashMap<OrganismeModele, ArrayList<AlimentaireModele>> detailsItems;

    /**
     * On ajoute les paramètres.
     *
     * @param context
     * @param groupItems
     * @param detailsItems
     */

    public ListeMarchandisesDisponiblesAdapter(Context context,
                                               ArrayList<AlimentaireModele> groupItems,
                                               HashMap<OrganismeModele, ArrayList<AlimentaireModele>> detailsItems) {
        this.context = context;
        this.groupItems = groupItems;
        this.detailsItems = detailsItems;
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
        return this.detailsItems.get(this.groupItems.get(groupPosition)).get(childPosition);
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
        final OrganismeModele modele =
                (OrganismeModele) this.getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.liste_marchandise_dispo_details, parent, false);
        }

        // Fait afficher le nom de l'entreprise
        ((TextView) convertView.findViewById(R.id.tv_md_nom_entreprise)).setText(modele.getNom());

        // Fait afficher l'adresse de l'entreprise
        // TODO: Arranger l'erreur du CharSequence, pour le moment, on le laisse commenté.
        // ((TextView) convertView.findViewById(R.id.tv_md_adresse_entreprise)).setText(modele.getAdresse());
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
        return this.detailsItems.get(this.groupItems.get(groupPosition)).size();
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
        return this.groupItems.get(groupPosition);
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
                             boolean isExpanded,
                             View convertView,
                             ViewGroup parent) {
        final AlimentaireModele modele = (AlimentaireModele) this.getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.liste_marchandise_dispo_group, null);
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
        return false;
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
}
