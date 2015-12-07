package com.pam.codenamehippie.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;
import com.pam.codenamehippie.R;

public class SplashScreen extends Activity {

    // minuterie d'écran du Splash screen 10000 = 10 sec
    private static int SPLASH_TIME_OUT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_land);

        //identifiant du vidéo
        final VideoView videoView =
                (VideoView) findViewById(R.id.videoView);

        //le chemin du vidéo
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.denree_o_suivant));

        //le démarrage du vidéo Logo dans le splash screen
        videoView.start();

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);

                // fermeture de l'activité
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}