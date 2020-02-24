package org.battleplugins.api.sponge.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import org.battleplugins.api.entity.EntityType;
import org.battleplugins.api.util.Identifier;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class SpongeEntityType implements EntityType {

    private org.spongepowered.api.entity.EntityType type;

    @Override
    public Identifier getIdentifier() {
        return Identifier.of(type.getId().split(":")[0], type.getId().split(":")[1]);
    }
}
