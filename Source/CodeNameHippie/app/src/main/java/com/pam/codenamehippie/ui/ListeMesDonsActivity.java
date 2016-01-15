package com.pam.codenamehippie.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AlimentaireModeleDepot;
import com.pam.codenamehippie.ui.adapter.HippieListAdapter;

/**
 * Created by Carl St-Louis le 2016-01-14.
 */
public class ListeMesDonsActivity extends HippieActivity {
    ListView listeMesDons;
    HippieListAdapter mesDonsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.liste_dons);

        AlimentaireModeleDepot alimentaireModeleDepot =
                ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();
        this.mesDonsAdapter = new HippieListAdapter(this, alimentaireModeleDepot.getListeDon());

        listeMesDons = (ListView) findViewById(R.id.lv_dons);
        listeMesDons.setItemsCanFocus(false);
        listeMesDons.setAdapter(mesDonsAdapter);

        // peut être non nécessaire
        listeMesDons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    final int position, long id) {
                Log.i("List View Clicked", "**********");
                Toast.makeText(ListeMesDonsActivity.this,
                        "List View Clicked:" + position, Toast.LENGTH_LONG)
                        .show();
            }
        });

    }
}