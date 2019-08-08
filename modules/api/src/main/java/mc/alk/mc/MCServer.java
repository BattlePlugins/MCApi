package mc.alk.mc;

import mc.alk.mc.plugin.MCPlugin;

import java.util.Collection;
import java.util.UUID;

public abstract class MCServer {

	private static MCServer INSTANCE;
	private static APIType type;

	public static void setInstance(MCServer api){
		if (INSTANCE == null){
			INSTANCE = api;
			type = api.getAPIType();
		}
	}

	public static MCLocation getLocation(String world, int x, int y, int z){
		return INSTANCE.getMCLocation(world, x, y, z);
	}

	public static MCWorld getWorld(String world){
		return INSTANCE.getMCWorld(world);
	}

	public static long scheduleSyncDelayedTask(MCPlugin plugin, Runnable runnable){
		return scheduleSyncDelayedTask(plugin, runnable,0L);
	}

	public static long scheduleSyncDelayedTask(MCPlugin plugin, Runnable runnable, long millis) {
		return INSTANCE.scheduleSyncTask(plugin, runnable, millis);
	}

	public abstract MCLocation getMCLocation(String world, int x, int y, int z);

	public abstract MCWorld getMCWorld(String world);

	public abstract APIType getAPIType();

	public abstract long scheduleSyncTask(MCPlugin plugin, Runnable runnable, long millis);
	public abstract boolean cancelMCTask(long id);

	public static int scheduleAsynchrounousTask(MCPlugin plugin, Runnable task) {
		return scheduleAsynchrounousTask(plugin, task, 0);
	}

	public static int scheduleAsynchrounousTask(MCPlugin plugin, Runnable task, long millis) {
		return Scheduler.scheduleAsynchrounousTask(task, millis);
	}

	public static MCPlayer getPlayer(String name) {
		return INSTANCE.getMCPlayer(name);
	}

	public abstract MCPlayer getMCPlayer(String name);

	public static APIType getType(){
		return type;
	}

	public static boolean cancelTask(long id) {
		return INSTANCE.cancelMCTask(id);
	}

	public abstract MCOfflinePlayer getMCOfflinePlayer(String name);

	public static MCOfflinePlayer getOfflinePlayer(String name) {
		return INSTANCE.getMCOfflinePlayer(name);
	}

	public abstract MCOfflinePlayer getMCOfflinePlayer(UUID uuid);

	public static MCOfflinePlayer getOfflinePlayer(UUID uuid) {
		return INSTANCE.getMCOfflinePlayer(uuid);
	}

	public abstract Collection<MCPlayer> getMCOnlinePlayers();

	public static Collection<MCPlayer> getOnlinePlayers() {
		return INSTANCE.getMCOnlinePlayers();
	}

	public abstract Collection<MCOfflinePlayer> getMCOfflinePlayers();

	public static Collection<MCOfflinePlayer> getOfflinePlayers() {
		return INSTANCE.getMCOfflinePlayers();
	}
}