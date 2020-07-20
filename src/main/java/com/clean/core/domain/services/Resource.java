package com.clean.core.domain.services;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jorge
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class Resource {

    private static final List<ResourceService> resourceService = new ArrayList<>();

    private Resource() {
    }

    public static void registerResourceService(ResourceService newService) {
        resourceService.add(newService);
    }

    public static String getString(String key) {
        for (ResourceService res : resourceService) {
            if (res.contain(key)) {
                return res.getString(key);
            }
        }
        return key;
    }

    public static boolean contain(String key) {
        return resourceService.stream().anyMatch(res -> (res.contain(key)));
    }

    public static Object getObject(String key) {
        for (ResourceService res : resourceService) {
            if (res.contain(key)) {
                return res.getObject(key);
            }
        }
        return key;
    }
}
