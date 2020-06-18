package com.clean.core.domain;

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
            throw new IllegalStateException("Bad call");
        }
        return resourceService;
    }

    public String getString(String key) {
        return getResourceService().getString(key);
    }

    public Object getObject(String key) {
        return getResourceService().getObject(key);
    }
}
