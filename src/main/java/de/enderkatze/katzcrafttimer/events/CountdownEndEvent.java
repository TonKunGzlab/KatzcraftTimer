package de.enderkatze.katzcrafttimer.events;


import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CountdownEndEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }


    public CountdownEndEvent() {


    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
