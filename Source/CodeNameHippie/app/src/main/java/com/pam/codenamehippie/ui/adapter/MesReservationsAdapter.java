package com.pam.codenamehippie.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.AlimentaireModeleDepot;

import java.util.ArrayList;

/**
 * Created by Catherine on 2016-01-19.
 */
public class MesReservationsAdapter extends BaseAdapter {

    private final ArrayList<AlimentaireModele> items;
    private final Context context;
    private final AlimentaireModeleDepot depot;

    public MesReservationsAdapter(ArrayList<AlimentaireModele> items,
                                  Context context,
                                  AlimentaireModeleDepot depot) {
        this.items = items;
        this.context = context;
        this.depot = depot;
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

    /**
     * Donc, on crée une méthode pour lier les données aux composantes de l'interface utilisateur.
     *
     * @param position
     * @param convertView
     * @param parent
     * @return row
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        final AlimentaireModele modele = this.getItem(position);
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        return row;
    }
}
