package io.github.hadixlin.iss.utils;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;

/**
 * Name: NotificationHelper<br>
 * User: Yao<br>
 * Date: 17/4/9<br>
 * Time: 14:17<br>
 */
public class NotificationHelper {
    public static void notify(String msg) {
        Notifications.Bus.notify(new Notification("VimPlugin", "Debugging", msg,
                NotificationType.INFORMATION));

    }
}
