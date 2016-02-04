package com.pam.codenamehippie.ui;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AdresseModele;
import com.pam.codenamehippie.modele.OrganismeModele;
import com.pam.codenamehippie.modele.depot.AlimentaireModeleDepot;
import com.pam.codenamehippie.modele.depot.ObservateurDeDepot;
import com.pam.codenamehippie.modele.depot.OrganismeModeleDepot;
import com.pam.codenamehippie.ui.adapter.CarteAdapterOption;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends HippieActivity implements OnMapReadyCallback,
                                                            ExpandableListView.OnGroupClickListener,
                                                            SlidingUpPanelLayout.PanelSlideListener,
                                                            ObservateurDeDepot<OrganismeModele>,
                                                            OnMarkerClickListener {

    private static class PrepareMarkerAsyncTask
            extends AsyncTask<OrganismeModele, MarkerOptions, Void> {

        private final MapsActivity activity;
        LatLngBounds.Builder builder = LatLngBounds.builder();

        public PrepareMarkerAsyncTask(@NonNull MapsActivity activity) {
            super();
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            if (this.activity.map != null) {
                this.activity.map.clear();
            }
        }

        @Override
        protected Void doInBackground(OrganismeModele... organismes) {
            for (OrganismeModele organisme : organismes) {
                if (this.isCancelled()) {
                    break;
                }
                AdresseModele adresse = organisme.getAdresse();
                LatLng point = this.activity.getLocationFromAddress(adresse.toFormattedString());
                if (point != null) {
                    MarkerOptions marker = new MarkerOptions().position(point)
                                                              .title(organisme.getNom());
                    this.publishProgress(marker);
                    this.builder.include(point);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if ((this.activity.lastKnownLocation != null) && (this.builder != null)) {
                LatLng lastKnownLocationPoint =
                        new LatLng(this.activity.lastKnownLocation.getLatitude(),
                                   this.activity.lastKnownLocation.getLongitude()
                        );
                this.builder.include(lastKnownLocationPoint);
            }
            LatLngBounds bounds = (this.builder != null) ? this.builder.build() : null;
            CameraUpdate cameraUpdate = null;
            this.activity.cacherLaProgressbar();
            if (this.activity.lastKnownLocation != null) {
                LatLng lastKnownLocationPoint =
                        new LatLng(this.activity.lastKnownLocation.getLatitude(),
                                   this.activity.lastKnownLocation.getLongitude()
                        );
                CameraPosition position = CameraPosition.builder()
                                                        .target(lastKnownLocationPoint)
                                                        .zoom(12.5f)
                                                        .build();
                cameraUpdate = CameraUpdateFactory.newCameraPosition(position);
            } else if (bounds != null) {
                Float padding = 30.0f;
                TypedValue outValue = new TypedValue();
                if (this.activity.getTheme()
                                 .resolveAttribute(R.attr.actionBarSize, outValue, true)) {
                    DisplayMetrics metrics = new DisplayMetrics();
                    metrics.setToDefaults();
                    padding = outValue.getDimension(metrics);
                }

                cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds,
                                                                   this.activity.mapView.getWidth(),
                                                                   this.activity.mapView
                                                                           .getHeight(),
                                                                   padding.intValue()
                                                                  );
            }
            if (cameraUpdate != null) {
                this.activity.map.animateCamera(cameraUpdate);
            }
        }

        @Override
        protected void onProgressUpdate(MarkerOptions... markers) {
            for (MarkerOptions marker : markers) {
                this.activity.map.addMarker(marker);
            }
        }
    }

    private static final String TAG = MapsActivity.class.getSimpleName();
    private volatile ArrayList<OrganismeModele> listOrganisme = new ArrayList<>();
    private SlidingUpPanelLayout slidingLayout;
    private ExpandableListView expandableListView;
    private GoogleMap map;
    private Location lastKnownLocation;
    private RelativeLayout mapView;
    private CarteAdapterOption adapter;

    /**
     * preparer la carte google et des donnees.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_maps_plus);
        this.slidingLayout = (SlidingUpPanelLayout) this.findViewById(R.id.sliding_layout);
        this.slidingLayout.setAnchorPoint(0.6f);
        this.expandableListView = (ExpandableListView) this.findViewById(R.id.expandableListView);
        this.adapter = new CarteAdapterOption(this);
        this.expandableListView.setAdapter(this.adapter);
        // mettre le listener pour le click de group de l'expandablelistview
        this.expandableListView.setOnGroupClickListener(this);
        this.mapView = (RelativeLayout) this.findViewById(R.id.mapView);
        this.slidingLayout.setPanelSlideListener(this);
        // FIXME: Checker dans this.getIntent().getExtras() pour afficher les bonnes listes.
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        this.googleApiClient = new GoogleApiClient.Builder(this).useDefaultAccount()
                                                                .addConnectionCallbacks(this)
                                                                .addOnConnectionFailedListener(this)
                                                                .addApi(LocationServices.API)
                                                                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        OrganismeModeleDepot organismeModeleDepot =
                ((HippieApplication) this.getApplication()).getOrganismeModeleDepot();
        AlimentaireModeleDepot alimentaireModeleDepot =
                ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();
        organismeModeleDepot.ajouterUnObservateur(this);
        alimentaireModeleDepot.ajouterUnObservateur(this.adapter);
        organismeModeleDepot.peuplerListeDonneur();
    }

    @Override
    protected void onPause() {
        super.onPause();
        AlimentaireModeleDepot alimentaireModeleDepot =
                ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();
        OrganismeModeleDepot organismeModeleDepot =
                ((HippieApplication) this.getApplication()).getOrganismeModeleDepot();
        organismeModeleDepot.setFiltreDeListe(null);
        organismeModeleDepot.supprimerTousLesObservateurs();
        alimentaireModeleDepot.setFiltreDeListe(null);
        alimentaireModeleDepot.supprimerTousLesObservateurs();

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        PrepareMarkerAsyncTask task = new PrepareMarkerAsyncTask(this);
        task.execute(this.listOrganisme.toArray(new OrganismeModele[this.listOrganisme.size()]));
        this.map.setMyLocationEnabled(true);
        this.map.setBuildingsEnabled(true);
        this.map.getUiSettings().setMapToolbarEnabled(false);
        this.map.getUiSettings().setMyLocationButtonEnabled(true);
        this.map.setOnMarkerClickListener(this);
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
        Geocoder geocoder = new Geocoder(this);
        Log.d(TAG, strAddress);
        try {
            List<Address> address = geocoder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            if (location == null) {
                return null;
            }
            return new LatLng(location.getLatitude(), location.getLongitude());

        } catch (Exception ex) {
            Log.e(TAG, "Erreur au géocoding", ex);
        }

        return null;
    }

    public void onButtonClick(View v) {
        switch (v.getId()) {

            case R.id.marchandiseDisponible:
                // affiche denree disponible sur la carte

                Toast.makeText(this.getApplicationContext(),
                               " Denrées disponible ",
                               Toast.LENGTH_SHORT
                              ).show();
                //   map.clear();
                //  listOrganisme = TestDonneeCentre.prepareDonnees_disponible();
                // FIXME: Connecter alimentaireModeleDepot et partir une requete pour l'organisme
                break;

            case R.id.mesReservation:
                // affiche mes reservations sur la carte
                // just pour tester,deuxiem clique va causer planter.
                Toast.makeText(this.getApplicationContext(),
                               " Mes réservations ",
                               Toast.LENGTH_SHORT
                              ).show();
                //  map.clear();
                // FIXME: Connecter alimentaireModeleDepot et partir une requete pour l'organisme
                break;

            case R.id.listeMarchandise:
                this.startActivity(new Intent(this, ListeMarchandisesDisponiblesActivity.class));
                break;
            default:
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
    public void surDebutDeRequete() {
        this.afficherLaProgressBar();
    }

    @Override
    public void surChangementDeDonnees(ArrayList<OrganismeModele> modeles) {
        this.listOrganisme = modeles;
        SupportMapFragment mapFragment = (SupportMapFragment) this.getSupportFragmentManager()
                                                                  .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void surFinDeRequete() {

    }

    @Override
    public void surErreur(IOException e) {
        Log.e(TAG, "Erreur de dépot", e);

    }

    @Override
    public void onConnected(Bundle bundle) {
        super.onConnected(bundle);
        this.lastKnownLocation =
                LocationServices.FusedLocationApi.getLastLocation(this.googleApiClient);
    }

    @Override
    public void onPanelSlide(View view, float v) {
    }

    @Override
    public void onPanelCollapsed(View view) {
        this.mapView.setVisibility(View.VISIBLE);
        ExpandableListAdapter adapter = this.expandableListView.getExpandableListAdapter();
        if (adapter != null) {
            for (int i = adapter.getGroupCount(); i >= 0; i -= 1) {
                this.expandableListView.collapseGroup(i);
            }
        }
    }

    @Override
    public void onPanelExpanded(View view) {
        this.mapView.setVisibility(View.GONE);
    }

    @Override
    public void onPanelAnchored(View view) {
        this.mapView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPanelHidden(View view) {
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        OrganismeModele adapterOrganisme = null;
        Log.d(TAG, "Marker: " + marker.getTitle());
        for (OrganismeModele organisme : this.listOrganisme) {
            if (organisme.getNom().compareToIgnoreCase((marker.getTitle())) == 0) {
                adapterOrganisme = organisme;
                break;
            }
        }
        //  expandableListView.setAdapter(new
        // CarteOrganismeAdapter(MapsActivity.this,
        // mOrganisme, viewID));
        this.adapter.setOrganisme(adapterOrganisme);
        if (this.slidingLayout.getPanelState() == PanelState.ANCHORED ||
            this.slidingLayout.getPanelState() == PanelState.EXPANDED) {
            this.expandableListView.expandGroup(0, true);
        }
        CameraPosition position =
                CameraPosition.builder(MapsActivity.this.map.getCameraPosition())
                              .target(marker.getPosition())
                              .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(position);
        this.map.animateCamera(cameraUpdate);
        return true;
    }
}

