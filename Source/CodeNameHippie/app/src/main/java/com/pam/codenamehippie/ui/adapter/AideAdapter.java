package com.pam.codenamehippie.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.pam.codenamehippie.R;

/**
 * Cette classe permet de faire le lien entre les composantes de l'interface utilisateur et
 * les données afin de relier les données aux vues correctement.
 */
public class AideAdapter extends BaseExpandableListAdapter {

    // Nombre de sections ou groupe
    private static final int GROUP_ITEMS_COUNT = 9;
    // Nombre d'item à afficher sous chaque section ou groupe
    private static final int CHILD_ITEMS_COUNT = 1;
    private Context context;

    public AideAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return GROUP_ITEMS_COUNT;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return CHILD_ITEMS_COUNT;
    }

    @Override
    public String getGroup(int groupPosition) {
        // Afficher les différentes sections de l'aide
        switch (groupPosition) {
            case 0:
                return this.context.getString(R.string.tv_aide_titre_denree);
            case 1:
                return this.context.getString(R.string.tv_aide_titre_menu);
            case 2:
                return this.context.getString(R.string.tv_aide_titre_organisme);
            case 3:
                return this.context.getString(R.string.tv_aide_titre_produits_disponibles);
            case 4:
                return this.context.getString(R.string.tv_aide_titre_mes_reservations);
            case 5:
                return this.context.getString(R.string.tv_aide_titre_faire_un_don);
            case 6:
                return this.context.getString(R.string.tv_aide_titre_mes_dons);
            case 7:
                return this.context.getString(R.string.tv_aide_titre_statistiques);
            case 8:
                return this.context.getString(R.string.tv_aide_titre_profil);
            default:
                return "bob";
        }
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {
        // Afficher les détails de chaque section de l'aide
        switch (groupPosition) {
            case 0:
                return this.context.getString(R.string.tv_aide_denree_detail);
            case 1:
                return this.context.getString(R.string.tv_aide_menu_detail);
            case 2:
                return this.context.getString(R.string.tv_aide_organisme_detail);
            case 3:
                return this.context.getString(R.string.tv_aide_produits_disponibles_detail);
            case 4:
                return this.context.getString(R.string.tv_aide_mes_reservations_detail);
            case 5:
                return this.context.getString(R.string.tv_aide_faire_un_don_detail);
            case 6:
                return this.context.getString(R.string.tv_aide_mes_dons_detail);
            case 7:
                return this.context.getString(R.string.tv_aide_statistiques_detail);
            case 8:
                return this.context.getString(R.string.tv_aide_profil_detail);
            default:
                return "bob";
        }
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
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = this.getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.activity_aide_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.tv_list_aide_group);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition,childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.activity_aide_detail, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.tv_list_aide_detail);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
