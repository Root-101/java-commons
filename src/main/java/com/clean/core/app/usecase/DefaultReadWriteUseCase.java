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

    private transient final java.beans.PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);

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
        Domain d = readWriteRepo.read();
        firePropertyChange("read", null, d);
        return d;
    }

    @Override
    public void write(Domain object) throws Exception {
        readWriteRepo.write(object);
        firePropertyChange("write", null, object);
    }

    @Override
    public void addPropertyChangeListener(java.beans.PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(java.beans.PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    private void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
}
