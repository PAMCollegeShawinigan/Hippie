package com.pam.codenamehippie.ui.fonts;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

/**
 * Created by Catherine on 2016-02-02.
 *
 * Évite le memory leak pour les fonts.
 */
public class FontCache {
    private static Hashtable<String, Typeface> fontCache = new Hashtable<String, Typeface>();

    public static Typeface get(String name, Context context) {
        Typeface tf = fontCache.get(name);
        if(tf == null) {
            try {
                tf = Typeface.createFromAsset(context.getAssets(), name);
            }
            catch (Exception e) {
                return null;
            }
            fontCache.put(name, tf);
        }
        return tf;
    }
}
