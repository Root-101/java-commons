package com.clean.core.domain;

/**
 * 
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 */
public class ExceptionHandler {

    private static ExceptionHandlerService exceptionHandlerService;

    private ExceptionHandler() {
    }

    public static void registerResourceService(ExceptionHandlerService newService) {
        exceptionHandlerService = newService;
    }

    public static ExceptionHandlerService getResourceService() {
        if (exceptionHandlerService == null) {
            throw new IllegalStateException("Bad call");
        }
        return exceptionHandlerService;
    }

}
