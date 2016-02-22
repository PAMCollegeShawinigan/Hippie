package com.pam.codenamehippie.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.VideoView;

import com.pam.codenamehippie.R;

public class SplashScreenActivity extends HippieActivity {

    // minuterie d'écran du Splash screen 10000 = 10 sec
    private static final int SPLASH_TIME_OUT = 10000;
    private final Handler handler = new Handler();
    private final Runnable endSplashScreen = new Runnable() {
        @Override
        public void run() {
            if (!SplashScreenActivity.this.isFinishing()) {
                SplashScreenActivity.this.videoView.stopPlayback();
                SplashScreenActivity.this.handler.removeCallbacks(this);
                Intent i;
                if (SplashScreenActivity.this.authentificateur.estAuthentifie()) {
                    i = new Intent(SplashScreenActivity.this, MenuActivity.class);
                } else {
                    i = new Intent(SplashScreenActivity.this, LoginActivity.class);

                }
                SplashScreenActivity.this.startActivity(i);
                // fermeture de l'activité
                SplashScreenActivity.this.finish();
            }
        }
    };
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_splash_land);

        //identifiant du vidéo
        this.videoView = (VideoView) this.findViewById(R.id.videoView);

        //le chemin du vidéo
        this.videoView.setVideoURI(Uri.parse("android.resource://" +
                                             this.getPackageName() + "/" + R.raw.denree_o_suivant));

        //le démarrage du vidéo Logo dans le splash screen
        this.videoView.start();
        this.handler.postDelayed(this.endSplashScreen, SPLASH_TIME_OUT);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.endSplashScreen.run();
        return super.onTouchEvent(event);
    }
}