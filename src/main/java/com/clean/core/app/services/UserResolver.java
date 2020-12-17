/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clean.core.app.services;

import com.clean.core.exceptions.AlreadyRegisteredService;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class UserResolver {

    private static UserResolverService userResolverService;

    private UserResolver() {
    }

    public static void registerUserResolverService(UserResolverService newService) {
        if (userResolverService != null) {
            throw new AlreadyRegisteredService("UserResolver");
        }
        userResolverService = newService;
    }

    public static UserResolverService getUserResolverService() {
        if (userResolverService == null) {
            throw new IllegalStateException("Bad call");
        }
        return userResolverService;
    }

    public static <T> T resolveUser() throws Exception {
        return (T) getUserResolverService().resolveUser();
    }

    public static <T> T resolveUser(Class<T> clazz) throws Exception {
        return (T) getUserResolverService().resolveUser();
    }
}
