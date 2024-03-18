package de.esterlino.timeplater.worktimes.outputter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import de.esterlino.timeplater.worktimes.model.WorkDay;
import de.esterlino.timeplater.worktimes.model.WorkWeek;
import de.esterlino.timeplater.worktimes.ouputter.MailTemplateWorkWeekOutputter;
import de.esterlino.timeplater.worktimes.supplier.ExcelWorkWeekSupplier;
import de.esterlino.timeplater.worktimes.supplier.ExcelWorkbookInitializationException;

public class MailTemplateWorkWeekOutputterTest {

    private static final WorkDay EXPECTED_MONDAY = new WorkDay(DayOfWeek.MONDAY, LocalTime.of(9, 35),
            LocalTime.of(15, 35), true);
    // on-site office only, exactly 6:01 (short break)
    private static final WorkDay EXPECTED_TUESDAY = new WorkDay(DayOfWeek.TUESDAY, LocalTime.of(8, 30),
            LocalTime.of(17, 30), false);
    // Hybrid day (home-office -> on-site office), both below 6 hours, 9:01h total
    // (long break)
    private static final WorkDay EXPECTED_WEDNESDAY = new WorkDay(DayOfWeek.WEDNESDAY, LocalTime.of(6, 45),
            LocalTime.of(10, 00), LocalTime.of(11, 45), LocalTime.of(17, 31));
    // Home-office only, 9:01 (long break)
    private static final WorkDay EXPECTED_THURSDAY = new WorkDay(DayOfWeek.THURSDAY, LocalTime.of(8, 10),
            LocalTime.of(17, 11), true);
    // Hybrid day, both 6 hours or below, 7:45 total, (short break)
    private static final WorkDay EXPECTED_FRIDAY = new WorkDay(DayOfWeek.FRIDAY, LocalTime.of(7, 30),
            LocalTime.of(9, 15), LocalTime.of(10, 00), LocalTime.of(16, 00));

    private static final WorkWeek EXPECTED_WORKWEEK = new WorkWeek(6, new WorkDay[] {
            // Order when adding shouldn't matter
            EXPECTED_THURSDAY,
            EXPECTED_MONDAY,
            EXPECTED_FRIDAY,
            EXPECTED_WEDNESDAY,
            EXPECTED_TUESDAY,
    });

    @Test
    public void testCreateOutput() {
        ExcelWorkWeekSupplier supplier = null;
        try {
            supplier = new ExcelWorkWeekSupplier("src/test/resources/excelWorkTimeSupplierTestFile.xlsx");
        } catch (ExcelWorkbookInitializationException e) {
            e.printStackTrace(System.err);
        }

        MailTemplateWorkWeekOutputter outputter = new MailTemplateWorkWeekOutputter("testOutput.txt");

        if (supplier != null) {
            outputter.createOutput(supplier.supplyWorkWeek(3));
        }
    }
}
