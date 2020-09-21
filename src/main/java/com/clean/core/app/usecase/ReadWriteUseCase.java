/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clean.core.app.usecase;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public interface ReadWriteUseCase<T> extends AbstractUseCase {

    public T read() throws Exception;

    public void write(T object) throws Exception;

    public void addPropertyChangeListener(java.beans.PropertyChangeListener listener);

    public void removePropertyChangeListener(java.beans.PropertyChangeListener listener);
}
