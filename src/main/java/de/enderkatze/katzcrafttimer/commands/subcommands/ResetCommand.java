package de.enderkatze.katzcrafttimer.commands.subcommands;

import de.enderkatze.katzcrafttimer.Main;
import de.enderkatze.katzcrafttimer.events.TimerPauseEvent;
import de.enderkatze.katzcrafttimer.timer.Timer;
import de.enderkatze.katzcrafttimer.utitlity.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ResetCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        Timer timer = Main.getInstance().getTimer();

        String Prefix = Main.getInstance().getPrefix();

        timer.setRunning(false);
        timer.setBackwards(false);
        timer.setTime(0);
        Bukkit.broadcastMessage(Prefix + ChatColor.valueOf(Main.getInstance().getConfig().getString("successColor")) + Main.getInstance().getLanguage().getString("reset"));
        if(sender instanceof Player) {
            Player player = (Player) sender;
            player.playSound(player, Sound.valueOf(Main.getInstance().getConfig().getString("positiveSound")), 100, 1);

        }

        TimerPauseEvent timerPauseEvent = new TimerPauseEvent(timer.getTime(), false);
        Main.getInstance().getServer().getPluginManager().callEvent(timerPauseEvent);
    }

    @Override
    public String getName() {
        return "reset";
    }

    @Override
    public List<String> getOptions() {
        return null;
    }
}
