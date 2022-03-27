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
package dev.root101.clean.core.utils;

import dev.root101.clean.core.utils.SortBy.List;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 */
@Retention(RUNTIME)
@Target(TYPE)
@Repeatable(List.class)
public @interface SortBy {

    public final static int ASCENDING = 1;
    public final static int DESCENDING = -1;

    /**
     * Sort the objects by it's fields acording to this priority, basically sort
     * by the first, if 2 or more are equals, sort it by the 2nd and so on.
     *
     * @return String array with fields name by decreasing priority
     */
    String[] priority();

    int order() default ASCENDING;

    /**
     * Defines several {@link SortBy} constraints on the same element.
     *
     * @see SortBy
     */
    @Target(TYPE)
    @Retention(RUNTIME)
    @interface List {

        SortBy[] value();
    }
}
