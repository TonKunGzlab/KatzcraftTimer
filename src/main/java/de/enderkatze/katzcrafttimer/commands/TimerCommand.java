package de.enderkatze.katzcrafttimer.commands;

import de.enderkatze.katzcrafttimer.Main;
import de.enderkatze.katzcrafttimer.utitlity.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;

public class TimerCommand implements CommandExecutor, TabCompleter {

    private final Map<String, SubCommand> subcommands = new HashMap<>();


    public void registerSubcommand(SubCommand subcommand) {
        subcommands.put(subcommand.getName(), subcommand);
    }

    String Prefix = Main.getInstance().getPrefix();

    String successColor = Main.getInstance().getConfig().getString("successColor");
    String errorColor = Main.getInstance().getConfig().getString("errorColor");

    String usageMessage = Main.getInstance().getLanguage().getString("usage");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 0) {

            sender.sendMessage(Prefix + ChatColor.valueOf(errorColor) + usageMessage);
            return true;
        }
        String subcommandName = args[0].toLowerCase();
        boolean didExecuteCommand = handleSubcommand(subcommands.get(subcommandName), sender, args, 0, "katzcrafttimer");

        if(!didExecuteCommand) {
            sender.sendMessage(Prefix + Main.getInstance().getLanguage().getString("usage"));
        }


        return true;
    }

    private boolean handleSubcommand(SubCommand subCommand, CommandSender sender, String[] args, int depth, String topLevelPermission) {

        if(subCommand != null) {

            String subcommandPermission = topLevelPermission + "." + subCommand.getName();


                if(sender.hasPermission(subcommandPermission)) {
                    subCommand.execute(sender, args);
                }
                return true;
            }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if(args.length == 0) return list;

        if(args.length == 1) {
            for (SubCommand subCommand : subcommands.values()) {
                list.add(subCommand.getName());
            }
        }

        if(args.length >= 2) {
            if(subcommands.containsKey(args[0])) {
                SubCommand selected = subcommands.get(args[0]);
                list = new ArrayList<String>(selected.getOptions());

            }
        }



        ArrayList<String> completerList = new ArrayList<>();
        String arg = args[args.length - 1].toLowerCase();
        for (String s : list) {
            String s1 = s.toLowerCase();
            if(s1.startsWith(arg)) {
                completerList.add(s);
            }
        }
        
        return completerList;
    }
}
