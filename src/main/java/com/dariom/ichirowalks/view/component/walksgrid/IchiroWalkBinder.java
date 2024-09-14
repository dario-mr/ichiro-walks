package com.dariom.ichirowalks.view.component.walksgrid;

import com.dariom.ichirowalks.core.converter.StringToLocalTimeConverter;
import com.dariom.ichirowalks.core.domain.IchiroWalk;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class IchiroWalkBinder extends Binder<IchiroWalk> {

    public IchiroWalkBinder(TextField leftAtField, TextField backAtField) {
        // converter
        var stringToLocalTimeConverter = new StringToLocalTimeConverter();

        // binders for the fields
        forField(leftAtField)
                .withConverter(stringToLocalTimeConverter)
                .bind(new LeftAtValueProvider(), new LeftAtSetter());

        forField(backAtField)
                .withConverter(stringToLocalTimeConverter)
                .bind(new BackAtValueProvider(), new BackAtSetter());
    }
}
