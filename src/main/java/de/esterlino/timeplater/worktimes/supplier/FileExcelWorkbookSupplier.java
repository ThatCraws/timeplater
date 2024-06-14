package de.esterlino.timeplater.worktimes.supplier;

import java.io.File;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FileExcelWorkbookSupplier implements ExcelWorkbookSupplier {
    private final String excelPath;
    
    private File excelFile = null;

    public FileExcelWorkbookSupplier(final String excelPath) {
        this.excelPath = excelPath;
    }
    
    public FileExcelWorkbookSupplier(final File excelFile) {
        this.excelFile = excelFile;
        this.excelPath = excelFile.getPath();
    }

    @Override
    public Workbook supplyWorkbook() throws ExcelWorkbookInitializationException {
        XSSFWorkbook toRet = null;
        
        if(excelFile == null) {
            excelFile = new File(excelPath);
        }

        try {
            toRet = new XSSFWorkbook(excelFile.getAbsolutePath());
        } catch (IOException e) {
            toRet = null;
            throw new ExcelWorkbookInitializationException(
                    String.format("I/O-error when trying to get Workbook from file at \"%s\"", excelPath));
        }

        return toRet;
    }

}
