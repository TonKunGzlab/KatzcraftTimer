package de.enderkatze.katzcrafttimer.commands.subcommands;

import de.enderkatze.katzcrafttimer.Main;
import de.enderkatze.katzcrafttimer.Utils;
import de.enderkatze.katzcrafttimer.utitlity.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;


// TODO rework how holograms are stored
public class HologramCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            Utils utils = new Utils();


            ArmorStand titleHologram = utils.createHologram(player.getWorld(), player.getLocation().add(0, -.3, 0), "none");

            if(args.length > 1) {

                StringBuilder title = new StringBuilder();


                for (int i = 1; i < args.length; i++) {

                    title.append(" ").append(args[i]);
                }


                titleHologram.setCustomName(title.toString().replace('&', '§'));
            } else {
                titleHologram.setCustomName(Main.getInstance().getConfig().getString("defaultHologramTitle"));
            }

            utils.createHologram(player.getWorld(), player.getLocation().add(0, -.6, 0), "days");
            utils.createHologram(player.getWorld(), player.getLocation().add(0, -.9, 0), "hours");
            utils.createHologram(player.getWorld(), player.getLocation().add(0, -1.2, 0), "minutes");
            utils.createHologram(player.getWorld(), player.getLocation().add(0, -1.5, 0), "seconds");
        }
    }

    @Override
    public String getName() {
        return "hologram";
    }

    @Override
    public List<String> getOptions() {
        return Arrays.asList("Title");
    }
}
