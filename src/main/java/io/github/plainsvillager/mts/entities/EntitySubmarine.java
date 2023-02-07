package io.github.plainsvillager.mts.entities;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.BlockState;
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
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

import static io.github.plainsvillager.mts.MTS.SUBMARINE_ITEM;

public class EntitySubmarine {

    public static class SubmarineEntity extends Entity {

        public SubmarineEntity(EntityType<?> type, World world) {
            super(type, world);
        }

        @Override
        protected void initDataTracker() {

        }

        @Override
        public void tick() {
            this.travel();
            if (!this.world.getFluidState(this.getBlockPos()).isIn(FluidTags.WATER)) {
                this.move(MovementType.SELF, new Vec3d(0, -2, 0));
            }
            List<Entity> entities = this.getPassengerList();
            if (this.hasPassengers()) {
                for (int i = 0; i < entities.size(); i ++) {
                    if (entities.get(i) != null && entities.get(i) instanceof LivingEntity) {
                        ((LivingEntity)entities.get(i)).addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 2, 255, true, false, true));
                    }
                }
            }
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
                    if (!this.world.getFluidState(this.getBlockPos()).isIn(FluidTags.WATER)) {
                        if (y > 0) y = 0;
                    }
                    float z = g * MathHelper.cos(yaw) * MathHelper.cos(pitch);
                    this.move(MovementType.SELF, new Vec3d(x, y, z));
                }
            }
        }

        @Override
        public void pushAwayFrom(Entity entity) {
            if (entity instanceof SubmarineEntity) {
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
            return SUBMARINE_ITEM;
        }

        protected void dropItems() {
            this.dropItem(this.asItem(), 0);
        }

    }

    public static class SubmarineEntityModel extends EntityModel<SubmarineEntity> {

        private final ModelPart base;

        public SubmarineEntityModel(ModelPart modelPart) {
            this.base = modelPart.getChild("body");
        }

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
        public void setAngles(SubmarineEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

        }

    }

    public static class SubmarineEntityRenderer extends EntityRenderer<SubmarineEntity> {

        protected final EntityModel<SubmarineEntity> model;

        public SubmarineEntityRenderer(EntityRendererFactory.Context context, SubmarineEntityModel entityModel, float f) {
            super(context);
            this.model = entityModel;
            this.shadowRadius = f;
        }

        @Override
        public void render(SubmarineEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
            if (this.hasLabel(entity)) {
                this.renderLabelIfPresent(entity, entity.getDisplayName(), matrices, vertexConsumers, light);
            }
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.model.getLayer(this.getTexture(entity)));
            //logger.info(String.valueOf(light));
            this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        }

        @Override
        public Identifier getTexture(SubmarineEntity entity) {
            return new Identifier("mts", "textures/entity/submarine.png");
        }

    }
}
