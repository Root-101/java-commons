package com.clean.core.app.services;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public interface NotificationService<T> {

    public default void showNotification(String type) {
        showNotification(type, null);
    }

    public void showNotification(String type, T toDisplay);

    public default boolean showConfirmDialog(String type) {
        return showConfirmDialog(type, null);
    }

    public boolean showConfirmDialog(String type, T toDisplay);

    public default Object showInputDialog(String type) {
        return showInputDialog(type, null);
    }

    public Object showInputDialog(String type, T toDisplay);

    public boolean contain(String type);
}
