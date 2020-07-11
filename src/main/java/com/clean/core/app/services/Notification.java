package com.clean.core.app.services;

/**
 * 
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class Notification {

    private static NotificationService notificationService;

    private Notification() {
    }

    public static void registerNotificationService(NotificationService newService) {
        notificationService = newService;
    }

    public static NotificationService getNotificationService() {
        if (notificationService == null) {
            throw new IllegalStateException("Bad call");
        }
        return notificationService;
    }

    public void showNotification(String type, String textToDisplay) {
        notificationService.showNotification(type, textToDisplay);
    }

    public boolean showConfirmDialog(String type, String textToDisplay) {
        return notificationService.showConfirmDialog(type, textToDisplay);
    }

    public Object showInputDialog(String type, String textToDisplay) {
        return notificationService.showInputDialog(type, textToDisplay);
    }
}
