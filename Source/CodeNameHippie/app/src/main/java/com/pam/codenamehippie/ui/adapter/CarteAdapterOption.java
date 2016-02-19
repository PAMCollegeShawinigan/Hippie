package com.pam.codenamehippie.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AdresseModele;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.OrganismeModele;
import com.pam.codenamehippie.modele.UtilisateurModele;
import com.pam.codenamehippie.modele.depot.AlimentaireModeleDepot;
import com.pam.codenamehippie.modele.depot.DepotManager;
import com.pam.codenamehippie.modele.depot.FiltreDeListe;
import com.pam.codenamehippie.modele.depot.ObservateurDeDepot;
import com.pam.codenamehippie.ui.HippieActivity;
import com.pam.codenamehippie.ui.view.ListeMarchandiseDispoGroupView;
import com.pam.codenamehippie.ui.view.ListeReservationsRowView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.pam.codenamehippie.ui.util.ViewUtils.afficherTexteOuMettreLaVueInvisible;

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
     * Nombre de child view destinées à afficher les info de l'organisme
     */
    private static final int ORGANISME_INFO_CHILD_COUNT = 1;

    /**
     * Constante pour indiquer d'afficher la liste de marchandise disponible
     *
     * @see CarteAdapterOption#setListeType(int)
     */
    public static final int LISTE_TYPE_MARCHANDISE_DISPO = (1 + ORGANISME_INFO_CHILD_COUNT) - 1;

    /**
     * Constante pour indiquer d'afficher la liste de marchandise disponible
     *
     * @see CarteAdapterOption#setListeType(int)
     */
    public static final int LISTE_TYPE_MARCHANDISE_RESERVEE = (2 + ORGANISME_INFO_CHILD_COUNT) - 1;

    private final HippieActivity activity;
    private final AlimentaireModeleDepot alimentaireModeleDepot;
    private final ViewSwitcher viewSwitcher;
    private final LayoutInflater inflater;
    private final int orgId;
    private volatile List<AlimentaireModele> listedon = new ArrayList<>();
    private OrganismeModele organisme;
    private int listeType = LISTE_TYPE_MARCHANDISE_DISPO;

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

    public int getListeType() {
        return this.listeType;
    }

    public void setListeType(int listeType) {
        this.listeType = listeType;
    }

    public List<AlimentaireModele> getListedon() {
        return this.listedon;
    }

    public void setOrganisme(@Nullable OrganismeModele organisme) {
        this.organisme = organisme;
        this.peuplerListe();
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
    public int getChildTypeCount() {
        return ORGANISME_INFO_CHILD_COUNT + 1;
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
    public int getChildType(int groupPosition, int childPosition) {
        if (childPosition < ORGANISME_INFO_CHILD_COUNT) {
            return childPosition;
        }
        return (this.listeType == LISTE_TYPE_MARCHANDISE_DISPO) ? LISTE_TYPE_MARCHANDISE_DISPO :
               LISTE_TYPE_MARCHANDISE_RESERVEE;
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
        return false;
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
        switch (this.getChildType(groupPosition, childPosition)) {
            case LISTE_TYPE_MARCHANDISE_DISPO: {
                AlimentaireModele modele = this.getChild(groupPosition, childPosition);
                if ((row == null) || !(row instanceof ListeMarchandiseDispoGroupView)) {
                    row = new ListeMarchandiseDispoGroupView(this.activity);
                }
                ((ListeMarchandiseDispoGroupView) row).afficherModele(modele);
                break;
            }
            case LISTE_TYPE_MARCHANDISE_RESERVEE: {
                AlimentaireModele modele = this.getChild(groupPosition, childPosition);
                if ((row == null) || !(row instanceof ListeReservationsRowView)) {
                    row = new ListeReservationsRowView(this.activity);
                }
                ((ListeReservationsRowView) row).afficherModele(modele);
                break;
            }
            default: {
                if (row == null) {
                    row = this.inflater.inflate(R.layout.liste_organisme_detail, parent, false);
                }
                AdresseModele adresse = this.organisme.getAdresse();
                UtilisateurModele contact = this.organisme.getContact();
                TextView v = ((TextView) row.findViewById(R.id.tv_org_telephone));
                afficherTexteOuMettreLaVueInvisible(v, this.organisme.getFormattedTelephone());
                String s = null;
                if (adresse != null) {
                    s = adresse.toFormattedString();
                }
                v = ((TextView) row.findViewById(R.id.tv_org_adresse));
                afficherTexteOuMettreLaVueInvisible(v, s);
                if (contact != null) {
                    s = contact.getNomComplet();
                }
                v = ((TextView) row.findViewById(R.id.tv_org_personne_contact));
                afficherTexteOuMettreLaVueInvisible(v, s);
                break;
            }
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

    private void peuplerListe() {
        if (this.organisme != null) {
            switch (this.listeType) {
                case LISTE_TYPE_MARCHANDISE_DISPO:
                    this.alimentaireModeleDepot.setFiltreDeListe(null);
                    this.alimentaireModeleDepot.peuplerListeCarte(this.organisme.getId());
                    break;
                case LISTE_TYPE_MARCHANDISE_RESERVEE:
                    this.alimentaireModeleDepot.setFiltreDeListe(
                            new FiltreDeListe<AlimentaireModele>() {
                                @Override
                                public boolean appliquer(AlimentaireModele item) {
                                    return item.getOrganisme()
                                               .equals(CarteAdapterOption.this.organisme);
                                }
                            });
                    this.alimentaireModeleDepot.peuplerListeReservation(this.orgId);
                    break;
                default:
                    throw new IllegalStateException("Type de liste inconnu");
            }
        } else {
            this.listedon = null;
            this.notifyDataSetChanged();
        }
    }
}
