package com.pam.codenamehippie.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AlimentaireModeleDepot;
import com.pam.codenamehippie.modele.OrganismeModeleDepot;
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
        ((TextView) findViewById(R.id.textView7)).setTypeface(typeface);
        ((TextView) findViewById(R.id.textView11)).setTypeface(typeface);
        ((TextView) findViewById(R.id.textView13)).setTypeface(typeface);
        ((TextView) findViewById(R.id.textView15)).setTypeface(typeface);

        AlimentaireModeleDepot alimentaireModeleDepot =
                ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();
        UtilisateurModeleDepot utilisateurModeleDepot =
                ((HippieApplication) this.getApplication())
                        .getUtilisateurModeleDepot();
        alimentaireModeleDepot.peuplerLesListes();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AlimentaireModeleDepot alimentaireModeleDepot =
                ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();
        // TODO: le id est temporairement Hardcoder
        alimentaireModeleDepot.peuplerListeDon(4);
        alimentaireModeleDepot.peuplerListeDonDispo();
        OrganismeModeleDepot organismeModeleDepot =
                ((HippieApplication) this.getApplication()).getOrganismeModeleDepot();
        organismeModeleDepot.peuplerListeDonneur();

    }
}



