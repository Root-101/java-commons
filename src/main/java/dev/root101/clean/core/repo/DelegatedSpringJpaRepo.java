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

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdez960717@Github
 * @param <Domain>
 * @param <Entity>
 * @param <ID>
 * @param <GeneralConverter>
 * @param <SpringJpaRepo>
 */
public class DelegatedSpringJpaRepo<Domain, Entity, ID, GeneralConverter extends Converter<Domain, Entity>, SpringJpaRepo extends JpaRepository<Entity, ID>> implements CRUDRepository<Domain, ID> {

    protected final SpringJpaRepo jpaRepo;
    protected final GeneralConverter converter;

    public DelegatedSpringJpaRepo(SpringJpaRepo externalRepo, GeneralConverter converter) {
        this.jpaRepo = externalRepo;
        this.converter = converter;
    }

    protected SpringJpaRepo repo() {
        return jpaRepo;
    }

    @Override
    public Domain create(Domain newObject) throws RuntimeException {
        //convert domain to entity
        Entity entity = converter.toEntity(newObject);

        //do the persist
        entity = jpaRepo.save(entity);

        //convert the domain back
        return converter.toDomain(entity);
    }

    @Override
    public List<Domain> createAll(List<Domain> newObjects) throws RuntimeException {
        //convert domain to entity
        List<Entity> entities = converter.toEntityAll(newObjects);

        //do the persist
        entities = jpaRepo.saveAll(entities);

        //convert the domain back
        return converter.toDomainAll(entities);
    }

    @Override
    public Domain edit(Domain objectToUpdate) throws RuntimeException {
        return create(objectToUpdate);
    }

    @Override
    public List<Domain> editAll(List<Domain> objectsToUpdate) throws RuntimeException {
        return createAll(objectsToUpdate);
    }

    @Override
    public void delete(Domain objectToDestroy) throws RuntimeException {
        //convert domain to entity
        Entity entity = converter.toEntity(objectToDestroy);

        //do the persist
        jpaRepo.delete(entity);
    }

    @Override
    public void deleteById(ID keyId) throws RuntimeException {
        //do the destroy by key, returned the entity
        jpaRepo.deleteById(keyId);
    }

    @Override
    public void deleteAllById(List<ID> keyIds) throws RuntimeException {
        jpaRepo.deleteAllById(keyIds);
    }

    @Override
    public Domain findById(ID keyId) throws RuntimeException {
        //do the findBy, returned the entity
        Optional<Entity> finded = jpaRepo.findById(keyId);
        Entity entity = finded.isPresent() ? finded.get() : null;

        //check if entity exists
        if (entity == null) {
            return null;
        }

        //convert the domain back
        return converter.toDomain(entity);
    }

    @Override
    public List<Domain> findAllById(List<ID> keyId) throws RuntimeException {
        //do the findBy, returned the entity
        List<Entity> entities = jpaRepo.findAllById(keyId);

        //check if entity exists
        if (entities == null) {
            return null;
        }

        //convert the domain back
        return converter.toDomainAll(entities);
    }

    @Override
    public List<Domain> findAll() throws RuntimeException {
        List<Entity> allEntities = jpaRepo.findAll();

        if (allEntities == null) {
            return null;
        }

        return converter.toDomainAll(allEntities);
    }

    @Override
    public long count() throws RuntimeException {
        return jpaRepo.count();
    }

}
