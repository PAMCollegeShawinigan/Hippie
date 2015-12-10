package com.pam.codenamehippie.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.pam.codenamehippie.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        if (toolbar != null) {
            this.setSupportActionBar(toolbar);
        }

    }

}
