package com.pam.codenamehippie.ui;

import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AlimentaireModeleDepot;
import com.pam.codenamehippie.modele.UtilisateurModeleDepot;

public class MainActivity extends HippieActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        Typeface typeface;
        typeface = Typeface.createFromAsset(getAssets(), "opensans_regular.ttf");
        ((TextView) findViewById(R.id.textView5)).setTypeface(typeface);
        ((TextView) findViewById(R.id.textView10)).setTypeface(typeface);
        ((TextView) findViewById(R.id.textView17)).setTypeface(typeface);

        Typeface typeface2;
        typeface2 = Typeface.createFromAsset(getAssets(), "opensans_light.ttf");
        ((TextView) findViewById(R.id.textView7)).setTypeface(typeface2);
        ((TextView) findViewById(R.id.textView11)).setTypeface(typeface2);
        ((TextView) findViewById(R.id.textView13)).setTypeface(typeface2);
        ((TextView) findViewById(R.id.textView15)).setTypeface(typeface2);

        AlimentaireModeleDepot alimentaireModeleDepot =
                ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();
        UtilisateurModeleDepot utilisateurModeleDepot =
                ((HippieApplication) this.getApplication())
                        .getUtilisateurModeleDepot();
        alimentaireModeleDepot.peuplerLesListes();
        // TODO: le id est temporairement Hardcoder

        utilisateurModeleDepot.peuplerLeDepot();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AlimentaireModeleDepot alimentaireModeleDepot =
                ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();
        alimentaireModeleDepot.peuplerListeDon(4);
    }
}



