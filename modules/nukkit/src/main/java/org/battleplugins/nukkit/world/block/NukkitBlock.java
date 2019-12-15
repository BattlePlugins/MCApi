package org.battleplugins.nukkit.world.block;

import cn.nukkit.block.Block;

import org.battleplugins.nukkit.util.NukkitUtil;
import org.battleplugins.nukkit.world.NukkitWorld;
import org.battleplugins.util.MCWrapper;
import org.battleplugins.world.Location;
import org.battleplugins.world.block.BlockRegistry;
import org.battleplugins.world.block.BlockType;

public class NukkitBlock extends MCWrapper<Block> implements org.battleplugins.world.block.Block {

	public NukkitBlock(Block block) {
		super(block);
	}

	@Override
	public NukkitWorld getWorld() {
		return new NukkitWorld(handle.getLevel());
	}

	@Override
	public Location getLocation() {
		return NukkitUtil.fromNukkitLocation(handle.getLocation());
	}

	@Override
	public BlockType getType() {
		return ((NukkitBlockRegistry) BlockRegistry.REGISTRY).fromPlatformBlock(handle);
	}

	@Override
	public NukkitBlock clone(){
		return new NukkitBlock(handle);
	}
}
