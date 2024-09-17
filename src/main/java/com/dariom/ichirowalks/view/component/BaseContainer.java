package com.dariom.ichirowalks.view.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

import static com.dariom.ichirowalks.util.Constant.MAX_WINDOW_WIDTH;

public class BaseContainer extends VerticalLayout {

    public BaseContainer(Component... children) {
        setHeightFull();
        setMaxWidth(MAX_WINDOW_WIDTH);
        addClassName(LumoUtility.Padding.Horizontal.SMALL);

        add(children);
    }
}
