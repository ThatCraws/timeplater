package de.esterlino.timeplater.worktimes.supplier;

import de.esterlino.timeplater.worktimes.model.WorkWeek;

public interface WorkTimeSupplier {
    public WorkWeek supplyWorkWeek(final int calendarWeek);
}
