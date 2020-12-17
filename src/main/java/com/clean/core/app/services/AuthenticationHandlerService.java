package com.clean.core.app.services;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 * @param <UserType>
 * @param <PassType>
 * @param <AnswType>
 * @param <LogedUserType>
 */
public interface AuthenticationHandlerService<UserType, PassType, AnswType, LogedUserType> {

    public default AnswType login(UserType user, PassType pass) {
        return login(user, pass, new HashMap<>());
    }

    public AnswType login(UserType user, PassType pass, Map<String, Object> args);

    public LogedUserType user();

    public boolean checkAccess(Method method);
}
