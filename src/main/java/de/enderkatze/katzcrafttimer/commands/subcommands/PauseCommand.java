package de.enderkatze.katzcrafttimer.commands.subcommands;

import de.enderkatze.katzcrafttimer.Main;
import de.enderkatze.katzcrafttimer.events.TimerPauseEvent;
import de.enderkatze.katzcrafttimer.timer.Timer;
import de.enderkatze.katzcrafttimer.utitlity.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class PauseCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {

        String Prefix = Main.getInstance().getPrefix();

        FileConfiguration language = Main.getInstance().getLanguage();

        Timer timer = Main.getInstance().getTimer();
        if (!timer.isRunning()) {
            sender.sendMessage(Prefix + ChatColor.valueOf(Main.getInstance().getConfig().getString("errorColor")) + language.getString("alreadyPaused"));
            if(sender instanceof Player) {
                Player player = (Player) sender;
                player.playSound(player, Sound.valueOf(Main.getInstance().getConfig().getString("negativeSound")), 100, 1);
            }
            return;
        }
        timer.setRunning(false);
        Bukkit.broadcastMessage(Prefix + ChatColor.valueOf(Main.getInstance().getConfig().getString("successColor")) + language.getString("paused"));
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player, Sound.valueOf(Main.getInstance().getConfig().getString("positiveSound")), 100, 0);
        }

        TimerPauseEvent timerPauseEvent = new TimerPauseEvent(timer.getTime(), timer.isBackwards());
        Main.getInstance().getServer().getPluginManager().callEvent(timerPauseEvent);
    }

    @Override
    public String getName() {
        return "pause";
    }

    @Override
    public List<String> getOptions() {
        return null;
    }
}
