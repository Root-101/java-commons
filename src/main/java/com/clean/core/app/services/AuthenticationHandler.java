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

    public static boolean login(Object user, Object pass) {
        return getAuthHandlerService().login(user, pass);
    }

    public static boolean login(Object user, Object pass, Map<String, Object> args) {
        return getAuthHandlerService().login(user, pass, args);
    }
    public static boolean logout() {
        return getAuthHandlerService().logout();
    }
}
