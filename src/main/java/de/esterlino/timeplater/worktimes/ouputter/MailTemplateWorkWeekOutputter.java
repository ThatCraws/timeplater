package de.esterlino.timeplater.worktimes.ouputter;

import de.esterlino.timeplater.utils.DateTimeFormatUtils;
import de.esterlino.timeplater.worktimes.model.WorkDay;
import de.esterlino.timeplater.worktimes.model.WorkWeek;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.DayOfWeek;

public class MailTemplateWorkWeekOutputter implements WorkWeekOutputter<Void> {

    private final String outputFilePath;

    public MailTemplateWorkWeekOutputter(final String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    /**
     * {@inheritDoc}
     * <br>
     * <br>
     * This implementation creates a mail-template capturing the Home Office
     * times.
     */
    @Override
    public Void createOutput(final WorkWeek workWeek) {
        StringBuffer outputStringBuffer = new StringBuffer();

        final DayOfWeek[] busDays = new DayOfWeek[] {
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY
        };

        for (DayOfWeek dayOfWeek : busDays) {
            WorkDay workDay = workWeek.getWorkDay(dayOfWeek);

            if (workDay != null) {

                outputStringBuffer.append(DateTimeFormatUtils.workDayFormat(workDay, workWeek.getCalendarWeek()));
                outputStringBuffer.append("\n");
            }
        }

        System.out.println(outputStringBuffer);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(outputFilePath);
            String output = new String(outputStringBuffer);
            fos.write(output.getBytes());
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }

        return null;
    }
}
