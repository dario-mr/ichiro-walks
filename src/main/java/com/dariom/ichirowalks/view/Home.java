package com.dariom.ichirowalks.view;

import com.dariom.ichirowalks.repository.IchiroWalkRepository;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Route("")
@PageTitle("Ichiro Walks")
@RequiredArgsConstructor
public class Home extends VerticalLayout {

    private final IchiroWalkRepository ichiroWalkRepository;

    @PostConstruct
    public void init() {
        setHeightFull();

        var walks = ichiroWalkRepository.findAll();
        add(new Text(walks.toString()));
    }
}
