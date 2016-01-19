package com.pam.codenamehippie.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.AlimentaireModeleDepot;
import com.pam.codenamehippie.ui.HippieActivity;

import java.util.ArrayList;

/**
 * Cette classe permet de faire le lien entre les composantes de l'interface utilisateur et
 * la source de données afin de relier les données aux vues correctement.
 */
public class HippieListAdapter extends BaseAdapter {

    private final ArrayList<AlimentaireModele> items;
    private final Context context;
    private final AlimentaireModeleDepot depot;

    public HippieListAdapter(Context context, ArrayList<AlimentaireModele> items, AlimentaireModeleDepot depot) {
        this.context = context;
        this.items = items;
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
     * Méthode pour lier les données aux composantes de l'interface utilisateur.
     *
     * @param position
     * @param convertView
     * @param parent
     * @return row
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final AlimentaireModele modele = this.getItem(position);
        if (row == null) {
            LayoutInflater inflater =
                    (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.liste_dons_row, parent, false);
        }
        ((TextView) row.findViewById(R.id.tv_dons_nom_marchandise)).setText(modele.getNom());
        ((TextView) row.findViewById(R.id.tv_dons_description_marchandise)).setText(modele.getDescription());
        String quantiteString = modele.getQuantite().toString() + " " + modele.getUnite();
        ((TextView) row.findViewById(R.id.tv_dons_qtee_marchandise)).setText(quantiteString);
        ImageButton ibDonModifier = (ImageButton) row.findViewById(R.id.ib_dons_modifier);
        ibDonModifier.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            // TODO: faire ce qui faut pour modifier un produit.
                Log.i("Bouton modifier cliqué", "**********" + position);

                Toast.makeText(context, "Bouton modifier sélectionné",
                        Toast.LENGTH_LONG).show();
            }
        });

        // Supprimer un item de la liste
        ImageButton ibDonSupprimer = (ImageButton) row.findViewById(R.id.ib_don_supprimer);
        ibDonSupprimer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i("Bouton supprimer cliqué", "**********" + position);
                depot.supprimerModele(modele);
                Toast.makeText(context, "Produit supprimé de la liste",
                        Toast.LENGTH_LONG).show();
            }
        });
        return row;
    }
}
