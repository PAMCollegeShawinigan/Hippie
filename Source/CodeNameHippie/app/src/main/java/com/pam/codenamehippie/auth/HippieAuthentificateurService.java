package com.pam.codenamehippie.auth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Point d'entrée au service d'authentification.
 */
public class HippieAuthentificateurService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        HippieAuthenticator authenticator = new HippieAuthenticator(this);
        return  authenticator.getIBinder();
    }
}
