package mc.alk.nukkit;

import cn.nukkit.IPlayer;
import cn.nukkit.OfflinePlayer;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.plugin.Plugin;

import cn.nukkit.plugin.service.ServicePriority;
import mc.alk.mc.APIType;
import mc.alk.mc.MCLocation;
import mc.alk.mc.MCOfflinePlayer;
import mc.alk.mc.MCPlatform;
import mc.alk.mc.MCPlayer;
import mc.alk.mc.chat.Message;
import mc.alk.mc.inventory.MCInventory;
import mc.alk.mc.plugin.MCPlugin;
import mc.alk.mc.MCWorld;
import mc.alk.mc.plugin.MCServicePriority;
import mc.alk.mc.plugin.platform.PlatformPlugin;
import mc.alk.nukkit.chat.NukkitMessage;
import mc.alk.nukkit.inventory.NukkitInventory;
import mc.alk.nukkit.inventory.fakeinventory.VirtualChestInventory;
import mc.alk.nukkit.inventory.fakeinventory.VirtualDoubleChestInventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

public class NukkitPlatform extends MCPlatform {

    @Override
    public APIType getAPIType() {
        return APIType.NUKKIT;
    }

    @Override
    public MCLocation getMCLocation(String world, double x, double y, double z) {
        return new NukkitLocation(world, x, y, z);
    }

    @Override
    public MCLocation getMCLocation(String world, double x, double y, double z, float pitch, float yaw) {
        return new NukkitLocation(world, x, y, z, pitch, yaw);
    }

    @Override
    public MCWorld getMCWorld(String world) {
        return new NukkitWorld(Server.getInstance().getLevelByName(world));
    }

    @Override
    public long scheduleSyncTask(PlatformPlugin plugin, Runnable runnable, long millis) {
        return Server.getInstance().getScheduler().scheduleDelayedTask((Plugin) plugin, runnable, (int) millis/50).getTaskId();
    }

    @Override
    public MCPlayer getMCPlayer(String name) {
        Player player = Server.getInstance().getPlayer(name);
        if (player == null)
            return null;

        return new NukkitPlayer(Server.getInstance().getPlayer(name));
    }

    @Override
    public MCOfflinePlayer getMCOfflinePlayer(String name) {
        IPlayer player = Server.getInstance().getOfflinePlayer(name);
        if (player == null)
            return null;

        if (player instanceof OfflinePlayer)
            return new NukkitOfflinePlayer((OfflinePlayer) player);
        else if (player instanceof Player)
            return new NukkitPlayer((Player) player);

        return null;
    }

    @Override
    public MCOfflinePlayer getMCOfflinePlayer(UUID uuid) {
        IPlayer player = Server.getInstance().getOfflinePlayer(uuid);
        if (player == null)
            return null;

        if (player instanceof OfflinePlayer)
            return new NukkitOfflinePlayer((OfflinePlayer) player);
        else if (player instanceof Player)
            return new NukkitPlayer((Player) player);

        return null;
    }

    @Override
    public Collection<MCPlayer> getMCOnlinePlayers() {
        return Server.getInstance().getOnlinePlayers().values()
                .stream().map(NukkitPlayer::new).collect(Collectors.toList());
    }

    @Override
    public Collection<MCOfflinePlayer> getMCOfflinePlayers() {
        Collection<MCOfflinePlayer> players = new ArrayList<>();
        // TODO: Find a way to do this
        return players;
    }

    @Override
    public boolean isMCMainThread() {
        return Server.getInstance().isPrimaryThread();
    }

    @Override
    public boolean isMCOnlineMode() {
        return Server.getInstance().getProperties().getBoolean("xbox-auth");
    }

    @Override
    public String getMCVersion() {
        return "Bedrock-" + Server.getInstance().getVersion();
    }

    @Override
    public boolean cancelMCTask(long id) {
        Server.getInstance().getScheduler().cancelTask((int) id);
        return true;
    }

    @Override
    public Message getMCMessage() {
        return new NukkitMessage();
    }

    @Override
    public MCInventory createMCInventory(MCPlugin plugin, int slots, String title) {
        // Nukkit on its own does not have support for virtual inventories
        // So instead, we have to use some hacky methods and packets to create this
        // However, they can only be 27 slots (3 rows) or 54 slots (6 rows) in size
        Inventory inventory = new VirtualChestInventory();
        if (slots > 27) {
            inventory = new VirtualDoubleChestInventory();
        }

        return new NukkitInventory(inventory);
    }

    @Override
    public <T> void registerMCService(Class<T> clazz, T service, MCPlugin plugin, MCServicePriority priority) {
        Server.getInstance().getServiceManager().register(clazz, service, (Plugin) plugin.getPlatformPlugin(), ServicePriority.values()[priority.ordinal()]);
    }

    @Override
    public <T> T getMCService(Class<T> clazz) {
        return Server.getInstance().getServiceManager().getProvider(clazz).getProvider();
    }
}
