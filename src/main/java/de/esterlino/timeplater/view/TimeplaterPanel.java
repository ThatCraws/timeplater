/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package de.esterlino.timeplater.view;

import de.esterlino.timeplater.view.content.ContentEvent;
import de.esterlino.timeplater.view.workweektable.BreakTimeCellEditor;
import de.esterlino.timeplater.view.workweektable.BreakTimeCellRenderer;
import de.esterlino.timeplater.view.workweektable.DayCellRenderer;
import de.esterlino.timeplater.view.workweektable.WorkTimeCellEditor;
import de.esterlino.timeplater.view.workweektable.WorkTimeCellRenderer;
import de.esterlino.timeplater.view.workweektable.WorkWeekTableModel;
import de.esterlino.timeplater.worktimes.model.WorkWeek;
import de.esterlino.timeplater.worktimes.supplier.ExcelWorkWeekSupplier;
import de.esterlino.timeplater.worktimes.supplier.FileExcelWorkbookSupplier;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Julien
 */
public class TimeplaterPanel extends javax.swing.JPanel {

    private ExcelWorkWeekSupplier workWeekSupplier = null;
    private WorkWeek selectedWorkWeek = null;

    private final CalWeekComboBoxActionListener calWeekComboBoxActionListener = new CalWeekComboBoxActionListener();

    /**
     * Creates new form TimeplaterPanel
     */
    public TimeplaterPanel() {
        initComponents();
        initialize();
    }

    private void initialize() {
        workWeekTable.getColumnModel().getColumn(WorkWeekTableModel.DAY_COLUMN_INDEX).setCellRenderer(dayRenderer);
        workWeekTable.getColumnModel().getColumn(WorkWeekTableModel.HOME_COLUMN_INDEX).setCellRenderer(workTimeRenderer);
        workWeekTable.getColumnModel().getColumn(WorkWeekTableModel.ONSITE_COLUMN_INDEX).setCellRenderer(workTimeRenderer);
        workWeekTable.getColumnModel().getColumn(WorkWeekTableModel.BREAK_COLUMN_INDEX).setCellRenderer(breakRenderer);

        workWeekTable.getColumnModel().getColumn(WorkWeekTableModel.HOME_COLUMN_INDEX).setCellEditor(workTimeCellEditor);
        workWeekTable.getColumnModel().getColumn(WorkWeekTableModel.ONSITE_COLUMN_INDEX).setCellEditor(workTimeCellEditor);
        workWeekTable.getColumnModel().getColumn(WorkWeekTableModel.BREAK_COLUMN_INDEX).setCellEditor(breakTimeCellEditor);

        excelFileChooserPanel.addContentListener((ContentEvent ce) -> {
            setExcelFile((File) ce.getContent());
        });

        calWeekComboBox.addActionListener(calWeekComboBoxActionListener);
    }

    private void updateControls() {
        int calWeekComboBoxCount = calWeekComboBox.getItemCount();
        int calWeekCount = workWeekSupplier.getCalendarWeekCount();
        if (calWeekComboBox.getItemCount() != calWeekCount) {
            calWeekComboBox.removeActionListener(calWeekComboBoxActionListener);
            calWeekComboBox.removeAllItems();
            for (WorkWeek currWeek : workWeekSupplier.getAllWorkWeeks()) {
                String calWeekString = String.valueOf(currWeek.getCalendarWeek());
                calWeekComboBox.addItem("KW" + (calWeekString.length() == 1 ? "0" : "") + calWeekString);
            }
            calWeekComboBox.addActionListener(calWeekComboBoxActionListener);
        }
        calWeekComboBoxCount = calWeekComboBox.getItemCount();
        calWeekComboBox.setEnabled(calWeekComboBoxCount > 0);

        totalWeeksLabel.setText(String.valueOf(calWeekComboBoxCount));
        // TODO: Be less lazy
        calWeekTextField.setText(calWeekComboBoxCount > 0 ? String.valueOf(calWeekComboBox.getSelectedIndex() + 1) : "0");
        calWeekTextField.setEnabled(calWeekComboBoxCount > 0);

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

        tableModel = new WorkWeekTableModel();
        dayRenderer = new DayCellRenderer();
        workTimeRenderer = new WorkTimeCellRenderer();
        breakRenderer = new BreakTimeCellRenderer();
        workTimeCellEditor = new WorkTimeCellEditor();
        breakTimeCellEditor = new BreakTimeCellEditor();
        excelFileChooserPanel = new ExcelFileChooserPanel();
        calWeekComboBox = new JComboBox<>();
        workWeekTableScrollPane = new JScrollPane();
        workWeekTable = new JTable();
        switcherPanel = new JPanel();
        prevButton = new JButton();
        calWeekTextField = new JTextField();
        dividerLabel = new JLabel();
        totalWeeksLabel = new JLabel();
        nextButton = new JButton();
        mainSeparator = new JSeparator();
        panel1 = new Panel();
        spacerH = new JLabel();
        sendItButton = new JButton();

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

        workWeekTable.setModel(tableModel);
        workWeekTable.setRowHeight(50);
        workWeekTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        workWeekTable.setShowHorizontalLines(true);
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

        switcherPanel.setLayout(new GridBagLayout());

        prevButton.setText("<");
        prevButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                prevButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(0, 4, 4, 4);
        switcherPanel.add(prevButton, gridBagConstraints);

        calWeekTextField.setColumns(3);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(0, 4, 4, 4);
        switcherPanel.add(calWeekTextField, gridBagConstraints);

        dividerLabel.setText("/");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(0, 4, 4, 4);
        switcherPanel.add(dividerLabel, gridBagConstraints);

        totalWeeksLabel.setText("0");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(0, 4, 4, 4);
        switcherPanel.add(totalWeeksLabel, gridBagConstraints);

        nextButton.setText(">");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(0, 4, 4, 4);
        switcherPanel.add(nextButton, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        add(switcherPanel, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(0, 0, 4, 0);
        add(mainSeparator, gridBagConstraints);

        panel1.setLayout(new GridBagLayout());
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.6;
        panel1.add(spacerH, gridBagConstraints);

        sendItButton.setText("Send it!");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        panel1.add(sendItButton, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        add(panel1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseWheelMoved(MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        // TODO add your handling code here:
        System.out.println("Triggered");
        workWeekTableScrollPane.repaint();
    }//GEN-LAST:event_formMouseWheelMoved

    private void prevButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_prevButtonActionPerformed
        if (calWeekComboBox.getSelectedIndex() <= 0) {
            calWeekComboBox.setSelectedIndex(calWeekComboBox.getItemCount() - 1);
        } else {
            calWeekComboBox.setSelectedIndex(calWeekComboBox.getSelectedIndex() - 1);
        }
    }//GEN-LAST:event_prevButtonActionPerformed

    private void nextButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        if (calWeekComboBox.getSelectedIndex() >= calWeekComboBox.getItemCount() - 1) {
            calWeekComboBox.setSelectedIndex(0);
        } else {
            calWeekComboBox.setSelectedIndex(calWeekComboBox.getSelectedIndex() + 1);
        }
    }//GEN-LAST:event_nextButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private BreakTimeCellRenderer breakRenderer;
    private BreakTimeCellEditor breakTimeCellEditor;
    private JComboBox<String> calWeekComboBox;
    private JTextField calWeekTextField;
    private DayCellRenderer dayRenderer;
    private JLabel dividerLabel;
    private ExcelFileChooserPanel excelFileChooserPanel;
    private JSeparator mainSeparator;
    private JButton nextButton;
    private Panel panel1;
    private JButton prevButton;
    private JButton sendItButton;
    private JLabel spacerH;
    private JPanel switcherPanel;
    private WorkWeekTableModel tableModel;
    private JLabel totalWeeksLabel;
    private WorkTimeCellEditor workTimeCellEditor;
    private WorkTimeCellRenderer workTimeRenderer;
    private JTable workWeekTable;
    private JScrollPane workWeekTableScrollPane;
    // End of variables declaration//GEN-END:variables

}
