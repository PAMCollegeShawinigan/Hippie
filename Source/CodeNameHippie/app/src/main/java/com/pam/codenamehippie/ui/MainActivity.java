package com.pam.codenamehippie.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AlimentaireModeleDepot;
import com.pam.codenamehippie.modele.OrganismeModeleDepot;

public class MainActivity extends HippieActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        Typeface typeface;
        typeface = Typeface.createFromAsset(this.getAssets(), "opensans_regular.ttf");
        ((TextView) this.findViewById(R.id.textView5)).setTypeface(typeface);
        ((TextView) this.findViewById(R.id.textView10)).setTypeface(typeface);
        ((TextView) this.findViewById(R.id.textView17)).setTypeface(typeface);
        ((TextView) this.findViewById(R.id.textView7)).setTypeface(typeface);
        ((TextView) this.findViewById(R.id.textView11)).setTypeface(typeface);
        ((TextView) this.findViewById(R.id.textView13)).setTypeface(typeface);
        ((TextView) this.findViewById(R.id.textView15)).setTypeface(typeface);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AlimentaireModeleDepot alimentaireModeleDepot =
                ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();
        alimentaireModeleDepot.peuplerLesListes();
        // TODO: Déplacer les deux ligne qui suivent dans l'activité de liste
        int orgId = this.sharedPreferences.getInt(this.getString(R.string.pref_org_id_key),
                                                  -1
                                                 );
        alimentaireModeleDepot.peuplerListeDon(orgId);
        OrganismeModeleDepot organismeModeleDepot =
                ((HippieApplication) this.getApplication()).getOrganismeModeleDepot();
        organismeModeleDepot.peuplerListeDonneur();

    }
}



