package com.pam.codenamehippie.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.pam.codenamehippie.R;

public class MainActivity extends HippieActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        final String openSansBold = this.getResources().getString(R.string.arialrounded);
        Typeface font = Typeface.createFromAsset(this.getAssets(), openSansBold);



        Typeface typeface;
        typeface = Typeface.createFromAsset(this.getAssets(), "fonts/arialrounded.ttf");
        ((TextView) this.findViewById(R.id.textView5)).setTypeface(typeface);
        ((TextView) this.findViewById(R.id.textView10)).setTypeface(typeface);
        ((TextView) this.findViewById(R.id.textView17)).setTypeface(typeface);
        ((TextView) this.findViewById(R.id.textView7)).setTypeface(typeface);
        ((TextView) this.findViewById(R.id.textView11)).setTypeface(typeface);
        ((TextView) this.findViewById(R.id.textView13)).setTypeface(typeface);
        ((TextView) this.findViewById(R.id.textView15)).setTypeface(typeface);
    }
}



