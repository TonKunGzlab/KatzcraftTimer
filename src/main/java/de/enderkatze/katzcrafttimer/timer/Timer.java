package de.enderkatze.katzcrafttimer.timer;


import de.enderkatze.katzcrafttimer.Main;
import de.enderkatze.katzcrafttimer.events.CountdownEndEvent;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

public class Timer {

    private boolean running;
    private boolean backwards;
    private int time;
    private boolean displayActionbar;

    private int seconds;
    private int minutes;
    private int hours;

    public boolean isRunning() {
        return running;
    }

    public boolean isBackwards() {
        return backwards;
    }

    public boolean isDisplayActionbar() {return this.displayActionbar;}

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setBackwards(boolean backwards) {

        this.backwards = backwards;
    }

    public void setDisplayActionbar(boolean display) {

        this.displayActionbar = display;
    }

    public int getTime() {
        return time;
    }

    public boolean getBackwards() {

        return backwards;
    }

    public int setTime(int time) {
        this.time = Math.abs(time);

        return this.time;
    }

    public Timer(boolean running, int time, boolean backwards, boolean displayActionbar) {
        this.running = running;
        this.time = time;
        this.backwards = backwards;

        this.displayActionbar = displayActionbar;

        run();
    }

    public void updateHolograms() {

        List<ArmorStand> holograms = new ArrayList<>();

        for(World world : Bukkit.getWorlds()) {

            for(ArmorStand stand : world.getEntitiesByClass(ArmorStand.class)) {

                if(stand.getPersistentDataContainer().has(Main.getInstance().getHologramKey(), PersistentDataType.STRING)) {

                    String colour = Main.getInstance().getConfig().getString("hologramTimerColor");

                    switch (stand.getPersistentDataContainer().get(Main.getInstance().getHologramKey(), PersistentDataType.STRING)) {

                        case "days":

                            String daysString = String.valueOf((getTime()/60/60) / 24);

                            stand.setCustomName(ChatColor.valueOf(colour) + daysString + " Days");
                            break;

                        case "hours":
                            String hoursString = String.valueOf((getTime()/60/60) % 24);

                            if((Integer.parseInt(hoursString) < 10)) {
                                hoursString = "0" + String.valueOf(hoursString);
                            }

                            stand.setCustomName(ChatColor.valueOf(colour) + hoursString + " Hours");
                            break;

                        case "minutes":
                            String minutesString = String.valueOf((getTime() / 60) % 60);

                            if((Integer.parseInt(minutesString) < 10)) {
                                minutesString = "0" + String.valueOf(minutesString);
                            }

                            stand.setCustomName(ChatColor.valueOf(colour) + minutesString + " Minutes");
                            break;

                        case "seconds":
                            String secondsString = String.valueOf((getTime()%60));

                            if((Integer.parseInt(secondsString) < 10)) {
                                secondsString = "0" + String.valueOf(secondsString);
                            }

                            stand.setCustomName(ChatColor.valueOf(colour) + secondsString + " Seconds");
                            break;

                        default:
                            break;
                    }
                }
            }
        }
    }

    void UpdateScoreboard(Player player) {

        Scoreboard scoreboard = player.getScoreboard();

        for(Objective objective: scoreboard.getObjectives()) {

            for(String entry: objective.getScoreboard().getEntries()) {

                Score score = objective.getScore(entry);
            }
        }
    }

    public String getTimeString() {

        String timeString = "";

        seconds = getTime() % 60;
        minutes = (getTime() / 60) % 60;
        hours = getTime() / 60 / 60;

        String secondsStr = String.valueOf(seconds);
        String minutesStr = String.valueOf(minutes);
        String hoursStr = String.valueOf(hours);

        if (seconds < 10) {
            secondsStr = "0" + String.valueOf(seconds);
        }
        if (minutes < 10) {
            minutesStr = "0" + String.valueOf(minutes);
        }
        if (hours < 10) {
            hoursStr = "0" + String.valueOf(hours);
        }

        timeString = hoursStr + ":" + minutesStr + ":" + secondsStr;

        return timeString;
    }

    public void sendActionBar() {

            for (Player player : Bukkit.getOnlinePlayers()) {

                UpdateScoreboard(player);

                if(!Main.getInstance().getToggledActionbarPlayers().contains(player)) {
                    if (!isRunning()) {

                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
                                ChatColor.valueOf(Main.getInstance().getConfig().getString("timerColor")).toString() +
                                        ChatColor.BOLD +Main.getInstance().getLanguage().getString(
                                        "actionbarPausedMessage")));
                    } else {



                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
                                ChatColor.valueOf(Main.getInstance().getConfig().getString("timerColor")).toString() +
                                        ChatColor.BOLD + getTimeString()));
                    }
                }
            }


    }

    private void run() {
        new BukkitRunnable(){
            @Override
            public void run() {
                sendActionBar();
                updateHolograms();
                if(!isRunning()) {
                    return;
                }

                if(isBackwards()) { setTime(getTime() - 1); }
                else { setTime(getTime() + 1); }

                if(getBackwards() && (getTime() <= 0)) {
                    CountdownEndEvent countdownEndEvent = new CountdownEndEvent();
                    Main.getInstance().getServer().getPluginManager().callEvent(countdownEndEvent);
                }

            }
        }.runTaskTimer(Main.getInstance(), 20, 20);
    }
}
