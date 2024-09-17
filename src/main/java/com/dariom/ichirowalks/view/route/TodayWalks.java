package com.dariom.ichirowalks.view.route;

import com.dariom.ichirowalks.core.service.IchiroWalkService;
import com.dariom.ichirowalks.view.component.Headline;
import com.dariom.ichirowalks.view.component.WalkRecorder;
import com.dariom.ichirowalks.view.component.walksgrid.IchiroWalksGrid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

import static com.dariom.ichirowalks.util.Constant.MAX_WINDOW_WIDTH;
import static com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.CENTER;

@Route("")
@PageTitle("Today's walks")
@RequiredArgsConstructor
public class TodayWalks extends VerticalLayout {

    @Value("${app.time-zone}")
    private String timeZone;

    private final IchiroWalkService ichiroWalkService;

    @PostConstruct
    public void init() {
        setHeightFull();
        setAlignItems(CENTER);
        setPadding(false);

        var timeZoneClock = Clock.system(ZoneId.of(timeZone));

        // main container
        var container = new VerticalLayout(
                new Headline("Today's walks"),
                new WalkRecorder(ichiroWalkService, timeZoneClock),
                new IchiroWalksGrid(ichiroWalkService, LocalDate.now(timeZoneClock))
        );
        container.setMaxWidth(MAX_WINDOW_WIDTH);
        container.setHeightFull();

        add(container);
    }
}
