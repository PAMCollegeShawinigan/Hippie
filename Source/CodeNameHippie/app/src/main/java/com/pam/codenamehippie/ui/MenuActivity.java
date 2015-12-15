package com.pam.codenamehippie.ui;

<<<<<<< Updated upstream
import android.app.Fragment;
import android.content.Context;
=======
import android.app.Activity;
>>>>>>> Stashed changes
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.pam.codenamehippie.R;
import com.pam.codenamehippie.ui.view.trianglemenu.TriangleLayout;
import com.pam.codenamehippie.ui.view.trianglemenu.TriangleImageView;
import com.pam.codenamehippie.ui.view.trianglemenu.TriangleLayout.OnCenterClickListener;
import com.pam.codenamehippie.ui.view.trianglemenu.TriangleLayout.OnItemClickListener;
import com.pam.codenamehippie.ui.view.trianglemenu.TriangleLayout.OnItemSelectedListener;
import com.pam.codenamehippie.ui.view.trianglemenu.TriangleLayout.OnRotationFinishedListener;

public class MenuActivity extends Activity implements OnItemSelectedListener,
        OnItemClickListener,
        OnRotationFinishedListener,
        OnCenterClickListener {

    private TextView selectedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< Updated upstream
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
  }
=======
>>>>>>> Stashed changes

        // Set content view by passed extra
        // Bundle extras = getIntent().getExtras();
        // int layoutId = extras.getInt(ARG_LAYOUT);
        this.setContentView(R.layout.main_circle_layout);

        //intégrer les polices sur le menu
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "opensans_light.ttf");
        TextView myTextview = (TextView)findViewById(R.id.main_selected_textView);
        myTextview.setTypeface(myTypeface);

        // Set listeners
<<<<<<< Updated upstream
        TriangleLayout triangleMenu = (TriangleLayout) getView().findViewById(R.id.main_menu_triangle);
=======
        TriangleLayout triangleMenu = (TriangleLayout) findViewById(R.id.main_circle_layout);
>>>>>>> Stashed changes
        triangleMenu.setOnItemSelectedListener(this);
        triangleMenu.setOnItemClickListener(this);
        triangleMenu.setOnRotationFinishedListener(this);
        triangleMenu.setOnCenterClickListener(this);

        selectedTextView = (TextView) findViewById(R.id.main_selected_textView);
        selectedTextView.setText(((TriangleImageView) triangleMenu
                .getSelectedItem()).getName());

<<<<<<< HEAD
<<<<<<< Updated upstream
        selectedTextView2 = (TextView) getView().findViewById(R.id.main_selected_textView2);
        selectedTextView2.setText(((TriangleImageView) triangleMenu
                .getSelectedItem()).getName());

=======
>>>>>>> origin/EricEtCatherine
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.main_menu, container, false);
=======
>>>>>>> Stashed changes
    }

    @Override
    public void onItemSelected(View view, String name) {
        selectedTextView.setText(name);

        switch (view.getId()) {
            case R.id.main_profil_image:
                // selection de image profil
                break;
            case R.id.main_organisme_image:
                // selection de image carte des organismes
                break;
            case R.id.main_statistique_image:
                // selection de image des statistiques
                break;
            case R.id.main_carte_image:
                // selection de la carte
                break;
            case R.id.main_reservation_image:
                // selection de les réservations
                break;
            case R.id.main_categorie_image:
                // selection de les catégories
                break;
        }
    }

    @Override
    public void onItemClick(View view, String name) {

<<<<<<< Updated upstream
        Toast.makeText(this.getActivity().getApplicationContext(),
                " Nouvelle activité ",
=======
        Toast.makeText(getApplicationContext(),
                " Nouvelle activitée ",
>>>>>>> Stashed changes
                Toast.LENGTH_SHORT).show();

        switch (view.getId()) {
            case R.id.main_profil_image:
                // clic sur image profil
                break;
            case R.id.main_organisme_image:
                // clic sur image carte des organismes
                break;
            case R.id.main_statistique_image:
                // clic sur image des statistiques
                break;
            case R.id.main_carte_image:
                // clic sur la carte
                break;
            case R.id.main_reservation_image:
                // clic sur les réservations
                break;
            case R.id.main_categorie_image:
                // clic sur les catégories
                break;
        }
    }

    @Override
    public void onRotationFinished(View view, String name) {
        Animation animation = new RotateAnimation(0, 360, view.getWidth() / 2,
                view.getHeight() / 2);
        animation.setDuration(250);
        view.startAnimation(animation);
    }

    @Override
    public void onCenterClick() {
<<<<<<< Updated upstream
        Toast.makeText(this.getActivity().getApplicationContext(), "LE CENTRE A ÉTÉ CLICKÉ",
=======
        Toast.makeText(getApplicationContext(), "activitée du centre",
>>>>>>> Stashed changes
                Toast.LENGTH_SHORT).show();
    }


}
