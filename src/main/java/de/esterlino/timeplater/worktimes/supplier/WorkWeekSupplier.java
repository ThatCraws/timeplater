package de.esterlino.timeplater.worktimes.supplier;

import de.esterlino.timeplater.worktimes.model.WorkWeek;

public interface WorkWeekSupplier {
    public WorkWeek supplyWorkWeek(final int calendarWeek);
}
