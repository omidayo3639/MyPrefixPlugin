package com.example.myprefixplugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class MyPrefixPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("チャットプレフィックスプラグインが起動しました！");
    }

    @Override
    public void onDisable() {
        getLogger().info("チャットプレフィックスプラグインが停止しました！");
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        Player player = event.getPlayer();
        getConfig().set("kicked." + player.getUniqueId().toString(), true);
        saveConfig();
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String prefix = "";

        boolean isKicked = getConfig().getBoolean("kicked." + player.getUniqueId().toString(), false);
        if (isKicked) {
            prefix += ChatColor.RED + "[!]" + ChatColor.RESET + " ";
        }

        if (player.hasPermission("myprefix.owner")) {
            prefix += ChatColor.GOLD + "[鯖主]" + ChatColor.RESET + " ";
        } else if (player.hasPermission("myprefix.admin")) {
            prefix += ChatColor.AQUA + "[管理者]" + ChatColor.RESET + " ";
        }

        event.setFormat(prefix + "%1$s: %2$s");
    }
}
