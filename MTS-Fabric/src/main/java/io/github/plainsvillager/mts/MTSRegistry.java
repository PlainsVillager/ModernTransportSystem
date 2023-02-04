package io.github.plainsvillager.mts;

import io.github.plainsvillager.mts.command.TagPlace;
import io.github.plainsvillager.mts.item.PlaneDebris;
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

        /**
         * This method allows to reg item first time
         */
        @ModRegistrySign(ModRegistrySignEnum.FIRST)
        static void itemsRegister() {
            Registry.register(ITEMS_REG_KEY, new Identifier(MOD_ID, "plane_debris"), PLANE_DEBRIS);
        }
    }

    @ModRegistrySign(ModRegistrySignEnum.MIDDLE)
    public static final class MTSCommandRegistry {
        @ModRegistrySign(ModRegistrySignEnum.FIRST)
        static void commandsRegister() {
            TagPlace.registerCommand();
        }
    }

}
