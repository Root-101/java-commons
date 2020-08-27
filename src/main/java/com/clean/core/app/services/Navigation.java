package com.clean.core.app.services;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class Navigation {

    private static NavigationService navigationService;

    private Navigation() {
    }

    public static void registerNavigationService(NavigationService newService) {
        navigationService = newService;
    }

    public static NavigationService getNavigationService() {
        if (navigationService == null) {
            throw new IllegalStateException("Bad call");
        }
        return navigationService;
    }

    public static void navigateTo(String to, Object caller) {
        getNavigationService().navigateTo(to, caller);
    }
}
