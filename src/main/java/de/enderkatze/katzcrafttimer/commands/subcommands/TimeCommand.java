package de.enderkatze.katzcrafttimer.commands.subcommands;

import de.enderkatze.katzcrafttimer.Main;
import de.enderkatze.katzcrafttimer.timer.Timer;
import de.enderkatze.katzcrafttimer.utitlity.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;


public class TimeCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length >= 2) {
            String amount = args[1];

            Timer timer = Main.getInstance().getTimer();

            String errorMessage = Main.getInstance().getPrefix() + Main.getInstance().getLanguage().getString("timeNotValid") + " " + Main.getInstance().getLanguage().getString("notANumber");


            switch (amount.charAt(0)) {
                case '+':
                    try {
                        timer.setTime(timer.getTime() + Integer.parseInt(amount.substring(1)));
                    } catch (NumberFormatException e) {
                        sender.sendMessage(errorMessage);
                    }
                    break;
                case '-':
                    try {
                        timer.setTime(timer.getTime() - Integer.parseInt(amount.substring(1)));
                    } catch (NumberFormatException e) {
                        sender.sendMessage(errorMessage);
                    }
                    break;
                default:
                    try {
                        timer.setTime(Integer.parseInt(amount));
                    } catch (NumberFormatException e) {
                        sender.sendMessage(errorMessage);
                    }
                    break;
            }
        }
    }

    @Override
    public String getName() {
        return "time";
    }

    @Override
    public List<String> getOptions() {
        return Arrays.asList("+30", "30", "-30");
    }
}
