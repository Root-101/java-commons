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
package com.clean.core.app.services;

import com.clean.core.exceptions.AlreadyRegisteredService;
import java.util.Map;

/**
 * 
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 */
public class AuthenticationHandler {

    private static AuthenticationService authHandlerService;

    private AuthenticationHandler() {
    }

    public static void registerAuthHandlerService(AuthenticationService newService) {
        if (authHandlerService != null) {
            throw new AlreadyRegisteredService("Authentication");
        }
        authHandlerService = newService;
    }

    public static AuthenticationService getAuthHandlerService() {
        if (authHandlerService == null) {
            throw new IllegalStateException("Bad call");
        }
        return authHandlerService;
    }

    public static boolean login(Object user, Object pass) {
        return getAuthHandlerService().login(user, pass);
    }

    public static boolean login(Object user, Object pass, Map<String, Object> args) {
        return getAuthHandlerService().login(user, pass, args);
    }
    public static boolean logout() {
        return getAuthHandlerService().logout();
    }
}
