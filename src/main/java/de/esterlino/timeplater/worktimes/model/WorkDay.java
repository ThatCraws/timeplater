package de.esterlino.timeplater.worktimes.model;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

public class WorkDay {
    private final LocalTime startTimeHome;
    private final LocalTime endTimeHome;
    private final LocalTime startTimeOffice;
    private final LocalTime endTimeOffice;

    private final boolean breakAtHome;
    /**
     * The break made that day in minutes.
     */
    private int breakTime;

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
     */
    public WorkDay(
            final DayOfWeek dayOfWeek,
            final LocalTime startTimeHome, final LocalTime endTimeHome,
            final LocalTime startTimeOffice, final LocalTime endTimeOffice,
            final boolean breakAtHome) {

        this.dayOfWeek = dayOfWeek;
        this.startTimeHome = startTimeHome;
        this.endTimeHome = endTimeHome;
        this.startTimeOffice = startTimeOffice;
        this.endTimeOffice = endTimeOffice;
        this.breakAtHome = breakAtHome;

        breakTime = getBreakTime();
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
    public WorkDay(final DayOfWeek dayOfWeek, final LocalTime startTime, final LocalTime endTime,
            final boolean breakAtHome) {
        this.dayOfWeek = dayOfWeek;
        this.breakAtHome = breakAtHome;
        if (breakAtHome) {
            this.startTimeHome = startTime;
            this.endTimeHome = endTime;
            this.startTimeOffice = null;
            this.endTimeOffice = null;
        } else {
            this.startTimeHome = null;
            this.endTimeHome = null;
            this.startTimeOffice = startTime;
            this.endTimeOffice = endTime;
        }

        breakTime = getBreakTime();
    }

    private int getBreakTime() {
        // TODO: Do
        // getTimeAtWork
        return Integer.MAX_VALUE;
    }

    // Setters / Getters
    public void setBreakTime(final int breakTime) {
        this.breakTime = breakTime;
    }

    public DayOfWeek getDayOfWeek() { 
        return dayOfWeek;
    }

    public Duration getNettoWorkTime() {
        if (isWorkFromHomeOnly()) {
            return Duration.between(endTimeHome, startTimeHome).minus(Duration.ofMinutes((long) breakTime));
        } else {
            // TODO: Check if it was a hybrid-day!!!
            return Duration.between(endTimeOffice, startTimeOffice).minus(Duration.ofMinutes(breakTime));
        }
    }

    /**
     * 
     * @return True, if I worked from home <b>only</b> that day
     */
    private boolean isWorkFromHomeOnly() {
        return startTimeOffice == null || endTimeOffice == null;
    }

}
