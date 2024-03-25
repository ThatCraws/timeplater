package de.esterlino.timeplater;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import de.esterlino.timeplater.view.TimeplaterFrame;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

public class TimeplaterApplication {

    public static void main(final String[] args) {
        
        FlatLaf.setup(new FlatLightLaf());
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TimeplaterApplication.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(TimeplaterApplication.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(TimeplaterApplication.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(TimeplaterApplication.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        SwingUtilities.invokeLater(() -> {
            final TimeplaterFrame root = new TimeplaterFrame();
            root.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            root.setMinimumSize(new Dimension(550, 400));

            root.setVisible(true);
        });
    }
}
