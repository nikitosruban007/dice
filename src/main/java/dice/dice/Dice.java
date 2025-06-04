/*
 * Copyright (c) 2025 nikitosruban007_
 *
 * Licensed under the MIT License.
 * See LICENSE file for details.
 */

package dice.dice;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

import static org.bukkit.ChatColor.*;


public final class Dice extends JavaPlugin implements Listener {

    private Dice plugin;
    private FileConfiguration config;
    private File configFile;

    private String I, II, III, IV, V, VI, text, text2, text3, s_text, s_text2, s_text3, wait, ertext;
    private String dice = I;
    private String dice2 = I;
    private String dice3 = I;
    private int dado, dado2, dado3, CooldownDuration, radius, cd, ch;
    private int taskId;
    private int cooldownTime = 0;
    private boolean CooldownEnb;


    @Override
    public void onEnable() {
        plugin = this;
        config = this.getConfig();
        parConfig();

        this.getServer().getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(getCommand("dice")).setTabCompleter(new ComCompleter());

        this.saveDefaultConfig();
    }

    private void parConfig() {
        I = config.getString("dicenum.num1");
        II = config.getString("dicenum.num2");
        III = config.getString("dicenum.num3");
        IV = config.getString("dicenum.num4");
        V = config.getString("dicenum.num5");
        VI = config.getString("dicenum.num6");
        radius = config.getInt("radius");
        text = config.getString("texts.text");
        text2 = config.getString("texts.text2");
        text3 = config.getString("texts.text3");
        s_text = config.getString("texts.s_text");
        s_text2 = config.getString("texts.s_text2");
        s_text3 = config.getString("texts.s_text3");
        ertext = config.getString("texts.ertext");
        wait = config.getString("cooldown.wait");
        CooldownDuration = config.getInt("cooldown.duration");
        CooldownEnb = config.getBoolean("cooldown.enabled");
    }

    boolean CooldownEn = CooldownEnb;

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTask(taskId);
    }

    public void numdice() {
        switch (dado) {
            case 2 -> dice = II;
            case 3 -> dice = III;
            case 4 -> dice = IV;
            case 5 -> dice = V;
            case 6 -> dice = VI;
            default -> dice = I;
        }
        switch (dado2) {
            case 2 -> dice2 = II;
            case 3 -> dice2 = III;
            case 4 -> dice2 = IV;
            case 5 -> dice2 = V;
            case 6 -> dice2 = VI;
            default -> dice2 = I;
        }
        switch (dado3) {
            case 2 -> dice3 = II;
            case 3 -> dice3 = III;
            case 4 -> dice3 = IV;
            case 5 -> dice3 = V;
            case 6 -> dice3 = VI;
            default -> dice3 = I;
        }
    }

    public void send1(Location location, double radius, String str, Player p) {
        dado = (int) Math.round(Math.random() * 6);
        numdice();
        String r1 = text.replace("%player%", str).replace("%get%", "roll the dice and get:").replace("%fn%", dice);
        String sr1 = s_text.replace("%you%", "*You").replace("%get%", "roll the dice and get:").replace("%fn%", dice);
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getLocation().distance(location) <= radius) {
                if (player == p) {
                    p.sendMessage(sr1);
                } else {
                    player.sendMessage(r1);
                }
            }
        }
    }

    public void send2(Location location, double radius, String str, Player p) {
        dado = (int) Math.round(Math.random() * 6);
        dado2 = (int) Math.round(Math.random() * 6);
        numdice();
        String r2 = text2.replace("%player%", str).replace("%get%", "roll the dice and get:").replace("%fn%", dice).replace("%sn%", dice2);
        String sr2 = s_text2.replace("%you%", "*You").replace("%get%", "roll the dice and get:").replace("%fn%", dice).replace("%sn%", dice2);
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getLocation().distance(location) <= radius) {
                if (player == p) {
                    p.sendMessage(sr2);
                } else {
                    player.sendMessage(r2);
                }
            }
        }
    }

    public void send3(Location location, double radius, String str, Player p) {
        dado = (int) Math.round(Math.random() * 6);
        dado2 = (int) Math.round(Math.random() * 6);
        dado3 = (int) Math.round(Math.random() * 6);
        numdice();
        String r3 = text3.replace("%player%", str).replace("%get%", "roll the dice and get:").replace("%fn%", dice).replace("%sn%", dice2).replace("%tn%", dice3);
        String sr3 = text3.replace("%player%", "*You").replace("%get%", "roll the dice and get:").replace("%fn%", dice).replace("%sn%", dice2).replace("%tn%", dice3);
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getLocation().distance(location) <= radius) {
                if (player == p) {
                    p.sendMessage(sr3);
                } else {
                    player.sendMessage(r3);
                }
            }
        }
    }


    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String message, String[] args) {
        Player player = (Player) sender;
        Location center = player.getLocation();
        String name = "*" + player.getName();

        if (args[0].equalsIgnoreCase("info")) {
            sender.sendMessage(GREEN + "Dice Plugin. Version: 1.5❤");
        } else if (args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            parConfig();
            sender.sendMessage(GREEN + "Config has been reloaded!");
        } else if (args[0].equalsIgnoreCase("1")) {
                send1(center, radius, name, player);
            } else if (args[0].equalsIgnoreCase("2")) {
                send2(center, radius, name, player);
            } else if (args[0].equalsIgnoreCase("3")) {
                send3(center, radius, name, player);
            }
        return false;
    }


    public Dice getPlugin() {
        return plugin;
    }
}
