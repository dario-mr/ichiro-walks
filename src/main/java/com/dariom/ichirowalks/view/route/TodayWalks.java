package com.dariom.ichirowalks.view.route;

import com.dariom.ichirowalks.core.service.IchiroWalkService;
import com.dariom.ichirowalks.view.component.BaseContainer;
import com.dariom.ichirowalks.view.component.WalkRecorder;
import com.dariom.ichirowalks.view.component.walksgrid.IchiroWalksGrid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

import static com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.CENTER;

@Route(value = "today", layout = Home.class)
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
        var container = new BaseContainer(
                new H4("Today"),
                new WalkRecorder(ichiroWalkService, timeZoneClock),
                new IchiroWalksGrid(ichiroWalkService, LocalDate.now(timeZoneClock))
        );

        add(container);
    }
}
