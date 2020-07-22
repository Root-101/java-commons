package com.clean.core.app.services;

import java.util.ArrayList;
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

    public static void handleException(Exception ex) {
        ex.printStackTrace();
        for (ExceptionHandlerService exc : exceptionHandlerService) {
            if (exc.contain(ex)) {
                exc.handleException(ex);
            }
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
