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
package dev.root101.clean.core.repo.framework;

import java.util.List;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 * @param <Entity>
 * @param <ID>
 */
public interface CRUDFrameworkRepository<Entity, ID> extends AbstractFrameworkRepository {

    public Entity create(Entity newObject) throws RuntimeException;

    public Entity edit(Entity objectToEdit) throws RuntimeException;

    public void destroy(Entity objectToDestroy) throws RuntimeException;

    public void destroyById(ID keyId) throws RuntimeException;

    public Entity findBy(ID keyId) throws RuntimeException;

    public List<Entity> findAll() throws RuntimeException;

    public default long count() throws RuntimeException {
        return findAll().size();
    }

}
