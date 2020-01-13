package org.battleplugins.api.sponge.inventory.item;

import org.battleplugins.api.inventory.item.ItemType;
import org.battleplugins.api.sponge.compat.SpongeCompatItemType;
import org.battleplugins.api.util.NamespacedKey;
import org.battleplugins.api.util.MCWrapper;

public class SpongeItemType extends MCWrapper<org.spongepowered.api.item.ItemType> implements ItemType {

    SpongeItemType(org.spongepowered.api.item.ItemType handle) {
        super(handle);
    }

    @Override
    public NamespacedKey getKey() {
        org.spongepowered.api.item.ItemType spongeItemType = SpongeCompatItemType.fromMaterial(handle)
                .map(item -> item.parseItem().getType()).orElse(handle);
        return NamespacedKey.of(spongeItemType.getId().split(":")[0], spongeItemType.getId().split(":")[1]);
    }

    @Override
    public int getMaximumStackSize() {
        return handle.getMaxStackQuantity();
    }
}