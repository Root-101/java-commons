package com.clean.core.domain;

import java.util.ResourceBundle;

/**
 * Default implementation of the {@code ResourceService}.
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class ResourceServiceImpl implements ResourceService {

    private static ResourceBundle resourceBundle = null;

    @Override
    public ResourceBundle getResourceBundle() {
        if (resourceBundle == null) {
            throw new NullPointerException("Not Resource Bundle registered.");
        }
        return resourceBundle;
    }

    @Override
    public void setResourceBundle(ResourceBundle newResourceBundle) {
        resourceBundle = newResourceBundle;
    }

    @Override
    public String getString(String key) {
        return getResourceBundle().getString(key);
    }

    @Override
    public Object getObject(String key) {
        return getResourceBundle().getObject(key);
    }

}
