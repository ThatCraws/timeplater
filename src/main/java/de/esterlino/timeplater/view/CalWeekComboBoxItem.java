/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.esterlino.timeplater.view;

import de.esterlino.timeplater.worktimes.model.WorkWeek;

/**
 *
 * @author Julien
 */
public class CalWeekComboBoxItem {

    private final WorkWeek workWeek;
    
    public CalWeekComboBoxItem(final WorkWeek workWeek) {
        this.workWeek = workWeek;
    }

    @Override
    public String toString() {
        String calWeekString = String.valueOf(workWeek.getCalendarWeek());
        return String.format("KW%s%s",(calWeekString.length() == 1) ? "0" : "", calWeekString);
    }

    public WorkWeek getWorkWeek() {
        return workWeek;
    }
}
