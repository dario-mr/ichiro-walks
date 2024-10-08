package com.dariom.ichirowalks.view.route;

import com.dariom.ichirowalks.core.service.IchiroWalkService;
import com.dariom.ichirowalks.view.component.BaseContainer;
import com.dariom.ichirowalks.view.component.walksgrid.IchiroWalksGrid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import static com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.CENTER;

@Route(value = "all", layout = Home.class)
@PageTitle("All walks")
@RequiredArgsConstructor
public class AllWalks extends VerticalLayout {

    private final IchiroWalkService ichiroWalkService;

    @PostConstruct
    public void init() {
        setHeightFull();
        setAlignItems(CENTER);
        setPadding(false);

        // main container
        var container = new BaseContainer(
                new H4("All"),
                new IchiroWalksGrid(ichiroWalkService)
        );

        add(container);
    }
}
