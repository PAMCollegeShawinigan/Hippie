package com.pam.codenamehippie.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.AlimentaireModeleDepot;
import com.pam.codenamehippie.modele.FiltreDeListe;
import com.pam.codenamehippie.modele.ObservateurDeDepot;
import com.pam.codenamehippie.ui.adapter.HippieListAdapter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Cette classe permet de récupérer la liste des dons personnalisée selon l'id du donneur
 * et de l'afficher dans l'interface utilisateur.
 */
public class ListeMesDonsActivity extends HippieActivity implements ObservateurDeDepot<AlimentaireModele> {
    private ListView listeMesDons;
    private HippieListAdapter mesDonsAdapter;
    private ViewSwitcher viewSwitcher;
    private static final String TAG = ListeMesDonsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.liste_dons);
        viewSwitcher = ((ViewSwitcher) findViewById(R.id.main_view_switcher));
        this.viewSwitcher.setInAnimation(this, android.R.anim.fade_in);
        this.viewSwitcher.setOutAnimation(this, android.R.anim.fade_out);
        AlimentaireModeleDepot alimentaireModeleDepot =
                ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();


        ArrayList<AlimentaireModele> modeles = new ArrayList<>();
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

    @Override
    protected void onPause() {
        super.onPause();
        AlimentaireModeleDepot alimentaireModeleDepot =
                ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();
        alimentaireModeleDepot.setFiltreDeListe(null);
        alimentaireModeleDepot.supprimerToutLesObservateurs();

    }

    @Override
    protected void onResume() {
        super.onResume();
        AlimentaireModeleDepot alimentaireModeleDepot =
                ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();
        // TODO: Déplacer les deux ligne qui suivent dans l'activité de liste
        int orgId = this.sharedPreferences.getInt(this.getString(R.string.pref_org_id_key),
                -1
        );
        alimentaireModeleDepot.ajouterUnObservateur(this);
        // Filtre pour récupérer les items dont le statut est Disponible ou Réservé
        alimentaireModeleDepot.setFiltreDeListe(new FiltreDeListe<AlimentaireModele>() {
            @Override
            public boolean appliquer(AlimentaireModele item) {
                String statut = item.getStatut();
                return (statut.equalsIgnoreCase("Disponible") || statut.equalsIgnoreCase("Réservé"));
            }
        });
        alimentaireModeleDepot.peuplerListeDon(orgId);
    }


    @Override
    public void surDebutDeRequete() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewSwitcher.showNext();
            }
        });
    }

    @Override
    public void surChangementDeDonnees(final ArrayList<AlimentaireModele> modeles) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("test", "Count= " + modeles.size());
                mesDonsAdapter.setItems(modeles);
            }
        });

    }

    @Override
    public void surFinDeRequete() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewSwitcher.showPrevious();
            }
        });
    }

    @Override
    public void surErreur(IOException e) {
        //TODO: Toast
        Log.e(TAG, "Request failed", e);
    }
}
