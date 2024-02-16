package de.esterlino.timeplater;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class TimeplaterApplication {

    public static void main(String[] args) {
        JFrame frame = new JFrame("My App");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        SwingUtilities.invokeLater(() -> {
            frame.setVisible(true);
        });
    }
}
