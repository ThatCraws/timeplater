package de.esterlino.timeplater.worktimes.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalTime;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class WorkDayUnitTest {
    @Test
    public void testDurationGettersNoBreak_success() {
        final Duration EXPECTED_BREAK_DURATION = Duration.ZERO;
        final Duration EXPECTED_WORK_DURATION = Duration.ofHours(WorkDay.THRESHOLD_WORK_HOURS_SHORT_BREAK);

        int startTimeHour = 7;
        int endTimeHour = startTimeHour + (int) EXPECTED_WORK_DURATION.toHours();

        WorkDay dayHomeNoBreakThreshold = new WorkDay(DayOfWeek.MONDAY, LocalTime.of(startTimeHour, 0), LocalTime.of(endTimeHour, 0), true);
        assertEquals(EXPECTED_BREAK_DURATION, dayHomeNoBreakThreshold.getBreakDuration());
        assertEquals(EXPECTED_WORK_DURATION, dayHomeNoBreakThreshold.getBruttoWorkDuration());
        assertEquals(EXPECTED_WORK_DURATION, dayHomeNoBreakThreshold.getNettoWorkDuration());
    }
    
    @Test
    public void testDurationGettersShortBreak_success() {
        final Duration EXPECTED_BREAK_DURATION = Duration.ofMinutes(WorkDay.SHORT_BREAK_MINS);
        final Duration EXPECTED_WORK_DURATION_BRUTTO_LOWER_LIMIT = Duration.ofHours(WorkDay.THRESHOLD_WORK_HOURS_SHORT_BREAK).plusMinutes(1);
        final Duration EXPECTED_WORK_DURATION_NETTO_LOWER_LIMIT = EXPECTED_WORK_DURATION_BRUTTO_LOWER_LIMIT.minus(EXPECTED_BREAK_DURATION);
        final Duration EXPECTED_WORK_DURATION_BRUTTO_UPPER_LIMIT = Duration.ofHours(WorkDay.THRESHOLD_WORK_HOURS_LONG_BREAK);
        final Duration EXPECTED_WORK_DURATION_NETTO_UPPER_LIMIT = EXPECTED_WORK_DURATION_BRUTTO_UPPER_LIMIT.minus(EXPECTED_BREAK_DURATION);

        LocalTime startTime = LocalTime.of(7, 0);
        LocalTime endTime = startTime.plus(EXPECTED_WORK_DURATION_BRUTTO_LOWER_LIMIT);


        WorkDay dayHomeShortBreakLowerThreshold = new WorkDay(DayOfWeek.MONDAY, startTime, endTime, true);
        assertEquals(EXPECTED_BREAK_DURATION, dayHomeShortBreakLowerThreshold.getBreakDuration());
        assertEquals(EXPECTED_WORK_DURATION_BRUTTO_LOWER_LIMIT, dayHomeShortBreakLowerThreshold.getBruttoWorkDuration());
        assertEquals(EXPECTED_WORK_DURATION_NETTO_LOWER_LIMIT, dayHomeShortBreakLowerThreshold.getNettoWorkDuration());
        
        endTime = startTime.plus(EXPECTED_WORK_DURATION_BRUTTO_UPPER_LIMIT);

        WorkDay dayHomeShortBreakUpperThreshold = new WorkDay(DayOfWeek.MONDAY, startTime, endTime, true);
        assertEquals(EXPECTED_BREAK_DURATION, dayHomeShortBreakUpperThreshold.getBreakDuration());
        assertEquals(EXPECTED_WORK_DURATION_BRUTTO_UPPER_LIMIT, dayHomeShortBreakUpperThreshold.getBruttoWorkDuration());
        assertEquals(EXPECTED_WORK_DURATION_NETTO_UPPER_LIMIT, dayHomeShortBreakUpperThreshold.getNettoWorkDuration());
    }

    @Test
    public void testDurationGettersLongBreak_success() {
        final Duration EXPECTED_BREAK_DURATION = Duration.ofMinutes(WorkDay.LONG_BREAK_MINS);

        final Duration EXPECTED_WORK_DURATION_BRUTTO_LOWER_LIMIT = Duration.ofHours(WorkDay.THRESHOLD_WORK_HOURS_LONG_BREAK).plus(Duration.ofMinutes(1));
        final Duration EXPECTED_WORK_DURATION_NETTO_LOWER_LIMIT = EXPECTED_WORK_DURATION_BRUTTO_LOWER_LIMIT.minus(EXPECTED_BREAK_DURATION);

        LocalTime startTime = LocalTime.of(7, 0);
        LocalTime endTime = startTime.plus(EXPECTED_WORK_DURATION_BRUTTO_LOWER_LIMIT);

        WorkDay dayHomeLongBreakLowerThreshold = new WorkDay(DayOfWeek.MONDAY, startTime, endTime, true);
        assertEquals(EXPECTED_BREAK_DURATION, dayHomeLongBreakLowerThreshold.getBreakDuration());
        assertEquals(EXPECTED_WORK_DURATION_BRUTTO_LOWER_LIMIT, dayHomeLongBreakLowerThreshold.getBruttoWorkDuration());
        assertEquals(EXPECTED_WORK_DURATION_NETTO_LOWER_LIMIT, dayHomeLongBreakLowerThreshold.getNettoWorkDuration());
    }

    @Test
    public void testConstructor_settingBreakAtHome() {
        WorkTime shorterTime = new WorkTime(LocalTime.of(0, 0), LocalTime.of(2, 0));
        WorkTime longerTime = new WorkTime(LocalTime.of(0, 0), LocalTime.of(7, 0));

        WorkDay homeOnlyDay = new WorkDay(DayOfWeek.MONDAY, longerTime, null);
        WorkDay officeOnlyDay = new WorkDay(DayOfWeek.MONDAY, null, longerTime);
        
        WorkDay homeLongerDay = new WorkDay(DayOfWeek.MONDAY, longerTime, shorterTime);
        WorkDay officeLongerDay = new WorkDay(DayOfWeek.MONDAY, shorterTime, longerTime);
        
        WorkDay equalTimesDay = new WorkDay(DayOfWeek.MONDAY, longerTime, longerTime);

        assertTrue(homeOnlyDay.isBreakAtHome());
        assertFalse(officeOnlyDay.isBreakAtHome());
        
        assertTrue(homeLongerDay.isBreakAtHome());
        assertFalse(officeLongerDay.isBreakAtHome());

        assertTrue(equalTimesDay.isBreakAtHome());
    }
}
