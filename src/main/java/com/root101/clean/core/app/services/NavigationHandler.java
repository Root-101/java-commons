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
package com.root101.clean.core.app.services;

import com.root101.clean.core.exceptions.AlreadyRegisteredService;
import com.root101.clean.core.exceptions.NoneRegisteredService;
import java.util.Objects;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 */
public class NavigationHandler {

    private static NavigationService navigationService;

    private NavigationHandler() {
    }

    public static void registerNavigationService(NavigationService newService) {
        if (navigationService != null) {
            throw new AlreadyRegisteredService("Navigation");
        }
        Objects.requireNonNull(newService, "NavigationService can't be null");

        navigationService = newService;
    }

    public static NavigationService getNavigationService() {
        if (navigationService == null) {
            throw new NoneRegisteredService("Navigation");
        }
        return navigationService;
    }

    public static void navigateTo(String to, Object caller) {
        getNavigationService().navigateTo(to, caller);
    }
}
