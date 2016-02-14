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
import com.pam.codenamehippie.modele.depot.AlimentaireModeleDepot;
import com.pam.codenamehippie.modele.depot.FiltreDeListe;
import com.pam.codenamehippie.modele.depot.ObservateurDeDepot;
import com.pam.codenamehippie.ui.adapter.HippieListAdapter;

import java.io.IOException;
import java.util.List;

/**
 * Cette classe permet de récupérer la liste des dons personnalisée selon l'id du donneur
 * et de l'afficher dans l'interface utilisateur.
 */
public class ListeMesDonsActivity extends HippieActivity
        implements ObservateurDeDepot<AlimentaireModele> {

    private static final String TAG = ListeMesDonsActivity.class.getSimpleName();
    private HippieListAdapter mesDonsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.liste_dons);
        AlimentaireModeleDepot alimentaireModeleDepot =
                ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();

        this.mesDonsAdapter = new HippieListAdapter(this, alimentaireModeleDepot);
        ListView listeMesDons = (ListView) this.findViewById(R.id.lv_dons);
        listeMesDons.setItemsCanFocus(false);
        listeMesDons.setAdapter(this.mesDonsAdapter);
        this.mesDonsAdapter.notifyDataSetChanged();

        // peut être non nécessaire
        listeMesDons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Log.i("List View Clicked", "**********");
                Toast.makeText(ListeMesDonsActivity.this,
                               "List View Clicked:" + position, Toast.LENGTH_LONG
                              )
                     .show();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        AlimentaireModeleDepot alimentaireModeleDepot =
                ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();
        alimentaireModeleDepot.setFiltreDeListe(null);
        alimentaireModeleDepot.supprimerTousLesObservateurs();

    }

    @Override
    protected void onResume() {
        super.onResume();
        AlimentaireModeleDepot alimentaireModeleDepot =
                ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();
        int orgId = this.sharedPreferences.getInt(this.getString(R.string.pref_org_id_key),
                                                  -1
                                                 );
        alimentaireModeleDepot.ajouterUnObservateur(this);
        // Filtre pour récupérer les items dont le statut est Disponible ou Réservé
        alimentaireModeleDepot.setFiltreDeListe(new FiltreDeListe<AlimentaireModele>() {
            @Override
            public boolean appliquer(AlimentaireModele item) {
                String statut = item.getStatut();
                return (statut.equalsIgnoreCase("Disponible") ||
                        statut.equalsIgnoreCase("Réservé"));
            }
        });
        alimentaireModeleDepot.peuplerListeDon(orgId);
    }

    @Override
    public void surDebutDeRequete() {
        this.afficherLaProgressBar();
    }

    @Override
    public void surChangementDeDonnees(List<AlimentaireModele> modeles) {
        Log.d("test", "Count= " + modeles.size());
        this.mesDonsAdapter.setItems(modeles);

    }

    @Override
    public void surFinDeRequete() {
        this.cacherLaProgressbar();
    }

    @Override
    public void surErreur(IOException e) {
        //TODO: Toast
        Log.e(TAG, "Request failed", e);
    }
}
