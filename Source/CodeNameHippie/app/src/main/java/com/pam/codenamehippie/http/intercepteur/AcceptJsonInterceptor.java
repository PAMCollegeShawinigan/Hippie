package com.pam.codenamehippie.http.intercepteur;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.Util;

import java.io.IOException;

/**
 * Classe servant à ajouter les en-têtes http pour déclarer que l'application n'accepte que du
 * json.
 */
public final class AcceptJsonInterceptor implements Interceptor {

    public static final MediaType JSON_MEDIA_TYPE =
            MediaType.parse("application/json; charset=utf-8");
    private static final String ACCEPT_HEADER_NAME = "Accept";
    private static final String ACCEPT_HEADER_VALUE =
            JSON_MEDIA_TYPE.type() + "/" + JSON_MEDIA_TYPE.subtype();
    private static final String ACCEPT_CHARSET_HEADER_NAME = "Accept-Charset";
    private static final String ACCEPT_CHARSET_HEADER_VALUE = Util.UTF_8.name();

    private AcceptJsonInterceptor() {}

    public static AcceptJsonInterceptor newInstance() {
        return new AcceptJsonInterceptor();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                               .newBuilder()
                               .addHeader(ACCEPT_HEADER_NAME, ACCEPT_HEADER_VALUE)
                               .addHeader(ACCEPT_CHARSET_HEADER_NAME, ACCEPT_CHARSET_HEADER_VALUE)
                               .build();
        return chain.proceed(request);
    }
}
