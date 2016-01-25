package com.pam.codenamehippie.http.exception;

import java.io.IOException;

import okhttp3.Response;

/**
 * Classe d'exception pour les erreurs de réponse HTTP.
 */
public class HttpReponseException extends IOException {

    private static final long serialVersionUID = 5262084840890202457L;

    /**
     * Construit une exception dont le message est le paramètre est la valeur de la méthode
     * {@link Response#toString()} de la réponse passée en paramètre.
     *
     * @param response
     *         Réponse de la requête http
     */
    public HttpReponseException(Response response) {
        super(response.toString());
    }

    /**
     * Construit une exception dont le message est le paramètre est le message de détail suivit de
     * la valeur {@link Response#toString()} de la réponse passée en paramètre.
     *
     * @param response
     *         Réponse de la requête http
     * @param detailMessage
     *         Information supplémentaire
     */
    public HttpReponseException(Response response, String detailMessage) {
        super(detailMessage + "\nresponse: " + response.toString());
    }
}
