package com.dariom.ichirowalks;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class Util {

    public static String formatToDate(LocalDateTime localDateTime) {
        return localDateTime == null
                ? ""
                : localDateTime.format(DateTimeFormatter.ofPattern("dd MMM ''yy"));
    }

    public static String formatToTime(LocalDateTime localDateTime) {
        return localDateTime == null
                ? ""
                : localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
