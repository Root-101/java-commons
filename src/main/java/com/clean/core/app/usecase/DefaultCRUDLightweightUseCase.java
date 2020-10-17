/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clean.core.app.usecase;

import com.clean.core.app.repo.CRUDLightweightRepo;
import com.clean.core.app.repo.CRUDRepository;

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
    public void create_ligth(Domain newObject) throws Exception {
        crudLightRepo.create_ligth(newObject);
        firePropertyChange("create_light", null, null);
    }

    @Override
    public void edit_ligth(Domain objectToUpdate) throws Exception {
        crudLightRepo.edit_ligth(objectToUpdate);
        firePropertyChange("edit_light", null, null);
    }

    @Override
    public void destroy_ligth(Domain objectToDestroy) throws Exception {
        crudLightRepo.destroy_ligth(objectToDestroy);
        firePropertyChange("destroy_light", null, null);
    }

    @Override
    public void destroyById_ligth(Object keyId) throws Exception {
        crudLightRepo.destroyById_ligth(keyId);
        firePropertyChange("destroyById_light", null, null);
    }

}
