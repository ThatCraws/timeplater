/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.esterlino.timeplater.worktimes.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Julien
 */
public class TimeplaterModelImpl implements TimeplaterModel {

    private final List<TimeplaterModelListener> listeners = new ArrayList<>();

    private List<WorkWeek> loadedWorkWeeks = new ArrayList<>();
    private WorkWeek selectedWorkWeek = null;

    @Override
    public List<WorkWeek> getLoadedWorkWeeks() {
        return loadedWorkWeeks;
    }

    @Override
    public void setLoadedWorkWeeks(List<WorkWeek> loadedWorkWeeks) {
        this.loadedWorkWeeks = loadedWorkWeeks;
        fireLoadedWorkWeeksChanged(loadedWorkWeeks);
    }

    @Override
    public WorkWeek getSelectedWorkWeek() {
        return selectedWorkWeek;
    }

    @Override
    public void setSelectedWorkWeek(WorkWeek selectedWorkWeek) {
        this.selectedWorkWeek = selectedWorkWeek;
        fireSelectedWorkWeekChanged(selectedWorkWeek);
    }

    @Override
    public void addListener(TimeplaterModelListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(TimeplaterModelListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void fireSelectedWorkWeekChanged(WorkWeek workWeek) {
        for (TimeplaterModelListener listener : listeners) {
            listener.selectedWorkWeekChanged(workWeek);
        }
    }

    @Override
    public void fireLoadedWorkWeeksChanged(final List<WorkWeek> workWeeks) {
        for (TimeplaterModelListener listener : listeners) {
            listener.loadedWorkWeeksChanged(workWeeks);
        }
    }
}
