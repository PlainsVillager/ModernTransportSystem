package io.github.plainsvillager.mts;

import io.github.plainsvillager.mts.client.PlainEntityModel;
import io.github.plainsvillager.mts.client.PlainEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

import static io.github.plainsvillager.mts.MTSRegistry.MTSEntityRegistry.PLAIN_ENTITY_TYPE;

public class MTSClient implements ClientModInitializer {
    public static final EntityModelLayer MODEL_PLAIN_LAYER = new EntityModelLayer(new Identifier("mts", "plain"), "main");
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(PLAIN_ENTITY_TYPE, (context) -> new PlainEntityRenderer(context, new PlainEntityModel(PlainEntityModel.getTexturedModelData().createModel()), 0.5F));
        EntityModelLayerRegistry.registerModelLayer(MODEL_PLAIN_LAYER, PlainEntityModel::getTexturedModelData);
    }
}
