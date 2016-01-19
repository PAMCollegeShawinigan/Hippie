package com.pam.codenamehippie.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.AlimentaireModeleDepot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;

        final AlimentaireModele modele = this.getItem(position);
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.liste_reservations_row, parent, false);
        }

        // TODO: Faire afficher l'icône correspondant au bon type alimentaire à côté du texte
       /* if(modele.getTypeAlimentaire()!= null) {
            String image = modele.getTypeAlimentaire();
            ImageView ivResCategorie = (ImageView) row.findViewById(R.id.iv_res_categorie);
            switch(image) {
                case "1":
                    ivResCategorie.setImageResource(R.drawable.surgele);
                    break;
                case "2":
                    ivResCategorie.setImageResource(R.drawable.fruit_legume);
                    break;
                case "3":
                    ivResCategorie.setImageResource(R.drawable.boulangerie);
                    break;
                case "4":
                    ivResCategorie.setImageResource(R.drawable.laitier);
                    break;
                case "5":
                    ivResCategorie.setImageResource(R.drawable.viande);
                    break;
                case "6":
                    ivResCategorie.setImageResource(R.drawable.non_perissable);
                    break;
                default:
                    ivResCategorie.setImageResource(R.drawable.non_comestible);
                    break;
            }
        } else {
            ((ImageView) row.findViewById(R.id.iv_res_categorie)).setImageResource(R.drawable.non_comestible);
        }*/


        //((ImageView) row.findViewById(R.id.iv_res_categorie)).setTransitionName(modele.getTypeAlimentaire());

        ((TextView) row.findViewById(R.id.tv_res_nom_marchandise)).setText(modele.getNom());
        ((TextView) row.findViewById(R.id.tv_res_description)).setText(modele.getDescription());

        // On affiche la quantité + l'unité de la marchandise
        String quantiteString = modele.getQuantite().toString() + " " + modele.getUnite();
        ((TextView) row.findViewById(R.id.tv_res_qtee_marchandise)).setText(quantiteString);

        // Faire afficher la date de péremption.
        if(modele.getDatePeremption() != null){
            DateFormat format = android.text.format.DateFormat.getLongDateFormat(this.context);
            String date = format.format(modele.getDatePeremption());
            ((TextView) row.findViewById(R.id.tv_res_date_marchandise)).setText(date);
        } else {
            ((TextView) row.findViewById(R.id.tv_res_date_marchandise)).setVisibility(View.INVISIBLE);
        }

        // Supprimer une réservation de la liste
        ImageButton ibSupprimer = (ImageButton) row.findViewById(R.id.ib_res_supprimer);
        ibSupprimer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO: faire ce qui faut pour supprimer une marchandise de la liste.
                Log.i("Boutton supprimé cliqué", "**********" + position);

                Toast.makeText(context, "Réservation supprimé de la liste",
                        Toast.LENGTH_LONG).show();
            }
        });
        return row;
    }
}
