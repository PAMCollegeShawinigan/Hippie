package com.pam.codenamehippie.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.pam.codenamehippie.ui.HippieActivity;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MapsActivity extends HippieActivity implements OnMapReadyCallback, ExpandableListView.OnGroupClickListener {
    private SlidingUpPanelLayout slidingLayout;
    private ArrayList<Entreprise> listEntreprise;
    private ExpandableListView expandableListView;
    private int ordre;
    private LatLng shawiniganLatLng, montrealLatLng, troisriviereLatLng, jolietteLatLng, victoriavilleLatLng;
    private ArrayList<Marker> listMarker;

    /**
     * preparer la carte google et des donnees.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_plus);
        slidingLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        final RelativeLayout mapView = (RelativeLayout) findViewById(R.id.mapView);
        slidingLayout.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {

            @Override
            public void onPanelSlide(View view, float v) {

            }

            @Override
            public void onPanelCollapsed(View view) {
                mapView.setVisibility(View.VISIBLE);
//expandableListView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPanelExpanded(View view) {
                mapView.setVisibility(View.GONE);
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
        prepareDonnees();
    }


    public void onButtonClick(View v){
     switch (v.getId()) {

            case R.id.marchandiseDisponible:
                // affiche denree disponible sur la carte
                startActivity(new Intent(this, MapsActivity.class));
                Toast.makeText(this.getApplicationContext(),
                        " Denrées disponible ",
                        Toast.LENGTH_SHORT
                ).show();
                break;
            case R.id.mesReservation:
                // affiche mes reservations sur la carte
                Toast.makeText(this.getApplicationContext(),
                        " Mes réservations ",
                        Toast.LENGTH_SHORT
                ).show();
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
    private void prepareDonnees() {
        //les LatLng infos sont obtenues pas webservice de googlemap,les entrees sont des addresses civiqes.
        //les points cidessus sont seulement pour les tests.
        //preparer les lattitudes et logitudes pour chaque entreprise
        shawiniganLatLng = new LatLng(46.5618559, -72.7435254);
        montrealLatLng = new LatLng(45.5454532, -73.6390814);
        troisriviereLatLng = new LatLng(46.35088, -72.54806);
        jolietteLatLng = new LatLng(46.02318, -73.44253);
        victoriavilleLatLng = new LatLng(46.05837, -71.95025);
        ArrayList<LatLng> listLatLng = new ArrayList();
        listLatLng.add(shawiniganLatLng);
        listLatLng.add(montrealLatLng);
        listLatLng.add(troisriviereLatLng);
        listLatLng.add(jolietteLatLng);
        listLatLng.add(victoriavilleLatLng);

        // preparer des entreprise et leurs liste denrees
        ArrayList<Denree> listDenree1 = new ArrayList<Denree>();
        listDenree1.add(new Denree("orange", "2", "kg", StateDenree.disponible, TypeDenree.fruit_legume));
        listDenree1.add(new Denree("poulet", "3", "kg", StateDenree.disponible, TypeDenree.perissable));
        listDenree1.add(new Denree("prune", "13", "kg", StateDenree.disponible, TypeDenree.fruit_legume));
        listDenree1.add(new Denree("lait", "6", "kg", StateDenree.disponible, TypeDenree.laitier));

        HashMap<String, String> mapCollectTime1 = new HashMap<>();
        mapCollectTime1.put("lundi", "12:00-14:00");
        mapCollectTime1.put("mardi", "9:00-16:00");
        mapCollectTime1.put("mercredi", "8:00-13:00");
        mapCollectTime1.put("jeudi", "9:00-14:00");
        mapCollectTime1.put("vendredi", "9:00-17:00");
        mapCollectTime1.put("samdi", "9:00-13:00");
        mapCollectTime1.put("dimanche", "ferme");

        Entreprise entreprise1 = new Entreprise("denree Shawinigan", "120,62E RUE Shawinigan", shawiniganLatLng, mapCollectTime1, "819-000-1234", listDenree1);

        ArrayList<Denree> listDenree2 = new ArrayList<Denree>();
        listDenree2.add(new Denree("pomme", "33", "kg", StateDenree.disponible, TypeDenree.boulangerie));
        listDenree2.add(new Denree("avocat", "8", "kg", StateDenree.disponible, TypeDenree.fruit_legume));
        listDenree2.add(new Denree("rasin", "7", "kg", StateDenree.disponible, TypeDenree.fruit_legume));
        listDenree2.add(new Denree("patate", "21", "kg", StateDenree.disponible, TypeDenree.fruit_legume));
        HashMap<String, String> mapCollectTime2 = new HashMap<>();
        mapCollectTime2.put("lundi", "9:00-14:00");
        mapCollectTime2.put("mardi", "8:00-14:00");
        mapCollectTime2.put("mercredi", "8:00-18:00");
        mapCollectTime2.put("jeudi", "9:00-14:00");
        mapCollectTime2.put("vendredi", "10:00-17:00");
        mapCollectTime2.put("samdi", "9:00-13:00");
        mapCollectTime2.put("dimanche", "ferme");
        Entreprise entreprise2 = new Entreprise("denree Montreal", "250,25E RUE Montreal", montrealLatLng, mapCollectTime2, "514-123-0000", listDenree2);

        ArrayList<Denree> listDenree3 = new ArrayList<>();
        listDenree3.add(new Denree("croissant", "7", "kg", StateDenree.disponible, TypeDenree.boulangerie));
        listDenree3.add(new Denree("mais", "40", "kg", StateDenree.disponible, TypeDenree.perissable));
        listDenree3.add(new Denree("carrot", "41", "kg", StateDenree.disponible, TypeDenree.fruit_legume));
        HashMap<String, String> mapCollectTime3 = new HashMap<>();
        mapCollectTime3.put("lundi", "9:00-14:00");
        mapCollectTime3.put("mardi", "9:00-11:00");
        mapCollectTime3.put("mercredi", "8:00-14:00");
        mapCollectTime3.put("jeudi", "13:00-14:00");
        mapCollectTime3.put("vendredi", "9:00-17:00");
        mapCollectTime3.put("samdi", "9:00-13:00");
        mapCollectTime3.put("dimanche", "ferme");
        Entreprise entreprise3 = new Entreprise("denree Joliette", "156,5E RUE Joliette", jolietteLatLng, mapCollectTime3, "819-040-0450", listDenree3);

        ArrayList<Denree> listDenree4 = new ArrayList<>();
        listDenree4.add(new Denree("rasin", "37", "kg", StateDenree.disponible, TypeDenree.fruit_legume));
        listDenree4.add(new Denree("tuna", "90", "kg", StateDenree.disponible, TypeDenree.surgele));
        HashMap<String, String> mapCollectTime4 = new HashMap<>();
        mapCollectTime4.put("lundi", "9:00-12:00");
        mapCollectTime4.put("mardi", "9:00-14:00");
        mapCollectTime4.put("mercredi", "10:00-14:00");
        mapCollectTime4.put("jeudi", "9:00-14:00");
        mapCollectTime4.put("vendredi", "9:00-16:00");
        mapCollectTime4.put("samdi", "9:00-13:00");
        mapCollectTime4.put("dimanche", "ferme");
        Entreprise entreprise4 = new Entreprise("denree Victoriaville", "350,36E RUE Victoriaville", victoriavilleLatLng, mapCollectTime4, "819-021-3214", listDenree4);

        ArrayList<Denree> listDenree5 = new ArrayList<>();
        listDenree5.add(new Denree("tuna", "34", "kg", StateDenree.disponible, TypeDenree.surgele));
        listDenree5.add(new Denree("avocat", "27", "kg", StateDenree.disponible, TypeDenree.perissable));
        listDenree5.add(new Denree("orange", "56", "kg", StateDenree.disponible, TypeDenree.fruit_legume));
        listDenree5.add(new Denree("samon", "34", "kg", StateDenree.disponible, TypeDenree.surgele));
        listDenree5.add(new Denree("beuf", "9", "kg", StateDenree.disponible, TypeDenree.viande));
        HashMap<String, String> mapCollectTime5 = new HashMap<>();
        mapCollectTime5.put("lundi", "9:00-10:00");
        mapCollectTime5.put("mardi", "11:00-14:00");
        mapCollectTime5.put("mercredi", "8:00-14:00");
        mapCollectTime5.put("jeudi", "9:00-16:00");
        mapCollectTime5.put("vendredi", "13:00-17:00");
        mapCollectTime5.put("samdi", "9:00-10:00");
        mapCollectTime5.put("dimanche", "ferme");
        Entreprise entreprise5 = new Entreprise("denree Trois Rivieres", "168,77E RUE Trois Rivieres", troisriviereLatLng, mapCollectTime5, "819-000-4527", listDenree5);

        //preparer les entreprise affichee sur carte
        listEntreprise = new ArrayList<>();
        listEntreprise.add(entreprise1);
        listEntreprise.add(entreprise2);
        listEntreprise.add(entreprise3);
        listEntreprise.add(entreprise4);
        listEntreprise.add(entreprise5);
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

        GoogleMap mMap = googleMap;
        //les LatLng infos sont obtenues pas webservice de googlemap,les entrees sont des addresses civiles.
        //les points cidessus sont seulement pour les tests.
        //ajouter les point sur carte

        final Marker markerShawinigan = mMap.addMarker(new MarkerOptions().position(shawiniganLatLng));
        final Marker markerMontreal = mMap.addMarker(new MarkerOptions().position(montrealLatLng));
        final Marker markerTroisriviere = mMap.addMarker(new MarkerOptions().position(troisriviereLatLng));
        final Marker markerJoliette = mMap.addMarker(new MarkerOptions().position(jolietteLatLng));
        final Marker markerVictoriaville = mMap.addMarker(new MarkerOptions().position(victoriavilleLatLng));

//        listMarker=new ArrayList<>();
//        for(int i=0;i<listLatLng.size();i++){
//            listMarker.add(mMap.addMarker(new MarkerOptions().position(listLatLng.get(i))));
//        }

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
//        for (Marker m : listMarker) {
//            builder.include(m.getPosition());
//        }

        builder.include(shawiniganLatLng).include(montrealLatLng).include(troisriviereLatLng).include(jolietteLatLng).include(victoriavilleLatLng);
        LatLngBounds bounds = builder.build();

        int padding = 1; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds,400,600, padding);
        mMap.moveCamera(cu);

        mMap.animateCamera(cu);


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

                                          @Override
                                          public boolean onMarkerClick(Marker marker) {
                                              //par le variable ordre,on peut determiner quelle entreprise a s'afficher
                                              if (marker.equals(markerShawinigan)) {
                                                  ordre = 0;
                                              } else if (marker.equals(markerMontreal)) {
                                                  ordre = 1;
                                              } else if (marker.equals(markerJoliette)) {
                                                  ordre = 2;
                                              } else if (marker.equals(markerVictoriaville)) {
                                                  ordre = 3;
                                              } else if (marker.equals(markerTroisriviere)) {
                                                  ordre = 4;
                                              }

                                              final Entreprise mEntreprise = listEntreprise.get(ordre);


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
                                                          count = mEntreprise.getListDenree().size();
                                                      }
                                                      return count;
                                                  }

                                                  @Override
                                                  public Object getGroup(int groupPosition) {
                                                      Object info = null;
                                                      if (groupPosition == 0) {
                                                          info = mEntreprise.getNomEntreprise();
                                                      } else if (groupPosition == 1) {
                                                          info = mEntreprise.getMapCollectTime();

                                                      } else {

                                                          info = mEntreprise.getListDenree();
                                                      }

                                                      return info;

                                                  }

                                                  @Override
                                                  public Object getChild(int groupPosition, int childPosition) {
                                                      Object info = null;


                                                      if (groupPosition == 0) {

                                                          switch (childPosition) {
                                                              case 0:
                                                                  info = mEntreprise.getAddresse();
                                                                  break;
                                                              case 1:
                                                                  info = mEntreprise.getTelephone();
                                                                  break;
                                                          }
                                                      } else if (groupPosition == 1) {
                                                          switch (childPosition) {
                                                              case 0:
                                                                  info = mEntreprise.getMapCollectTime().get("lundi");
                                                                  break;
                                                              case 1:
                                                                  info = mEntreprise.getMapCollectTime().get("mardi");
                                                                  break;
                                                              case 2:
                                                                  info = mEntreprise.getMapCollectTime().get("mercredi");
                                                                  break;
                                                              case 3:
                                                                  info = mEntreprise.getMapCollectTime().get("jeudi");
                                                                  break;
                                                              case 4:
                                                                  info = mEntreprise.getMapCollectTime().get("vendredi");
                                                                  break;
                                                              case 5:
                                                                  info = mEntreprise.getMapCollectTime().get("samdi");
                                                                  break;
                                                              case 6:
                                                                  info = mEntreprise.getMapCollectTime().get("dimanche");
                                                                  break;
                                                          }

                                                      } else {
                                                          info = mEntreprise.getListDenree().get(childPosition);
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

                                                          logo.setImageResource(R.drawable.addren);

                                                          textView.setText(mEntreprise.getAddresse());


                                                      } else if (groupPosition == 1) {

                                                          logo.setImageResource(R.drawable.clockn);
                                                          final Calendar c = Calendar.getInstance();
                                                          int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                                                          if (dayOfWeek == 1) {
                                                              //  textView.setText(mEntreprise.getMapCollectTime().get("lundi"));
                                                              textView.setText(((HashMap) getGroup(groupPosition)).get("lundi").toString());
                                                          } else if (dayOfWeek == 2) {
                                                              textView.setText(mEntreprise.getMapCollectTime().get("mardi"));
                                                          } else if (dayOfWeek == 3) {
                                                              textView.setText(mEntreprise.getMapCollectTime().get("mercredi"));
                                                          } else if (dayOfWeek == 4) {
                                                              textView.setText(mEntreprise.getMapCollectTime().get("jeudi"));
                                                          } else if (dayOfWeek == 5) {
                                                              textView.setText(mEntreprise.getMapCollectTime().get("vendredi"));
                                                          } else if (dayOfWeek == 6) {
                                                              textView.setText(mEntreprise.getMapCollectTime().get("samdi"));
                                                          } else {
                                                              textView.setText(mEntreprise.getMapCollectTime().get("dimanche"));
                                                          }

                                                      } else {

                                                          logo.setImageResource(R.drawable.lists);
                                                          textView.setText("afficher les denree a donner              unites:" + mEntreprise.getListDenree().size());

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
                                                          textView.setPadding(5, 55, 5, 5);

                                                          switch (childPosition) {
                                                              case 0:
                                                                  logo.setImageResource(R.drawable.trademarks);
                                                                  textView.setText(mEntreprise.getNomEntreprise());
                                                                  break;
                                                              case 1:
                                                                  logo.setImageResource(R.drawable.newtele);
                                                                  textView.setText(mEntreprise.getTelephone());
                                                                  break;
                                                          }

                                                          layout.addView(logo);
                                                          layout.addView(textView);

                                                      } else if (groupPosition == 1) {
                                                          TextView textViewDay = new TextView(MapsActivity.this);
                                                          textViewDay.setTextColor(Color.BLACK);
                                                          textViewDay.setTextSize(20);
                                                          textViewDay.setPadding(55, 35, 5, 5);
                                                          textViewDay.setWidth(300);

                                                          TextView textViewTime = new TextView(MapsActivity.this);
                                                          textViewTime.setTextColor(Color.BLACK);
                                                          textViewTime.setTextSize(20);
                                                          textViewTime.setPadding(55, 35, 5, 5);
                                                          textViewTime.setWidth(300);
                                                          switch (childPosition) {

                                                              case 0:
                                                                  textViewTime.setText("lundi");
                                                                  textViewDay.setText(mEntreprise.getMapCollectTime().get("lundi"));
                                                                  break;
                                                              case 1:
                                                                  textViewTime.setText("mardi");
                                                                  textViewDay.setText(mEntreprise.getMapCollectTime().get("mardi"));
                                                                  break;
                                                              case 2:
                                                                  textViewTime.setText("mercredi");
                                                                  textViewDay.setText(mEntreprise.getMapCollectTime().get("mercredi"));
                                                                  break;
                                                              case 3:
                                                                  textViewTime.setText("jeudi");
                                                                  textViewDay.setText(mEntreprise.getMapCollectTime().get("jeudi"));
                                                                  break;
                                                              case 4:
                                                                  textViewTime.setText("vendredi");
                                                                  textViewDay.setText(mEntreprise.getMapCollectTime().get("vendredi"));
                                                                  break;
                                                              case 5:
                                                                  textViewTime.setText("samdi");
                                                                  textViewDay.setText(mEntreprise.getMapCollectTime().get("samdi"));
                                                                  break;
                                                              case 6:
                                                                  textViewTime.setText("dimanche");
                                                                  textViewDay.setText(mEntreprise.getMapCollectTime().get("dimanche"));
                                                                  break;

                                                          }
                                                          layout.addView(textViewDay);
                                                          layout.addView(textViewTime);
                                                      } else {
                                                          ImageView logo = new ImageView(MapsActivity.this);
                                                          String typeDenree = (((Denree) getChild(groupPosition, childPosition))).getTypeDenree().toString();
                                                          if ((typeDenree.equals("fruit_legume"))) {
                                                              logo.setImageResource(R.drawable.fruits);
                                                          } else if (typeDenree.equals("viande")) {
                                                              logo.setImageResource(R.drawable.meats);
                                                          } else if (typeDenree.equals("laitier")) {
                                                              logo.setImageResource(R.drawable.milks);
                                                          } else if (typeDenree.equals("surgele")) {
                                                              logo.setImageResource(R.drawable.frozens);
                                                          } else if (typeDenree.equals("perissable")) {
                                                              logo.setImageResource(R.drawable.perishables);
                                                          } else if (typeDenree.equals("boulangerie")) {
                                                              logo.setImageResource(R.drawable.breads);
                                                          } else {
                                                              logo.setImageResource(R.drawable.nonperishables);
                                                          }

                                                          logo.setMinimumWidth(300);

                                                          TextView textView1 = new TextView(MapsActivity.this);
                                                          textView1.setTextColor(Color.BLACK);
                                                          textView1.setTextSize(20);
                                                          textView1.setWidth(200);
                                                          textView1.setPadding(5, 55, 5, 5);
                                                          textView1.setText(mEntreprise.getListDenree().get(childPosition).getNomDenree());
                                                          TextView textView3 = new TextView(MapsActivity.this);
                                                          textView3.setTextColor(Color.BLACK);
                                                          textView3.setTextSize(20);
                                                          textView3.setWidth(200);
                                                          textView3.setPadding(5, 55, 5, 5);
                                                          textView3.setText(mEntreprise.getListDenree().get(childPosition).getQuantiteDenree());
                                                          Button btn = new Button(MapsActivity.this);
                                                          btn.setText("reserver");
                                                          btn.setPadding(5, 5, 5, 5);
                                                          layout.addView(logo);
                                                          layout.addView(textView1);
                                                          layout.addView(textView3);
                                                          layout.addView(btn);
                                                          btn.setOnClickListener(new View.OnClickListener() {
                                                              @Override
                                                              public void onClick(View v) {
                                                                  mEntreprise.getListDenree().get(childPosition).setStateDenree(StateDenree.reserveee);
                                                              }

                                                          });

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
        if(!parent.isGroupExpanded(groupPosition)){
            parent.expandGroup(groupPosition);
            return true;
        }

        return  false;
    }


}

