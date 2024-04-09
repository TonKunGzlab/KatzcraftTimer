package de.enderkatze.katzcrafttimer.listeners;

import de.enderkatze.katzcrafttimer.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent event) {

        if(Main.getInstance().updateAvailable && event.getPlayer().isOp()) {

            event.getPlayer().sendMessage(Main.getInstance().getPrefix() + Main.getInstance().getLanguage().getString("updateChecker.update"));
        }
    }
}
