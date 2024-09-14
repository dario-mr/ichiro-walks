package com.dariom.ichirowalks.view;

import com.dariom.ichirowalks.core.service.IchiroWalkService;
import com.dariom.ichirowalks.view.component.Headline;
import com.dariom.ichirowalks.view.component.walksgrid.IchiroWalksGrid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import static com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.CENTER;

@Route("")
@PageTitle("Ichiro Walks")
@RequiredArgsConstructor
public class Home extends VerticalLayout {

    private static final String MAX_WINDOW_WIDTH = "800px";

    private final IchiroWalkService ichiroWalkService;

    @PostConstruct
    public void init() {
        setHeightFull();
        setAlignItems(CENTER);
        setPadding(false);

        // main container
        var container = new VerticalLayout(
                new Headline(),
                new IchiroWalksGrid(ichiroWalkService)
        );
        container.setMaxWidth(MAX_WINDOW_WIDTH);
        container.setHeightFull();

        add(container);
    }
}
