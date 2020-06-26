package com.clean.core.app.repo;

/**
 *
 * @author Jorge
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 * @param <T>
 */
public interface ReadWriteRepository<T> extends AbstractRepository {

    public T read() throws Exception;

    public void write(T object) throws Exception;
}
