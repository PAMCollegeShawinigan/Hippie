package com.pam.codenamehippie.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.mock.MockApplication;
import android.widget.Button;
import android.widget.TextView;

import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.UtilisateurModele;
import com.pam.codenamehippie.modele.depot.UtilisateurModeleDepot;

public class ProfilActivity extends HippieActivity {
UtilisateurModele utilisateur;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);


        ((TextView) findViewById(R.id.tv_profil)).setText(R.string.tv_profil_name);
        ((TextView) findViewById(R.id.tv_profil_contact_name_placeholder)).setText("Yongshun Li");
        ((TextView) findViewById(R.id.tv_profil_email_placeholder)).setText("yongshunl@yahoo.ca");
        ((TextView) findViewById(R.id.tv_profil_phone_placeholder)).setText("819 539-6406");
        ((TextView) findViewById(R.id.tv_profil_moyen_contact_placeholder)).setText("telephone ou courriel");
        ((TextView) findViewById(R.id.tv_enterprise_section)).setText(R.string.tv_entreprise_section);
        ((TextView) findViewById(R.id.tv_profil_nom_entreprise_placeholder)).setText("College Shawinigan");
        ((TextView) findViewById(R.id.tv_profil_adresse_placeholder)).setText(" 2263 Avenue du Collège, Shawinigan, QC G9N 6V8");
        ((TextView) findViewById(R.id.tv_profil_phone2_placeholder)).setText("819 539-6406");
        ((TextView) findViewById(R.id.tv_profil_osbl_placeholder)).setText(R.string.tv_profil_osbl);
        ((TextView) findViewById(R.id.tv_profil_neq_placeholder)).setText(R.string.tv_profil_neq);
        ((Button) findViewById(R.id.btn_edit_profile)).setText(R.string.btn_edit_profile);
        ((Button) findViewById(R.id.btn_edit_password)).setText(R.string.btn_edit_password);



    }



}
