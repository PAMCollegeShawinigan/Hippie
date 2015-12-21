package com.pam.codenamehippie.ui;

import android.os.Bundle;

import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AlimentaireModeleDepot;
import com.pam.codenamehippie.modele.UtilisateurModeleDepot;

public class MainActivity extends HippieActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        AlimentaireModeleDepot alimentaireModeleDepot =
                ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();
        UtilisateurModeleDepot utilisateurModeleDepot =
                ((HippieApplication) this.getApplication())
                        .getUtilisateurModeleDepot();
        alimentaireModeleDepot.peuplerLesListes();
        utilisateurModeleDepot.peuplerLeDepot();
    }
}



