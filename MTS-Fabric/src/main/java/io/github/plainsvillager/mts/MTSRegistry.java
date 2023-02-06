package io.github.plainsvillager.mts;

import io.github.plainsvillager.mts.command.TagPlace;
import io.github.plainsvillager.mts.item.PlainItem;
import io.github.plainsvillager.mts.item.PlaneDebris;
import io.github.plainsvillager.mts.entity.transport.PlainEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.registry.DefaultedRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static io.github.plainsvillager.mts.MTS.MOD_ID;


@ModRegistrySign(ModRegistrySignEnum.CLASS_WITHIN)
public final class MTSRegistry {
    /**
     * {@link MTSRegistry#finalRegistry()} 最终注册，模组的一切注册都将<b><i>重定向</i></b>到这里
     *
     * @author 帅气的PlainsVillager
     */
    @ModRegistrySign(ModRegistrySignEnum.FINAL)
    public static void finalRegistry()
    {
        MTSItemRegistry.itemsRegister();
        MTSCommandRegistry.commandsRegister();
    }

    /**
     * Item Registry Class
     *
     * @author PlainsVillager
     * @date 2023/02/04
     */
    @ModRegistrySign(ModRegistrySignEnum.MIDDLE)
    public static final class MTSItemRegistry {
        public static final DefaultedRegistry<Item> ITEMS_REG_KEY = Registries.ITEM;

        public static final Item PLANE_DEBRIS = new PlaneDebris();
        public static Item PLAIN_ITEM = new PlainItem(new Item.Settings().maxCount(1));

        /**
         * This method allows to reg item first time
         */
        @ModRegistrySign(ModRegistrySignEnum.FIRST)
        static void itemsRegister() {
            Registry.register(ITEMS_REG_KEY, new Identifier(MOD_ID, "plane_debris"), PLANE_DEBRIS);
            Registry.register(Registries.ITEM, new Identifier(MOD_ID, "plain"), PLAIN_ITEM);
        }
    }

    @ModRegistrySign(ModRegistrySignEnum.MIDDLE)
    public static final class MTSCommandRegistry {
        @ModRegistrySign(ModRegistrySignEnum.FIRST)
        static void commandsRegister() {
            TagPlace.registerCommand();
        }
    }

    @ModRegistrySign(ModRegistrySignEnum.MIDDLE)
    public static final class MTSEntityRegistry {
        public static EntityType PLAIN_ENTITY_TYPE =
                EntityType.Builder.create(PlainEntity::new, SpawnGroup.MISC)
                        .setDimensions(1.375F, 0.5625F)
                        .maxTrackingRange(10)
                        .build("plain");
        @ModRegistrySign(ModRegistrySignEnum.FIRST)
        static void entitiesRegister(){
            Registry.register(Registries.ENTITY_TYPE, new Identifier(MOD_ID, "plain"), PLAIN_ENTITY_TYPE);
        }
    }

}
