package com.dariom.ichirowalks.view.component;

import com.dariom.ichirowalks.core.domain.IchiroWalk;
import com.dariom.ichirowalks.core.service.IchiroWalkService;
import com.dariom.ichirowalks.event.RegisterWalkEvent;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;

public class WalkRecorder extends VerticalLayout {

    private final IchiroWalkService ichiroWalkService;

    private final Button leavingButton;
    private final Button backButton;

    public WalkRecorder(IchiroWalkService ichiroWalkService) {
        showTimeZones();

        this.ichiroWalkService = ichiroWalkService;
        setPadding(false);

        // add buttons
        leavingButton = new Button("Leaving...");
        backButton = new Button("...Back!");

        // add button listeners
        leavingButton.addClickListener(event -> handleLeaving());
        backButton.addClickListener(event -> handleBack());

        // place buttons in container
        var container = new HorizontalLayout(leavingButton, backButton);
        container.setWidthFull();
        container.setFlexGrow(1, leavingButton, backButton);
        add(container);
    }

    private void handleLeaving() {
        ichiroWalkService.save(IchiroWalk.builder()
                .leftAt(LocalDateTime.now())
                .build());
        ComponentUtil.fireEvent(UI.getCurrent(), new RegisterWalkEvent(leavingButton));
    }

    private void handleBack() {
        ichiroWalkService.updateBackAt(LocalDateTime.now());
        ComponentUtil.fireEvent(UI.getCurrent(), new RegisterWalkEvent(backButton));
    }

    // TODO remove
    private void showTimeZones() {
        var defaultZone = Clock.systemDefaultZone();
        var UTCzone = Clock.systemUTC();

        var defaultNow = LocalDateTime.now(defaultZone);
        var utcNow = LocalDateTime.now(UTCzone);
        var instant = Instant.now();

        var zoneTxt = new TextField("Default time-zone: ", defaultZone.toString(), "");
        zoneTxt.setWidthFull();
        zoneTxt.setReadOnly(true);
        var defaultTxt = new TextField("Default now: ", defaultNow.toString(), "");
        defaultTxt.setWidthFull();
        defaultTxt.setReadOnly(true);
        var utcTxt = new TextField("UTC now: ", utcNow.toString(), "");
        utcTxt.setWidthFull();
        utcTxt.setReadOnly(true);
        var instantTxt = new TextField("Instant now: ", instant.toString(), "");
        instantTxt.setWidthFull();
        instantTxt.setReadOnly(true);

        add(zoneTxt, defaultTxt, utcTxt, instantTxt);
    }
}
