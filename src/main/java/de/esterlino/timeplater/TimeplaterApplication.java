package de.esterlino.timeplater;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import de.esterlino.timeplater.worktimes.ouputter.MailTemplateWorkWeekOutputter;
import de.esterlino.timeplater.worktimes.supplier.ExcelWorkWeekSupplier;
import de.esterlino.timeplater.worktimes.supplier.ExcelWorkbookInitializationException;

public class TimeplaterApplication {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Too few arguments. Please enter: inputFile calendarWeek [outputFile]");
            return;
        }

        ExcelWorkWeekSupplier supplier = null;
        try {
            supplier = new ExcelWorkWeekSupplier(args[0]);
        } catch (ExcelWorkbookInitializationException e) {
            e.printStackTrace(System.err);
            return;
        }
        
        MailTemplateWorkWeekOutputter outputter = new MailTemplateWorkWeekOutputter(args.length >= 3 ? args[2] : "output.txt");
        outputter.createOutput(supplier.supplyWorkWeek(Integer.parseInt(args[1])));
    }
}
