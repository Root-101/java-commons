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
package com.root101.clean.core.app.rest;

import static com.root101.clean.core.app.PropertyChangeConstrains.*;
import com.root101.clean.core.app.usecase.*;
import com.root101.clean.core.utils.Licenced;
import java.util.List;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 * @param <Domain>
 */
public class DefaultRestService<Domain> implements CRUDRestService<Domain> {

    protected transient final java.beans.PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);

    protected CRUDUseCase<Domain> crudUC;

    @Override
    public Domain create(Domain newObject) throws RuntimeException {
        firePropertyChange(BEFORE_CREATE, null, newObject);

        Domain d = crudUC.create(newObject);

        firePropertyChange(AFTER_CREATE, null, d);

        return d;
    }

    @Override
    public Domain edit(Domain objectToUpdate) throws RuntimeException {
        firePropertyChange(BEFORE_EDIT, null, objectToUpdate);

        Domain d = crudUC.edit(objectToUpdate);

        firePropertyChange(AFTER_EDIT, null, d);
        
        return d;
    }

    @Override
    public Domain destroy(Domain objectToDestroy) throws RuntimeException {
        firePropertyChange(BEFORE_DESTROY, null, objectToDestroy);

        Domain d = crudUC.destroy(objectToDestroy);

        firePropertyChange(AFTER_DESTROY, null, d);
        
        return d;
    }

    @Override
    public Domain destroyById(Object keyId) throws RuntimeException {
        firePropertyChange(BEFORE_DESTROY_BY_ID, null, keyId);

        Domain d = crudUC.destroyById(keyId);

        firePropertyChange(AFTER_DESTROY_BY_ID, null, d);

        return d;
    }

    @Override
    public Domain findBy(Object keyId) throws RuntimeException {
        firePropertyChange(BEFORE_FIND_BY, null, keyId);
        
        Domain d = crudUC.findBy(keyId);

        firePropertyChange(AFTER_FIND_BY, null, d);

        return d;
    }

    @Override
    public List<Domain> findAll() throws RuntimeException {
        firePropertyChange(BEFORE_FIND_ALL, null, null);

        List<Domain> d = crudUC.findAll();

        firePropertyChange(AFTER_FIND_ALL, null, d);
        
        return d;
    }

    @Override
    public int count() throws RuntimeException {
        firePropertyChange(BEFORE_COUNT, null, null);

        int c = crudUC.count();

        firePropertyChange(AFTER_COUNT, null, c);

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

    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

}
