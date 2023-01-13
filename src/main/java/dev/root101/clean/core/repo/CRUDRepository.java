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

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdez960717@Github
 * @param <Domain>
 * @param <ID>
 */
public interface CRUDRepository<Domain, ID> {

    public Domain create(Domain newObject) throws RuntimeException;

    public List<Domain> createAll(List<Domain> newObjects) throws RuntimeException;

    public Domain edit(Domain objectToEdit) throws RuntimeException;

    public List<Domain> editAll(List<Domain> objectsToUpdate) throws RuntimeException;

    public void delete(Domain objectToDestroy) throws RuntimeException;

    public void deleteById(ID keyId) throws RuntimeException;

    public void deleteAllById(List<ID> keyIds) throws RuntimeException;

    public Domain findById(ID keyId) throws RuntimeException;

    public List<Domain> findAllById(List<ID> keyId) throws RuntimeException;

    public List<Domain> findAll() throws RuntimeException;

    public default long count() throws RuntimeException {
        return findAll().size();
    }

}
