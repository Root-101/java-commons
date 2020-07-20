package com.clean.core.app.services;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class Notification implements NotificationService {

    private static final List<NotificationService> notificationService = new ArrayList<>();

    private Notification() {
    }

    public static void registerNotificationService(NotificationService newService) {
        notificationService.add(newService);
    }

    @Override
    public void showNotification(String type, String textToDisplay) {
        for (NotificationService notif : notificationService) {
            notif.showNotification(type, textToDisplay);
        }
    }

    @Override
    public boolean showConfirmDialog(String type, String textToDisplay) {
        for (NotificationService notif : notificationService) {
            if (notif.contain(type)) {
                return notif.showConfirmDialog(type, textToDisplay);
            }
        }
        return false;
    }

    @Override
    public Object showInputDialog(String type, String textToDisplay) {
        for (NotificationService notif : notificationService) {
            if (notif.contain(type)) {
                return notif.showInputDialog(type, textToDisplay);
            }
        }
        return null;
    }

    @Override
    public boolean contain(String type) {
        for (NotificationService notif : notificationService) {
            if (notif.contain(type)) {
                return true;
            }
        }
        return false;
    }

}
