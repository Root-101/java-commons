package com.clean.core.utils.validation.checkables;

/**
 *
 * @author Jorge
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 * @param <T>
 */
public interface Checkable<T> {

    public boolean check();

    public T getSource();

}
