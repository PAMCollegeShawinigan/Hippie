package com.pam.codenamehippie.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.OrganismeModele;
import com.pam.codenamehippie.modele.UtilisateurModele;
import com.pam.codenamehippie.modele.depot.AlimentaireModeleDepot;
import com.pam.codenamehippie.modele.depot.DepotManager;
import com.pam.codenamehippie.modele.depot.FiltreDeListe;
import com.pam.codenamehippie.modele.depot.ObservateurDeDepot;
import com.pam.codenamehippie.ui.HippieActivity;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BEG-163 on 2016-01-18.
 * cette classe est cours de construction,une version modifiee de CarteOrganismeAdapter,
 * pour afficher les details de liste marchandise directement,pas besoin de cliquer sur le
 * groupview
 * pour les voir.
 */
public class CarteAdapterOption extends BaseExpandableListAdapter
        implements ObservateurDeDepot<AlimentaireModele> {

    /**
     *
     */
    public static final int LIST_TYPE_MARCHANDISE_DISPO = 0;
    public static final int LIST_TYPE_MARCHANDISE_RESERVEE = 1;

    /**
     * Nombre de child view destinées à afficher les info de l'organisme
     */
    private static final int ORGANISME_INFO_CHILD_COUNT = 1;
    private final HippieActivity activity;
    private final AlimentaireModeleDepot alimentaireModeleDepot;
    private final ViewSwitcher viewSwitcher;
    private final LayoutInflater inflater;
    private volatile List<AlimentaireModele> listedon = new ArrayList<>();
    private OrganismeModele organisme;
    private int listType = 0;
    private int orgId;

    public CarteAdapterOption(HippieActivity activity, int orgId) {
        this.activity = activity;
        this.orgId = orgId;
        this.inflater =
                ((LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        this.alimentaireModeleDepot =
                DepotManager.getInstance().getAlimentaireModeleDepot();
        this.viewSwitcher = ((ViewSwitcher) this.activity.findViewById(R.id.panel_view_switcher));
        if (this.viewSwitcher != null) {
            this.viewSwitcher.setInAnimation(this.activity, android.R.anim.fade_in);
            this.viewSwitcher.setOutAnimation(this.activity, android.R.anim.fade_out);
        }
    }

    public int getListType() {
        return this.listType;
    }

    public void setListType(int listType) {
        this.listType = listType;
    }

    public List<AlimentaireModele> getListedon() {
        return this.listedon;
    }

    public void setOrganisme(@Nullable OrganismeModele organisme) {
        this.organisme = organisme;
        this.notifyDataSetChanged();
        if (organisme != null) {
            Log.d("ORG", this.organisme.toString());
            switch (this.listType) {
                case LIST_TYPE_MARCHANDISE_DISPO:
                    this.alimentaireModeleDepot.setFiltreDeListe(null);
                    this.alimentaireModeleDepot.peuplerListeCarte(this.organisme.getId());
                    break;
                case LIST_TYPE_MARCHANDISE_RESERVEE:
                    this.alimentaireModeleDepot.setFiltreDeListe(new FiltreDeListe<AlimentaireModele>() {
                        @Override
                        public boolean appliquer(AlimentaireModele item) {
                            return item.getOrganisme().equals(CarteAdapterOption.this.organisme);
                        }
                    });
                    this.alimentaireModeleDepot.peuplerListeReservation(this.orgId);
                    break;
                default:
                    throw new IllegalStateException("Type de liste inconnu");
            }
        }

    }

    @Override
    public int getGroupCount() {
        return (this.organisme != null) ? 1 : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int count = 0;
        if (this.organisme != null) {
            count += ORGANISME_INFO_CHILD_COUNT;
        }
        if ((this.listedon != null) && (!this.listedon.isEmpty())) {
            count += this.listedon.size();
        }
        return count;
    }

    @Override
    public OrganismeModele getGroup(int groupPosition) {
        // On fait comme si on avait une liste d'un seul objet qui lance pas d'exception.
        // Vu qu'on gère juste un organisme à la fois, il me semble…
        return ((this.organisme != null) && (groupPosition == 0)) ? this.organisme : null;

    }

    @Override
    public AlimentaireModele getChild(int groupPosition, int childPosition) {
        int pos = (childPosition == 0) ? 0 : childPosition - ORGANISME_INFO_CHILD_COUNT;
        return (!this.listedon.isEmpty()) ? this.listedon.get(pos) : null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        OrganismeModele modele = this.getGroup(groupPosition);
        return (modele != null) ? modele.getId() : groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        AlimentaireModele modele = this.getChild(groupPosition, childPosition);
        return (modele != null) ? modele.getId() : childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition,
                             boolean isExpanded,
                             View convertView,
                             ViewGroup parent) {

        View group = convertView;
        if (group == null) {
            group = this.inflater.inflate(R.layout.liste_organisme_group, parent, false);
        }
        View v = group.findViewById(R.id.tv_org_nom_organisme);
        if ((v != null) && (v instanceof TextView)) {
            TextView textView = ((TextView) v);
            textView.setText(this.organisme.getNom());
        }
        return group;
    }

    @Override
    public View getChildView(int groupPosition,
                             int childPosition,
                             boolean isLastChild,
                             View convertView,
                             ViewGroup parent) {
        View row = convertView;
        @LayoutRes
        int resId = (childPosition < ORGANISME_INFO_CHILD_COUNT)
                    ? R.layout.liste_organisme_detail
                    : (this.listType == LIST_TYPE_MARCHANDISE_DISPO) ?
                      R.layout.liste_marchandise_dispo_group : R.layout.liste_reservations_row;

        if (row == null) {
            row = this.inflater.inflate(resId, parent, false);
        }
        AlimentaireModele modele = this.getChild(groupPosition, childPosition);
        ;
        switch (resId) {
            case R.layout.liste_organisme_detail:
                // Fait afficher l'adresse de l'entreprise
                String addresse = this.organisme.getAdresse().toFormattedString();
                ((TextView) row.findViewById(R.id.tv_org_adresse)).setText(addresse);
                // Fait afficher le nom de la personne contact de l'entreprise
                UtilisateurModele contact = this.organisme.getContact();
                TextView textView = ((TextView) row.findViewById(R.id.tv_org_personne_contact));
                if (textView != null) {
                    if (contact != null) {
                        textView.setVisibility(View.VISIBLE);
                        textView.setText(contact.getNomComplet());
                    } else {
                        textView.setVisibility(View.GONE);
                    }
                }
                textView = ((TextView) row.findViewById(R.id.tv_org_telephone));
                if (textView != null) {
                    if (contact != null) {
                        textView.setVisibility(View.VISIBLE);
                        textView.setText(this.organisme.getFormattedTelephone());
                    } else {
                        textView.setVisibility(View.GONE);
                    }
                }
                break;
            case R.layout.liste_marchandise_dispo_group:
                if (modele != null) {
                    // Fait afficher l'icône correspondant au bon type alimentaire à côté du texte
                    String image = modele.getTypeAlimentaire();
                    if (image != null) {
                        //FIXME : Envoyer type alimentaire.
                        ImageView ivResCategorie =
                                (ImageView) row.findViewById(R.id.iv_md_categorie);
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
                    }
                    // Affiche le nom de la marchandise
                    ((TextView) row.findViewById(R.id.tv_md_nom_marchandise)).setText(modele.getNom());
                    // Affiche la description de la marchandise
                    ((TextView) row.findViewById(R.id.tv_md_description)).setText(modele.getDescription());

                    // On affiche la quantité + l'unité de la marchandise
                    ((TextView) row.findViewById(R.id.tv_md_qtee_marchandise)).setText(modele.getQuantiteString());

                    // Affiche la date de péremption, quand c'est valable.
                    if (modele.getDatePeremption() != null) {
                        DateFormat format =
                                android.text.format.DateFormat.getLongDateFormat(this.activity);
                        String date = format.format(modele.getDatePeremption());
                        ((TextView) row.findViewById(R.id.tv_md_date_marchandise)).setText(date);
                    } else {
                        row.findViewById(R.id.tv_md_date_marchandise)
                           .setVisibility(View.INVISIBLE);
                    }
                    row.findViewById(R.id.ib_md_ajouter).setFocusable(false);
                    //FIXME: Arranger le bouton réserver, afin de les faire fonctionner
                }
                break;
            case R.layout.liste_reservations_row:
                // Fait afficher l'icône correspondant au bon type alimentaire à côté du texte
                String image = modele.getTypeAlimentaire();
                ImageView ivResCategorie = (ImageView) row.findViewById(R.id.iv_res_categorie);
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

                // Assigner les valeurs nom, description, quantités, unité et ajouter deux
                // ImageButton par
                // rangée selon le nombre d'items contenus dans l'ArrayList.
                ((TextView) row.findViewById(R.id.tv_res_nom_marchandise)).setText(modele.getNom());
                ((TextView) row.findViewById(R.id.tv_res_description)).setText(modele.getDescription());
                ((TextView) row.findViewById(R.id.tv_res_qtee_marchandise)).setText(modele.getQuantiteString());

                // Affiche la date de péremption.
                if (modele.getDatePeremption() != null) {
                    DateFormat format =
                            android.text.format.DateFormat.getLongDateFormat(this.activity);
                    String date = format.format(modele.getDatePeremption());
                    ((TextView) row.findViewById(R.id.tv_res_date_marchandise)).setText(date);
                } else {
                    ((TextView) row.findViewById(R.id.tv_res_date_marchandise)).setVisibility
                                                                                        (View.INVISIBLE);
                }

                //FIXME: Arranger bouton supprimer réservation et collecte, afin de les faires
                // fonctionner
                break;
            default:
                break;
        }
        return row;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return (this.getGroupCount() != 0);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return groupId + childId;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return groupId;
    }

    @Override
    public void surDebutDeRequete() {
        if (this.viewSwitcher != null) {
            this.viewSwitcher.showNext();
        }
    }

    @Override
    public void surChangementDeDonnees(List<AlimentaireModele> modeles) {
        this.listedon = modeles;
        this.notifyDataSetChanged();
    }

    @Override
    public void surFinDeRequete() {
        if (this.viewSwitcher != null) {
            this.viewSwitcher.showPrevious();
        }
    }

    @Override
    public void surErreur(IOException e) {

    }
}
