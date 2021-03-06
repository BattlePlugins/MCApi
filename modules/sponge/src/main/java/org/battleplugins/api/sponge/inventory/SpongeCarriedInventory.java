package org.battleplugins.api.sponge.inventory;

import org.battleplugins.api.inventory.CarriedInventory;
import org.battleplugins.api.inventory.carrier.Carrier;

public class SpongeCarriedInventory<T extends org.spongepowered.api.item.inventory.type.CarriedInventory, C extends Carrier> extends SpongeInventory<T> implements CarriedInventory<C> {

    private C carrier;

    public SpongeCarriedInventory(T inventory, C carrier) {
        super(inventory);

        this.carrier = carrier;
    }

    @Override
    public C getCarrier() {
        return carrier;
    }
}