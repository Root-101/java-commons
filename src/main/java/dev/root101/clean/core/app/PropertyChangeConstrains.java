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
package dev.root101.clean.core.app;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 * @param <T>
 */
public class PropertyChangeConstrains {

    /*
    public static void main(String args[]) {
        System.out.println(PropertyChangeConstrains.AFTER_COUNT);
    }
     */
    private final static Comparator<Field> compare = (Field o1, Field o2) -> {
        if (Modifier.isStatic(o1.getModifiers())
                && String.class.isAssignableFrom(o1.getType())
                && Modifier.isStatic(o2.getModifiers())
                && String.class.isAssignableFrom(o2.getType())) {
            try {
                String val1 = (String) o1.get(null);
                String val2 = (String) o2.get(null);
                return val1.compareToIgnoreCase(val2);
            } catch (IllegalAccessException | IllegalArgumentException ex) {
            }
        }
        return 0;
    };

    static {//no repeated names
        Field[] fields = PropertyChangeConstrains.class.getDeclaredFields();
        Arrays.sort(fields, compare);

        for (int i = 0; i < fields.length - 1; i++) {
            if (compare.compare(fields[i], fields[i + 1]) == 0) {
                throw new InternalError(String.format("Fields %s and %s of different property changes have same value", fields[i], fields[i + 1]));
            }
        }
    }

    //-----------------------    For CRUD    -----------------------\\
    //create
    public static final String BEFORE_CREATE = "before_create";
    public static final String AFTER_CREATE = "after_create";

    //edit
    public static final String BEFORE_EDIT = "before_edit";
    public static final String AFTER_EDIT = "after_edit";

    //destroy
    public static final String BEFORE_DESTROY = "before_destroy";
    public static final String AFTER_DESTROY = "after_destroy";

    //destroyById
    public static final String BEFORE_DESTROY_BY_ID = "before_destroyById";
    public static final String AFTER_DESTROY_BY_ID = "after_destroyById";

    //findBy
    public static final String BEFORE_FIND_BY = "before_findBy";
    public static final String AFTER_FIND_BY = "after_findBy";

    //findAll
    public static final String BEFORE_FIND_ALL = "before_findAll";
    public static final String AFTER_FIND_ALL = "after_findAll";

    //count
    public static final String BEFORE_COUNT = "before_count";
    public static final String AFTER_COUNT = "after_count";

    //-----------------------    For Read/Write    -----------------------\\
    //read
    public static final String BEFORE_READ = "before_read";
    public static final String AFTER_READ = "after_read";

    //write
    public static final String BEFORE_WRITE = "before_write";
    public static final String AFTER_WRITE = "after_write";
}
