/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package de.esterlino.timeplater.controller;

import de.esterlino.timeplater.worktimes.model.TimeplaterModelListener;
import de.esterlino.timeplater.worktimes.model.WorkWeek;
import java.io.File;

/**
 * Supplies the view with methods to propagate User-Input events to the model.
 *
 * @author <a href=mailto:julien.krause@pm.me>Julien Krause</a>
 */
public interface TimeplaterController extends TimeplaterModelListener {

    public void outputTriggered(final WorkWeek toOutput);

    public void workweekChanged(WorkWeek workWeek);

    public void workbookFileChanged(File workbookFile);

}
