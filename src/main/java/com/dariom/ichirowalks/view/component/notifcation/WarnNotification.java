package com.dariom.ichirowalks.view.component.notifcation;

import com.vaadin.flow.component.notification.Notification;

import static com.vaadin.flow.component.notification.Notification.Position.TOP_CENTER;
import static com.vaadin.flow.component.notification.NotificationVariant.LUMO_WARNING;

public class WarnNotification extends Notification {

    private WarnNotification(String text) {
        setText(text);
        setDuration(2_000);
        setPosition(TOP_CENTER);
        addThemeVariants(LUMO_WARNING);
    }

    public static WarnNotification show(String text) {
        var notification = new WarnNotification(text);
        notification.open();

        return notification;
    }
}
