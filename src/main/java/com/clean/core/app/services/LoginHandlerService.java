package com.clean.core.app.services;

/**
 *
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 */
public interface LoginHandlerService<UserType, PassType, ArgsType, AnswType> {

    public AnswType login(UserType user, PassType pass, ArgsType... args);

}
