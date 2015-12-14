package com.pam.codenamehippie.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.pam.codenamehippie.R;
import com.pam.codenamehippie.ui.view.trianglemenu.TriangleLayout;
import com.pam.codenamehippie.ui.view.trianglemenu.TriangleImageView;
import com.pam.codenamehippie.ui.view.trianglemenu.TriangleLayout.OnCenterClickListener;
import com.pam.codenamehippie.ui.view.trianglemenu.TriangleLayout.OnItemClickListener;
import com.pam.codenamehippie.ui.view.trianglemenu.TriangleLayout.OnItemSelectedListener;
import com.pam.codenamehippie.ui.view.trianglemenu.TriangleLayout.OnRotationFinishedListener;

public class MenuActivity extends Fragment implements OnItemSelectedListener,
        OnItemClickListener,
        OnRotationFinishedListener,
        OnCenterClickListener {
   // public static final String ARG_LAYOUT = "layout";

    private TextView selectedTextView;
    private TextView selectedTextView2;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MenuActivity() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuActivity newInstance(String param1, String param2) {
        MenuActivity fragment = new MenuActivity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


//
//        // Set content view by passed extra
//        // Bundle extras = getIntent().getExtras();
//        // int layoutId = extras.getInt(ARG_LAYOUT);
//        this.setContentView(R.layout.main_circle_layout);
//
//        //intégrer les polices sur le menu
//        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "opensans_light.ttf");
//        TextView myTextview = (TextView)findViewById(R.id.main_selected_textView);
//        myTextview.setTypeface(myTypeface);
//
//        // Set listeners
//        TriangleLayout triangleMenu = (TriangleLayout) findViewById(R.id.main_circle_layout);
//        triangleMenu.setOnItemSelectedListener(this);
//        triangleMenu.setOnItemClickListener(this);
//        triangleMenu.setOnRotationFinishedListener(this);
//        triangleMenu.setOnCenterClickListener(this);
//
//
//
//        selectedTextView = (TextView) findViewById(R.id.main_selected_textView);
//        selectedTextView.setText(((TriangleImageView) triangleMenu
//                .getSelectedItem()).getName());
//
//        selectedTextView2 = (TextView) findViewById(R.id.main_selected_textView2);
//        selectedTextView2.setText(((TriangleImageView) triangleMenu
//                .getSelectedItem()).getName());
//
//
  }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {

        //intégrer les polices sur le menu
        Typeface myTypeface = Typeface.createFromAsset(getActivity().getAssets(), "opensans_light.ttf");
        TextView myTextview = (TextView)getView().findViewById(R.id.main_selected_textView);
        myTextview.setTypeface(myTypeface);

        // Set listeners
        TriangleLayout triangleMenu = (TriangleLayout) getView().findViewById(R.id.main_circle_layout);
        triangleMenu.setOnItemSelectedListener(this);
        triangleMenu.setOnItemClickListener(this);
        triangleMenu.setOnRotationFinishedListener(this);
        triangleMenu.setOnCenterClickListener(this);

        selectedTextView = (TextView) getView().findViewById(R.id.main_selected_textView);
        selectedTextView.setText(((TriangleImageView) triangleMenu
                .getSelectedItem()).getName());

<<<<<<< HEAD
=======
        selectedTextView2 = (TextView) getView().findViewById(R.id.main_selected_textView2);
        selectedTextView2.setText(((TriangleImageView) triangleMenu
                .getSelectedItem()).getName());
>>>>>>> origin/EricEtCatherine

        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.main_circle_layout, container, false);
    }

    @Override
    public void onItemSelected(View view, String name) {
        selectedTextView.setText(name);

        switch (view.getId()) {
            case R.id.main_Profil_image:
                // selection de image profil
                break;
            case R.id.main_Organisme_image:
                // selection de image carte des organismes
                break;
            case R.id.main_Statistique_image:
                // selection de image des statistiques
                break;
            case R.id.main_carte_image:
                // selection de la carte
                break;
            case R.id.main_réservation_image:
                // selection de les réservations
                break;
            case R.id.main_catégorie_image:
                // selection de les catégories
                break;
        }
    }

    @Override
    public void onItemClick(View view, String name) {

<<<<<<< HEAD
        Toast.makeText(getApplicationContext(),
                " Nouvelle activitée ",
=======
        Toast.makeText(this.getActivity().getApplicationContext(),
                " Allo ",
>>>>>>> origin/EricEtCatherine
                Toast.LENGTH_SHORT).show();

        switch (view.getId()) {
            case R.id.main_Profil_image:
                // clic sur image profil
                break;
            case R.id.main_Organisme_image:
                // clic sur image carte des organismes
                break;
            case R.id.main_Statistique_image:
                // clic sur image des statistiques
                break;
            case R.id.main_carte_image:
                // clic sur la carte
                break;
            case R.id.main_réservation_image:
                // clic sur les réservations
                break;
            case R.id.main_catégorie_image:
                // clic sur les catégories
                break;
        }
    }

    @Override
    public void onRotationFinished(View view, String name) {
        Animation animation = new RotateAnimation(0, 360, view.getWidth() / 2,
                view.getHeight() / 2);
        animation.setDuration(250);
        view.startAnimation(animation);
    }

    @Override
    public void onCenterClick() {
<<<<<<< HEAD
        Toast.makeText(getApplicationContext(), "LE CENTRE A ÉTÉ CLICKÉ",
=======
        Toast.makeText(this.getActivity().getApplicationContext(), "Center has been clicked",
>>>>>>> origin/EricEtCatherine
                Toast.LENGTH_SHORT).show();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
