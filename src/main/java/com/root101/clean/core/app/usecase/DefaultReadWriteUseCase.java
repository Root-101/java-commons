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
package com.root101.clean.core.app.usecase;

import com.root101.clean.core.app.repo.ReadWriteRepository;
import com.root101.clean.core.utils.validation.Validable;
import com.root101.clean.core.utils.validation.ValidationResult;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 * @param <Domain>
 */
public class DefaultReadWriteUseCase<Domain> implements ReadWriteUseCase<Domain> {

    protected transient final java.beans.PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);

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
    public Domain read() throws RuntimeException {
        Domain d = readWriteRepo.read();
        firePropertyChange("read", null, d);
        return d;
    }

    @Override
    public void write(Domain object) throws RuntimeException {
        validateDomain(object);

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

    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

    private ValidationResult validateDomain(Domain domain) throws RuntimeException {
        if (domain instanceof Validable) {
            return ((Validable) domain).validate().throwException();
        }
        return new ValidationResult();
    }
}
