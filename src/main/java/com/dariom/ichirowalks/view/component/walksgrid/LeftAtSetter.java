package com.dariom.ichirowalks.view.component.walksgrid;

import com.dariom.ichirowalks.core.domain.IchiroWalk;
import com.vaadin.flow.data.binder.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LeftAtSetter implements Setter<IchiroWalk, LocalTime> {

    @Override
    public void accept(IchiroWalk walk, LocalTime localTime) {
        if (localTime == null) {
            return;
        }

        if (walk.getLeftAt() == null) {
            walk.setLeftAt(LocalDateTime.of(LocalDate.now(), localTime));
        } else {
            walk.setLeftAt(LocalDateTime.of(walk.getLeftAt().toLocalDate(), localTime));
        }
    }
}
