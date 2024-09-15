package com.dariom.ichirowalks.view.component.walksgrid;

import com.dariom.ichirowalks.core.domain.IchiroWalk;
import com.vaadin.flow.function.ValueProvider;

import java.time.Duration;

import static java.lang.String.format;

public class TimeSpentValueProvider implements ValueProvider<IchiroWalk, String> {

    @Override
    public String apply(IchiroWalk walk) {
        var leftAt = walk.getLeftAt();
        var backAt = walk.getBackAt();
        if (leftAt == null || backAt == null) {
            return "";
        }

        var timeSpent = Duration.between(leftAt, backAt);
        var totalMinutes = timeSpent.toMinutes();
        var hours = totalMinutes / 60;
        var minutes = totalMinutes % 60;

        return format("%02d:%02d", hours, minutes);
    }
}
