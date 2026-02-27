/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.esterlino.timeplater.worktimes.model;

import java.util.List;

/**
 *
 * @author <a href=mailto:julien.krause@pm.me>Julien Krause</a>
 */
public interface TimeplaterModel {
    
    public List<WorkWeek> getLoadedWorkWeeks();
    public void setLoadedWorkWeeks(final List<WorkWeek> loadedWorkWeeks);
    
    public WorkWeek getSelectedWorkWeek();
    public void setSelectedWorkWeek(WorkWeek selectedWorkWeek);
    
    public void addListener(final TimeplaterModelListener listener);
    public void removeListener(final TimeplaterModelListener listener);
    
    public void fireLoadedWorkWeeksChanged(final List<WorkWeek> workWeeks);
    public void fireSelectedWorkWeekChanged(final WorkWeek selectedWorkWeek);
}
