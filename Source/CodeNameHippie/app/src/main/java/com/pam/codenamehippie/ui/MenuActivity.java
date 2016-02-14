package com.pam.codenamehippie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import com.pam.codenamehippie.R;
import com.pam.codenamehippie.ui.view.trianglemenu.TriangleLayout;
import com.pam.codenamehippie.ui.view.trianglemenu.TriangleLayout.OnCenterClickListener;
import com.pam.codenamehippie.ui.view.trianglemenu.TriangleLayout.OnItemClickListener;
import com.pam.codenamehippie.ui.view.trianglemenu.TriangleLayout.OnItemSelectedListener;
import com.pam.codenamehippie.ui.view.trianglemenu.TriangleLayout.OnRotationFinishedListener;

public class MenuActivity extends HippieActivity implements OnItemSelectedListener,
                                                            OnItemClickListener,
                                                            OnRotationFinishedListener,
                                                            OnCenterClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.main_menu);

        //intégrer les polices sur le menu
//        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "opensans_light.ttf");
//        TextView myTextview = (TextView) findViewById(R.id.main_selected_textView);
//        myTextview.setTypeface(myTypeface);

        // Set listeners
        TriangleLayout triangleMenu = (TriangleLayout) this.findViewById(R.id.main_menu_triangle);
        triangleMenu.setOnItemSelectedListener(this);
        triangleMenu.setOnItemClickListener(this);
        triangleMenu.setOnRotationFinishedListener(this);
        triangleMenu.setOnCenterClickListener(this);

//        selectedTextView = (TextView) findViewById(R.id.main_selected_textView);
//        selectedTextView.setText(((TriangleImageView) triangleMenu
//                .getSelectedItem()).getName());

    }

    @Override
    public void onItemSelected(View view, String name) {
//        selectedTextView.setText(name);
//
//        switch (view.getId()) {
//            case R.id.main_profil_image:
//                // selection de image profil
//                break;
//            case R.id.main_organisme_image:
//                // selection de image carte des organismes
//                break;
//            case R.id.main_statistique_image:
//                // selection de image des statistiques
//                break;
//            case R.id.main_carte_image:
//                // selection de la carte
//                break;
//            case R.id.main_reservation_image:
//                // selection de les réservations
//                break;
//            case R.id.main_categorie_image:
//                // selection de les catégories
//                break;
//        }
    }

    @Override
    public void onItemClick(View view, String name) {
        Intent intent;

        switch (view.getId()) {
            case R.id.main_mes_dons_image:
                // clic sur image profil
                this.startActivity(new Intent(this, ListeMesDonsActivity.class));
                Toast.makeText(this.getApplicationContext(),
                               " Mes dons ",
                               Toast.LENGTH_SHORT
                              ).show();
                break;

            case R.id.main_organisme_image:
                intent = new Intent(this, ListeOrganismesActivity.class);
                this.startActivity(intent);

                // clic sur image carte des organismes
                Toast.makeText(this.getApplicationContext(),
                               " Liste des organismes ",
                               Toast.LENGTH_SHORT
                              ).show();
                break;

            case R.id.main_statistique_image:
                // clic sur image des statistiques
                this.startActivity(new Intent(this, Stats_Activity.class));
                Toast.makeText(this.getApplicationContext(),
                               " Statistiques ",
                               Toast.LENGTH_SHORT
                              ).show();

                break;

            case R.id.main_carte_image:
                // clic sur la carte
                intent = new Intent(this, MapsActivity.class);
                //intent.setFlags(view.getId());
                this.startActivity(intent);
                //startActivity(new Intent(this, MapsActivity.class));
                Toast.makeText(this.getApplicationContext(),
                               " Carte ",
                               Toast.LENGTH_SHORT
                              ).show();
                break;

            case R.id.main_reservation_image:
                // clic sur les réservations
                intent = new Intent(this, ListeMesReservationsActivity.class);
                //  intent.setFlags(view.getId());
                this.startActivity(intent);
                //  startActivity(new Intent(this, MapsActivity.class));
                Toast.makeText(this.getApplicationContext(),
                               " Mes réservations ",
                               Toast.LENGTH_SHORT
                              ).show();
                break;
            case R.id.plus:
                this.startActivity(new Intent(this, AjoutMarchandiseActivity.class));
                Toast.makeText(this.getApplicationContext(),
                               " Ajout d'une nouvelle marchandise ",
                               Toast.LENGTH_SHORT
                              ).show();
                // clic sur les catégories
                break;
        }
    }

    @Override
    public void onRotationFinished(View view, String name) {
        Animation animation = new RotateAnimation(0, 360, view.getWidth() / 2,
                                                  view.getHeight() / 2
        );
        animation.setDuration(250);
        view.startAnimation(animation);
    }

    @Override
    public void onCenterClick() {
        //FIXME: Faire Autre chose quand C'est la seule activité.
        this.finish();
    }

}
