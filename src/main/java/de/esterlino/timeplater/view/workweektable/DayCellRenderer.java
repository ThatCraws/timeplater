/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.esterlino.timeplater.view.workweektable;

import java.awt.Color;
import java.awt.Component;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author <a href=mailto:julien.krause@pm.me>Julien Krause</a>
 */
public class DayCellRenderer implements TableCellRenderer {

    private static final Color DEFAULT_BACKGROUND = UIManager.getDefaults().getColor("Table.background");
    private static final Color SELECTED_BACKGROUND = DEFAULT_BACKGROUND.darker();
    
    private final JLabel toRender = new JLabel();

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (!(value instanceof DayOfWeek)) {
            Logger.getLogger(DayCellRenderer.class.getName() + ".getTableCellRendererComponent(JTable, Object, boolean, boolean, int, int)").warning(String.format(
                    "Not a DayOfWeek"));
            return null;
        }
        
        DayOfWeek day = (DayOfWeek) value;
        toRender.setText(day.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault()));

        toRender.setBackground(isSelected ? SELECTED_BACKGROUND : DEFAULT_BACKGROUND);

        return toRender;
    }
}
