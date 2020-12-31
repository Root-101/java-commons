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
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 * @param <T> 
 */
public abstract class NotificationServiceFunctional<T> implements NotificationService<T> {

    private final HashMap<String, Consumer<T>> notifMap = new HashMap<>();
    private final HashMap<String, Predicate<T>> confirmMap = new HashMap<>();
    private final HashMap<String, Function<Object, T>> inputMap = new HashMap<>();

    public NotificationServiceFunctional() {
        addAll();
    }

    public void addAll() {
        addNotifications();
        addConfirmDialog();
        addInputDialog();
    }

    protected void addNotifications() {
    }

    protected void addConfirmDialog() {
    }

    protected void addInputDialog() {
    }

    public final void addNotification(String type, Consumer<T> consumer) {
        notifMap.put(type, consumer);
    }

    public final void addConfirmDialog(String type, Predicate<T> predicate) {
        confirmMap.put(type, predicate);
    }

    public final void addInputDialog(String type, Function<Object, T> function) {
        inputMap.put(type, function);
    }

    @Override
    public void showNotification(String type, T toDisplay) {
        if (notifMap.containsKey(type)) {
            notifMap.get(type).accept(toDisplay);
        }
    }

    @Override
    public boolean showConfirmDialog(String type, T toDisplay) {
        if (confirmMap.containsKey(type)) {
            return confirmMap.get(type).test(toDisplay);
        }
        return false;
    }

    @Override
    public Object showInputDialog(String type, T toDisplay) {
        if (inputMap.containsKey(type)) {
            return inputMap.get(type).apply(toDisplay);
        }
        return null;
    }

    @Override
    public boolean contain(String type) {
        return notifMap.containsKey(type) || confirmMap.containsKey(type) || inputMap.containsKey(type);
    }

}
