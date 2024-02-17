package de.esterlino.timeplater.worktimes.model;

import java.util.logging.Logger;

public class WorkWeek {
    private final WorkDay[] workDays;

    /**
     * Constructs a {@code WorkWeek} with the given array. The array should have size 5 and reflect the working days (so index 0 is Mo and 4 is Fr).
     * @param workDays Array of {@Code WorkDays}
     */
    public WorkWeek(final WorkDay[] workDays) {
        if (workDays.length != 5) {
            Logger.getLogger(WorkWeek.class.getName() + ".constructor(WorkDay[])").warning("Constructing WorkWeek with an array of WorkDays, that has a size different than 5");
        }
        this.workDays = workDays;
    }

    /**
     * Constructs an empty {@code WorkWeek}.
     */
    public WorkWeek() {
        this.workDays = new WorkDay[5];
    }

    /**
     * Adds a {@code WorkDay} to this {@code WorkWeek}. Will replace {@code WorkDays} of the same day of the week, if present.
     * @param workDay The {@code WorkDay} to add to this {@code WorkWeed}
     * @return The {@code WorkDay} that previously set for this day of the week (null, if none!)
     */
    public WorkDay addWorkDay(final WorkDay workDay) {
        int indexDayOfWeek = workDay.getDayOfWeek().getValue() - 1;
        WorkDay previouslySetForDayOfWeek = workDays[indexDayOfWeek];
        workDays[indexDayOfWeek] = workDay;
        return previouslySetForDayOfWeek;
    }
}
