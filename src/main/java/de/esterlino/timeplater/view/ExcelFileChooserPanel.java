/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package de.esterlino.timeplater.view;

import de.esterlino.timeplater.view.content.Content;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Julien
 */
public class ExcelFileChooserPanel extends JPanel implements Content {

    private File excelFile = null;

    /**
     * Creates new form ExcelFileChooserPanelWithForm
     */
    public ExcelFileChooserPanel() {
        initComponents();
        
        FileFilter excelFileFilter = new FileNameExtensionFilter("Excel Spreadsheets (.xlsx)", "xlsx");
        excelFileChooser.setFileFilter(excelFileFilter);
    }

    private void updateControls() {
        excelFileChooser.setSelectedFile(excelFile);
        excelFileCurrentSelectionLabel.setText(excelFile.getAbsolutePath());
    }

    @Override
    public Object getContent() {
        return excelFile;
    }

    @Override
    public void _setContent(Object content) {
        excelFile = (File) content;
        updateControls();
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

        excelFileChooser = new JFileChooser();
        excelFileChooserButtonLabel = new JLabel();
        excelFileChooserButton = new JButton();
        excelFileSelectedLabel = new JLabel();
        excelFileCurrentSelectionLabel = new JLabel();

        excelFileChooser.setDialogTitle("Pick worktimes-table");
        excelFileChooser.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        setLayout(new GridBagLayout());

        excelFileChooserButtonLabel.setText("XLSX-File:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new Insets(3, 7, 5, 4);
        add(excelFileChooserButtonLabel, gridBagConstraints);

        excelFileChooserButton.setText("Open XLSX-File...");
        excelFileChooserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                excelFileChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new Insets(3, 7, 5, 4);
        add(excelFileChooserButton, gridBagConstraints);

        excelFileSelectedLabel.setText("File selected:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new Insets(3, 7, 5, 4);
        add(excelFileSelectedLabel, gridBagConstraints);

        excelFileCurrentSelectionLabel.setText("No file selected");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new Insets(3, 7, 5, 4);
        add(excelFileCurrentSelectionLabel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void excelFileChooserButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_excelFileChooserButtonActionPerformed
        int dialogReturn = excelFileChooser.showOpenDialog(this);
        if (dialogReturn == JFileChooser.APPROVE_OPTION) {
            excelFile = excelFileChooser.getSelectedFile();
            if (excelFile != null) {
                setContent(excelFile);
            }
        }
    }//GEN-LAST:event_excelFileChooserButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    JFileChooser excelFileChooser;
    JButton excelFileChooserButton;
    JLabel excelFileChooserButtonLabel;
    JLabel excelFileCurrentSelectionLabel;
    JLabel excelFileSelectedLabel;
    // End of variables declaration//GEN-END:variables

}
