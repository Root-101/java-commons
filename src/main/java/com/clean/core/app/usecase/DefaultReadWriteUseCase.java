/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clean.core.app.usecase;

import com.clean.core.app.repo.ReadWriteRepository;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 * @param <Domain>
 */
public class DefaultReadWriteUseCase<Domain> implements ReadWriteUseCase<Domain> {

    protected ReadWriteRepository<Domain> readWriteRepo;

    public DefaultReadWriteUseCase() {
    }

    public DefaultReadWriteUseCase(ReadWriteRepository<Domain> repo) {
        this.readWriteRepo = repo;
    }

    protected ReadWriteRepository<Domain> getRepo() {
        return readWriteRepo;
    }

    protected void setRepo(ReadWriteRepository<Domain> repo) {
        this.readWriteRepo = repo;
    }

    @Override
    public Domain read() throws Exception {
        return readWriteRepo.read();
    }

    @Override
    public void write(Domain object) throws Exception {
        readWriteRepo.write(object);
    }

}
