package org.battleplugins.api.bukkit.registry.entity;

import org.battleplugins.api.bukkit.entity.BukkitEntityType;
import org.battleplugins.api.registry.entity.EntityRegistry;
import org.battleplugins.api.util.Identifier;
import org.bukkit.entity.EntityType;

import java.util.Optional;

public class BukkitEntityRegistry extends EntityRegistry {

    @Override
    public Optional<org.battleplugins.api.entity.EntityType> fromIdentifier(Identifier identifier) {
        return Optional.ofNullable(EntityType.fromName(identifier.getKey())).map(BukkitEntityType::new);
    }
}
