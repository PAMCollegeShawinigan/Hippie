package com.pam.codenamehippie.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.MarchandiseModeleDepot;

public class MainActivity extends HippieActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        MarchandiseModeleDepot marchandiseModeleDepot =
                ((HippieApplication) this.getApplication()).getMarchandiseModeleDepot();
        marchandiseModeleDepot.peuplerLesListes();

        //style temporairement appliquer en Java avant l'implémentation du theme et des styles
        Typeface typeface = Typeface.createFromAsset(getAssets(), "opensans_regular.ttf");
        ((TextView) findViewById(R.id.textView5)).setTypeface(typeface);
        ((TextView) findViewById(R.id.textView10)).setTypeface(typeface);
        ((TextView) findViewById(R.id.textView17)).setTypeface(typeface);

        Typeface typeface2 = Typeface.createFromAsset(getAssets(), "opensans_light.ttf");
        ((TextView) findViewById(R.id.textView7)).setTypeface(typeface2);
        ((TextView) findViewById(R.id.textView11)).setTypeface(typeface2);
        ((TextView) findViewById(R.id.textView13)).setTypeface(typeface2);
        ((TextView) findViewById(R.id.textView15)).setTypeface(typeface2);
        ((TextView) findViewById(R.id.textView8)).setTypeface(typeface2);
        ((TextView) findViewById(R.id.textView9)).setTypeface(typeface2);
        ((TextView) findViewById(R.id.textView12)).setTypeface(typeface2);
        ((TextView) findViewById(R.id.textView14)).setTypeface(typeface2);
        ((TextView) findViewById(R.id.textView16)).setTypeface(typeface2);
        ((TextView) findViewById(R.id.textView18)).setTypeface(typeface2);
        ((TextView) findViewById(R.id.textView6)).setTypeface(typeface2);

    }
}



