package com.clean.core.app.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class ExceptionHandler {

    private static final List<ExceptionHandlerService> exceptionHandlerService = new ArrayList<>();

    private ExceptionHandler() {
    }

    public static void registerExceptionHandlerService(ExceptionHandlerService newService) {
        exceptionHandlerService.add(newService);
    }

    public static void handleException(Throwable ex) {
        System.out.println("Handling Exception: " + ex.getMessage());
        System.out.println(Arrays.toString(ex.getStackTrace()));
        boolean found = false;
        for (ExceptionHandlerService exc : exceptionHandlerService) {
            if (exc.contain(ex)) {
                exc.handleException(ex);
                found = true;
            }
        }
        //si no se encontro y no es una Exception generica, la convierto a generica y la proceso
        if (!found && !ex.getClass().toString().equals(Exception.class.toString())) {
            handleException(new Exception(ex));
        }
    }

    public static boolean contain(Class type) {
        if (exceptionHandlerService.stream().anyMatch(excep -> (excep.contain(type)))) {
            return true;
        }
        return false;
    }

    public static boolean contain(Exception ex) {
        if (exceptionHandlerService.stream().anyMatch(excep -> (excep.contain(ex)))) {
            return true;
        }
        return false;
    }
}
