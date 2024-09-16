package com.dariom.ichirowalks.event;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.button.Button;

public class RegisterWalkEvent extends ComponentEvent<Button> {

    public RegisterWalkEvent(Button source) {
        super(source, false);
    }
}
