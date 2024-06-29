/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package de.esterlino.timeplater.worktimes.model;

import java.util.List;

/**
 *
 * @author Julien
 */
public interface TimeplaterModelListener {
    public void loadedWorkWeeksChanged(final List<WorkWeek> workWeeks);
    public void selectedWorkWeekChanged(final WorkWeek workWeek);
}
