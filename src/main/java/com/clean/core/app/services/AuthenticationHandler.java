package com.clean.core.app.services;

/**
 * 
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class AuthenticationHandler {

    private static AuthenticationHandlerService loginHandlerService;

    private AuthenticationHandler() {
    }

    public static void registerLoginHandlerService(AuthenticationHandlerService newService) {
        loginHandlerService = newService;
    }

    public static AuthenticationHandlerService getLoginHandlerService() {
        if (loginHandlerService == null) {
            throw new IllegalStateException("Bad call");
        }
        return loginHandlerService;
    }

}
