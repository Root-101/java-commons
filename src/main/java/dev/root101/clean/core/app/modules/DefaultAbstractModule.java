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
package dev.root101.clean.core.app.modules;

import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 */
public abstract class DefaultAbstractModule implements AbstractModule {

    private final HashMap<String, AbstractModule> registeredModules = new HashMap<>();

    @Override
    public <T> T getImplementation(Class<T> classType) {
        T ans = null;
        try {
            ans = getOwnImplementation(classType);
        } catch (Exception e) {
        }
        return ans != null ? ans : getImplementationRegistered(classType);
    }

    protected abstract <T> T getOwnImplementation(Class<T> classType);

    private <T> T getImplementationRegistered(Class<T> classType) {
        for (AbstractModule value : registeredModules.values()) {
            T actual = null;
            try {
                actual = value.getImplementation(classType);
            } catch (Exception e) {
            }
            if (actual != null) {
                return actual;
            }
        }
        throw new IllegalStateException("None registered module has a valid implementation of " + classType.getName());
    }

    public Collection<AbstractModule> getAllRegisteredModules() {
        return registeredModules.values();
    }

    public AbstractModule getModule(String moduleName) {
        return registeredModules.get(moduleName);
    }

    @Override
    public <T> T getImplementation(String moduleName, Class<T> classType) {
        return registeredModules.get(moduleName).getImplementation(classType);
    }

    @Override
    public void registerModule(AbstractModule classType) {
        if (registeredModules.containsKey(classType.getModuleName())) {
            throw new IllegalArgumentException("Module " + classType.getModuleName() + " already exists. Can't override an existing method.");
        }
        registeredModules.put(classType.getModuleName(), classType);
    }

    @Override
    public void removeModule(AbstractModule classType) {
        registeredModules.remove(classType.getModuleName());
    }

    @Override
    public void removeModule(String moduleName) {
        registeredModules.remove(moduleName);
    }

}
