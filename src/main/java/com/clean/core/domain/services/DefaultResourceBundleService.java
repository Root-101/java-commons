package com.clean.core.domain.services;

import java.util.ResourceBundle;

/**
 * Default implementation of the {@code ResourceService}.
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class DefaultResourceBundleService implements ResourceService {

    private ResourceBundle resourceBundle = null;

    public DefaultResourceBundleService(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public ResourceBundle getResourceBundle() {
        if (resourceBundle == null) {
            throw new NullPointerException("Not Resource Bundle registered.");
        }
        return resourceBundle;
    }

    public void setResourceBundle(ResourceBundle newResourceBundle) {
        resourceBundle = newResourceBundle;
    }

    @Override
    public String getString(String key) {//return only if contain key
        return getResourceBundle().containsKey(key) ? getResourceBundle().getString(key) : key;
    }

    @Override
    public Object getObject(String key) {//return only if contain key
        return getResourceBundle().containsKey(key) ? getResourceBundle().getObject(key) : null;
    }

    @Override
    public boolean contain(String key) {
        return getResourceBundle().containsKey(key);
    }

}
