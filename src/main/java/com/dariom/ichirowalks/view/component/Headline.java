package com.dariom.ichirowalks.view.component;

import com.vaadin.flow.component.html.H2;

public class Headline extends H2 {

    public Headline(String title) {
        setText(title);
        addClassName("headline");
    }
}
