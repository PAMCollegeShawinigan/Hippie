package com.pam.codenamehippie.ui;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
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
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends HippieActivity implements OnMapReadyCallback, ExpandableListView.OnGroupClickListener {
    private SlidingUpPanelLayout slidingLayout;
    private ArrayList<Organisme> listOrganisme = new ArrayList<>();
    private ExpandableListView expandableListView;
    private int ordre;
    private LatLng shawiniganLatLng, montrealLatLng, troisriviereLatLng, jolietteLatLng, victoriavilleLatLng, quebecvilleLatLng;
    private ArrayList<Marker> listMarker;
    private ArrayList<LatLng> latLngList;
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

        //preparer les donnees pour tester
        // prepareDonnees();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
                latLngList = new ArrayList<>();
                for (int i = 0; i < listOrganisme.size(); i++) {
                    latLngList.add(getLocationFromAddress(listOrganisme.get(i).getAddresse()));
                }
                listMarker = new ArrayList<>();
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (int i = 0; i < latLngList.size(); i++) {
                    listMarker.add(mMap.addMarker(new MarkerOptions().position(latLngList.get(i))));
                    builder.include(latLngList.get(i));
                }
                break;

            case R.id.mesReservation:
                // affiche mes reservations sur la carte
                // just pour tester,deuxiem clique va causer planter.
                Toast.makeText(this.getApplicationContext(),
                        " Mes réservations ",
                        Toast.LENGTH_SHORT
                ).show();
                mMap.clear();
                listOrganisme.remove(5);
                listOrganisme.remove(0);
                listOrganisme.get(1).getListDenree().remove(0);
                latLngList = new ArrayList<>();
                for (int i = 0; i < listOrganisme.size(); i++) {
                    latLngList.add(getLocationFromAddress(listOrganisme.get(i).getAddresse()));
                }
                listMarker = new ArrayList<>();
                LatLngBounds.Builder builder1 = new LatLngBounds.Builder();
                for (int i = 0; i < latLngList.size(); i++) {
                    listMarker.add(mMap.addMarker(new MarkerOptions().position(latLngList.get(i))));
                    builder1.include(latLngList.get(i));
                }
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


    /**
     * obtenir les lattitudes et longitudes des entreprises,et d'autres donnees.
     */
    private ArrayList<Organisme> prepareDonnees() {

        // preparer des entreprise et leurs liste denrees
        ArrayList<Denree> listDenree1 = new ArrayList<>();
        listDenree1.add(new Denree("orange", "2", "kg","9999-12-31", TypeDenree.fruit_legume));
        listDenree1.add(new Denree("poulet", "3", "unite", "9999-12-31", TypeDenree.perissable));
        listDenree1.add(new Denree("prune", "13", "kg", "9999-12-31", TypeDenree.fruit_legume));
        listDenree1.add(new Denree("lait", "6", "litre", "9999-12-31", TypeDenree.laitier));
        HashMap<String, String> mapCollectTime1 = new HashMap<>();
        mapCollectTime1.put("lundi", "12:00-14:00");
        mapCollectTime1.put("mardi", "9:00-16:00");
        mapCollectTime1.put("mercredi", "8:00-13:00");
        mapCollectTime1.put("jeudi", "9:00-14:00");
        mapCollectTime1.put("vendredi", "9:00-17:00");
        mapCollectTime1.put("samdi", "9:00-13:00");
        mapCollectTime1.put("dimanche", "ferme");
        Organisme organisme1 = new Organisme("Maxi&Cie ", "6825 Chemin de la Cote-des-Neiges, Montreal,QC H3S 2B6", mapCollectTime1, "(514) 734-1280", listDenree1);

        ArrayList<Denree> listDenree2 = new ArrayList<Denree>();
        listDenree2.add(new Denree("pomme", "33", "kg","9999-12-31", TypeDenree.boulangerie));
        listDenree2.add(new Denree("avocat", "8", "unite", "9999-12-31", TypeDenree.fruit_legume));
        listDenree2.add(new Denree("rasin", "7", "kg", "9999-12-31", TypeDenree.fruit_legume));
        listDenree2.add(new Denree("patate", "21", "kg", "9999-12-31", TypeDenree.fruit_legume));
        HashMap<String, String> mapCollectTime2 = new HashMap<>();
        mapCollectTime2.put("lundi", "9:00-14:00");
        mapCollectTime2.put("mardi", "8:00-14:00");
        mapCollectTime2.put("mercredi", "8:00-18:00");
        mapCollectTime2.put("jeudi", "9:00-14:00");
        mapCollectTime2.put("vendredi", "10:00-17:00");
        mapCollectTime2.put("samdi", "9:00-13:00");
        mapCollectTime2.put("dimanche", "ferme");
        Organisme organisme2 = new Organisme("Rona", "7750 Boulevard des Hêtres, Shawinigan, QC G9N 4X4", mapCollectTime2, "(819) 537-3113", listDenree2);

        ArrayList<Denree> listDenree3 = new ArrayList<>();
        listDenree3.add(new Denree("croissant", "7", "unite","9999-12-31", TypeDenree.boulangerie));
        listDenree3.add(new Denree("mais", "40", "kg","9999-12-31", TypeDenree.perissable));
        listDenree3.add(new Denree("carrot", "41", "kg", "9999-12-31", TypeDenree.fruit_legume));
        HashMap<String, String> mapCollectTime3 = new HashMap<>();
        mapCollectTime3.put("lundi", "9:00-14:00");
        mapCollectTime3.put("mardi", "9:00-11:00");
        mapCollectTime3.put("mercredi", "8:00-14:00");
        mapCollectTime3.put("jeudi", "13:00-14:00");
        mapCollectTime3.put("vendredi", "9:00-17:00");
        mapCollectTime3.put("samdi", "9:00-13:00");
        mapCollectTime3.put("dimanche", "ferme");
        Organisme organisme3 = new Organisme("Maxi ", "909 Boulevard Firestone, Joliette, QC J6E 2W4", mapCollectTime3, "(450) 752-0088", listDenree3);

        ArrayList<Denree> listDenree4 = new ArrayList<>();
        listDenree4.add(new Denree("rasin", "37", "kg","9999-12-31", TypeDenree.fruit_legume));
        listDenree4.add(new Denree("tuna", "90", "kg", "9999-12-31", TypeDenree.surgele));
        HashMap<String, String> mapCollectTime4 = new HashMap<>();
        mapCollectTime4.put("lundi", "9:00-12:00");
        mapCollectTime4.put("mardi", "9:00-14:00");
        mapCollectTime4.put("mercredi", "10:00-14:00");
        mapCollectTime4.put("jeudi", "9:00-14:00");
        mapCollectTime4.put("vendredi", "9:00-16:00");
        mapCollectTime4.put("samdi", "9:00-13:00");
        mapCollectTime4.put("dimanche", "ferme");
        Organisme organisme4 = new Organisme("Marché IGA Bellevue ", " 560 Boulevard des Bois Francs S, Victoriaville, QC G6P 5X4", mapCollectTime4, "(819) 357-2241", listDenree4);

        ArrayList<Denree> listDenree5 = new ArrayList<>();
        listDenree5.add(new Denree("tuna", "34", "kg","9999-12-31", TypeDenree.surgele));
        listDenree5.add(new Denree("avocat", "27", "unite", "9999-12-31", TypeDenree.perissable));
        listDenree5.add(new Denree("orange", "56", "kg", "9999-12-31", TypeDenree.fruit_legume));
        listDenree5.add(new Denree("samon", "34", "kg","9999-12-31", TypeDenree.surgele));
        listDenree5.add(new Denree("beuf", "9", "kg", "9999-12-31", TypeDenree.viande));
        HashMap<String, String> mapCollectTime5 = new HashMap<>();
        mapCollectTime5.put("lundi", "9:00-10:00");
        mapCollectTime5.put("mardi", "11:00-14:00");
        mapCollectTime5.put("mercredi", "8:00-14:00");
        mapCollectTime5.put("jeudi", "9:00-16:00");
        mapCollectTime5.put("vendredi", "13:00-17:00");
        mapCollectTime5.put("samdi", "9:00-10:00");
        mapCollectTime5.put("dimanche", "ferme");
        Organisme organisme5 = new Organisme("Tigre Géant", " 800 Boulevard Thibeau, Trois-Rivières, QC G8T 7A6", mapCollectTime5, "(819) 697-3833", listDenree5);

        ArrayList<Denree> listDenree6 = new ArrayList<>();
        listDenree6.add(new Denree("tuna", "34", "kg", "9999-12-31", TypeDenree.surgele));
        listDenree6.add(new Denree("avocat", "27", "kg", "9999-12-31", TypeDenree.perissable));
        listDenree6.add(new Denree("orange", "23", "kg", "9999-12-31", TypeDenree.fruit_legume));
        listDenree6.add(new Denree("samon", "4", "kg","9999-12-31", TypeDenree.surgele));
        listDenree6.add(new Denree("beuf", "19", "kg", "9999-12-31", TypeDenree.viande));
        HashMap<String, String> mapCollectTime6 = new HashMap<>();
        mapCollectTime6.put("lundi", "9:00-10:00");
        mapCollectTime6.put("mardi", "11:00-14:00");
        mapCollectTime6.put("mercredi", "8:00-12:00");
        mapCollectTime6.put("jeudi", "9:00-16:00");
        mapCollectTime6.put("vendredi", "13:00-17:00");
        mapCollectTime6.put("samdi", "9:00-15:00");
        mapCollectTime6.put("dimanche", "ferme");
        Organisme organisme6 = new Organisme("Provigo", "4545 Boulevard Henri-Bourassa, Ville de Québec, QC G1H 7L9", mapCollectTime6, "(418) 622-7070", listDenree6);
        //preparer les entreprise affichees sur carte
        //  listOrganisme = new ArrayList<>();
        listOrganisme.add(organisme1);
        listOrganisme.add(organisme2);
        listOrganisme.add(organisme3);
        listOrganisme.add(organisme4);
        listOrganisme.add(organisme5);
        listOrganisme.add(organisme6);

        return listOrganisme;

    }

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
        //les LatLng infos sont obtenues pas webservice de googlemap,les entrees sont des addresses civiles.
        //les points cidessus sont seulement pour les tests.
        //ajouter les point sur carte
        listOrganisme = prepareDonnees();

        latLngList = new ArrayList<>();
        for (int i = 0; i < listOrganisme.size(); i++) {
            latLngList.add(getLocationFromAddress(listOrganisme.get(i).getAddresse()));
        }
        listMarker = new ArrayList<>();
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
//                                              expandableListView.setAdapter(new BaseExpandableListAdapter() {
//
//                                                  @Override
//                                                  public int getGroupCount() {
//                                                      return 3;
//                                                  }
//
//                                                  @Override
//                                                  public int getChildrenCount(int groupPosition) {
//                                                      int count;
//                                                      if (groupPosition == 0) {
//                                                          count = 2;
//                                                      } else if (groupPosition == 1) {
//                                                          count = 7;
//                                                      }
//                                                      else {
//                                                          count =mOrganisme.getListDenree().size();
//                                                      }
//                                                      return count;
//                                                  }
//
//                                                  @Override
//                                                  public Object getGroup(int groupPosition) {
//                                                      Object info ;
//                                                      if (groupPosition == 0) {
//                                                          info = mOrganisme.getNomOrganisme();
//                                                      } else if (groupPosition == 1) {
//                                                          info = mOrganisme.getMapCollectTime();
//
//                                                      } else {
//
//                                                          info = mOrganisme.getListDenree();
//                                                      }
//                                                      return info;
//                                                  }
//
//                                                  @Override
//                                                  public Object getChild(int groupPosition, int childPosition) {
//                                                      Object info = null;
//
//                                                      if (groupPosition == 0) {
//
//                                                          switch (childPosition) {
//                                                              case 0:
//                                                                  info = mOrganisme.getAddresse();
//                                                                  break;
//                                                              case 1:
//                                                                  info = mOrganisme.getTelephone();
//                                                                  break;
//                                                          }
//                                                      } else if (groupPosition == 1) {
//                                                          switch (childPosition) {
//                                                              case 0:
//                                                                  info = mOrganisme.getMapCollectTime().get("lundi");
//                                                                  break;
//                                                              case 1:
//                                                                  info = mOrganisme.getMapCollectTime().get("mardi");
//                                                                  break;
//                                                              case 2:
//                                                                  info = mOrganisme.getMapCollectTime().get("mercredi");
//                                                                  break;
//                                                              case 3:
//                                                                  info = mOrganisme.getMapCollectTime().get("jeudi");
//                                                                  break;
//                                                              case 4:
//                                                                  info = mOrganisme.getMapCollectTime().get("vendredi");
//                                                                  break;
//                                                              case 5:
//                                                                  info = mOrganisme.getMapCollectTime().get("samdi");
//                                                                  break;
//                                                              case 6:
//                                                                  info = mOrganisme.getMapCollectTime().get("dimanche");
//                                                                  break;
//                                                          }
//
//                                                      }else{
//
//                                                       info=mOrganisme.getListDenree().get(childPosition);
//
//                                                      }
//                                                      return info;
//                                                  }
//
//                                                  @Override
//                                                  public long getGroupId(int groupPosition) {
//                                                      return groupPosition;
//                                                  }
//
//                                                  @Override
//                                                  public long getChildId(int groupPosition, int childPosition) {
//                                                      return childPosition;
//                                                  }
//
//                                                  @Override
//                                                  public boolean hasStableIds() {
//                                                      return true;
//                                                  }
//
//                                                  @Override
//                                                  public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//
//                                                      LinearLayout layout1 = new LinearLayout(MapsActivity.this);
//                                                      layout1.setOrientation(LinearLayout.HORIZONTAL);
//
//                                                      ImageView logo = new ImageView(MapsActivity.this);
//                                                      logo.setMinimumWidth(300);
//
//                                                      TextView textView = new TextView(MapsActivity.this);
//                                                      textView.setTextColor(Color.BLACK);
//                                                      textView.setTextSize(20);
//                                                      textView.setPadding(15, 15, 15, 15);
//
//                                                      if (groupPosition == 0) {
//
//                                                          logo.setImageResource(R.drawable.adresse);
//
//                                                          textView.setText(mOrganisme.getAddresse());
//                                                          layout1.addView(logo);
//                                                          layout1.addView(textView);
//
//                                                      } else if (groupPosition == 1) {
//
//                                                          logo.setImageResource(R.drawable.horaire);
//                                                          final Calendar c = Calendar.getInstance();
//                                                          int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
//                                                          if (dayOfWeek == 2) {
//                                                              //  textView.setText(mOrganisme.getMapCollectTime().get("lundi"));
//                                                              textView.setText("Collecte Aujourd'hui:" + ((HashMap) getGroup(groupPosition)).get("lundi"));
//                                                          } else if (dayOfWeek == 3) {
//                                                              textView.setText("Collecte Aujourd'hui:" + ((HashMap) getGroup(groupPosition)).get("mardi"));
//                                                          } else if (dayOfWeek == 4) {
//                                                              textView.setText("Collecte Aujourd'hui:" + ((HashMap) getGroup(groupPosition)).get("mercredi"));
//                                                          } else if (dayOfWeek == 5) {
//                                                              textView.setText("Collecte Aujourd'hui:" + ((HashMap) getGroup(groupPosition)).get("jeudi"));
//                                                          } else if (dayOfWeek == 6) {
//                                                              textView.setText("Collecte Aujourd'hui:" + ((HashMap) getGroup(groupPosition)).get("vendredi"));
//                                                          } else if (dayOfWeek == 7) {
//                                                              textView.setText("Collecte Aujourd'hui:" + ((HashMap) getGroup(groupPosition)).get("samdi"));
//                                                          } else {
//                                                              textView.setText("Collecte Aujourd'hui:" + ((HashMap) getGroup(groupPosition)).get("dimanche"));
//                                                          }
//
//                                                          layout1.addView(logo);
//                                                          layout1.addView(textView);
//
//                                                      } else {
//
////
////
//                                                          logo.setImageResource(R.drawable.liste_marchandise);
//                                                          textView.setText("afficher les denree a donner              unites:" + mOrganisme.getListDenree().size());
//
//                                                      }
//
//
//                                                      return layout1;
//
//
//                                                  }
//
//                                                  @Override
//                                                  public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//
//                                                      LinearLayout layout = new LinearLayout(MapsActivity.this);
//                                                      layout.setOrientation(LinearLayout.HORIZONTAL);
//                                                      if (groupPosition == 0) {
//                                                          ImageView logo = new ImageView(MapsActivity.this);
//                                                          logo.setMinimumWidth(300);
//                                                          TextView textView = new TextView(MapsActivity.this);
//                                                          textView.setTextColor(Color.BLACK);
//                                                          textView.setTextSize(20);
//                                                          textView.setPadding(5, 55, 5, 5);
//
//                                                          switch (childPosition) {
//                                                              case 0:
//                                                                  logo.setImageResource(R.drawable.trademarks);
//                                                                  textView.setText(mOrganisme.getNomOrganisme());
//                                                                  break;
//                                                              case 1:
//
//                                                                  logo.setImageResource(R.drawable.telephone2);
//                                                                  textView.setText(mOrganisme.getTelephone());
//                                                                  break;
//                                                          }
//
//                                                          layout.addView(logo);
//                                                          layout.addView(textView);
//
//                                                      } else if (groupPosition == 1) {
//                                                          TextView textViewDay = new TextView(MapsActivity.this);
//                                                          textViewDay.setTextColor(Color.BLACK);
//                                                          textViewDay.setTextSize(20);
//                                                          textViewDay.setPadding(55, 35, 5, 5);
//                                                          textViewDay.setWidth(300);
//
//                                                          TextView textViewTime = new TextView(MapsActivity.this);
//                                                          textViewTime.setTextColor(Color.BLACK);
//                                                          textViewTime.setTextSize(20);
//                                                          textViewTime.setPadding(55, 35, 5, 5);
//                                                          textViewTime.setWidth(300);
//                                                          switch (childPosition) {
//
//                                                              case 0:
//                                                                  textViewTime.setText("lundi");
//                                                                  textViewDay.setText(mOrganisme.getMapCollectTime().get("lundi"));
//                                                                  break;
//                                                              case 1:
//                                                                  textViewTime.setText("mardi");
//                                                                  textViewDay.setText(mOrganisme.getMapCollectTime().get("mardi"));
//                                                                  break;
//                                                              case 2:
//                                                                  textViewTime.setText("mercredi");
//                                                                  textViewDay.setText(mOrganisme.getMapCollectTime().get("mercredi"));
//                                                                  break;
//                                                              case 3:
//                                                                  textViewTime.setText("jeudi");
//                                                                  textViewDay.setText(mOrganisme.getMapCollectTime().get("jeudi"));
//                                                                  break;
//                                                              case 4:
//                                                                  textViewTime.setText("vendredi");
//                                                                  textViewDay.setText(mOrganisme.getMapCollectTime().get("vendredi"));
//                                                                  break;
//                                                              case 5:
//                                                                  textViewTime.setText("samdi");
//                                                                  textViewDay.setText(mOrganisme.getMapCollectTime().get("samdi"));
//                                                                  break;
//                                                              case 6:
//                                                                  textViewTime.setText("dimanche");
//                                                                  textViewDay.setText(mOrganisme.getMapCollectTime().get("dimanche"));
//                                                                  break;
//
//                                                          }
//                                                          layout.addView(textViewDay);
//                                                          layout.addView(textViewTime);
//                                                      }
//                                                      else{
//
//                                                          setContentView(getLayoutInflater().inflate(R.layout.item_liste_marchandise, layout));
//                                                         ((ImageView) findViewById(R.id.icon_marchandise)).setImageResource(R.drawable.liste_marchandise);
//                                                             ((TextView) findViewById(R.id.nom_marchandise)).setText(((Denree) getGroup(childPosition)).getNomDenree());
//                                                             ((TextView) findViewById(R.id.quantite_marchandise)).setText(((Denree) getGroup(childPosition)).getQuantiteDenree());
//                                                             ((TextView) findViewById(R.id.unite_marchandise)).setText(((Denree) getGroup(childPosition)).getUnite());
//                                                             ((TextView) findViewById(R.id.date_de_peremption)).setText("9999,12,30");
//                                                             ((ImageButton) findViewById(R.id.ib_reserver)).setImageResource(R.drawable.liste_marchandise);
//                                                             ((ImageButton) findViewById(R.id.ib_annuler)).setImageResource(R.drawable.liste_marchandise);
//
//
//
//                                                      }
//
//                                                      return layout;
//                                                  }
//
//                                                  @Override
//                                                  public boolean isChildSelectable(int groupPosition, int childPosition) {
//                                                      return true;
//                                                  }
//
//                                                  @Override
//                                                  public boolean areAllItemsEnabled() {
//                                                      return false;
//                                                  }
//
//                                                  @Override
//                                                  public boolean isEmpty() {
//                                                      return false;
//                                                  }
//
//                                                  @Override
//                                                  public void onGroupExpanded(int groupPosition) {
//
//                                                  }
//
//                                                  @Override
//                                                  public void onGroupCollapsed(int groupPosition) {
//
//                                                  }
//
//                                                  @Override
//                                                  public long getCombinedChildId(long groupId, long childId) {
//                                                      return 0;
//                                                  }
//
//                                                  @Override
//                                                  public long getCombinedGroupId(long groupId) {
//                                                      return 0;
//                                                  }
//                                              });

                                              expandableListView.setAdapter(new BaseExpandableListAdapter() {

                                                  @Override
                                                  public int getGroupCount() {
                                                      return 3;
                                                  }

                                                  @Override
                                                  public int getChildrenCount(int groupPosition) {
                                                      int count;
                                                      if (groupPosition == 0) {
                                                          count = 2;
                                                      } else if (groupPosition == 1) {
                                                          count = 7;
                                                      } else {
                                                          count = mOrganisme.getListDenree().size() + 1;
                                                      }
                                                      return count;
                                                  }

                                                  @Override
                                                  public Object getGroup(int groupPosition) {
                                                      Object info;
                                                      if (groupPosition == 0) {
                                                          info = mOrganisme.getNomOrganisme();
                                                      } else if (groupPosition == 1) {
                                                          info = mOrganisme.getMapCollectTime();

                                                      } else {

                                                          info = mOrganisme.getListDenree();
                                                      }

                                                      return info;

                                                  }

                                                  @Override
                                                  public Object getChild(int groupPosition, int childPosition) {
                                                      Object info = null;


                                                      if (groupPosition == 0) {

                                                          switch (childPosition) {
                                                              case 0:
                                                                  info = mOrganisme.getAddresse();
                                                                  break;
                                                              case 1:
                                                                  info = mOrganisme.getTelephone();
                                                                  break;
                                                          }
                                                      } else if (groupPosition == 1) {
                                                          switch (childPosition) {
                                                              case 0:
                                                                  info = mOrganisme.getMapCollectTime().get("lundi");
                                                                  break;
                                                              case 1:
                                                                  info = mOrganisme.getMapCollectTime().get("mardi");
                                                                  break;
                                                              case 2:
                                                                  info = mOrganisme.getMapCollectTime().get("mercredi");
                                                                  break;
                                                              case 3:
                                                                  info = mOrganisme.getMapCollectTime().get("jeudi");
                                                                  break;
                                                              case 4:
                                                                  info = mOrganisme.getMapCollectTime().get("vendredi");
                                                                  break;
                                                              case 5:
                                                                  info = mOrganisme.getMapCollectTime().get("samdi");
                                                                  break;
                                                              case 6:
                                                                  info = mOrganisme.getMapCollectTime().get("dimanche");
                                                                  break;
                                                          }

                                                      } else {
                                                          switch (childPosition) {
                                                              case 0:
                                                                  String[] str = {"nom", "quantite", "date de peremption"};
                                                                  info = str;
                                                                  break;
                                                              default:
                                                                  info = mOrganisme.getListDenree().get(childPosition-1);
                                                          }

                                                      }
                                                      return info;
                                                  }

                                                  @Override
                                                  public long getGroupId(int groupPosition) {
                                                      return groupPosition;
                                                  }

                                                  @Override
                                                  public long getChildId(int groupPosition, int childPosition) {
                                                      return childPosition;
                                                  }

                                                  @Override
                                                  public boolean hasStableIds() {
                                                      return true;
                                                  }

                                                  @Override
                                                  public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

                                                      LinearLayout layout1 = new LinearLayout(MapsActivity.this);
                                                      layout1.setOrientation(LinearLayout.HORIZONTAL);

                                                      ImageView logo = new ImageView(MapsActivity.this);
                                                      logo.setMinimumWidth(300);

                                                      TextView textView = new TextView(MapsActivity.this);
                                                      textView.setTextColor(Color.BLACK);
                                                      textView.setTextSize(20);
                                                      textView.setPadding(15, 15, 15, 15);

                                                      if (groupPosition == 0) {

                                                          logo.setImageResource(R.drawable.adresse);

                                                          textView.setText(mOrganisme.getAddresse());


                                                      } else if (groupPosition == 1) {

                                                          logo.setImageResource(R.drawable.horaire);
                                                          final Calendar c = Calendar.getInstance();
                                                          int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                                                          if (dayOfWeek == 2) {
                                                              //  textView.setText(mOrganisme.getMapCollectTime().get("lundi"));
                                                              textView.setText("Collecte Aujourd'hui:" + ((HashMap) getGroup(groupPosition)).get("lundi"));
                                                          } else if (dayOfWeek == 3) {
                                                              textView.setText("Collecte Aujourd'hui:" + ((HashMap) getGroup(groupPosition)).get("mardi"));
                                                          } else if (dayOfWeek == 4) {
                                                              textView.setText("Collecte Aujourd'hui:" + ((HashMap) getGroup(groupPosition)).get("mercredi"));
                                                          } else if (dayOfWeek == 5) {
                                                              textView.setText("Collecte Aujourd'hui:" + ((HashMap) getGroup(groupPosition)).get("jeudi"));
                                                          } else if (dayOfWeek == 6) {
                                                              textView.setText("Collecte Aujourd'hui:" + ((HashMap) getGroup(groupPosition)).get("vendredi"));
                                                          } else if (dayOfWeek == 7) {
                                                              textView.setText("Collecte Aujourd'hui:" + ((HashMap) getGroup(groupPosition)).get("samdi"));
                                                          } else {
                                                              textView.setText("Collecte Aujourd'hui:" + ((HashMap) getGroup(groupPosition)).get("dimanche"));
                                                          }

                                                      } else {

                                                          logo.setImageResource(R.drawable.liste_marchandise);
                                                          textView.setText("Afficher les denrées à donner              unites:" + mOrganisme.getListDenree().size());

                                                      }

                                                      layout1.addView(logo);
                                                      layout1.addView(textView);
                                                      return layout1;


                                                  }

                                                  @Override
                                                  public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

                                                      LinearLayout layout = new LinearLayout(MapsActivity.this);
                                                      layout.setOrientation(LinearLayout.HORIZONTAL);
                                                      if (groupPosition == 0) {
                                                          ImageView logo = new ImageView(MapsActivity.this);
                                                          logo.setMinimumWidth(300);
                                                          TextView textView = new TextView(MapsActivity.this);
                                                          textView.setTextColor(Color.BLACK);
                                                          textView.setTextSize(20);
                                                          textView.setPadding(5, 17, 5, 5);

                                                          switch (childPosition) {
                                                              case 0:
                                                                  logo.setImageResource(R.drawable.trademarks);
                                                                  textView.setText(mOrganisme.getNomOrganisme());
                                                                  break;
                                                              case 1:

                                                                  logo.setImageResource(R.drawable.telephone2);
                                                                  textView.setText(mOrganisme.getTelephone());
                                                                  break;
                                                          }

                                                          layout.addView(logo);
                                                          layout.addView(textView);

                                                      } else if (groupPosition == 1) {
                                                          TextView textViewDay = new TextView(MapsActivity.this);
                                                          textViewDay.setTextColor(Color.BLACK);
                                                          textViewDay.setTextSize(20);
                                                          textViewDay.setPadding(55, 17, 5, 5);
                                                          textViewDay.setWidth(300);

                                                          TextView textViewTime = new TextView(MapsActivity.this);
                                                          textViewTime.setTextColor(Color.BLACK);
                                                          textViewTime.setTextSize(20);
                                                          textViewTime.setPadding(55, 17, 5, 5);
                                                          textViewTime.setWidth(300);
                                                          switch (childPosition) {

                                                              case 0:
                                                                  textViewTime.setText("lundi");
                                                                  textViewDay.setText(mOrganisme.getMapCollectTime().get("lundi"));
                                                                  break;
                                                              case 1:
                                                                  textViewTime.setText("mardi");
                                                                  textViewDay.setText(mOrganisme.getMapCollectTime().get("mardi"));
                                                                  break;
                                                              case 2:
                                                                  textViewTime.setText("mercredi");
                                                                  textViewDay.setText(mOrganisme.getMapCollectTime().get("mercredi"));
                                                                  break;
                                                              case 3:
                                                                  textViewTime.setText("jeudi");
                                                                  textViewDay.setText(mOrganisme.getMapCollectTime().get("jeudi"));
                                                                  break;
                                                              case 4:
                                                                  textViewTime.setText("vendredi");
                                                                  textViewDay.setText(mOrganisme.getMapCollectTime().get("vendredi"));
                                                                  break;
                                                              case 5:
                                                                  textViewTime.setText("samdi");
                                                                  textViewDay.setText(mOrganisme.getMapCollectTime().get("samdi"));
                                                                  break;
                                                              case 6:
                                                                  textViewTime.setText("dimanche");
                                                                  textViewDay.setText(mOrganisme.getMapCollectTime().get("dimanche"));
                                                                  break;

                                                          }
                                                          layout.addView(textViewDay);
                                                          layout.addView(textViewTime);
                                                      } else {
                                                          if (childPosition == 0) {
                                                              Space space=new Space(MapsActivity.this);
                                                              space.setMinimumHeight(60);
                                                              space.setMinimumWidth(300);

                                                              TextView textView1 = new TextView(MapsActivity.this);
                                                              textView1.setTextColor(Color.BLACK);
                                                              textView1.setTextSize(20);
                                                              textView1.setPadding(5, 17, 5, 17);
                                                              textView1.setWidth(100);
                                                              textView1.setText("Nom");

                                                              TextView textView2 = new TextView(MapsActivity.this);
                                                              textView2.setTextColor(Color.BLACK);
                                                              textView2.setTextSize(20);
                                                              textView2.setPadding(105, 17, 5, 17);
                                                              textView2.setWidth(280);
                                                              textView2.setText("Quantite");

                                                              TextView textView3 = new TextView(MapsActivity.this);
                                                              textView3.setTextColor(Color.BLACK);
                                                              textView3.setTextSize(20);
                                                              textView3.setPadding(45, 17, 5, 17);
                                                              textView3.setWidth(800);
                                                              textView3.setText("Date de peremption");
                                                              layout.addView(space);
                                                              layout.addView(textView1);
                                                              layout.addView(textView2);
                                                              layout.addView(textView3);


//                                                              setContentView(getLayoutInflater().inflate(R.layout.head_liste_disponible, layout));
//                                                              ((TextView) findViewById(R.id.tv_nom)).setText("nom");
//                                                              ((TextView) findViewById(R.id.tv_quantite)).setText("quantite");
//                                                              ((TextView) findViewById(R.id.tv_date)).setText("date de peremption");
                                                          } else {
                                                              ImageView logo = new ImageView(MapsActivity.this);
                                                              String typeDenree = (((Denree) getChild(groupPosition, childPosition))).getTypeDenree().toString();
                                                              if ((typeDenree.equals("fruit_legume"))) {
                                                                  logo.setImageResource(R.drawable.map_fruit_legume);
                                                              } else if (typeDenree.equals("viande")) {
                                                                  logo.setImageResource(R.drawable.map_viande);
                                                              } else if (typeDenree.equals("laitier")) {
                                                                  logo.setImageResource(R.drawable.map_laitier);
                                                              } else if (typeDenree.equals("surgele")) {
                                                                  logo.setImageResource(R.drawable.map_surgele);
                                                              } else if (typeDenree.equals("non-comestible")) {
                                                                  logo.setImageResource(R.drawable.map_non_comestible);
                                                              } else if (typeDenree.equals("boulangerie")) {
                                                                  logo.setImageResource(R.drawable.map_boulangerie);
                                                              } else {
                                                                  logo.setImageResource(R.drawable.map_non_perissable);
                                                              }

                                                              logo.setMinimumWidth(300);

                                                              TextView textView1 = new TextView(MapsActivity.this);
                                                              textView1.setTextColor(Color.BLACK);
                                                              textView1.setTextSize(20);
                                                              textView1.setWidth(200);
                                                              textView1.setPadding(5, 17, 5, 17);
                                                              textView1.setText(mOrganisme.getListDenree().get(childPosition - 1).getNomDenree());

                                                              TextView textView2 = new TextView(MapsActivity.this);
                                                              textView2.setTextColor(Color.BLACK);
                                                              textView2.setTextSize(20);
                                                              textView2.setWidth(100);
                                                              textView2.setPadding(0, 17, 5, 17);
                                                              textView2.setText(mOrganisme.getListDenree().get(childPosition-1).getTypeUnite());

                                                              TextView textView3 = new TextView(MapsActivity.this);
                                                              textView3.setTextColor(Color.BLACK);
                                                              textView3.setTextSize(20);
                                                              textView3.setWidth(60);
                                                              textView3.setPadding(5, 17, 0, 17);
                                                              textView3.setText(mOrganisme.getListDenree().get(childPosition-1).getQuantiteDenree());

                                                              TextView textView4 = new TextView(MapsActivity.this);
                                                              textView4.setTextColor(Color.BLACK);
                                                              textView4.setTextSize(20);
                                                              textView4.setWidth(400);
                                                              textView4.setPadding(65, 17, 5, 17);
                                                              textView4.setText(mOrganisme.getListDenree().get(childPosition-1).getDatePeremption());

                                                              Button btn = new Button(MapsActivity.this);
                                                              btn.setText("Reserver");

                                                              btn.setPadding(5, 5, 5, 5);
                                                              layout.addView(logo);
                                                              layout.addView(textView1);
                                                              layout.addView(textView3);
                                                              layout.addView(textView2);
                                                              layout.addView(textView4);
                                                              layout.addView(btn);
                                                              btn.setOnClickListener(new View.OnClickListener() {
                                                                  @Override
                                                                  public void onClick(View v) {
                                                                      mOrganisme.getListDenree().get(childPosition-1).setStateDenree(StateDenree.reserveee);
                                                                  }

                                                              });

                                                          }
                                                      }

                                                      return layout;
                                                  }

                                                  @Override
                                                  public boolean isChildSelectable(int groupPosition, int childPosition) {
                                                      return true;
                                                  }

                                                  @Override
                                                  public boolean areAllItemsEnabled() {
                                                      return false;
                                                  }

                                                  @Override
                                                  public boolean isEmpty() {
                                                      return false;
                                                  }

                                                  @Override
                                                  public void onGroupExpanded(int groupPosition) {

                                                  }

                                                  @Override
                                                  public void onGroupCollapsed(int groupPosition) {

                                                  }

                                                  @Override
                                                  public long getCombinedChildId(long groupId, long childId) {
                                                      return 0;
                                                  }

                                                  @Override
                                                  public long getCombinedGroupId(long groupId) {
                                                      return 0;
                                                  }
                                              });

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

