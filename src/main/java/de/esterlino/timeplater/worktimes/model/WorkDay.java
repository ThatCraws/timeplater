package de.esterlino.timeplater.worktimes.model;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

/**
 * Represents a day of work with potentially two {@link WorkTime}{@code s}, one
 * for working in home-office, one for working on-site in the office.
 * Handles calculation of {@link breakTime} and supplies convenience methods for
 * calculating different important/useful Durations
 * 
 * @see WorkTime
 * @see LocalTime
 * @see Duration
 * 
 * @author <a href=julien.krause@pm.me>Julien</a>
 */
public class WorkDay {

    /**
     * After exceeding this amount of work hours, the short break has to be made at
     * least
     */
    public static final int THRESHOLD_WORK_HOURS_SHORT_BREAK = 6;
    /**
     * After exceeding this amount of work hours, the long break has to be made at
     * least
     */
    public static final int THRESHOLD_WORK_HOURS_LONG_BREAK = 9;
    /**
     * The duration of break minutes to be subtracted (at least), after
     * {@link THRESHOLD_WORK_HOURS_SHORT_BREAK} of work time.
     */
    public static final int SHORT_BREAK_MINS = 30;
    /**
     * The duration of break minutes to be subtracted (at least), after
     * {@link THRESHOLD_WORK_HOURS_LONG_BREAK} of work time.
     */
    public static final int LONG_BREAK_MINS = 45;

    /**
     * Time spent working in home-office (brutto, so including break)
     */
    private final WorkTime homeTime;
    /**
     * Time spent working in office-office (brutto, so including break)
     */
    private final WorkTime officeTime;
    /**
     * Shows where the break was made. If {@code true}, then the break was made at
     * home, if false, the break was made in the office.
     */
    private final boolean breakAtHome;
    /**
     * The duration of the break made that day.
     */
    private Duration breakDuration;
    /**
     * The day, which this {@code WorkDay} represents.
     */
    private DayOfWeek dayOfWeek;

    /**
     * This constructor constructs a {@code WorkDay}, where I switched either from
     * home office to office-office or from office-office to home-office.
     * <br>
     * <br>
     * The {@code breakTime} will be set solely by duration of work-time. If it
     * differs, set retroactively via {@link setBreakTime}.
     * 
     * @param dayOfWeek       The day of the week this work-day was
     * @param startTimeHome   Starting time of home office
     * @param endTimeHome     Ending time of home office
     * @param startTimeOffice Starting time of office-office
     * @param endTimeOffice   Ending time of office-office
     * @param breakAtHome     If true, the break will be counted towards the home
     *                        office. If false, the break will count towards the
     *                        office-office
     * 
     * @see calculateMinimumBreakDuration
     */
    public WorkDay(
            final DayOfWeek dayOfWeek,
            final LocalTime startTimeHome, final LocalTime endTimeHome,
            final LocalTime startTimeOffice, final LocalTime endTimeOffice,
            final boolean breakAtHome) {

        this.dayOfWeek = dayOfWeek;
        this.homeTime = startTimeHome != null && endTimeHome != null ? new WorkTime(startTimeHome, endTimeHome) : null;
        this.officeTime = startTimeOffice != null && endTimeOffice != null
                ? new WorkTime(startTimeOffice, endTimeOffice)
                : null;
        this.breakAtHome = breakAtHome;

        breakDuration = calculateMinimumBreakDuration(getBruttoWorkDuration());
    }

    /**
     * This constructor constructs a WorkDay, where I either worked only in the
     * home-office or only in the office-office.
     * <br>
     * <br>
     * The {@code breakTime} will be set solely by duration of work-time. If it
     * differs, set retroactively via {@link setBreakTime}.
     * 
     * @param dayOfWeek   The day of the week this work-day was
     * @param startTime   Time of starting work
     * @param endTime     Time of ending work
     * @param breakAtHome If true, the whole day was spent in home-office. If
     *                    false, the whole day was spent in office-office.
     */
    public WorkDay(
            final DayOfWeek dayOfWeek,
            final LocalTime startTime,
            final LocalTime endTime,
            final boolean breakAtHome) {
        this(
                dayOfWeek,
                breakAtHome ? startTime : null,
                breakAtHome ? endTime : null,
                breakAtHome ? null : startTime,
                breakAtHome ? null : endTime,
                breakAtHome);
    }

    /**
     * Calculates and returns minimal break time, legally speaking for the given
     * {@code workTime}.
     * 
     * @param workTime The Duration of total brutto working time
     * @return The minimal legally required break time for the Duration worked
     * 
     * @see #THRESHOLD_WORK_HOURS_SHORT_BREAK
     * @see #THRESHOLD_WORK_HOURS_LONG_BREAK
     * @see #SHORT_BREAK_MINS
     * @see #LONG_BREAK_MINS
     */
    private Duration calculateMinimumBreakDuration(final Duration workTime) {
        final Duration SHORT_BREAK_THRESHOLD = Duration.ofHours(THRESHOLD_WORK_HOURS_SHORT_BREAK);
        final Duration LONG_BREAK_THRESHOLD = Duration.ofHours(THRESHOLD_WORK_HOURS_LONG_BREAK);

        Duration minBreakTimeDuration = Duration.ZERO;

        if (workTime.compareTo(SHORT_BREAK_THRESHOLD) == 1
                && (workTime.compareTo(LONG_BREAK_THRESHOLD) == -1 || workTime.compareTo(LONG_BREAK_THRESHOLD) == 0)) {
            minBreakTimeDuration = minBreakTimeDuration.plusMinutes(SHORT_BREAK_MINS);
        } else if (workTime.compareTo(LONG_BREAK_THRESHOLD) == 1) {
            minBreakTimeDuration = minBreakTimeDuration.plusMinutes(LONG_BREAK_MINS);
        }

        return minBreakTimeDuration;
    }

    // Setters / Getters

    public WorkTime getHomeTime() {
        return homeTime;
    }

    public WorkTime getOfficeTime() {
        return officeTime;
    }

    /**
     * Sum up given {@link WorkTime}{@code s}' Durations
     * ({@link WorkTime#getWorkDuration()}).
     * 
     * @return Total time spent at work (including breaks)
     */
    public Duration getBruttoWorkDuration() {
        Duration bruttoWorkDuration = Duration.ZERO;

        if (isWorkFromHome()) {
            bruttoWorkDuration = bruttoWorkDuration.plus(homeTime.getWorkDuration());
        }
        if (isWorkFromOffice()) {
            bruttoWorkDuration = bruttoWorkDuration.plus(officeTime.getWorkDuration());
        }

        return bruttoWorkDuration;
    }

    /**
     * Returns the total time spent working subtracted from the recently set
     * {@link breakDuration}).
     * 
     * @return {@link Duration} of paid working time
     * 
     * @see getBruttoWorkDuration
     * @see getBreakDuration
     */
    public Duration getNettoWorkDuration() {
        return getBruttoWorkDuration().minus(getBreakDuration());
    }

    public Duration getBreakDuration() {
        return breakDuration;
    }

    public void setBreakDuration(final Duration breakDuration) {
        this.breakDuration = breakDuration;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public boolean isBreakAtHome() {
        return breakAtHome;
    }

    /**
     * @return True, if I worked from home <b>only</b> that day
     */
    private boolean isWorkFromHome() {
        return homeTime != null;
    }

    /**
     * @return True, if I worked in the office <b>only</b> that day
     */
    private boolean isWorkFromOffice() {
        return officeTime != null;
    }
}
