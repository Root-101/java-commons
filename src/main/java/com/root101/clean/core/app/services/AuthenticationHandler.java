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
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 */
public class AuthenticationHandler {

    private static AuthenticationService authenticationService;

    private AuthenticationHandler() {
    }

    public static void registerAuthenticationService(AuthenticationService newService) {
        if (authenticationService != null) {
            throw new AlreadyRegisteredService("Authentication");
        }
        Objects.requireNonNull(newService, "AuthenticationService can't be null");
        authenticationService = newService;
    }

    public static AuthenticationService getAuthenticationService() {
        if (authenticationService == null) {
            throw new NoneRegisteredService("Authentication");
        }
        return authenticationService;
    }

    public static boolean login(Object user, Object pass) {
        System.out.printf("-----AUTHENTICATION_HANDLER----- > Logging user '%s' | No extra args\n", user);
        return getAuthenticationService().login(user, pass);
    }

    public static boolean login(Object user, Object pass, Map<String, Object> args) {
        System.out.printf("-----AUTHENTICATION_HANDLER----- > Logging user '%s' | '%s' extra args\n", user, args.size());
        return getAuthenticationService().login(user, pass, args);
    }

    public static boolean logout() {
        System.out.println("-----AUTHENTICATION_HANDLER----- > Logout current user");
        return getAuthenticationService().logout();
    }
}
