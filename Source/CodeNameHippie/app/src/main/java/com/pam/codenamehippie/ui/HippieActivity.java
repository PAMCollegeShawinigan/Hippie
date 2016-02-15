package com.pam.codenamehippie.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.http.Authentificateur;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import okhttp3.OkHttpClient;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.pam.codenamehippie.modele.depot.DepotManager.DEPOT_SERVICE;

/**
 * Classe de base pour toutes les {@link android.app.Activity} du projet. Sert principalement à
 * propager le menu principal dans l'action bar.
 */
public class HippieActivity extends AppCompatActivity implements ConnectionCallbacks,
                                                                 OnConnectionFailedListener {

    /**
     * @hide
     */
    @TargetApi(VERSION_CODES.M)
    @StringDef({
                       POWER_SERVICE,
                       WINDOW_SERVICE,
                       LAYOUT_INFLATER_SERVICE,
                       ACCOUNT_SERVICE,
                       ACTIVITY_SERVICE,
                       ALARM_SERVICE,
                       NOTIFICATION_SERVICE,
                       ACCESSIBILITY_SERVICE,
                       CAPTIONING_SERVICE,
                       KEYGUARD_SERVICE,
                       LOCATION_SERVICE,
                       //@hide: COUNTRY_DETECTOR,
                       SEARCH_SERVICE,
                       SENSOR_SERVICE,
                       STORAGE_SERVICE,
                       WALLPAPER_SERVICE,
                       VIBRATOR_SERVICE,
                       //@hide: STATUS_BAR_SERVICE,
                       CONNECTIVITY_SERVICE,
                       //@hide: UPDATE_LOCK_SERVICE,
                       //@hide: NETWORKMANAGEMENT_SERVICE,
                       NETWORK_STATS_SERVICE,
                       //@hide: NETWORK_POLICY_SERVICE,
                       WIFI_SERVICE,
                       WIFI_P2P_SERVICE,
                       //@hide: WIFI_RTT_SERVICE,
                       //@hide: ETHERNET_SERVICE,
                       NSD_SERVICE,
                       AUDIO_SERVICE,
                       FINGERPRINT_SERVICE,
                       MEDIA_ROUTER_SERVICE,
                       TELEPHONY_SERVICE,
                       TELEPHONY_SUBSCRIPTION_SERVICE,
                       CARRIER_CONFIG_SERVICE,
                       TELECOM_SERVICE,
                       CLIPBOARD_SERVICE,
                       INPUT_METHOD_SERVICE,
                       TEXT_SERVICES_MANAGER_SERVICE,
                       APPWIDGET_SERVICE,
                       //@hide: VOICE_INTERACTION_MANAGER_SERVICE,
                       //@hide: BACKUP_SERVICE,
                       DROPBOX_SERVICE,
                       //@hide: DEVICE_IDLE_CONTROLLER,
                       DEVICE_POLICY_SERVICE,
                       UI_MODE_SERVICE,
                       DOWNLOAD_SERVICE,
                       NFC_SERVICE,
                       BLUETOOTH_SERVICE,
                       //@hide: SIP_SERVICE,
                       USB_SERVICE,
                       LAUNCHER_APPS_SERVICE,
                       //@hide: SERIAL_SERVICE,
                       //@hide: HDMI_CONTROL_SERVICE,
                       INPUT_SERVICE,
                       DISPLAY_SERVICE,
                       USER_SERVICE,
                       RESTRICTIONS_SERVICE,
                       APP_OPS_SERVICE,
                       CAMERA_SERVICE,
                       PRINT_SERVICE,
                       CONSUMER_IR_SERVICE,
                       //@hide: TRUST_SERVICE,
                       TV_INPUT_SERVICE,
                       //@hide: NETWORK_SCORE_SERVICE,
                       USAGE_STATS_SERVICE,
                       MEDIA_SESSION_SERVICE,
                       BATTERY_SERVICE,
                       JOB_SCHEDULER_SERVICE,
                       //@hide: PERSISTENT_DATA_BLOCK_SERVICE,
                       MEDIA_PROJECTION_SERVICE,
                       MIDI_SERVICE,
                       DEPOT_SERVICE
               })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ServiceName { } // Ceci est pour fermer la trappe à Android Lint... Il y a
    protected Authentificateur authentificateur;
    protected OkHttpClient httpClient;
    protected SharedPreferences sharedPreferences;
    protected ViewSwitcher viewSwitcher;
    protected TextView progressBarTextView;
    protected ProgressBar progressBar;
    protected GoogleApiClient googleApiClient;
    protected ArrayMap<String, Boolean> permissionsResult = null;
    private Boolean progressBarEstAffichee = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.httpClient = ((HippieApplication) this.getApplication()).getHttpClient();
        this.authentificateur = ((Authentificateur) this.httpClient.authenticator());
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    protected void onStart() {
        if (this.googleApiClient != null) {
            this.googleApiClient.connect();
        }
        super.onStart();
        ConnectivityManager connectivityManager =
                ((ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE));
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            if (((activeNetwork != null) && !activeNetwork.isConnected()) ||
                (activeNetwork == null)) {
                WifiManager wifiManager = ((WifiManager) this.getSystemService(WIFI_SERVICE));
                if (((wifiManager != null) && !wifiManager.isWifiEnabled())) {
                    wifiManager.setWifiEnabled(true);
                }
                if (((activeNetwork != null) && !activeNetwork.isConnected()) ||
                    (activeNetwork == null)) {
                    // TODO: Internet check 2.0
                    new AlertDialog.Builder(this).setPositiveButton("OK", new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            HippieActivity.this.finish();
                        }
                    })
                                                 .setMessage(R.string.error_no_internet)
                                                 .setTitle(R.string.title_no_internet)
                                                 .show();
                }
            }
        }
    }

    @Override
    protected void onStop() {
        if (this.googleApiClient != null) {
            this.googleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO: Gérer le back stack comme du monde
        // http://developer.android.com/training/implementing-navigation/temporal.html
        switch (item.getItemId()) {
            case android.R.id.home: {
                Intent intent = this.getSupportParentActivityIntent();
                if ((intent != null) && (NavUtils.shouldUpRecreateTask(this, intent))) {
                    TaskStackBuilder.create(this)
                                    .addNextIntentWithParentStack(intent)
                                    .startActivities();
                } else {
                    NavUtils.navigateUpFromSameTask(this);
                }
            }
            return true;
            // FIXME: Utilisation temporaire pour afficher ListeMesDonsActivity
            case R.id.menu_profil:
                if (!this.getClass().equals(ProfilActivity.class)) {
                    this.startActivity(new Intent(this, ProfilActivity.class));
                }
                return true;
            case R.id.menu_un:
                // Invoque le menu si on est pas déjà dedans
                if (!this.getClass().equals(MenuActivity.class)) {
                    this.startActivity(new Intent(this, MenuActivity.class));
                }
                return true;
            case R.id.info:
                if (!this.getClass().equals(InfoActivity.class)) {
                    this.startActivity(new Intent(this, InfoActivity.class));
                }
                return true;
            case R.id.menu_deconnexion:
                this.authentificateur.deconnecter();
                this.startActivity(new Intent(this, LoginActivity.class));
                this.finish();
                return true;
            case R.id.menu_aide:
                if (!this.getClass().equals(AideActivity.class)) {
                    this.startActivity(new Intent(this,
                                                  AideActivity.class
                    ));
                }
                return true;
            // FIXME: Utilisation temporaire pour afficher ListeStatistiquesActivity
            case R.id.menu_statistique:
                if (!this.getClass().equals(ListeStatistiquesActivity.class)) {
                    this.startActivity(new Intent(this, ListeStatistiquesActivity.class));
                }
                return true;

            // FIXME: Utilisation temporaire pour afficher la page inscription
            case R.id.inscription:
                if (!this.getClass().equals(RegisterActivity.class)) {
                    this.startActivity(new Intent(this, RegisterActivity.class));
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ((permissions.length != 0) && (grantResults.length != 0)) {
            this.permissionsResult = new ArrayMap<>(permissions.length);
            for (int i = 0; i < permissions.length; i += 1) {
                this.permissionsResult.put(permissions[i], (grantResults[i] == PERMISSION_GRANTED));
            }
        }
    }

    /**
     * Fait la même chose que {@link AppCompatActivity#setContentView(int)} à la différence que
     * qu'on appelle {@link AppCompatActivity#setSupportActionBar(Toolbar)} si on trouve une
     * toolbar avec l'id toolbar.
     *
     * @param layoutResID
     *         Ressource de layout à gonfler.
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        // Configuration de l'action bar
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        if (toolbar != null) {
            this.setSupportActionBar(toolbar);
            ActionBar actionBar = this.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setLogo(R.drawable.logo);
                actionBar.setDisplayUseLogoEnabled(true);
                actionBar.setDisplayShowTitleEnabled(false);
                actionBar.setDisplayHomeAsUpEnabled(this.getSupportParentActivityIntent() != null);
            }
        }
        // Configuration du viewSwitcher
        this.viewSwitcher = ((ViewSwitcher) this.findViewById(R.id.main_view_switcher));
        if (this.viewSwitcher != null) {
            this.viewSwitcher.setInAnimation(this, android.R.anim.fade_in);
            this.viewSwitcher.setOutAnimation(this, android.R.anim.fade_out);
        }
        this.progressBar = ((ProgressBar) this.findViewById(R.id.main_progress));
        this.progressBarTextView = ((TextView) this.findViewById(R.id.tv_main_progress));
        if ((this.viewSwitcher != null) && (this.progressBar != null)) {
            this.progressBarEstAffichee = false;
        }
    }

    /**
     * Méthode de comodité pour afficher un layout de progress bar à l'aide d'un view switcher.
     */
    public void afficherLaProgressBar() {
        if (this.progressBarEstAffichee == null) {
            throw new IllegalStateException("Le layout de l'activité ne gère pas l'affichage de " +
                                            "progressbar");
        }
        if (!this.progressBarEstAffichee) {
            this.viewSwitcher.showPrevious();
            this.progressBarEstAffichee = !this.progressBarEstAffichee;
        }
    }

    /**
     * Méthode de comodité pour cacher un layout de progress bar à l'aide d'un view switcher.
     */
    public void cacherLaProgressbar() {
        if (this.progressBarEstAffichee == null) {
            throw new IllegalStateException("Le layout de l'activité ne gère pas l'affichage de " +
                                            "progressbar");
        }
        if (this.progressBarEstAffichee) {
            this.viewSwitcher.showNext();
            this.progressBarEstAffichee = !this.progressBarEstAffichee;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("Connection", "Google client failed to connect" + connectionResult.getErrorMessage());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public Object getSystemService(@ServiceName @NonNull String name) {
        return super.getSystemService(name);
    }
}
