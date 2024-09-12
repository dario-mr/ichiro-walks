package com.dariom.ichirowalks.view.component;

import com.dariom.ichirowalks.core.domain.IchiroWalk;
import com.dariom.ichirowalks.core.service.IchiroWalkService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;

import static com.dariom.ichirowalks.Util.formatToDate;
import static com.dariom.ichirowalks.Util.formatToTime;
import static com.vaadin.flow.component.grid.GridVariant.LUMO_ROW_STRIPES;

public class IchiroWalksGrid extends Grid<IchiroWalk> {

    private final IchiroWalkService ichiroWalkService;

    public IchiroWalksGrid(IchiroWalkService ichiroWalkService) {
        this.ichiroWalkService = ichiroWalkService;

        // add columns
        addColumn(walk -> formatToDate(walk.leftAt())).setHeader("Day");
        addColumn(walk -> formatToTime(walk.leftAt())).setHeader("Left At");
        addColumn(walk -> formatToTime(walk.backAt())).setHeader("Back At");

        // Add edit and delete buttons
        addColumn(new ComponentRenderer<>(walk -> {
            Button editButton = new Button(VaadinIcon.EDIT.create(), e -> editItem(walk));
            Button deleteButton = new Button(VaadinIcon.TRASH.create(), e -> deleteItem(walk));

            return new HorizontalLayout(editButton, deleteButton);
        }));

        addThemeVariants(LUMO_ROW_STRIPES);
        setItems(this.ichiroWalkService.getAllWalks());
    }

    private void editItem(IchiroWalk item) {
        // Logic for editing the item, e.g., open a dialog with item data pre-filled for editing
        Notification.show("Edit item with ID: " + item.id());
    }

    private void deleteItem(IchiroWalk item) {
        // Logic for deleting the item
        Notification.show("Deleted item with ID: " + item.id());
    }

}
