package com.example.capstonebackendv2.service;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;

public interface DateService {
    String getDate();
    String getTime();
    String addDays(String start, String option);
    String fixStartDate(String start, String option);
    String getDateAsOf(String option, String start);
    default String convertWeekNumberToDate(@NotNull String start) {
        String[] arr = start.split("-");
        int week = Integer.parseInt(arr[1].substring(1));
        int year = Integer.parseInt(arr[0]);

        Calendar tempCal = Calendar.getInstance();
        tempCal.setWeekDate(year,1, GregorianCalendar.MONDAY);
        LocalDate tempDate = LocalDate.ofInstant(tempCal.toInstant(), ZoneId.systemDefault());

        if(!(year == tempDate.getYear())) week++;

        Calendar cal = Calendar.getInstance();
        cal.setWeekDate(year,week, GregorianCalendar.MONDAY);
        LocalDate date = LocalDate.ofInstant(cal.toInstant(), ZoneId.systemDefault());
        return date.toString();
    }
}
