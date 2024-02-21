package de.esterlino.timeplater.worktimes.supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.esterlino.timeplater.worktimes.model.WorkDay;
import de.esterlino.timeplater.worktimes.model.WorkWeek;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class ExcelWorkWeekSupplierTest {

    private static final String TEST_EXCEL_FILE = "src/test/resources/excelWorkTimeSupplierTestFile.xlsx";
    // Home-Office only, exactly 6 hours (no break)
    private static final WorkDay EXPECTED_MONDAY = new WorkDay(DayOfWeek.MONDAY, LocalTime.of(9, 35), LocalTime.of(15, 35), true); 
    // Office-office only, exactly 6:01 (short break)
    private static final WorkDay EXPECTED_TUESDAY = new WorkDay(DayOfWeek.TUESDAY, LocalTime.of(8, 30), LocalTime.of(17, 30), false); 
    // Hybrid day (home-office -> office-office), both below 6 hours, 9:01h total (long break)
    private static final WorkDay EXPECTED_WEDNESDAY = new WorkDay(DayOfWeek.WEDNESDAY, LocalTime.of(6, 45), LocalTime.of(10, 00), LocalTime.of(11, 45), LocalTime.of(17, 31), false); 
    // Home-office only, 9:01 (long break)
    private static final WorkDay EXPECTED_THURSDAY = new WorkDay(DayOfWeek.THURSDAY, LocalTime.of(8, 10), LocalTime.of(17, 11), true); 
    // Hybrid day, both 6 hours or below, 7:45 total, (short break)
    private static final WorkDay EXPECTED_FRIDAY = new WorkDay(DayOfWeek.FRIDAY, LocalTime.of(7, 30), LocalTime.of(9, 15), LocalTime.of(10, 00), LocalTime.of(16, 00), true); 

    private static final WorkWeek EXPECTED_WORKWEEK = new WorkWeek(6, new WorkDay[]{
        // Order when adding shouldn't matter
        EXPECTED_THURSDAY,
        EXPECTED_MONDAY,
        EXPECTED_FRIDAY,
        EXPECTED_WEDNESDAY,
        EXPECTED_TUESDAY,
    });

    @Test
    public void supplyWorkWeek_success() {
        ExcelWorkWeekSupplier supplier = new ExcelWorkWeekSupplier(TEST_EXCEL_FILE);
        WorkWeek actualWorkWeek = supplier.supplyWorkWeek(EXPECTED_WORKWEEK.getCalendarWeek());

        assertEquals(EXPECTED_WORKWEEK, actualWorkWeek);
    }
}
