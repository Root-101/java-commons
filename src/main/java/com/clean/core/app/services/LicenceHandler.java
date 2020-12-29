package com.clean.core.app.services;

import com.clean.core.exceptions.AlreadyRegisteredService;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class LicenceHandler {

    private static LicenceService licenceService;

    private LicenceHandler() {
    }

    public static void registerLicenceService(LicenceService newService) {
        if (licenceService != null) {
            throw new AlreadyRegisteredService("Licence");
        }
        licenceService = newService;
    }

    public static LicenceService getLicenceService() {
        if (licenceService == null) {
            throw new IllegalStateException("Bad call");
        }
        return licenceService;
    }

    public static boolean isActive() {
        return getLicenceService().isActive();
    }

}
