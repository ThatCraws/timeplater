package de.esterlino.timeplater.worktimes.supplier;

import de.esterlino.timeplater.worktimes.model.WorkDay;
import de.esterlino.timeplater.worktimes.model.WorkTime;
import de.esterlino.timeplater.worktimes.model.WorkWeek;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * {@inheritDoc}
 * <br><br>
 * This implementation of {@link WorkWeekSupplier} uses a .xlsx-file to
 * construct/supply the requested {@link WorkWeek}.
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
     *
     * @param excelFileSupplier Path of the excel-workbook / XLSX-file
     * @throws ExcelWorkbookInitializationException If Workbook can't be found,
     * accessed or other I/O-error
     */
    public ExcelWorkWeekSupplier(final ExcelWorkbookSupplier excelFileSupplier) {
        workTimeWorkbook = getWorkbookFromSupplier(excelFileSupplier);
    }

    private Workbook getWorkbookFromSupplier(final ExcelWorkbookSupplier workbookSupplier) {
        Workbook toRet = null;

        try {
            toRet = workbookSupplier.supplyWorkbook();
        } catch (ExcelWorkbookInitializationException e) {
            Logger.getLogger(ExcelWorkWeekSupplier.class.getName() + ".constructor(ExcelWorkbookSupplier)").warning("ExcelWorkbookSupplier returned null!");
        }

        return toRet;
    }

    /**
     * {@inheritDoc}
     * <br><br>
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
     * Creates and returns a {@link WorkDay} from the given column index of the
     * desired day. Set calendar-week before calling.
     *
     * @param colIndex Column-index of the day with the work-times
     * @return A {@link WorkDay}-instance with containing all {@link WorkTime}s,
     * in the given column
     *
     * @see setCalendarWeek
     */
    private WorkDay createWorkDayFromWeekDayColIndex(final int colIndex) {
        if (workTimeWorkbook == null) {
            return null;
        }

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

    public WorkWeek[] getAllWorkWeeks() {
        WorkWeek[] workWeeks = new WorkWeek[getCalendarWeekCount()];
        for (int i = 0; i < getCalendarWeekCount(); i++) {
            Sheet currSheet = workTimeWorkbook.getSheetAt(i + 1);
            String currSheetCalWeek = currSheet.getSheetName().split("KW")[1];
            int calWeek = Integer.parseInt(currSheetCalWeek);
            workWeeks[i] = supplyWorkWeek(calWeek);
        }

        return workWeeks;
    }

    public int getCalendarWeekCount() {
        return workTimeWorkbook.getNumberOfSheets() - 2;
    }

    public int getFirstCalendarWeek() {
        String calWeekSheetName = workTimeWorkbook.getSheetName(1);
        calWeekSheetName = calWeekSheetName.split("KW")[1];
        return Integer.parseInt(calWeekSheetName);
    }
}
