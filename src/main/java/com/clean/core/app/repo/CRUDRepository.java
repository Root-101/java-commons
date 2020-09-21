package com.clean.core.app.repo;

import java.util.List;

/**
 *
 * @author Jorge
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 * @param <T>
 */
public interface CRUDRepository<T> extends AbstractRepository {

    public T create(T newObject) throws Exception;

    public T edit(T objectToEdit) throws Exception;

    public T destroy(T objectToDestroy) throws Exception;

    public T destroyById(Object keyId) throws Exception;

    public T findBy(Object keyId) throws Exception;

    public List<T> findAll() throws Exception;

    public default int count() throws Exception {
        return findAll().size();
    }

    public void addPropertyChangeListener(java.beans.PropertyChangeListener listener);

    public void removePropertyChangeListener(java.beans.PropertyChangeListener listener);
}
