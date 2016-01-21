package com.pam.codenamehippie.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.AlimentaireModeleDepot;
import com.pam.codenamehippie.ui.adapter.HippieListAdapter;

import java.util.ArrayList;

/**
 * Cette classe permet de récupérer la liste des dons personnalisée selon l'id du donneur
 * et de l'afficher dans l'interface utilisateur.
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

        // Filtre pour récupérer les items dont le statut est Disponible ou Réservé
        ArrayList<AlimentaireModele> modeles = new ArrayList<>();
        for (AlimentaireModele modele : alimentaireModeleDepot.getListeDon()) {
            String statut = modele.getStatut();
            // FIXME: Utiliser ressource Sting pour le texte
            if (statut.equalsIgnoreCase("Disponible") || statut.equalsIgnoreCase("Réservé")){
                modeles.add(modele);
            }
        }

        this.mesDonsAdapter = new HippieListAdapter(this, modeles, alimentaireModeleDepot);
        listeMesDons = (ListView) findViewById(R.id.lv_dons);
        listeMesDons.setItemsCanFocus(false);
        listeMesDons.setAdapter(mesDonsAdapter);
        mesDonsAdapter.notifyDataSetChanged();

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
