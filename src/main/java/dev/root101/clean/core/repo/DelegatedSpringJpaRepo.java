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
import dev.root101.clean.core.app.domain.DomainObject;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 * @param <Domain>
 * @param <Entity>
 * @param <ID>
 * @param <GeneralConverter>
 * @param <SpringJpaRepo>
 */
public class DelegatedSpringJpaRepo<Domain extends DomainObject<ID>, Entity, ID, GeneralConverter extends Converter<Domain, Entity>, SpringJpaRepo extends JpaRepository<Entity, ID>> implements CRUDRepository<Domain, ID> {

    private final boolean doFirePropertyChanges = false;//for the momento allways enabled
    protected transient final java.beans.PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);

    protected final SpringJpaRepo externalRepo;
    protected final GeneralConverter converter;

    public DelegatedSpringJpaRepo(SpringJpaRepo externalRepo, GeneralConverter converter) {
        this.externalRepo = externalRepo;
        this.converter = converter;
    }

    protected SpringJpaRepo repo() {
        return externalRepo;
    }

    @Override
    public Domain create(Domain newObject) throws RuntimeException {
        firePropertyChange(BEFORE_CREATE, null, newObject);

        //convert domain to entity
        Entity entity = converter.toEntity(newObject);

        //do the persist
        entity = externalRepo.save(entity);

        //convert the domain back
        newObject = converter.toDomain(entity);

        firePropertyChange(AFTER_CREATE, null, newObject);

        return newObject;
    }

    @Override
    public Domain edit(Domain objectToUpdate) throws RuntimeException {
        firePropertyChange(BEFORE_EDIT, null, objectToUpdate);

        //convert domain to entity
        Entity entity = converter.toEntity(objectToUpdate);

        //do the persist
        entity = externalRepo.save(entity);

        //convert the domain back
        objectToUpdate = converter.toDomain(entity);

        firePropertyChange(AFTER_CREATE, null, objectToUpdate);

        return objectToUpdate;
    }

    @Override
    public void destroy(Domain objectToDestroy) throws RuntimeException {
        firePropertyChange(BEFORE_DESTROY, null, objectToDestroy);

        //convert domain to entity
        Entity entity = converter.toEntity(objectToDestroy);

        //do the persist
        externalRepo.delete(entity);

        firePropertyChange(AFTER_DESTROY, null, objectToDestroy);
    }

    @Override
    public void destroyById(ID keyId) throws RuntimeException {
        firePropertyChange(BEFORE_DESTROY_BY_ID, null, keyId);

        //do the destroy by key, returned the entity
        externalRepo.deleteById(keyId);

        firePropertyChange(AFTER_DESTROY_BY_ID, null, keyId);
    }

    @Override
    public Domain findBy(ID keyId) throws RuntimeException {
        firePropertyChange(BEFORE_FIND_BY, null, keyId);

        //do the findBy, returned the entity
        Optional<Entity> finded = externalRepo.findById(keyId);
        Entity entity = finded.isPresent() ? finded.get() : null;

        //check if entity exists
        if (entity == null) {
            firePropertyChange(AFTER_FIND_BY, null, null);

            return null;
        }

        //convert the domain back
        Domain objectFinded = converter.toDomain(entity);

        firePropertyChange(AFTER_FIND_BY, null, objectFinded);

        return objectFinded;
    }

    @Override
    public List<Domain> findAll() throws RuntimeException {
        firePropertyChange(BEFORE_FIND_ALL, null, null);

        List<Entity> allEntities = externalRepo.findAll();

        if (allEntities == null) {
            firePropertyChange(AFTER_FIND_ALL, null, null);
            return null;
        }

        List<Domain> list = converter.toDomainAll(externalRepo.findAll());

        firePropertyChange(AFTER_FIND_ALL, null, list);

        return list;
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
