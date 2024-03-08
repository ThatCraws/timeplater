package de.esterlino.timeplater.worktimes.ouputter;

import de.esterlino.timeplater.worktimes.model.WorkWeek;

public interface WorkWeekOutputter<T> {
    /**
     * Creates the output for the given {@link WorkWeek}. The form of the output
     * heavily depends on the implementing classes purpose. It could be written to
     * file, created as Object or displayed in a GUI.
     * 
     * @param workWeek The {@link WorkWeek} to generate output out of.
     * 
     * @return The implementation-dependent form of the processed {@link WorkWeek}.
     *         Can be void, if for example writing to a file
     */
    public T createOutput(final WorkWeek workWeek);
}
