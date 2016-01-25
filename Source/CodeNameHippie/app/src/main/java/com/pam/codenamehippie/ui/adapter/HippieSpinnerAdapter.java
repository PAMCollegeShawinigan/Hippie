package com.pam.codenamehippie.ui.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.pam.codenamehippie.modele.DescriptionModel;

import java.util.ArrayList;

/**
 * Cette classe permet de récupérer les descriptions contenues dans une ArrayList<DescriptionModele>
 * et, selon le type, les afficher à l'intérieur du Spinner correspondant.
 */
public class HippieSpinnerAdapter implements SpinnerAdapter {

    private final ArrayList<DescriptionModel> items;
    private final Context context;

    public HippieSpinnerAdapter(Context context, ArrayList<DescriptionModel> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        // On vérifie le type avant de faire un type cast. Ensuite, on affecte les descriptions
        // selon la position.
        CheckedTextView view =
                (convertView instanceof CheckedTextView) ? (CheckedTextView) convertView : null;
        if (view != null) {
            view.setText(this.getItem(position).getDescription());
        } else {
            LayoutInflater inflater =
                    (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = (CheckedTextView) inflater.inflate(android.R.layout.simple_spinner_dropdown_item,
                                                      parent,
                                                      false
                                                     );
            view.setText(this.getItem(position).getDescription());
        }
        return view;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public DescriptionModel getItem(int position) {
        return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.items.get(position).getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // On vérifie le type avant de faire un type cast. Ensuite, on affecte les descriptions
        // selon la position.
        TextView view = (convertView instanceof TextView) ? (TextView) convertView : null;
        if (view != null) {
            view.setText(this.getItem(position).getDescription());
        } else {
            LayoutInflater inflater =
                    (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = (TextView) inflater.inflate(android.R.layout.simple_spinner_item,
                                               parent,
                                               false
                                              );
            view.setText(this.getItem(position).getDescription());
        }
        return view;
    }

    @Override
    public int getItemViewType(int position) {
        return IGNORE_ITEM_VIEW_TYPE;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
