package de.enderkatze.katzcrafttimer.commands.subcommands;

import de.enderkatze.katzcrafttimer.Main;
import de.enderkatze.katzcrafttimer.events.TimerResumeEvent;
import de.enderkatze.katzcrafttimer.timer.Timer;
import de.enderkatze.katzcrafttimer.utitlity.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.Prefix;

import java.util.List;

public class ResumeCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {

        String Prefix = Main.getInstance().getPrefix();

        FileConfiguration language = Main.getInstance().getLanguage();

        Timer timer = Main.getInstance().getTimer();
        if (timer.isRunning()) {
            sender.sendMessage(Prefix + ChatColor.valueOf(Main.getInstance().getConfig().getString("errorColor")) + language.getString("alreadyRunning"));
            if(sender instanceof Player) {
                Player player = (Player) sender;
                player.playSound(player, Sound.valueOf(Main.getInstance().getConfig().getString("negativeSound")), 100, 1);
            }
            return;
        }
        timer.setRunning(true);
        Bukkit.broadcastMessage(Prefix + ChatColor.valueOf(Main.getInstance().getConfig().getString("successColor")) + language.getString("started"));
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player, Sound.valueOf(Main.getInstance().getConfig().getString("positiveSound")), 100, 2);
        }
        TimerResumeEvent timerResumeEvent = new TimerResumeEvent(timer.getTime(), timer.isBackwards());
        Main.getInstance().getServer().getPluginManager().callEvent(timerResumeEvent);
    }

    @Override
    public String getName() {
        return "resume";
    }

    @Override
    public List<String> getOptions() {
        return null;
    }
}
