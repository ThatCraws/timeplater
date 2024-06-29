/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.esterlino.timeplater.view.workweektable;

import de.esterlino.timeplater.worktimes.model.BreakTime;
import de.esterlino.timeplater.worktimes.model.WorkDay;
import de.esterlino.timeplater.worktimes.model.WorkTime;
import de.esterlino.timeplater.worktimes.model.WorkWeek;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author <a href=mailto:julien.krause@pm.me>Julien Krause</a>
 */
public class WorkWeekTableModel extends AbstractTableModel {

    public static final int DAY_COLUMN_INDEX = 0;
    public static final int HOME_COLUMN_INDEX = 1;
    public static final int ONSITE_COLUMN_INDEX = 2;
    public static final int BREAK_COLUMN_INDEX = 3;

    private final String[] columns = new String[] {
        "Day",
        "Home",
        "On-Site",
        "Break",};

    private final Class[] columnClasses = new Class[] {
        Integer.class,
        WorkTime.class,
        WorkTime.class,
        BreakTime.class,};

    private WorkWeek modelWorkWeek = null;

    @Override
    public int getRowCount() {
        if (modelWorkWeek == null) {
            return 0;
        }

        return modelWorkWeek.getWorkDays().length;
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        DayOfWeek day = DayOfWeek.of(rowIndex + 1);
        WorkDay workDay = modelWorkWeek.getWorkDay(day);
        if (workDay == null) {
            workDay = new WorkDay(day, LocalTime.MIN, LocalTime.MIN, true);
            modelWorkWeek.addWorkDay(workDay);
        }

        return switch (columnIndex) {
            case DAY_COLUMN_INDEX ->
                day;
            case HOME_COLUMN_INDEX ->
                workDay.getHomeTime();
            case ONSITE_COLUMN_INDEX ->
                workDay.getOfficeTime();
            case BREAK_COLUMN_INDEX ->
                workDay.getBreakTime();
            default ->
                null;
        };
    }

    public WorkWeek getModelWorkWeek() {
        return modelWorkWeek;
    }

    public void setModelWorkWeek(final WorkWeek modelWorkWeek) {
        this.modelWorkWeek = modelWorkWeek;
        fillWorkWeekModel();
        fireTableDataChanged();
    }

    private void fillWorkWeekModel() {
        if (modelWorkWeek.getWorkDays().length >= 5) {
            return;
        }

        ArrayList<DayOfWeek> expectedDays = new ArrayList<>();
        expectedDays.addAll(Arrays.asList(DayOfWeek.values()));
        expectedDays.remove(DayOfWeek.SATURDAY);
        expectedDays.remove(DayOfWeek.SUNDAY);

        for (DayOfWeek missingDay : expectedDays) {
            WorkDay toAdd = new WorkDay(missingDay, null, null);
            modelWorkWeek.addWorkDay(toAdd, false);
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        WorkDay workDay = getByRow(rowIndex);

        switch (columnIndex) {
            case HOME_COLUMN_INDEX -> {
                WorkTime updatedHomeTime = (WorkTime) aValue;
                workDay.setHomeTime(updatedHomeTime);
            }
            case ONSITE_COLUMN_INDEX -> {
                WorkTime updatedOfficeTime = (WorkTime) aValue;
                workDay.setOfficeTime(updatedOfficeTime);
            }
            case BREAK_COLUMN_INDEX -> {
                BreakTime updatedBreakTime = (BreakTime) aValue;
                workDay.setBreakTime(updatedBreakTime);
            }
            case DAY_COLUMN_INDEX -> {
            }
            default ->
                throw new AssertionError();
        }

        fireTableRowsUpdated(rowIndex, rowIndex);
//        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        WorkDay workDay = getByRow(rowIndex);
        
        return switch(columnIndex) {
            case BREAK_COLUMN_INDEX -> !workDay.getBruttoWorkDuration().isZero();
            case DAY_COLUMN_INDEX -> false;
            default -> true;
        };
    }

    private WorkDay getByRow(final int rowIndex) {
        DayOfWeek dayOfWeek = (DayOfWeek) getValueAt(rowIndex, DAY_COLUMN_INDEX);
        WorkDay workDay = modelWorkWeek.getWorkDay(dayOfWeek);
        return workDay;
    }

}
