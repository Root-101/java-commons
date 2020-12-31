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

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 */
public class LicenceHandler {

    private static LicenceService licenceService;

    private LicenceHandler() {
    }

    public static void registerLicenceService(LicenceService newService) {
        if (licenceService != null) {
            throw new AlreadyRegisteredService("Licence");
        }
        licenceService = newService;
    }

    public static LicenceService getLicenceService() {
        if (licenceService == null) {
            throw new NoneRegisteredService("Licence");
        }
        return licenceService;
    }

    public static boolean isActive() {
        return getLicenceService().isActive();
    }

}
