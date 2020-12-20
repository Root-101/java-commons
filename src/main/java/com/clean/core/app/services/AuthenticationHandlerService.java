package com.clean.core.app.services;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 * @param <UserType>
 * @param <PassType>
 * @param <LogedUserType>
 */
public interface AuthenticationHandlerService<UserType, PassType, LogedUserType> {

    public default boolean login(UserType user, PassType pass) {
        return login(user, pass, new HashMap<>());
    }

    public boolean login(UserType user, PassType pass, Map<String, Object> args);

    public boolean logout();

    //public boolean checkAccess(Method method){throw }
}
