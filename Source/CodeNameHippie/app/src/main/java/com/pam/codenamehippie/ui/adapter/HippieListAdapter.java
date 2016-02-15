package com.pam.codenamehippie.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import com.pam.codenamehippie.modele.depot.AlimentaireModeleDepot;
import com.pam.codenamehippie.ui.AjoutMarchandiseActivity;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.text.format.DateFormat.getLongDateFormat;

/**
 * Cette classe permet de faire le lien entre les composantes de l'interface utilisateur et
 * la source de données afin de relier les données aux vues correctement.
 */
public class HippieListAdapter extends BaseAdapter {

    private final Context context;
    private final AlimentaireModeleDepot depot;
    private volatile ArrayList<AlimentaireModele> items = new ArrayList<>();

    public HippieListAdapter(Context context, AlimentaireModeleDepot depot) {
        this.context = context;
        this.depot = depot;
    }

    public void setItems(ArrayList<AlimentaireModele> items) {
        this.items = items;
        this.notifyDataSetChanged();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final AlimentaireModele modele = this.getItem(position);
        if (row == null) {
            LayoutInflater inflater =
                    (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.liste_dons_row, parent, false);
        }

        // Fait afficher l'icône correspondant au bon type alimentaire à côté du texte
        String image = modele.getTypeAlimentaire();
        ImageView ivDonsCategorie = (ImageView) row.findViewById(R.id.iv_dons_categorie);
        switch (image) {
            case "Surgelés":
                ivDonsCategorie.setImageResource(R.drawable.map_surgele);
                break;
            case "Fruits et Légumes":
                ivDonsCategorie.setImageResource(R.drawable.map_fruit_legume);
                break;
            case "Boulangerie":
                ivDonsCategorie.setImageResource(R.drawable.map_boulangerie);
                break;
            case "Produits laitiers":
                ivDonsCategorie.setImageResource(R.drawable.map_laitier);
                break;
            case "Viandes":
                ivDonsCategorie.setImageResource(R.drawable.map_viande);
                break;
            case "Non Périssable":
                ivDonsCategorie.setImageResource(R.drawable.map_non_perissable);
                break;
            default:
                ivDonsCategorie.setImageResource(R.drawable.map_non_comestible);
                break;
        }

        // Assigner les valeurs nom, description, quantités, unité et ajouter deux ImageButton par
        // rangée selon le nombre d'items contenus dans l'ArrayList.
        ((TextView) row.findViewById(R.id.tv_dons_nom_marchandise)).setText(modele.getNom());
        ((TextView) row.findViewById(R.id.tv_dons_description_marchandise))
                .setText(modele.getDescription());
        ((TextView) row.findViewById(R.id.tv_dons_qtee_marchandise)).setText(modele.getQuantiteString());
        ImageButton ibDonSupprimer = (ImageButton) row.findViewById(R.id.ib_don_supprimer);
        ImageButton ibDonModifier = (ImageButton) row.findViewById(R.id.ib_dons_modifier);

        // Modifier un item de la liste liste_dons_row
        ibDonModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =
                        new Intent(HippieListAdapter.this.context, AjoutMarchandiseActivity.class);
                // Créer un bundle pour faire voyager les données vers AjoutMarchandiseActivity
                Bundle bundle = new Bundle();
                // Insérer les données aux bundle
                bundle.putString("nom", modele.getNom());
                bundle.putString("description", modele.getDescription());
                bundle.putString("quantite", modele.getQuantite().toString());
                bundle.putString("unite", modele.getUniteDeQuantite());
                bundle.putString("valeur", modele.getValeur().toString());
                bundle.putString("typeAlimentaire", modele.getTypeAlimentaire());
                bundle.putInt("id", modele.getId());
                if (modele.getDatePeremption() != null) {
                    Date date = modele.getDatePeremption();
                    DateFormat df = getLongDateFormat(HippieListAdapter.this.context);
                    bundle.putString("datePeremption", df.format(date));
                }
                intent.putExtras(bundle);
                HippieListAdapter.this.context.startActivity(intent);
            }
        });

        // Supprimer un item de la liste liste_dons_row
        ibDonSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Runnable showToast = new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(HippieListAdapter.this.context,
                                       R.string.msg_produit_supprime,
                                       Toast.LENGTH_LONG
                                      ).show();
                    }
                };
                // Confirmer la suppression du don
                // Pour sauver de la mémoire, on instancie un seul click listener pour les deux
                // bouton.
                DialogInterface.OnClickListener dialogOnClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        HippieListAdapter.this.depot.supprimerModele(modele,
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

                // Construction du message pour suppression d'un don
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(HippieListAdapter.this.context);
                builder.setMessage(R.string.msg_confirme_suppression)
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
}
