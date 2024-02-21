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
    
    private static final WorkDay EXPECTED_MONDAY = new WorkDay(DayOfWeek.MONDAY, LocalTime.of(9, 35), LocalTime.of(16, 50), true); 
    private static final WorkDay EXPECTED_TUESDAY = new WorkDay(DayOfWeek.TUESDAY, LocalTime.of(8, 30), LocalTime.of(14, 30), false); 
    private static final WorkDay EXPECTED_WEDNESDAY = new WorkDay(DayOfWeek.WEDNESDAY, LocalTime.of(8, 00), LocalTime.of(8, 45), LocalTime.of(10, 00), LocalTime.of(16, 15), false); 
    private static final WorkDay EXPECTED_THURSDAY = new WorkDay(DayOfWeek.THURSDAY, LocalTime.of(8, 10), LocalTime.of(15, 40), true); 
    private static final WorkDay EXPECTED_FRIDAY = new WorkDay(DayOfWeek.FRIDAY, LocalTime.of(9, 10), LocalTime.of(17, 40), true); 

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
        WorkWeek actualWorkWeek = supplier.supplyWorkWeek(1);

        assertEquals(EXPECTED_WORKWEEK, actualWorkWeek);
    }
}
