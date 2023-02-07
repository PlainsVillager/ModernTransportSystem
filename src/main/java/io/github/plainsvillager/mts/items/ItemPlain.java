package io.github.plainsvillager.mts.items;

import io.github.plainsvillager.mts.entities.EntityPlain;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

import static io.github.plainsvillager.mts.MTS.PLAIN_ENTITY_TYPE;

public class ItemPlain {

    public static class PlainItem extends Item {

        public PlainItem(Settings settings) {
            super(settings);
        }

        public ActionResult useOnBlock(ItemUsageContext context) {
            EntityPlain.PlainEntity plainEntity = new EntityPlain.PlainEntity(PLAIN_ENTITY_TYPE, context.getWorld());
            plainEntity.setPosition(context.getHitPos());
            context.getWorld().spawnEntity(plainEntity);
            return ActionResult.CONSUME;
        }

    }

}