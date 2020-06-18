package com.clean.core.domain;

import java.util.ResourceBundle;

/**
 *
 * @author Jorge
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public interface ResourceService {

    public String getString(String key);

    public Object getObject(String key);

    public ResourceBundle getResourceBundle();

    public void setResourceBundle(ResourceBundle newResourceBundle);
}
