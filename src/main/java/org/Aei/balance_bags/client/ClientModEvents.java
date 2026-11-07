package org.Aei.balance_bags.client;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
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
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            CuriosRendererRegistry.register(BalanceBags.POUCH.get(), () -> new BagCurioRenderer(
                    new PouchModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(PouchModel.LAYER_LOCATION)),
                    new ResourceLocation(BalanceBags.MOD_ID, "textures/entity/pouch.png")
            ));

            CuriosRendererRegistry.register(BalanceBags.REGULAR_BAG.get(), () -> new BagCurioRenderer(
                    new RegularBagModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(RegularBagModel.LAYER_LOCATION)),
                    new ResourceLocation(BalanceBags.MOD_ID, "textures/entity/regular_bag.png")
            ));

            CuriosRendererRegistry.register(BalanceBags.DUFFEL_BAG.get(), () -> new BagCurioRenderer(
                    new DuffelBagModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(DuffelBagModel.LAYER_LOCATION)),
                    new ResourceLocation(BalanceBags.MOD_ID, "textures/entity/duffel_bag.png")
            ));

            CuriosRendererRegistry.register(BalanceBags.BACKPACK.get(), () -> new BagCurioRenderer(
                    new BackpackModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(BackpackModel.LAYER_LOCATION)),
                    new ResourceLocation(BalanceBags.MOD_ID, "textures/entity/backpack.png")
            ));
        });
    }
}
