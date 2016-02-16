package com.pam.codenamehippie.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.OrganismeModele;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BEG-163 on 2016-01-18.
 * cette class est pour but de affichier les organismes communautaires
 */
public class CarteListeOrganismeAdapter extends BaseExpandableListAdapter {

    private final Context context;
    private volatile List<OrganismeModele> items = new ArrayList<>();

    public CarteListeOrganismeAdapter(Context context) {
        this.context = context;
        this.items = this.items;

    }

    public void setItems(List<OrganismeModele> listOrgan) {
        this.items = listOrgan;
        this.notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return this.items.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {

        return this.items.get(groupPosition);

    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return this.items.get(groupPosition);
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
        LinearLayout layout = new LinearLayout(this.context);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        ImageView logo = new ImageView(this.context);
        TextView textView = new TextView(this.context);
        textView.setText(this.items.get(groupPosition).getNom());

        layout.addView(logo);
        layout.addView(textView);
        textView.setTextSize(20);
        logo.setImageResource(R.drawable.trademarks);
        logo.setPadding(40, 0, 40, 0);
        return layout;

    }

    @Override
    public View getChildView(int groupPosition,
                             int childPosition,
                             boolean isLastChild,
                             View convertView,
                             ViewGroup parent) {

        LinearLayout layout = new LinearLayout(this.context);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        ImageView logo1 = new ImageView(this.context);

        TextView textView1 = new TextView(this.context);

        LinearLayout layout1 = new LinearLayout(this.context);
        layout1.setOrientation(LinearLayout.VERTICAL);

        LinearLayout layout2 = new LinearLayout(this.context);
        layout2.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout layout3 = new LinearLayout(this.context);
        layout3.setOrientation(LinearLayout.HORIZONTAL);

        ImageView logo2 = new ImageView(this.context);
        TextView textView2 = new TextView(this.context);
        layout2.addView(logo2);
        layout2.addView(textView2);

        ImageView logo3 = new ImageView(this.context);
        TextView textView3 = new TextView(this.context);
        layout3.addView(logo3);
        layout3.addView(textView3);

        layout1.addView(layout2);
        layout1.addView(layout3);

        layout.addView(logo1);
        layout.addView(textView1);
        layout.addView(layout1);
        logo1.setImageResource(R.drawable.adresse);
        logo1.setPadding(40, 0, 40, 0);
        logo2.setImageResource(R.drawable.personne_contact);
        logo3.setImageResource(R.drawable.telephone2);
        textView1.setText(this.items.get(groupPosition).getAdresse().toFormattedString());
        String nomContact = this.items.get(groupPosition).getContact().getPrenom() +
                            " " +
                            this.items.get(groupPosition).getContact().getNom();
        textView2.setText(nomContact);
        textView3.setText(this.items.get(groupPosition).getFormattedTelephone());
        textView1.setTextSize(20);
        textView1.setMaxWidth(700);

        textView2.setTextSize(20);
        textView3.setTextSize(20);

        return layout;
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
}
