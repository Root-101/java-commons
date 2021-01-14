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
package com.root101.clean.core.app.repo;

import java.util.List;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 * @author jjhurtado@Github
 * @param <T>
 */
public interface CRUDRepository<T> extends AbstractRepository {

    public T create(T newObject) throws RuntimeException;

    public T edit(T objectToEdit) throws RuntimeException;

    public T destroy(T objectToDestroy) throws RuntimeException;

    public T destroyById(Object keyId) throws RuntimeException;

    public T findBy(Object keyId) throws RuntimeException;

    public List<T> findAll() throws RuntimeException;

    public default int count() throws RuntimeException {
        return findAll().size();
    }

    public void addPropertyChangeListener(java.beans.PropertyChangeListener listener);

    public void removePropertyChangeListener(java.beans.PropertyChangeListener listener);
}
