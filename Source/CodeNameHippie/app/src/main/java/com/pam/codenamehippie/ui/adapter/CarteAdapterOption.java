package com.pam.codenamehippie.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.OrganismeModele;
import com.pam.codenamehippie.ui.view.trianglemenu.TestDonneeCentre;

import java.util.ArrayList;

/**
 * Created by BEG-163 on 2016-01-18.
 * cette classe est cours de construction,une version modifiee de CarteOrganismeAdapter,
 * pour afficher les details de liste marchandise directement,pas besoin de cliquer sur le
 * groupview
 * pour les voir.
 */
public class CarteAdapterOption extends BaseExpandableListAdapter {

    Context context;
    OrganismeModele mOrganisme;
    ArrayList<AlimentaireModele> listedon = new ArrayList<>();
    int viewID;

    public CarteAdapterOption(Context context,
                              OrganismeModele mOrganisme,
                              ArrayList<AlimentaireModele> listedon,
                              int viewID) {
        this.context = context;
        this.mOrganisme = mOrganisme;
        this.listedon = listedon;
        this.viewID = viewID;
    }

    @Override
    public int getGroupCount() {
        return 1 + listedon.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int count;
        if (groupPosition == 0) {
            count = 2;
        } else {
            count = 1;
        }

        return count;
    }

    @Override
    public Object getGroup(int groupPosition) {
        Object info;
        if (groupPosition == 0) {
            info = mOrganisme.getNom();
        } else {

            info = listedon.get(groupPosition - 2);
        }

        return info;

    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Object info = null;

        if (groupPosition == 0) {

            switch (childPosition) {
                case 0:
                    info = mOrganisme.getAdresse();
                    break;
                case 1:
                    info = mOrganisme.getTelephone();
                    break;
            }

        } else {

            info = listedon.get(groupPosition - 2).getDescription();

        }
        return info;
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

        LinearLayout layout1 = new LinearLayout(context);
        layout1.setOrientation(LinearLayout.HORIZONTAL);

        ImageView logo = new ImageView(context);
        logo.setMinimumWidth(300);

        TextView textView = new TextView(context);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(20);
        textView.setPadding(15, 15, 15, 15);

        if (groupPosition == 0) {

            logo.setImageResource(R.drawable.adresse);

            textView.setText(mOrganisme.getAdresse().toFormattedString());
            layout1.addView(logo);
            layout1.addView(textView);

        } else {

            String typeDenree = listedon.get(groupPosition - 2).getTypeAlimentaire();

            if ((typeDenree.equals(TestDonneeCentre.Denree.TypeDenree.fruit_legume))) {
                logo.setImageResource(R.drawable.map_fruit_legume);
            } else if (typeDenree.equals(TestDonneeCentre.Denree.TypeDenree.viande)) {
                logo.setImageResource(R.drawable.map_viande);
            } else if (typeDenree.equals(TestDonneeCentre.Denree.TypeDenree.laitier)) {
                logo.setImageResource(R.drawable.map_laitier);
            } else if (typeDenree.equals(TestDonneeCentre.Denree.TypeDenree.surgele)) {
                logo.setImageResource(R.drawable.map_surgele);
            } else if (typeDenree.equals(TestDonneeCentre.Denree.TypeDenree.non_comestible)) {
                logo.setImageResource(R.drawable.map_non_comestible);
            } else if (typeDenree.equals(TestDonneeCentre.Denree.TypeDenree.boulangerie)) {
                logo.setImageResource(R.drawable.map_boulangerie);
            } else {
                logo.setImageResource(R.drawable.map_non_perissable);
            }

            logo.setMinimumWidth(300);

            TextView textView1 = new TextView(context);
            textView1.setTextColor(Color.BLACK);
            textView1.setTextSize(20);
            textView1.setWidth(200);
            textView1.setPadding(5, 17, 5, 17);
            textView1.setText(listedon.get(groupPosition - 2).getNom());

            TextView textView2 = new TextView(context);
            textView2.setTextColor(Color.BLACK);
            textView2.setTextSize(20);
            textView2.setWidth(200);
            textView2.setPadding(0, 17, 5, 17);
            textView2.setText(listedon.get(groupPosition - 2).getQuantite() +
                              listedon.get(groupPosition - 2).getUnite());

            TextView textView4 = new TextView(context);
            textView4.setTextColor(Color.BLACK);
            textView4.setTextSize(20);
            textView4.setWidth(400);
            textView4.setPadding(65, 17, 5, 17);
            textView4.setText((CharSequence) listedon.get(groupPosition - 2).getDatePeremption());
            layout1.addView(logo);
            layout1.addView(textView1);
            layout1.addView(textView2);
            layout1.addView(textView4);
        }

        return layout1;
    }

    @Override
    public View getChildView(int groupPosition,
                             final int childPosition,
                             boolean isLastChild,
                             View convertView,
                             ViewGroup parent) {

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        if (groupPosition == 0) {
            ImageView logo = new ImageView(context);
            logo.setMinimumWidth(300);
            TextView textView = new TextView(context);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(20);
            textView.setPadding(5, 17, 5, 5);

            switch (childPosition) {
                case 0:
                    logo.setImageResource(R.drawable.trademarks);
                    textView.setText(mOrganisme.getNom());
                    break;
                case 1:
                    logo.setImageResource(R.drawable.telephone2);
                    textView.setText(mOrganisme.getTelephone());
                    break;
            }

            layout.addView(logo);
            layout.addView(textView);

        } else {

            TextView textView1 = new TextView(context);
            textView1.setTextColor(Color.BLACK);
            textView1.setTextSize(20);
            textView1.setWidth(1000);
            textView1.setPadding(300, 17, 5, 17);
            textView1.setText(listedon.get(groupPosition - 2).getDescription());

            Button btn = new Button(context);
            if (viewID == R.id.marchandiseDisponible || viewID == R.id.main_carte_image) {
                btn.setText("Reserver");
                btn.setBackgroundColor(Color.GREEN);
            } else {
                btn.setText("Annuler");
                btn.setBackgroundColor(Color.RED);
            }

            btn.setPadding(5, 5, 5, 5);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }

            });
            layout.addView(textView1);
            layout.addView(btn);

        }

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
