package com.pam.codenamehippie.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.OrganismeModele;
import com.pam.codenamehippie.modele.depot.AlimentaireModeleDepot;

import java.util.ArrayList;

/**
 * Created by BEG-163 on 2016-01-18.
 * cette classe est cours de construction,une version modifiee de CarteOrganismeAdapter,
 * pour afficher les details de liste marchandise directement,pas besoin de cliquer sur le
 * groupview
 * pour les voir.
 */
public class CarteAdapterOption extends BaseExpandableListAdapter {

    /**
     * Nombre de child view destinées à afficher les info de l'organisme
     */
    private static final int ORGANISME_INFO_CHILD_COUNT = 1;
    private final Context context;
    private final AlimentaireModeleDepot alimentaireModeleDepot;
    private volatile ArrayList<AlimentaireModele> listedon = new ArrayList<>();
    private OrganismeModele organisme;

    public CarteAdapterOption(Context context,
                              AlimentaireModeleDepot depot) {
        this.context = context;
        this.alimentaireModeleDepot = depot;
    }

    public ArrayList<AlimentaireModele> getListedon() {
        return this.listedon;
    }

    public void setListedon(ArrayList<AlimentaireModele> listedon) {
        this.listedon = listedon;
        this.notifyDataSetChanged();
    }

    public void setOrganisme(OrganismeModele organisme) {
        this.organisme = organisme;
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
        return this.listedon.get((childPosition - 1));
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
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
            LayoutInflater inflater =
                    ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
            group = inflater.inflate(R.layout.liste_organisme_group, parent, false);
        }
        View v = group.findViewById(R.id.tv_org_nom_organisme);
        if ((v != null) && (v instanceof TextView)) {
            TextView textView = ((TextView) v);
            textView.setText("BOB");
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
        int resId;
        if (childPosition < ORGANISME_INFO_CHILD_COUNT) {
            resId = R.layout.liste_organisme_detail;
        } else {
            resId = R.layout.liste_dons_row;
        }

        return convertView;
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
}
