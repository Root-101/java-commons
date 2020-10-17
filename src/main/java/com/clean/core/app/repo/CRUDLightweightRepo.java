/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clean.core.app.repo;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public interface CRUDLightweightRepo<T> extends CRUDRepository<T> {

    public void create_ligth(T newObject) throws Exception;

    public void edit_ligth(T objectToUpdate) throws Exception;

    public void destroy_ligth(T objectToDestroy) throws Exception;

    public void destroyById_ligth(Object keyId) throws Exception;
}
