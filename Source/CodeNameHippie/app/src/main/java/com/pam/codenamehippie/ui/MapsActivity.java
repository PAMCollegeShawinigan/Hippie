package com.pam.codenamehippie.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pam.codenamehippie.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ExpandableListView.OnGroupClickListener {

    String keyJour = null;
    private GoogleMap mMap;
    private ArrayList<Denree> listDenree1, listDenree2, listDenree3, listDenree4, listDenree5;
    private ArrayList<Entreprise> listEntreprise;
    private Entreprise entreprise1, entreprise2, entreprise3, entreprise4, entreprise5;
    private ExpandableListView expandableListView;
    private int ordre;
    private HashMap<String, String> mapCollectTime1, mapCollectTime2, mapCollectTime3, mapCollectTime4, mapCollectTime5, mapCollectTime6, mapCollectTime7;
    private LatLng shawiniganLatLng, montrealLatLng, troisriviereLatLng, jolietteLatLng, victoriavilleLatLng;
    public LayoutInflater minflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_plus);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //preparer les donnees pour tester
        prepareDonnees();


    }

    private void prepareDonnees() {
        //les LatLng infos sont obtenues pas webservice de googlemap,les entrees sont des addresses civiqes.
        //les points cidessus sont seulement pour les tests.
        //preparer les lattitudes et logitudes pour chaque entreprise
        shawiniganLatLng = new LatLng(46.5618559, -72.7435254);
        montrealLatLng = new LatLng(45.5454532, -73.6390814);
        troisriviereLatLng = new LatLng(46.35088, -72.54806);
        jolietteLatLng = new LatLng(46.02318, -73.44253);
        victoriavilleLatLng = new LatLng(46.05837, -71.95025);


        // preparer des entreprise et leurs liste denrees
        listDenree1 = new ArrayList<Denree>();
        listDenree1.add(new Denree("orange", "2", "kg", StateDenree.disponible, TypeDenree.fruit_legume));
        listDenree1.add(new Denree("poulai", "3", "kg", StateDenree.disponible, TypeDenree.perissable));
        listDenree1.add(new Denree("prune", "13", "kg", StateDenree.disponible, TypeDenree.fruit_legume));
        listDenree1.add(new Denree("lait", "6", "kg", StateDenree.disponible, TypeDenree.laitier));

        mapCollectTime1 = new HashMap<>();
        mapCollectTime1.put("lundi", "12:00-14:00");
        mapCollectTime1.put("mardi", "9:00-16:00");
        mapCollectTime1.put("mercredi", "8:00-13:00");
        mapCollectTime1.put("jeudi", "9:00-14:00");
        mapCollectTime1.put("vendredi", "9:00-17:00");
        mapCollectTime1.put("samdi", "9:00-13:00");
        mapCollectTime1.put("dimanche", "ferme");

        entreprise1 = new Entreprise("denree Shawinigan", "120,62E RUE Shawinigan", shawiniganLatLng, mapCollectTime1, "819-000-1234", listDenree1);

        listDenree2 = new ArrayList<Denree>();
        listDenree2.add(new Denree("pomme", "33", "kg", StateDenree.disponible, TypeDenree.boulangerie));
        listDenree2.add(new Denree("avocat", "8", "kg", StateDenree.disponible, TypeDenree.fruit_legume));
        listDenree2.add(new Denree("rasin", "7", "kg", StateDenree.disponible, TypeDenree.fruit_legume));
        listDenree2.add(new Denree("patate", "21", "kg", StateDenree.disponible, TypeDenree.fruit_legume));
        mapCollectTime2 = new HashMap<>();
        mapCollectTime2.put("lundi", "9:00-14:00");
        mapCollectTime2.put("mardi", "8:00-14:00");
        mapCollectTime2.put("mercredi", "8:00-18:00");
        mapCollectTime2.put("jeudi", "9:00-14:00");
        mapCollectTime2.put("vendredi", "10:00-17:00");
        mapCollectTime2.put("samdi", "9:00-13:00");
        mapCollectTime2.put("dimanche", "ferme");
        entreprise2 = new Entreprise("denree Montreal", "250,25E RUE Montreal", montrealLatLng, mapCollectTime2, "514-123-0000", listDenree2);

        listDenree3 = new ArrayList<>();
        listDenree3.add(new Denree("croissant", "7", "kg", StateDenree.disponible, TypeDenree.boulangerie));
        listDenree3.add(new Denree("mais", "40", "kg", StateDenree.disponible, TypeDenree.perissable));
        listDenree3.add(new Denree("carrot", "41", "kg", StateDenree.disponible, TypeDenree.fruit_legume));
        mapCollectTime3 = new HashMap<>();
        mapCollectTime3.put("lundi", "9:00-14:00");
        mapCollectTime3.put("mardi", "9:00-11:00");
        mapCollectTime3.put("mercredi", "8:00-14:00");
        mapCollectTime3.put("jeudi", "13:00-14:00");
        mapCollectTime3.put("vendredi", "9:00-17:00");
        mapCollectTime3.put("samdi", "9:00-13:00");
        mapCollectTime3.put("dimanche", "ferme");
        entreprise3 = new Entreprise("denree Joliette", "156,5E RUE Joliette", jolietteLatLng, mapCollectTime3, "819-040-0450", listDenree3);

        listDenree4 = new ArrayList<>();
        listDenree4.add(new Denree("rasin", "37", "kg", StateDenree.disponible, TypeDenree.fruit_legume));
        listDenree4.add(new Denree("tuna", "90", "kg", StateDenree.disponible, TypeDenree.surgele));
        mapCollectTime4 = new HashMap<>();
        mapCollectTime4.put("lundi", "9:00-12:00");
        mapCollectTime4.put("mardi", "9:00-14:00");
        mapCollectTime4.put("mercredi", "10:00-14:00");
        mapCollectTime4.put("jeudi", "9:00-14:00");
        mapCollectTime4.put("vendredi", "9:00-16:00");
        mapCollectTime4.put("samdi", "9:00-13:00");
        mapCollectTime4.put("dimanche", "ferme");
        entreprise4 = new Entreprise("denree Victoriaville", "350,36E RUE Victoriaville", victoriavilleLatLng, mapCollectTime4, "819-021-3214", listDenree4);

        listDenree5 = new ArrayList<>();
        listDenree5.add(new Denree("tuna", "34", "kg", StateDenree.disponible, TypeDenree.surgele));
        listDenree5.add(new Denree("avocat", "27", "kg", StateDenree.disponible, TypeDenree.perissable));
        listDenree5.add(new Denree("orange", "56", "kg", StateDenree.disponible, TypeDenree.fruit_legume));
        listDenree5.add(new Denree("samon", "34", "kg", StateDenree.disponible, TypeDenree.surgele));
        listDenree5.add(new Denree("beuf", "9", "kg", StateDenree.disponible, TypeDenree.viande));
        mapCollectTime5 = new HashMap<>();
        mapCollectTime5.put("lundi", "9:00-10:00");
        mapCollectTime5.put("mardi", "11:00-14:00");
        mapCollectTime5.put("mercredi", "8:00-14:00");
        mapCollectTime5.put("jeudi", "9:00-16:00");
        mapCollectTime5.put("vendredi", "13:00-17:00");
        mapCollectTime5.put("samdi", "9:00-10:00");
        mapCollectTime5.put("dimanche", "ferme");
        entreprise5 = new Entreprise("denree Trois Rivieres", "168,77E RUE Trois Rivieres", troisriviereLatLng, mapCollectTime5, "819-000-4527", listDenree5);

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

        mMap = googleMap;
        //les LatLng infos sont obtenues pas webservice de googlemap,les entrees sont des addresses civiles.
        //les points cidessus sont seulement pour les tests.

        //ajouter les point sur carte


        final Marker markerShawinigan = mMap.addMarker(new MarkerOptions().position(shawiniganLatLng));
        final Marker markerMontreal = mMap.addMarker(new MarkerOptions().position(montrealLatLng));
        final Marker markerTroisriviere = mMap.addMarker(new MarkerOptions().position(troisriviereLatLng));
        final Marker markerJoliette = mMap.addMarker(new MarkerOptions().position(jolietteLatLng));
        final Marker markerVictoriaville = mMap.addMarker(new MarkerOptions().position(victoriavilleLatLng));

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


//                                                                                    LinearLayout layout1 = new LinearLayout(MapsActivity.this);
//                                                                                    layout1.setOrientation(LinearLayout.HORIZONTAL);
//
//                                                                                    ImageView logo = new ImageView(MapsActivity.this);
//                                                                                    logo.setMinimumWidth(300);
//
//                                                                                    TextView textView = new TextView(MapsActivity.this);
//                                                                                    textView.setTextColor(Color.BLACK);
//                                                                                    textView.setTextSize(20);
//                                                                                    textView.setPadding(15, 15, 15, 15);
//
//                                                                                    if (groupPosition == 0) {
//
//                                                                                        logo.setImageResource(R.drawable.addren);
//
//                                                                                        textView.setText(mEntreprise.getAddresse());
//
//
//                                                                                    } else if (groupPosition == 1) {
//
//                                                                                        logo.setImageResource(R.drawable.clockn);
//                                                                                        final Calendar c = Calendar.getInstance();
//                                                                                        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
//                                                                                        if (dayOfWeek == 1) {
//                                                                                            //  textView.setText(mEntreprise.getMapCollectTime().get("lundi"));
//                                                                                            textView.setText(((HashMap) getGroup(groupPosition)).get("lundi").toString());
//                                                                                        } else if (dayOfWeek == 2) {
//                                                                                            textView.setText(mEntreprise.getMapCollectTime().get("mardi"));
//                                                                                        } else if (dayOfWeek == 3) {
//                                                                                            textView.setText(mEntreprise.getMapCollectTime().get("mercredi"));
//                                                                                        } else if (dayOfWeek == 4) {
//                                                                                            textView.setText(mEntreprise.getMapCollectTime().get("jeudi"));
//                                                                                        } else if (dayOfWeek == 5) {
//                                                                                            textView.setText(mEntreprise.getMapCollectTime().get("vendredi"));
//                                                                                        } else if (dayOfWeek == 6) {
//                                                                                            textView.setText(mEntreprise.getMapCollectTime().get("samdi"));
//                                                                                        } else {
//                                                                                            textView.setText(mEntreprise.getMapCollectTime().get("dimanche"));
//                                                                                        }
//
//                                                                                    } else {
//
//                                                                                        logo.setImageResource(R.drawable.lists);
//                                                                                        textView.setText("afficher les denree a donner              unites:" + mEntreprise.getListDenree().size());
//
//                                                                                    }
//
//                                                                                    layout1.addView(logo);
//                                                                                    layout1.addView(textView);
//                                                                                    return layout1;

                                                                                    View v;

                                                                                    if (convertView == null) {
                                                                                        LayoutInflater inflater = (LayoutInflater) getSystemService
                                                                                                (Context.LAYOUT_INFLATER_SERVICE);
                                                                                        v = inflater.inflate(R.layout.layout_normal, parent, false);
                                                                                    }else{
                                                                                        v=convertView;
                                                                                    }

                                                                                    ImageView groupName = (ImageView) v.findViewById(R.id.imageView);
                                                                                    TextView groupDescr = (TextView) v.findViewById(R.id.textView);

                                                                                    groupName.setImageResource(R.drawable.newtele);
                                                                                    groupDescr.setText("pas de quoi");

                                                                                    return v;

                                                                                }

                                                                                @Override
                                                                                public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
                                                                                    View v;

                                                                                    if (convertView == null) {
                                                                                        LayoutInflater inflater = (LayoutInflater) getSystemService
                                                                                                (Context.LAYOUT_INFLATER_SERVICE);
                                                                                        v = inflater.inflate(R.layout.layout_normal, parent, false);
                                                                                    }else{
                                                                                        v=convertView;
                                                                                    }

                                                                                    ImageView groupName = (ImageView) v.findViewById(R.id.imageView);
                                                                                    TextView groupDescr = (TextView) v.findViewById(R.id.textView);

                                                                                    groupName.setImageResource(R.drawable.newtele);
                                                                                    groupDescr.setText("pas de quoi");

                                                                                    return v;



//                                                                                   LinearLayout layout = new LinearLayout(MapsActivity.this);
//                                                                                    layout.setOrientation(LinearLayout.HORIZONTAL);
//                                                                                    if (groupPosition == 0) {
//                                                                                        ImageView logo = new ImageView(MapsActivity.this);
//                                                                                        logo.setMinimumWidth(300);
//                                                                                        TextView textView = new TextView(MapsActivity.this);
//                                                                                        textView.setTextColor(Color.BLACK);
//                                                                                        textView.setTextSize(20);
//                                                                                        textView.setPadding(5, 55, 5, 5);
//                                                                                        textView.setText(getChild(groupPosition,childPosition).toString());
//                                                                                        switch (childPosition) {
//                                                                                            case 0:
//                                                                                                logo.setImageResource(R.drawable.trademarks);
//                                                                                                break;
//                                                                                            case 1:
//                                                                                                logo.setImageResource(R.drawable.newtele);
//                                                                                                break;
//                                                                                        }
//
//                                                                                        layout.addView(logo);
//                                                                                        layout.addView(textView);
//                                                                                    } else if (groupPosition == 1) {
//                                                                                        TextView textViewDay = new TextView(MapsActivity.this);
//                                                                                        textViewDay.setTextColor(Color.BLACK);
//                                                                                        textViewDay.setTextSize(20);
//                                                                                        textViewDay.setPadding(55, 35, 5, 5);
//                                                                                        textViewDay.setWidth(300);
//
//                                                                                        TextView textViewTime = new TextView(MapsActivity.this);
//                                                                                        textViewTime.setTextColor(Color.BLACK);
//                                                                                        textViewTime.setTextSize(20);
//                                                                                        textViewTime.setPadding(55, 35, 5, 5);
//                                                                                        textViewTime.setWidth(300);
//                                                                                        switch (childPosition) {
//
//                                                                                            case 0:
//                                                                                                textViewTime.setText("lundi");
//                                                                                                textViewDay.setText(mEntreprise.getMapCollectTime().get("lundi"));
//                                                                                                break;
//                                                                                            case 1:
//                                                                                                textViewTime.setText("mardi");
//                                                                                                textViewDay.setText(mEntreprise.getMapCollectTime().get("mardi"));
//                                                                                                break;
//                                                                                            case 2:
//                                                                                                textViewTime.setText("mercredi");
//                                                                                                textViewDay.setText(mEntreprise.getMapCollectTime().get("mercredi"));
//                                                                                                break;
//                                                                                            case 3:
//                                                                                                textViewTime.setText("jeudi");
//                                                                                                textViewDay.setText(mEntreprise.getMapCollectTime().get("jeudi"));
//                                                                                                break;
//                                                                                            case 4:
//                                                                                                textViewTime.setText("vendredi");
//                                                                                                textViewDay.setText(mEntreprise.getMapCollectTime().get("vendredi"));
//                                                                                                break;
//                                                                                            case 5:
//                                                                                                textViewTime.setText("samdi");
//                                                                                                textViewDay.setText(mEntreprise.getMapCollectTime().get("samdi"));
//                                                                                                break;
//                                                                                            case 6:
//                                                                                                textViewTime.setText("dimanche");
//                                                                                                textViewDay.setText(mEntreprise.getMapCollectTime().get("dimanche"));
//                                                                                                break;
//
//                                                                                        }
//                                                                                        layout.addView(textViewDay);
//                                                                                        layout.addView(textViewTime);
//                                                                                    } else {
//                                                                                        ImageView logo = new ImageView(MapsActivity.this);
//
//                                                                                        if (
//
//                                                                                                getChild(groupPosition, childPosition).equals("fruit_legume")
//                                                                                                ) {
//                                                                                            logo.setImageResource(R.drawable.fruits);
//                                                                                        } else if (
//                                                                                                getChild(groupPosition, childPosition).equals("viande")) {
//                                                                                            logo.setImageResource(R.drawable.meats);
//                                                                                        } else if (getChild(groupPosition, childPosition).equals("laitier")) {
//                                                                                            logo.setImageResource(R.drawable.milks);
//                                                                                        } else if (getChild(groupPosition, childPosition).equals("surgele")) {
//                                                                                            logo.setImageResource(R.drawable.frozens);
//                                                                                        } else if (getChild(groupPosition, childPosition).equals("perissable")) {
//                                                                                            logo.setImageResource(R.drawable.perishables);
//                                                                                        } else if (getChild(groupPosition, childPosition).equals("boulangerie")) {
//                                                                                            logo.setImageResource(R.drawable.breads);
//                                                                                        } else {
//                                                                                            logo.setImageResource(R.drawable.nonperishables);
//                                                                                        }
//
//                                                                                        logo.setMinimumWidth(300);
//
//                                                                                        TextView textView1 = new TextView(MapsActivity.this);
//                                                                                        textView1.setTextColor(Color.BLACK);
//                                                                                        textView1.setTextSize(20);
//                                                                                        textView1.setWidth(200);
//                                                                                        textView1.setPadding(5, 55, 5, 5);
//                                                                                        textView1.setText(mEntreprise.getListDenree().get(childPosition).getNomDenree());
//                                                                                        TextView textView3 = new TextView(MapsActivity.this);
//                                                                                        textView3.setTextColor(Color.BLACK);
//                                                                                        textView3.setTextSize(20);
//                                                                                        textView3.setWidth(200);
//                                                                                        textView3.setPadding(5, 55, 5, 5);
//                                                                                        textView3.setText(mEntreprise.getListDenree().get(childPosition).getQuantiteDenree());
//                                                                                        Button btn = new Button(MapsActivity.this);
//                                                                                        btn.setText("reserver");
//                                                                                        btn.setPadding(5, 5, 5, 5);
//                                                                                        layout.addView(logo);
//                                                                                        layout.addView(textView1);
//                                                                                        layout.addView(textView3);
//                                                                                        layout.addView(btn);
//                                                                                        btn.setOnClickListener(new View.OnClickListener() {
//                                                                                            @Override
//                                                                                            public void onClick(View v) {
//                                                                                                mEntreprise.getListDenree().get(childPosition).setStateDenree(StateDenree.reserveee);
//                                                                                            }
//
//                                                                                        });
//
//                                                                                    }
//
//
//                                                                                    return layout;

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
                                                                            }
                                              );
                                              return false;
                                          }
                                      }
        );

        mMap.moveCamera(CameraUpdateFactory.newLatLng(shawiniganLatLng));
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        //configurer le listener pour group de l'expandablelistview
        expandableListView.setOnGroupClickListener(this);
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        parent.expandGroup(groupPosition);
        return false;
    }
}

