/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package de.esterlino.timeplater.view;

import de.esterlino.timeplater.worktimes.model.WorkWeek;
import java.io.File;
import java.util.List;

/**
 *
 * @author Julien
 */
public interface TimeplaterView {

    public void updateLoadedWorkWeeks(final List<WorkWeek> workWeeks);
    
    public void updateSelectedWorkWeek(final WorkWeek selectedWorkWeek);
    
    public void addListener(final TimeplaterViewListener listener);
    public void removeListener(final TimeplaterViewListener listener);
    public void fireWorkbookFileChanged(final File workbookFile);
    public void fireWorkWeekChanged(final WorkWeek workWeek);
    public void fireOutputTriggered(final WorkWeek toOutput);

}
