package com.pam.codenamehippie.ui;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
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

public class MenuActivity extends Activity implements OnItemSelectedListener,
        OnItemClickListener,
        OnRotationFinishedListener,
        OnCenterClickListener {
   // public static final String ARG_LAYOUT = "layout";

    private TextView selectedTextView;
    private TextView selectedTextView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set content view by passed extra
        // Bundle extras = getIntent().getExtras();
        // int layoutId = extras.getInt(ARG_LAYOUT);
        this.setContentView(R.layout.main_circle_layout);

        //intégrer les polices sur le menu
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "opensans_light.ttf");
        TextView myTextview = (TextView)findViewById(R.id.main_selected_textView);
        myTextview.setTypeface(myTypeface);

        // Set listeners
        TriangleLayout triangleMenu = (TriangleLayout) findViewById(R.id.main_circle_layout);
        triangleMenu.setOnItemSelectedListener(this);
        triangleMenu.setOnItemClickListener(this);
        triangleMenu.setOnRotationFinishedListener(this);
        triangleMenu.setOnCenterClickListener(this);



        selectedTextView = (TextView) findViewById(R.id.main_selected_textView);
        selectedTextView.setText(((TriangleImageView) triangleMenu
                .getSelectedItem()).getName());

        selectedTextView2 = (TextView) findViewById(R.id.main_selected_textView2);
        selectedTextView2.setText(((TriangleImageView) triangleMenu
                .getSelectedItem()).getName());


    }

    @Override
    public void onItemSelected(View view, String name) {
        selectedTextView.setText(name);
        selectedTextView2.setText(name);

        switch (view.getId()) {
            case R.id.main_tap_image:
                // Handle calendar selection
                break;
            case R.id.main_cloud_image:
                // Handle cloud selection
                break;
            case R.id.main_facebook_image:
                // Handle facebook selection
                break;
            case R.id.main_key_image:
                // Handle key selection
                break;
            case R.id.main_profile_image:
                // Handle profile selection
                break;
            case R.id.main_calendar_image:
                // Handle tap selection
                break;
        }
    }

    @Override
    public void onItemClick(View view, String name) {

        Toast.makeText(getApplicationContext(),
                " Allo ",
                Toast.LENGTH_SHORT).show();

        switch (view.getId()) {
            case R.id.main_calendar_image:
                // Handle calendar click
                break;
            case R.id.main_cloud_image:
                // Handle cloud click
                break;
            case R.id.main_facebook_image:
                // Handle facebook click
                break;
            case R.id.main_key_image:
                // Handle key click
                break;
            case R.id.main_profile_image:
                // Handle profile click
                break;
            case R.id.main_tap_image:
                // Handle tap click
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
        Toast.makeText(getApplicationContext(), "Center has been clicked",
                Toast.LENGTH_SHORT).show();
    }


}
