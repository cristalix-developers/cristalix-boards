package ru.cristalix.boards.bukkitapi;

import org.bukkit.plugin.java.JavaPlugin;

public class BoardsPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Boards.init();
    }
}
