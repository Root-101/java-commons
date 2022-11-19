/*
 * Copyright 2022 Root101 (jhernandezb96@gmail.com, +53-5-426-8660).
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
package dev.root101.clean.core.app.usecase;

import static dev.root101.clean.core.app.PropertyChangeConstrains.*;
import dev.root101.clean.core.repo.ReadWriteRepository;
import dev.root101.clean.core.app.domain.DomainObject;
import dev.root101.clean.core.utils.validation.ValidationService;

/**
 * Delega las operaciones basicas del crud en el repo.
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 * @param <Domain>
 * @param <CRUDRepo>
 */
public class DelegatedReadWriteUseCase<Domain extends DomainObject, CRUDRepo extends ReadWriteRepository<Domain>> implements ReadWriteUseCase<Domain> {

    private final boolean doValidateDomain = true;//for the momento allways enabled
    private final boolean doFirePropertyChanges = true;//for the momento allways enabled
    protected transient final java.beans.PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);

    protected CRUDRepo readWriteRepo;

    public DelegatedReadWriteUseCase(CRUDRepo repo) {
        this.readWriteRepo = repo;
    }

    protected CRUDRepo repo() {
        return readWriteRepo;
    }

    @Override
    public Domain read() throws RuntimeException {
        firePropertyChange(BEFORE_READ, null, null);

        Domain d = readWriteRepo.read();

        firePropertyChange(AFTER_READ, null, d);

        return d;
    }

    @Override
    public void write(Domain object) throws RuntimeException {
        firePropertyChange(BEFORE_WRITE, null, object);
        validateDomain(object);

        readWriteRepo.write(object);
        firePropertyChange(AFTER_WRITE, null, object);
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

    private void validateDomain(Domain domain) throws RuntimeException {
        if (doValidateDomain) {
            ValidationService.validateAndThrow(domain);
        }
    }
}
