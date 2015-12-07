package pam.yongshunli.hippiemaps;

import android.database.DataSetObserver;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ExpandableListView.OnGroupClickListener {

    private GoogleMap mMap;

    private ArrayList<Denree> listDenree1, listDenree2, listDenree3, listDenree4, listDenree5;
    private ArrayList<String> group_list1, group_list2, group_list3, group_list4, group_list5;
    private ArrayList<Entreprise> listEntreprise;
    private Entreprise entreprise1, entreprise2, entreprise3, entreprise4, entreprise5;
    private ListView listViewDenree, listViewEntreprise;
    private String[] attributsEntreprise;
    private TextView textViewEntrepriseInfo, textViewDenree;
    private ExpandableListView expandableListView;


    private ArrayList infoList;
    private ArrayList denreeList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_plus);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);











        entreprise1 = new Entreprise("denree Shawinigan", "120,62E RUE Shawinigan", "11:00-15:00", "819-000-1234", listDenree1);
        entreprise2 = new Entreprise("denree Montreal", "250,25E RUE Montreal", "11:00-15:00", "819-123-0000", listDenree2);
        entreprise3 = new Entreprise("denree Joliette", "156,5E RUE Joliette", "11:00-15:00", "819-040-0450", listDenree3);
        entreprise4 = new Entreprise("denree Victoriaville", "350,36E RUE Victoriaville", "11:00-15:00", "819-021-3214", listDenree4);
        entreprise5 = new Entreprise("denree Trois Rivieres", "168,77E RUE Trois Rivieres", "11:00-15:00", "819-000-4527", listDenree5);

        listDenree1 = new ArrayList<Denree>();
        listDenree1.add(new Denree("orange", 23, "kg", StateDenree.disponible, TypeDenree.fruit_legume));
        listDenree1.add(new Denree("avocat", 3, "kg", StateDenree.disponible, TypeDenree.fruit_legume));
        listDenree1.add(new Denree("prune", 7, "kg", StateDenree.disponible, TypeDenree.fruit_legume));
        listDenree1.add(new Denree("lait", 8, "kg", StateDenree.disponible, TypeDenree.fruit_legume));

        listDenree2 = new ArrayList<Denree>();
        listDenree2.add(new Denree("pomme", 23, "kg", StateDenree.disponible, TypeDenree.fruit_legume));
        listDenree2.add(new Denree("avocat", 3, "kg", StateDenree.disponible, TypeDenree.boulangerie));
        listDenree2.add(new Denree("rasin", 1, "kg", StateDenree.disponible, TypeDenree.laitier));
        listDenree2.add(new Denree("patate", 8, "kg", StateDenree.disponible, TypeDenree.fruit_legume));

        listDenree3 = new ArrayList<Denree>();
        listDenree3.add(new Denree("kiwi", 3, "kg", StateDenree.disponible, TypeDenree.boulangerie));
        listDenree3.add(new Denree("avocat", 3, "kg", StateDenree.disponible, TypeDenree.viande));
        listDenree3.add(new Denree("rasin", 21, "kg", StateDenree.disponible, TypeDenree.perissable));
        listDenree3.add(new Denree("carrot", 8, "kg", StateDenree.disponible, TypeDenree.non_perissable));

        listDenree4 = new ArrayList<Denree>();
        listDenree4.add(new Denree("samon", 4, "kg", StateDenree.disponible, TypeDenree.viande));
        listDenree4.add(new Denree("avocat", 33, "kg", StateDenree.disponible, TypeDenree.fruit_legume));
        listDenree4.add(new Denree("rasin", 21, "kg", StateDenree.disponible, TypeDenree.perissable));
        listDenree4.add(new Denree("lait", 8, "kg", StateDenree.disponible, TypeDenree.surgele));

        listDenree5 = new ArrayList<Denree>();
        listDenree5.add(new Denree("tuna", 2, "kg", StateDenree.disponible, TypeDenree.fruit_legume));
        listDenree5.add(new Denree("avocat", 3, "kg", StateDenree.disponible, TypeDenree.perissable));
        listDenree5.add(new Denree("rasin", 21, "kg", StateDenree.disponible, TypeDenree.boulangerie));
        listDenree5.add(new Denree("lait", 8, "kg", StateDenree.disponible, TypeDenree.surgele));

        listEntreprise = new ArrayList<Entreprise>();
        listEntreprise.add(entreprise1);
        listEntreprise.add(entreprise2);
        listEntreprise.add(entreprise3);
        listEntreprise.add(entreprise4);
        listEntreprise.add(entreprise5);





        final ArrayList<String> group_list1 = new ArrayList<>();
        group_list1.add(entreprise1.getNomEntreprise());
        group_list1.add("afficher les denree a donner");

        ArrayList<String> group_list2 = new ArrayList<>();
        group_list2.add(entreprise1.getNomEntreprise());
        group_list2.add("afficher les denree a donner");

        ArrayList<String> group_list3 = new ArrayList<>();
        group_list3.add(entreprise1.getNomEntreprise());
        group_list3.add("afficher les denree a donner");

        ArrayList<String> group_list4 = new ArrayList<>();
        group_list4.add(entreprise1.getNomEntreprise());
        group_list4.add("afficher les denree a donner");

        ArrayList<String> group_list5 = new ArrayList<>();
        group_list5.add(entreprise1.getNomEntreprise());
        group_list5.add("afficher les denree a donner");


        infoList=new ArrayList();
        for(int i=0;i<listEntreprise.size();++i){
            infoList.add(listEntreprise.get(i));
        }





        listViewEntreprise = (ListView) findViewById(R.id.lv_enterprise_info);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListView.setOnGroupClickListener(this);
        expandableListView.setAdapter(new ExpandableListAdapter() {
            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public int getGroupCount() {
                return group_list1.size();
            }

            @Override
            public int getChildrenCount(int groupPosition) {
                if(groupPosition==0){
                    return 3;
                }
                return 5;
            }

            @Override
            public Object getGroup(int groupPosition) {
                return null;
            }

            @Override
            public Object getChild(int groupPosition, int childPosition) {
                return null;
            }

            @Override
            public long getGroupId(int groupPosition) {
                return 0;
            }

            @Override
            public long getChildId(int groupPosition, int childPosition) {
                return 0;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {


                LinearLayout layout = new LinearLayout(MapsActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                ImageView logo = new ImageView(MapsActivity.this);
                logo.setImageResource(R.drawable.telephone);
                logo.setPadding(50, 0, 0, 0);
                layout.addView(logo);

                TextView textView = new TextView(MapsActivity.this);
                textView.setTextColor(Color.BLACK);
                textView.setText(group_list1.get(groupPosition));
                layout.addView(textView);
                return layout;
            }

            @Override
            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

                LinearLayout layout = new LinearLayout(MapsActivity.this);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                if(groupPosition==0){
                    ImageView logo = new ImageView(MapsActivity.this);
                    logo.setImageResource(R.drawable.telephone);
                    logo.setPadding(50, 0, 0, 0);
                    layout.addView(logo);

                    TextView textView = new TextView(MapsActivity.this);
                    textView.setTextColor(Color.BLACK);
                    textView.setText(group_list1.get(groupPosition));
                    layout.addView(textView);
                }else{
                    ImageView logo = new ImageView(MapsActivity.this);
                    logo.setImageResource(R.drawable.telephone);
                    logo.setPadding(50, 0, 0, 0);
                    layout.addView(logo);

                    TextView textView1 = new TextView(MapsActivity.this);
                    textView1.setTextColor(Color.BLACK);
                    textView1.setText(group_list1.get(groupPosition));
                    layout.addView(textView1);

                    TextView textView2 = new TextView(MapsActivity.this);
                    textView2.setTextColor(Color.BLACK);
                    textView2.setText(group_list1.get(groupPosition));
                    layout.addView(textView2);

                }
                return layout;
            }

            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return false;
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

        LatLng shawinigan = new LatLng(46.5618559, -72.7435254);
        LatLng montreal = new LatLng(45.5454532, -73.6390814);
        LatLng troisriviere = new LatLng(46.35088, -72.54806);
        LatLng joliette = new LatLng(46.02318, -73.44253);
        LatLng victoriaville = new LatLng(46.05837, -71.95025);


        final Marker markerShawinigan = mMap.addMarker(new MarkerOptions().position(shawinigan));
        final Marker markerMontreal = mMap.addMarker(new MarkerOptions().position(montreal));
        final Marker markerTroisriviere = mMap.addMarker(new MarkerOptions().position(troisriviere));
        final Marker markerJoliette = mMap.addMarker(new MarkerOptions().position(joliette));
        final Marker markerVictoriaville = mMap.addMarker(new MarkerOptions().position(victoriaville));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                          @Override
                                          public boolean onMarkerClick(Marker marker) {

                                              if (marker.equals(markerJoliette)) {
                                                  listViewEntreprise.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                                                          android.R.layout.simple_list_item_1,
                                                          new String[]{entreprise1.getNomEntreprise(), entreprise1.getAddresse(),
                                                                  entreprise1.getHeuresCollect(), entreprise1.getTelephone(), "afficher les denree a donner"}));
                                              } else if (marker.equals(markerMontreal)) {
                                                  listViewEntreprise.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                                                          android.R.layout.simple_list_item_1,
                                                          new String[]{entreprise2.getNomEntreprise(), entreprise2.getAddresse(),
                                                                  entreprise2.getHeuresCollect(), entreprise2.getTelephone(), "afficher les denree a donner"}));
                                              } else if (marker.equals(markerShawinigan)) {
                                                  listViewEntreprise.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                                                          android.R.layout.simple_list_item_1,
                                                          new String[]{entreprise3.getNomEntreprise(), entreprise3.getAddresse(),
                                                                  entreprise3.getHeuresCollect(), entreprise3.getTelephone(), "afficher les denree a donner"}));
                                              } else if (marker.equals(markerTroisriviere)) {
                                                  listViewEntreprise.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                                                          android.R.layout.simple_list_item_1,
                                                          new String[]{entreprise4.getNomEntreprise(), entreprise4.getAddresse(),
                                                                  entreprise4.getHeuresCollect(), entreprise4.getTelephone(), "afficher les denree a donner"}));
                                              } else {
                                                  listViewEntreprise.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                                                          android.R.layout.simple_list_item_1,
                                                          new String[]{entreprise5.getNomEntreprise(), entreprise5.getAddresse(),
                                                                  entreprise5.getHeuresCollect(), entreprise5.getTelephone(), "afficher les denree a donner"}));
                                              }

                                              return false;
                                          }
                                      }
        );


        mMap.moveCamera(CameraUpdateFactory.newLatLng(shawinigan));
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        if (group_list1.get(groupPosition).isEmpty()) {
            return true;
        }
        return false;
    }
}
