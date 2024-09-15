package com.dariom.ichirowalks.view.component.walksgrid;

import com.dariom.ichirowalks.core.domain.IchiroWalk;
import com.vaadin.flow.data.binder.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

import static java.time.LocalDateTime.of;

public class LeftAtSetter implements Setter<IchiroWalk, LocalTime> {

    @Override
    public void accept(IchiroWalk walk, LocalTime localTime) {
        if (localTime == null) {
            walk.setLeftAt(null);
            return;
        }

        if (walk.getLeftAt() == null) { // TODO this should never happen
            walk.setLeftAt(of(LocalDate.now(), localTime));
        } else {
            walk.setLeftAt(of(walk.getLeftAt().toLocalDate(), localTime));
        }
    }
}
