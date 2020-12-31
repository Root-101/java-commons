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

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 * @param <UserType>
 * @param <PassType>
 * @param <LogedUserType> 
 */
public interface AuthenticationService<UserType, PassType, LogedUserType> {

    public default boolean login(UserType user, PassType pass) {
        return login(user, pass, new HashMap<>());
    }

    public boolean login(UserType user, PassType pass, Map<String, Object> args);

    public boolean logout();

    //public boolean checkAccess(Method method){throw }
}
