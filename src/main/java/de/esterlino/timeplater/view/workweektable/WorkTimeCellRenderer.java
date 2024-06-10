/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.esterlino.timeplater.view.workweektable;

import de.esterlino.timeplater.worktimes.model.WorkTime;
import java.awt.Color;
import java.awt.Component;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Julien
 */
public class WorkTimeCellRenderer implements TableCellRenderer {

    private static final Color DEFAULT_BACKGROUND = UIManager.getDefaults().getColor("Table.background");
    private static final Color SELECTED_BACKGROUND = DEFAULT_BACKGROUND.darker();

    private WorkTimeColumnPanel toRender = new WorkTimeColumnPanel();

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value == null) {
            toRender.setEnabled(false);
            toRender.setContent(null);
            return toRender;
        }
        if (!(value instanceof WorkTime)) {
            Logger.getLogger(WorkTimeCellRenderer.class.getName() + ".getTableCellRendererComponent(JTable, Object, boolean, boolean, int, int)").warning(String.format(
                    "Not a WorkTime..."));
            return null;
        }

        WorkTime workTime = (WorkTime) value;
        boolean workTimeEmpty = workTime.getWorkDuration().isZero();
        
        toRender.setEnabled(!workTimeEmpty);
        toRender.setContent(value);
        
//        toRender.setAllBackgrounds(isSelected ? SELECTED_BACKGROUND : DEFAULT_BACKGROUND);

        return toRender;
    }
}
