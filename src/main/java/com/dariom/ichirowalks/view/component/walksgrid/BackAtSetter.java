package com.dariom.ichirowalks.view.component.walksgrid;

import com.dariom.ichirowalks.core.domain.IchiroWalk;
import com.vaadin.flow.data.binder.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class BackAtSetter implements Setter<IchiroWalk, LocalTime> {

    @Override
    public void accept(IchiroWalk walk, LocalTime localTime) {
        if (localTime == null) {
            return;
        }

        if (walk.getBackAt() == null) {
            walk.setBackAt(LocalDateTime.of(LocalDate.now(), localTime));
        } else {
            walk.setBackAt(LocalDateTime.of(walk.getBackAt().toLocalDate(), localTime));
        }
    }
}
