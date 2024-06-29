/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.esterlino.timeplater.worktimes.ouputter;

import de.esterlino.timeplater.utils.DateTimeFormatUtils;
import de.esterlino.timeplater.worktimes.model.WorkDay;
import de.esterlino.timeplater.worktimes.model.WorkWeek;

/**
 *
 * @author <a href=mailto:julien.krause@pm.me>Julien Krause</a>
 */
public class TemplateStringWorkWeekOutputter implements WorkWeekOutputter<String> {

    @Override
    public String createOutput(WorkWeek workWeek) {
        StringBuilder output = new StringBuilder();
        
        for (WorkDay day : workWeek.getWorkDays()) {
            appendDay(output, workWeek.getCalendarWeek(), day);
        }
        
        return new String(output);
    }
    
    private void appendDay(final StringBuilder toAppendTo, final int calendarWeek, final WorkDay workDay) {
        if (workDay.getHomeTime() == null || workDay.getHomeTime().getWorkDuration().isZero()) {
            return;
        }
        
        toAppendTo.append(DateTimeFormatUtils.workDayFormat(workDay, calendarWeek));
        toAppendTo.append("\n");
    }
}
