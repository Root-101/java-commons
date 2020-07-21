package com.clean.core.app.services;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public interface ExceptionHandlerService {

    public void handleException(Exception ex);

    public boolean contain(Exception ex);

    public boolean contain(String type);
}
