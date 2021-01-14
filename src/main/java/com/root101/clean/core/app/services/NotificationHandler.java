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
package com.root101.clean.core.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 */
public class NotificationHandler {

    private static final List<NotificationService> notificationServices = new ArrayList<>();

    private NotificationHandler() {
    }

    public static void registerNotificationService(NotificationService newService) {
        Objects.requireNonNull(newService, "NotificationService can't be null");

        notificationServices.add(newService);
    }

    public static void showNotification(String type) {
        showNotification(type, null);
    }

    public static void showNotification(String type, Object textToDisplay) {
        System.out.printf("-----NOTIFICATION HANDLER----- NOTIFICATION | Type => '%s' | Message => '%s'\n", type, textToDisplay);
        for (NotificationService notif : notificationServices) {
            notif.showNotification(type, textToDisplay);
        }
    }

    public static boolean showConfirmDialog(String type) {
        return showConfirmDialog(type, null);
    }

    public static boolean showConfirmDialog(String type, Object textToDisplay) {
        System.out.printf("-----NOTIFICATION HANDLER----- CONFIRM | Type => '%s' | Message => '%s'\n", type, textToDisplay);
        for (NotificationService notif : notificationServices) {
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
        System.out.printf("-----NOTIFICATION HANDLER----- INPUT | Type => '%s' | Message => '%s'\n", type, textToDisplay);
        for (NotificationService notif : notificationServices) {
            if (notif.contain(type)) {
                return notif.showInputDialog(type, textToDisplay);
            }
        }
        return null;
    }

    public static boolean contain(String type) {
        for (NotificationService notif : notificationServices) {
            if (notif.contain(type)) {
                return true;
            }
        }
        return false;
    }

}
