package io.github.plainsvillager.mts;

import io.github.plainsvillager.mts.entities.EntityPlain;
import io.github.plainsvillager.mts.entities.EntitySubmarine;
import io.github.plainsvillager.mts.items.ItemPlain;
import io.github.plainsvillager.mts.items.ItemSubmarine;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.logging.Logger;

public class MTS implements ModInitializer {

    static Logger logger = Logger.getLogger("MTS-Mod");

    public static EntityType PLAIN_ENTITY_TYPE = EntityType.Builder.create(EntityPlain.PlainEntity::new, SpawnGroup.MISC).setDimensions(1.375F, 0.5625F).maxTrackingRange(10).build("plain");
    public static Item PLAIN_ITEM = new ItemPlain.PlainItem(new Item.Settings().maxCount(1));
    public static EntityType SUBMARINE_ENTITY_TYPE = EntityType.Builder.create(EntitySubmarine.SubmarineEntity::new, SpawnGroup.MISC).setDimensions(1.375F, 0.5625F).maxTrackingRange(10).build("submarine");
    public static Item SUBMARINE_ITEM = new ItemSubmarine.SubmarineItem(new Item.Settings().maxCount(1));

    @Override
    public void onInitialize() {
        Registry.register(Registries.ENTITY_TYPE, new Identifier("mts", "plain"), PLAIN_ENTITY_TYPE);
        Registry.register(Registries.ITEM, new Identifier("mts", "plain"), PLAIN_ITEM);
        Registry.register(Registries.ENTITY_TYPE, new Identifier("mts", "submarine"), SUBMARINE_ENTITY_TYPE);
        Registry.register(Registries.ITEM, new Identifier("mts", "submarine"), SUBMARINE_ITEM);
    }
}
