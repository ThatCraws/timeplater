package de.esterlino.timeplater.worktimes.supplier;

import de.esterlino.timeplater.worktimes.model.WorkWeek;

/**
 * This {@code Interface} is used to supply {@link WorkWeek}-instances to the
 * {@code TimePlater}. Implementing classes decide the source of the
 * {@link WorkWeek}.
 * 
 * @author <a href=mailto:julien.krause@pm.me>Julien Krause</a>
 */
public interface WorkWeekSupplier {
    /**
     * Return the {@link WorkWeek} of the given calendar-week.
     * 
     * @param calendarWeek The calendar-week of which to construct and return a
     *                     {@link WorkWeek}-object of
     * @return The {@link WorkWeek}, containing the respective
     *         {@link de.esterlino.timeplater.worktimes.model.WorkTime}s of the
     *         given calendar-week
     */
    public WorkWeek supplyWorkWeek(final int calendarWeek);
    public WorkWeek[] getAllWorkWeeks();
    public int getFirstCalendarWeek();
}
