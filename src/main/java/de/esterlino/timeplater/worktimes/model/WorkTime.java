package de.esterlino.timeplater.worktimes.model;

import java.time.Duration;
import java.time.LocalTime;

import java.util.logging.Logger;

/**
 * A single work "session", meaning consecutive work time at one place
 * 
 * @author <a href=julien.krause@pm.me>Julien</a>
 */
public class WorkTime {
    private final LocalTime startTime;
    private final LocalTime endTime;

    public WorkTime(final LocalTime startTime, final LocalTime endTime) {
        if (startTime == null || endTime == null) {
            Logger.getLogger(WorkTime.class.getName() + ".WorkTime(LocalTime, LocalTime)").warning(String.format(
                    "Constructed WorkTime with \"%s\" being null", startTime == null ? "startTime" : "endTime"));
        }

        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Duration getWorkDuration() {
        Duration workDuration = null;
        if (startTime != null && endTime != null) {
            workDuration = Duration.between(startTime, endTime);
        }

        return workDuration;
    }
}
