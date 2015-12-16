package com.pam.codenamehippie.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;

import com.pam.codenamehippie.R;

public class SplashScreenActivity extends HippieActivity {

    // minuterie d'écran du Splash screen 10000 = 10 sec
    private static final int SPLASH_TIME_OUT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_splash_land);

        //identifiant du vidéo
        VideoView videoView =
                (VideoView) this.findViewById(R.id.videoView);

        //le chemin du vidéo
        videoView.setVideoURI(Uri.parse("android.resource://" +
                                        this.getPackageName() + "/" + R.raw.denree_o_suivant));

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
                Intent i;
                if (SplashScreenActivity.this.authentificateur.estAuthentifie()) {
                    i = new Intent(SplashScreenActivity.this, MainActivity.class);
                } else {
                    i = new Intent(SplashScreenActivity.this, LoginActivity.class);

                }
                SplashScreenActivity.this.startActivity(i);
                // fermeture de l'activité
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_TIME_OUT);
    }

}