package com.dariom.ichirowalks;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ofPattern;

@UtilityClass
public class Util {

    public static String formatToDate(LocalDateTime localDateTime) {
        return localDateTime == null ? ""
                : localDateTime.format(ofPattern("dd MMM ''yy"));
    }

    public static String formatToTime(LocalDateTime localDateTime) {
        return localDateTime == null ? ""
                : localDateTime.format(ofPattern("HH:mm"));
    }
}
