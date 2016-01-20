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
        // Affiche un item personnalisé à la liste
        if (row == null) {
            LayoutInflater inflater =
                    (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.liste_reservations_row, parent, false);
        }

        // Fait afficher l'icône correspondant au bon type alimentaire à côté du texte
        String image = modele.getTypeAlimentaire();
        ImageView ivResCategorie = (ImageView) row.findViewById(R.id.iv_res_categorie);
        switch (image) {
            case "Surgelés":
                ivResCategorie.setImageResource(R.drawable.map_surgele);
                break;
            case "Fruits et Légumes":
                ivResCategorie.setImageResource(R.drawable.map_fruit_legume);
                break;
            case "Boulangerie":
                ivResCategorie.setImageResource(R.drawable.map_boulangerie);
                break;
            case "Produits laitiers":
                ivResCategorie.setImageResource(R.drawable.map_laitier);
                break;
            case "Viandes":
                ivResCategorie.setImageResource(R.drawable.map_viande);
                break;
            case "Non Périssable":
                ivResCategorie.setImageResource(R.drawable.map_non_perissable);
                break;
            default:
                ivResCategorie.setImageResource(R.drawable.map_non_comestible);
                break;
        }

        ((TextView) row.findViewById(R.id.tv_res_nom_marchandise)).setText(modele.getNom());
        ((TextView) row.findViewById(R.id.tv_res_description)).setText(modele.getDescription());

        // On affiche la quantité + l'unité de la marchandise
        String quantiteString = modele.getQuantite().toString() + " " + modele.getUnite();
        ((TextView) row.findViewById(R.id.tv_res_qtee_marchandise)).setText(quantiteString);

        // Faire afficher la date de péremption.
        // TODO: Faire afficher la date de réservation. Présentement, il y a un mélange entre Date de réservation et date de péremption.
        if (modele.getDatePeremption() != null) {
            DateFormat format = android.text.format.DateFormat.getLongDateFormat(this.context);
            String date = format.format(modele.getDatePeremption());
            ((TextView) row.findViewById(R.id.tv_res_date_marchandise)).setText(date);
        } else {
            ((TextView) row.findViewById(R.id.tv_res_date_marchandise)).setVisibility(View.INVISIBLE);
        }

        // Supprimer une réservation de la liste
        ImageButton ibSupprimer = (ImageButton) row.findViewById(R.id.ib_res_supprimer);
        ibSupprimer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO: faire ce qui faut pour supprimer une réservation de la liste. Ou bien mettre la réservation de Réservé à disponible.
                Log.i("Boutton supprimé cliqué", "**********" + position);

                /**
                 * String statut = modele.getStatut();
                 */




                Toast.makeText(context, "Réservation supprimé",
                        Toast.LENGTH_LONG).show();
            }
        });
        return row;
    }
}
