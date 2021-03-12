package ru.cristalix.boards.bukkitapi;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.UUID;

public interface Board {

    UUID getUuid();

    void setTitle(String title);

    String getTitle();

    void addColumn(String header, double width);

    void setLocation(Location location);

    Location getLocation();

    void clearContent();

    void addContent(UUID contentId, String... cellValues);

    void updateContent();

    void updateContent(Player player);

    void updateStructure();

    void updateStructure(Player player);

    Collection<Player> getWatchers();

}
