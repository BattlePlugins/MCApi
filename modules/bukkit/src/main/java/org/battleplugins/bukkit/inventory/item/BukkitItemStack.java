package org.battleplugins.bukkit.inventory.item;

import java.util.HashMap;
import java.util.Map;

import org.battleplugins.bukkit.util.BukkitMaterialAdapter;
import org.battleplugins.inventory.item.ItemRegistry;
import org.battleplugins.inventory.item.ItemType;
import org.battleplugins.util.MCWrapper;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class BukkitItemStack extends MCWrapper<ItemStack> implements org.battleplugins.inventory.item.ItemStack {

	public BukkitItemStack(ItemStack itemStack) {
		super(itemStack == null ? new ItemStack(Material.AIR): itemStack);
	}

	@Override
	public void setType(ItemType type) {
		handle.setType(BukkitMaterialAdapter.getMaterial(type.getIdentifier()).orElse(Material.AIR));
	}

	@Override
	public ItemType getType() {
		return ((BukkitItemRegistry) ItemRegistry.REGISTRY).fromPlatformItem(handle.getType());
	}

	@Override
	public void setQuantity(int quantity) {
		handle.setAmount(quantity);
	}

	@Override
	public int getQuantity() {
		return handle.getAmount();
	}

	@Override
	public Map<String, Integer> getEnchantments() {
		Map<String, Integer> encs = new HashMap<>();
		for (Map.Entry<Enchantment, Integer> entry : handle.getEnchantments().entrySet()) {
			encs.put(entry.getKey().getName(), entry.getValue());
		}
		return encs;
	}
	@Override
	public void addEnchantment(String ench, int level) {
		handle.addEnchantment(Enchantment.getByName(ench), level);
	}

	@Override
	public BukkitItemStack clone(){
		return new BukkitItemStack(handle.clone());
	}
}