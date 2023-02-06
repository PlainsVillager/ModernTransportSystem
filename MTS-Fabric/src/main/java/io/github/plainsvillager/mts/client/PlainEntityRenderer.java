package io.github.plainsvillager.mts.client;

import io.github.plainsvillager.mts.entity.transport.PlainEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class PlainEntityRenderer extends EntityRenderer<PlainEntity> {
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
        //logger.info(String.valueOf(light));
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public Identifier getTexture(PlainEntity entity) {
        return new Identifier("mts", "textures/entity/plain.png");
    }
}
