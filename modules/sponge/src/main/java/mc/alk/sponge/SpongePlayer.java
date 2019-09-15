package mc.alk.sponge;

import mc.alk.mc.MCPlayer;
import mc.alk.sponge.entity.SpongeHumanEntity;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.RespawnLocation;

public class SpongePlayer extends SpongeHumanEntity implements MCPlayer {

    private Player player;

    public SpongePlayer(Player player) {
        super(player);

        this.player = player;
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public SpongePlayer getPlayer() {
        return this;
    }

    @Override
    public long getFirstPlayed() {
        return player.get(Keys.FIRST_DATE_PLAYED).get().toEpochMilli();
    }

    @Override
    public long getLastPlayed() {
        return player.get(Keys.LAST_DATE_PLAYED).get().toEpochMilli();
    }

    @Override
    public boolean hasPlayedBefore() {
        return player != null;
    }

    @Override
    public SpongeLocation getBedSpawnLocation() {
        RespawnLocation respawnLoc = player.get(Keys.RESPAWN_LOCATIONS).get().get(player.getWorldUniqueId().get());
        if (respawnLoc != null && respawnLoc.isForced()) {
            return new SpongeLocation(respawnLoc.asLocation().get());
        }
        return null;
    }

    @Override
    public String getDisplayName() {
        return player.getName();
    }

    @Override
    public boolean hasPermission(String node) {
        return player.hasPermission(node);
    }

    @Override
    public void sendMessage(String message) {
        player.sendMessage(Text.of(message));
    }

    @Override
    public boolean isOp() {
        return false; // Why sponge.. why?
    }

    @Override
    public void updateInventory() {
        // TODO: Add API here
    }

    @Override
    public boolean isOnline() {
        return player.isOnline();
    }
}
