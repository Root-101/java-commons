/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clean.core.app.services;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
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

    protected abstract void addNotifications();
    protected abstract void addConfirmDialog();
    protected abstract void addInputDialog();

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
        notifMap.get(type).accept(toDisplay);
    }

    @Override
    public boolean showConfirmDialog(String type, T toDisplay) {
        return confirmMap.get(type).test(toDisplay);
    }

    @Override
    public Object showInputDialog(String type, T toDisplay) {
        return inputMap.get(type).apply(toDisplay);
    }

    @Override
    public boolean contain(String type) {
        return notifMap.containsKey(type) || confirmMap.containsKey(type) || inputMap.containsKey(type);
    }

}
