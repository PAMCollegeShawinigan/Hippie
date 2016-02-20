package com.pam.codenamehippie.ui;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewSwitcher;

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
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.http.exception.HttpReponseException;
import com.pam.codenamehippie.modele.AdresseModele;
import com.pam.codenamehippie.modele.OrganismeModele;
import com.pam.codenamehippie.modele.UtilisateurModele;
import com.pam.codenamehippie.modele.depot.AlimentaireModeleDepot;
import com.pam.codenamehippie.modele.depot.DepotManager;
import com.pam.codenamehippie.modele.depot.ObservateurDeDepot;
import com.pam.codenamehippie.modele.depot.OrganismeModeleDepot;
import com.pam.codenamehippie.ui.adapter.CarteAdapterOption;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.ANCHORED;
import static com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.COLLAPSED;
import static com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.EXPANDED;
import static com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.HIDDEN;

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
            this.listeType = this.activity.adapter.getListeType();
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
                LatLng point = this.activity.getLocationFromName(adresse.string());
                if (point != null) {
                    MarkerOptions marker = new MarkerOptions().position(point)
                                                              .title(organisme.getNom());
                    switch (this.listeType) {
                        case CarteAdapterOption.LISTE_TYPE_MARCHANDISE_DISPO:
                            marker.icon(dispoMarker);
                            break;
                        case CarteAdapterOption.LISTE_TYPE_MARCHANDISE_RESERVEE:
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
                // On set la carte pour projeter sur la taille de l'écran.
                // C'est pas idéal, mais ça fait.
                int width = this.activity.getResources().getDisplayMetrics().widthPixels;
                int height = this.activity.getResources().getDisplayMetrics().heightPixels;
                int padding =
                        this.activity.getResources()
                                     .getDimensionPixelOffset(R.dimen.activity_vertical_margin) * 2;
                ActionBar bar = this.activity.getSupportActionBar();
                if (bar != null) {
                    padding += bar.getHeight();
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

    private static final int REQUEST_FINE_LOCATION = 0x10CA1;
    private final Object mapLock = new Object();
    private volatile List<OrganismeModele> listOrganisme = new ArrayList<>();
    private SlidingUpPanelLayout slidingLayout;
    private ExpandableListView expandableListView;
    private volatile GoogleMap map;
    private volatile Location lastKnownLocation;
    private MapView mapView;
    private RelativeLayout mapViewContainer;
    private CarteAdapterOption adapter;
    private AsyncTask prepareMarkerAsyncTask;
    private int orgId;
    private Geocoder geocoder;
    private Boolean hasFineLocation = true;
    private ViewSwitcher panelViewSwitcher;
    private int panelHeight;

    /**
     * preparer la carte google et des donnees.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_maps_plus);
        UtilisateurModele uc = this.authentificateur.getUtilisateur();
        OrganismeModele org = (uc != null) ? uc.getOrganisme() : null;
        this.orgId = (org != null) ? org.getId() : -1;
        this.geocoder = new Geocoder(this);
        this.panelViewSwitcher = ((ViewSwitcher) this.findViewById(R.id.panel_view_switcher));
        this.mapViewContainer = ((RelativeLayout) this.findViewById(R.id.mapView));
        this.mapView = ((MapView) this.findViewById(R.id.map));
        this.mapView.onCreate(savedInstanceState);
        this.slidingLayout = (SlidingUpPanelLayout) this.findViewById(R.id.sliding_layout);
        this.slidingLayout.setAnchorPoint(0.6f);
        this.panelHeight = this.slidingLayout.getPanelHeight();
        this.slidingLayout.setPanelState(HIDDEN);
        this.slidingLayout.setPanelSlideListener(this);
        this.expandableListView = (ExpandableListView) this.findViewById(R.id.expandableListView);
        // On fait disparaitre le chevron.
        this.expandableListView.setGroupIndicator(null);
        this.adapter = new CarteAdapterOption(this, this.orgId, this.panelViewSwitcher);
        this.expandableListView.setAdapter(this.adapter);
        // mettre le listener pour le click de group de l'expandablelistview
        this.expandableListView.setOnGroupClickListener(this);
        // FIXME: Checker dans this.getIntent().getExtras() pour afficher les bonnes listes.

        this.googleApiClient = new GoogleApiClient.Builder(this).useDefaultAccount()
                                                                .addConnectionCallbacks(this)
                                                                .addOnConnectionFailedListener(this)
                                                                .addApi(LocationServices.API)
                                                                .build();
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {
            // TODO: Expliquer la pourquoi on veux cette permission.
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_FINE_LOCATION)) {
//            } else {
            ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION},
                                              REQUEST_FINE_LOCATION);
//            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (this.permissionsResult != null) {
            switch (requestCode) {
                case REQUEST_FINE_LOCATION:
                    this.hasFineLocation = this.permissionsResult.get(ACCESS_FINE_LOCATION);
                    break;
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.mapView.onResume();
        OrganismeModeleDepot organismeModeleDepot =
                DepotManager.getInstance().getOrganismeModeleDepot();
        AlimentaireModeleDepot alimentaireModeleDepot =
                DepotManager.getInstance().getAlimentaireModeleDepot();
        alimentaireModeleDepot.ajouterUnObservateur(this.adapter);
        this.peuplerListeOrganisme(organismeModeleDepot);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.mapView.onPause();
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
        this.map.setMyLocationEnabled((this.hasFineLocation) || (this.lastKnownLocation != null));
        this.map.setBuildingsEnabled(true);
        this.map.getUiSettings().setMapToolbarEnabled(false);
        this.map.getUiSettings().setMyLocationButtonEnabled(true);
        this.map.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        if (!parent.isGroupExpanded(groupPosition)) {
            parent.expandGroup(groupPosition);
            this.slidingLayout.setPanelState(ANCHORED);
        } else if (this.slidingLayout.getPanelState() == ANCHORED) {
            this.slidingLayout.setPanelState(EXPANDED);
        } else {
            parent.collapseGroup(groupPosition);
            this.slidingLayout.setPanelState(PanelState.COLLAPSED);
        }
        return true;
    }

    /**
     * Méthode permettant de soumettre des requete géocoding. Retourne le premier résultat du
     * géocoder.
     *
     * @param name
     *         une string à chercher
     *
     * @return LatLng la longitude et latitude de l'addresse
     */
    public LatLng getLocationFromName(String name) {
        class Local { }
        String TAG = Local.class.getEnclosingMethod().getName();
        if (!Geocoder.isPresent()) {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Snackbar.make(MapsActivity.this.mapView,
                                  "Geocoder service unavailable",
                                  Snackbar.LENGTH_LONG
                    ).show();
                }
            });
            return null;
        }
        Log.d(TAG, name);
        try {
            List<Address> addresses = this.geocoder.getFromLocationName(name, 5);
            if ((addresses == null) || (addresses.size() == 0)) {
                Log.e(TAG, "Le géocoder a retourné aucun résultat pour " + name);
                return null;
            }
            Address location = addresses.get(0);
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
                DepotManager.getInstance().getOrganismeModeleDepot();

        switch (v.getId()) {

            case R.id.marchandiseDisponible:
                // affiche denree disponible sur la carte
                Toast.makeText(this.getApplicationContext(),
                               " Denrées disponible ",
                               Toast.LENGTH_SHORT
                ).show();
                this.adapter.setOrganisme(null);
                this.adapter.setListeType(CarteAdapterOption.LISTE_TYPE_MARCHANDISE_DISPO);
                this.peuplerListeOrganisme(organismeModeleDepot);
                break;

            case R.id.mesReservation:
                // affiche mes reservations sur la carte
                // just pour tester,deuxiem clique va causer planter.
                Toast.makeText(this.getApplicationContext(),
                               " Mes réservations ",
                               Toast.LENGTH_SHORT
                ).show();
                this.adapter.setOrganisme(null);
                this.adapter.setListeType(CarteAdapterOption.LISTE_TYPE_MARCHANDISE_RESERVEE);
                this.peuplerListeOrganisme(organismeModeleDepot);
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
    public void surChangementDeDonnees(List<OrganismeModele> modeles) {
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
        if (this.prepareMarkerAsyncTask == null) {
            this.cacherLaProgressbar();
        }
    }

    @Override
    public void surErreur(IOException e) {
        if (e instanceof HttpReponseException) {
            switch (((HttpReponseException) e).getCode()) {
                case 404:
                    //FIXME: Faire bon message.
                    Snackbar.make(this.mapView, R.string.error_http_404, Snackbar.LENGTH_LONG)
                            .show();
                    break;
                case 500:
                    Snackbar.make(this.mapView, R.string.error_http_500, Snackbar.LENGTH_LONG)
                            .show();
                    break;
                default:
                    Snackbar.make(this.mapView, R.string.error_connection, Snackbar.LENGTH_LONG)
                            .show();
                    break;
            }
        } else {
            Snackbar.make(this.mapView, R.string.error_connection, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        super.onConnected(bundle);
        if (this.hasFineLocation) {
            this.lastKnownLocation =
                    LocationServices.FusedLocationApi.getLastLocation(this.googleApiClient);
        }
    }

    @Override
    public void onPanelSlide(View view, float v) {
    }

    @Override
    public void onPanelCollapsed(View view) {
        this.mapViewContainer.setVisibility(VISIBLE);
        ExpandableListAdapter adapter = this.expandableListView.getExpandableListAdapter();
        if (adapter != null) {
            for (int i = adapter.getGroupCount(); i >= 0; i -= 1) {
                this.expandableListView.collapseGroup(i);
            }
        }
    }

    @Override
    public void onPanelExpanded(View view) {
        this.mapViewContainer.setVisibility(GONE);
    }

    @Override
    public void onPanelAnchored(View view) {
        this.mapViewContainer.setVisibility(VISIBLE);
    }

    @Override
    public void onPanelHidden(View view) {
        this.mapViewContainer.setVisibility(VISIBLE);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        OrganismeModele adapterOrganisme = null;
        for (OrganismeModele organisme : this.listOrganisme) {
            if (organisme.getNom().compareToIgnoreCase((marker.getTitle())) == 0) {
                adapterOrganisme = organisme;
                break;
            }
        }

        this.adapter.setOrganisme(adapterOrganisme);
        if (this.slidingLayout.getPanelState() == ANCHORED ||
            this.slidingLayout.getPanelState() == EXPANDED) {
            this.expandableListView.expandGroup(0, true);
        } else if (this.slidingLayout.getPanelState() == HIDDEN) {
            this.slidingLayout.setPanelState(COLLAPSED);
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
        switch (this.adapter.getListeType()) {
            case CarteAdapterOption.LISTE_TYPE_MARCHANDISE_DISPO:
                depot.peuplerListeDonneur();
                break;
            case CarteAdapterOption.LISTE_TYPE_MARCHANDISE_RESERVEE:
                if (this.orgId != -1) {
                    depot.peuplerListeDonneurReservation(this.orgId);
                } else {
                    Snackbar.make(this.mapView, "Non disponible", Snackbar.LENGTH_LONG).show();
                }
                break;
            default:
                throw new IllegalStateException("Type de liste inconnu");
        }
    }
}

