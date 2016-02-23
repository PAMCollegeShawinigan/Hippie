package com.pam.codenamehippie.ui.util;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe d'utilité pour les maths utilisé dans les vues. Cette classe utilise des caches pour
 * accélérer les conversions
 */
public final class Math {

    private static final Map<Float, Float> pxCache = new HashMap<>();
    private static final Map<Integer, Integer> pxIntCache = new HashMap<>();
    private static final Map<Float, Float> dpCache = new HashMap<>();
    private static final Map<Integer, Integer> dpIntCache = new HashMap<>();

    /**
     * Constructeur privé pour éviter d'instancier cette classe
     */
    private Math() {}

    public static Float convertirPixelsEnDp(@NonNull Float px) {
        Float v = dpCache.get(px);
        if (v == null) {
            synchronized (dpCache) {
                DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
                v = px / (metrics.densityDpi / 160f);
                dpCache.put(px, v);
            }
        }
        return v;
    }

    public static Integer convertirPixelsEnDp(@NonNull Integer px) {
        Integer v = dpIntCache.get(px);
        if (v == null) {
            synchronized (dpIntCache) {
                v = java.lang.Math.round(convertirPixelsEnDp(px.floatValue()));
                dpIntCache.put(px, v);
            }
        }
        return v;
    }

    public static Float convertirDpEnPixels(@NonNull Float dp) {
        Float v = pxCache.get(dp);
        if (v == null) {
            synchronized (pxCache) {
                DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
                v = dp * (metrics.densityDpi / 160f);
                pxCache.put(dp, v);
            }
        }
        return v;
    }

    public static Integer convertirDpEnPixels(@NonNull Integer dp) {
        Integer v = pxIntCache.get(dp);
        if (v == null) {
            synchronized (pxIntCache) {
                v = java.lang.Math.round(convertirDpEnPixels(dp.floatValue()));
                pxIntCache.put(dp, v);
            }
        }
        return v;
    }
}
