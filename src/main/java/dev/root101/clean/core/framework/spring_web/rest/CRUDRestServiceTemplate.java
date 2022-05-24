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
package dev.root101.clean.core.framework.spring_web.rest;

import dev.root101.clean.core.app.domain.DomainObject;
import dev.root101.clean.core.app.usecase.CRUDUseCase;
import dev.root101.clean.core.rest.CRUDRestService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 * @param <Domain>
 * @param <ID>
 * @param <UseCase>
 */
public class CRUDRestServiceTemplate<Domain extends DomainObject<ID>, ID, UseCase extends CRUDUseCase<Domain, ID>> implements CRUDRestService<Domain, ID> {

    private final boolean doFirePropertyChanges = false;//for the moment allways disabled
    protected transient final java.beans.PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);

    protected final UseCase crudUC;

    public CRUDRestServiceTemplate(UseCase crudRepo) {
        this.crudUC = crudRepo;
    }

    protected CRUDUseCase useCase() {
        return crudUC;
    }

    @Override
    @PostMapping(path = RESTUrlConstants.CREATE_PATH)
    public Domain create(@RequestBody Domain domain) throws RuntimeException {
        return crudUC.create(domain);
    }

    @Override
    @PostMapping(RESTUrlConstants.EDIT_PATH)
    public Domain edit(@RequestBody Domain domain) throws RuntimeException {
        return crudUC.edit(domain);
    }

    @Override
    @PostMapping(RESTUrlConstants.DESTROY_PATH)
    public void destroy(@RequestBody Domain t) throws RuntimeException {
        crudUC.destroy(t);
    }

    @Override
    @PostMapping(RESTUrlConstants.DESTROY_ID_PATH)
    public void destroyById(@RequestBody ID id) throws RuntimeException {
        crudUC.destroyById(id);
    }

    @Override
    @GetMapping(RESTUrlConstants.FIND_ALL_PATH)
    public List<Domain> findAll() throws RuntimeException {
        return crudUC.findAll();
    }

    @Override
    @GetMapping(RESTUrlConstants.FIND_BY_PATH)
    public Domain findBy(@PathVariable(RESTUrlConstants.ID) ID id) throws RuntimeException {
        return crudUC.findBy(id);
    }

    @Override
    @GetMapping(RESTUrlConstants.COUNT_PATH)
    public long count() throws RuntimeException {
        return crudUC.count();
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
