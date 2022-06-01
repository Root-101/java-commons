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
package dev.root101.clean.core.repo;

import static dev.root101.clean.core.app.PropertyChangeConstrains.*;
import dev.root101.clean.core.repo.external_repo.CRUDExternalRepository;
import dev.root101.clean.core.app.domain.DomainObject;
import dev.root101.clean.core.utils.Licenced;
import java.util.List;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 * @param <Domain>
 * @param <Entity>
 * @param <ID>
 * @param <GeneralConverter>
 * @param <ExternalRepo>
 */
@Licenced
public class DefaultCRUDRepo<Domain extends DomainObject<ID>, Entity, ID, GeneralConverter extends Converter<Domain, Entity>, ExternalRepo extends CRUDExternalRepository<Entity, ID>> implements CRUDRepository<Domain, ID> {

    private final boolean doFirePropertyChanges = false;//for the momento allways enabled
    protected transient final java.beans.PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);

    protected final ExternalRepo externalRepo;
    protected final GeneralConverter converter;

    public DefaultCRUDRepo(ExternalRepo externalRepo, GeneralConverter converter) {
        this.externalRepo = externalRepo;
        this.converter = converter;
    }

    protected ExternalRepo repo() {
        return externalRepo;
    }

    @Licenced
    @Override
    public Domain create(Domain newObject) throws RuntimeException {
        firePropertyChange(BEFORE_CREATE, null, newObject);

        //convert domain to entity
        Entity entity = converter.toEntity(newObject);

        //do the persist
        entity = externalRepo.create(entity);

        //convert the domain back
        newObject = converter.toDomain(entity);

        firePropertyChange(AFTER_CREATE, null, newObject);

        return newObject;
    }

    @Licenced
    @Override
    public Domain edit(Domain objectToUpdate) throws RuntimeException {
        firePropertyChange(BEFORE_EDIT, null, objectToUpdate);

        //convert domain to entity
        Entity entity = converter.toEntity(objectToUpdate);

        //do the persist
        entity = externalRepo.edit(entity);

        //convert the domain back
        objectToUpdate = converter.toDomain(entity);

        firePropertyChange(AFTER_CREATE, null, objectToUpdate);

        return objectToUpdate;
    }

    @Licenced
    @Override
    public void destroy(Domain objectToDestroy) throws RuntimeException {
        firePropertyChange(BEFORE_DESTROY, null, objectToDestroy);

        //convert domain to entity
        Entity entity = converter.toEntity(objectToDestroy);

        //do the persist
        externalRepo.destroy(entity);

        firePropertyChange(AFTER_DESTROY, null, objectToDestroy);
    }

    @Licenced
    @Override
    public void destroyById(ID keyId) throws RuntimeException {
        firePropertyChange(BEFORE_DESTROY_BY_ID, null, keyId);

        //do the destroy by key, returned the entity
        externalRepo.destroyById(keyId);

        firePropertyChange(AFTER_DESTROY_BY_ID, null, keyId);
    }

    @Override
    public Domain findBy(ID keyId) throws RuntimeException {
        firePropertyChange(BEFORE_FIND_BY, null, keyId);

        //do the findBy, returned the entity
        Entity entity = externalRepo.findBy(keyId);

        //convert the domain back
        Domain objectDestroyed = converter.toDomain(entity);

        firePropertyChange(AFTER_FIND_BY, null, objectDestroyed);

        return objectDestroyed;
    }

    @Override
    public List<Domain> findAll() throws RuntimeException {
        firePropertyChange(BEFORE_FIND_ALL, null, null);

        List<Domain> d = converter.toDomainAll(externalRepo.findAll());

        firePropertyChange(AFTER_FIND_ALL, null, d);

        return d;
    }

    @Override
    public long count() throws RuntimeException {
        firePropertyChange(BEFORE_COUNT, null, null);

        long c = externalRepo.count();

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

}
