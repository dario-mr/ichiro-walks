package com.dariom.ichirowalks.view.component.walksgrid;

import com.dariom.ichirowalks.core.domain.IchiroWalk;
import com.vaadin.flow.data.binder.Setter;

import java.time.LocalTime;

import static java.time.LocalDateTime.of;

public class BackAtSetter implements Setter<IchiroWalk, LocalTime> {

    @Override
    public void accept(IchiroWalk walk, LocalTime localTime) {
        if (localTime == null) {
            walk.setBackAt(null);
            return;
        }

        var leftAt = walk.getLeftAt();
        if (localTime.isBefore(leftAt.toLocalTime())) {
            walk.setBackAt(of(leftAt.toLocalDate().plusDays(1), localTime));
        } else {
            walk.setBackAt(of(leftAt.toLocalDate(), localTime));
        }
    }
}
