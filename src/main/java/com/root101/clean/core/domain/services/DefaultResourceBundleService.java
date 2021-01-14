/*
 * Copyright 2021 Root101 (jhernandezb96@gmail.com, +53-5-426-8660).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Or read it directly from LICENCE.txt file at the root of this project.
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.root101.clean.core.domain.services;

import java.net.MalformedURLException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Default implementation of the {@code ResourceService}.
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 */
public class DefaultResourceBundleService implements ResourceService {

    private ResourceBundle resourceBundle = null;

    public DefaultResourceBundleService(ResourceBundle resourceBundle) {
        Objects.requireNonNull(resourceBundle, "Resource Bundle can't be null");

        this.resourceBundle = resourceBundle;
    }

    protected ResourceBundle getResourceBundle() {
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

    /**
     * Construye una instancia de ResourceService de un .properties interno de
     * la carpeta resources. Ejemplo: Carpeta:
     * (...)src/main/resource/some_file.properties Llamada:
     * DefaultResourceBundleService.buildInternal("some_file");
     *
     * @param resourceURL
     * @param locale
     * @return
     */
    public static ResourceService buildInternal(String resourceURL, Locale locale) {
        return new DefaultResourceBundleService(
                ResourceBundleUtils.fromInternalFile(resourceURL,
                        locale));
    }

    /**
     *
     * Igual que el {@code buildInternal(String resourceURL)} con el
     * Locale.getDefault()
     *
     * @param resourceURL
     * @return
     */
    public static ResourceService buildInternal(String resourceURL) {
        return new DefaultResourceBundleService(
                ResourceBundleUtils.fromInternalFile(resourceURL,
                        Locale.getDefault()));
    }

    /**
     * ResourceService.
     * buildExternal("C:\\Users\\Yo\\Desktop\\some_file")
     *
     * @param resourceURL
     * @param locale
     * @return
     * @throws MalformedURLException
     */
    public static ResourceService buildExternal(String resourceURL, Locale locale) throws MalformedURLException {
        return new DefaultResourceBundleService(
                ResourceBundleUtils.fromExternalFile(resourceURL,
                        locale));
    }

    public static ResourceService buildExternal(String resourceURL) throws MalformedURLException {
        return new DefaultResourceBundleService(
                ResourceBundleUtils.fromExternalFile(resourceURL,
                        Locale.getDefault()));
    }
}
