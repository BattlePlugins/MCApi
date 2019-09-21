package mc.alk.sponge.block;

import mc.alk.mc.MCPlayer;
import mc.alk.mc.block.MCSign;

import mc.alk.sponge.SpongePlayer;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class SpongeSign extends SpongeBlock implements MCSign {

    private Sign sign;

    public SpongeSign(Sign sign) {
        super(BlockSnapshot.builder().blockState(sign.getBlock()).build());

        this.sign = sign;
    }

    @Override
    public String getLine(int index) {
        return Text.of(sign.getSignData().get(index)).toPlain();
    }

    @Override
    public String[] getLines() {
        String[] lines = new String[sign.getSignData().lines().size()];
        for (int i = 0; i < sign.getSignData().lines().size(); i++) {
            lines[i] = getLine(i);
        }

        return lines;
    }

    @Override
    public void setLine(int index, String msg) {
        SignData signData = sign.getOrCreate(SignData.class).get();
        signData.set(signData.lines().set(index, Text.of(msg)));
        sign.offer(signData);
    }

    @Override
    public void sendSignChange(MCPlayer player, String[] lines) {
        // This isn't possible at the moment, so instead we send the
        // player a message of what is to be displayed.
        Player sPlayer = ((SpongePlayer) player).getHandle();
        for (String line : lines) {
            sPlayer.sendMessage(Text.of(line));
        }
    }
}
