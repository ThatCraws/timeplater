package de.esterlino.timeplater.worktimes.supplier;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * 
 * @author <a href=mailto:julien.krause@pm.me>Julien Krause</a>
 */
public interface ExcelWorkbookSupplier {
    public Workbook supplyWorkbook() throws ExcelWorkbookInitializationException;
}
