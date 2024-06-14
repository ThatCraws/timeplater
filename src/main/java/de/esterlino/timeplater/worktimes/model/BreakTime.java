/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.esterlino.timeplater.worktimes.model;

import java.time.Duration;

/**
 *
 * @author Julien
 */
public class BreakTime {
    private Duration breakDuration;
    private boolean atHome;
    
    public BreakTime(final Duration breakDuration, final boolean atHome) {
        this.breakDuration = breakDuration;
        this.atHome = atHome;
    }

    public void setBreakDuration(Duration breakDuration) {
        this.breakDuration = breakDuration;
    }
    
    public Duration getBreakDuration() {
        return breakDuration;
    }

    public void setAtHome(boolean atHome) {
        this.atHome = atHome;
    }

    public boolean isAtHome() {
        return atHome;
    }
    
}
