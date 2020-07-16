/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clean.core.app.usecase;

import java.util.List;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public interface CRUDUseCase<T> extends AbstractUseCase {

    public T create(T newObject) throws Exception;

    public T update(T objectToUpdate) throws Exception;

    public T destroy(T objectToDestroy) throws Exception;

    public T destroyById(Object keyId) throws Exception;

    public T findBy(Object keyId) throws Exception;

    public List<T> findAll() throws Exception;

    public default int count() throws Exception {
        return findAll().size();
    }
}
