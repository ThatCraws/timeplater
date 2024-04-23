/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.esterlino.timeplater.utils;

import de.esterlino.timeplater.worktimes.model.WorkDay;
import de.esterlino.timeplater.worktimes.model.WorkTime;
import java.time.DayOfWeek;
import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.TUESDAY;
import static java.time.DayOfWeek.WEDNESDAY;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;

/**
 *
 * @author Julien
 */
public class DateTimeFormatUtils {
    private static final int WRONG_NUMBER_IN_TIME_FORMAT_ERROR = 69;

    private DateTimeFormatUtils() {
        throw new AssertionError("Tried to create Instance of " + getClass().getSimpleName());
    }

    public static String workDayFormat(final WorkDay workDay, final int calendarWeek) {

        if (workDay.getHomeTime() == null) {
            return "";
        }

        StringBuilder outputStringBuilder = new StringBuilder();
        WorkTime homeTime = workDay.getHomeTime();
        LocalDate dayOfWork = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        dayOfWork = dayOfWork
                .plusDays((calendarWeek - 1) * 7 + workDay.getDayOfWeek().get(ChronoField.DAY_OF_WEEK) - 1);

        outputStringBuilder.append(dayOfWeekFormat(workDay.getDayOfWeek()));

        outputStringBuilder.append(", d. ").append(dayOfWork.format(DateTimeFormatter.ofPattern("dd.LL.")));
        outputStringBuilder.append("\n");

        outputStringBuilder.append(homeTime.getStartTime().toString()).append(" - ")
                .append(homeTime.getEndTime().toString());
        if (!workDay.getBreakTime().getBreakDuration().isZero()) {
            outputStringBuilder
                    .append(workDay.getBreakTime().isAtHome()
                            ? String.format(" (inkl. %d Minuten Pause)", workDay.getBreakTime().getBreakDuration().toMinutes())
                            : " (Pause im Unternehmen)");
        }
        outputStringBuilder.append("\n");
        outputStringBuilder.append("(").append(durationFormat(homeTime.getWorkDuration()));
        if (workDay.getBreakTime().isAtHome() && !workDay.getBreakTime().getBreakDuration().isZero()) {
            outputStringBuilder.append(" - ").append(durationFormat(workDay.getBreakTime().getBreakDuration()))
                    .append(" = ")
                    .append(durationFormat(homeTime.getWorkDuration().minus(workDay.getBreakTime().getBreakDuration())));
        }
        outputStringBuilder.append(")");
        outputStringBuilder.append("\n");

        return new String(outputStringBuilder);
    }

    private static String dayOfWeekFormat(final DayOfWeek dayOfWeek) {
        String toRet = "";
        switch (dayOfWeek) {
            case MONDAY -> toRet = "Mo.";
            case TUESDAY -> toRet = "Di.";
            case WEDNESDAY -> toRet = "Mi.";
            case THURSDAY -> toRet = "Do.";
            case FRIDAY -> toRet = "Fr.";
            case SATURDAY -> toRet = "Sa.";
            case SUNDAY -> toRet = "So.";
        }

        return toRet;
    }

    public static String localTimeFormat(final LocalTime time) {
        int hour = time.getHour();
        int min = time.getMinute();

        return intTimeFormat(hour, min);
    }

    public static String durationFormat(final Duration duration) {
        int hoursPart = duration.toHoursPart();
        int minutesPart = duration.toMinutesPart();
        return intTimeFormat(hoursPart, minutesPart);
    }

    public static String padTimeStringWithLeadingZeroIfNecessary(String timeString) {
        String[] hourMinArr = timeString.split(":");
        if (hourMinArr.length != 2) {
            throw new DateTimeParseException("Wrong number of \":\" parsed. String not in \"HH:MM\"-format.", timeString, WRONG_NUMBER_IN_TIME_FORMAT_ERROR);
        }

        if (hourMinArr[0].length() == 1) {
            timeString = "0" + hourMinArr[0] + ":" + hourMinArr[1];
        }

        return timeString;
    }

    private static String intTimeFormat(final int hour, final int min) {
        String timeString = String.format("%s%d:%s%d",
                hour < 10 ? "0" : "",
                hour,
                min < 10 ? "0" : "",
                min);

        return timeString;
    }
}
