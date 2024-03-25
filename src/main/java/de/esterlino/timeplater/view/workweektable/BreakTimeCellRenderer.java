/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.esterlino.timeplater.view.workweektable;

import de.esterlino.timeplater.worktimes.model.BreakTime;
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
public class BreakTimeCellRenderer implements TableCellRenderer {

    private static final Color DEFAULT_BACKGROUND = UIManager.getDefaults().getColor("Table.background");
    private static final Color SELECTED_BACKGROUND = DEFAULT_BACKGROUND.darker();

    private BreakTimeColumnPanel toRender = new BreakTimeColumnPanel();

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value == null) {
            toRender.setContent(null);
            toRender.setEnabled(false);
            return toRender;
        }
        if (!(value instanceof BreakTime)) {
            Logger.getLogger(BreakTimeCellRenderer.class.getName() + ".getTableCellRendererComponent(JTable, Object, boolean, boolean, int, int)").warning(String.format(
                    String.format("Not a BreakTime, but %s", value.getClass().toGenericString())));
            return null;
        }

        toRender.setContent(value);
        toRender.setEnabled(true);

//        toRender.setAllBackgrounds(isSelected ? SELECTED_BACKGROUND : DEFAULT_BACKGROUND);

        return toRender;
    }

}
