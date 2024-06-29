/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package de.esterlino.timeplater.view.workweektable;

import de.esterlino.timeplater.view.content.Content;
import de.esterlino.timeplater.worktimes.model.BreakTime;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextField;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;

/**
 *
 * @author <a href=mailto:julien.krause@pm.me>Julien Krause</a>
 */
public class BreakTimeColumnPanel extends JPanel implements Content {

    private BreakTime breakTime = null;
    private final boolean editMode;

    /**
     * Creates new form BreakPanel
     */
    public BreakTimeColumnPanel(final BreakTime breakTime, final boolean editMode) {
        this.breakTime = breakTime;
        this.editMode = editMode;

        initComponents();

        breakDurationPanel.setVisible(!editMode);
        breakDurationTextField.setVisible(editMode);

        updateControls();
    }

    public BreakTimeColumnPanel(final BreakTime breakTime) {
        this(breakTime, false);
    }

    public BreakTimeColumnPanel() {
        this(null);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        breakDurationLabel.setEnabled(enabled);
        breakDurationPostfixLabel.setEnabled(enabled);
        breakDurationPrefixLabel.setEnabled(enabled);
        breakLocationHomeRadioButton.setEnabled(enabled);
        breakLocationOnsiteRadioButton.setEnabled(enabled);
    }
    
    public void setBreakLocationEnabled(final boolean atHome, final boolean onSite) {
        breakLocationHomeRadioButton.setEnabled(atHome);
        breakLocationOnsiteRadioButton.setEnabled(onSite);
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

        breakLocationGroup = new ButtonGroup();
        breakDurationTextField = new TextField();
        breakDurationPanel = new JPanel();
        breakDurationPrefixLabel = new JLabel();
        breakDurationLabel = new JLabel();
        breakDurationPostfixLabel = new JLabel();
        breakLocationHomeRadioButton = new JRadioButton();
        breakLocationOnsiteRadioButton = new JRadioButton();

        setBackground(UIManager.getDefaults().getColor("Table.background"));
        setLayout(new GridBagLayout());

        breakDurationTextField.setBackground(new Color(255, 255, 255));
        breakDurationTextField.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        breakDurationTextField.setText("00:00");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new Insets(3, 7, 5, 4);
        add(breakDurationTextField, gridBagConstraints);

        breakDurationPanel.setBackground(UIManager.getDefaults().getColor("Table.background"));
        breakDurationPanel.setLayout(new GridBagLayout());

        breakDurationPrefixLabel.setBackground(UIManager.getDefaults().getColor("Table.background"));
        breakDurationPrefixLabel.setText("(");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(3, 7, 5, 0);
        breakDurationPanel.add(breakDurationPrefixLabel, gridBagConstraints);

        breakDurationLabel.setBackground(UIManager.getDefaults().getColor("Table.background"));
        breakDurationLabel.setText("00:00");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(3, 0, 5, 0);
        breakDurationPanel.add(breakDurationLabel, gridBagConstraints);

        breakDurationPostfixLabel.setBackground(UIManager.getDefaults().getColor("Table.background"));
        breakDurationPostfixLabel.setText(")");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(3, 0, 5, 4);
        breakDurationPanel.add(breakDurationPostfixLabel, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        add(breakDurationPanel, gridBagConstraints);

        breakLocationGroup.add(breakLocationHomeRadioButton);
        breakLocationHomeRadioButton.setText("Home");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        add(breakLocationHomeRadioButton, gridBagConstraints);

        breakLocationGroup.add(breakLocationOnsiteRadioButton);
        breakLocationOnsiteRadioButton.setText("On-Site");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        add(breakLocationOnsiteRadioButton, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JLabel breakDurationLabel;
    private JPanel breakDurationPanel;
    private JLabel breakDurationPostfixLabel;
    private JLabel breakDurationPrefixLabel;
    private TextField breakDurationTextField;
    private ButtonGroup breakLocationGroup;
    private JRadioButton breakLocationHomeRadioButton;
    private JRadioButton breakLocationOnsiteRadioButton;
    // End of variables declaration//GEN-END:variables

    @Override
    public Object getContent() {
        Duration dur;
        String durationString;
        if (editMode) {
            durationString = breakDurationTextField.getText();
        } else {
            durationString = breakDurationLabel.getText();
        }
        try {
            String[] durationStrings = durationString.split(":");
            String parseString = "PT" + durationStrings[0] + "H" + durationStrings[1] + "M";
            dur = Duration.parse(parseString);
        } catch (DateTimeParseException e) {
            Logger.getLogger(BreakTimeColumnPanel.class.getName()).log(Level.WARNING, String.format(
                    "Could not parse string \"%s\" into Duration; Using 30 mins", 
                    durationString), 
                    e);
            dur = Duration.ZERO.plusMinutes(30);
        }

        return new BreakTime(dur, breakLocationHomeRadioButton.isSelected());
    }

    @Override
    public void _setContent(Object content) {
        breakTime = (BreakTime) content;
        updateControls();
    }

    private void updateControls() {
        if (breakTime == null) {
            breakDurationTextField.setText("00:00");
            breakDurationLabel.setText("00:00");
            return;
        }

        Duration breakDuration = breakTime.getBreakDuration();
        String hoursPart = String.valueOf(breakDuration.toHoursPart());
        String minutesPart = String.valueOf(breakDuration.toMinutesPart());
        String breakDurationString = String.format("%s:%s", hoursPart.length() == 2 ? hoursPart : "0" + hoursPart, minutesPart.length() == 2 ? minutesPart : "0" + minutesPart);
        breakDurationTextField.setText(breakDurationString);
        breakDurationLabel.setText(breakDurationString);

        boolean breakAtHome = breakTime.isAtHome();
        breakLocationHomeRadioButton.setSelected(breakAtHome);
        breakLocationOnsiteRadioButton.setSelected(!breakAtHome);
    }
}
