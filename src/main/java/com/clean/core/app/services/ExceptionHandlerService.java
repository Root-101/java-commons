package com.clean.core.app.services;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public interface ExceptionHandlerService {

    public void handleException(Throwable ex);

    public boolean contain(Throwable ex);

    public boolean contain(Class type);
}
