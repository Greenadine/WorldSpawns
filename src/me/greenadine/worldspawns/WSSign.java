package me.greenadine.worldspawns;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import java.util.List;

public class WSSign {

    private Main main;

    private List<Sign> signs;

    private Block block = null;
    private Sign sign = null;
    private SignType type;
    private String line1;
    private String line2;
    private String line3;
    private String line4;

    public WSSign(Sign sign, Main main) {
        this.sign = sign;
        this.main = main;
    }

    public void createNew() {
        signs.add(sign);
    }

    public SignType getType() {
        return type;
    }

    public World getWorld() {
        if (type == SignType.SPAWN) {
            World w = main.getServer().getWorld(line3);
            return w;
        } else {
            return null;
        }
    }

    public Block getBlock() {
        return block;
    }

    public Sign getSign() {
        return (Sign) sign.getBlock().getState();
    }

    public String[] getLines() {
        String[] lines = { line1, line2, line3, line4 };
        return lines;
    }

    public String getLine(int i) {
        if (i == 0) {
            return line1;
        }
        if (i == 1) {
            return line2;
        }
        if (i == 2) {
            return line3;
        }
        if (i == 3) {
            return line4;
        } else {
            return null;
        }
    }

    public void setLine(int i, String value) {
        if (i == 0) {
            line1 = value;
        }
        if (i == 1) {
            line2 = value;
        }
        if (i == 2) {
            line3 = value;
        }
        if (i == 3) {
            line4 = value;
        } else {
            return;
        }
    }

    public void setType(SignType type) {
        this.type = type;
    }
}
