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

import com.pam.codenamehippie.R;
import com.pam.codenamehippie.controleur.ActionReservation;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.depot.AlimentaireModeleDepot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe permet de faire le lien entre les composantes de l'interface utilisateur et
 * la source de données afin de relier les données aux vues correctement.
 */
public class MesReservationsAdapter extends BaseAdapter {

    private final Context context;
    private final AlimentaireModeleDepot depot;
    private volatile List<AlimentaireModele> items = new ArrayList<>();

    public MesReservationsAdapter(Context context, AlimentaireModeleDepot depot) {
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
     * Méthode pour lier les données aux composantes de l'interface utilisateur.
     * Implémentation de {@link android.widget.Adapter#getView(int, View, ViewGroup)}
     *
     * @return Retourne une instance de {@link View} gonfler de liste_reservations_row.xml
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

        Log.i("ITEM_RES", "Count = " + this.items.size());
        // Assigner les valeurs nom, description, quantités, unité et ajouter deux ImageButton par
        // rangée selon le nombre d'items contenus dans l'ArrayList.
        ((TextView) row.findViewById(R.id.tv_res_nom_marchandise)).setText(modele.getNom());
        ((TextView) row.findViewById(R.id.tv_res_description)).setText(modele.getDescription());
        ((TextView) row.findViewById(R.id.tv_res_qtee_marchandise)).setText(modele.getQuantiteString());
        ImageButton ibSupprimerReservation = (ImageButton) row.findViewById(R.id.ib_res_supprimer);
        ImageButton ibCollecterReservation = (ImageButton) row.findViewById(R.id.ib_res_collecter);

        // Affiche la date de péremption.
        if (modele.getDatePeremption() != null) {
            DateFormat format = android.text.format.DateFormat.getLongDateFormat(this.context);
            String date = format.format(modele.getDatePeremption());
            ((TextView) row.findViewById(R.id.tv_res_date_marchandise)).setText(date);
        } else {
            ((TextView) row.findViewById(R.id.tv_res_date_marchandise)).setVisibility(View.INVISIBLE);
        }
        ActionReservation.instancier(ibSupprimerReservation, ibCollecterReservation, modele);

        return row;
    }

    public void setItems(List<AlimentaireModele> items) {
        this.items = items;
        this.notifyDataSetChanged();
    }

}
