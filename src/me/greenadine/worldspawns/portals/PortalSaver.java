package me.greenadine.worldspawns.portals;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.bukkit.Server;
import org.bukkit.block.Block;

import me.greenadine.worldspawns.Main;

public class PortalSaver {
	
	private static Main main;
	private static String file;
	
	public PortalSaver(Main main) {
		PortalSaver.main = main;
	}

	public static void write(ArrayList<Portal> portals) {
		try {
			PrintWriter out = new PrintWriter(new FileWriter(new File(main.getDataFolder(), "portals" + File.separator + file)));
			for(Portal portal : portals) {
				file = portal.getName() + ".yml";
				out.print(portal.getName() + ",");
				Block b_main = portal.getMainBlock();
				out.write(b_main.getWorld().getName() + ",");
				out.write("MB,");
				out.write(b_main.getX() + ",");
				out.write(b_main.getY() + ",");
				out.write(b_main.getZ() + ",");
				
				for (int i = 0; i < portal.getPortalBlocks(portal.getName()).size(); i++) {
					Block block = portal.getPortalBlocks(portal.getName()).get(i);
					out.write(i + ",");
					out.write(block.getX() + ",");
					out.write(block.getY() + ",");
					out.write(block.getZ() + ",");
				}
			}
			
			out.flush();
			out.close();
		} catch(Exception e) {
			main.log.severe("Could not save portals to file!");
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	public static ArrayList<Portal> read(Server server) {
		ArrayList<Portal> portals = new ArrayList<Portal>();
		
		try {
			for(Portal portal : portals) {
				Scanner in = new Scanner(new FileReader(new File(main.getDataFolder(), portal.getName() + ".yml")));
				while(in.hasNextLine()) {
					String[] line = in.nextLine().split(",");
					
					for (int i = 0; i < portal.getPortalBlocks(portal.getName()).size(); i++) {
						int x = Integer.parseInt(line[i+7]);
						int y = Integer.parseInt(line[i+8]);
						int z = Integer.parseInt(line[i+9]);
						@SuppressWarnings("unused")
						Block b = server.getWorld(line[1]).getBlockAt(x, y, z);
					}
				}
				
			}
		} catch(Exception e) {
			main.log.severe("Could not load portals from file!");
			e.printStackTrace();
		}
		
		return portals;
	}
}
