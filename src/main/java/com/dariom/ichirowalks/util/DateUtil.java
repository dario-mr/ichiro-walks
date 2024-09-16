package com.dariom.ichirowalks.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ofPattern;

@UtilityClass
public class DateUtil {

    public static String formatToDate(LocalDateTime localDateTime) {
        return localDateTime == null ? ""
                : localDateTime.format(ofPattern("dd MMM"));
    }

    public static String formatToTime(LocalDateTime localDateTime) {
        return localDateTime == null ? ""
                : localDateTime.format(ofPattern("HH:mm"));
    }
}
