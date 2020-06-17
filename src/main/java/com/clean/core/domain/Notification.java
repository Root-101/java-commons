package com.clean.core.domain;

/**
 *
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
 */
public class Notification {

    private static NotificationService notificationService;

    private Notification() {
    }

    public static void registerResourceService(NotificationService newService) {
        notificationService = newService;
    }

    public static NotificationService getNotificationService() {
        if (notificationService == null) {
            throw new IllegalStateException("Bad call");
        }
        return notificationService;
    }

    public static void makeNotification() {
        getNotificationService().makeNotification();
    }
}
