package de.enderkatze.katzcrafttimer.commands.subcommands;

import de.enderkatze.katzcrafttimer.Main;
import de.enderkatze.katzcrafttimer.utitlity.SubCommand;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ReloadCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        Main.getInstance().reloadConfig();

        sender.sendMessage(Main.getInstance().getPrefix() + Main.getInstance().getLanguage().getString("reload"));
        if(sender instanceof Player) {
            Player player = (Player) sender;
            player.playSound(player, Sound.valueOf(Main.getInstance().getConfig().getString("positiveSound")), 100, 0);
        }
    }

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public List<String> getOptions() {
        return null;
    }
}
