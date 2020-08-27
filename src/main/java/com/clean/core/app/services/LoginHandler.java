package com.clean.core.app.services;

/**
 * 
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class LoginHandler {

    private static LoginHandlerService loginHandlerService;

    private LoginHandler() {
    }

    public static void registerLoginHandlerService(LoginHandlerService newService) {
        loginHandlerService = newService;
    }

    public static LoginHandlerService getLoginHandlerService() {
        if (loginHandlerService == null) {
            throw new IllegalStateException("Bad call");
        }
        return loginHandlerService;
    }

}
