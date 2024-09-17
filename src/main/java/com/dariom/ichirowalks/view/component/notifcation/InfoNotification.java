package com.dariom.ichirowalks.view.component.notifcation;

import com.vaadin.flow.component.notification.Notification;

import static com.vaadin.flow.component.notification.Notification.Position.TOP_CENTER;

public class InfoNotification extends Notification {

    private InfoNotification(String text) {
        setText(text);
        setDuration(2_000);
        setPosition(TOP_CENTER);
    }

    public static InfoNotification show(String text) {
        var notification = new InfoNotification(text);
        notification.open();

        return notification;
    }
}
