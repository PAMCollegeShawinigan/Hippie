package com.pam.codenamehippie.ui;

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
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.ui.adapter.CarteOrganismeAdapter;
import com.pam.codenamehippie.ui.view.trianglemenu.TestDonneeCentre;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends HippieActivity implements OnMapReadyCallback, ExpandableListView.OnGroupClickListener {
    private SlidingUpPanelLayout slidingLayout;
    private ArrayList<Organisme> listOrganisme = new ArrayList<>();
    private ExpandableListView expandableListView;
    private int ordre;
    GoogleMap mMap;
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
        mapView.invalidate();
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
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    /**
     * preparer les donnees pour list marchandises reservees.
     * @return
     */


    /**
     * obtenir lattitude et longitude par string d'adresse .
     *
     * @param strAddress
     * @return LatLng
     */
    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng pоint = null;

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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final ArrayList<Organisme> listOrganisme = TestDonneeCentre.prepareDonnees_disponible();
        prepareMarkers(listOrganisme);

    }
    /**
     * selon list d'organisme,positionner les markers d'organisme,etablir le border de markers et set adapter et listener pour markers
     *
     * @param listOrganisme
     */
    private void prepareMarkers(final ArrayList<Organisme> listOrganisme) {
        ArrayList<LatLng> latLngList = new ArrayList<>();
        for (int i = 0; i < listOrganisme.size(); i++) {
            latLngList.add(getLocationFromAddress(listOrganisme.get(i).getAddresse()));
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
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

                                          @Override
                                          public boolean onMarkerClick(Marker marker) {

                                              for (int i = 0; i < listOrganisme.size(); i++) {
                                                  if (listMarker.get(i).equals(marker)) {
                                                      ordre = i;
                                                      break;
                                                  }
                                              }
                                              final Organisme mOrganisme = listOrganisme.get(ordre);
                                              expandableListView.setAdapter(new CarteOrganismeAdapter(MapsActivity.this, mOrganisme));
                                              return false;
                                          }
                                      }
        );
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
            return true;
        }
        return false;
    }

    public void onButtonClick(View v) {
        switch (v.getId()) {

            case R.id.marchandiseDisponible:
                // affiche denree disponible sur la carte

                Toast.makeText(this.getApplicationContext(),
                        " Denrées disponible ",
                        Toast.LENGTH_SHORT
                ).show();
                 mMap.clear();

                ArrayList<Organisme> listOrganisme1 = TestDonneeCentre.prepareDonnees_disponible();
              prepareMarkers(listOrganisme1);
                break;

            case R.id.mesReservation:
                // affiche mes reservations sur la carte
                // just pour tester,deuxiem clique va causer planter.
                Toast.makeText(this.getApplicationContext(),
                        " Mes réservations ",
                        Toast.LENGTH_SHORT
                ).show();
                  mMap.clear();
                ArrayList<Organisme> listOrganisme2 = TestDonneeCentre.prepareDonnees_reservees();
             prepareMarkers(listOrganisme2);

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
}

