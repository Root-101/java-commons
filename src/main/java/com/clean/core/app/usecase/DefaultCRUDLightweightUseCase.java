/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clean.core.app.usecase;

import com.clean.core.app.repo.CRUDLightweightRepo;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 * @param <Domain>
 */
public class DefaultCRUDLightweightUseCase<Domain> extends DefaultCRUDUseCase<Domain> implements CRUDLightweightUseCase<Domain> {

    protected CRUDLightweightRepo<Domain> crudLightRepo;

    @Override
    protected CRUDLightweightRepo<Domain> getRepo() {
        return crudLightRepo;
    }

    protected void setRepo(CRUDLightweightRepo<Domain> repo) {
        this.crudLightRepo = repo;
        super.setRepo(repo);
    }

    @Override
    public void create_light(Domain newObject) throws Exception {
        crudLightRepo.create_light(newObject);
        firePropertyChange("create_light", null, null);
    }

    @Override
    public void edit_light(Domain objectToUpdate) throws Exception {
        crudLightRepo.edit_light(objectToUpdate);
        firePropertyChange("edit_light", null, null);
    }

    @Override
    public void destroy_light(Domain objectToDestroy) throws Exception {
        crudLightRepo.destroy_light(objectToDestroy);
        firePropertyChange("destroy_light", null, null);
    }

    @Override
    public void destroyById_light(Object keyId) throws Exception {
        crudLightRepo.destroyById_light(keyId);
        firePropertyChange("destroyById_light", null, null);
    }

}
