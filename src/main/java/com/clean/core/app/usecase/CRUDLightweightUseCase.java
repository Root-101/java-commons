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
public interface CRUDLightweightUseCase<T> extends CRUDUseCase<T> {

    public default void create_ligth(T newObject) throws Exception {
        create(newObject);
    }

    public default void edit_ligth(T objectToUpdate) throws Exception {
        edit(objectToUpdate);
    }

    public default void destroy_ligth(T objectToDestroy) throws Exception {
        destroy(objectToDestroy);
    }

    public default void destroyById_ligth(Object keyId) throws Exception {
        destroyById(keyId);
    }
}
