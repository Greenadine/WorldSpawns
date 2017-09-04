package me.greenadine.worldspawns;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Random;

public class SpawnFirework {

    private Player player;
    private Main main;

    public SpawnFirework(Player player, Main main) {
        this.player = player;
        this.main = main;
    }

    public void spawnRandomFirework() {
        Firework fw = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();

        Random r = new Random();

        int rt = r.nextInt(4) + 1;
        Type type = Type.BALL;
        if (rt == 1)
            type = Type.BALL;
        if (rt == 2)
            type = Type.BALL_LARGE;
        if (rt == 3)
            type = Type.BURST;
        if (rt == 4)
            type = Type.CREEPER;
        if (rt == 5)
            type = Type.STAR;

        int r1i = r.nextInt(17) + 1;
        int r2i = r.nextInt(17) + 1;
        Color c1 = getColor(r1i);
        Color c2 = getColor(r2i);

        FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type)
                .trail(r.nextBoolean()).build();

        fwm.addEffect(effect);

        int rp = r.nextInt(2) + 1;
        fwm.setPower(rp);

        fw.setFireworkMeta(fwm);
    }

    public void spawnFirework() {
        FileConfiguration config = main.getConfig();
        Type type = Type.valueOf(config.getString("firework.rocket.type"));
        String color = config.getString("firework.rocket.color");
        String fade = config.getString("firework.rocket.fade");
        int power = config.getInt("firework.rocket.power");
        boolean flicker = config.getBoolean("firework.rocket.flicker");
        boolean trail = config.getBoolean("firework.rocket.trail");

        Firework fw = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();

        Color c1 = getColor(color);
        Color c2 = getColor(fade);

        FireworkEffect effect = FireworkEffect.builder().flicker(flicker).withColor(c1).withFade(c2).with(type)
                .trail(trail).build();

        fwm.addEffect(effect);
        fwm.setPower(power);

        fw.setFireworkMeta(fwm);
    }

    private Color getColor(int i) {
        Color c = null;
        if (i == 1) {
            c = Color.AQUA;
        }
        if (i == 2) {
            c = Color.BLACK;
        }
        if (i == 3) {
            c = Color.BLUE;
        }
        if (i == 4) {
            c = Color.FUCHSIA;
        }
        if (i == 5) {
            c = Color.GRAY;
        }
        if (i == 6) {
            c = Color.GREEN;
        }
        if (i == 7) {
            c = Color.LIME;
        }
        if (i == 8) {
            c = Color.MAROON;
        }
        if (i == 9) {
            c = Color.NAVY;
        }
        if (i == 10) {
            c = Color.OLIVE;
        }
        if (i == 11) {
            c = Color.ORANGE;
        }
        if (i == 12) {
            c = Color.PURPLE;
        }
        if (i == 13) {
            c = Color.RED;
        }
        if (i == 14) {
            c = Color.SILVER;
        }
        if (i == 15) {
            c = Color.TEAL;
        }
        if (i == 16) {
            c = Color.WHITE;
        }
        if (i == 17) {
            c = Color.YELLOW;
        }
        return c;
    }

    private Color getColor(String color) {
        Color c = null;
        if (color.equals("AQUA")) {
            c = Color.AQUA;
        }
        if (color.equals("BLACK")) {
            c = Color.BLACK;
        }
        if (color.equals("BLUE")) {
            c = Color.BLUE;
        }
        if (color.equals("FUCHSIA")) {
            c = Color.FUCHSIA;
        }
        if (color.equals("GRAY")) {
            c = Color.GRAY;
        }
        if (color.equals("GREEN")) {
            c = Color.GREEN;
        }
        if (color.equals("LIME")) {
            c = Color.LIME;
        }
        if (color.equals("MAROON")) {
            c = Color.MAROON;
        }
        if (color.equals("NAVY")) {
            c = Color.NAVY;
        }
        if (color.equals("OLIVE")) {
            c = Color.OLIVE;
        }
        if (color.equals("ORANGE")) {
            c = Color.ORANGE;
        }
        if (color.equals("PURPLE")) {
            c = Color.PURPLE;
        }
        if (color.equals("RED")) {
            c = Color.RED;
        }
        if (color.equals("SILVER")) {
            c = Color.SILVER;
        }
        if (color.equals("TEAL")) {
            c = Color.TEAL;
        }
        if (color.equals("WHITE")) {
            c = Color.WHITE;
        }
        if (color.equals("YELLOW")) {
            c = Color.YELLOW;
        }
        return c;
    }
}
