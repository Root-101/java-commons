package com.clean.core.domain.services;

/**
 *
 * @author Jorge
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public interface ResourceService<T> {

    public String getString(String key);

    public T getObject(String key);

    public boolean contain(String key);
}
