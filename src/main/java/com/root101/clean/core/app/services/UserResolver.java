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
public class UserResolver {

    private static UserResolverService userResolverService;

    private UserResolver() {
    }

    public static void registerUserResolverService(UserResolverService newService) {
        if (userResolverService != null) {
            throw new AlreadyRegisteredService("UserResolver");
        }
        Objects.requireNonNull(newService, "UserResolverService can't be null");

        userResolverService = newService;
    }

    public static UserResolverService getUserResolverService() {
        if (userResolverService == null) {
            throw new NoneRegisteredService("UserResolver");
        }
        return userResolverService;
    }

    public static <T> T resolveUser() throws RuntimeException {
        return (T) getUserResolverService().resolveUser();
    }

    public static <T> T resolveUser(Class<T> clazz) throws RuntimeException {
        return (T) getUserResolverService().resolveUser();
    }
}
