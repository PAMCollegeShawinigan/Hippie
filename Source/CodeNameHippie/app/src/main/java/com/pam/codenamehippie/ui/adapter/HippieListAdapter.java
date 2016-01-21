package com.pam.codenamehippie.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.AlimentaireModeleDepot;
import com.pam.codenamehippie.ui.AjoutMarchandiseActivity;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Cette classe permet de faire le lien entre les composantes de l'interface utilisateur et
 * la source de données afin de relier les données aux vues correctement.
 */
public class HippieListAdapter extends BaseAdapter {

    private volatile ArrayList<AlimentaireModele> items;
    private final Context context;

    public void setItems(ArrayList<AlimentaireModele> items) {
        this.items = items;
        this.notifyDataSetChanged();
    }

    private final AlimentaireModeleDepot depot;


    public HippieListAdapter(Context context, ArrayList<AlimentaireModele> items,
                             AlimentaireModeleDepot depot) {
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
     * Implémentation de {@link android.widget.Adapter#getView(int, View, ViewGroup)}
     *
     * @return Retourne une instance de {@link View} gonfler de liste_dons_row.xml
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
        // Assigner les valeurs nom, description, quantités, unité et ajouter deux ImageButton par
        // rangée selon le nombre d'items contenus dans l'ArrayList.
        ((TextView) row.findViewById(R.id.tv_dons_nom_marchandise)).setText(modele.getNom());
        ((TextView) row.findViewById(R.id.tv_dons_description_marchandise)).setText(modele.getDescription());
        String quantiteString = modele.getQuantite().toString() + " " + modele.getUnite();
        ((TextView) row.findViewById(R.id.tv_dons_qtee_marchandise)).setText(quantiteString);
        ImageButton ibDonSupprimer = (ImageButton) row.findViewById(R.id.ib_don_supprimer);
        ImageButton ibDonModifier = (ImageButton) row.findViewById(R.id.ib_dons_modifier);

        // Modifier un item de la liste liste_dons_row
        ibDonModifier.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AjoutMarchandiseActivity.class);
                // Créer un bundle pour faire voyager les données vers AjoutMarchandiseActivity
                Bundle bundle = new Bundle();
                // Insérer les données aux bundle
                bundle.putString("nom", modele.getNom().toString());
                bundle.putString("description", modele.getDescription().toString());
                bundle.putString("quantite", modele.getQuantite().toString());
                bundle.putString("unite", modele.getUnite());
                bundle.putString("valeur", modele.getValeur().toString());
                bundle.putString("typeAlimentaire", modele.getTypeAlimentaire());
                bundle.putInt("id", modele.getId());
                if (modele.getDatePeremption() != null){
                    Date date = modele.getDatePeremption();
                    DateFormat df = android.text.format.DateFormat.getLongDateFormat(context);
                    bundle.putString("datePeremption", df.format(date));
                }
                intent.putExtras(bundle);
               context.startActivity(intent);
            }
        });

        // Supprimer un item de la liste liste_dons_row
        ibDonSupprimer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Confirmer la suppression du don
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage(R.string.msg_confirme_suppression);
                alertDialogBuilder.setPositiveButton(R.string.bouton_confirme_suppression_oui,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        depot.supprimerModele(modele);
                        Toast.makeText(context, R.string.msg_produit_supprime,
                                Toast.LENGTH_LONG).show();
                    }
                });
                alertDialogBuilder.setNegativeButton(R.string.bouton_confirme_suppression_non,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        return row;
    }
}
