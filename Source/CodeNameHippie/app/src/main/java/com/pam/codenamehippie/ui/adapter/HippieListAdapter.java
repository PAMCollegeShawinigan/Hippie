package com.pam.codenamehippie.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AlimentaireModele;


import java.util.ArrayList;

/**
 * Created by Carl St-Louis le 2016-01-13.
 */
public class HippieListAdapter extends BaseAdapter {

    private final ArrayList<AlimentaireModele> items;
    private final Context context;

    public HippieListAdapter(Context context, ArrayList<AlimentaireModele> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public AlimentaireModele getItem(int position) {
        return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        AlimentaireModele modele = this.getItem(position);
        if (row == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.liste_dons_row, parent, false);
        }
        ((TextView) row.findViewById(R.id.tv_dons_nom_marchandise)).setText(modele.getNom());
        ((TextView) row.findViewById(R.id.tv_dons_description_marchandise)).setText(modele.getDescription());
        ((TextView) row.findViewById(R.id.tv_dons_qtee_marchandise)).setText(modele.getQuantite().toString() + " " + "Unités");
        //TODO: mettre l'unité marchandise (Lbs)

        return row;
    }
}
