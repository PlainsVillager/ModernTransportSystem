package io.github.plainsvillager.mts.entity.transport;

import io.github.plainsvillager.mts.MTSRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * 恭喜你发现了个彩蛋：这个类的类名！
 */
public class PlainEntity extends Entity {
    public PlainEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    public void tick() {
        this.travel();
    }

    private void updateVelocity() {
        double d = -0.03999999910593033;
        double e = this.hasNoGravity() ? 0.0 : -0.03999999910593033;
        double f = 0.0;
        float velocityDecay = 0.05F;
        Vec3d vec3d = this.getVelocity();
        this.setVelocity(vec3d.x * (double)velocityDecay, vec3d.y + e, vec3d.z * (double)velocityDecay);
        if (f > 0.0) {
            Vec3d vec3d2 = this.getVelocity();
            this.setVelocity(vec3d2.x, (vec3d2.y + f * 0.06153846016296973) * 0.75, vec3d2.z);
        }
    }

    public void travel() {
        LivingEntity livingEntity = (LivingEntity) this.getFirstPassenger();
        if (this.hasPassengers() && livingEntity != null) {
            this.setRotation(livingEntity.getYaw(), livingEntity.getPitch() * 0.5F);
            //float f = livingEntity.sidewaysSpeed * 0.5F;
            this.speed = 5;
            float g = this.speed;//livingEntity.forwardSpeed;
            if (g <= 0.0F) {
                g *= 0.25F;
            }

            if (livingEntity instanceof PlayerEntity) {
                float yaw = livingEntity.getYaw();
                float pitch = livingEntity.getPitch();
                float x = g * MathHelper.sin(yaw) * MathHelper.cos(pitch);
                float y = g * MathHelper.sin(pitch);
                float z = g * MathHelper.cos(yaw) * MathHelper.cos(pitch);
                //this.updateVelocity();
                //logger.info(g +";"+ x +";"+ y +";"+ z);
                this.move(MovementType.SELF, new Vec3d(x, y, z));
            }
        }
    }

    @Override
    public void pushAwayFrom(Entity entity) {
        if (entity instanceof BoatEntity) {
            if (entity.getBoundingBox().minY < this.getBoundingBox().maxY) {
                super.pushAwayFrom(entity);
            }
        } else if (entity.getBoundingBox().minY <= this.getBoundingBox().minY) {
            super.pushAwayFrom(entity);
        }

    }

    @Override
    public boolean canHit() {
        return !this.isRemoved();
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (player.shouldCancelInteraction()) {
            return ActionResult.PASS;
        } else if (!this.world.isClient) {
            return player.startRiding(this) ? ActionResult.CONSUME : ActionResult.PASS;
        } else {
            return ActionResult.PASS;
        }
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengerList().size() < 2;
    }

    public static boolean canCollide(Entity entity, Entity other) {
        return (other.isCollidable() || other.isPushable()) && !entity.isConnectedThroughVehicle(other);
    }

    @Override
    public boolean collidesWith(Entity other) {
        return canCollide(this, other);
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else if (!this.world.isClient && !this.isRemoved()) {
            this.discard();
            this.dropItems();
            return true;
        } else {
            return true;
        }
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }

    public Item asItem() {
        return MTSRegistry.MTSItemRegistry.PLAIN_ITEM;
    }

    protected void dropItems() {
        this.dropItem(this.asItem(), 0);
    }
}
