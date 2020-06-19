package com.clean.core.domain.services;

/**
 *
 * @author Jorge
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class Resource {

    private static ResourceService resourceService;

    private Resource() {
    }

    public static void registerResourceService(ResourceService newService) {
        resourceService = newService;
    }

    public static ResourceService getResourceService() {
        if (resourceService == null) {
            throw new IllegalStateException("There isn't any ResourceService registered");
        }
        return resourceService;
    }

    public static String getString(String key) {
        return getResourceService().getString(key);
    }

    public static Object getObject(String key) {
        return getResourceService().getObject(key);
    }
}
