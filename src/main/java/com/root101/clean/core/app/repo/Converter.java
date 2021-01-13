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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 * @param <Domain>
 * @param <Entity>
 */
public interface Converter<Domain, Entity> {

    public Domain from(Entity object) throws RuntimeException;

    public Entity to(Domain object) throws RuntimeException;

    public default List<Domain> from(List<Entity> list) throws RuntimeException {
        List<Domain> answ = new ArrayList<>();
        for (Entity entity : list) {
            answ.add(from(entity));
        }
        return answ;
    }

    public default List<Entity> to(List<Domain> list) throws RuntimeException {//convert entities to domain
        List<Entity> answ = new ArrayList<>();
        for (Domain domain : list) {
            answ.add(to(domain));
        }
        return answ;
    }

}
