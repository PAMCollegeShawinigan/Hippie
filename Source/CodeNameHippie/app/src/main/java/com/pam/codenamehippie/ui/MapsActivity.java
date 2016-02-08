package com.pam.codenamehippie.ui;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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

    private static final class PrepareMarkerAsyncTask
            extends AsyncTask<OrganismeModele, MarkerOptions, LatLngBounds.Builder> {

        private final MapsActivity activity;
        private final int listeType;

        public PrepareMarkerAsyncTask(@NonNull MapsActivity activity) {
            super();
            this.activity = activity;
            this.listeType = this.activity.adapter.getListType();
        }

        public static PrepareMarkerAsyncTask newInstance(MapsActivity activity) {
            return new PrepareMarkerAsyncTask(activity);
        }

        @Override
        protected void onPreExecute() {
            synchronized (this.activity.mapLock) {
                if (this.activity.map != null) {
                    this.activity.map.clear();
                }
            }
        }

        @Override
        protected LatLngBounds.Builder doInBackground(OrganismeModele... organismes) {
            if ((this.isCancelled()) || (organismes.length == 0)) {
                return null;
            }
            synchronized (this.activity.mapLock) {
                while (this.activity.map == null) {
                    if (this.isCancelled()) {
                        return null;
                    }
                    try {
                        this.activity.mapLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            LatLngBounds.Builder boundsBuilder = LatLngBounds.builder();
            BitmapDescriptor dispoMarker =
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory
                                                                  .HUE_GREEN);
            BitmapDescriptor reserveeMarker =
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
            BitmapDescriptor defautMarker =
                    BitmapDescriptorFactory.defaultMarker();
            for (OrganismeModele organisme : organismes) {
                if (this.isCancelled()) {
                    break;
                }
                AdresseModele adresse = organisme.getAdresse();
                LatLng point = this.activity.getLocationFromAddress(adresse.toFormattedString());
                if (point != null) {
                    MarkerOptions marker = new MarkerOptions().position(point)
                                                              .title(organisme.getNom());
                    switch (this.listeType) {
                        case CarteAdapterOption.LIST_TYPE_MARCHANDISE_DISPO:
                            marker.icon(dispoMarker);
                            break;
                        case CarteAdapterOption.LIST_TYPE_MARCHANDISE_RESERVEE:
                            marker.icon(reserveeMarker);
                            break;
                        default:
                            marker.icon(defautMarker);
                            break;
                    }
                    this.publishProgress(marker);
                    boundsBuilder.include(point);

                }
            }

            return boundsBuilder;
        }

        @Override
        protected void onPostExecute(LatLngBounds.Builder boundsBuilder) {
            LatLngBounds bounds = (boundsBuilder != null) ? boundsBuilder.build() : null;
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
                int width = this.activity.mapView.getWidth();
                int height = this.activity.mapView.getHeight();
                int padding = 30;
                ActionBar actionBar = this.activity.getSupportActionBar();
                if (actionBar != null) {
                    padding = actionBar.getHeight();
                }
                cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
            }
            if (cameraUpdate != null) {
                this.activity.map.animateCamera(cameraUpdate);
            }
            this.activity.prepareMarkerAsyncTask = null;

        }

        @Override
        protected void onProgressUpdate(MarkerOptions... markers) {
            synchronized (this.activity.mapLock) {
                this.activity.map.addMarker(markers[0]);
            }
        }
    }

    private static final String TAG = MapsActivity.class.getSimpleName();
    private final Object mapLock = new Object();
    private volatile ArrayList<OrganismeModele> listOrganisme = new ArrayList<>();
    private SlidingUpPanelLayout slidingLayout;
    private ExpandableListView expandableListView;
    private volatile GoogleMap map;
    private volatile Location lastKnownLocation;
    private MapView mapView;
    private CarteAdapterOption adapter;
    private AsyncTask prepareMarkerAsyncTask;

    /**
     * preparer la carte google et des donnees.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_maps_plus);
        this.mapView = ((MapView) this.findViewById(R.id.map));
        this.mapView.onCreate(savedInstanceState);
        this.slidingLayout = (SlidingUpPanelLayout) this.findViewById(R.id.sliding_layout);
        this.slidingLayout.setAnchorPoint(0.6f);
        this.slidingLayout.setVisibility(View.GONE);
        this.expandableListView = (ExpandableListView) this.findViewById(R.id.expandableListView);
        this.adapter = new CarteAdapterOption(this);
        this.expandableListView.setAdapter(this.adapter);
        // mettre le listener pour le click de group de l'expandablelistview
        this.expandableListView.setOnGroupClickListener(this);
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
    protected void onResume() {
        super.onResume();
        this.mapView.onResume();
        OrganismeModeleDepot organismeModeleDepot =
                ((HippieApplication) this.getApplication()).getOrganismeModeleDepot();
        AlimentaireModeleDepot alimentaireModeleDepot =
                ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();
        organismeModeleDepot.ajouterUnObservateur(this);
        //comprend le ligne code dessous-------------------------------------
        alimentaireModeleDepot.ajouterUnObservateur(this.adapter);
        this.peuplerListeOrganisme(organismeModeleDepot);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.mapView.onPause();
        AlimentaireModeleDepot alimentaireModeleDepot =
                ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();
        OrganismeModeleDepot organismeModeleDepot =
                ((HippieApplication) this.getApplication()).getOrganismeModeleDepot();
        organismeModeleDepot.setFiltreDeListe(null);
        organismeModeleDepot.supprimerTousLesObservateurs();
        alimentaireModeleDepot.setFiltreDeListe(null);
        alimentaireModeleDepot.supprimerTousLesObservateurs();
        if (this.prepareMarkerAsyncTask != null) {
            this.prepareMarkerAsyncTask.cancel(true);
        }

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        this.mapView.onLowMemory();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        synchronized (this.mapLock) {
            this.map = googleMap;
            this.mapLock.notifyAll();
        }
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
        } else if (this.slidingLayout.getPanelState() == PanelState.ANCHORED) {
            this.slidingLayout.setPanelState(PanelState.EXPANDED);
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
        OrganismeModeleDepot organismeModeleDepot =
                ((HippieApplication) getApplication()).getOrganismeModeleDepot();

        switch (v.getId()) {

            case R.id.marchandiseDisponible:
                // affiche denree disponible sur la carte
                Toast.makeText(this.getApplicationContext(),
                               " Denrées disponible ",
                               Toast.LENGTH_SHORT
                              ).show();

                this.adapter.setOrganisme(null);
                this.adapter.setListType(CarteAdapterOption.LIST_TYPE_MARCHANDISE_DISPO);
                this.peuplerListeOrganisme(organismeModeleDepot);
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

                this.adapter.setOrganisme(null);
                this.adapter.setListType(CarteAdapterOption.LIST_TYPE_MARCHANDISE_RESERVEE);
                organismeModeleDepot.peuplerListeDonneur();
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
        OrganismeModele[] array = modeles.toArray(new OrganismeModele[modeles.size()]);
        this.prepareMarkerAsyncTask = PrepareMarkerAsyncTask.newInstance(this)
                                                            .execute(array);
    }

    @Override
    public void surFinDeRequete() {
        if (this.map == null) {
            this.mapView.getMapAsync(this);
        }
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

    public void peuplerListeOrganisme(OrganismeModeleDepot depot) {
        
        depot.peuplerListeDonneur();
    }
}

