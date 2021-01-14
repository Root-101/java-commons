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
package com.root101.clean.core.app.usecase;

import com.root101.clean.core.app.repo.CRUDLightweightRepo;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 * @param <Domain>
 */
public class DefaultCRUDLightweightUseCase<Domain> extends DefaultCRUDUseCase<Domain> implements CRUDLightweightUseCase<Domain> {

    protected CRUDLightweightRepo<Domain> crudLightRepo;

    @Override
    protected CRUDLightweightRepo<Domain> getRepo() {
        return crudLightRepo;
    }

    protected void setRepo(CRUDLightweightRepo<Domain> repo) {
        this.crudLightRepo = repo;
        super.setRepo(repo);
    }

    @Override
    public void create_light(Domain newObject) throws RuntimeException {
        crudLightRepo.create_light(newObject);
        firePropertyChange("create_light", null, null);
    }

    @Override
    public void edit_light(Domain objectToUpdate) throws RuntimeException {
        crudLightRepo.edit_light(objectToUpdate);
        firePropertyChange("edit_light", null, null);
    }

    @Override
    public void destroy_light(Domain objectToDestroy) throws RuntimeException {
        crudLightRepo.destroy_light(objectToDestroy);
        firePropertyChange("destroy_light", null, null);
    }

    @Override
    public void destroyById_light(Object keyId) throws RuntimeException {
        crudLightRepo.destroyById_light(keyId);
        firePropertyChange("destroyById_light", null, null);
    }

}
