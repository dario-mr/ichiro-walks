package com.dariom.ichirowalks.core.converter;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static java.lang.String.format;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class StringToLocalTimeConverter implements Converter<String, LocalTime> {

    private static final String HH_MM = "HH:mm";
    private static final DateTimeFormatter TIME_FORMATTER = ofPattern(HH_MM);

    @Override
    public Result<LocalTime> convertToModel(String value, ValueContext context) {
        if (isEmpty(value)) {
            return Result.ok(null);
        }

        try {
            return Result.ok(LocalTime.parse(value, TIME_FORMATTER));
        } catch (DateTimeParseException e) {
            return Result.error(format("Please use format: %s", HH_MM));
        }
    }

    @Override
    public String convertToPresentation(LocalTime value, ValueContext context) {
        return value == null ? "" : value.format(TIME_FORMATTER);
    }
}
