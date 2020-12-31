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
package com.clean.core.app.services;

import java.util.HashMap;
import java.util.function.Consumer;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 */
public abstract class ExceptionHandlerServiceFunctional implements ExceptionHandlerService {

    private final HashMap<String, Consumer<Throwable>> exceptionsMap = new HashMap<>();

    public ExceptionHandlerServiceFunctional() {
        addAll();
    }

    protected abstract void addAll();

    public final void addHandler(String type, Consumer<Throwable> consumer) {
        exceptionsMap.put(type, consumer);
    }

    public final void addHandler(Consumer<Throwable> consumer, String... type) {
        for (String string : type) {
            exceptionsMap.put(string, consumer);
        }
    }

    @Override
    public void handleException(Throwable ex) {
        handleExceptionInternal(getExceptionType(ex), ex);
    }

    private void handleExceptionInternal(String type, Throwable ex) {
        exceptionsMap.get(type).accept(ex);
    }

    @Override
    public boolean contain(Throwable ex) {
        return contain(ex.getClass());
    }

    @Override
    public boolean contain(Class type) {
        return exceptionsMap.containsKey(getExceptionType(type));
    }

    public static String getExceptionType(Throwable type) {
        return getExceptionType(type.getClass());
    }

    public static String getExceptionType(Class type) {
        String split[] = type.toString().split(" ");
        return split[split.length - 1];
    }
}
