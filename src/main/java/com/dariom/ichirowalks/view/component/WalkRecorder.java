package com.dariom.ichirowalks.view.component;

import com.dariom.ichirowalks.core.service.IchiroWalkService;
import com.dariom.ichirowalks.event.RegisterWalkEvent;
import com.dariom.ichirowalks.view.component.notifcation.WarnNotification;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.time.Clock;
import java.time.LocalDateTime;

public class WalkRecorder extends VerticalLayout {

    private final IchiroWalkService ichiroWalkService;

    private final Button leavingButton;
    private final Button backButton;

    public WalkRecorder(IchiroWalkService ichiroWalkService, Clock timeZoneClock) {
        this.ichiroWalkService = ichiroWalkService;

        // add buttons
        leavingButton = new Button("Leaving...");
        backButton = new Button("...Back!");

        // add button listeners
        leavingButton.addClickListener(event -> handleLeaving(timeZoneClock));
        backButton.addClickListener(event -> handleBack(timeZoneClock));

        // place buttons in container
        var container = new HorizontalLayout(leavingButton, backButton);
        container.setWidthFull();
        container.setFlexGrow(1, leavingButton, backButton);
        add(container);

        setPadding(false);
    }

    private void handleLeaving(Clock clock) {
        var result = ichiroWalkService.createWalk(LocalDateTime.now(clock));
        if (result.success()) {
            ComponentUtil.fireEvent(UI.getCurrent(), new RegisterWalkEvent(leavingButton));
            return;
        }

        WarnNotification.show(result.reason());
    }

    private void handleBack(Clock clock) {
        var result = ichiroWalkService.updateBackAt(LocalDateTime.now(clock));
        if (result.success()) {
            ComponentUtil.fireEvent(UI.getCurrent(), new RegisterWalkEvent(backButton));
            return;
        }

        WarnNotification.show(result.reason());
    }
}
