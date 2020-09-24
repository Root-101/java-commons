/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clean.core.app.usecase;

import com.clean.core.app.repo.CRUDRepository;
import com.clean.core.utils.validation.Validable;
import com.clean.core.utils.validation.ValidationResult;
import java.util.List;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 * @param <Domain>
 */
public class DefaultCRUDUseCase<Domain> implements CRUDUseCase<Domain> {

    private transient final java.beans.PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);

    protected CRUDRepository<Domain> crudRepo;

    protected CRUDRepository<Domain> getRepo() {
        return crudRepo;
    }

    protected void setRepo(CRUDRepository<Domain> repo) {
        this.crudRepo = repo;
    }

    @Override
    public Domain create(Domain newObject) throws Exception {
        validateDomain(newObject);
        
        Domain d = crudRepo.create(newObject);
        firePropertyChange("create", null, d);
        return d;
    }

    @Override
    public Domain edit(Domain objectToUpdate) throws Exception {
        validateDomain(objectToUpdate);
        
        Domain d = crudRepo.edit(objectToUpdate);
        firePropertyChange("edit", null, d);
        return d;
    }

    @Override
    public Domain destroy(Domain objectToDestroy) throws Exception {
        Domain d = crudRepo.destroy(objectToDestroy);
        firePropertyChange("destroy", null, d);
        return d;
    }

    @Override
    public Domain destroyById(Object keyId) throws Exception {
        Domain d = crudRepo.destroyById(keyId);
        firePropertyChange("destroyById", null, d);
        return d;
    }

    @Override
    public Domain findBy(Object keyId) throws Exception {
        Domain d = crudRepo.findBy(keyId);
        firePropertyChange("findBy", null, d);
        return d;
    }

    @Override
    public List<Domain> findAll() throws Exception {
        List<Domain> d = crudRepo.findAll();
        firePropertyChange("findAll", null, d);
        return d;
    }

    @Override
    public int count() throws Exception {
        int c = crudRepo.count();
        firePropertyChange("count", null, c);
        return c;
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

    private ValidationResult validateDomain(Domain domain) throws Exception {
        if (domain instanceof Validable) {
            return ((Validable) domain).validate().throwException();
        }
        return new ValidationResult();
    }
}
