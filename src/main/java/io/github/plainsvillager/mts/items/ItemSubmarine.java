package io.github.plainsvillager.mts.items;

import io.github.plainsvillager.mts.entities.EntitySubmarine;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

import static io.github.plainsvillager.mts.MTS.SUBMARINE_ENTITY_TYPE;

public class ItemSubmarine {

    public static class SubmarineItem extends Item {

        public SubmarineItem(Settings settings) {
            super(settings);
        }

        public ActionResult useOnBlock(ItemUsageContext context) {
            EntitySubmarine.SubmarineEntity submarineEntity = new EntitySubmarine.SubmarineEntity(SUBMARINE_ENTITY_TYPE, context.getWorld());
            submarineEntity.setPosition(context.getHitPos());
            context.getWorld().spawnEntity(submarineEntity);
            return ActionResult.CONSUME;
        }

    }
}
