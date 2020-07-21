package com.clean.core.app.services;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class Notification {

    private static final List<NotificationService> notificationService = new ArrayList<>();

    private Notification() {
    }

    public static void registerNotificationService(NotificationService newService) {
        notificationService.add(newService);
    }

    public static void showNotification(String type) {
        showNotification(type, null);
    }

    public static void showNotification(String type, Object textToDisplay) {
        for (NotificationService notif : notificationService) {
            if (notif.contain(type)) {
                notif.showNotification(type, textToDisplay);
            }
        }
    }

    public static boolean showConfirmDialog(String type) {
        return showConfirmDialog(type, null);
    }

    public static boolean showConfirmDialog(String type, Object textToDisplay) {
        for (NotificationService notif : notificationService) {
            if (notif.contain(type)) {
                return notif.showConfirmDialog(type, textToDisplay);
            }
        }
        return false;
    }

    public static Object showInputDialog(String type) {
        return showInputDialog(type, null);
    }

    public static Object showInputDialog(String type, Object textToDisplay) {
        for (NotificationService notif : notificationService) {
            if (notif.contain(type)) {
                return notif.showInputDialog(type, textToDisplay);
            }
        }
        return null;
    }

    public static boolean contain(String type) {
        for (NotificationService notif : notificationService) {
            if (notif.contain(type)) {
                return true;
            }
        }
        return false;
    }

}
