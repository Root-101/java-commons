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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 * @author jjhurtado@Github
 */
public class ResourceHandler {

    private static final List<ResourceService> resourceService = new ArrayList<>();

    static {
        Locale.setDefault(ResourceBundleUtils.SPANISH);
    }

    private ResourceHandler() {
    }

    public static void registerResourceService(ResourceService newService) {
        Objects.requireNonNull(newService, "ResourceService can't be null");

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

    public static ResourceService registerInternal(String internalFile, Locale locale) {
        ResourceService service = DefaultResourceBundleService.buildInternal(internalFile, locale);
        registerResourceService(service);
        return service;
    }

    public static ResourceService registerInternal(String internalFile) {
        ResourceService service = DefaultResourceBundleService.buildInternal(internalFile);
        registerResourceService(service);
        return service;
    }

    public static ResourceService registerExternal(String internalFile, Locale locale) throws MalformedURLException {
        ResourceService service = DefaultResourceBundleService.buildExternal(internalFile, locale);
        registerResourceService(service);
        return service;
    }

    public static ResourceService registerExternal(String internalFile) throws MalformedURLException {
        ResourceService service = DefaultResourceBundleService.buildExternal(internalFile);
        registerResourceService(service);
        return service;
    }
}
