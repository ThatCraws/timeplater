package de.esterlino.timeplater.worktimes.ouputter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

import de.esterlino.timeplater.worktimes.model.WorkDay;
import de.esterlino.timeplater.worktimes.model.WorkTime;
import de.esterlino.timeplater.worktimes.model.WorkWeek;

public class MailTemplateWorkWeekOutputter implements WorkWeekOutputter<Void> {
    private final String outputFilePath;

    public MailTemplateWorkWeekOutputter(final String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    /**
     * {@inheritDoc}
     * <br>
     * <br>
     * This implementation creates a mail-template capturing the Home Office times.
     */
    @Override
    public Void createOutput(final WorkWeek workWeek) {
        StringBuffer outputStringBuffer = new StringBuffer();
        for (DayOfWeek dayOfWeek : new DayOfWeek[] { DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY, DayOfWeek.FRIDAY }) {
            WorkDay workDay = workWeek.getWorkDay(dayOfWeek);

            if (workDay != null) {

                outputStringBuffer.append(createOutputWorkDay(workDay, workWeek.getCalendarWeek()));
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

    private String createOutputWorkDay(
            final WorkDay workDay, final int calendarWeek) {

        if (workDay.getHomeTime() == null) {
            return "";
        }

        StringBuilder outputStringBuilder = new StringBuilder();
        WorkTime homeTime = workDay.getHomeTime();
        LocalDate dayOfWork = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        dayOfWork = dayOfWork
                .plusDays((calendarWeek - 1) * 7 + workDay.getDayOfWeek().get(ChronoField.DAY_OF_WEEK) - 1);

        switch (workDay.getDayOfWeek()) {
            case MONDAY:
                outputStringBuilder.append("Mo.");
                break;
            case TUESDAY:
                outputStringBuilder.append("Di.");
                break;
            case WEDNESDAY:
                outputStringBuilder.append("Mi.");
                break;
            case THURSDAY:
                outputStringBuilder.append("Do.");
                break;
            case FRIDAY:
                outputStringBuilder.append("Fr.");
                break;
            default:
                break;
        }

        outputStringBuilder.append(", d. ").append(dayOfWork.format(DateTimeFormatter.ofPattern("dd.LL.")));
        outputStringBuilder.append("\n");

        outputStringBuilder.append(homeTime.getStartTime().toString()).append(" - ")
                .append(homeTime.getEndTime().toString());
        if (!workDay.getBreakDuration().isZero()) {
            outputStringBuilder
                    .append(workDay.isBreakAtHome()
                            ? String.format(" (inkl. %d Minuten Pause)", workDay.getBreakDuration().toMinutes())
                            : " (Pause im Unternehmen)");
        }
        outputStringBuilder.append("\n");
        outputStringBuilder.append("(").append(durationToHourMinuteString(homeTime.getWorkDuration()));
        if (workDay.isBreakAtHome() && !workDay.getBreakDuration().isZero()) {
            outputStringBuilder.append(" - ").append(durationToHourMinuteString(workDay.getBreakDuration()))
                    .append(" = ")
                    .append(durationToHourMinuteString(homeTime.getWorkDuration().minus(workDay.getBreakDuration())));
        }
        outputStringBuilder.append(")");
        outputStringBuilder.append("\n");

        return new String(outputStringBuilder);
    }

    private String durationToHourMinuteString(final Duration duration) {
        long s = duration.getSeconds();
        return String.format("%d:%02d", s / 3600, (s % 3600) / 60);
    }
}
