package com.pam.codenamehippie.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AlimentaireModele;

import static com.pam.codenamehippie.ui.util.Math.convertirDpEnPixels;
import static com.pam.codenamehippie.ui.util.ViewUtils.afficherDateOuMettreLaVueInvisble;
import static com.pam.codenamehippie.ui.util.ViewUtils.afficherTexteOuMettreLaVueInvisible;

public class ListeReservationsRowView extends LinearLayout {

    private TextView nomMarchandise;
    private TextView description;
    private TextView quantite;
    private TextView date;
    private ImageView image;
    private AlimentaireModele modele;

    public ListeReservationsRowView(Context context) {
        super(context);
        this.init(context);
    }

    public AlimentaireModele getModele() {
        return this.modele;
    }

    private void init(Context context) {
        int v = convertirDpEnPixels(10);
        int defaultBottomPadding =
                (this.isPaddingOffsetRequired()) ? v + this.getBottomPaddingOffset() : v;
        int bottomPadding =
                (this.getPaddingBottom() > 0) ? this.getPaddingBottom() : defaultBottomPadding;
        this.setPadding(0, 0, 0, bottomPadding);
        LayoutInflater.from(context).inflate(R.layout.content_reservations_row, this, true);
        this.nomMarchandise = ((TextView) this.findViewById(R.id.tv_res_nom_marchandise));
        this.description = ((TextView) this.findViewById(R.id.tv_res_description));
        this.date = ((TextView) this.findViewById(R.id.tv_res_date_marchandise));
        this.quantite = ((TextView) this.findViewById(R.id.tv_res_qtee_marchandise));
        this.image = ((ImageView) this.findViewById(R.id.iv_res_categorie));
    }

    public void afficherModele(@NonNull AlimentaireModele item) {
        this.modele = item;
        afficherTexteOuMettreLaVueInvisible(this.nomMarchandise, this.modele.getNom());
        afficherTexteOuMettreLaVueInvisible(this.description, this.modele.getDescription());
        afficherTexteOuMettreLaVueInvisible(this.quantite, this.modele.getQuantiteString());
        afficherDateOuMettreLaVueInvisble(this.date,
                                          DateFormat.getLongDateFormat(this.getContext()),
                                          this.modele.getDatePeremption());
        String typeAlimentaire = this.modele.getTypeAlimentaire();
        if (typeAlimentaire != null) {
            switch (typeAlimentaire) {
                case "Surgelés":
                    this.image.setImageResource(R.drawable.map_surgele);
                    break;
                case "Fruits et Légumes":
                    this.image.setImageResource(R.drawable.map_fruit_legume);
                    break;
                case "Boulangerie":
                    this.image.setImageResource(R.drawable.map_boulangerie);
                    break;
                case "Produits laitiers":
                    this.image.setImageResource(R.drawable.map_laitier);
                    break;
                case "Viandes":
                    this.image.setImageResource(R.drawable.map_viande);
                    break;
                case "Non Périssable":
                    this.image.setImageResource(R.drawable.map_non_perissable);
                    break;
                default:
                    this.image.setImageResource(R.drawable.map_non_comestible);
                    break;
            }
        }
    }
}
