package com.clean.core.app.services;

import com.clean.core.exceptions.AlreadyRegisteredService;
import java.util.Map;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class AuthenticationHandler {

    private static AuthenticationHandlerService authHandlerService;

    private AuthenticationHandler() {
    }

    public static void registerAuthHandlerService(AuthenticationHandlerService newService) {
        if (authHandlerService != null) {
            throw new AlreadyRegisteredService("Authentication");
        }
        authHandlerService = newService;
    }

    public static AuthenticationHandlerService getAuthHandlerService() {
        if (authHandlerService == null) {
            throw new IllegalStateException("Bad call");
        }
        return authHandlerService;
    }

    public static <T> T login(Object user, Object pass) {
        return (T) getAuthHandlerService().login(user, pass);
    }

    public static <T> T login(Object user, Object pass, Map<String, Object> args) {
        return (T) getAuthHandlerService().login(user, pass, args);
    }
}
