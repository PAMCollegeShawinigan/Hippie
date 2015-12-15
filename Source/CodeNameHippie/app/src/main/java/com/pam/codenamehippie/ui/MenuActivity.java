package com.pam.codenamehippie.ui;


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

    private TextView selectedTextView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private OnFragmentInteractionListener mListener;

    public MenuActivity() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuActivity newInstance(String param1) {
        MenuActivity fragment = new MenuActivity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }


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
        TriangleLayout triangleMenu = (TriangleLayout) getView().findViewById(R.id.main_menu_triangle);
        triangleMenu.setOnItemSelectedListener(this);
        triangleMenu.setOnItemClickListener(this);
        triangleMenu.setOnRotationFinishedListener(this);
        triangleMenu.setOnCenterClickListener(this);

        selectedTextView = (TextView) getView().findViewById(R.id.main_selected_textView);
        selectedTextView.setText(((TriangleImageView) triangleMenu
                .getSelectedItem()).getName());

        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.main_menu, container, false);
    }

    @Override
    public void onItemSelected(View view, String name) {
        selectedTextView.setText(name);

        switch (view.getId()) {
            case R.id.main_profil_image:
                // selection de image profil
                break;
            case R.id.main_organisme_image:
                // selection de image carte des organismes
                break;
            case R.id.main_statistique_image:
                // selection de image des statistiques
                break;
            case R.id.main_carte_image:
                // selection de la carte
                break;
            case R.id.main_reservation_image:
                // selection de les réservations
                break;
            case R.id.main_categorie_image:
                // selection de les catégories
                break;
        }
    }

    @Override
    public void onItemClick(View view, String name) {
        Toast.makeText(this.getActivity().getApplicationContext(),
                " Nouvelle activité ",
                Toast.LENGTH_SHORT).show();

        switch (view.getId()) {
            case R.id.main_profil_image:
                // clic sur image profil
                break;
            case R.id.main_organisme_image:
                // clic sur image carte des organismes
                break;
            case R.id.main_statistique_image:
                // clic sur image des statistiques
                break;
            case R.id.main_carte_image:
                // clic sur la carte
                break;
            case R.id.main_reservation_image:
                // clic sur les réservations
                break;
            case R.id.main_categorie_image:
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
        Toast.makeText(this.getActivity().getApplicationContext(), "LE CENTRE A ÉTÉ CLICKÉ",
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
