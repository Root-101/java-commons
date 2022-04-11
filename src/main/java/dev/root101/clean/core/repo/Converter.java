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
import java.util.stream.Collectors;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 * @param <Domain>
 * @param <Entity>
 */
public interface Converter<Domain, Entity> {

    public Domain toDomain(Entity entity) throws RuntimeException;

    public Entity toEntity(Domain domain) throws RuntimeException;

    public default List<Domain> toDomainAll(List<Entity> list) throws RuntimeException {
        return list.stream().map((entity) -> toDomain(entity)).collect(Collectors.toList());
    }

    public default List<Entity> toEntityAll(List<Domain> list) throws RuntimeException {//convert entities to domain
        return list.stream().map((domain) -> toEntity(domain)).collect(Collectors.toList());
    }

}