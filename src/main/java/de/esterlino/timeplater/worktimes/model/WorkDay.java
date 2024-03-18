package de.esterlino.timeplater.worktimes.model;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

/**
 * Represents a day of work with potentially two {@link WorkTime}{@code s}, one
 * for working in home-office, one for working on-site in the office.
 * Handles calculation of {@link breakDuration} and supplies convenience methods
 * for
 * calculating different important/useful Durations
 * 
 * @see WorkTime
 * @see LocalTime
 * @see Duration
 * 
 * @author <a href=mailto:julien.krause@pm.me>Julien</a>
 */
public class WorkDay {

    /**
     * After exceeding this amount of work hours, the short break has to be at least this many minutes.
     * 
     * @see #SHORT_BREAK_MINS
     */
    public static final int THRESHOLD_WORK_HOURS_SHORT_BREAK = 6;
    /**
     * After exceeding this amount of work hours, the long break hat to be at least this many minutes.
     * 
     * @see #LONG_BREAK_MINS
     */
    public static final int THRESHOLD_WORK_HOURS_LONG_BREAK = 9;
    /**
     * The number of minutes to be subtracted (as default) from work time, after
     * the threshold for the short break has been exceeded.
     * 
     * @see #THRESHOLD_WORK_HOURS_SHORT_BREAK
     */
    public static final int SHORT_BREAK_MINS = 30;
    /**
     * The number of minutes to be subtracted (as default) from work time, after
     * the threshold for the long break has been exceeded.
     * 
     * @see #THRESHOLD_WORK_HOURS_LONG_BREAK
     */
    public static final int LONG_BREAK_MINS = 45;

    /**
     * Time spent working in home-office (brutto, so including break)
     */
    private final WorkTime homeTime;
    /**
     * Time spent working in on-site office (brutto, so including break)
     */
    private final WorkTime officeTime;
    /**
     * Shows where the break was had. If {@code true}, then it was at
     * home. If false, it was in the office.
     */
    private boolean breakAtHome;
    /**
     * The duration of the break that day.
     */
    private Duration breakDuration;
    /**
     * The day, which this {@code WorkDay} represents.
     */
    private DayOfWeek dayOfWeek;

    /**
     * This constructor constructs a {@code WorkDay}, setting the fields to the
     * given parameters. The values {@code homeTime} or {@code officeTime} can be
     * {@code null}. The break will be counted towards the longer working-time. Set
     * via {@link setBreakAtHome}, if incorrect.
     * 
     * @param dayOfWeek  The day of the week this work-day was
     * @param homeTime   The worked time in home-office
     * @param officeTime The worked time in the on-site office
     * 
     * @see setBreakAtHome
     */
    public WorkDay(
            final DayOfWeek dayOfWeek,
            final WorkTime homeTime,
            final WorkTime officeTime) {
        this.dayOfWeek = dayOfWeek;
        this.homeTime = homeTime;
        this.officeTime = officeTime;

        if (homeTime == null) {
            breakAtHome = false;
        } else if (officeTime == null) {
            breakAtHome = true;
        } else {
            breakAtHome = homeTime.getWorkDuration().compareTo(officeTime.getWorkDuration()) >= 0;
        }

        breakDuration = calculateMinimumBreakDuration(getBruttoWorkDuration());
    }

    /**
     * This constructor constructs a {@code WorkDay}, where I switched either from
     * home office to on-site office or from on-site office to home-office.
     * <br>
     * <br>
     * The {@code breakTime} will be set solely by duration of work-time. If it
     * differs, set retroactively, via {@link setBreakDuration}. Break will also be
     * counted towards the longer work-duration {@link WorkTime}. Can be adjusted
     * via {@link setBreakAtHome}.
     * 
     * @param dayOfWeek       The day of the week this work-day was
     * @param startTimeHome   Starting time of home office
     * @param endTimeHome     Ending time of home office
     * @param startTimeOffice Starting time of on-site office
     * @param endTimeOffice   Ending time of on-site office
     * 
     * @see #calculateMinimumBreakDuration
     * @see #setBreakAtHome
     */
    public WorkDay(
            final DayOfWeek dayOfWeek,
            final LocalTime startTimeHome, final LocalTime endTimeHome,
            final LocalTime startTimeOffice, final LocalTime endTimeOffice) {

        this(
                dayOfWeek,
                startTimeHome != null && endTimeHome != null ? new WorkTime(startTimeHome, endTimeHome) : null,
                startTimeOffice != null && endTimeOffice != null ? new WorkTime(startTimeOffice, endTimeOffice) : null);
    }

    /**
     * This constructor constructs a WorkDay, where I either worked only in the
     * home-office or only in the on-site office.
     * <br>
     * <br>
     * The {@code breakTime} will be set solely by duration of work-time. If it
     * differs, set retroactively via {@link setBreakDuration}.
     * 
     * @param dayOfWeek    The day of the week this work-day was
     * @param startTime    Time of starting work
     * @param endTime      Time of ending work
     * @param workedAtHome If true, the {@link WorkTime} will be constructed for
     *                     {@link #homeTime}, else for {@link #officeTime}
     */
    public WorkDay(
            final DayOfWeek dayOfWeek,
            final LocalTime startTime,
            final LocalTime endTime,
            final boolean workedAtHome) {

        this(
                dayOfWeek,
                workedAtHome ? startTime : null,
                workedAtHome ? endTime : null,
                workedAtHome ? null : startTime,
                workedAtHome ? null : endTime);
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

    public void setBreakAtHome(final boolean breakAtHome) {
        this.breakAtHome = breakAtHome;
    }

    public boolean isBreakAtHome() {
        return breakAtHome;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
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
