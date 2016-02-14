package com.pam.codenamehippie.http.intercepteur;

import android.util.Log;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Classe d'aide qui intercepte le traffic HTTP et qui Log le contenu dans Logcat
 */
public class HttpDebugInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        Log.d("HTTP",
              String.format(Locale.getDefault(),
                            "Envoi d'une requête %s sur %s%n%s",
                            request.method(),
                            request.url(),
                            request.headers()
                           )
             );
        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        Log.d("HTTP",
              String.format(Locale.getDefault(),
                            "Received response %d - %s for %s in %.1fms%n%s",
                            response.code(),
                            response.message(),
                            response.request().url(),
                            (t2 - t1) / 1e6d,
                            response.headers()
                           )
             );
        return response;
    }
}
