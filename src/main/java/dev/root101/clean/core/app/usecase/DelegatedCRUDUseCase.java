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
import dev.root101.clean.core.repo.CRUDRepository;
import dev.root101.clean.core.app.domain.DomainObject;
import dev.root101.clean.core.utils.validation.ValidationService;
import java.util.List;

/**
 * Delega las operaciones basicas del crud en el repo.
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 * @param <Domain>
 * @param <ID>
 * @param <CRUDRepo>
 */
public class DelegatedCRUDUseCase<Domain extends DomainObject<ID>, ID, CRUDRepo extends CRUDRepository<Domain, ID>> implements CRUDUseCase<Domain, ID> {

    private final boolean doValidateDomain = true;//for the moment allways enabled
    private final boolean doFirePropertyChanges = true;//for the moment allways enabled
    protected transient final java.beans.PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);

    protected final CRUDRepo crudRepo;

    public DelegatedCRUDUseCase(CRUDRepo crudRepo) {
        this.crudRepo = crudRepo;
    }

    protected CRUDRepo repo() {
        return crudRepo;
    }

    @Override
    public Domain create(Domain newObject) throws RuntimeException {
        firePropertyChange(BEFORE_CREATE, null, newObject);

        validateDomain(newObject);

        Domain d = crudRepo.create(newObject);

        firePropertyChange(AFTER_CREATE, null, d);

        return d;
    }

    @Override
    public Domain edit(Domain objectToUpdate) throws RuntimeException {
        firePropertyChange(BEFORE_EDIT, null, objectToUpdate);

        validateDomain(objectToUpdate);

        Domain d = crudRepo.edit(objectToUpdate);

        firePropertyChange(AFTER_EDIT, null, d);

        return d;
    }

    @Override
    public void destroy(Domain objectToDestroy) throws RuntimeException {
        firePropertyChange(BEFORE_DESTROY, null, objectToDestroy);

        crudRepo.destroy(objectToDestroy);

        firePropertyChange(AFTER_DESTROY, null, objectToDestroy);
    }

    @Override
    public void destroyById(ID keyId) throws RuntimeException {
        firePropertyChange(BEFORE_DESTROY_BY_ID, null, keyId);

        crudRepo.destroyById(keyId);

        firePropertyChange(AFTER_DESTROY_BY_ID, null, keyId);
    }

    @Override
    public Domain findBy(ID keyId) throws RuntimeException {
        firePropertyChange(BEFORE_FIND_BY, null, keyId);

        Domain d = crudRepo.findBy(keyId);

        firePropertyChange(AFTER_FIND_BY, null, d);

        return d;
    }

    @Override
    public List<Domain> findAll() throws RuntimeException {
        firePropertyChange(BEFORE_FIND_ALL, null, null);

        List<Domain> d = crudRepo.findAll();

        firePropertyChange(AFTER_FIND_ALL, null, d);

        return d;
    }

    @Override
    public long count() throws RuntimeException {
        firePropertyChange(BEFORE_COUNT, null, null);

        long c = crudRepo.count();

        firePropertyChange(AFTER_COUNT, null, c);

        return c;
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
