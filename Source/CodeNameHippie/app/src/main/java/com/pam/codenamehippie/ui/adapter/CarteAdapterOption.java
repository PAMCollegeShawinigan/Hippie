package com.pam.codenamehippie.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.OrganismeModele;
import com.pam.codenamehippie.modele.depot.AlimentaireModeleDepot;
import com.pam.codenamehippie.modele.depot.ObservateurDeDepot;
import com.pam.codenamehippie.ui.HippieActivity;

import java.io.IOException;
import java.util.ArrayList;

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
    private final HippieActivity context;
    private final AlimentaireModeleDepot alimentaireModeleDepot;
    private final LayoutInflater inflater;
    private volatile ArrayList<AlimentaireModele> listedon = new ArrayList<>();
    private OrganismeModele organisme;

    public CarteAdapterOption(HippieActivity context) {
        this.context = context;
        this.inflater =
                ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        this.alimentaireModeleDepot =
                ((HippieApplication) context.getApplication()).getAlimentaireModeleDepot();
    }

    public ArrayList<AlimentaireModele> getListedon() {
        return this.listedon;
    }

    public void setOrganisme(OrganismeModele organisme) {
        this.organisme = organisme;
        this.listedon.clear();
        this.notifyDataSetChanged();
        this.alimentaireModeleDepot.peuplerListeDon(this.organisme.getId());
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
                    : R.layout.liste_marchandise_dispo_group;
        if (row == null) {
            row = this.inflater.inflate(resId, parent, false);
        }
        switch (resId) {
            case R.layout.liste_organisme_detail:
                break;
            case R.layout.liste_marchandise_dispo_group:
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
        return false;
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

    }

    @Override
    public void surChangementDeDonnees(ArrayList<AlimentaireModele> modeles) {
        this.listedon = modeles;
        this.notifyDataSetChanged();
    }

    @Override
    public void surFinDeRequete() {

    }

    @Override
    public void surErreur(IOException e) {

    }
}
