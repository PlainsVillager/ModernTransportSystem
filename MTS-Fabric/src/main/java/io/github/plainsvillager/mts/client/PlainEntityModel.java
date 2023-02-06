package io.github.plainsvillager.mts.client;

import com.google.common.collect.ImmutableList;
import io.github.plainsvillager.mts.entity.transport.PlainEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class PlainEntityModel extends EntityModel<PlainEntity> {
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
        ImmutableList.of(this.base).forEach((modelRenderer) -> {
            modelRenderer.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        });
    }

    @Override
    public void setAngles(PlainEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }

}
