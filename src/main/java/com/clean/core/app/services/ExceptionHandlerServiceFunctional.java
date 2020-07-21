/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clean.core.app.services;

import java.util.HashMap;
import java.util.function.Consumer;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public abstract class ExceptionHandlerServiceFunctional implements ExceptionHandlerService {

    private final HashMap<String, Consumer<Exception>> exceptionsMap = new HashMap<>();

    public ExceptionHandlerServiceFunctional() {
        addAll();
    }

    protected abstract void addAll();

    public final void addHandler(String type, Consumer<Exception> consumer) {
        exceptionsMap.put(type, consumer);
    }

    @Override
    public void handleException(Exception ex) {
        handleExceptionInternal(getExceptionType(ex), ex);
    }

    private void handleExceptionInternal(String type, Exception ex) {
        exceptionsMap.get(type).accept(ex);
    }

    @Override
    public boolean contain(Exception ex) {
        return contain(getExceptionType(ex));
    }

    @Override
    public boolean contain(String type) {
        return exceptionsMap.containsKey(type);
    }

    public static String getExceptionType(Exception ex) {
        String split[] = ex.getClass().toString().split(" ");
        return split[split.length - 1];
    }
}
