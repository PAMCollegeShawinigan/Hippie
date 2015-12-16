package com.pam.codenamehippie.ui;

import android.os.Bundle;

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

    }
}



