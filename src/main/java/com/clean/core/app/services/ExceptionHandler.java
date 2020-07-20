package com.clean.core.app.services;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class ExceptionHandler implements ExceptionHandlerService {

    private static final List<ExceptionHandlerService> exceptionHandlerService = new ArrayList<>();

    private ExceptionHandler() {
    }

    public static void registerExceptionHandlerService(ExceptionHandlerService newService) {
        exceptionHandlerService.add(newService);
    }

    @Override
    public void handleException(Exception ex) {
        exceptionHandlerService.forEach(service -> {
            service.handleException(ex);
        });
    }

}
