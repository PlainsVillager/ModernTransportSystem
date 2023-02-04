package io.github.plainsvillager.mts.entity.transport;

import io.github.plainsvillager.mts.MTS;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class MTSEntities {
    public static final EntityType<AirPlane> AIR_PLANE = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MTS.MOD_ID, "air_plane"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, AirPlane::new).dimensions(EntityDimensions.fixed(6, 12)).build()
    );
}
