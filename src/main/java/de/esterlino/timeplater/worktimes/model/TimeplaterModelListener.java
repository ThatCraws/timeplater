/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package de.esterlino.timeplater.worktimes.model;

import java.util.List;

/**
 *
 * @author <a href=mailto:julien.krause@pm.me>Julien Krause</a>
 */
public interface TimeplaterModelListener {
    public void loadedWorkWeeksChanged(final List<WorkWeek> workWeeks);
    public void selectedWorkWeekChanged(final WorkWeek workWeek);
}
