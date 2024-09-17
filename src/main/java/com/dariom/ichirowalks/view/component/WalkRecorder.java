package com.dariom.ichirowalks.view.component;

import com.dariom.ichirowalks.core.domain.IchiroWalk;
import com.dariom.ichirowalks.core.service.IchiroWalkService;
import com.dariom.ichirowalks.event.RegisterWalkEvent;
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
        ichiroWalkService.save(IchiroWalk.builder()
                .leftAt(LocalDateTime.now(clock))
                .build());
        ComponentUtil.fireEvent(UI.getCurrent(), new RegisterWalkEvent(leavingButton));
    }

    private void handleBack(Clock clock) {
        ichiroWalkService.updateBackAt(LocalDateTime.now(clock));
        ComponentUtil.fireEvent(UI.getCurrent(), new RegisterWalkEvent(backButton));
    }
}
