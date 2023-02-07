package io.github.plainsvillager.mts;

import io.github.plainsvillager.mts.entities.EntityPlain;
import io.github.plainsvillager.mts.entities.EntitySubmarine;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

import static io.github.plainsvillager.mts.MTS.PLAIN_ENTITY_TYPE;
import static io.github.plainsvillager.mts.MTS.SUBMARINE_ENTITY_TYPE;

public class MTSClient implements ClientModInitializer {
    public static final EntityModelLayer MODEL_PLAIN_LAYER = new EntityModelLayer(new Identifier("mts", "plain"), "main");
    public static final EntityModelLayer MODEL_SUBMARINE_LAYER = new EntityModelLayer(new Identifier("mts", "submarine"), "main");
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(PLAIN_ENTITY_TYPE, (context) -> {
            return new EntityPlain.PlainEntityRenderer(context, new EntityPlain.PlainEntityModel(EntityPlain.PlainEntityModel.getTexturedModelData().createModel()), 0.5F);
        });
        EntityModelLayerRegistry.registerModelLayer(MODEL_PLAIN_LAYER, EntityPlain.PlainEntityModel::getTexturedModelData);


        EntityRendererRegistry.register(SUBMARINE_ENTITY_TYPE, (context) -> {
            return new EntitySubmarine.SubmarineEntityRenderer(context, new EntitySubmarine.SubmarineEntityModel(EntitySubmarine.SubmarineEntityModel.getTexturedModelData().createModel()), 0.5F);
        });
        EntityModelLayerRegistry.registerModelLayer(MODEL_SUBMARINE_LAYER, EntitySubmarine.SubmarineEntityModel::getTexturedModelData);
    }
}
