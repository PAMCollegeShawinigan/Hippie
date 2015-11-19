package com.pam.codenamehippie.auth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * "Point d'entrée" au service d'authentification.
 * <p>
 * Cette classe attache un {@link HippieAuthenticator} au système de gestion de compte d'Android.
 *
 * @see <a href="http://developer.android.com/training/sync-adapters/creating-authenticator.html">
 * Creating a Stub Authenticator</a>
 */
public class HippieAuthenticatorService extends Service {

    /**
     * Champ pour stocker l'authentificateur à notre service web.
     */
    private HippieAuthenticator authenticator;

    @Override
    public void onCreate() {
        super.onCreate();
        this.authenticator = new HippieAuthenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.authenticator.getIBinder();
    }
}
