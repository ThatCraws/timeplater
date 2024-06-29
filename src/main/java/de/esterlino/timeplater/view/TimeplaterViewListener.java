/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package de.esterlino.timeplater.view;

import de.esterlino.timeplater.worktimes.model.WorkWeek;
import java.io.File;

/**
 *
 * @author <a href=mailto:julien.krause@pm.me>Julien Krause</a>
 */
public interface TimeplaterViewListener {
    public void workbookFileChanged(final File workbookFile);
    public void workweekChanged(final WorkWeek workWeek);
    public void outputTriggered(final WorkWeek toOutput);
}
