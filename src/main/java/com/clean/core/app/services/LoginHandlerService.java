package com.clean.core.app.services;

import java.lang.reflect.Method;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 * @param <UserType>
 * @param <PassType>
 * @param <ArgsType>
 * @param <AnswType>
 * @param <LogedUserType>
 */
public interface LoginHandlerService<UserType, PassType, ArgsType, AnswType, LogedUserType> {

    public AnswType login(UserType user, PassType pass, ArgsType... args);

    public LogedUserType user();

    public boolean checkAccess(Method method);
}
