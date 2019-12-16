package org.battleplugins.bukkit.inventory;

import org.battleplugins.inventory.Inventory;
import org.battleplugins.inventory.item.ItemStack;
import org.bukkit.Bukkit;

public class BukkitInventoryBuilder implements Inventory.Builder {

    private String name;
    private int size;
    private ItemStack[] contents = new ItemStack[54];

    @Override
    public BukkitInventoryBuilder fromInventory(Inventory inventory, String name) {
        this.name = name;
        this.size = ((BukkitInventory) inventory).getHandle().getSize();
        this.contents = inventory.getContents();
        return this;
    }

    @Override
    public BukkitInventoryBuilder name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public BukkitInventoryBuilder size(int size) {
        this.size = size;
        if (this.contents.length > size) {
            throw new IllegalArgumentException("Current inventory contents are larger than the set inventory size!");
        }

        ItemStack[] contents = this.contents.clone();
        System.arraycopy(contents, 0, this.contents, 0, contents.length);
        return this;
    }

    @Override
    public BukkitInventoryBuilder item(int slot, ItemStack item) {
        if (slot > size) {
            throw new IllegalArgumentException("Slot is greater than the inventory size!");
        }
        this.contents[slot] = item;
        return this;
    }

    @Override
    public BukkitInventoryBuilder contents(ItemStack[] contents) {
        if (contents.length > size) {
            throw new IllegalArgumentException("Given contents are larger than the inventory size!");
        }
        this.contents = contents;
        return null;
    }

    @Override
    public BukkitInventory build() {
        BukkitInventory inventory = new BukkitInventory<>(Bukkit.createInventory(null, size, name));
        inventory.setContents(this.contents);
        return inventory;
    }
}