/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clean.core.repo;

import java.util.List;
import javax.swing.text.html.parser.Entity;

/**
 *
 * @author Jorge
 * @param <T>
 */
public interface CRUDRepository<T> extends AbstractRepository {

    public T create(T newObject) throws Exception;

    public T update(T objectToUpdate) throws Exception;

    public T destroy(T objecttToDestroy) throws Exception;

    public T findBy(Object keyId) throws Exception;

    public List<T> findAll() throws Exception;

    public default int count() throws Exception {
        return findAll().size();
    }
}
