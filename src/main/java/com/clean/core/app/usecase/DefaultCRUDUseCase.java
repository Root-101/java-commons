/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clean.core.app.usecase;

import com.clean.core.app.repo.CRUDRepository;
import java.util.List;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 * @param <Domain>
 */
public class DefaultCRUDUseCase<Domain> implements CRUDUseCase<Domain> {

    protected CRUDRepository<Domain> crudRepo;

    protected CRUDRepository<Domain> getRepo() {
        return crudRepo;
    }

    protected void setRepo(CRUDRepository<Domain> repo) {
        this.crudRepo = repo;
    }

    @Override
    public Domain create(Domain newObject) throws Exception {
        return crudRepo.create(newObject);
    }

    @Override
    public Domain update(Domain objectToUpdate) throws Exception {
        return crudRepo.update(objectToUpdate);
    }

    @Override
    public Domain destroy(Domain objecttToDestroy) throws Exception {
        return crudRepo.destroy(objecttToDestroy);
    }

    @Override
    public Domain destroyById(Object keyId) throws Exception {
        return crudRepo.destroyById(keyId);
    }

    @Override
    public Domain findBy(Object keyId) throws Exception {
        return crudRepo.findBy(keyId);
    }

    @Override
    public List<Domain> findAll() throws Exception {
        return crudRepo.findAll();
    }

    @Override
    public int count() throws Exception {
        return crudRepo.count();
    }

}
