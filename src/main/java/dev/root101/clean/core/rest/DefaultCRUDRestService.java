/*
 * Copyright 2021 Root101 (jhernandezb96@gmail.com, +53-5-426-8660).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Or read it directly from LICENCE.txt file at the root of this project.
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.root101.clean.core.rest;

import dev.root101.clean.core.app.usecase.*;
import dev.root101.clean.core.utils.validation.*;
import static dev.root101.clean.core.app.PropertyChangeConstrains.*;
import dev.root101.clean.core.app.domain.DomainObject;
import java.util.List;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 * @param <Domain>
 * @param <UseCase>
 */
public class DefaultCRUDRestService<Domain extends DomainObject, UseCase extends CRUDUseCase<Domain>> implements CRUDRestService<Domain> {

    private final boolean doFirePropertyChanges = false;//for the momento allways enabled
    protected transient final java.beans.PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);

    protected final UseCase crudUC;

    public DefaultCRUDRestService(UseCase crudRepo) {
        this.crudUC = crudRepo;
    }

    protected CRUDUseCase useCase() {
        return crudUC;
    }

    @Override
    public Domain create(Domain newObject) throws RuntimeException {
        firePropertyChange(BEFORE_CREATE, null, newObject);

        Domain domain = crudUC.create(newObject);

        firePropertyChange(AFTER_CREATE, null, domain);

        return domain;
    }

    @Override
    public Domain edit(Domain objectToUpdate) throws RuntimeException {
        firePropertyChange(BEFORE_EDIT, null, objectToUpdate);

        Domain domain = crudUC.edit(objectToUpdate);

        firePropertyChange(AFTER_EDIT, null, domain);

        return domain;
    }

    @Override
    public Domain destroy(Domain objectToDestroy) throws RuntimeException {
        firePropertyChange(BEFORE_DESTROY, null, objectToDestroy);

        Domain domain = crudUC.destroy(objectToDestroy);

        firePropertyChange(AFTER_DESTROY, null, domain);

        return domain;
    }

    @Override
    public Domain destroyById(Object keyId) throws RuntimeException {
        firePropertyChange(BEFORE_DESTROY_BY_ID, null, keyId);

        Domain domain = crudUC.destroyById(keyId);

        firePropertyChange(AFTER_DESTROY_BY_ID, null, domain);

        return domain;
    }

    @Override
    public Domain findBy(Object keyId) throws RuntimeException {
        firePropertyChange(BEFORE_FIND_BY, null, keyId);

        Domain domain = crudUC.findBy(keyId);

        firePropertyChange(AFTER_FIND_BY, null, domain);

        return domain;
    }

    @Override
    public List<Domain> findAll() throws RuntimeException {
        firePropertyChange(BEFORE_FIND_ALL, null, null);

        List<Domain> domain = crudUC.findAll();

        firePropertyChange(AFTER_FIND_ALL, null, domain);

        return domain;
    }

    @Override
    public int count() throws RuntimeException {
        firePropertyChange(BEFORE_COUNT, null, null);

        int count = crudUC.count();

        firePropertyChange(AFTER_COUNT, null, count);

        return count;
    }

    @Override
    public void addPropertyChangeListener(java.beans.PropertyChangeListener listener) {
        if (doFirePropertyChanges) {
            propertyChangeSupport.addPropertyChangeListener(listener);
        }
    }

    @Override
    public void removePropertyChangeListener(java.beans.PropertyChangeListener listener) {
        if (doFirePropertyChanges) {
            propertyChangeSupport.removePropertyChangeListener(listener);
        }
    }

    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        if (doFirePropertyChanges) {
            propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
        }
    }

}
