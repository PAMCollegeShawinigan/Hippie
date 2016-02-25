package com.pam.codenamehippie.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pam.codenamehippie.R;
import com.pam.codenamehippie.controleur.ActionMarchandiseDispo;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.OrganismeModele;

import static com.pam.codenamehippie.ui.util.Math.convertirDpEnPixels;
import static com.pam.codenamehippie.ui.util.ViewUtils.afficherDateOuMettreLaVueInvisble;
import static com.pam.codenamehippie.ui.util.ViewUtils.afficherTexteOuMettreLaVueInvisible;

public class ListeMarchandiseDispoGroupView extends LinearLayout {

    private TextView nomMarchandise;
    private TextView description;
    private TextView quantite;
    private TextView date;
    private ImageView image;
    private AlimentaireModele modele;
    private ImageButton imageButton;

    public ListeMarchandiseDispoGroupView(Context context) {
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
        LayoutInflater.from(context).inflate(R.layout.content_marchandise_dispo_group, this, true);
        this.nomMarchandise = ((TextView) this.findViewById(R.id.tv_md_nom_marchandise));
        this.description = ((TextView) this.findViewById(R.id.tv_md_description));
        this.date = ((TextView) this.findViewById(R.id.tv_md_date_marchandise));
        this.quantite = ((TextView) this.findViewById(R.id.tv_md_qtee_marchandise));
        this.image = ((ImageView) this.findViewById(R.id.iv_md_categorie));
        this.imageButton = ((ImageButton) this.findViewById(R.id.ib_md_ajouter));
    }

    public void afficherModele(@NonNull AlimentaireModele item,
                               @Nullable OrganismeModele receveur) {
        this.modele = item;
        afficherTexteOuMettreLaVueInvisible(this.nomMarchandise, this.modele.getNom());
        afficherTexteOuMettreLaVueInvisible(this.description, this.modele.getDescription());
        afficherTexteOuMettreLaVueInvisible(this.quantite, this.modele.getQuantiteString());
        afficherDateOuMettreLaVueInvisble(this.date,
                                          DateFormat.getLongDateFormat(this.getContext()),
                                          this.modele.getDatePeremption());
        if (receveur != null) {
            ActionMarchandiseDispo.instancier(this.imageButton, receveur, item);
        } else {
            this.imageButton.setVisibility(GONE);
        }
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
