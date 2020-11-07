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

    public void create_light(T newObject) throws Exception;

    public void edit_light(T objectToUpdate) throws Exception;

    public void destroy_light(T objectToDestroy) throws Exception;

    public void destroyById_light(Object keyId) throws Exception;
}
