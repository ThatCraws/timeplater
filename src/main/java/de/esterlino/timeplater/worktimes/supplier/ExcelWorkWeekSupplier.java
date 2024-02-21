package de.esterlino.timeplater.worktimes.supplier;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;

import de.esterlino.timeplater.worktimes.model.WorkWeek;

public class ExcelWorkWeekSupplier implements WorkWeekSupplier {
    private final Workbook workTimeWorkBook;

    public ExcelWorkWeekSupplier(final String excelPath) {
        FileInputStream excelFileInputStream = null;
        try {
            excelFileInputStream = new FileInputStream(excelPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace(System.err);
        }
        workTimeWorkBook = null;
        try {
            if (excelFileInputStream != null) {
                excelFileInputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public WorkWeek supplyWorkWeek(final int calendarWeek) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'supplyWorkWeek'");
        return new WorkWeek(1);
    }

}
