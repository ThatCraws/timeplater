package de.esterlino.timeplater.worktimes.model;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents one set of working days for a week.
 * 
 * @author <a href=mailto:julien.krause@pm.me>Julien</a>
 */
public class WorkWeek {
    private final int calendarWeek;
    private final Map<DayOfWeek, WorkDay> workDays;

    /**
     * Constructs a {@link WorkWeek} with the given array of {@code WorkWeeks}.
     * It will be iterated and duplicate values for the
     * {@link WorkDay#getDayOfWeek()}
     * will override each other. So the last instance of each {@link DayOfWeek} will
     * be used.
     * 
     * @param workDayArray Array of {@code WorkDays}
     */
    public WorkWeek(final int calendarWeek, final WorkDay[] workDayArray) {
        this.calendarWeek = calendarWeek;
        this.workDays = new HashMap<>();

        for (WorkDay workDay : workDayArray) {
            if (workDay != null) {
                workDays.put(workDay.getDayOfWeek(), workDay);
            }
        }
    }

    /**
     * Constructs an empty {@link WorkWeek}.
     * 
     * @param calendarWeek The calendar week of the year to construct a
     *                     {@link WorkWeek} for
     */
    public WorkWeek(final int calendarWeek) {
        this(calendarWeek, new WorkDay[] {});
    }

    /**
     * Adds a {@link WorkDay} to this {@link WorkWeek}. If {@code replace} is
     * {@code true} (default), will replace {@code WorkDays} of the same
     * {@code dayOfTheWeek}.
     * 
     * @param workDay The {@link WorkDay} to add to this {@link WorkWeek}
     * @param replace If true, replace {@code WorkDays} referring to the same
     *                {@code dayOfWeek}. If false, will do nothing, if
     *                {@code workDay} with same {@code dayOfTheWeek} exists already.
     *                <b>Default: {@code true}</b>
     * @return The {@link WorkDay} that was previously set for the day of the week
     *         of the added {@link WorkDay}.
     *         Will be {@code null}, if none pre-existed or replace is {@code false}
     *         and
     *         {@link WorkDay} of the same day of the week did already exist
     */
    public WorkDay addWorkDay(final WorkDay workDay, final boolean replace) {
        if (!replace && workDays.containsKey(workDay.getDayOfWeek())) {
            return null;
        }
        return workDays.put(workDay.getDayOfWeek(), workDay);
    }

    /**
     * Adds a {@link WorkDay} to this {@link WorkWeek}. Will replace
     * {@code WorkDays} of the same day of the week, if present.
     * 
     * @param workDay The {@link WorkDay} to add to this {@link WorkWeek}
     * @return The {@link WorkDay} that previously set for this day of the week
     *         (null, if none!)
     */
    public WorkDay addWorkDay(final WorkDay workDay) {
        return addWorkDay(workDay, true);
    }

    /**
     * Returns the {@link WorkDay}, that is set for the given {@link DayOfWeek}
     * 
     * @param dayOfWeek The day of the work week to retrieve the {@link WorkDay} for
     * @return The {@link WorkDay} referenced by the given {@code dayOfWeek}
     */
    public WorkDay getWorkDay(final DayOfWeek dayOfWeek) {
        return workDays.get(dayOfWeek);
    }

    public WorkDay[] getWorkDays() {
        ArrayList<WorkDay> daysOfWeekWorked = new ArrayList<>();
        daysOfWeekWorked.addAll(workDays.values());
        daysOfWeekWorked.sort((dayOne, dayTwo) -> {
            return dayOne.getDayOfWeek().compareTo(dayTwo.getDayOfWeek());
        });
        
        return daysOfWeekWorked.toArray(WorkDay[]::new);
    }

    public int getCalendarWeek() {
        return calendarWeek;
    }
}
