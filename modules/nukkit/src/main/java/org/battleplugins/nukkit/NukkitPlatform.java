package org.battleplugins.nukkit;

import cn.nukkit.Server;
import cn.nukkit.item.Item;
import cn.nukkit.plugin.service.RegisteredServiceProvider;

import mc.euro.version.Version;

import org.battleplugins.PlatformType;
import org.battleplugins.Platform;
import org.battleplugins.message.Message;
import org.battleplugins.nukkit.entity.living.player.NukkitOfflinePlayer;
import org.battleplugins.nukkit.entity.living.player.NukkitPlayer;
import org.battleplugins.nukkit.message.NukkitMessage;
import org.battleplugins.nukkit.inventory.item.NukkitItemStack;
import org.battleplugins.nukkit.world.NukkitWorld;
import org.battleplugins.plugin.Plugin;
import org.battleplugins.plugin.service.ServicePriority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class NukkitPlatform extends Platform {

    private NukkitRegistry registry;

    public NukkitPlatform() {
        registry = new NukkitRegistry();
    }

    @Override
    public PlatformType getType() {
        return PlatformType.NUKKIT;
    }

    @Override
    public Optional<NukkitWorld> getWorld(String world) {
        return Optional.ofNullable(Server.getInstance().getLevelByName(world)).map(NukkitWorld::new);
    }

    @Override
    public long scheduleSyncTask(Plugin plugin, Runnable runnable, long millis) {
        return Server.getInstance().getScheduler().scheduleDelayedTask((cn.nukkit.plugin.Plugin) plugin.getPlatformPlugin(), runnable, (int) millis/50).getTaskId();
    }

    @Override
    public long scheduleRepeatingTask(Plugin plugin, Runnable runnable, long millis) {
        return Server.getInstance().getScheduler().scheduleRepeatingTask((cn.nukkit.plugin.Plugin) plugin, runnable, (int) millis/50).getTaskId();
    }

    @Override
    public Optional<NukkitPlayer> getPlayer(String name) {
        return Optional.ofNullable(Server.getInstance().getPlayer(name)).map(NukkitPlayer::new);
    }

    @Override
    public Optional<NukkitPlayer> getPlayer(UUID uuid) {
        return Server.getInstance().getPlayer(uuid).map(NukkitPlayer::new);
    }

    @Override
    public Optional<NukkitOfflinePlayer> getOfflinePlayer(String name) {
        return Optional.ofNullable(Server.getInstance().getOfflinePlayer(name)).map(NukkitOfflinePlayer::new);
    }

    @Override
    public Optional<NukkitOfflinePlayer> getOfflinePlayer(UUID uuid) {
        return Optional.ofNullable(Server.getInstance().getOfflinePlayer(uuid)).map(NukkitOfflinePlayer::new);
    }

    @Override
    public Collection<NukkitPlayer> getOnlinePlayers() {
        List<NukkitPlayer> playerList = new ArrayList<>();
        for (cn.nukkit.Player player : Server.getInstance().getOnlinePlayers().values()) {
            playerList.add(new NukkitPlayer(player));
        }

        return playerList;
    }

    @Override
    public Collection<NukkitOfflinePlayer> getOfflinePlayers() {
        Collection<NukkitOfflinePlayer> players = new ArrayList<>();
        // TODO: Find a way to do this
        return players;
    }

    @Override
    public boolean isMainThread() {
        return Server.getInstance().isPrimaryThread();
    }

    @Override
    public boolean isOnlineMode() {
        return Server.getInstance().getProperties().getBoolean("xbox-auth");
    }

    @Override
    public Version<NukkitPlatform> getVersion() {
        return new Version<>(Server.getInstance().getVersion());
    }

    @Override
    public Message getDefaultPlatformMessage() {
        return new NukkitMessage();
    }

    @Override
    public NukkitItemStack getDefaultPlatformItemStack() {
        return new NukkitItemStack(Item.get(0));
    }

    @Override
    public boolean cancelTask(long id) {
        Server.getInstance().getScheduler().cancelTask((int) id);
        return true;
    }

    @Override
    public <T> void registerService(Class<T> clazz, T service, Plugin plugin, ServicePriority priority) {
        Server.getInstance().getServiceManager().register(clazz, service, (cn.nukkit.plugin.Plugin) plugin.getPlatformPlugin(), cn.nukkit.plugin.service.ServicePriority.values()[priority.ordinal()]);
    }

    @Override
    public <T> Optional<T> getService(Class<T> clazz) {
        return Optional.ofNullable(Server.getInstance().getServiceManager().getProvider(clazz)).map(RegisteredServiceProvider::getProvider);
    }

    @Override
    public NukkitRegistry getRegistry() {
        return registry;
    }
}
