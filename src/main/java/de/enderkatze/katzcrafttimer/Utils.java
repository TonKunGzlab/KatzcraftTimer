package de.enderkatze.katzcrafttimer;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.persistence.PersistentDataType;

public class Utils {

    public ArmorStand createHologram(World world, Location location, String type) {

        ArmorStand stand = (ArmorStand) world.spawnEntity(location, EntityType.ARMOR_STAND);

        stand.setGravity(false);
        stand.setVisible(false);
        stand.setCustomNameVisible(true);
        stand.setCustomName("Hologram Test");

        stand.getPersistentDataContainer().set(Main.getInstance().getHologramKey(), PersistentDataType.STRING, type);

        return stand;
    }
}
