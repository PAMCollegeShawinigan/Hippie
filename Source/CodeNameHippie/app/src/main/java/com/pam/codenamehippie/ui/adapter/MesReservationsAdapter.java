package com.pam.codenamehippie.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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
 * Cette classe permet de faire le lien entre les composantes de l'interface utilisateur et
 * la source de données afin de relier les données aux vues correctement.
 */
public class MesReservationsAdapter extends BaseAdapter {

    private volatile ArrayList<AlimentaireModele> items = new ArrayList<>();
    private final Context context;
    private final AlimentaireModeleDepot depot;

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
         String quantiteString = modele.getQuantite().toString() + " " + modele.getUnite();
        ((TextView) row.findViewById(R.id.tv_res_qtee_marchandise)).setText(quantiteString);
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

        // Supprimer une réservation de la liste

        ibSupprimerReservation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Runnable showToast = new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MesReservationsAdapter.this.context,
                                R.string.msg_reservation_supprime, Toast.LENGTH_LONG).show();
                    }
                };
                // Confirmer la suppression de la réservation
                // Pour sauver de la mémoire, on instancie un seul click listener pour les deux
                // bouton.
                DialogInterface.OnClickListener dialogOnClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        MesReservationsAdapter.this.depot.annulerReservation(modele,
                                                showToast
                                        );
                                        dialog.dismiss();
                                        break;
                                    default:
                                        dialog.dismiss();
                                        break;
                                }
                            }
                        };

                // Construction du message pour suppression d'une réservation
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(MesReservationsAdapter.this.context);
                builder.setMessage(R.string.msg_reservation_confirme_suppression)
                        .setPositiveButton(R.string.bouton_confirme_oui,
                                dialogOnClickListener
                        )
                        .setNegativeButton(R.string.bouton_confirme_non,
                                dialogOnClickListener
                        )
                        .create()
                        .show();
            }
        });

        ibCollecterReservation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Runnable showToast = new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MesReservationsAdapter.this.context,
                                R.string.msg_reservation_collecte, Toast.LENGTH_LONG).show();
                    }
                };
                // Confirmer la collecte de la réservation
                // Pour sauver de la mémoire, on instancie un seul click listener pour les deux
                // bouton.
                DialogInterface.OnClickListener dialogOnClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        MesReservationsAdapter.this.depot.collecter(modele,
                                                showToast
                                        );
                                        dialog.dismiss();
                                        break;
                                    default:
                                        dialog.dismiss();
                                        break;
                                }
                            }
                        };

                // Construction du message pour collecte d'une reservation
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(MesReservationsAdapter.this.context);
                builder.setMessage(R.string.msg_reservation_confirme_collecte)
                        .setPositiveButton(R.string.bouton_confirme_oui,
                                dialogOnClickListener
                        )
                        .setNegativeButton(R.string.bouton_confirme_non,
                                dialogOnClickListener
                        )
                        .create()
                        .show();
            }
        });

        return row;
    }

    public void setItems(ArrayList<AlimentaireModele> items) {
        this.items = items;
        this.notifyDataSetChanged();
    }

}
