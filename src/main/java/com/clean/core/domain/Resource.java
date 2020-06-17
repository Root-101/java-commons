/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clean.core.domain;

/**
 *
 * JoBits
 *
 * @author Jorge
 *
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

}
