package org.battleplugins.api.sponge.world.block;

import org.battleplugins.api.sponge.compat.SpongeCompatItemType;
import org.battleplugins.api.util.Identifier;
import org.battleplugins.api.world.block.BlockRegistry;
import org.battleplugins.api.world.block.BlockType;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockTypes;

import java.util.Optional;

public class SpongeBlockRegistry implements BlockRegistry<org.spongepowered.api.block.BlockType> {

    @Override
    public BlockType fromPlatformBlock(org.spongepowered.api.block.BlockType block) {
        return new SpongeBlockType(block);
    }

    @Override
    public Optional<BlockType> fromIdentifier(Identifier identifier) {
        return SpongeCompatItemType.fromString(identifier.getKey())
                .map(itemType -> fromPlatformBlock(Sponge.getRegistry()
                        .getType(org.spongepowered.api.block.BlockType.class,
                                itemType.name()).orElse(BlockTypes.AIR)));
    }
}
