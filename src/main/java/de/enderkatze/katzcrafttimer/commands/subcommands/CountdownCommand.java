package de.enderkatze.katzcrafttimer.commands.subcommands;

import de.enderkatze.katzcrafttimer.Main;
import de.enderkatze.katzcrafttimer.timer.Timer;
import de.enderkatze.katzcrafttimer.utitlity.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class CountdownCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {

        String Prefix = Main.getInstance().getPrefix();

        Timer timer = Main.getInstance().getTimer();
        String errorColor = Main.getInstance().getConfig().getString("errorColor");
        if(args.length != 2) {
            sender.sendMessage(Prefix + ChatColor.valueOf(errorColor) + Main.getInstance().getLanguage().getString("noValue"));
            if(sender instanceof Player) {
                Player player = (Player) sender;
                player.playSound(player, Sound.valueOf(Main.getInstance().getConfig().getString("negativeSound")), 100, 1);
            }
        } else {
            try {
                timer.setTime(Integer.parseInt(args[1]));
                timer.setBackwards(true);
                timer.setRunning(true);
                sender.sendMessage(Prefix + ChatColor.GREEN + Main.getInstance().getLanguage().getString("countdownStarted"));
                if(sender instanceof Player) {
                    Player player = (Player) sender;
                    player.playSound(player, Sound.valueOf(Main.getInstance().getConfig().getString("positiveSound")), 100, 0);
                }
            } catch (NumberFormatException e) {
                sender.sendMessage(Prefix + ChatColor.valueOf(errorColor) + Main.getInstance().getLanguage().getString("notANumber"));
                if(sender instanceof Player) {
                    Player player = (Player) sender;
                    player.playSound(player, Sound.valueOf(Main.getInstance().getConfig().getString("negativeSound")), 100, 1);
                }
            }
        }
    }

    @Override
    public String getName() {
        return "countdown";
    }

    @Override
    public List<String> getOptions() {
        return Arrays.asList("30", "60", "3600");
    }
}
