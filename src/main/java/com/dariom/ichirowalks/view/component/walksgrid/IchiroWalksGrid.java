package com.dariom.ichirowalks.view.component.walksgrid;

import com.dariom.ichirowalks.core.domain.IchiroWalk;
import com.dariom.ichirowalks.core.service.IchiroWalkService;
import com.dariom.ichirowalks.view.component.SuccessNotification;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.KeyDownEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.grid.editor.EditorSaveEvent;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

import static com.dariom.ichirowalks.Util.formatToDate;
import static com.dariom.ichirowalks.Util.formatToTime;
import static com.vaadin.flow.component.Key.ESCAPE;
import static com.vaadin.flow.component.grid.GridVariant.LUMO_ROW_STRIPES;
import static com.vaadin.flow.component.icon.VaadinIcon.CHECK;
import static com.vaadin.flow.component.icon.VaadinIcon.TRASH;

@Slf4j
public class IchiroWalksGrid extends Grid<IchiroWalk> {

    private final IchiroWalkService ichiroWalkService;
    private final Editor<IchiroWalk> editor;
    private final ListDataProvider<IchiroWalk> dataProvider;

    public IchiroWalksGrid(IchiroWalkService ichiroWalkService) {
        this.ichiroWalkService = ichiroWalkService;

        // create editor components
        var leftAtField = new TextField();
        var backAtField = new TextField();
        var saveButton = new Button(CHECK.create());
        leftAtField.setWidthFull();
        backAtField.setWidthFull();

        // add columns
        addColumn(walk -> formatToDate(walk.getLeftAt())).setHeader("Day");
        addColumn(walk -> formatToTime(walk.getLeftAt()))
                .setHeader("Left At")
                .setEditorComponent(leftAtField);
        addColumn(walk -> formatToTime(walk.getBackAt()))
                .setHeader("Back At")
                .setEditorComponent(backAtField);
        addColumn(new TimeSpentValueProvider()).setHeader("Time spent");
        addColumn(new ComponentRenderer<>(walk -> new Button(TRASH.create(), e -> showDeleteConfirmation(walk))))
                .setEditorComponent(saveButton);

        // set up binder and editor
        var binder = new IchiroWalkBinder(leftAtField, backAtField);
        editor = getEditor();
        editor.setBinder(binder);
        editor.setBuffered(true); // edits are only committed when the user clicks the save button

        // open editor on single-click
        addItemClickListener(this::handleRowClick);

        // handle escape action on the editable fields
        ComponentEventListener<KeyDownEvent> keyDownListener = this::handleEscape;
        leftAtField.addKeyDownListener(keyDownListener);
        backAtField.addKeyDownListener(keyDownListener);

        // handle save action
        saveButton.addClickListener(event -> editor.save());
        editor.addSaveListener(this::handleSave);

        // set data provider
        var walks = new ArrayList<>(ichiroWalkService.getAllWalks()); // mutable list
        dataProvider = new ListDataProvider<>(walks);
        setDataProvider(dataProvider);

        addThemeVariants(LUMO_ROW_STRIPES);
    }

    private void handleRowClick(ItemClickEvent<IchiroWalk> event) {
        if (editor.isOpen()) {
            editor.cancel();
        }

        var editorComponent = event.getColumn().getEditorComponent();
        if (editorComponent instanceof Focusable) {
            editor.editItem(event.getItem());
            ((Focusable<?>) editorComponent).focus();
        }
    }

    private void handleEscape(KeyDownEvent event) {
        var keyPressed = event.getKey().toString();

        if (ESCAPE.matches(keyPressed)) {
            editor.cancel();
        }
    }

    private void handleSave(EditorSaveEvent<IchiroWalk> event) {
        ichiroWalkService.save(event.getItem());
        dataProvider.refreshItem(event.getItem());
        editor.closeEditor();

        SuccessNotification.show("Change saved!");
    }

    private void showDeleteConfirmation(IchiroWalk walk) {
        var confirmDialog = new ConfirmDialog();
        confirmDialog.setHeader("Confirm deletion");
        confirmDialog.setText("Are you sure you want to delete this item?");
        confirmDialog.setCancelable(true);
        confirmDialog.setConfirmText("Delete");
        confirmDialog.setCancelText("Cancel");

        confirmDialog.addConfirmListener(event -> deleteWalk(walk));

        confirmDialog.open();
    }

    public void deleteWalk(IchiroWalk walk) {
        ichiroWalkService.delete(walk.getId());
        dataProvider.getItems().remove(walk);
        dataProvider.refreshAll();
    }
}
