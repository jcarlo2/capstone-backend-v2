package com.example.capstonebackendv2.service.impl;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Service
public class DateService {

    public String getDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dtf.format(LocalDateTime.now());
    }

    public String getTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        return dtf.format(LocalDateTime.now());
    }

    public @NotNull String addDays(@NotNull String start, String option) {
        String end;
        try {
            LocalDateTime date = LocalDateTime.parse(start);
            end = switch (option) {
                case "Week" -> date.plusWeeks(1).minusDays(1).toString();
                case "Month" -> date.plusMonths(1).minusDays(1).toString();
                case "Year" -> date.plusYears(1).minusDays(1).toString();
                default -> date.toString();
            };
        }catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return end.substring(0,10) + "T23:59:59";
    }

    public String fixStartDate(String start, @NotNull String option) {
        switch (option) {
            case "Annual":
            case "Year": start += "-01-01T00:00:00";
                break;
            case "Monthly":
            case "Month": start += "-01T00:00:00";
                break;
            case "Weekly":
            case "Week": start = convertWeekNumberToDate(start);
            default: start += "T00:00:00";
        }
        return start;
    }

    private @NotNull String convertWeekNumberToDate(@NotNull String start) {
        String[] arr = start.split("-");
        int week = Integer.parseInt(arr[1].substring(1));
        int year = Integer.parseInt(arr[0]);

        Calendar tempCal = Calendar.getInstance();
        tempCal.setWeekDate(year,1,GregorianCalendar.MONDAY);
        LocalDate tempDate = LocalDate.ofInstant(tempCal.toInstant(),ZoneId.systemDefault());

        if(!(year == tempDate.getYear())) week++;

        Calendar cal = Calendar.getInstance();
        cal.setWeekDate(year,week, GregorianCalendar.MONDAY);
        LocalDate date = LocalDate.ofInstant(cal.toInstant(), ZoneId.systemDefault());
        return date.toString();
    }

    public String getDateAsOf(@NotNull String option, String start) {
        start = fixStartDate(start,option).split("T")[0];
        LocalDate date = LocalDate.parse(start);
        switch (option) {
            case "Weekly" -> {
                String end = addDays(start + "T00:00:00","Week").split("T")[0];
                int endAndNow = LocalDate.parse(end).compareTo(LocalDate.now());
                if(endAndNow > 0) return start + " to " + LocalDate.now();
                return start + " to " + end;
            }
            case "Monthly" -> {
                return date.getYear() + "-" + date.getMonth();
            }
            case "Annual" -> {
                return String.valueOf(date.getYear());
            }
        }
        return start;
    }
}
