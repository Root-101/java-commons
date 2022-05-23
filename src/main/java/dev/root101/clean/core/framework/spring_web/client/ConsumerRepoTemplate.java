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
package dev.root101.clean.core.framework.spring_web.client;

import dev.root101.clean.core.repo.external_repo.CRUDExternalRepository;
import dev.root101.clean.core.framework.spring_web.rest.RESTUrlConstants;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import org.springframework.web.client.RestOperations;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 * @param <Entity>
 * @param <ID>
 */
public abstract class ConsumerRepoTemplate<Entity, ID> implements CRUDExternalRepository<Entity, ID> {

    private final boolean doFirePropertyChanges = false;//for the moment allways disabled
    protected transient final java.beans.PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);

    protected final Class<? extends Entity> clazz;
    protected final String urlGeneral;
    protected final Supplier<RestOperations> template;

    public ConsumerRepoTemplate(Class<? extends Entity> clazz, String urlGeneral, Supplier<RestOperations> template) {
        this.clazz = clazz;
        this.urlGeneral = urlGeneral;
        this.template = template;
    }

    protected RestOperations template() {
        return template.get();
    }

    @Override
    public Entity create(Entity newObject) throws RuntimeException {
        return template().postForObject(urlGeneral + RESTUrlConstants.CREATE_PATH, newObject, clazz);
    }

    @Override
    public Entity edit(Entity objectToEdit) throws RuntimeException {
        return template().postForObject(urlGeneral + RESTUrlConstants.EDIT_PATH, objectToEdit, clazz);
    }

    @Override
    public void destroy(Entity objectToDestroy) throws RuntimeException {
        template().postForObject(urlGeneral + RESTUrlConstants.DESTROY_PATH, objectToDestroy, clazz);
    }

    @Override
    public void destroyById(ID keyId) throws RuntimeException {
        template().postForObject(urlGeneral + RESTUrlConstants.DESTROY_ID_PATH, keyId, clazz);
    }

    @Override
    public Entity findBy(ID keyId) throws RuntimeException {
        Map<String, Object> map = new HashMap<>();
        map.put(RESTUrlConstants.ID, keyId);
        return template().getForObject(urlGeneral + RESTUrlConstants.FIND_BY_PATH, clazz, map);
    }

    @Override
    public List<Entity> findAll() throws RuntimeException {
        return (List<Entity>) RestTemplateUtils.getForList(template(), urlGeneral + RESTUrlConstants.FIND_ALL_PATH, clazz);
    }

    @Override
    public long count() throws RuntimeException {
        return template().getForObject(urlGeneral + RESTUrlConstants.COUNT_PATH, Integer.class);
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
        if (doFirePropertyChanges) {
            propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
        }
    }

}
