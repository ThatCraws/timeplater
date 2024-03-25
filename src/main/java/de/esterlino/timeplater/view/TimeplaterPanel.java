/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package de.esterlino.timeplater.view;

import de.esterlino.timeplater.view.content.ContentEvent;
import de.esterlino.timeplater.view.workweektable.BreakTimeCellRenderer;
import de.esterlino.timeplater.view.workweektable.DayCellRenderer;
import de.esterlino.timeplater.view.workweektable.WorkTimeCellRenderer;
import de.esterlino.timeplater.view.workweektable.WorkWeekTableModel;
import de.esterlino.timeplater.worktimes.model.WorkWeek;
import de.esterlino.timeplater.worktimes.supplier.ExcelWorkWeekSupplier;
import de.esterlino.timeplater.worktimes.supplier.FileExcelWorkbookSupplier;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author Julien
 */
public class TimeplaterPanel extends javax.swing.JPanel {

    private ExcelWorkWeekSupplier workWeekSupplier = null;
    private WorkWeek selectedWorkWeek = null;

    private WorkWeekTableModel tableModel = new WorkWeekTableModel();
    private final DayCellRenderer dayRenderer = new DayCellRenderer();
    private final WorkTimeCellRenderer workTimeRenderer = new WorkTimeCellRenderer();
    private final BreakTimeCellRenderer breakRenderer = new BreakTimeCellRenderer();

    private final CalWeekComboBoxActionListener calWeekComboBoxActionListener = new CalWeekComboBoxActionListener();

    /**
     * Creates new form TimeplaterPanel
     */
    public TimeplaterPanel() {
        initComponents();
        initialize();
    }

    private void initialize() {
        workWeekTable.setModel(tableModel);
        workWeekTable.getColumnModel().getColumn(WorkWeekTableModel.DAY_COLUMN_INDEX).setCellRenderer(dayRenderer);
        workWeekTable.getColumnModel().getColumn(WorkWeekTableModel.HOME_COLUMN_INDEX).setCellRenderer(workTimeRenderer);
        workWeekTable.getColumnModel().getColumn(WorkWeekTableModel.ONSITE_COLUMN_INDEX).setCellRenderer(workTimeRenderer);
        workWeekTable.getColumnModel().getColumn(WorkWeekTableModel.BREAK_COLUMN_INDEX).setCellRenderer(breakRenderer);
        

        excelFileChooserPanel.addContentListener((ContentEvent ce) -> {
            setExcelFile((File) ce.getContent());
        });
        
        calWeekComboBox.addActionListener(calWeekComboBoxActionListener);
    }

    private void updateControls() {
        int calWeekComboBoxCount = calWeekComboBox.getItemCount();
        int calWeekCount = workWeekSupplier.getCalendarWeekCount();
        if (calWeekComboBox.getItemCount() != workWeekSupplier.getCalendarWeekCount()) {
            calWeekComboBox.removeActionListener(calWeekComboBoxActionListener);
            calWeekComboBox.removeAllItems();
            for (int i = workWeekSupplier.getFirstCalendarWeek(); i <= workWeekSupplier.getCalendarWeekCount() + (workWeekSupplier.getFirstCalendarWeek() - 1); i++) {
                calWeekComboBox.addItem("KW" + (String.valueOf(i).length() == 1 ? "0" : "") + String.valueOf(i));
            }
            calWeekComboBox.addActionListener(calWeekComboBoxActionListener);
        }

        calWeekComboBox.setEnabled(calWeekComboBox.getItemCount() > 0);

        if (tableModel.getModelWorkWeek() != selectedWorkWeek) {
            tableModel.setModelWorkWeek(selectedWorkWeek);
        }
        workWeekTable.setEnabled(tableModel.getModelWorkWeek() != null);
    }

    private void setExcelFile(File excelFile) {
        FileExcelWorkbookSupplier workbookSupplier = new FileExcelWorkbookSupplier(excelFile);
        workWeekSupplier = new ExcelWorkWeekSupplier(workbookSupplier);
        selectedWorkWeek = workWeekSupplier.supplyWorkWeek(workWeekSupplier.getFirstCalendarWeek());
        updateControls();
    }

    private class CalWeekComboBoxActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (calWeekComboBox.getItemCount() == 0 || workWeekSupplier == null) {
                return;
            }

            int calWeekIndex = calWeekComboBox.getSelectedIndex();
            int firstWeek = workWeekSupplier.getFirstCalendarWeek();
            selectedWorkWeek = workWeekSupplier.supplyWorkWeek(calWeekIndex + firstWeek);
            updateControls();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        GridBagConstraints gridBagConstraints;

        workWeekTableModel = new WorkWeekTableModel();
        excelFileChooserPanel = new ExcelFileChooserPanel();
        calWeekComboBox = new JComboBox<>();
        workWeekTableScrollPane = new JScrollPane();
        workWeekTable = new JTable();

        addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent evt) {
                formMouseWheelMoved(evt);
            }
        });
        setLayout(new GridBagLayout());
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(excelFileChooserPanel, gridBagConstraints);

        calWeekComboBox.setEnabled(false);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new Insets(3, 7, 5, 4);
        add(calWeekComboBox, gridBagConstraints);

        workWeekTable.setModel(workWeekTableModel);
        workWeekTable.setRowHeight(50);
        workWeekTable.getTableHeader().setReorderingAllowed(false);
        workWeekTableScrollPane.setViewportView(workWeekTable);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(3, 7, 5, 4);
        add(workWeekTableScrollPane, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseWheelMoved(MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        // TODO add your handling code here:
        System.out.println("Triggered");
        workWeekTableScrollPane.repaint();
    }//GEN-LAST:event_formMouseWheelMoved


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JComboBox<String> calWeekComboBox;
    private ExcelFileChooserPanel excelFileChooserPanel;
    private JTable workWeekTable;
    private WorkWeekTableModel workWeekTableModel;
    private JScrollPane workWeekTableScrollPane;
    // End of variables declaration//GEN-END:variables

}
