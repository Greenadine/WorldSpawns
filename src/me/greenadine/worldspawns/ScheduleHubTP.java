package me.greenadine.worldspawns;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ScheduleHubTP implements Runnable {

	private int id;
	private final CommandSender sender;
	private final Player target;
	private final Main main;
	SettingsManager settings;
	private String prefix = ChatColor.translateAlternateColorCodes('&', Lang.PREFIX.toString());
	private int counter;
	private int counterMax;

	private double posX;
	private double posY;
	private double posZ;

	public ScheduleHubTP(Main main, CommandSender sender, int counter, double posX, double posY, double posZ) {
		settings = SettingsManager.getInstance();
		this.sender = sender;
		this.main = main;
		this.target = null;

		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;

		if (counter < 1) {
			throw new IllegalArgumentException("Counter must be greater than 1.");
		} else {
			this.counter = counter;
			this.counterMax = counter;
		}
	}

	public ScheduleHubTP(Main main, CommandSender sender, Player target, int counter, double posX, double posY,
			double posZ) {
		settings = SettingsManager.getInstance();
		this.sender = sender;
		this.target = target;
		this.main = main;

		if (counter < 1) {
			throw new IllegalArgumentException("Counter must be greater than 1.");
		} else {
			this.counter = counter;
			this.counterMax = counter;
		}
	}

	public int getId() {
		return id;
	}

	public void setTaskId(int id) {
		this.id = id;
	}

	private void cancel() {
		Bukkit.getScheduler().cancelTask(id);
	}

	@Override
	public void run() {
		if (target == null) {
			Player player = (Player) sender;
			
			if (player.getNoDamageTicks() > 0) {
				player.sendMessage(prefix
						+ ChatColor.translateAlternateColorCodes('&', Lang.TELEPORTING_CANCEL_DAMAGE.toString()));
				this.cancel();
				return;
			}
			
			if (player.getLocation().getX() != posX || player.getLocation().getY() != posY
					|| player.getLocation().getZ() != posZ) {
				player.sendMessage(
						prefix + ChatColor.translateAlternateColorCodes('&', Lang.TELEPORTING_CANCEL_MOVE.toString()));
				this.cancel();
				return;
			}

			if (main.getConfig().getBoolean("settings.countdownAllNumbers")) {
				if (counter > 0) {
					player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
							Lang.TELEPORTING_IN_SECONDS.toString().replace("%counter%", String.valueOf(counter))));
					counter--;
					return;
				} else {
					World w = main.getServer().getWorld(settings.getHub().getString("hub.world"));
					double x2 = settings.getHub().getDouble("hub.x");
					double y2 = settings.getHub().getDouble("hub.y");
					double z2 = settings.getHub().getDouble("hub.z");
					float yaw = settings.getHub().getInt("hub.yaw");
					float pitch = settings.getHub().getInt("hub.pitch");
					player.teleport(new Location(w, x2, y2, z2, yaw, pitch));
					SpawnFirework firework = new SpawnFirework(player, main);
					if (main.getConfig().getBoolean("firework.enable")) {
						if (main.getConfig().getBoolean("firework.random")) {
							firework.spawnRandomFirework();
						} else {
							firework.spawnFirework();
						}
					} else {
						// Do nothing.
					}
					player.sendMessage(prefix
							+ ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_HUB_TELEPORTED.toString()));
					if (enableSounds()) {
						player.playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 10F, 1.0F);
					} else {
						// Nothing.
					}
					this.cancel();
				}
			} else {
				if (counter == counterMax) {
					player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
							Lang.TELEPORTING_IN_SECONDS.toString().replace("%counter%", String.valueOf(counter))));
					counter--;
				} else if (counter > 0 && counter < counterMax) {
					counter--;
					return;
				} else {
					World w = main.getServer().getWorld(settings.getHub().getString("hub.world"));
					double x2 = settings.getHub().getDouble("hub.x");
					double y2 = settings.getHub().getDouble("hub.y");
					double z2 = settings.getHub().getDouble("hub.z");
					float yaw = settings.getHub().getInt("hub.yaw");
					float pitch = settings.getHub().getInt("hub.pitch");
					player.teleport(new Location(w, x2, y2, z2, yaw, pitch));
					SpawnFirework firework = new SpawnFirework(player, main);
					if (main.getConfig().getBoolean("firework.enable")) {
						if (main.getConfig().getBoolean("firework.random")) {
							firework.spawnRandomFirework();
						} else {
							firework.spawnFirework();
						}
					} else {
						// Do nothing.
					}
					player.sendMessage(prefix
							+ ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_HUB_TELEPORTED.toString()));
					if (enableSounds()) {
						player.playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 10F, 1.0F);
					} else {
						// Nothing.
					}
					this.cancel();
				}
			}
		} else {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				double x = target.getLocation().getX();
				double y = target.getLocation().getY();
				double z = target.getLocation().getZ();
				if (x != target.getLocation().getX() || y != target.getLocation().getY()
						|| z != target.getLocation().getZ()) {
					target.sendMessage(prefix
							+ ChatColor.translateAlternateColorCodes('&', Lang.TELEPORTING_CANCEL_MOVE.toString()));
					player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
							Lang.TELEPORTING_CANCEL_MOVE_SENDER.toString().replaceAll("%target%", target.getName())));
					this.cancel();
					return;
				} else if (target.getNoDamageTicks() > 0) {
					target.sendMessage(prefix
							+ ChatColor.translateAlternateColorCodes('&', Lang.TELEPORTING_CANCEL_DAMAGE.toString()));
					player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
							Lang.TELEPORTING_CANCEL_DAMAGE_SENDER.toString().replaceAll("%target%", target.getName())));
					this.cancel();
					return;
				}

				if (main.getConfig().getBoolean("settings.countdownAllNumbers")) {
					if (counter > 0) {
						target.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
								Lang.TELEPORTING_IN_SECONDS.toString().replace("%counter%", String.valueOf(counter))));
						counter--;
						return;
					} else {
						World w = main.getServer().getWorld(settings.getHub().getString("hub.world"));
						double x2 = settings.getHub().getDouble("hub.x");
						double y2 = settings.getHub().getDouble("hub.y");
						double z2 = settings.getHub().getDouble("hub.z");
						float yaw = settings.getHub().getInt("hub.yaw");
						float pitch = settings.getHub().getInt("hub.pitch");
						target.teleport(new Location(w, x2, y2, z2, yaw, pitch));
						SpawnFirework firework = new SpawnFirework(target, main);
						if (main.getConfig().getBoolean("firework.enable")) {
							if (main.getConfig().getBoolean("firework.random")) {
								firework.spawnRandomFirework();
							} else {

								firework.spawnFirework();
							}
						} else {
							// Do nothing.
						}
						player.sendMessage(
								prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_HUB_TARGET_TELEPORTED
										.toString().replaceAll("%target%", target.getName())));
						target.sendMessage(prefix
								+ ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_HUB_TELEPORTED.toString()));
						if (enableSounds()) {
							target.playSound(target.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 10F, 1.0F);
						} else {
							// Nothing.
						}
						this.cancel();
					}
				} else {
					if (counter == counterMax) {
						target.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
								Lang.TELEPORTING_IN_SECONDS.toString().replace("%counter%", String.valueOf(counter))));
						counter--;
						return;
					} else if (counter > 0 && counter < counterMax) {
						counter--;
						return;
					} else {
						World w = main.getServer().getWorld(settings.getHub().getString("hub.world"));
						double x2 = settings.getHub().getDouble("hub.x");
						double y2 = settings.getHub().getDouble("hub.y");
						double z2 = settings.getHub().getDouble("hub.z");
						float yaw = settings.getHub().getInt("hub.yaw");
						float pitch = settings.getHub().getInt("hub.pitch");
						target.teleport(new Location(w, x2, y2, z2, yaw, pitch));
						SpawnFirework firework = new SpawnFirework(target, main);
						if (main.getConfig().getBoolean("firework.enable")) {
							if (main.getConfig().getBoolean("firework.random")) {
								firework.spawnRandomFirework();
							} else {
								firework.spawnFirework();
							}
						} else {
							// Do nothing.
						}
						player.sendMessage(
								prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_HUB_TARGET_TELEPORTED
										.toString().replaceAll("%target%", target.getName())));
						target.sendMessage(prefix
								+ ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_HUB_TELEPORTED.toString()));
						if (enableSounds()) {
							target.playSound(target.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 10F, 1.0F);
						} else {
							// Nothing.
						}
						this.cancel();
					}
				}

			} else {
				double x = target.getLocation().getX();
				double y = target.getLocation().getY();
				double z = target.getLocation().getZ();
				if (x != target.getLocation().getX() || y != target.getLocation().getY()
						|| z != target.getLocation().getZ()) {
					target.sendMessage(prefix
							+ ChatColor.translateAlternateColorCodes('&', Lang.TELEPORTING_CANCEL_MOVE.toString()));
					main.consoleMessage(prefix + ChatColor.translateAlternateColorCodes('&',
							Lang.TELEPORTING_CANCEL_MOVE_SENDER.toString().replaceAll("%target%", target.getName())));
					this.cancel();
					return;
				}
				if (target.getNoDamageTicks() > 0) {
					target.sendMessage(prefix
							+ ChatColor.translateAlternateColorCodes('&', Lang.TELEPORTING_CANCEL_DAMAGE.toString()));
					main.consoleMessage(prefix + ChatColor.translateAlternateColorCodes('&',
							Lang.TELEPORTING_CANCEL_DAMAGE_SENDER.toString().replaceAll("%target%", target.getName())));
					this.cancel();
					return;
				}

				if (main.getConfig().getBoolean("settings.countdownAllNumbers")) {
					if (counter > 0) {
						target.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
								Lang.TELEPORTING_IN_SECONDS.toString().replace("%counter%", String.valueOf(counter))));
						counter--;
						return;
					} else {
						World w = main.getServer().getWorld(settings.getHub().getString("hub.world"));
						double x2 = settings.getHub().getDouble("hub.x");
						double y2 = settings.getHub().getDouble("hub.y");
						double z2 = settings.getHub().getDouble("hub.z");
						float yaw = settings.getHub().getInt("hub.yaw");
						float pitch = settings.getHub().getInt("hub.pitch");
						target.teleport(new Location(w, x2, y2, z2, yaw, pitch));
						SpawnFirework firework = new SpawnFirework(target, main);
						if (main.getConfig().getBoolean("firework.enable")) {
							if (main.getConfig().getBoolean("firework.random")) {
								firework.spawnRandomFirework();
							} else {
								firework.spawnFirework();
							}
						} else {
							// Do nothing.
						}
						main.consoleMessage(
								prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_HUB_TARGET_TELEPORTED
										.toString().replaceAll("%target%", target.getName())));
						target.sendMessage(prefix
								+ ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_HUB_TELEPORTED.toString()));
						if (enableSounds()) {
							target.playSound(target.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 10F, 1.0F);
						} else {
							// Nothing.
						}
						this.cancel();
					}
				} else {
					if (counter == counterMax) {
						target.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
								Lang.TELEPORTING_IN_SECONDS.toString().replace("%counter%", String.valueOf(counter))));
						counter--;
						return;
					} else if (counter > 0 && counter < counterMax) {
						counter--;
						return;
					} else {
						World w = main.getServer().getWorld(settings.getHub().getString("hub.world"));
						double x2 = settings.getHub().getDouble("hub.x");
						double y2 = settings.getHub().getDouble("hub.y");
						double z2 = settings.getHub().getDouble("hub.z");
						float yaw = settings.getHub().getInt("hub.yaw");
						float pitch = settings.getHub().getInt("hub.pitch");
						target.teleport(new Location(w, x2, y2, z2, yaw, pitch));
						SpawnFirework firework = new SpawnFirework(target, main);
						if (main.getConfig().getBoolean("firework.enable")) {
							if (main.getConfig().getBoolean("firework.random")) {
								firework.spawnRandomFirework();
							} else {
								firework.spawnFirework();
							}
						} else {
							// Do nothing.
						}
						main.consoleMessage(
								prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_HUB_TARGET_TELEPORTED
										.toString().replaceAll("%target%", target.getName())));
						target.sendMessage(prefix
								+ ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_HUB_TELEPORTED.toString()));
						if (enableSounds()) {
							target.playSound(target.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 10F, 1.0F);
						} else {
							// Nothing.
						}
						this.cancel();
					}
				}

			}

		}

	}

	private boolean enableSounds() {
		if (main.getConfig().getBoolean("settings.enablesounds"))
			return true;
		if (!main.getConfig().getBoolean("settings.enableSounds")) {
			return false;
		} else {
			main.getConfig().set("settings.enableSounds", Boolean.valueOf(false));
			return false;
		}
	}

}
