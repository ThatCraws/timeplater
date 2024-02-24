package de.esterlino.timeplater.worktimes.supplier;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import de.esterlino.timeplater.worktimes.model.WorkDay;
import de.esterlino.timeplater.worktimes.model.WorkTime;
import de.esterlino.timeplater.worktimes.model.WorkWeek;

/**
 * This implementation of {@link WorkWeekSupplier} uses a .xlsx-file to construct/supply the requested {@link WorkWeek}.
 */
public class ExcelWorkWeekSupplier implements WorkWeekSupplier {
    // --- Constants ---
    private static final int START_HOME_ROW = 1;
    private static final int END_HOME_ROW = 2;
    
    private static final int START_OFFICE_ROW = 4;
    private static final int END_OFFICE_ROW = 5;
    
    private static final int MONDAY_COL = 2;

    // --- State vars ---
    private final Workbook workTimeWorkbook;

    private Sheet weekSheet = null;
    private Row startHomeRow = null;
    private Row endHomeRow = null;
    private Row startOfficeRow = null;
    private Row endOfficeRow = null;

    /**
     * Initialize with {@link Workbook} from given path
     * @param excelPath Path of the excel-workbook / XLSX-file
     * @throws ExcelWorkbookInitializationException If Workbook can't be found, accessed or other I/O-error
     */
    public ExcelWorkWeekSupplier(final String excelPath) throws ExcelWorkbookInitializationException {
        workTimeWorkbook = getXssfWorkbookFromPath(excelPath);
    }

    private XSSFWorkbook getXssfWorkbookFromPath(final String excelPath) throws ExcelWorkbookInitializationException {
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

    /**
     * {@inheritDoc}
     * 
     * Using the Excel-{@link Workbook} given at instantiation.
     * 
     * @see #ExcelWorkWeekSupplier(String)
     */
    @Override
    public WorkWeek supplyWorkWeek(final int calendarWeek) {

        setCalendarWeek(calendarWeek);

        WorkDay[] week = new WorkDay[5];

        for (int i = 0; i < week.length; i++) {
            WorkDay toAdd = createWorkDayFromWeekDayColIndex(MONDAY_COL + i);
            week[i] = toAdd;
        }

        return new WorkWeek(calendarWeek, week);
    }

    private void setCalendarWeek(final int calendarWeek) {
        weekSheet = getSheetOfCalendarWeek(calendarWeek);

        startHomeRow = weekSheet.getRow(START_HOME_ROW);
        endHomeRow = weekSheet.getRow(END_HOME_ROW);
        startOfficeRow = weekSheet.getRow(START_OFFICE_ROW);
        endOfficeRow = weekSheet.getRow(END_OFFICE_ROW);
    }

    /**
     * Creates and returns a {@link WeekDay} from the given column index of the desired day. Set calendar-week before calling.
     * @param colIndex Column-index of the day with the work-times
     * @return A {@link WeekDay}-instance with containing all {@link WorkTime}s, in the given column
     * 
     * @see setCalendarWeek
     */
    private WorkDay createWorkDayFromWeekDayColIndex(final int colIndex) {
        Cell startHomeCell = startHomeRow.getCell(colIndex);
        Cell endHomeCell = endHomeRow.getCell(colIndex);

        Cell startOfficeCell = startOfficeRow.getCell(colIndex);
        Cell endOfficeCell = endOfficeRow.getCell(colIndex);

        WorkTime homeWorkTime = createWorkTimeFromCells(startHomeCell, endHomeCell);
        WorkTime officeWorkTime = createWorkTimeFromCells(startOfficeCell, endOfficeCell);

        if (homeWorkTime == null && officeWorkTime == null) {
            return null;
        }

        DayOfWeek dayOfWork = null;
        for (int i = 0; i < DayOfWeek.values().length; i++) {
            if (colIndex == MONDAY_COL + i) {
                dayOfWork = DayOfWeek.values()[i];
                break;
            }
        }
        
        if (dayOfWork == null) {
            Logger.getLogger(ExcelWorkWeekSupplier.class.getName() + ".createWorkDayFromWeekDayColIndex(int)").warning(String.format(
                    "Given column-index can't be mapped to DayOfWeek. Given index: %d", colIndex));
        }

        return new WorkDay(dayOfWork, homeWorkTime, officeWorkTime);
    }

    private WorkTime createWorkTimeFromCells(final Cell startTimeCell, final Cell endTimeCell) {
        if (startTimeCell.getCellType() == CellType.BLANK || endTimeCell.getCellType() == CellType.BLANK) {
            return null;
        }

        LocalTime startTime = startTimeCell.getLocalDateTimeCellValue().toLocalTime();
        LocalTime endTime = endTimeCell.getLocalDateTimeCellValue().toLocalTime();

        return new WorkTime(startTime, endTime);
    }

    private Sheet getSheetOfCalendarWeek(final int calWeek) {
        String calWeekString = String.valueOf(calWeek);
        calWeekString = "KW" + (calWeekString.length() == 1 ? "0" : "") + calWeekString;
        if (calWeekString.length() != 4) {
            Logger.getLogger(ExcelWorkWeekSupplier.class.getName() + ".supplyWorkWeek(int)").warning(String.format(
                    "Given calendar week can't be formatted to KWXX format. Given: %d, format-attempt: %s", calWeek, calWeekString));
            return null;
        }

        final int indexFound = workTimeWorkbook.getSheetIndex(calWeekString);

        if (indexFound == -1) {
            return null;
        }

        return workTimeWorkbook.getSheetAt(indexFound);
    }
}
