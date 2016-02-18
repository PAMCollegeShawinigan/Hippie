package com.pam.codenamehippie.http.intercepteur;

import android.app.Application;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Classe servant à ajouter les en-têtes http pour déclarer la langue affichée par l'application
 */
public final class AcceptLanguageInterceptor implements Interceptor {

    private static final String ACCEPT_HEADER_NAME = "Accept-Language";

    private final Application application;

    private AcceptLanguageInterceptor(Application application) {
        this.application = application;
    }

    public static AcceptLanguageInterceptor newInstance(Application application) {
        return new AcceptLanguageInterceptor(application);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Locale locale = this.application.getResources().getConfiguration().locale;
        String value = locale.toString().replace('_', '-').toLowerCase();
        Request request = chain.request().newBuilder().addHeader(ACCEPT_HEADER_NAME, value).build();
        return chain.proceed(request);
    }
}
