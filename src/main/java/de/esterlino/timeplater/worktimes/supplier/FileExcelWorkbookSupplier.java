package de.esterlino.timeplater.worktimes.supplier;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FileExcelWorkbookSupplier implements ExcelWorkbookSupplier {
    private final String excelPath;

    public FileExcelWorkbookSupplier(final String excelPath) {
        this.excelPath = excelPath;
    }

    @Override
    public Workbook supplyWorkbook() throws ExcelWorkbookInitializationException {
        FileInputStream excelFileInputStream = null;
        XSSFWorkbook toRet = null;

        try {
            excelFileInputStream = new FileInputStream(excelPath);
            toRet = new XSSFWorkbook(excelFileInputStream);
            excelFileInputStream.close();
        } catch (FileNotFoundException e) {
            toRet = null;
            throw new ExcelWorkbookInitializationException(String.format(
                    "Could not find Excel-Workbook at path \"%s\". Supplier not initialized correctly", excelPath));
        } catch (IOException e) {
            toRet = null;
            throw new ExcelWorkbookInitializationException(
                    String.format("I/O-error when trying to get Workbook from file at \"%s\"", excelPath));
        }

        return toRet;
    }

}
