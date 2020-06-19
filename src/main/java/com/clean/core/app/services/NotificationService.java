package com.clean.core.app.services;

/**
 *
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 */
public interface NotificationService {

    public void showNotification(String type, String textToDisplay);

    public boolean showConfirmDialog(String type, String textToDisplay);

    public Object showInputDialog(String type, String textToDisplay);

}
