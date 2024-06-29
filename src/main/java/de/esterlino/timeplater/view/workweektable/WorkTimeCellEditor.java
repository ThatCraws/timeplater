/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.esterlino.timeplater.view.workweektable;

import de.esterlino.timeplater.worktimes.model.WorkTime;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.time.LocalTime;
import java.util.EventObject;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author <a href=mailto:julien.krause@pm.me>Julien Krause</a>
 */
public class WorkTimeCellEditor extends WorkTimeColumnPanel implements TableCellEditor {

    private final Set<CellEditorListener> listeners = new HashSet<>();

    public WorkTimeCellEditor() {
        super(new WorkTime(LocalTime.NOON, LocalTime.MIDNIGHT), true);
        setEnabled(true);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

        if (value instanceof WorkTime) {
            setContent(value);
        }
        
        return this;
    }

    @Override
    public Object getCellEditorValue() {
        return getContent();
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {
        return (anEvent instanceof MouseEvent) && (((MouseEvent) anEvent).getClickCount() >= 2);
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return true;
    }

    @Override
    public boolean stopCellEditing() {
        fireEditingStopped();
        return true;
    }

    @Override
    public void cancelCellEditing() {
        fireEditingCanceled();
    }
    
    private void fireEditingCanceled() {
        CellEditorListener[] listenerArr = listeners.toArray(CellEditorListener[]::new);

        for (CellEditorListener listener : listenerArr) {
            listener.editingCanceled(new ChangeEvent(getContent()));
        }
    }
    
    private void fireEditingStopped() {
        CellEditorListener[] listenerArr = listeners.toArray(CellEditorListener[]::new);

        for (CellEditorListener listener : listenerArr) {
            listener.editingStopped(new ChangeEvent(getContent()));
        }
    }

    @Override
    public void addCellEditorListener(CellEditorListener toAdd) {
        if (!listeners.contains(toAdd)) {
            listeners.add(toAdd);
        }
    }

    @Override
    public void removeCellEditorListener(CellEditorListener toRemove) {
        listeners.remove(toRemove);
    }
}
