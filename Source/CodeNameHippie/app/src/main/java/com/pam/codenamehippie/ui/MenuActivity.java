package com.pam.codenamehippie.ui;

import android.app.Activity;
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
   // public static final String ARG_LAYOUT = "layout";

    private TextView selectedTextView;
    private TextView selectedTextView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set content view by passed extra
        // Bundle extras = getIntent().getExtras();
        // int layoutId = extras.getInt(ARG_LAYOUT);
        this.setContentView(R.layout.main_circle_layout);

        //intégrer les polices sur le menu
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "opensans_light.ttf");
        TextView myTextview = (TextView)findViewById(R.id.main_selected_textView);
        myTextview.setTypeface(myTypeface);

        // Set listeners
        TriangleLayout triangleMenu = (TriangleLayout) findViewById(R.id.main_circle_layout);
        triangleMenu.setOnItemSelectedListener(this);
        triangleMenu.setOnItemClickListener(this);
        triangleMenu.setOnRotationFinishedListener(this);
        triangleMenu.setOnCenterClickListener(this);



        selectedTextView = (TextView) findViewById(R.id.main_selected_textView);
        selectedTextView.setText(((TriangleImageView) triangleMenu
                .getSelectedItem()).getName());



    }

    @Override
    public void onItemSelected(View view, String name) {
        selectedTextView.setText(name);

        switch (view.getId()) {
            case R.id.main_Profil_image:
                // selection de image profil
                break;
            case R.id.main_Organisme_image:
                // selection de image carte des organismes
                break;
            case R.id.main_Statistique_image:
                // selection de image des statistiques
                break;
            case R.id.main_carte_image:
                // selection de la carte
                break;
            case R.id.main_réservation_image:
                // selection de les réservations
                break;
            case R.id.main_catégorie_image:
                // selection de les catégories
                break;
        }
    }

    @Override
    public void onItemClick(View view, String name) {

        Toast.makeText(getApplicationContext(),
                " Nouvelle activitée ",
                Toast.LENGTH_SHORT).show();

        switch (view.getId()) {
            case R.id.main_Profil_image:
                // clic sur image profil
                break;
            case R.id.main_Organisme_image:
                // clic sur image carte des organismes
                break;
            case R.id.main_Statistique_image:
                // clic sur image des statistiques
                break;
            case R.id.main_carte_image:
                // clic sur la carte
                break;
            case R.id.main_réservation_image:
                // clic sur les réservations
                break;
            case R.id.main_catégorie_image:
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
        Toast.makeText(getApplicationContext(), "LE CENTRE A ÉTÉ CLICKÉ",
                Toast.LENGTH_SHORT).show();
    }


}
