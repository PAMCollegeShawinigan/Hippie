package com.pam.codenamehippie.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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
import com.pam.codenamehippie.modele.depot.AlimentaireModeleDepot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe permet de faire le lien entre les composantes de l'interface utilisateur et
 * la source de données afin de relier les données aux vues correctement.
 */
public class ListeMarchandisesDisponiblesAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<AlimentaireModele> groupItems = new ArrayList<>();
    private AlimentaireModeleDepot depot;

    public ListeMarchandisesDisponiblesAdapter(Context context,
                                               AlimentaireModeleDepot alimentaireDepot) {
        this.context = context;
        this.depot = alimentaireDepot;
    }

    /**
     * Ici, on ajoute les élements pour faire afficher la liste expendable
     * Donc, on affiche le layout : liste_marchandises_dispo_details, afin de faire afficher
     * l'adresse
     * et le nom de l'entreprise (du donneur en fait) qui donne la marchandise ciblée.
     * <p/>
     * <p/>
     * On ajoute les paramètres.
     *
     * @param groupPosition
     * @param childPosition
     *
     * @return
     */

    @Override
    public OrganismeModele getChild(int groupPosition, int childPosition) {
        return groupItems.get(groupPosition).getOrganisme();
    }

    /**
     * On ajoute les paramètres.
     *
     * @param groupPosition
     * @param childPosition
     *
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
     *
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
        OrganismeModele modele = this.getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.liste_marchandise_dispo_details, parent, false);
        }

        // Fait afficher le nom de l'entreprise
        ((TextView) convertView.findViewById(R.id.tv_md_nom_entreprise)).setText(modele.getNom());

        // Fait afficher l'adresse de l'entreprise
        String addresse = modele.getAdresse().toFormattedString();
        ((TextView) convertView.findViewById(R.id.tv_md_adresse_entreprise)).setText(addresse);
        return convertView;
    }

    /**
     * On ajoute les paramètres.
     *
     * @param groupPosition
     *
     * @return
     */

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    /**
     * Ici, on ajoute les élements pour faire afficher les informations de la marchandise.
     * On affiche donc le layout liste_marchandises_dispo_group
     * <p/>
     * <p/>
     * On ajoute les paramètres.
     *
     * @param groupPosition
     *
     * @return
     */

    @Override
    public AlimentaireModele getGroup(int groupPosition) {
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
     *
     * @return
     */

    @Override
    public long getGroupId(int groupPosition) {
        return this.getGroup(groupPosition).getId();
    }

    /**
     * On ajoute les paramètres.
     *
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     *
     * @return
     */

    @Override
    public View getGroupView(final int groupPosition,
                             final boolean isExpanded,
                             View convertView,
                             ViewGroup parent) {
        final AlimentaireModele modele = this.getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater =
                    (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =
                    infalInflater.inflate(R.layout.liste_marchandise_dispo_group, parent, false);
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
        ((TextView) convertView.findViewById(R.id.tv_md_qtee_marchandise)).setText(modele.getQuantiteString());

        // Affiche la date de péremption, quand c'est valable.
        if (modele.getDatePeremption() != null) {
            DateFormat format = android.text.format.DateFormat.getLongDateFormat(this.context);
            String date = format.format(modele.getDatePeremption());
            ((TextView) convertView.findViewById(R.id.tv_md_date_marchandise)).setText(date);
        } else {
            convertView.findViewById(R.id.tv_md_date_marchandise).setVisibility(View.INVISIBLE);
        }

        // Réserver la marchandise (pour les organismes seulement)
        ImageButton ibAjouter = (ImageButton) convertView.findViewById(R.id.ib_md_ajouter);
        ibAjouter.setFocusable(false);
        ibAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Runnable showToast = new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ListeMarchandisesDisponiblesAdapter.this.context,
                                       "Marchandise réservée",
                                       Toast.LENGTH_LONG
                                      ).show();
                    }
                };

                // Confirmer la collecte de la réservation
                // Pour sauver de la mémoire, on instancie un seul click listener pour les deux
                // bouton.
                DialogInterface.OnClickListener dialogOnClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        ListeMarchandisesDisponiblesAdapter.this.depot
                                                .ajouterReservation(
                                                        modele,
                                                        showToast
                                                                   );
                                        dialog.dismiss();
                                        break;
                                    default:
                                        dialog.dismiss();
                                        break;
                                }
                            }
                        };

                // Construction du message pour collecte d'une reservation
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(ListeMarchandisesDisponiblesAdapter.this.context);
                builder.setMessage("Êtes-vous sur de réserver ce produit ?")
                       .setPositiveButton(R.string.bouton_confirme_oui,
                                          dialogOnClickListener
                                         )
                       .setNegativeButton(R.string.bouton_confirme_non,
                                          dialogOnClickListener
                                         )
                       .create()
                       .show();
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
     *
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

    public void setGroupItems(List<AlimentaireModele> groupItems) {
        this.groupItems = groupItems;
        this.notifyDataSetChanged();
    }
}
