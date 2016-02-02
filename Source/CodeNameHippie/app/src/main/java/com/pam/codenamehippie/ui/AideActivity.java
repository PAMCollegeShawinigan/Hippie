package com.pam.codenamehippie.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.BaseModele;
import com.pam.codenamehippie.modele.MarchandiseModele;
import com.pam.codenamehippie.ui.adapter.AideAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AideActivity extends HippieActivity {

    private ExpandableListAdapter listAdapter;
    private ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aide);

        expandableListView = (ExpandableListView) findViewById(R.id.list_aide_group);
        listAdapter = new AideAdapter(this);
        expandableListView.setAdapter(listAdapter);
    }
}
