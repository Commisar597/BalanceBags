package org.Aei.balance_bags.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ContainerScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.Aei.balance_bags.BalanceBags;
import top.theillusivec4.curios.api.CuriosApi;

@Mod.EventBusSubscriber(modid = BalanceBags.MOD_ID, value = Dist.CLIENT)
public class GuiBackgroundRenderer {

    private static final ResourceLocation BACKPACK_TEXTURE = new ResourceLocation(BalanceBags.MOD_ID, "textures/gui/backpack_bg.png");
    private static final ResourceLocation REGULAR_BAG_TEXTURE = new ResourceLocation(BalanceBags.MOD_ID, "textures/gui/regular_bag_bg.png");
    private static final ResourceLocation POUCH_TEXTURE = new ResourceLocation(BalanceBags.MOD_ID, "textures/gui/pouch_bg.png");
    private static final ResourceLocation DUFFEL_BAG_TEXTURE = new ResourceLocation(BalanceBags.MOD_ID, "textures/gui/duffel_bag_bg.png");

    @SubscribeEvent
    public static void onRenderContainerBackground(ContainerScreenEvent.Render.Background event) {
        if (!(event.getContainerScreen() instanceof InventoryScreen screen)) return;

        Player player = screen.getMinecraft().player;
        if (player == null) return;

        GuiGraphics graphics = event.getGuiGraphics();
        int guiLeft = screen.getGuiLeft();
        int guiTop = screen.getGuiTop();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        if (CuriosApi.getCuriosHelper().findFirstCurio(player, BalanceBags.BACKPACK.get()).isPresent()) {
            graphics.blit(BACKPACK_TEXTURE, guiLeft - 72, guiTop + 30, 0, 0, 68, 130, 68, 130);
        }

        if (CuriosApi.getCuriosHelper().findFirstCurio(player, BalanceBags.REGULAR_BAG.get()).isPresent()) {
            graphics.blit(REGULAR_BAG_TEXTURE, guiLeft + 180, guiTop + 15, 0, 0, 70, 86, 70, 86);
        }

        if (CuriosApi.getCuriosHelper().findFirstCurio(player, BalanceBags.POUCH.get()).isPresent()) {
            graphics.blit(POUCH_TEXTURE, guiLeft + 180, guiTop + 125, 0, 0, 68, 37, 68, 37);
        }

        if (CuriosApi.getCuriosHelper().findFirstCurio(player, BalanceBags.DUFFEL_BAG.get()).isPresent()) {
            graphics.blit(DUFFEL_BAG_TEXTURE, guiLeft, guiTop + 170, 0, 0, 176, 82, 176, 82);
        }

        RenderSystem.disableBlend();
    }
}
