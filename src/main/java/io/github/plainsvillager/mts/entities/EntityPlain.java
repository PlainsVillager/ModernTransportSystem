package io.github.plainsvillager.mts.entities;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.*;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static io.github.plainsvillager.mts.MTS.PLAIN_ITEM;

public class EntityPlain {
    public static class PlainEntity extends Entity {

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

        @Override
        public double getMountedHeightOffset() {
            return 0.3;
        }

        public void travel() {
            LivingEntity livingEntity = (LivingEntity) this.getFirstPassenger();
            if (this.hasPassengers() && livingEntity != null) {
                this.setRotation(livingEntity.getYaw(), livingEntity.getPitch() * 0.5F);
                this.speed = 5;
                float g = livingEntity.forwardSpeed;
                if (g <= 0.0F) {
                    g *= 0.25F;
                }

                if (livingEntity instanceof PlayerEntity) {
                    float yaw = livingEntity.getYaw()/180* MathHelper.PI;
                    float pitch = -livingEntity.getPitch()/180*MathHelper.PI;
                    float x = - g * MathHelper.sin(yaw) * MathHelper.cos(pitch);
                    float y = g * MathHelper.sin(pitch);
                    float z = g * MathHelper.cos(yaw) * MathHelper.cos(pitch);
                    this.move(MovementType.SELF, new Vec3d(x, y, z));
                }
            }
        }

        @Override
        public void pushAwayFrom(Entity entity) {
            if (entity instanceof PlainEntity) {
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
            return PLAIN_ITEM;
        }

        protected void dropItems() {
            this.dropItem(this.asItem(), 0);
        }
    }

    public static class PlainEntityModel extends EntityModel<PlainEntity> {

        private final ModelPart base;

        public PlainEntityModel(ModelPart modelPart) {
            this.base = modelPart.getChild("body");
        }

        // You can use BlockBench, make your model and export it to get this method for your entity model.
        public static TexturedModelData getTexturedModelData() {
            ModelData modelData = new ModelData();
            ModelPartData modelPartData = modelData.getRoot();
            modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(0, 0, 0, 1, 1, 4), ModelTransform.pivot(0F, 0F, 0F));
            return TexturedModelData.of(modelData, 128, 64);
        }

        @Override
        public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
            ImmutableList.of(this.base).forEach((modelRenderer) -> modelRenderer.render(matrices, vertices, light, overlay, red, green, blue, alpha));
        }

        @Override
        public void setAngles(PlainEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

        }
    }

    public static class PlainEntityRenderer extends EntityRenderer<PlainEntity> {

        protected final EntityModel<PlainEntity> model;

        public PlainEntityRenderer(EntityRendererFactory.Context context, PlainEntityModel entityModel, float f) {
            super(context);
            this.model = entityModel;
            this.shadowRadius = f;
        }

        @Override
        public void render(PlainEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
            if (this.hasLabel(entity)) {
                this.renderLabelIfPresent(entity, entity.getDisplayName(), matrices, vertexConsumers, light);
            }
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.model.getLayer(this.getTexture(entity)));
            this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        }

        @Override
        public Identifier getTexture(PlainEntity entity) {
            return new Identifier("mts", "textures/entity/plain.png");
        }

    }

}
