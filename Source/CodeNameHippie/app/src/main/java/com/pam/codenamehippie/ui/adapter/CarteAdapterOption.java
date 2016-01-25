package com.pam.codenamehippie.ui.adapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pam.codenamehippie.R;
import com.pam.codenamehippie.ui.view.trianglemenu.TestDonneeCentre;


import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by BEG-163 on 2016-01-18.
 * cette classe est cours de construction,une version modifiee de CarteOrganismeAdapter,
 * pour afficher les details de liste marchandise directement,pas besoin de cliquer sur le groupview pour les voir.
 */
public class CarteAdapterOption extends BaseExpandableListAdapter {
    Context context;
    TestDonneeCentre.Organisme mOrganisme;
   int viewID;

    public CarteAdapterOption(Context context,TestDonneeCentre.Organisme mOrganisme,int viewID){
        this.context=context;
        this.mOrganisme=mOrganisme;
        this.viewID=viewID;
    }

    @Override
    public int getGroupCount() {
        return 2+mOrganisme.getListDenree().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int count;
        if (groupPosition == 0) {
            count = 2;
        } else if (groupPosition == 1) {
            count = 7;
        } else {
            count = 1;
        }
        return count;
    }

    @Override
    public Object getGroup(int groupPosition) {
        Object info;
        if (groupPosition == 0) {
            info = mOrganisme.getNomOrganisme();
        } else if (groupPosition == 1) {
            info = mOrganisme.getMapCollectTime();

        } else {

            info = mOrganisme.getListDenree().get(groupPosition-2);
        }

        return info;

    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Object info = null;


        if (groupPosition == 0) {

            switch (childPosition) {
                case 0:
                    info = mOrganisme.getAddresse();
                    break;
                case 1:
                    info = mOrganisme.getTelephone();
                    break;
            }
        } else if (groupPosition == 1) {
            switch (childPosition) {
                case 0:
                    info = mOrganisme.getMapCollectTime().get("lundi");
                    break;
                case 1:
                    info = mOrganisme.getMapCollectTime().get("mardi");
                    break;
                case 2:
                    info = mOrganisme.getMapCollectTime().get("mercredi");
                    break;
                case 3:
                    info = mOrganisme.getMapCollectTime().get("jeudi");
                    break;
                case 4:
                    info = mOrganisme.getMapCollectTime().get("vendredi");
                    break;
                case 5:
                    info = mOrganisme.getMapCollectTime().get("samdi");
                    break;
                case 6:
                    info = mOrganisme.getMapCollectTime().get("dimanche");
                    break;
            }

        } else {
//            switch (childPosition) {
//                case 0:
//                    String[] str = {"nom", "quantite", "date de peremption"};
//                    info = str;
//                    break;
//                default:
//                    info = mOrganisme.getListDenree().get(childPosition - 1);
//            }
            info = mOrganisme.getListDenree().get(groupPosition-2).getDescription();

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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

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

            textView.setText(mOrganisme.getAddresse());
            layout1.addView(logo);
            layout1.addView(textView);

        } else if (groupPosition == 1) {

            logo.setImageResource(R.drawable.horaire);
            final Calendar c = Calendar.getInstance();
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == 2) {
                //  textView.setText(mOrganisme.getMapCollectTime().get("lundi"));
                textView.setText("Collecte Aujourd'hui:" + ((HashMap) getGroup(groupPosition)).get("lundi"));
            } else if (dayOfWeek == 3) {
                textView.setText("Collecte Aujourd'hui:" + ((HashMap) getGroup(groupPosition)).get("mardi"));
            } else if (dayOfWeek == 4) {
                textView.setText("Collecte Aujourd'hui:" + ((HashMap) getGroup(groupPosition)).get("mercredi"));
            } else if (dayOfWeek == 5) {
                textView.setText("Collecte Aujourd'hui:" + ((HashMap) getGroup(groupPosition)).get("jeudi"));
            } else if (dayOfWeek == 6) {
                textView.setText("Collecte Aujourd'hui:" + ((HashMap) getGroup(groupPosition)).get("vendredi"));
            } else if (dayOfWeek == 7) {
                textView.setText("Collecte Aujourd'hui:" + ((HashMap) getGroup(groupPosition)).get("samdi"));
            } else {
                textView.setText("Collecte Aujourd'hui:" + ((HashMap) getGroup(groupPosition)).get("dimanche"));

            }
            layout1.addView(logo);
            layout1.addView(textView);

        } else {

            TestDonneeCentre.Denree.TypeDenree typeDenree = mOrganisme.getListDenree().get(groupPosition-2).getTypeDenree();

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
            textView1.setText(mOrganisme.getListDenree().get(groupPosition-2).getNomDenree());

            TextView textView2 = new TextView(context);
            textView2.setTextColor(Color.BLACK);
            textView2.setTextSize(20);
            textView2.setWidth(200);
            textView2.setPadding(0, 17, 5, 17);
            textView2.setText(mOrganisme.getListDenree().get(groupPosition-2).getQuantiteDenree()+mOrganisme.getListDenree().get(groupPosition-2).getTypeUnite());

            TextView textView4 = new TextView(context);
            textView4.setTextColor(Color.BLACK);
            textView4.setTextSize(20);
            textView4.setWidth(400);
            textView4.setPadding(65, 17, 5, 17);
            textView4.setText(mOrganisme.getListDenree().get(groupPosition-2).getDatePeremption());
            layout1.addView(logo);
            layout1.addView(textView1);
            layout1.addView(textView2);
            layout1.addView(textView4);
        }

        return layout1;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

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
                    textView.setText(mOrganisme.getNomOrganisme());
                    break;
                case 1:

                    logo.setImageResource(R.drawable.telephone2);
                    textView.setText(mOrganisme.getTelephone());
                    break;
            }

            layout.addView(logo);
            layout.addView(textView);

        } else if (groupPosition == 1) {
            TextView textViewDay = new TextView(context);
            textViewDay.setTextColor(Color.BLACK);
            textViewDay.setTextSize(20);
            textViewDay.setPadding(55, 17, 5, 5);
            textViewDay.setWidth(300);

            TextView textViewTime = new TextView(context);
            textViewTime.setTextColor(Color.BLACK);
            textViewTime.setTextSize(20);
            textViewTime.setPadding(95, 17, 5, 5);
            textViewTime.setWidth(300);
            switch (childPosition) {

                case 0:
                    textViewTime.setText("lundi");
                    textViewDay.setText(mOrganisme.getMapCollectTime().get("lundi"));
                    break;
                case 1:
                    textViewTime.setText("mardi");
                    textViewDay.setText(mOrganisme.getMapCollectTime().get("mardi"));
                    break;
                case 2:
                    textViewTime.setText("mercredi");
                    textViewDay.setText(mOrganisme.getMapCollectTime().get("mercredi"));
                    break;
                case 3:
                    textViewTime.setText("jeudi");
                    textViewDay.setText(mOrganisme.getMapCollectTime().get("jeudi"));
                    break;
                case 4:
                    textViewTime.setText("vendredi");
                    textViewDay.setText(mOrganisme.getMapCollectTime().get("vendredi"));
                    break;
                case 5:
                    textViewTime.setText("samdi");
                    textViewDay.setText(mOrganisme.getMapCollectTime().get("samdi"));
                    break;
                case 6:
                    textViewTime.setText("dimanche");
                    textViewDay.setText(mOrganisme.getMapCollectTime().get("dimanche"));
                    break;

            }

            layout.addView(textViewTime);    layout.addView(textViewDay);
        } else {


                TextView textView1 = new TextView(context);
                textView1.setTextColor(Color.BLACK);
                textView1.setTextSize(20);
                textView1.setWidth(1000);
                textView1.setPadding(300, 17, 5, 17);
                textView1.setText(mOrganisme.getListDenree().get(groupPosition-2).getDescription());


                Button btn = new Button(context);
                if(viewID==R.id.marchandiseDisponible||viewID==R.id.main_carte_image){
                    btn.setText("Reserver");
                    btn.setBackgroundColor(Color.GREEN);}
                else{
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
