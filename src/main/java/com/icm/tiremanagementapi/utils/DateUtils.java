package com.icm.tiremanagementapi.utils;

import java.time.Month;
import java.util.Locale;

public class DateUtils {
    public static String getMonthName(int month) {
        Month monthEnum = Month.of(month);
        return monthEnum.getDisplayName(java.time.format.TextStyle.FULL, new Locale("es", "ES"));
    }
}
