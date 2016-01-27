package com.pam.codenamehippie.ui;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AdresseModele;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.ObservateurDeDepot;
import com.pam.codenamehippie.modele.OrganismeModele;
import com.pam.codenamehippie.modele.OrganismeModeleDepot;
import com.pam.codenamehippie.ui.adapter.CarteAdapterOption;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class MapsActivity extends HippieActivity
        implements OnMapReadyCallback,
                   ExpandableListView.OnGroupClickListener,
                   ObservateurDeDepot<OrganismeModele> {

    private SlidingUpPanelLayout slidingLayout;
    private ExpandableListView expandableListView;
    private ArrayList<OrganismeModele> listOrganisme = new ArrayList<>();
    private ArrayList<AlimentaireModele> listedon = new ArrayList<>();
    private Context context;
    private OkHttpClient httpClient;
    private int ordre;
    private Intent intent;
    private GoogleMap mMap;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    /**
     * preparer la carte google et des donnees.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_plus);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        slidingLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        slidingLayout.setAnchorPoint(0.6f);
        final RelativeLayout mapView = (RelativeLayout) findViewById(R.id.mapView);
        slidingLayout.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {

            @Override
            public void onPanelSlide(View view, float v) {
            }

            @Override
            public void onPanelCollapsed(View view) {
                mapView.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPanelExpanded(View view) {
                mapView.setVisibility(View.GONE);
                toolbar.setVisibility(View.GONE);
            }

            @Override
            public void onPanelAnchored(View view) {
            }

            @Override
            public void onPanelHidden(View view) {
            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        intent = getIntent();
        int viewID = intent.getFlags();

        if (viewID == R.id.main_carte_image) {
            /// listOrganisme = TestDonneeCentre.prepareDonnees_disponible();

        } else if (viewID == R.id.main_reservation_image) {
            // listOrganisme = TestDonneeCentre.prepareDonnees_reservees();

        } else {

            // listOrganisme=TestDonneeCentre.prepareDonnees_organismes();
        }

        prepareMarkers(listOrganisme, viewID);

//        switch (viewID){
//            case R.id.marchandiseDisponible:listOrganisme = TestDonneeCentre
// .prepareDonnees_disponible();
//               break;
//            case R.id.mesReservation: listOrganisme = TestDonneeCentre.prepareDonnees_reservees();
//                break;
//            default:break;
//        }
//        prepareMarkers(listOrganisme,viewID);
    }

    /**
     * selon list d'organisme,positionner les markers d'organisme,etablir le border de markers et
     * set adapter et listener pour markers
     *
     * @param listeOrganisme
     */
    private void prepareMarkers(final ArrayList<OrganismeModele> listeOrganisme, final int viewID) {
        if (mMap != null) {
            mMap.clear();
        }
        Log.d("Map", "LISTE ORGANISME: " + listeOrganisme.toString());
        ArrayList<LatLng> latLngList = new ArrayList<>();
        for (OrganismeModele organisme : listeOrganisme) {
            AdresseModele adresse = organisme.getAdresse();
            latLngList.add(getLocationFromAddress(adresse.toFormattedString()));
        }
        final ArrayList<Marker> listMarker = new ArrayList<>();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (int i = 0; i < latLngList.size(); i++) {
            listMarker.add(mMap.addMarker(new MarkerOptions().position(latLngList.get(i))));
            builder.include(latLngList.get(i));
        }
        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 600, 800, 1);
        mMap.moveCamera(cu);
        mMap.animateCamera(cu);
        this.mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                for (int i = 0; i < listeOrganisme.size(); i++) {
                    if (listMarker.get(i).equals(marker)) {
                        ordre = i;
                    }
                }
                final OrganismeModele mOrganisme = listeOrganisme
                                                           .get(ordre);

                //  expandableListView.setAdapter(new
                // CarteOrganismeAdapter(MapsActivity.this,
                // mOrganisme, viewID));
                expandableListView.setAdapter(new CarteAdapterOption(MapsActivity.this,
                                                                     mOrganisme,
                                                                     listedon,
                                                                     viewID
                ));
                if (slidingLayout.getPanelState() == PanelState.ANCHORED ||
                    slidingLayout.getPanelState() == PanelState.EXPANDED) {
                    expandableListView.expandGroup(0, true);
                }
                return true;
            }
        });
        // Pour désactiver les logo de googlemap
        mMap.getUiSettings().setMapToolbarEnabled(false);
        //  mMap.moveCamera(CameraUpdateFactory.newLatLng(shawiniganLatLng));
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        //mettre le listener pour group de l'expandablelistview
        expandableListView.setOnGroupClickListener(this);
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        if (!parent.isGroupExpanded(groupPosition)) {
            parent.expandGroup(groupPosition);
            this.slidingLayout.setPanelState(PanelState.ANCHORED);
        } else {
            parent.collapseGroup(groupPosition);
            this.slidingLayout.setPanelState(PanelState.COLLAPSED);
        }
        return true;
    }

    /**
     * obtenir lattitude et longitude par string d'adresse .
     *
     * @param strAddress
     *
     * @return LatLng
     */
    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng pоint = null;
        Log.d("MAP", strAddress);
        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);

            pоint = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (Exception ex) {

            ex.printStackTrace();
            Log.d("Wrong address", ex.toString());
        }

        return pоint;
    }

    public void onButtonClick(View v) {
        switch (v.getId()) {

            case R.id.marchandiseDisponible:
                // affiche denree disponible sur la carte

                Toast.makeText(this.getApplicationContext(),
                               " Denrées disponible ",
                               Toast.LENGTH_SHORT
                              ).show();
                //   mMap.clear();

                //  listOrganisme = TestDonneeCentre.prepareDonnees_disponible();
                prepareMarkers(listOrganisme, v.getId());
                break;

            case R.id.mesReservation:
                // affiche mes reservations sur la carte
                // just pour tester,deuxiem clique va causer planter.
                Toast.makeText(this.getApplicationContext(),
                               " Mes réservations ",
                               Toast.LENGTH_SHORT
                              ).show();
                //  mMap.clear();
                //  listOrganisme = TestDonneeCentre.prepareDonnees_reservees();
                prepareMarkers(listOrganisme, v.getId());

                break;

         /*   case R.id.main_liste_denree_disponible:
                // affiche les denrees disponible en liste
                Toast.makeText(this.getApplicationContext(),
                " Nouvelle activité ",
                Toast.LENGTH_SHORT
        ).show();
                break;
           */
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.pam.codenamehippie.ui/http/host/path")
                                            );
        // AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.pam.codenamehippie.ui/http/host/path")
                                            );
//        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        OrganismeModeleDepot depot =
                ((HippieApplication) this.getApplication()).getOrganismeModeleDepot();
        depot.setFiltreDeListe(null);
        depot.supprimerTousLesObservateurs();

    }

    @Override
    protected void onResume() {
        super.onResume();
        OrganismeModeleDepot depot =
                ((HippieApplication) this.getApplication()).getOrganismeModeleDepot();
        depot.ajouterUnObservateur(this);
        depot.peuplerListeOrganisme();

    }

    @Override
    public void surDebutDeRequete() {
        this.afficherLaProgressBar();
    }

    @Override
    public void surChangementDeDonnees(ArrayList<OrganismeModele> modeles) {
        this.listOrganisme = modeles;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                                                      .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void surFinDeRequete() {
        this.cacherLaProgressbar();
    }

    @Override
    public void surErreur(IOException e) {
        Log.e("carte", "Error", e);

    }
}

