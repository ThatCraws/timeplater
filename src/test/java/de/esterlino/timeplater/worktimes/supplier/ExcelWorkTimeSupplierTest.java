package de.esterlino.timeplater.worktimes.supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.esterlino.timeplater.worktimes.model.WorkDay;
import de.esterlino.timeplater.worktimes.model.WorkWeek;

public class ExcelWorkTimeSupplierTest {

    private static final String TEST_EXCEL_FILE = "src/test/resources/excelWorkTimeSupplierTestFile.xlsx";
    
    private static final WorkDay EXPECTED_MONDAY;
    private static final WorkDay EXPECTED_TUESDAY;
    private static final WorkDay EXPECTED_WEDNESDAY;
    private static final WorkDay EXPECTED_THURSDAY;
    private static final WorkDay EXPECTED_FRIDAY;

    private static final WorkWeek EXPECTED_WORKWEEK = new WorkWeek(new WorkDay[]{
        EXPECTED_MONDAY,
        EXPECTED_TUESDAY,
        EXPECTED_WEDNESDAY,
        EXPECTED_THURSDAY,
        EXPECTED_FRIDAY,
    });

    @Test
    public void supplyWorkWeek_success() {
        ExcelWorkTimeSupplier supplier = new ExcelWorkTimeSupplier(TEST_EXCEL_FILE);
        WorkWeek actualWorkWeek = supplier.supplyWorkWeek(1);

        assertEquals(EXPECTED_WORKWEEK, actualWorkWeek);
    }
}
