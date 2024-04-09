package de.enderkatze.katzcrafttimer.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class TimerResumeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private int time;
    private boolean isTimerBackwards;

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }


    public TimerResumeEvent(int time, boolean isBackwards) {

        this.time = time;
        this.isTimerBackwards = isBackwards;
    }
}
