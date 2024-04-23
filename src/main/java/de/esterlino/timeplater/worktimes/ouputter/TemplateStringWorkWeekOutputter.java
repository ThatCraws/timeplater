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
 * @author Julien
 */
public class TemplateStringWorkWeekOutputter implements WorkWeekOutputter<String> {

    @Override
    public String createOutput(WorkWeek workWeek) {
        StringBuilder output = new StringBuilder();
        
        for (WorkDay day : workWeek.getWorkDays()) {
            output.append(DateTimeFormatUtils.workDayFormat(day, workWeek.getCalendarWeek()));
            output.append("\n");
        }
        
        return new String(output);
    }
    
}
