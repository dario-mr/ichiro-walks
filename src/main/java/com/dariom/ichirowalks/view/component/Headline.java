package com.dariom.ichirowalks.view.component;

import com.vaadin.flow.component.html.H3;

public class Headline extends H3 {

    public Headline(String title) {
        setText(title);
        addClassName("headline");
    }
}
