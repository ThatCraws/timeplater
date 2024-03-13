package de.esterlino.timeplater;

import de.esterlino.timeplater.worktimes.ouputter.MailTemplateWorkWeekOutputter;
import de.esterlino.timeplater.worktimes.supplier.ExcelWorkWeekSupplier;
import de.esterlino.timeplater.worktimes.supplier.FileExcelWorkbookSupplier;

public class TimeplaterApplication {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Too few arguments. Please enter: inputFile calendarWeek [outputFile]");
            return;
        }

        ExcelWorkWeekSupplier supplier = null;
        supplier = new ExcelWorkWeekSupplier(new FileExcelWorkbookSupplier(args[0]));

        MailTemplateWorkWeekOutputter outputter = new MailTemplateWorkWeekOutputter(
                args.length >= 3 ? args[2] : "output.txt");
        outputter.createOutput(supplier.supplyWorkWeek(Integer.parseInt(args[1])));
    }
}
