package com.pam.codenamehippie.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
