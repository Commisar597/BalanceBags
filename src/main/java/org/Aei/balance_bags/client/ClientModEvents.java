package org.Aei.balance_bags.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.Aei.balance_bags.BalanceBags;
import org.Aei.balance_bags.client.model.*;
import org.Aei.balance_bags.client.renderer.BagCurioRenderer;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod.EventBusSubscriber(modid = BalanceBags.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PouchModel.LAYER_LOCATION, PouchModel::createBodyLayer);
        event.registerLayerDefinition(RegularBagModel.LAYER_LOCATION, RegularBagModel::createBodyLayer);
        event.registerLayerDefinition(DuffelBagModel.LAYER_LOCATION, DuffelBagModel::createBodyLayer);
        event.registerLayerDefinition(BackpackModel.LAYER_LOCATION, BackpackModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(ModKeyMappings.POUCH_SLOT_1);
        event.register(ModKeyMappings.POUCH_SLOT_2);
        event.register(ModKeyMappings.POUCH_SLOT_3);
        event.register(ModKeyMappings.USE_POUCH_ITEM);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Оборачиваем в enqueueWork для безопасного выполнения в потоке клиента
        event.enqueueWork(() -> {
            EntityModelSet entityModels = Minecraft.getInstance().getEntityModels();

            CuriosRendererRegistry.register(BalanceBags.POUCH.get(), () -> new BagCurioRenderer(
                    new PouchModel<>(entityModels.bakeLayer(PouchModel.LAYER_LOCATION)),
                    new ResourceLocation(BalanceBags.MOD_ID, "textures/entity/pouch.png")
            ));

            CuriosRendererRegistry.register(BalanceBags.REGULAR_BAG.get(), () -> new BagCurioRenderer(
                    new RegularBagModel<>(entityModels.bakeLayer(RegularBagModel.LAYER_LOCATION)),
                    new ResourceLocation(BalanceBags.MOD_ID, "textures/entity/regular_bag.png")
            ));

            CuriosRendererRegistry.register(BalanceBags.DUFFEL_BAG.get(), () -> new BagCurioRenderer(
                    new DuffelBagModel<>(entityModels.bakeLayer(DuffelBagModel.LAYER_LOCATION)),
                    new ResourceLocation(BalanceBags.MOD_ID, "textures/entity/duffel_bag.png")
            ));

            CuriosRendererRegistry.register(BalanceBags.BACKPACK.get(), () -> new BagCurioRenderer(
                    new BackpackModel<>(entityModels.bakeLayer(BackpackModel.LAYER_LOCATION)),
                    new ResourceLocation(BalanceBags.MOD_ID, "textures/entity/backpack.png")
            ));
        });
    }
}