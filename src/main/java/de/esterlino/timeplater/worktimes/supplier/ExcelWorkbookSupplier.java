package de.esterlino.timeplater.worktimes.supplier;

import org.apache.poi.ss.usermodel.Workbook;

public interface ExcelWorkbookSupplier {
    public Workbook supplyWorkbook() throws ExcelWorkbookInitializationException;
}
