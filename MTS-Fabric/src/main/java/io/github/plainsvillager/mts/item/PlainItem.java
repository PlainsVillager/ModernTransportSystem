package io.github.plainsvillager.mts.item;

import io.github.plainsvillager.mts.entity.transport.PlainEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

import static io.github.plainsvillager.mts.MTSRegistry.MTSEntityRegistry.PLAIN_ENTITY_TYPE;

public class PlainItem extends Item {
    public PlainItem(Settings settings) {
        super(settings);
    }

    /**
     *
     * @param context the usage context
     * @return
     */
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlainEntity plainEntity = new PlainEntity(PLAIN_ENTITY_TYPE, context.getWorld());
        plainEntity.setPosition(context.getHitPos());
        context.getWorld().spawnEntity(plainEntity);
        return ActionResult.CONSUME;
    }

}
