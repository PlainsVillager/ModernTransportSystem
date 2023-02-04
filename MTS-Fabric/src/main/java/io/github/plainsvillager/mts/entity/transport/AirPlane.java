package io.github.plainsvillager.mts.entity.transport;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

/**
 * <h2>飞机类</h2>
 * @author PlainsVillager ZhiChii
 */
public class AirPlane extends Entity {
    public AirPlane(EntityType<?> type, World world) {
        super(type, world);
    }
    

    /**
     * 不知道啥玩意儿
     */
    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {}

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {}

    @Override
    public boolean isCollidable() {
        return true;
    }
}
