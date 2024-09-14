package com.dariom.ichirowalks.view.component.walksgrid;

import com.dariom.ichirowalks.core.domain.IchiroWalk;
import com.vaadin.flow.function.ValueProvider;

import java.time.LocalTime;

public class BackAtValueProvider implements ValueProvider<IchiroWalk, LocalTime> {

    @Override
    public LocalTime apply(IchiroWalk walk) {
        return walk.getBackAt() == null ? null
                : walk.getBackAt().toLocalTime();
    }
}
